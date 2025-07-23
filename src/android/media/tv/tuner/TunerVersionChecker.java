package android.media.tv.tuner;

import android.annotation.SystemApi;
import android.media.MediaMetrics;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public final class TunerVersionChecker {
    private static final String TAG = "TunerVersionChecker";
    public static final int TUNER_VERSION_1_0 = 65536;
    public static final int TUNER_VERSION_1_1 = 65537;
    public static final int TUNER_VERSION_2_0 = 131072;
    public static final int TUNER_VERSION_3_0 = 196608;
    public static final int TUNER_VERSION_4_0 = 262144;
    public static final int TUNER_VERSION_UNKNOWN = 0;

    @Retention(RetentionPolicy.SOURCE)
    public @interface TunerVersion {
    }

    public static int getMajorVersion(int i) {
        return (i & (-65536)) >>> 16;
    }

    public static int getMinorVersion(int i) {
        return i & 65535;
    }

    private TunerVersionChecker() {
    }

    public static int getTunerVersion() {
        return Tuner.getTunerVersion();
    }

    public static boolean supportTunerVersion(int i) {
        return isHigherOrEqualVersionTo(i) && getMajorVersion(i) == getMajorVersion(Tuner.getTunerVersion());
    }

    public static boolean isHigherOrEqualVersionTo(int i) {
        return Tuner.getTunerVersion() >= i;
    }

    public static boolean checkHigherOrEqualVersionTo(int i, String str) {
        if (isHigherOrEqualVersionTo(i)) {
            return true;
        }
        Log.e(TAG, "Current Tuner version " + getMajorVersion(Tuner.getTunerVersion()) + MediaMetrics.SEPARATOR + getMinorVersion(Tuner.getTunerVersion()) + " does not support " + str + MediaMetrics.SEPARATOR);
        return false;
    }

    public static boolean checkSupportVersion(int i, String str) {
        if (supportTunerVersion(i)) {
            return true;
        }
        Log.e(TAG, "Current Tuner version " + getMajorVersion(Tuner.getTunerVersion()) + MediaMetrics.SEPARATOR + getMinorVersion(Tuner.getTunerVersion()) + " does not support " + str + MediaMetrics.SEPARATOR);
        return false;
    }
}
