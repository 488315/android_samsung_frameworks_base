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

    SemSoundTriggerModule(int moduleId, final SemSoundTrigger.StatusListener listener, Handler handler) {
        this.instance = null;
        SoundTrigger.StatusListener stListener = new SoundTrigger.StatusListener() { // from class: com.samsung.android.hardware.soundtrigger.SemSoundTriggerModule.1
            @Override // android.hardware.soundtrigger.SoundTrigger.StatusListener
            public void onRecognition(SoundTrigger.RecognitionEvent recognitionEvent) {
                if (recognitionEvent.status == 1) {
                    listener.onServiceStateChange(1);
                    return;
                }
                if (recognitionEvent instanceof SoundTrigger.KeyphraseRecognitionEvent) {
                    SoundTrigger.KeyphraseRecognitionExtra[] stKeyphraseExtras = ((SoundTrigger.KeyphraseRecognitionEvent) recognitionEvent).keyphraseExtras;
                    SemSoundTrigger.KeyphraseRecognitionExtra[] KeyphraseExtras = new SemSoundTrigger.KeyphraseRecognitionExtra[stKeyphraseExtras.length];
                    for (int i = 0; i < stKeyphraseExtras.length; i++) {
                        SemSoundTrigger.ConfidenceLevel[] confidenceLevels = new SemSoundTrigger.ConfidenceLevel[stKeyphraseExtras[i].confidenceLevels.length];
                        for (int j = 0; j < stKeyphraseExtras[i].confidenceLevels.length; j++) {
                            confidenceLevels[j] = new SemSoundTrigger.ConfidenceLevel(stKeyphraseExtras[i].confidenceLevels[j].userId, stKeyphraseExtras[i].confidenceLevels[j].confidenceLevel);
                        }
                        KeyphraseExtras[i] = new SemSoundTrigger.KeyphraseRecognitionExtra(stKeyphraseExtras[i].id, stKeyphraseExtras[i].recognitionModes, stKeyphraseExtras[i].coarseConfidenceLevel, confidenceLevels);
                    }
                    listener.onRecognition(new SemSoundTrigger.KeyphraseRecognitionEvent(recognitionEvent.status, recognitionEvent.soundModelHandle, recognitionEvent.captureAvailable, recognitionEvent.captureSession, recognitionEvent.captureDelayMs, recognitionEvent.capturePreambleMs, recognitionEvent.triggerInData, recognitionEvent.captureFormat, recognitionEvent.data, KeyphraseExtras));
                    return;
                }
                listener.onRecognition(new SemSoundTrigger.RecognitionEvent(recognitionEvent.status, recognitionEvent.soundModelHandle, recognitionEvent.captureAvailable, recognitionEvent.captureSession, recognitionEvent.captureDelayMs, recognitionEvent.capturePreambleMs, recognitionEvent.triggerInData, recognitionEvent.captureFormat, recognitionEvent.data));
            }

            @Override // android.hardware.soundtrigger.SoundTrigger.StatusListener
            public void onModelUnloaded(int modelHandle) {
            }

            @Override // android.hardware.soundtrigger.SoundTrigger.StatusListener
            public void onResourcesAvailable() {
                listener.onServiceStateChange(0);
            }

            @Override // android.hardware.soundtrigger.SoundTrigger.StatusListener
            public void onServiceDied() {
                listener.onServiceDied();
            }
        };
        this.instance = new SoundTriggerModuleWrapper(moduleId, stListener, handler);
    }

    public void detach() {
        if (this.instance != null) {
            this.instance.detach();
        }
    }

    public int loadSoundModel(SemSoundTrigger.KeyphraseSoundModel model, int[] soundModelHandle) {
        try {
            return this.instance.loadSoundModel(model.instance, soundModelHandle);
        } catch (Exception e) {
            e.printStackTrace();
            return Integer.MIN_VALUE;
        }
    }

    public int unloadSoundModel(int soundModelHandle) {
        if (this.instance != null) {
            return this.instance.unloadSoundModel(soundModelHandle);
        }
        return Integer.MIN_VALUE;
    }

    public int startRecognition(int soundModelHandle, SemSoundTrigger.RecognitionConfig config) {
        if (this.instance != null) {
            return this.instance.startRecognition(soundModelHandle, config.instance);
        }
        return Integer.MIN_VALUE;
    }

    public int stopRecognition(int soundModelHandle) {
        if (this.instance != null) {
            return this.instance.stopRecognition(soundModelHandle);
        }
        return Integer.MIN_VALUE;
    }
}
