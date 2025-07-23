package com.android.systemui.log;

import android.os.Process;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda7;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SamsungServiceLoggerImpl implements SamsungServiceLogger {
    public final LogBuffer buffer;

    public SamsungServiceLoggerImpl(String str, int i, DumpManager dumpManager, LogcatEchoTracker logcatEchoTracker) {
        LogBuffer logBuffer = new LogBuffer(str, i, logcatEchoTracker, false, null, 24, null);
        this.buffer = logBuffer;
        dumpManager.registerBuffer(logBuffer, str);
    }

    public final void logWithThreadId(String str, LogLevel logLevel, String str2) {
        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda7 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda7 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda7(str2);
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain(str, logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda7, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.threadId = Process.myTid();
        logMessageImpl.tagSeparator = '|';
        logBuffer.commit(obtain);
    }
}
