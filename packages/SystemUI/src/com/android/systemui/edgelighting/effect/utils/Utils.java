package com.android.systemui.edgelighting.effect.utils;

import android.os.Build;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import android.util.Slog;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.view.SemWindowManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class Utils {
    public static String MODEL_NAME = SemSystemProperties.get("ro.product.model");
    public static final String MODEL_NAME_FOR_JPN = SemSystemProperties.get("ro.factory.model");
    public static final int MODEL_DEFAULT_DENSITY = SemSystemProperties.getInt("ro.sf.lcd_density", -1);
    public static final String TAG = "Utils";

    public static void getRadiusController() {
        String str = SemSystemProperties.get("ro.product.device");
        Slog.i(TAG, " getDeviceRadius : " + (((str != null ? str.toLowerCase() : "").contains("f2q") && isFolded()) ? 3.9f : 6.0f));
    }

    public static boolean isFolded() {
        SemWindowManager semWindowManager = SemWindowManager.getInstance();
        if (semWindowManager != null) {
            return semWindowManager.isFolded();
        }
        return false;
    }

    public static boolean isLargeCoverFlipFolded() {
        String string = ("user".equals(Build.TYPE) || (SystemProperties.getInt("persist.debug.subdisplay_test_mode", 0) & 1) == 0) ? SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY") : "";
        if (string.contains("COVER") && string.contains("LARGESCREEN")) {
            return isFolded();
        }
        return false;
    }
}
