package android.media.audiofx;

import android.util.Log;

/* loaded from: classes2.dex */
public class AutomaticGainControl extends AudioEffect {
    private static final String TAG = "AutomaticGainControl";

    public static boolean isAvailable() {
        return AudioEffect.isEffectTypeAvailable(AudioEffect.EFFECT_TYPE_AGC);
    }

    public static AutomaticGainControl create(int i) {
        try {
            return new AutomaticGainControl(i);
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

    private AutomaticGainControl(int i) throws RuntimeException {
        super(EFFECT_TYPE_AGC, EFFECT_TYPE_NULL, 0, i);
    }
}
