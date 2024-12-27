package com.android.modules.utils.build;

import android.os.Build;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SdkLevel {
    private SdkLevel() {}

    private static boolean isAtLeastPreReleaseCodename(String str) {
        String str2 = Build.VERSION.CODENAME;
        return !"REL".equals(str2) && str2.compareTo(str) >= 0;
    }

    public static boolean isAtLeastR() {
        return true;
    }

    public static boolean isAtLeastS() {
        return true;
    }

    public static boolean isAtLeastSv2() {
        return true;
    }

    public static boolean isAtLeastT() {
        return true;
    }

    public static boolean isAtLeastU() {
        return true;
    }

    public static boolean isAtLeastV() {
        return true;
    }
}
