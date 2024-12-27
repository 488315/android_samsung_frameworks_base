package com.android.systemui;

import android.os.Build;
import android.os.Debug;
import android.os.SystemProperties;
import com.android.systemui.util.LogUtil;
import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ScRune extends Rune {
    public static final boolean ENHANCEMENT_DEBUG_MEMORY_LOG;
    public static final boolean ENHANCEMENT_DUMP_HELPER;
    public static final boolean QUICK_MANAGE_SUBSCREEN_TILE_LIST;

    static {
        ENHANCEMENT_DEBUG_MEMORY_LOG = Debug.semIsProductDev() || LogUtil.isDebugLevelMid();
        ENHANCEMENT_DUMP_HELPER = Debug.semIsProductDev() || LogUtil.isDebugLevelMid();
        QUICK_MANAGE_SUBSCREEN_TILE_LIST = (("user".equals(Build.TYPE) || (SystemProperties.getInt("persist.debug.subdisplay_test_mode", 0) & 1) == 0) ? SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY") : "").contains("LARGESCREEN");
    }
}
