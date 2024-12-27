package com.android.systemui.util;

import android.os.Build;

public class TestHelper {
    public static boolean isRoboUnitTest() {
        return "robolectric".equals(Build.FINGERPRINT);
    }
}
