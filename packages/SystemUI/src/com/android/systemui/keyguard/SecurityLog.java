package com.android.systemui.keyguard;

import com.android.systemui.log.SamsungServiceLogger;
import com.android.systemui.log.SamsungServiceLoggerImpl;
import com.android.systemui.log.core.LogLevel;

public final class SecurityLog {
    public static void d(String str, String str2) {
        android.util.Log.d(str, str2);
        LogLevel logLevel = LogLevel.DEBUG;
        SamsungServiceLogger samsungServiceLogger = SecurityDumpLog.logger;
        if (samsungServiceLogger != null) {
            ((SamsungServiceLoggerImpl) samsungServiceLogger).logWithThreadId(str, logLevel, str2);
        }
    }
}
