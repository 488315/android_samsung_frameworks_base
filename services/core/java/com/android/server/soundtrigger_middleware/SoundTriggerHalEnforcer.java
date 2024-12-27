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
import android.util.Slog;

import java.util.HashMap;
import java.util.Map;

public final class SoundTriggerHalEnforcer implements ISoundTriggerHal {
    public final Map mModelStates = new HashMap();
    public final ISoundTriggerHal mUnderlying;

    public final class ModelCallbackEnforcer implements ISoundTriggerHal.ModelCallback {
        public final ISoundTriggerHal.ModelCallback mUnderlying;

        public ModelCallbackEnforcer(ISoundTriggerHal.ModelCallback modelCallback) {
            this.mUnderlying = modelCallback;
        }

        @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback
        public final void modelUnloaded(int i) {
            synchronized (SoundTriggerHalEnforcer.this.mModelStates) {
                try {
                    ModelState modelState =
                            (ModelState)
                                    ((HashMap) SoundTriggerHalEnforcer.this.mModelStates)
                                            .get(Integer.valueOf(i));
                    if (modelState == null) {
                        Slog.wtfStack(
                                "SoundTriggerHalEnforcer",
                                "Unexpected unload event for model: " + i);
                        SoundTriggerHalEnforcer.this.reboot();
                        return;
                    }
                    if (modelState == ModelState.ACTIVE) {
                        Slog.wtfStack(
                                "SoundTriggerHalEnforcer",
                                "Trying to unload an active model: " + i);
                        SoundTriggerHalEnforcer.this.reboot();
                        return;
                    }
                    ((HashMap) SoundTriggerHalEnforcer.this.mModelStates)
                            .remove(Integer.valueOf(i));
                    this.mUnderlying.modelUnloaded(i);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback
        public final void phraseRecognitionCallback(
                int i, PhraseRecognitionEventSys phraseRecognitionEventSys) {
            int i2;
            synchronized (SoundTriggerHalEnforcer.this.mModelStates) {
                try {
                    if (((ModelState)
                                    ((HashMap) SoundTriggerHalEnforcer.this.mModelStates)
                                            .get(Integer.valueOf(i)))
                            == null) {
                        Slog.wtfStack(
                                "SoundTriggerHalEnforcer",
                                "Unexpected recognition event for model: " + i);
                        SoundTriggerHalEnforcer.this.reboot();
                        return;
                    }
                    RecognitionEvent recognitionEvent =
                            phraseRecognitionEventSys.phraseRecognitionEvent.common;
                    boolean z = recognitionEvent.recognitionStillActive;
                    if (z && (i2 = recognitionEvent.status) != 0 && i2 != 3) {
                        Slog.wtfStack(
                                "SoundTriggerHalEnforcer",
                                "recognitionStillActive is only allowed when the recognition status"
                                    + " is SUCCESS");
                        SoundTriggerHalEnforcer.this.reboot();
                    } else {
                        if (!z) {
                            ((HashMap) SoundTriggerHalEnforcer.this.mModelStates)
                                    .replace(Integer.valueOf(i), ModelState.INACTIVE);
                        }
                        this.mUnderlying.phraseRecognitionCallback(i, phraseRecognitionEventSys);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal.ModelCallback
        public final void recognitionCallback(int i, RecognitionEventSys recognitionEventSys) {
            int i2;
            synchronized (SoundTriggerHalEnforcer.this.mModelStates) {
                try {
                    if (((ModelState)
                                    ((HashMap) SoundTriggerHalEnforcer.this.mModelStates)
                                            .get(Integer.valueOf(i)))
                            == null) {
                        Slog.wtfStack(
                                "SoundTriggerHalEnforcer",
                                "Unexpected recognition event for model: " + i);
                        SoundTriggerHalEnforcer.this.reboot();
                        return;
                    }
                    RecognitionEvent recognitionEvent = recognitionEventSys.recognitionEvent;
                    boolean z = recognitionEvent.recognitionStillActive;
                    if (z && (i2 = recognitionEvent.status) != 0 && i2 != 3) {
                        Slog.wtfStack(
                                "SoundTriggerHalEnforcer",
                                "recognitionStillActive is only allowed when the recognition status"
                                    + " is SUCCESS");
                        SoundTriggerHalEnforcer.this.reboot();
                    } else {
                        if (!z) {
                            ((HashMap) SoundTriggerHalEnforcer.this.mModelStates)
                                    .replace(Integer.valueOf(i), ModelState.INACTIVE);
                        }
                        this.mUnderlying.recognitionCallback(i, recognitionEventSys);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    final class ModelState {
        public static final /* synthetic */ ModelState[] $VALUES;
        public static final ModelState ACTIVE;
        public static final ModelState INACTIVE;
        public static final ModelState PENDING_STOP;

        static {
            ModelState modelState = new ModelState("INACTIVE", 0);
            INACTIVE = modelState;
            ModelState modelState2 = new ModelState("ACTIVE", 1);
            ACTIVE = modelState2;
            ModelState modelState3 = new ModelState("PENDING_STOP", 2);
            PENDING_STOP = modelState3;
            $VALUES = new ModelState[] {modelState, modelState2, modelState3};
        }

        public static ModelState valueOf(String str) {
            return (ModelState) Enum.valueOf(ModelState.class, str);
        }

        public static ModelState[] values() {
            return (ModelState[]) $VALUES.clone();
        }
    }

    public SoundTriggerHalEnforcer(SoundTriggerHalWatchdog soundTriggerHalWatchdog) {
        this.mUnderlying = soundTriggerHalWatchdog;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void clientAttached(IBinder iBinder) {
        this.mUnderlying.clientAttached(iBinder);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void clientDetached(IBinder iBinder) {
        this.mUnderlying.clientDetached(iBinder);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void detach() {
        this.mUnderlying.detach();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void forceRecognitionEvent(int i) {
        try {
            this.mUnderlying.forceRecognitionEvent(i);
        } catch (RuntimeException e) {
            handleException(e);
            throw null;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final int getModelParameter(int i, int i2) {
        try {
            return this.mUnderlying.getModelParameter(i, i2);
        } catch (RuntimeException e) {
            this.handleException(e);
            throw null;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final Properties getProperties() {
        try {
            return this.mUnderlying.getProperties();
        } catch (RuntimeException e) {
            this.handleException(e);
            throw null;
        }
    }

    public final void handleException(RuntimeException runtimeException) {
        if (runtimeException instanceof RecoverableException) {
            throw runtimeException;
        }
        if (runtimeException.getCause() instanceof DeadObjectException) {
            Slog.e("SoundTriggerHalEnforcer", "HAL died");
            throw new RecoverableException(4);
        }
        Slog.e("SoundTriggerHalEnforcer", "Exception caught from HAL, rebooting HAL");
        reboot();
        throw runtimeException;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void linkToDeath(IBinder.DeathRecipient deathRecipient) {
        this.mUnderlying.linkToDeath(deathRecipient);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final int loadPhraseSoundModel(
            PhraseSoundModel phraseSoundModel, ISoundTriggerHal.ModelCallback modelCallback) {
        int loadPhraseSoundModel;
        try {
            synchronized (this.mModelStates) {
                loadPhraseSoundModel =
                        this.mUnderlying.loadPhraseSoundModel(
                                phraseSoundModel, new ModelCallbackEnforcer(modelCallback));
                ((HashMap) this.mModelStates)
                        .put(Integer.valueOf(loadPhraseSoundModel), ModelState.INACTIVE);
            }
            return loadPhraseSoundModel;
        } catch (RuntimeException e) {
            handleException(e);
            throw null;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final int loadSoundModel(
            SoundModel soundModel, ISoundTriggerHal.ModelCallback modelCallback) {
        int loadSoundModel;
        try {
            synchronized (this.mModelStates) {
                loadSoundModel =
                        this.mUnderlying.loadSoundModel(
                                soundModel, new ModelCallbackEnforcer(modelCallback));
                ((HashMap) this.mModelStates)
                        .put(Integer.valueOf(loadSoundModel), ModelState.INACTIVE);
            }
            return loadSoundModel;
        } catch (RuntimeException e) {
            handleException(e);
            throw null;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final ModelParameterRange queryParameter(int i, int i2) {
        try {
            return this.mUnderlying.queryParameter(i, i2);
        } catch (RuntimeException e) {
            this.handleException(e);
            throw null;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void reboot() {
        this.mUnderlying.reboot();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void registerCallback(ISoundTriggerHal.GlobalCallback globalCallback) {
        try {
            this.mUnderlying.registerCallback(globalCallback);
        } catch (RuntimeException e) {
            handleException(e);
            throw null;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void setModelParameter(int i, int i2, int i3) {
        try {
            this.mUnderlying.setModelParameter(i, i2, i3);
        } catch (RuntimeException e) {
            handleException(e);
            throw null;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void startRecognition(int i, int i2, int i3, RecognitionConfig recognitionConfig) {
        try {
            synchronized (this.mModelStates) {
                this.mUnderlying.startRecognition(i, i2, i3, recognitionConfig);
                ((HashMap) this.mModelStates).replace(Integer.valueOf(i), ModelState.ACTIVE);
            }
        } catch (RuntimeException e) {
            handleException(e);
            throw null;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void stopRecognition(int i) {
        try {
            synchronized (this.mModelStates) {
                ((HashMap) this.mModelStates).replace(Integer.valueOf(i), ModelState.PENDING_STOP);
            }
            this.mUnderlying.stopRecognition(i);
            synchronized (this.mModelStates) {
                ((HashMap) this.mModelStates).replace(Integer.valueOf(i), ModelState.INACTIVE);
            }
        } catch (RuntimeException e) {
            handleException(e);
            throw null;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void unloadSoundModel(int i) {
        try {
            this.mUnderlying.unloadSoundModel(i);
            synchronized (this.mModelStates) {
                ((HashMap) this.mModelStates).remove(Integer.valueOf(i));
            }
        } catch (RuntimeException e) {
            handleException(e);
            throw null;
        }
    }
}
