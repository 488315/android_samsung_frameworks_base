package com.android.systemui.edgelighting.effect.utils;

import android.os.Build;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import android.util.Slog;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.view.SemWindowManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Utils {
    public static String MODEL_NAME = SemSystemProperties.get("ro.product.model");
    public static final String MODEL_NAME_FOR_JPN = SemSystemProperties.get("ro.factory.model");
    public static final int MODEL_DEFAULT_DENSITY = SemSystemProperties.getInt("ro.sf.lcd_density", -1);
    public static final String TAG = "Utils";

    public static void getRadiusController() {
        SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_EDGE");
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
