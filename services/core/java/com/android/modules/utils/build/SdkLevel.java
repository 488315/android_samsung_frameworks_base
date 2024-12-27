package com.android.modules.utils.build;

import android.os.Build;

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
