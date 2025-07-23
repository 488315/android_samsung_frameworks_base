package android.media.audiofx;

import android.util.Log;

/* loaded from: classes2.dex */
public class NoiseSuppressor extends AudioEffect {
    private static final String TAG = "NoiseSuppressor";

    public static boolean isAvailable() {
        return AudioEffect.isEffectTypeAvailable(AudioEffect.EFFECT_TYPE_NS);
    }

    public static NoiseSuppressor create(int i) {
        try {
            return new NoiseSuppressor(i);
        } catch (IllegalArgumentException unused) {
            Log.w(TAG, "not implemented on this device null");
            return null;
        } catch (UnsupportedOperationException unused2) {
            Log.w(TAG, "not enough resources");
            return null;
        } catch (RuntimeException unused3) {
            Log.w(TAG, "not enough memory");
            return null;
        }
    }

    private NoiseSuppressor(int i) throws IllegalArgumentException, UnsupportedOperationException, RuntimeException {
        super(EFFECT_TYPE_NS, EFFECT_TYPE_NULL, 0, i);
    }
}
