package android.hardware.soundtrigger;

import android.media.permission.Identity;
import android.os.Binder;
import android.os.Handler;

public class SoundTriggerModuleWrapper {
    private SoundTriggerModule instance;

    public SoundTriggerModuleWrapper(
            int moduleId, SoundTrigger.StatusListener listener, Handler handler) {
        this.instance = null;
        Identity originatorIdentity = new Identity();
        originatorIdentity.pid = Binder.getCallingPid();
        originatorIdentity.uid = Binder.getCallingUid();
        this.instance =
                SoundTrigger.attachModuleAsOriginator(
                        moduleId, listener, handler, originatorIdentity);
    }

    public void detach() {
        if (this.instance != null) {
            this.instance.detach();
        }
    }

    public int loadSoundModel(SoundTrigger.KeyphraseSoundModel model, int[] soundModelHandle) {
        if (this.instance != null) {
            return this.instance.loadSoundModel(model, soundModelHandle);
        }
        return Integer.MIN_VALUE;
    }

    public int unloadSoundModel(int soundModelHandle) {
        if (this.instance != null) {
            return this.instance.unloadSoundModel(soundModelHandle);
        }
        return Integer.MIN_VALUE;
    }

    public int startRecognition(int soundModelHandle, SoundTrigger.RecognitionConfig config) {
        if (this.instance != null) {
            return this.instance.startRecognition(soundModelHandle, config);
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
