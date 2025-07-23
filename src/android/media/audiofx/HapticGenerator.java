package android.media.audiofx;

import android.media.AudioManager;
import android.util.Log;
import java.util.UUID;

/* loaded from: classes2.dex */
public class HapticGenerator extends AudioEffect implements AutoCloseable {
    private static final String TAG = "HapticGenerator";
    private AudioEffect mVolumeControlEffect;

    public static boolean isAvailable() {
        return AudioManager.isHapticPlaybackSupported() && AudioEffect.isEffectTypeAvailable(AudioEffect.EFFECT_TYPE_HAPTIC_GENERATOR);
    }

    public static HapticGenerator create(int i) {
        return new HapticGenerator(i);
    }

    private HapticGenerator(int i) {
        super(EFFECT_TYPE_HAPTIC_GENERATOR, EFFECT_TYPE_NULL, 0, i);
        this.mVolumeControlEffect = new AudioEffect(AudioEffect.EFFECT_TYPE_NULL, UUID.fromString("119341a0-8469-11df-81f9-0002a5d5c51b"), 0, i);
    }

    @Override // android.media.audiofx.AudioEffect
    public int setEnabled(boolean z) {
        AudioEffect audioEffect;
        int enabled = super.setEnabled(z);
        if (enabled == 0 && ((audioEffect = this.mVolumeControlEffect) == null || audioEffect.setEnabled(z) != 0)) {
            Log.w(TAG, "Failed to enable volume control effect for HapticGenerator");
        }
        return enabled;
    }

    @Override // android.media.audiofx.AudioEffect
    public void release() {
        AudioEffect audioEffect = this.mVolumeControlEffect;
        if (audioEffect != null) {
            audioEffect.release();
        }
        super.release();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        release();
    }
}
