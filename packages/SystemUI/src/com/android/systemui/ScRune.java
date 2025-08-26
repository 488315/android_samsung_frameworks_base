package com.android.systemui;

import android.os.Build;
import android.os.Debug;
import android.os.SystemProperties;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.LogUtil;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;

/* loaded from: classes.dex */
public class ScRune extends Rune {
    public static final boolean ENHANCEMENT_DEBUG_MEMORY_LOG;
    public static final boolean ENHANCEMENT_DUMP_HELPER;
    public static final boolean QUICK_MANAGE_MULTI_QSHOST;
    public static final boolean QUICK_MANAGE_SUBSCREEN_TILE_LIST;
    public static final boolean QUICK_MANAGE_TILE_LIST_TEST;
    public static final boolean QUICK_SUPPORT_LOCATION_PRIVACY_CHIP;

    static {
        ENHANCEMENT_DEBUG_MEMORY_LOG = Debug.semIsProductDev() || LogUtil.isDebugLevelMid();
        ENHANCEMENT_DUMP_HELPER = Debug.semIsProductDev() || LogUtil.isDebugLevelMid();
        QUICK_MANAGE_TILE_LIST_TEST = !DeviceType.isShipBuild();
        QUICK_SUPPORT_LOCATION_PRIVACY_CHIP = "US".equals(SemCscFeature.getInstance().getString("CountryISO", ""));
        QUICK_MANAGE_SUBSCREEN_TILE_LIST = (("user".equals(Build.TYPE) || (SystemProperties.getInt("persist.debug.subdisplay_test_mode", 0) & 1) == 0) ? SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY") : "").contains("LARGESCREEN");
        QUICK_MANAGE_MULTI_QSHOST = true;
    }
}
