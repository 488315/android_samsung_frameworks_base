package com.android.systemui.log;

import com.android.systemui.dump.DumpManager;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SamsungServiceLoggerImpl implements SamsungServiceLogger {
    public final LogBuffer buffer;

    public SamsungServiceLoggerImpl(String str, int i, DumpManager dumpManager, LogcatEchoTracker logcatEchoTracker) {
        LogBuffer logBuffer = new LogBuffer(str, i, logcatEchoTracker, false, 8, null);
        this.buffer = logBuffer;
        dumpManager.registerBuffer(logBuffer, str);
    }

    public final void log(String str, LogLevel logLevel, final String str2) {
        Function1 function1 = new Function1() { // from class: com.android.systemui.log.SamsungServiceLoggerImpl$log$4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return str2;
            }
        };
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain(str, logLevel, function1, null));
    }

    public final void logWithThreadId(String str, LogLevel logLevel, final String str2) {
        Function1 function1 = new Function1() { // from class: com.android.systemui.log.SamsungServiceLoggerImpl$logWithThreadId$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return str2;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain(str, logLevel, function1, null);
        obtain.setThreadId(Thread.currentThread().getId());
        obtain.setTagSeparator('|');
        logBuffer.commit(obtain);
    }
}
