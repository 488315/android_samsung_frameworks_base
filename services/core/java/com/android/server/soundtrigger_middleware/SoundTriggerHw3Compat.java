package com.android.server.soundtrigger_middleware;

import android.hardware.soundtrigger3.ISoundTriggerHw;
import android.hardware.soundtrigger3.ISoundTriggerHwCallback;
import android.hardware.soundtrigger3.ISoundTriggerHwGlobalCallback;
import android.media.soundtrigger.ModelParameterRange;
import android.media.soundtrigger.PhraseRecognitionEvent;
import android.media.soundtrigger.PhraseSoundModel;
import android.media.soundtrigger.Properties;
import android.media.soundtrigger.RecognitionConfig;
import android.media.soundtrigger.RecognitionEvent;
import android.media.soundtrigger.SoundModel;
import android.media.soundtrigger_middleware.PhraseRecognitionEventSys;
import android.media.soundtrigger_middleware.RecognitionEventSys;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.os.SystemClock;

import com.android.server.FgThread;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class SoundTriggerHw3Compat implements ISoundTriggerHal {
    public final ISoundTriggerHw mDriver;
    public final Runnable mRebootRunnable;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class GlobalCallbackAdaper extends ISoundTriggerHwGlobalCallback.Stub {
        public final ISoundTriggerHal.GlobalCallback mDelegate;

        public GlobalCallbackAdaper(ISoundTriggerHal.GlobalCallback globalCallback) {
            this.mDelegate = globalCallback;
        }

        public final String getInterfaceHash() {
            return "6b24e60ad261e3ff56106efd86ce6aa7ef5621b0";
        }

        public final int getInterfaceVersion() {
            return 2;
        }

        public final void onResourcesAvailable() {
            HandlerExecutor executor = FgThread.getExecutor();
            ISoundTriggerHal.GlobalCallback globalCallback = this.mDelegate;
            Objects.requireNonNull(globalCallback);
            executor.execute(
                    new SoundTriggerHalConcurrentCaptureHandler$$ExternalSyntheticLambda3(
                            0, globalCallback));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ModelCallbackAdaper extends ISoundTriggerHwCallback.Stub {
        public final ISoundTriggerHal.ModelCallback mDelegate;

        public ModelCallbackAdaper(ISoundTriggerHal.ModelCallback modelCallback) {
            this.mDelegate = modelCallback;
        }

        public final String getInterfaceHash() {
            return "6b24e60ad261e3ff56106efd86ce6aa7ef5621b0";
        }

        public final int getInterfaceVersion() {
            return 2;
        }

        public final void modelUnloaded(int i) {
            this.mDelegate.modelUnloaded(i);
        }

        public final void phraseRecognitionCallback(
                int i, PhraseRecognitionEvent phraseRecognitionEvent) {
            RecognitionEvent recognitionEvent = phraseRecognitionEvent.common;
            recognitionEvent.recognitionStillActive |= recognitionEvent.status == 3;
            PhraseRecognitionEventSys phraseRecognitionEventSys = new PhraseRecognitionEventSys();
            phraseRecognitionEventSys.phraseRecognitionEvent = phraseRecognitionEvent;
            phraseRecognitionEventSys.halEventReceivedMillis = SystemClock.elapsedRealtimeNanos();
            this.mDelegate.phraseRecognitionCallback(i, phraseRecognitionEventSys);
        }

        public final void recognitionCallback(int i, RecognitionEvent recognitionEvent) {
            recognitionEvent.recognitionStillActive |= recognitionEvent.status == 3;
            RecognitionEventSys recognitionEventSys = new RecognitionEventSys();
            recognitionEventSys.recognitionEvent = recognitionEvent;
            recognitionEventSys.halEventReceivedMillis = SystemClock.elapsedRealtimeNanos();
            this.mDelegate.recognitionCallback(i, recognitionEventSys);
        }
    }

    public SoundTriggerHw3Compat(IBinder iBinder, Runnable runnable) {
        this.mDriver = ISoundTriggerHw.Stub.asInterface(iBinder);
        this.mRebootRunnable = runnable;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void clientAttached(IBinder iBinder) {}

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void clientDetached(IBinder iBinder) {}

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void detach() {}

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void forceRecognitionEvent(int i) {
        try {
            this.mDriver.forceRecognitionEvent(i);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final int getModelParameter(int i, int i2) {
        try {
            return this.mDriver.getParameter(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final Properties getProperties() {
        try {
            return this.mDriver.getProperties();
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void linkToDeath(IBinder.DeathRecipient deathRecipient) {
        try {
            this.mDriver.asBinder().linkToDeath(deathRecipient, 0);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final int loadPhraseSoundModel(
            PhraseSoundModel phraseSoundModel, ISoundTriggerHal.ModelCallback modelCallback) {
        try {
            return this.mDriver.loadPhraseSoundModel(
                    phraseSoundModel, new ModelCallbackAdaper(modelCallback));
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 1) {
                throw new RecoverableException(1);
            }
            throw e2;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final int loadSoundModel(
            SoundModel soundModel, ISoundTriggerHal.ModelCallback modelCallback) {
        try {
            return this.mDriver.loadSoundModel(soundModel, new ModelCallbackAdaper(modelCallback));
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (ServiceSpecificException e2) {
            if (e2.errorCode == 1) {
                throw new RecoverableException(1);
            }
            throw e2;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final ModelParameterRange queryParameter(int i, int i2) {
        try {
            return this.mDriver.queryParameter(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void reboot() {
        this.mRebootRunnable.run();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void registerCallback(ISoundTriggerHal.GlobalCallback globalCallback) {
        try {
            this.mDriver.registerGlobalCallback(new GlobalCallbackAdaper(globalCallback));
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void setModelParameter(int i, int i2, int i3) {
        try {
            this.mDriver.setParameter(i, i2, i3);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void startRecognition(int i, int i2, int i3, RecognitionConfig recognitionConfig) {
        try {
            this.mDriver.startRecognition(i, i2, i3, recognitionConfig);
        } catch (ServiceSpecificException e) {
            if (e.errorCode != 1) {
                throw e;
            }
            throw new RecoverableException(1);
        } catch (RemoteException e2) {
            throw e2.rethrowAsRuntimeException();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void stopRecognition(int i) {
        try {
            this.mDriver.stopRecognition(i);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void unloadSoundModel(int i) {
        try {
            this.mDriver.unloadSoundModel(i);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }
}
