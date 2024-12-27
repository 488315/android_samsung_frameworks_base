package com.android.server.soundtrigger_middleware;

import android.media.permission.Identity;
import android.media.permission.IdentityContext;
import android.media.soundtrigger.ConfidenceLevel;
import android.media.soundtrigger.ModelParameterRange;
import android.media.soundtrigger.Phrase;
import android.media.soundtrigger.PhraseRecognitionExtra;
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
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.util.Preconditions;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SoundTriggerMiddlewareValidation implements ISoundTriggerMiddlewareInternal, Dumpable {
    public final ISoundTriggerMiddlewareInternal mDelegate;
    public Map mModules;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ModelState {
        public final String description;
        public Activity activityState = Activity.LOADED;
        public final Map parameterSupport = new HashMap();

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        final class Activity {
            public static final /* synthetic */ Activity[] $VALUES;
            public static final Activity ACTIVE;
            public static final Activity LOADED;
            public static final Activity PREEMPTED;

            static {
                Activity activity = new Activity("LOADED", 0);
                LOADED = activity;
                Activity activity2 = new Activity("ACTIVE", 1);
                ACTIVE = activity2;
                Activity activity3 = new Activity("PREEMPTED", 2);
                PREEMPTED = activity3;
                $VALUES = new Activity[]{activity, activity2, activity3};
            }

            public static Activity valueOf(String str) {
                return (Activity) Enum.valueOf(Activity.class, str);
            }

            public static Activity[] values() {
                return (Activity[]) $VALUES.clone();
            }
        }

        public ModelState(PhraseSoundModel phraseSoundModel) {
            this.description = ObjectPrinter.print(phraseSoundModel);
        }

        public ModelState(SoundModel soundModel) {
            this.description = ObjectPrinter.print(soundModel);
        }

        public final void checkSupported(int i) {
            if (!this.parameterSupport.containsKey(Integer.valueOf(i))) {
                throw new IllegalStateException("Parameter has not been checked for support.");
            }
            if (((ModelParameterRange) this.parameterSupport.get(Integer.valueOf(i))) == null) {
                throw new IllegalArgumentException("Paramater is not supported.");
            }
        }

        public final void checkSupported(int i, int i2) {
            if (!this.parameterSupport.containsKey(Integer.valueOf(i))) {
                throw new IllegalStateException("Parameter has not been checked for support.");
            }
            ModelParameterRange modelParameterRange = (ModelParameterRange) this.parameterSupport.get(Integer.valueOf(i));
            if (modelParameterRange == null) {
                throw new IllegalArgumentException("Paramater is not supported.");
            }
            Preconditions.checkArgumentInRange(i2, modelParameterRange.minInclusive, modelParameterRange.maxInclusive, "value");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ModuleState {
        public Properties properties;
        public Set sessions;
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class ModuleStatus {
        public static final /* synthetic */ ModuleStatus[] $VALUES;
        public static final ModuleStatus ALIVE;
        public static final ModuleStatus DEAD;
        public static final ModuleStatus DETACHED;

        static {
            ModuleStatus moduleStatus = new ModuleStatus("ALIVE", 0);
            ALIVE = moduleStatus;
            ModuleStatus moduleStatus2 = new ModuleStatus("DETACHED", 1);
            DETACHED = moduleStatus2;
            ModuleStatus moduleStatus3 = new ModuleStatus("DEAD", 2);
            DEAD = moduleStatus3;
            $VALUES = new ModuleStatus[]{moduleStatus, moduleStatus2, moduleStatus3};
        }

        public static ModuleStatus valueOf(String str) {
            return (ModuleStatus) Enum.valueOf(ModuleStatus.class, str);
        }

        public static ModuleStatus[] values() {
            return (ModuleStatus[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Session extends ISoundTriggerModule.Stub {
        public final CallbackWrapper mCallbackWrapper;
        public ISoundTriggerModule mDelegate;
        public final int mHandle;
        public final Map mLoadedModels = new HashMap();
        public ModuleStatus mState = ModuleStatus.ALIVE;
        public final Identity mOriginatorIdentity = IdentityContext.get();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class CallbackWrapper implements ISoundTriggerCallback, IBinder.DeathRecipient {
            public final ISoundTriggerCallback mCallback;

            public CallbackWrapper(ISoundTriggerCallback iSoundTriggerCallback) {
                this.mCallback = iSoundTriggerCallback;
                try {
                    iSoundTriggerCallback.asBinder().linkToDeath(this, 0);
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            }

            public final IBinder asBinder() {
                return this.mCallback.asBinder();
            }

            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                SparseArray sparseArray = new SparseArray();
                synchronized (SoundTriggerMiddlewareValidation.this) {
                    try {
                        for (Map.Entry entry : ((HashMap) Session.this.mLoadedModels).entrySet()) {
                            sparseArray.put(((Integer) entry.getKey()).intValue(), ((ModelState) entry.getValue()).activityState);
                        }
                    } finally {
                    }
                }
                for (int i = 0; i < sparseArray.size(); i++) {
                    try {
                        if (sparseArray.valueAt(i) == ModelState.Activity.ACTIVE) {
                            Session.this.mDelegate.stopRecognition(sparseArray.keyAt(i));
                        }
                        Session.this.mDelegate.unloadModel(sparseArray.keyAt(i));
                    } catch (Exception e) {
                        SoundTriggerMiddlewareValidation.handleException(e);
                        throw null;
                    }
                }
                synchronized (SoundTriggerMiddlewareValidation.this) {
                    try {
                        for (Map.Entry entry2 : ((HashMap) Session.this.mLoadedModels).entrySet()) {
                            if (sparseArray.get(((Integer) entry2.getKey()).intValue()) != ((ModelState) entry2.getValue()).activityState) {
                                Slog.e("SoundTriggerMiddlewareValidation", "Unexpected state update in binderDied. Race occurred!");
                            }
                        }
                        if (((HashMap) Session.this.mLoadedModels).size() != sparseArray.size()) {
                            Slog.e("SoundTriggerMiddlewareValidation", "Unexpected state update in binderDied. Race occurred!");
                        }
                        try {
                            Session.this.detachInternal();
                        } catch (Exception e2) {
                            SoundTriggerMiddlewareValidation.handleException(e2);
                            throw null;
                        }
                    } finally {
                    }
                }
            }

            public final void onModelUnloaded(int i) {
                synchronized (SoundTriggerMiddlewareValidation.this) {
                    ((ModelState) ((HashMap) Session.this.mLoadedModels).get(Integer.valueOf(i))).activityState = ModelState.Activity.PREEMPTED;
                }
                try {
                    this.mCallback.onModelUnloaded(i);
                } catch (Exception e) {
                    Slog.w("SoundTriggerMiddlewareValidation", "Client callback exception.", e);
                }
            }

            public final void onModuleDied() {
                synchronized (SoundTriggerMiddlewareValidation.this) {
                    Session.this.mState = ModuleStatus.DEAD;
                }
                try {
                    this.mCallback.onModuleDied();
                } catch (RemoteException e) {
                    Slog.e("SoundTriggerMiddlewareValidation", "Client callback exception.", e);
                }
            }

            public final void onPhraseRecognition(int i, PhraseRecognitionEventSys phraseRecognitionEventSys, int i2) {
                synchronized (SoundTriggerMiddlewareValidation.this) {
                    try {
                        ModelState modelState = (ModelState) ((HashMap) Session.this.mLoadedModels).get(Integer.valueOf(i));
                        if (!phraseRecognitionEventSys.phraseRecognitionEvent.common.recognitionStillActive) {
                            modelState.activityState = ModelState.Activity.LOADED;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                try {
                    this.mCallback.onPhraseRecognition(i, phraseRecognitionEventSys, i2);
                } catch (Exception e) {
                    Slog.w("SoundTriggerMiddlewareValidation", "Client callback exception.", e);
                }
            }

            public final void onRecognition(int i, RecognitionEventSys recognitionEventSys, int i2) {
                synchronized (SoundTriggerMiddlewareValidation.this) {
                    try {
                        ModelState modelState = (ModelState) ((HashMap) Session.this.mLoadedModels).get(Integer.valueOf(i));
                        if (!recognitionEventSys.recognitionEvent.recognitionStillActive) {
                            modelState.activityState = ModelState.Activity.LOADED;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                try {
                    this.mCallback.onRecognition(i, recognitionEventSys, i2);
                } catch (Exception e) {
                    Slog.w("SoundTriggerMiddlewareValidation", "Client callback exception.", e);
                }
            }

            public final void onResourcesAvailable() {
                try {
                    this.mCallback.onResourcesAvailable();
                } catch (RemoteException e) {
                    Slog.e("SoundTriggerMiddlewareValidation", "Client callback exception.", e);
                }
            }

            public final String toString() {
                return Objects.toString(Session.this.mDelegate);
            }
        }

        public Session(int i, ISoundTriggerCallback iSoundTriggerCallback) {
            this.mCallbackWrapper = new CallbackWrapper(iSoundTriggerCallback);
            this.mHandle = i;
        }

        public final void attach(ISoundTriggerModule iSoundTriggerModule) {
            this.mDelegate = iSoundTriggerModule;
            ((HashSet) ((ModuleState) ((HashMap) SoundTriggerMiddlewareValidation.this.mModules).get(Integer.valueOf(this.mHandle))).sessions).add(this);
        }

        public final void detach() {
            synchronized (SoundTriggerMiddlewareValidation.this) {
                ModuleStatus moduleStatus = this.mState;
                if (moduleStatus == ModuleStatus.DETACHED) {
                    throw new IllegalStateException("Module has already been detached.");
                }
                if (moduleStatus == ModuleStatus.ALIVE && !((HashMap) this.mLoadedModels).isEmpty()) {
                    throw new IllegalStateException("Cannot detach while models are loaded.");
                }
                try {
                    detachInternal();
                } catch (Exception e) {
                    SoundTriggerMiddlewareValidation.handleException(e);
                    throw null;
                }
            }
        }

        public final void detachInternal() {
            try {
                this.mDelegate.detach();
                this.mState = ModuleStatus.DETACHED;
                CallbackWrapper callbackWrapper = this.mCallbackWrapper;
                callbackWrapper.mCallback.asBinder().unlinkToDeath(callbackWrapper, 0);
                ((HashSet) ((ModuleState) ((HashMap) SoundTriggerMiddlewareValidation.this.mModules).get(Integer.valueOf(this.mHandle))).sessions).remove(this);
            } catch (RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }

        public final void dump(PrintWriter printWriter) {
            if (this.mState != ModuleStatus.ALIVE) {
                printWriter.printf("Session %s is dead", Objects.toString(this.mDelegate));
                printWriter.println();
                return;
            }
            printWriter.println("-------------------------------");
            printWriter.printf("Session %s, client: %s\n", Objects.toString(this.mDelegate), ObjectPrinter.print(this.mOriginatorIdentity));
            printWriter.println("Loaded models (handle, active, description):");
            printWriter.println();
            printWriter.println("-------------------------------");
            for (Map.Entry entry : ((HashMap) this.mLoadedModels).entrySet()) {
                printWriter.print(entry.getKey());
                printWriter.print('\t');
                printWriter.print(((ModelState) entry.getValue()).activityState.name());
                printWriter.print('\t');
                printWriter.print(((ModelState) entry.getValue()).description);
                printWriter.println();
            }
            printWriter.println();
        }

        public final void forceRecognitionEvent(int i) {
            synchronized (SoundTriggerMiddlewareValidation.this) {
                if (this.mState == ModuleStatus.DETACHED) {
                    throw new IllegalStateException("Module has been detached.");
                }
                ModelState modelState = (ModelState) ((HashMap) this.mLoadedModels).get(Integer.valueOf(i));
                if (modelState == null) {
                    throw new IllegalStateException("Invalid handle: " + i);
                }
                try {
                    if (modelState.activityState == ModelState.Activity.ACTIVE) {
                        this.mDelegate.forceRecognitionEvent(i);
                    }
                } catch (Exception e) {
                    SoundTriggerMiddlewareValidation.handleException(e);
                    throw null;
                }
            }
        }

        public final int getModelParameter(int i, int i2) {
            int modelParameter;
            if (i2 != 0) {
                throw new IllegalArgumentException("Invalid model parameter");
            }
            synchronized (SoundTriggerMiddlewareValidation.this) {
                try {
                    if (this.mState == ModuleStatus.DETACHED) {
                        throw new IllegalStateException("Module has been detached.");
                    }
                    ModelState modelState = (ModelState) ((HashMap) this.mLoadedModels).get(Integer.valueOf(i));
                    if (modelState == null) {
                        throw new IllegalStateException("Invalid handle: " + i);
                    }
                    modelState.checkSupported(i2);
                    try {
                        modelParameter = this.mDelegate.getModelParameter(i, i2);
                    } catch (Exception e) {
                        SoundTriggerMiddlewareValidation.handleException(e);
                        throw null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return modelParameter;
        }

        public final int loadModel(SoundModel soundModel) {
            int loadModel;
            ValidationUtil.validateModel(soundModel, 1);
            synchronized (SoundTriggerMiddlewareValidation.this) {
                try {
                    if (this.mState == ModuleStatus.DETACHED) {
                        throw new IllegalStateException("Module has been detached.");
                    }
                    try {
                        loadModel = this.mDelegate.loadModel(soundModel);
                        ((HashMap) this.mLoadedModels).put(Integer.valueOf(loadModel), new ModelState(soundModel));
                    } catch (Exception e) {
                        SoundTriggerMiddlewareValidation.handleException(e);
                        throw null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return loadModel;
        }

        public final int loadPhraseModel(PhraseSoundModel phraseSoundModel) {
            int loadPhraseModel;
            Objects.requireNonNull(phraseSoundModel);
            ValidationUtil.validateModel(phraseSoundModel.common, 0);
            Objects.requireNonNull(phraseSoundModel.phrases);
            for (Phrase phrase : phraseSoundModel.phrases) {
                Objects.requireNonNull(phrase);
                if ((phrase.recognitionModes & (-16)) != 0) {
                    throw new IllegalArgumentException("Invalid recognitionModes");
                }
                Objects.requireNonNull(phrase.users);
                Objects.requireNonNull(phrase.locale);
                Objects.requireNonNull(phrase.text);
            }
            synchronized (SoundTriggerMiddlewareValidation.this) {
                try {
                    if (this.mState == ModuleStatus.DETACHED) {
                        throw new IllegalStateException("Module has been detached.");
                    }
                    try {
                        loadPhraseModel = this.mDelegate.loadPhraseModel(phraseSoundModel);
                        ((HashMap) this.mLoadedModels).put(Integer.valueOf(loadPhraseModel), new ModelState(phraseSoundModel));
                    } catch (Exception e) {
                        SoundTriggerMiddlewareValidation.handleException(e);
                        throw null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return loadPhraseModel;
        }

        public final ModelParameterRange queryModelParameterSupport(int i, int i2) {
            ModelParameterRange queryModelParameterSupport;
            if (i2 != 0) {
                throw new IllegalArgumentException("Invalid model parameter");
            }
            synchronized (SoundTriggerMiddlewareValidation.this) {
                try {
                    if (this.mState == ModuleStatus.DETACHED) {
                        throw new IllegalStateException("Module has been detached.");
                    }
                    ModelState modelState = (ModelState) ((HashMap) this.mLoadedModels).get(Integer.valueOf(i));
                    if (modelState == null) {
                        throw new IllegalStateException("Invalid handle: " + i);
                    }
                    try {
                        queryModelParameterSupport = this.mDelegate.queryModelParameterSupport(i, i2);
                        ((HashMap) modelState.parameterSupport).put(Integer.valueOf(i2), queryModelParameterSupport);
                    } catch (Exception e) {
                        SoundTriggerMiddlewareValidation.handleException(e);
                        throw null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return queryModelParameterSupport;
        }

        public final void setModelParameter(int i, int i2, int i3) {
            if (i2 != 0) {
                throw new IllegalArgumentException("Invalid model parameter");
            }
            synchronized (SoundTriggerMiddlewareValidation.this) {
                try {
                    if (this.mState == ModuleStatus.DETACHED) {
                        throw new IllegalStateException("Module has been detached.");
                    }
                    ModelState modelState = (ModelState) ((HashMap) this.mLoadedModels).get(Integer.valueOf(i));
                    if (modelState == null) {
                        throw new IllegalStateException("Invalid handle: " + i);
                    }
                    modelState.checkSupported(i2, i3);
                    try {
                        this.mDelegate.setModelParameter(i, i2, i3);
                    } catch (Exception e) {
                        SoundTriggerMiddlewareValidation.handleException(e);
                        throw null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final IBinder startRecognition(int i, RecognitionConfig recognitionConfig) {
            IBinder startRecognition;
            Objects.requireNonNull(recognitionConfig);
            Objects.requireNonNull(recognitionConfig.phraseRecognitionExtras);
            for (PhraseRecognitionExtra phraseRecognitionExtra : recognitionConfig.phraseRecognitionExtras) {
                Objects.requireNonNull(phraseRecognitionExtra);
                if ((phraseRecognitionExtra.recognitionModes & (-16)) != 0) {
                    throw new IllegalArgumentException("Invalid recognitionModes");
                }
                int i2 = phraseRecognitionExtra.confidenceLevel;
                if (i2 < 0 || i2 > 100) {
                    throw new IllegalArgumentException("Invalid confidenceLevel");
                }
                Objects.requireNonNull(phraseRecognitionExtra.levels);
                for (ConfidenceLevel confidenceLevel : phraseRecognitionExtra.levels) {
                    Objects.requireNonNull(confidenceLevel);
                    int i3 = confidenceLevel.levelPercent;
                    if (i3 < 0 || i3 > 100) {
                        throw new IllegalArgumentException("Invalid confidenceLevel");
                    }
                }
            }
            Objects.requireNonNull(recognitionConfig.data);
            synchronized (SoundTriggerMiddlewareValidation.this) {
                if (this.mState == ModuleStatus.DETACHED) {
                    throw new IllegalStateException("Module has been detached.");
                }
                ModelState modelState = (ModelState) ((HashMap) this.mLoadedModels).get(Integer.valueOf(i));
                if (modelState == null) {
                    throw new IllegalStateException("Invalid handle: " + i);
                }
                ModelState.Activity activity = modelState.activityState;
                if (activity != ModelState.Activity.LOADED && activity != ModelState.Activity.PREEMPTED) {
                    throw new IllegalStateException("Model with handle: " + i + " has invalid state for starting recognition");
                }
                try {
                    startRecognition = this.mDelegate.startRecognition(i, recognitionConfig);
                    modelState.activityState = ModelState.Activity.ACTIVE;
                } catch (Exception e) {
                    SoundTriggerMiddlewareValidation.handleException(e);
                    throw null;
                }
            }
            return startRecognition;
        }

        public final void stopRecognition(int i) {
            synchronized (SoundTriggerMiddlewareValidation.this) {
                if (this.mState == ModuleStatus.DETACHED) {
                    throw new IllegalStateException("Module has been detached.");
                }
                if (((ModelState) ((HashMap) this.mLoadedModels).get(Integer.valueOf(i))) == null) {
                    throw new IllegalStateException("Invalid handle: " + i);
                }
            }
            try {
                this.mDelegate.stopRecognition(i);
                synchronized (SoundTriggerMiddlewareValidation.this) {
                    try {
                        ModelState modelState = (ModelState) ((HashMap) this.mLoadedModels).get(Integer.valueOf(i));
                        if (modelState == null) {
                            return;
                        }
                        if (modelState.activityState != ModelState.Activity.PREEMPTED) {
                            modelState.activityState = ModelState.Activity.LOADED;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } catch (Exception e) {
                SoundTriggerMiddlewareValidation.handleException(e);
                throw null;
            }
        }

        public final String toString() {
            return Objects.toString(this.mDelegate);
        }

        public final void unloadModel(int i) {
            synchronized (SoundTriggerMiddlewareValidation.this) {
                try {
                    if (this.mState == ModuleStatus.DETACHED) {
                        throw new IllegalStateException("Module has been detached.");
                    }
                    ModelState modelState = (ModelState) ((HashMap) this.mLoadedModels).get(Integer.valueOf(i));
                    if (modelState == null) {
                        throw new IllegalStateException("Invalid handle: " + i);
                    }
                    ModelState.Activity activity = modelState.activityState;
                    if (activity != ModelState.Activity.LOADED && activity != ModelState.Activity.PREEMPTED) {
                        throw new IllegalStateException("Model with handle: " + i + " has invalid state for unloading");
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            try {
                this.mDelegate.unloadModel(i);
                synchronized (SoundTriggerMiddlewareValidation.this) {
                    ((HashMap) this.mLoadedModels).remove(Integer.valueOf(i));
                }
            } catch (Exception e) {
                SoundTriggerMiddlewareValidation.handleException(e);
                throw null;
            }
        }
    }

    public SoundTriggerMiddlewareValidation(SoundTriggerMiddlewareImpl soundTriggerMiddlewareImpl) {
        this.mDelegate = soundTriggerMiddlewareImpl;
    }

    public static void handleException(Exception exc) {
        if (exc instanceof RecoverableException) {
            throw new ServiceSpecificException(((RecoverableException) exc).errorCode, exc.getMessage());
        }
        Slog.wtf("SoundTriggerMiddlewareValidation", "Unexpected exception", exc);
        throw new ServiceSpecificException(5, exc.getMessage());
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal
    public final ISoundTriggerModule attach(int i, ISoundTriggerCallback iSoundTriggerCallback, boolean z) {
        Session session;
        Objects.requireNonNull(iSoundTriggerCallback);
        Objects.requireNonNull(iSoundTriggerCallback.asBinder());
        synchronized (this) {
            try {
                Map map = this.mModules;
                if (map == null) {
                    throw new IllegalStateException("Client must call listModules() prior to attaching.");
                }
                if (!((HashMap) map).containsKey(Integer.valueOf(i))) {
                    throw new IllegalArgumentException("Invalid handle: " + i);
                }
                try {
                    session = new Session(i, iSoundTriggerCallback);
                    session.attach(this.mDelegate.attach(i, session.mCallbackWrapper, z));
                } catch (Exception e) {
                    handleException(e);
                    throw null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return session;
    }

    @Override // com.android.server.soundtrigger_middleware.Dumpable
    public final void dump(PrintWriter printWriter) {
        synchronized (this) {
            try {
                Map map = this.mModules;
                if (map != null) {
                    for (Integer num : ((HashMap) map).keySet()) {
                        num.getClass();
                        ModuleState moduleState = (ModuleState) ((HashMap) this.mModules).get(num);
                        printWriter.println("=========================================");
                        printWriter.printf("Module %d\n%s\n", num, ObjectPrinter.print(moduleState.properties));
                        printWriter.println("=========================================");
                        Iterator it = ((HashSet) moduleState.sessions).iterator();
                        while (it.hasNext()) {
                            ((Session) it.next()).dump(printWriter);
                        }
                    }
                } else {
                    printWriter.println("Modules have not yet been enumerated.");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        printWriter.println();
        ISoundTriggerMiddlewareInternal iSoundTriggerMiddlewareInternal = this.mDelegate;
        if (iSoundTriggerMiddlewareInternal instanceof Dumpable) {
            ((Dumpable) iSoundTriggerMiddlewareInternal).dump(printWriter);
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal
    public final SoundTriggerModuleDescriptor[] listModules() {
        SoundTriggerModuleDescriptor[] listModules;
        synchronized (this) {
            try {
                try {
                    listModules = this.mDelegate.listModules();
                    Map map = this.mModules;
                    int i = 0;
                    if (map == null) {
                        this.mModules = new HashMap(listModules.length);
                        int length = listModules.length;
                        while (i < length) {
                            SoundTriggerModuleDescriptor soundTriggerModuleDescriptor = listModules[i];
                            Map map2 = this.mModules;
                            Integer valueOf = Integer.valueOf(soundTriggerModuleDescriptor.handle);
                            Properties properties = soundTriggerModuleDescriptor.properties;
                            ModuleState moduleState = new ModuleState();
                            moduleState.sessions = new HashSet();
                            moduleState.properties = properties;
                            ((HashMap) map2).put(valueOf, moduleState);
                            i++;
                        }
                    } else {
                        if (listModules.length != ((HashMap) map).size()) {
                            throw new RuntimeException("listModules must always return the same result.");
                        }
                        int length2 = listModules.length;
                        while (i < length2) {
                            SoundTriggerModuleDescriptor soundTriggerModuleDescriptor2 = listModules[i];
                            if (!((HashMap) this.mModules).containsKey(Integer.valueOf(soundTriggerModuleDescriptor2.handle))) {
                                throw new RuntimeException("listModules must always return the same result.");
                            }
                            ((ModuleState) ((HashMap) this.mModules).get(Integer.valueOf(soundTriggerModuleDescriptor2.handle))).properties = soundTriggerModuleDescriptor2.properties;
                            i++;
                        }
                    }
                } catch (Exception e) {
                    handleException(e);
                    throw null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return listModules;
    }

    public final String toString() {
        return this.mDelegate.toString();
    }
}
