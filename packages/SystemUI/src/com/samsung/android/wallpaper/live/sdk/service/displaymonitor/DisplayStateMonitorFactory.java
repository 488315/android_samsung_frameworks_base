package com.samsung.android.wallpaper.live.sdk.service.displaymonitor;

import com.android.systemui.BasicRune$$ExternalSyntheticOutline0;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.wallpaper.live.sdk.utils.SdkDeviceUtils;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public class DisplayStateMonitorFactory {
    public static final int sFullAodSupportDisplays;
    public static final boolean sIsSupportAod;
    public static final boolean sIsSupportSeamlessAod;

    static {
        Method method = SdkDeviceUtils.sMethodIsDozeAfterScreenOff;
        int i = SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_AOD_FULLSCREEN");
        boolean z = false;
        int i2 = 4;
        if (i != 1) {
            if (i != 2) {
                i2 = i != 3 ? 0 : 16;
            }
        } else if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FLIP") || SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD")) {
            i2 = 20;
        }
        sFullAodSupportDisplays = i2;
        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY");
        if (string.contains("WATCHFACE") && string.contains("LARGESCREEN")) {
            z = true;
        }
        sIsSupportSeamlessAod = z;
        sIsSupportAod = BasicRune$$ExternalSyntheticOutline0.m("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM", "aodversion");
    }
}
