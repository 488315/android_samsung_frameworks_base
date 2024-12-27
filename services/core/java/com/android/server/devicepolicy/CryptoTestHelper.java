package com.android.server.devicepolicy;

import android.app.admin.SecurityLog;

public abstract class CryptoTestHelper {
    public static void runAndLogSelfTest() {
        SecurityLog.writeEvent(210031, new Object[] {Integer.valueOf(runSelfTest())});
    }

    private static native int runSelfTest();
}
