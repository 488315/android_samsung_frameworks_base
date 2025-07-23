package com.android.systemui.log;

import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class BouncerLogger {
    public final LogBuffer buffer;

    public BouncerLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void interestedStateChanged(String str, boolean z) {
        LogLevel logLevel = LogLevel.DEBUG;
        BouncerLogger$$ExternalSyntheticLambda0 bouncerLogger$$ExternalSyntheticLambda0 = new BouncerLogger$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("BouncerLog", logLevel, bouncerLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.bool1 = z;
        logBuffer.commit(obtain);
    }
}
