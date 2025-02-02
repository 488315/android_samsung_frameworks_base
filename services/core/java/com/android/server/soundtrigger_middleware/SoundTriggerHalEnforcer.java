package com.android.server.soundtrigger_middleware;

import android.media.soundtrigger.ModelParameterRange;
import android.media.soundtrigger.PhraseSoundModel;
import android.media.soundtrigger.Properties;
import android.media.soundtrigger.RecognitionConfig;
import android.media.soundtrigger.RecognitionEvent;
import android.media.soundtrigger.SoundModel;
import android.media.soundtrigger_middleware.PhraseRecognitionEventSys;
import android.media.soundtrigger_middleware.RecognitionEventSys;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class SoundTriggerHalEnforcer implements ISoundTriggerHal {
  public final Map mModelStates = new HashMap();
  public final ISoundTriggerHal mUnderlying;

  enum ModelState {
    INACTIVE,
    ACTIVE,
    PENDING_STOP
  }

  public SoundTriggerHalEnforcer(ISoundTriggerHal iSoundTriggerHal) {
    this.mUnderlying = iSoundTriggerHal;
  }

  @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
  public Properties getProperties() {
    try {
      return this.mUnderlying.getProperties();
    } catch (RuntimeException e) {
      throw this.handleException(e);
    }
  }

  @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
  public void registerCallback(ISoundTriggerHal.GlobalCallback globalCallback) {
    try {
      this.mUnderlying.registerCallback(globalCallback);
    } catch (RuntimeException e) {
      throw handleException(e);
    }
  }

  @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
  public int loadSoundModel(SoundModel soundModel, ISoundTriggerHal.ModelCallback modelCallback) {
    int loadSoundModel;
    try {
      synchronized (this.mModelStates) {
        loadSoundModel =
            this.mUnderlying.loadSoundModel(soundModel, new ModelCallbackEnforcer(modelCallback));
        this.mModelStates.put(Integer.valueOf(loadSoundModel), ModelState.INACTIVE);
      }
      return loadSoundModel;
    } catch (RuntimeException e) {
      throw handleException(e);
    }
  }

  @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
  public int loadPhraseSoundModel(
      PhraseSoundModel phraseSoundModel, ISoundTriggerHal.ModelCallback modelCallback) {
    int loadPhraseSoundModel;
    try {
      synchronized (this.mModelStates) {
        loadPhraseSoundModel =
            this.mUnderlying.loadPhraseSoundModel(
                phraseSoundModel, new ModelCallbackEnforcer(modelCallback));
        this.mModelStates.put(Integer.valueOf(loadPhraseSoundModel), ModelState.INACTIVE);
      }
      return loadPhraseSoundModel;
    } catch (RuntimeException e) {
      throw handleException(e);
    }
  }

  @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
  public void unloadSoundModel(int i) {
    try {
      this.mUnderlying.unloadSoundModel(i);
      synchronized (this.mModelStates) {
        this.mModelStates.remove(Integer.valueOf(i));
      }
    } catch (RuntimeException e) {
      throw handleException(e);
    }
  }

  @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
  public void stopRecognition(int i) {
    try {
      synchronized (this.mModelStates) {
        this.mModelStates.replace(Integer.valueOf(i), ModelState.PENDING_STOP);
      }
      this.mUnderlying.stopRecognition(i);
      synchronized (this.mModelStates) {
        this.mModelStates.replace(Integer.valueOf(i), ModelState.INACTIVE);
      }
    } catch (RuntimeException e) {
      throw handleException(e);
    }
  }

  @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
  public void startRecognition(int i, int i2, int i3, RecognitionConfig recognitionConfig) {
    try {
      synchronized (this.mModelStates) {
        this.mUnderlying.startRecognition(i, i2, i3, recognitionConfig);
        this.mModelStates.replace(Integer.valueOf(i), ModelState.ACTIVE);
      }
    } catch (RuntimeException e) {
      throw handleException(e);
    }
  }

  @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
  public void forceRecognitionEvent(int i) {
    try {
      this.mUnderlying.forceRecognitionEvent(i);
    } catch (RuntimeException e) {
      throw handleException(e);
    }
  }

  @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
  public int getModelParameter(int i, int i2) {
    try {
      return this.mUnderlying.getModelParameter(i, i2);
    } catch (RuntimeException e) {
      throw this.handleException(e);
    }
  }

  @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
  public void setModelParameter(int i, int i2, int i3) {
    try {
      this.mUnderlying.setModelParameter(i, i2, i3);
    } catch (RuntimeException e) {
      throw handleException(e);
    }
  }

  @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
  public ModelParameterRange queryParameter(int i, int i2) {
    try {
      return this.mUnderlying.queryParameter(i, i2);
    } catch (RuntimeException e) {
      throw this.handleException(e);
    }
  }

  @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
  public void linkToDeath(IBinder.DeathRecipient deathRecipient) {
    this.mUnderlying.linkToDeath(deathRecipient);
  }

  @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
  public void clientAttached(IBinder iBinder) {
    this.mUnderlying.clientAttached(iBinder);
  }

  @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
  public void clientDetached(IBinder iBinder) {
    this.mUnderlying.clientDetached(iBinder);
  }

  public final RuntimeException handleException(RuntimeException runtimeException) {
    if (runtimeException instanceof RecoverableException) {
      throw runtimeException;
    }
    if (runtimeException.getCause() instanceof DeadObjectException) {
      Log.e("SoundTriggerHalEnforcer", "HAL died");
      throw new RecoverableException(4);
    }
    Log.e("SoundTriggerHalEnforcer", "Exception caught from HAL, rebooting HAL");
    reboot();
    throw runtimeException;
  }

  @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
  public void reboot() {
    this.mUnderlying.reboot();
  }

  @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
  public void detach() {
    this.mUnderlying.detach();
  }

  public class ModelCallbackEnforcer implements ISoundTriggerHal.ModelCallback {
    public final ISoundTriggerHal.ModelCallback mUnderlying;

    public ModelCallbackEnforcer(ISoundTriggerHal.ModelCallback modelCallback) {
      this.mUnderlying = modelCallback;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback
    public void recognitionCallback(int i, RecognitionEventSys recognitionEventSys) {
      int i2;
      synchronized (SoundTriggerHalEnforcer.this.mModelStates) {
        if (((ModelState) SoundTriggerHalEnforcer.this.mModelStates.get(Integer.valueOf(i)))
            == null) {
          Log.wtfStack("SoundTriggerHalEnforcer", "Unexpected recognition event for model: " + i);
          SoundTriggerHalEnforcer.this.reboot();
          return;
        }
        RecognitionEvent recognitionEvent = recognitionEventSys.recognitionEvent;
        boolean z = recognitionEvent.recognitionStillActive;
        if (z && (i2 = recognitionEvent.status) != 0 && i2 != 3) {
          Log.wtfStack(
              "SoundTriggerHalEnforcer",
              "recognitionStillActive is only allowed when the recognition status is SUCCESS");
          SoundTriggerHalEnforcer.this.reboot();
        } else {
          if (!z) {
            SoundTriggerHalEnforcer.this.mModelStates.replace(
                Integer.valueOf(i), ModelState.INACTIVE);
          }
          this.mUnderlying.recognitionCallback(i, recognitionEventSys);
        }
      }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback
    public void phraseRecognitionCallback(
        int i, PhraseRecognitionEventSys phraseRecognitionEventSys) {
      int i2;
      synchronized (SoundTriggerHalEnforcer.this.mModelStates) {
        if (((ModelState) SoundTriggerHalEnforcer.this.mModelStates.get(Integer.valueOf(i)))
            == null) {
          Log.wtfStack("SoundTriggerHalEnforcer", "Unexpected recognition event for model: " + i);
          SoundTriggerHalEnforcer.this.reboot();
          return;
        }
        RecognitionEvent recognitionEvent = phraseRecognitionEventSys.phraseRecognitionEvent.common;
        boolean z = recognitionEvent.recognitionStillActive;
        if (z && (i2 = recognitionEvent.status) != 0 && i2 != 3) {
          Log.wtfStack(
              "SoundTriggerHalEnforcer",
              "recognitionStillActive is only allowed when the recognition status is SUCCESS");
          SoundTriggerHalEnforcer.this.reboot();
        } else {
          if (!z) {
            SoundTriggerHalEnforcer.this.mModelStates.replace(
                Integer.valueOf(i), ModelState.INACTIVE);
          }
          this.mUnderlying.phraseRecognitionCallback(i, phraseRecognitionEventSys);
        }
      }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback
    public void modelUnloaded(int i) {
      synchronized (SoundTriggerHalEnforcer.this.mModelStates) {
        ModelState modelState =
            (ModelState) SoundTriggerHalEnforcer.this.mModelStates.get(Integer.valueOf(i));
        if (modelState == null) {
          Log.wtfStack("SoundTriggerHalEnforcer", "Unexpected unload event for model: " + i);
          SoundTriggerHalEnforcer.this.reboot();
          return;
        }
        if (modelState == ModelState.ACTIVE) {
          Log.wtfStack("SoundTriggerHalEnforcer", "Trying to unload an active model: " + i);
          SoundTriggerHalEnforcer.this.reboot();
          return;
        }
        SoundTriggerHalEnforcer.this.mModelStates.remove(Integer.valueOf(i));
        this.mUnderlying.modelUnloaded(i);
      }
    }
  }
}
