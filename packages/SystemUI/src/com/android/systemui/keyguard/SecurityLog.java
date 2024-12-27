package com.android.systemui.keyguard;

import com.android.systemui.log.SamsungServiceLogger;
import com.android.systemui.log.SamsungServiceLoggerImpl;
import com.android.systemui.log.core.LogLevel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
