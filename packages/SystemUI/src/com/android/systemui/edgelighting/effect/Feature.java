package com.android.systemui.edgelighting.effect;

import android.os.Build;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import com.samsung.android.feature.SemFloatingFeature;

/* loaded from: classes2.dex */
public class Feature {
    public static final boolean FEATURE_IS_CANVAS;
    public static final boolean FEATURE_IS_FOLDABLE;
    public static final boolean FEATURE_IS_TABLET_DEVICE;
    public static final boolean FEATURE_IS_TOP;

    static {
        (("user".equals(Build.TYPE) || (SystemProperties.getInt("persist.debug.subdisplay_test_mode", 0) & 1) == 0) ? SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY") : "").contains("LOCKSCREEN");
        String str = SemSystemProperties.get("ro.build.characteristics");
        if (str != null) {
            str.contains("tablet");
        }
        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_EDGE");
        FEATURE_IS_TOP = string != null && string.contains("TOP");
        String lowerCase = SemSystemProperties.get("ro.product.device").toLowerCase();
        FEATURE_IS_CANVAS = lowerCase.contains("c1") || lowerCase.contains("c2");
        FEATURE_IS_FOLDABLE = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD");
        String str2 = SemSystemProperties.get("ro.build.characteristics");
        FEATURE_IS_TABLET_DEVICE = str2 != null && str2.contains("tablet");
    }
}
