package android.hardware.soundtrigger;

import android.hardware.soundtrigger.SoundTrigger;
import android.media.permission.Identity;
import android.os.Binder;
import android.os.Handler;

/* loaded from: classes2.dex */
public class SoundTriggerModuleWrapper {
    private SoundTriggerModule instance;

    public SoundTriggerModuleWrapper(int i, SoundTrigger.StatusListener statusListener, Handler handler) {
        this.instance = null;
        Identity identity = new Identity();
        identity.pid = Binder.getCallingPid();
        identity.uid = Binder.getCallingUid();
        this.instance = SoundTrigger.attachModuleAsOriginator(i, statusListener, handler, identity);
    }

    public void detach() {
        SoundTriggerModule soundTriggerModule = this.instance;
        if (soundTriggerModule != null) {
            soundTriggerModule.detach();
        }
    }

    public int loadSoundModel(SoundTrigger.KeyphraseSoundModel keyphraseSoundModel, int[] iArr) {
        SoundTriggerModule soundTriggerModule = this.instance;
        if (soundTriggerModule != null) {
            return soundTriggerModule.loadSoundModel(keyphraseSoundModel, iArr);
        }
        return Integer.MIN_VALUE;
    }

    public int unloadSoundModel(int i) {
        SoundTriggerModule soundTriggerModule = this.instance;
        if (soundTriggerModule != null) {
            return soundTriggerModule.unloadSoundModel(i);
        }
        return Integer.MIN_VALUE;
    }

    public int startRecognition(int i, SoundTrigger.RecognitionConfig recognitionConfig) {
        SoundTriggerModule soundTriggerModule = this.instance;
        if (soundTriggerModule != null) {
            return soundTriggerModule.startRecognition(i, recognitionConfig);
        }
        return Integer.MIN_VALUE;
    }

    public int stopRecognition(int i) {
        SoundTriggerModule soundTriggerModule = this.instance;
        if (soundTriggerModule != null) {
            return soundTriggerModule.stopRecognition(i);
        }
        return Integer.MIN_VALUE;
    }
}
