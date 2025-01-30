package com.android.server.soundtrigger_middleware;

import android.media.permission.Identity;
import android.media.permission.IdentityContext;
import android.media.soundtrigger.ModelParameterRange;
import android.media.soundtrigger.PhraseSoundModel;
import android.media.soundtrigger.Properties;
import android.media.soundtrigger.RecognitionConfig;
import android.media.soundtrigger.SoundModel;
import android.media.soundtrigger_middleware.ISoundTriggerCallback;
import android.media.soundtrigger_middleware.ISoundTriggerModule;
import android.media.soundtrigger_middleware.PhraseRecognitionEventSys;
import android.media.soundtrigger_middleware.RecognitionEventSys;
import android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.util.Preconditions;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes3.dex */
public class SoundTriggerMiddlewareValidation implements ISoundTriggerMiddlewareInternal, Dumpable {
  public final ISoundTriggerMiddlewareInternal mDelegate;
  public Map mModules;

  enum ModuleStatus {
    ALIVE,
    DETACHED,
    DEAD
  }

  public class ModuleState {
    public Properties properties;
    public Set sessions;

    public ModuleState(Properties properties) {
      this.sessions = new HashSet();
      this.properties = properties;
    }
  }

  public SoundTriggerMiddlewareValidation(
      ISoundTriggerMiddlewareInternal iSoundTriggerMiddlewareInternal) {
    this.mDelegate = iSoundTriggerMiddlewareInternal;
  }

  public static RuntimeException handleException(Exception exc) {
    if (exc instanceof RecoverableException) {
      throw new ServiceSpecificException(((RecoverableException) exc).errorCode, exc.getMessage());
    }
    Log.wtf("SoundTriggerMiddlewareValidation", "Unexpected exception", exc);
    throw new ServiceSpecificException(5, exc.getMessage());
  }

  @Override // com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal
  public SoundTriggerModuleDescriptor[] listModules() {
    SoundTriggerModuleDescriptor[] listModules;
    synchronized (this) {
      try {
        listModules = this.mDelegate.listModules();
        Map map = this.mModules;
        int i = 0;
        if (map == null) {
          this.mModules = new HashMap(listModules.length);
          int length = listModules.length;
          while (i < length) {
            SoundTriggerModuleDescriptor soundTriggerModuleDescriptor = listModules[i];
            this.mModules.put(
                Integer.valueOf(soundTriggerModuleDescriptor.handle),
                new ModuleState(soundTriggerModuleDescriptor.properties));
            i++;
          }
        } else {
          if (listModules.length != map.size()) {
            throw new RuntimeException("listModules must always return the same result.");
          }
          int length2 = listModules.length;
          while (i < length2) {
            SoundTriggerModuleDescriptor soundTriggerModuleDescriptor2 = listModules[i];
            if (!this.mModules.containsKey(Integer.valueOf(soundTriggerModuleDescriptor2.handle))) {
              throw new RuntimeException("listModules must always return the same result.");
            }
            ((ModuleState) this.mModules.get(Integer.valueOf(soundTriggerModuleDescriptor2.handle)))
                    .properties =
                soundTriggerModuleDescriptor2.properties;
            i++;
          }
        }
      } catch (Exception e) {
        throw handleException(e);
      }
    }
    return listModules;
  }

  @Override // com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal
  public ISoundTriggerModule attach(int i, ISoundTriggerCallback iSoundTriggerCallback, boolean z) {
    Session session;
    Objects.requireNonNull(iSoundTriggerCallback);
    Objects.requireNonNull(iSoundTriggerCallback.asBinder());
    synchronized (this) {
      Map map = this.mModules;
      if (map == null) {
        throw new IllegalStateException("Client must call listModules() prior to attaching.");
      }
      if (!map.containsKey(Integer.valueOf(i))) {
        throw new IllegalArgumentException("Invalid handle: " + i);
      }
      try {
        session = new Session(i, iSoundTriggerCallback);
        session.attach(this.mDelegate.attach(i, session.getCallbackWrapper(), z));
      } catch (Exception e) {
        throw handleException(e);
      }
    }
    return session;
  }

  public String toString() {
    return this.mDelegate.toString();
  }

  @Override // com.android.server.soundtrigger_middleware.Dumpable
  public void dump(PrintWriter printWriter) {
    synchronized (this) {
      Map map = this.mModules;
      if (map != null) {
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
          int intValue = ((Integer) it.next()).intValue();
          ModuleState moduleState = (ModuleState) this.mModules.get(Integer.valueOf(intValue));
          printWriter.println("=========================================");
          printWriter.printf(
              "Module %d\n%s\n",
              Integer.valueOf(intValue), ObjectPrinter.print(moduleState.properties, 16));
          printWriter.println("=========================================");
          Iterator it2 = moduleState.sessions.iterator();
          while (it2.hasNext()) {
            ((Session) it2.next()).dump(printWriter);
          }
        }
      } else {
        printWriter.println("Modules have not yet been enumerated.");
      }
    }
    printWriter.println();
    ISoundTriggerMiddlewareInternal iSoundTriggerMiddlewareInternal = this.mDelegate;
    if (iSoundTriggerMiddlewareInternal instanceof Dumpable) {
      ((Dumpable) iSoundTriggerMiddlewareInternal).dump(printWriter);
    }
  }

  public class ModelState {
    public RecognitionConfig config;
    public final String description;
    public Activity activityState = Activity.LOADED;
    public final Map parameterSupport = new HashMap();

    enum Activity {
      LOADED,
      ACTIVE,
      PREEMPTED
    }

    public ModelState(SoundModel soundModel) {
      this.description = ObjectPrinter.print(soundModel, 16);
    }

    public ModelState(PhraseSoundModel phraseSoundModel) {
      this.description = ObjectPrinter.print(phraseSoundModel, 16);
    }

    public void checkSupported(int i) {
      if (!this.parameterSupport.containsKey(Integer.valueOf(i))) {
        throw new IllegalStateException("Parameter has not been checked for support.");
      }
      if (((ModelParameterRange) this.parameterSupport.get(Integer.valueOf(i))) == null) {
        throw new IllegalArgumentException("Paramater is not supported.");
      }
    }

    public void checkSupported(int i, int i2) {
      if (!this.parameterSupport.containsKey(Integer.valueOf(i))) {
        throw new IllegalStateException("Parameter has not been checked for support.");
      }
      ModelParameterRange modelParameterRange =
          (ModelParameterRange) this.parameterSupport.get(Integer.valueOf(i));
      if (modelParameterRange == null) {
        throw new IllegalArgumentException("Paramater is not supported.");
      }
      Preconditions.checkArgumentInRange(
          i2, modelParameterRange.minInclusive, modelParameterRange.maxInclusive, "value");
    }
  }

  public class Session extends ISoundTriggerModule.Stub {
    public final CallbackWrapper mCallbackWrapper;
    public ISoundTriggerModule mDelegate;
    public final int mHandle;
    public final Map mLoadedModels = new HashMap();
    public ModuleStatus mState = ModuleStatus.ALIVE;
    public final Identity mOriginatorIdentity = IdentityContext.get();

    public Session(int i, ISoundTriggerCallback iSoundTriggerCallback) {
      this.mCallbackWrapper = new CallbackWrapper(iSoundTriggerCallback);
      this.mHandle = i;
    }

    public ISoundTriggerCallback getCallbackWrapper() {
      return this.mCallbackWrapper;
    }

    public void attach(ISoundTriggerModule iSoundTriggerModule) {
      this.mDelegate = iSoundTriggerModule;
      ((ModuleState)
              SoundTriggerMiddlewareValidation.this.mModules.get(Integer.valueOf(this.mHandle)))
          .sessions.add(this);
    }

    public int loadModel(SoundModel soundModel) {
      int loadModel;
      ValidationUtil.validateGenericModel(soundModel);
      synchronized (SoundTriggerMiddlewareValidation.this) {
        if (this.mState == ModuleStatus.DETACHED) {
          throw new IllegalStateException("Module has been detached.");
        }
        try {
          loadModel = this.mDelegate.loadModel(soundModel);
          this.mLoadedModels.put(Integer.valueOf(loadModel), new ModelState(soundModel));
        } catch (Exception e) {
          throw SoundTriggerMiddlewareValidation.handleException(e);
        }
      }
      return loadModel;
    }

    public int loadPhraseModel(PhraseSoundModel phraseSoundModel) {
      int loadPhraseModel;
      ValidationUtil.validatePhraseModel(phraseSoundModel);
      synchronized (SoundTriggerMiddlewareValidation.this) {
        if (this.mState == ModuleStatus.DETACHED) {
          throw new IllegalStateException("Module has been detached.");
        }
        try {
          loadPhraseModel = this.mDelegate.loadPhraseModel(phraseSoundModel);
          this.mLoadedModels.put(
              Integer.valueOf(loadPhraseModel), new ModelState(phraseSoundModel));
        } catch (Exception e) {
          throw SoundTriggerMiddlewareValidation.handleException(e);
        }
      }
      return loadPhraseModel;
    }

    public void unloadModel(int i) {
      synchronized (SoundTriggerMiddlewareValidation.this) {
        if (this.mState == ModuleStatus.DETACHED) {
          throw new IllegalStateException("Module has been detached.");
        }
        ModelState modelState = (ModelState) this.mLoadedModels.get(Integer.valueOf(i));
        if (modelState == null) {
          throw new IllegalStateException("Invalid handle: " + i);
        }
        ModelState.Activity activity = modelState.activityState;
        if (activity != ModelState.Activity.LOADED && activity != ModelState.Activity.PREEMPTED) {
          throw new IllegalStateException(
              "Model with handle: " + i + " has invalid state for unloading");
        }
      }
      try {
        this.mDelegate.unloadModel(i);
        synchronized (SoundTriggerMiddlewareValidation.this) {
          this.mLoadedModels.remove(Integer.valueOf(i));
        }
      } catch (Exception e) {
        throw SoundTriggerMiddlewareValidation.handleException(e);
      }
    }

    public IBinder startRecognition(int i, RecognitionConfig recognitionConfig) {
      IBinder startRecognition;
      ValidationUtil.validateRecognitionConfig(recognitionConfig);
      synchronized (SoundTriggerMiddlewareValidation.this) {
        if (this.mState == ModuleStatus.DETACHED) {
          throw new IllegalStateException("Module has been detached.");
        }
        ModelState modelState = (ModelState) this.mLoadedModels.get(Integer.valueOf(i));
        if (modelState == null) {
          throw new IllegalStateException("Invalid handle: " + i);
        }
        ModelState.Activity activity = modelState.activityState;
        if (activity != ModelState.Activity.LOADED && activity != ModelState.Activity.PREEMPTED) {
          throw new IllegalStateException(
              "Model with handle: " + i + " has invalid state for starting recognition");
        }
        try {
          startRecognition = this.mDelegate.startRecognition(i, recognitionConfig);
          modelState.config = recognitionConfig;
          modelState.activityState = ModelState.Activity.ACTIVE;
        } catch (Exception e) {
          throw SoundTriggerMiddlewareValidation.handleException(e);
        }
      }
      return startRecognition;
    }

    public void stopRecognition(int i) {
      synchronized (SoundTriggerMiddlewareValidation.this) {
        if (this.mState == ModuleStatus.DETACHED) {
          throw new IllegalStateException("Module has been detached.");
        }
        if (((ModelState) this.mLoadedModels.get(Integer.valueOf(i))) == null) {
          throw new IllegalStateException("Invalid handle: " + i);
        }
      }
      try {
        this.mDelegate.stopRecognition(i);
        synchronized (SoundTriggerMiddlewareValidation.this) {
          ModelState modelState = (ModelState) this.mLoadedModels.get(Integer.valueOf(i));
          if (modelState == null) {
            return;
          }
          if (modelState.activityState != ModelState.Activity.PREEMPTED) {
            modelState.activityState = ModelState.Activity.LOADED;
          }
        }
      } catch (Exception e) {
        throw SoundTriggerMiddlewareValidation.handleException(e);
      }
    }

    public void forceRecognitionEvent(int i) {
      synchronized (SoundTriggerMiddlewareValidation.this) {
        if (this.mState == ModuleStatus.DETACHED) {
          throw new IllegalStateException("Module has been detached.");
        }
        ModelState modelState = (ModelState) this.mLoadedModels.get(Integer.valueOf(i));
        if (modelState == null) {
          throw new IllegalStateException("Invalid handle: " + i);
        }
        try {
          if (modelState.activityState == ModelState.Activity.ACTIVE) {
            this.mDelegate.forceRecognitionEvent(i);
          }
        } catch (Exception e) {
          throw SoundTriggerMiddlewareValidation.handleException(e);
        }
      }
    }

    public void setModelParameter(int i, int i2, int i3) {
      ValidationUtil.validateModelParameter(i2);
      synchronized (SoundTriggerMiddlewareValidation.this) {
        if (this.mState == ModuleStatus.DETACHED) {
          throw new IllegalStateException("Module has been detached.");
        }
        ModelState modelState = (ModelState) this.mLoadedModels.get(Integer.valueOf(i));
        if (modelState == null) {
          throw new IllegalStateException("Invalid handle: " + i);
        }
        modelState.checkSupported(i2, i3);
        try {
          this.mDelegate.setModelParameter(i, i2, i3);
        } catch (Exception e) {
          throw SoundTriggerMiddlewareValidation.handleException(e);
        }
      }
    }

    public int getModelParameter(int i, int i2) {
      int modelParameter;
      ValidationUtil.validateModelParameter(i2);
      synchronized (SoundTriggerMiddlewareValidation.this) {
        if (this.mState == ModuleStatus.DETACHED) {
          throw new IllegalStateException("Module has been detached.");
        }
        ModelState modelState = (ModelState) this.mLoadedModels.get(Integer.valueOf(i));
        if (modelState == null) {
          throw new IllegalStateException("Invalid handle: " + i);
        }
        modelState.checkSupported(i2);
        try {
          modelParameter = this.mDelegate.getModelParameter(i, i2);
        } catch (Exception e) {
          throw SoundTriggerMiddlewareValidation.handleException(e);
        }
      }
      return modelParameter;
    }

    public ModelParameterRange queryModelParameterSupport(int i, int i2) {
      ModelParameterRange queryModelParameterSupport;
      ValidationUtil.validateModelParameter(i2);
      synchronized (SoundTriggerMiddlewareValidation.this) {
        if (this.mState == ModuleStatus.DETACHED) {
          throw new IllegalStateException("Module has been detached.");
        }
        ModelState modelState = (ModelState) this.mLoadedModels.get(Integer.valueOf(i));
        if (modelState == null) {
          throw new IllegalStateException("Invalid handle: " + i);
        }
        try {
          queryModelParameterSupport = this.mDelegate.queryModelParameterSupport(i, i2);
          modelState.parameterSupport.put(Integer.valueOf(i2), queryModelParameterSupport);
        } catch (Exception e) {
          throw SoundTriggerMiddlewareValidation.handleException(e);
        }
      }
      return queryModelParameterSupport;
    }

    public void detach() {
      synchronized (SoundTriggerMiddlewareValidation.this) {
        ModuleStatus moduleStatus = this.mState;
        if (moduleStatus == ModuleStatus.DETACHED) {
          throw new IllegalStateException("Module has already been detached.");
        }
        if (moduleStatus == ModuleStatus.ALIVE && !this.mLoadedModels.isEmpty()) {
          throw new IllegalStateException("Cannot detach while models are loaded.");
        }
        try {
          detachInternal();
        } catch (Exception e) {
          throw SoundTriggerMiddlewareValidation.handleException(e);
        }
      }
    }

    public String toString() {
      return Objects.toString(this.mDelegate);
    }

    public final void detachInternal() {
      try {
        this.mDelegate.detach();
        this.mState = ModuleStatus.DETACHED;
        this.mCallbackWrapper.detached();
        ((ModuleState)
                SoundTriggerMiddlewareValidation.this.mModules.get(Integer.valueOf(this.mHandle)))
            .sessions.remove(this);
      } catch (RemoteException e) {
        throw e.rethrowAsRuntimeException();
      }
    }

    public void dump(PrintWriter printWriter) {
      if (this.mState == ModuleStatus.ALIVE) {
        printWriter.println("-------------------------------");
        printWriter.printf(
            "Session %s, client: %s\n",
            toString(), ObjectPrinter.print(this.mOriginatorIdentity, 16));
        printWriter.println("Loaded models (handle, active, description):");
        printWriter.println();
        printWriter.println("-------------------------------");
        for (Map.Entry entry : this.mLoadedModels.entrySet()) {
          printWriter.print(entry.getKey());
          printWriter.print('\t');
          printWriter.print(((ModelState) entry.getValue()).activityState.name());
          printWriter.print('\t');
          printWriter.print(((ModelState) entry.getValue()).description);
          printWriter.println();
        }
        printWriter.println();
        return;
      }
      printWriter.printf("Session %s is dead", toString());
      printWriter.println();
    }

    public class CallbackWrapper implements ISoundTriggerCallback, IBinder.DeathRecipient {
      public final ISoundTriggerCallback mCallback;

      public CallbackWrapper(ISoundTriggerCallback iSoundTriggerCallback) {
        this.mCallback = iSoundTriggerCallback;
        try {
          iSoundTriggerCallback.asBinder().linkToDeath(this, 0);
        } catch (RemoteException e) {
          throw e.rethrowAsRuntimeException();
        }
      }

      public void detached() {
        this.mCallback.asBinder().unlinkToDeath(this, 0);
      }

      public void onRecognition(int i, RecognitionEventSys recognitionEventSys, int i2) {
        synchronized (SoundTriggerMiddlewareValidation.this) {
          ModelState modelState = (ModelState) Session.this.mLoadedModels.get(Integer.valueOf(i));
          if (!recognitionEventSys.recognitionEvent.recognitionStillActive) {
            modelState.activityState = ModelState.Activity.LOADED;
          }
        }
        try {
          this.mCallback.onRecognition(i, recognitionEventSys, i2);
        } catch (Exception e) {
          Log.w("SoundTriggerMiddlewareValidation", "Client callback exception.", e);
        }
      }

      public void onPhraseRecognition(
          int i, PhraseRecognitionEventSys phraseRecognitionEventSys, int i2) {
        synchronized (SoundTriggerMiddlewareValidation.this) {
          ModelState modelState = (ModelState) Session.this.mLoadedModels.get(Integer.valueOf(i));
          if (!phraseRecognitionEventSys.phraseRecognitionEvent.common.recognitionStillActive) {
            modelState.activityState = ModelState.Activity.LOADED;
          }
        }
        try {
          this.mCallback.onPhraseRecognition(i, phraseRecognitionEventSys, i2);
        } catch (Exception e) {
          Log.w("SoundTriggerMiddlewareValidation", "Client callback exception.", e);
        }
      }

      public void onModelUnloaded(int i) {
        synchronized (SoundTriggerMiddlewareValidation.this) {
          ((ModelState) Session.this.mLoadedModels.get(Integer.valueOf(i))).activityState =
              ModelState.Activity.PREEMPTED;
        }
        try {
          this.mCallback.onModelUnloaded(i);
        } catch (Exception e) {
          Log.w("SoundTriggerMiddlewareValidation", "Client callback exception.", e);
        }
      }

      public void onResourcesAvailable() {
        try {
          this.mCallback.onResourcesAvailable();
        } catch (RemoteException e) {
          Log.e("SoundTriggerMiddlewareValidation", "Client callback exception.", e);
        }
      }

      public void onModuleDied() {
        synchronized (SoundTriggerMiddlewareValidation.this) {
          Session.this.mState = ModuleStatus.DEAD;
        }
        try {
          this.mCallback.onModuleDied();
        } catch (RemoteException e) {
          Log.e("SoundTriggerMiddlewareValidation", "Client callback exception.", e);
        }
      }

      @Override // android.os.IBinder.DeathRecipient
      public void binderDied() {
        SparseArray sparseArray = new SparseArray();
        synchronized (SoundTriggerMiddlewareValidation.this) {
          for (Map.Entry entry : Session.this.mLoadedModels.entrySet()) {
            sparseArray.put(
                ((Integer) entry.getKey()).intValue(),
                ((ModelState) entry.getValue()).activityState);
          }
        }
        for (int i = 0; i < sparseArray.size(); i++) {
          try {
            if (sparseArray.valueAt(i) == ModelState.Activity.ACTIVE) {
              Session.this.mDelegate.stopRecognition(sparseArray.keyAt(i));
            }
            Session.this.mDelegate.unloadModel(sparseArray.keyAt(i));
          } catch (Exception e) {
            throw SoundTriggerMiddlewareValidation.handleException(e);
          }
        }
        synchronized (SoundTriggerMiddlewareValidation.this) {
          for (Map.Entry entry2 : Session.this.mLoadedModels.entrySet()) {
            if (sparseArray.get(((Integer) entry2.getKey()).intValue())
                != ((ModelState) entry2.getValue()).activityState) {
              Log.e(
                  "SoundTriggerMiddlewareValidation",
                  "Unexpected state update in binderDied. Race occurred!");
            }
          }
          if (Session.this.mLoadedModels.size() != sparseArray.size()) {
            Log.e(
                "SoundTriggerMiddlewareValidation",
                "Unexpected state update in binderDied. Race occurred!");
          }
          try {
            Session.this.detachInternal();
          } catch (Exception e2) {
            throw SoundTriggerMiddlewareValidation.handleException(e2);
          }
        }
      }

      public IBinder asBinder() {
        return this.mCallback.asBinder();
      }

      public String toString() {
        return Objects.toString(Session.this.mDelegate);
      }
    }
  }
}
