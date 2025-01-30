package android.hardware.soundtrigger;

import android.hardware.soundtrigger.SoundTrigger;
import android.media.permission.Identity;
import android.p009os.Binder;
import android.p009os.Handler;

/* loaded from: classes2.dex */
public class SoundTriggerModuleWrapper {
    private SoundTriggerModule instance;

    public SoundTriggerModuleWrapper(int moduleId, SoundTrigger.StatusListener listener, Handler handler) {
        this.instance = null;
        Identity originatorIdentity = new Identity();
        originatorIdentity.pid = Binder.getCallingPid();
        originatorIdentity.uid = Binder.getCallingUid();
        this.instance = SoundTrigger.attachModuleAsOriginator(moduleId, listener, handler, originatorIdentity);
    }

    public void detach() {
        SoundTriggerModule soundTriggerModule = this.instance;
        if (soundTriggerModule != null) {
            soundTriggerModule.detach();
        }
    }

    public int loadSoundModel(SoundTrigger.KeyphraseSoundModel model, int[] soundModelHandle) {
        SoundTriggerModule soundTriggerModule = this.instance;
        if (soundTriggerModule != null) {
            return soundTriggerModule.loadSoundModel(model, soundModelHandle);
        }
        return Integer.MIN_VALUE;
    }

    public int unloadSoundModel(int soundModelHandle) {
        SoundTriggerModule soundTriggerModule = this.instance;
        if (soundTriggerModule != null) {
            return soundTriggerModule.unloadSoundModel(soundModelHandle);
        }
        return Integer.MIN_VALUE;
    }

    public int startRecognition(int soundModelHandle, SoundTrigger.RecognitionConfig config) {
        SoundTriggerModule soundTriggerModule = this.instance;
        if (soundTriggerModule != null) {
            return soundTriggerModule.startRecognition(soundModelHandle, config);
        }
        return Integer.MIN_VALUE;
    }

    public int stopRecognition(int soundModelHandle) {
        SoundTriggerModule soundTriggerModule = this.instance;
        if (soundTriggerModule != null) {
            return soundTriggerModule.stopRecognition(soundModelHandle);
        }
        return Integer.MIN_VALUE;
    }
}
