package android.media.audiofx;

import android.media.AudioManager;
import android.util.Log;

import java.util.UUID;

public class HapticGenerator extends AudioEffect implements AutoCloseable {
    private static final String TAG = "HapticGenerator";
    private AudioEffect mVolumeControlEffect;

    public static boolean isAvailable() {
        return AudioManager.isHapticPlaybackSupported()
                && AudioEffect.isEffectTypeAvailable(AudioEffect.EFFECT_TYPE_HAPTIC_GENERATOR);
    }

    public static HapticGenerator create(int audioSession) {
        return new HapticGenerator(audioSession);
    }

    private HapticGenerator(int audioSession) {
        super(EFFECT_TYPE_HAPTIC_GENERATOR, EFFECT_TYPE_NULL, 0, audioSession);
        this.mVolumeControlEffect =
                new AudioEffect(
                        AudioEffect.EFFECT_TYPE_NULL,
                        UUID.fromString("119341a0-8469-11df-81f9-0002a5d5c51b"),
                        0,
                        audioSession);
    }

    @Override // android.media.audiofx.AudioEffect
    public int setEnabled(boolean enabled) {
        int ret = super.setEnabled(enabled);
        if (ret == 0
                && (this.mVolumeControlEffect == null
                        || this.mVolumeControlEffect.setEnabled(enabled) != 0)) {
            Log.w(TAG, "Failed to enable volume control effect for HapticGenerator");
        }
        return ret;
    }

    @Override // android.media.audiofx.AudioEffect
    public void release() {
        if (this.mVolumeControlEffect != null) {
            this.mVolumeControlEffect.release();
        }
        super.release();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        release();
    }
}
