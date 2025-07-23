package com.android.systemui.keyguard;

import com.android.systemui.log.SamsungServiceLogger;
import com.android.systemui.log.SamsungServiceLoggerImpl;
import com.android.systemui.log.core.LogLevel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SecurityLog {
    public static void d(String str, String str2) {
        android.util.Log.d(str, str2);
        LogLevel logLevel = LogLevel.DEBUG;
        SamsungServiceLogger samsungServiceLogger = SecurityDumpLog.logger;
        if (samsungServiceLogger != null) {
            ((SamsungServiceLoggerImpl) samsungServiceLogger).logWithThreadId(str, logLevel, str2);
        }
    }
}
