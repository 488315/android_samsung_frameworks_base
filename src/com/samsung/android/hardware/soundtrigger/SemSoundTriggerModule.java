package com.samsung.android.hardware.soundtrigger;

import android.hardware.soundtrigger.SoundTrigger;
import android.hardware.soundtrigger.SoundTriggerModuleWrapper;
import android.os.Handler;
import com.samsung.android.hardware.soundtrigger.SemSoundTrigger;

/* loaded from: classes6.dex */
public class SemSoundTriggerModule {
    private static final int EVENT_RECOGNITION = 1;
    private static final int EVENT_SERVICE_DIED = 2;
    private static final int EVENT_SERVICE_STATE_CHANGE = 4;
    private static final int EVENT_SOUNDMODEL = 3;
    private SoundTriggerModuleWrapper instance;

    SemSoundTriggerModule(int i, final SemSoundTrigger.StatusListener statusListener, Handler handler) {
        this.instance = null;
        this.instance = new SoundTriggerModuleWrapper(i, new SoundTrigger.StatusListener(this) { // from class: com.samsung.android.hardware.soundtrigger.SemSoundTriggerModule.1
            @Override // android.hardware.soundtrigger.SoundTrigger.StatusListener
            public void onModelUnloaded(int i2) {
            }

            @Override // android.hardware.soundtrigger.SoundTrigger.StatusListener
            public void onRecognition(SoundTrigger.RecognitionEvent recognitionEvent) {
                if (recognitionEvent.status == 1) {
                    statusListener.onServiceStateChange(1);
                    return;
                }
                if (recognitionEvent instanceof SoundTrigger.KeyphraseRecognitionEvent) {
                    SoundTrigger.KeyphraseRecognitionExtra[] keyphraseRecognitionExtraArr = ((SoundTrigger.KeyphraseRecognitionEvent) recognitionEvent).keyphraseExtras;
                    SemSoundTrigger.KeyphraseRecognitionExtra[] keyphraseRecognitionExtraArr2 = new SemSoundTrigger.KeyphraseRecognitionExtra[keyphraseRecognitionExtraArr.length];
                    for (int i2 = 0; i2 < keyphraseRecognitionExtraArr.length; i2++) {
                        SemSoundTrigger.ConfidenceLevel[] confidenceLevelArr = new SemSoundTrigger.ConfidenceLevel[keyphraseRecognitionExtraArr[i2].confidenceLevels.length];
                        for (int i3 = 0; i3 < keyphraseRecognitionExtraArr[i2].confidenceLevels.length; i3++) {
                            confidenceLevelArr[i3] = new SemSoundTrigger.ConfidenceLevel(keyphraseRecognitionExtraArr[i2].confidenceLevels[i3].userId, keyphraseRecognitionExtraArr[i2].confidenceLevels[i3].confidenceLevel);
                        }
                        keyphraseRecognitionExtraArr2[i2] = new SemSoundTrigger.KeyphraseRecognitionExtra(keyphraseRecognitionExtraArr[i2].id, keyphraseRecognitionExtraArr[i2].recognitionModes, keyphraseRecognitionExtraArr[i2].coarseConfidenceLevel, confidenceLevelArr);
                    }
                    statusListener.onRecognition(new SemSoundTrigger.KeyphraseRecognitionEvent(recognitionEvent.status, recognitionEvent.soundModelHandle, recognitionEvent.captureAvailable, recognitionEvent.captureSession, recognitionEvent.captureDelayMs, recognitionEvent.capturePreambleMs, recognitionEvent.triggerInData, recognitionEvent.captureFormat, recognitionEvent.data, keyphraseRecognitionExtraArr2));
                    return;
                }
                statusListener.onRecognition(new SemSoundTrigger.RecognitionEvent(recognitionEvent.status, recognitionEvent.soundModelHandle, recognitionEvent.captureAvailable, recognitionEvent.captureSession, recognitionEvent.captureDelayMs, recognitionEvent.capturePreambleMs, recognitionEvent.triggerInData, recognitionEvent.captureFormat, recognitionEvent.data));
            }

            @Override // android.hardware.soundtrigger.SoundTrigger.StatusListener
            public void onResourcesAvailable() {
                statusListener.onServiceStateChange(0);
            }

            @Override // android.hardware.soundtrigger.SoundTrigger.StatusListener
            public void onServiceDied() {
                statusListener.onServiceDied();
            }
        }, handler);
    }

    public void detach() {
        SoundTriggerModuleWrapper soundTriggerModuleWrapper = this.instance;
        if (soundTriggerModuleWrapper != null) {
            soundTriggerModuleWrapper.detach();
        }
    }

    public int loadSoundModel(SemSoundTrigger.KeyphraseSoundModel keyphraseSoundModel, int[] iArr) {
        try {
            return this.instance.loadSoundModel(keyphraseSoundModel.instance, iArr);
        } catch (Exception e) {
            e.printStackTrace();
            return Integer.MIN_VALUE;
        }
    }

    public int unloadSoundModel(int i) {
        SoundTriggerModuleWrapper soundTriggerModuleWrapper = this.instance;
        if (soundTriggerModuleWrapper != null) {
            return soundTriggerModuleWrapper.unloadSoundModel(i);
        }
        return Integer.MIN_VALUE;
    }

    public int startRecognition(int i, SemSoundTrigger.RecognitionConfig recognitionConfig) {
        SoundTriggerModuleWrapper soundTriggerModuleWrapper = this.instance;
        if (soundTriggerModuleWrapper != null) {
            return soundTriggerModuleWrapper.startRecognition(i, recognitionConfig.instance);
        }
        return Integer.MIN_VALUE;
    }

    public int stopRecognition(int i) {
        SoundTriggerModuleWrapper soundTriggerModuleWrapper = this.instance;
        if (soundTriggerModuleWrapper != null) {
            return soundTriggerModuleWrapper.stopRecognition(i);
        }
        return Integer.MIN_VALUE;
    }
}
