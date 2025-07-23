package android.media.audiofx;

import android.util.Log;

/* loaded from: classes2.dex */
public class AcousticEchoCanceler extends AudioEffect {
    private static final String TAG = "AcousticEchoCanceler";

    public static boolean isAvailable() {
        return AudioEffect.isEffectTypeAvailable(AudioEffect.EFFECT_TYPE_AEC);
    }

    public static AcousticEchoCanceler create(int i) {
        try {
            return new AcousticEchoCanceler(i);
        } catch (IllegalArgumentException unused) {
            Log.w(TAG, "not implemented on this devicenull");
            return null;
        } catch (UnsupportedOperationException unused2) {
            Log.w(TAG, "not enough resources");
            return null;
        } catch (RuntimeException unused3) {
            Log.w(TAG, "not enough memory");
            return null;
        }
    }

    private AcousticEchoCanceler(int i) throws IllegalArgumentException, UnsupportedOperationException, RuntimeException {
        super(EFFECT_TYPE_AEC, EFFECT_TYPE_NULL, 0, i);
    }
}
