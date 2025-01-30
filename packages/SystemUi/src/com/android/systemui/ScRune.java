package com.android.systemui;

import android.os.Build;
import android.os.Debug;
import android.os.SystemProperties;
import com.android.systemui.util.LogUtil;
import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ScRune extends Rune {
    public static final boolean ENHANCEMENT_DUMP_HELPER;
    public static final boolean QUICK_MANAGE_SUBSCREEN_TILE_LIST;
    public static final boolean QUICK_TILE_SUBSCREEN_SENSOR_PRIVACY;

    static {
        ENHANCEMENT_DUMP_HELPER = Debug.semIsProductDev() || LogUtil.isDebugLevelMid;
        String string = ("user".equals(Build.TYPE) || (SystemProperties.getInt("persist.debug.subdisplay_test_mode", 0) & 1) == 0) ? SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY") : "";
        QUICK_MANAGE_SUBSCREEN_TILE_LIST = string.contains("LARGESCREEN");
        QUICK_TILE_SUBSCREEN_SENSOR_PRIVACY = string.contains("LARGESCREEN");
    }
}
