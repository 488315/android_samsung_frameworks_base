package com.android.systemui.log;

import android.os.Process;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

public final class SamsungServiceLoggerImpl implements SamsungServiceLogger {
    public final LogBuffer buffer;

    public SamsungServiceLoggerImpl(String str, int i, DumpManager dumpManager, LogcatEchoTracker logcatEchoTracker) {
        LogBuffer logBuffer = new LogBuffer(str, i, logcatEchoTracker, false, 8, null);
        this.buffer = logBuffer;
        dumpManager.registerBuffer(logBuffer, str);
    }

    public final void log(String str, LogLevel logLevel, final String str2) {
        Function1 function1 = new Function1() { // from class: com.android.systemui.log.SamsungServiceLoggerImpl$log$4
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
        ((LogMessageImpl) obtain).threadId = Process.myTid();
        ((LogMessageImpl) obtain).tagSeparator = '|';
        logBuffer.commit(obtain);
    }
}
