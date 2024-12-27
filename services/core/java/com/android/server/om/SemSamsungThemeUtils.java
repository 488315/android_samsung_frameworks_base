package com.android.server.om;

import android.os.SystemProperties;

import java.util.Arrays;
import java.util.List;

public abstract class SemSamsungThemeUtils {
    public static final List disableOverlayList;

    static {
        "eng".equals(SystemProperties.get("ro.build.type"));
        "userdebug".equals(SystemProperties.get("ro.build.type"));
        disableOverlayList =
                Arrays.asList("SemWT_com.android.systemui", "SemWT_android", "SemWT_MonetPalette");
    }
}
