package com.android.systemui.log;

import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;

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
        LogMessage logMessageObtain = logBuffer.obtain("BouncerLog", logLevel, bouncerLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str;
        logMessageImpl.bool1 = z;
        logBuffer.commit(logMessageObtain);
    }
}
