package com.android.server.soundtrigger_middleware;

import android.media.soundtrigger.ModelParameterRange;
import android.media.soundtrigger.PhraseSoundModel;
import android.media.soundtrigger.Properties;
import android.media.soundtrigger.RecognitionConfig;
import android.media.soundtrigger.SoundModel;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SoundTriggerDuplicateModelHandler implements ISoundTriggerHal {
    public final ISoundTriggerHal mDelegate;
    public ISoundTriggerHal.GlobalCallback mGlobalCallback;
    public final List mModelList = new ArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ModelData {
        public final int mModelId;
        public final String mUuid;
        public boolean mWasContended = false;

        public ModelData(int i, String str) {
            this.mModelId = i;
            this.mUuid = str;
        }
    }

    public SoundTriggerDuplicateModelHandler(ISoundTriggerHal iSoundTriggerHal) {
        this.mDelegate = iSoundTriggerHal;
    }

    public final void checkDuplicateModelUuid(final String str) {
        Optional findFirst =
                this.mModelList.stream()
                        .filter(
                                new Predicate() { // from class:
                                                  // com.android.server.soundtrigger_middleware.SoundTriggerDuplicateModelHandler$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        return ((SoundTriggerDuplicateModelHandler.ModelData) obj)
                                                .mUuid.equals(str);
                                    }
                                })
                        .findFirst();
        if (findFirst.isPresent()) {
            ((ModelData) findFirst.get()).mWasContended = true;
            throw new RecoverableException(1);
        }
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
    public final int loadPhraseSoundModel(
            PhraseSoundModel phraseSoundModel, ISoundTriggerHal.ModelCallback modelCallback) {
        int loadPhraseSoundModel;
        synchronized (this) {
            checkDuplicateModelUuid(phraseSoundModel.common.uuid);
            loadPhraseSoundModel =
                    this.mDelegate.loadPhraseSoundModel(phraseSoundModel, modelCallback);
            ((ArrayList) this.mModelList)
                    .add(new ModelData(loadPhraseSoundModel, phraseSoundModel.common.uuid));
        }
        return loadPhraseSoundModel;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public final int loadSoundModel(
            SoundModel soundModel, ISoundTriggerHal.ModelCallback modelCallback) {
        int loadSoundModel;
        synchronized (this) {
            checkDuplicateModelUuid(soundModel.uuid);
            loadSoundModel = this.mDelegate.loadSoundModel(soundModel, modelCallback);
            ((ArrayList) this.mModelList).add(new ModelData(loadSoundModel, soundModel.uuid));
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
        this.mDelegate.unloadSoundModel(i);
        for (int i2 = 0; i2 < ((ArrayList) this.mModelList).size(); i2++) {
            if (((ModelData) ((ArrayList) this.mModelList).get(i2)).mModelId == i) {
                if (((ModelData) ((ArrayList) this.mModelList).remove(i2)).mWasContended) {
                    this.mGlobalCallback.onResourcesAvailable();
                    return;
                }
                return;
            }
        }
    }
}
