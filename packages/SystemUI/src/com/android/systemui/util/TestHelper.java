package com.android.systemui.util;

import android.os.Build;

/* loaded from: classes3.dex */
public class TestHelper {
    public static boolean isRoboUnitTest() {
        return "robolectric".equals(Build.FINGERPRINT);
    }
}
