package android.media.audiofx;

import android.media.AudioManager;
import java.util.UUID;

/* loaded from: classes2.dex */
public class SemHapticGenerator extends AudioEffect {
    public static final UUID EFFECT_TYPE_SEC_HAPTIC_GENERATOR = UUID.fromString("2d43d8b6-1861-43d9-8eda-11f63bef2233");
    private static final String TAG = "SemHapticGenerator";

    public static boolean isAvailable() {
        return AudioManager.isHapticPlaybackSupported() && AudioEffect.isEffectTypeAvailable(EFFECT_TYPE_SEC_HAPTIC_GENERATOR);
    }

    public static SemHapticGenerator create(int i) {
        return new SemHapticGenerator(i);
    }

    private SemHapticGenerator(int i) {
        super(EFFECT_TYPE_SEC_HAPTIC_GENERATOR, EFFECT_TYPE_NULL, 0, i);
    }

    @Override // android.media.audiofx.AudioEffect
    public int setEnabled(boolean z) {
        return super.setEnabled(z);
    }
}
