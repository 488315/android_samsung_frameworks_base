package com.android.systemui.keyguard;

import com.android.systemui.log.SamsungServiceLogger;

public final class SecurityDumpLog {
    public static SamsungServiceLogger logger;

    static {
        new SecurityDumpLog();
    }

    private SecurityDumpLog() {
    }
}
