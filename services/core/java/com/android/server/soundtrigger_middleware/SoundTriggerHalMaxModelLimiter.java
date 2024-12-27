package com.android.server.soundtrigger_middleware;

import android.media.soundtrigger.ModelParameterRange;
import android.media.soundtrigger.PhraseSoundModel;
import android.media.soundtrigger.Properties;
import android.media.soundtrigger.RecognitionConfig;
import android.media.soundtrigger.SoundModel;
import android.os.IBinder;
import com.android.server.soundtrigger_middleware.ISoundTriggerHal;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SoundTriggerHalMaxModelLimiter implements ISoundTriggerHal {
    public final ISoundTriggerHal mDelegate;
    public ISoundTriggerHal.GlobalCallback mGlobalCallback;
    public final int mMaxModels;
    public int mNumLoadedModels = 0;

    public SoundTriggerHalMaxModelLimiter(SoundTriggerHw2Compat soundTriggerHw2Compat, int i) {
        this.mDelegate = soundTriggerHw2Compat;
        this.mMaxModels = i;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void clientAttached(IBinder iBinder) {
        this.mDelegate.clientAttached(iBinder);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void clientDetached(IBinder iBinder) {
        this.mDelegate.clientDetached(iBinder);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void detach() {
        this.mDelegate.detach();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void forceRecognitionEvent(int i) {
        this.mDelegate.forceRecognitionEvent(i);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final int getModelParameter(int i, int i2) {
        return this.mDelegate.getModelParameter(i, i2);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final Properties getProperties() {
        return this.mDelegate.getProperties();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void linkToDeath(IBinder.DeathRecipient deathRecipient) {
        this.mDelegate.linkToDeath(deathRecipient);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final int loadPhraseSoundModel(PhraseSoundModel phraseSoundModel, ISoundTriggerHal.ModelCallback modelCallback) {
        int loadPhraseSoundModel;
        synchronized (this) {
            try {
                if (this.mNumLoadedModels == this.mMaxModels) {
                    throw new RecoverableException(1);
                }
                loadPhraseSoundModel = this.mDelegate.loadPhraseSoundModel(phraseSoundModel, modelCallback);
                this.mNumLoadedModels++;
            } catch (Throwable th) {
                throw th;
            }
        }
        return loadPhraseSoundModel;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final int loadSoundModel(SoundModel soundModel, ISoundTriggerHal.ModelCallback modelCallback) {
        int loadSoundModel;
        synchronized (this) {
            try {
                if (this.mNumLoadedModels == this.mMaxModels) {
                    throw new RecoverableException(1);
                }
                loadSoundModel = this.mDelegate.loadSoundModel(soundModel, modelCallback);
                this.mNumLoadedModels++;
            } catch (Throwable th) {
                throw th;
            }
        }
        return loadSoundModel;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final ModelParameterRange queryParameter(int i, int i2) {
        return this.mDelegate.queryParameter(i, i2);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void reboot() {
        this.mDelegate.reboot();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void registerCallback(ISoundTriggerHal.GlobalCallback globalCallback) {
        this.mGlobalCallback = globalCallback;
        this.mDelegate.registerCallback(globalCallback);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void setModelParameter(int i, int i2, int i3) {
        this.mDelegate.setModelParameter(i, i2, i3);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void startRecognition(int i, int i2, int i3, RecognitionConfig recognitionConfig) {
        this.mDelegate.startRecognition(i, i2, i3, recognitionConfig);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void stopRecognition(int i) {
        this.mDelegate.stopRecognition(i);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final void unloadSoundModel(int i) {
        boolean z;
        synchronized (this) {
            int i2 = this.mNumLoadedModels;
            this.mNumLoadedModels = i2 - 1;
            z = i2 == this.mMaxModels;
        }
        try {
            this.mDelegate.unloadSoundModel(i);
            if (z) {
                this.mGlobalCallback.onResourcesAvailable();
            }
        } catch (Exception e) {
            synchronized (this) {
                this.mNumLoadedModels++;
                throw e;
            }
        }
    }
}
