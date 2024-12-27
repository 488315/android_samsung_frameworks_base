package com.android.server.devicepolicy;

import android.app.admin.SecurityLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class CryptoTestHelper {
    public static void runAndLogSelfTest() {
        SecurityLog.writeEvent(210031, new Object[]{Integer.valueOf(runSelfTest())});
    }

    private static native int runSelfTest();
}
