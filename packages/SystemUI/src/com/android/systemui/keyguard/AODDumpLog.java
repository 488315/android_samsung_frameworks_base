package com.android.systemui.keyguard;

import com.android.systemui.log.SamsungServiceLogger;

public final class AODDumpLog {
    public static SamsungServiceLogger logger;

    static {
        new AODDumpLog();
    }

    private AODDumpLog() {
    }
}
