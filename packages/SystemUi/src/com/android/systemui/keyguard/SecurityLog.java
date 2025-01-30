package com.android.systemui.keyguard;

import com.android.systemui.log.LogLevel;
import com.android.systemui.log.SamsungServiceLogger;
import com.android.systemui.log.SamsungServiceLoggerImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SecurityLog {
    /* renamed from: d */
    public static void m143d(String str, String str2) {
        android.util.Log.d(str, str2);
        LogLevel logLevel = LogLevel.DEBUG;
        SamsungServiceLogger samsungServiceLogger = SecurityDumpLog.logger;
        if (samsungServiceLogger != null) {
            ((SamsungServiceLoggerImpl) samsungServiceLogger).logWithThreadId(str, logLevel, str2);
        }
    }
}
