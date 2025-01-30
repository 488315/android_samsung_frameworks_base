package com.android.server.soundtrigger_middleware;

import android.media.soundtrigger.ModelParameterRange;
import android.media.soundtrigger.PhraseSoundModel;
import android.media.soundtrigger.Properties;
import android.media.soundtrigger.RecognitionConfig;
import android.media.soundtrigger.SoundModel;
import android.os.IBinder;
import com.android.server.soundtrigger_middleware.ISoundTriggerHal;
import com.android.server.soundtrigger_middleware.SoundTriggerDuplicateModelHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class SoundTriggerDuplicateModelHandler implements ISoundTriggerHal {
    public final ISoundTriggerHal mDelegate;
    public ISoundTriggerHal.GlobalCallback mGlobalCallback;
    public final List mModelList = new ArrayList();

    public final class ModelData {
        public int mModelId;
        public String mUuid;
        public boolean mWasContended = false;

        public ModelData(int i, String str) {
            this.mModelId = i;
            this.mUuid = str;
        }

        public int getModelId() {
            return this.mModelId;
        }

        public String getUuid() {
            return this.mUuid;
        }

        public boolean getWasContended() {
            return this.mWasContended;
        }

        public void setWasContended() {
            this.mWasContended = true;
        }
    }

    public SoundTriggerDuplicateModelHandler(ISoundTriggerHal iSoundTriggerHal) {
        this.mDelegate = iSoundTriggerHal;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void reboot() {
        this.mDelegate.reboot();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void detach() {
        this.mDelegate.detach();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public Properties getProperties() {
        return this.mDelegate.getProperties();
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void registerCallback(ISoundTriggerHal.GlobalCallback globalCallback) {
        this.mGlobalCallback = globalCallback;
        this.mDelegate.registerCallback(globalCallback);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public int loadSoundModel(SoundModel soundModel, ISoundTriggerHal.ModelCallback modelCallback) {
        int loadSoundModel;
        synchronized (this) {
            checkDuplicateModelUuid(soundModel.uuid);
            loadSoundModel = this.mDelegate.loadSoundModel(soundModel, modelCallback);
            this.mModelList.add(new ModelData(loadSoundModel, soundModel.uuid));
        }
        return loadSoundModel;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public int loadPhraseSoundModel(PhraseSoundModel phraseSoundModel, ISoundTriggerHal.ModelCallback modelCallback) {
        int loadPhraseSoundModel;
        synchronized (this) {
            checkDuplicateModelUuid(phraseSoundModel.common.uuid);
            loadPhraseSoundModel = this.mDelegate.loadPhraseSoundModel(phraseSoundModel, modelCallback);
            this.mModelList.add(new ModelData(loadPhraseSoundModel, phraseSoundModel.common.uuid));
        }
        return loadPhraseSoundModel;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void unloadSoundModel(int i) {
        this.mDelegate.unloadSoundModel(i);
        for (int i2 = 0; i2 < this.mModelList.size(); i2++) {
            if (((ModelData) this.mModelList.get(i2)).getModelId() == i) {
                if (((ModelData) this.mModelList.remove(i2)).getWasContended()) {
                    this.mGlobalCallback.onResourcesAvailable();
                    return;
                }
                return;
            }
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void stopRecognition(int i) {
        this.mDelegate.stopRecognition(i);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void startRecognition(int i, int i2, int i3, RecognitionConfig recognitionConfig) {
        this.mDelegate.startRecognition(i, i2, i3, recognitionConfig);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void forceRecognitionEvent(int i) {
        this.mDelegate.forceRecognitionEvent(i);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public int getModelParameter(int i, int i2) {
        return this.mDelegate.getModelParameter(i, i2);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void setModelParameter(int i, int i2, int i3) {
        this.mDelegate.setModelParameter(i, i2, i3);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public ModelParameterRange queryParameter(int i, int i2) {
        return this.mDelegate.queryParameter(i, i2);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void linkToDeath(IBinder.DeathRecipient deathRecipient) {
        this.mDelegate.linkToDeath(deathRecipient);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void clientAttached(IBinder iBinder) {
        this.mDelegate.clientAttached(iBinder);
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal
    public void clientDetached(IBinder iBinder) {
        this.mDelegate.clientDetached(iBinder);
    }

    public static /* synthetic */ boolean lambda$checkDuplicateModelUuid$0(String str, ModelData modelData) {
        return modelData.getUuid().equals(str);
    }

    public final void checkDuplicateModelUuid(final String str) {
        Optional findFirst = this.mModelList.stream().filter(new Predicate() { // from class: com.android.server.soundtrigger_middleware.SoundTriggerDuplicateModelHandler$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$checkDuplicateModelUuid$0;
                lambda$checkDuplicateModelUuid$0 = SoundTriggerDuplicateModelHandler.lambda$checkDuplicateModelUuid$0(str, (SoundTriggerDuplicateModelHandler.ModelData) obj);
                return lambda$checkDuplicateModelUuid$0;
            }
        }).findFirst();
        if (findFirst.isPresent()) {
            ((ModelData) findFirst.get()).setWasContended();
            throw new RecoverableException(1);
        }
    }
}
