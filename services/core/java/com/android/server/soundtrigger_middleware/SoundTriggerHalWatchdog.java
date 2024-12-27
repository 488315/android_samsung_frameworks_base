package com.android.server.soundtrigger_middleware;

import android.media.soundtrigger.ModelParameterRange;
import android.media.soundtrigger.PhraseSoundModel;
import android.media.soundtrigger.Properties;
import android.media.soundtrigger.RecognitionConfig;
import android.media.soundtrigger.SoundModel;
import android.os.Handler;
import android.os.IBinder;
import android.util.Slog;
import com.android.server.soundtrigger_middleware.ISoundTriggerHal;
import com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog;
import com.android.server.soundtrigger_middleware.UptimeTimer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SoundTriggerHalWatchdog implements ISoundTriggerHal {
    public final UptimeTimer mTimer = new UptimeTimer();
    public final ISoundTriggerHal mUnderlying;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Watchdog implements AutoCloseable {
        public final Exception mException = new Exception();
        public final UptimeTimer.TaskImpl mTask;

        public Watchdog() {
            UptimeTimer uptimeTimer = SoundTriggerHalWatchdog.this.mTimer;
            Runnable runnable = new Runnable() { // from class: com.android.server.soundtrigger_middleware.SoundTriggerHalWatchdog$Watchdog$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SoundTriggerHalWatchdog.Watchdog watchdog = SoundTriggerHalWatchdog.Watchdog.this;
                    Slog.e("SoundTriggerHalWatchdog", "HAL deadline expired. Rebooting.", watchdog.mException);
                    SoundTriggerHalWatchdog.this.reboot();
                }
            };
            Object obj = new Object();
            Handler handler = uptimeTimer.mHandler;
            UptimeTimer.TaskImpl taskImpl = new UptimeTimer.TaskImpl(handler, obj);
            handler.postDelayed(runnable, obj, 3000L);
            this.mTask = taskImpl;
        }

        @Override // java.lang.AutoCloseable
        public final void close() {
            UptimeTimer.TaskImpl taskImpl = this.mTask;
            taskImpl.mHandler.removeCallbacksAndMessages(taskImpl.mToken);
        }
    }

    public SoundTriggerHalWatchdog(SoundTriggerDuplicateModelHandler soundTriggerDuplicateModelHandler) {
        this.mUnderlying = soundTriggerDuplicateModelHandler;
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
        this.mTimer.mHandlerThread.quitSafely();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void forceRecognitionEvent(int i) {
        Watchdog watchdog = new Watchdog();
        try {
            this.mUnderlying.forceRecognitionEvent(i);
            watchdog.close();
        } catch (Throwable th) {
            try {
                watchdog.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final int getModelParameter(int i, int i2) {
        Watchdog watchdog = new Watchdog();
        try {
            int modelParameter = this.mUnderlying.getModelParameter(i, i2);
            watchdog.close();
            return modelParameter;
        } catch (Throwable th) {
            try {
                watchdog.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final Properties getProperties() {
        Watchdog watchdog = new Watchdog();
        try {
            Properties properties = this.mUnderlying.getProperties();
            watchdog.close();
            return properties;
        } catch (Throwable th) {
            try {
                watchdog.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void linkToDeath(IBinder.DeathRecipient deathRecipient) {
        this.mUnderlying.linkToDeath(deathRecipient);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final int loadPhraseSoundModel(PhraseSoundModel phraseSoundModel, ISoundTriggerHal.ModelCallback modelCallback) {
        Watchdog watchdog = new Watchdog();
        try {
            int loadPhraseSoundModel = this.mUnderlying.loadPhraseSoundModel(phraseSoundModel, modelCallback);
            watchdog.close();
            return loadPhraseSoundModel;
        } catch (Throwable th) {
            try {
                watchdog.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final int loadSoundModel(SoundModel soundModel, ISoundTriggerHal.ModelCallback modelCallback) {
        Watchdog watchdog = new Watchdog();
        try {
            int loadSoundModel = this.mUnderlying.loadSoundModel(soundModel, modelCallback);
            watchdog.close();
            return loadSoundModel;
        } catch (Throwable th) {
            try {
                watchdog.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final ModelParameterRange queryParameter(int i, int i2) {
        Watchdog watchdog = new Watchdog();
        try {
            ModelParameterRange queryParameter = this.mUnderlying.queryParameter(i, i2);
            watchdog.close();
            return queryParameter;
        } catch (Throwable th) {
            try {
                watchdog.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void reboot() {
        this.mUnderlying.reboot();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void registerCallback(ISoundTriggerHal.GlobalCallback globalCallback) {
        Watchdog watchdog = new Watchdog();
        try {
            this.mUnderlying.registerCallback(globalCallback);
            watchdog.close();
        } catch (Throwable th) {
            try {
                watchdog.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void setModelParameter(int i, int i2, int i3) {
        Watchdog watchdog = new Watchdog();
        try {
            this.mUnderlying.setModelParameter(i, i2, i3);
            watchdog.close();
        } catch (Throwable th) {
            try {
                watchdog.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void startRecognition(int i, int i2, int i3, RecognitionConfig recognitionConfig) {
        Watchdog watchdog = new Watchdog();
        try {
            this.mUnderlying.startRecognition(i, i2, i3, recognitionConfig);
            watchdog.close();
        } catch (Throwable th) {
            try {
                watchdog.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void stopRecognition(int i) {
        Watchdog watchdog = new Watchdog();
        try {
            this.mUnderlying.stopRecognition(i);
            watchdog.close();
        } catch (Throwable th) {
            try {
                watchdog.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void unloadSoundModel(int i) {
        Watchdog watchdog = new Watchdog();
        try {
            this.mUnderlying.unloadSoundModel(i);
            watchdog.close();
        } catch (Throwable th) {
            try {
                watchdog.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
