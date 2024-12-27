package com.android.server.om;

import android.os.SystemProperties;

import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class SemSamsungThemeUtils {
    public static final List disableOverlayList;

    static {
        "eng".equals(SystemProperties.get("ro.build.type"));
        "userdebug".equals(SystemProperties.get("ro.build.type"));
        disableOverlayList =
                Arrays.asList("SemWT_com.android.systemui", "SemWT_android", "SemWT_MonetPalette");
    }
}
