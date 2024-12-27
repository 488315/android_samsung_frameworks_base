package com.android.systemui.edgelighting.effect;

import android.os.Build;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class Feature {
    public static final boolean FEATURE_IS_CANVAS;
    public static final boolean FEATURE_IS_FOLDABLE;
    public static final boolean FEATURE_IS_TABLET_DEVICE;
    public static final boolean FEATURE_IS_TOP;

    static {
        boolean z;
        (("user".equals(Build.TYPE) || (SystemProperties.getInt("persist.debug.subdisplay_test_mode", 0) & 1) == 0) ? SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY") : "").contains("LOCKSCREEN");
        String str = SemSystemProperties.get("ro.build.characteristics");
        if (str != null) {
            str.contains("tablet");
        }
        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_EDGE");
        FEATURE_IS_TOP = string != null && string.contains("TOP");
        String str2 = SemSystemProperties.get("ro.product.device");
        if (str2 != null) {
            String lowerCase = str2.toLowerCase();
            if (lowerCase.contains("c1") || lowerCase.contains("c2")) {
                z = true;
                FEATURE_IS_CANVAS = z;
                FEATURE_IS_FOLDABLE = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD");
                String str3 = SemSystemProperties.get("ro.build.characteristics");
                FEATURE_IS_TABLET_DEVICE = str3 == null && str3.contains("tablet");
            }
        }
        z = false;
        FEATURE_IS_CANVAS = z;
        FEATURE_IS_FOLDABLE = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD");
        String str32 = SemSystemProperties.get("ro.build.characteristics");
        FEATURE_IS_TABLET_DEVICE = str32 == null && str32.contains("tablet");
    }
}
