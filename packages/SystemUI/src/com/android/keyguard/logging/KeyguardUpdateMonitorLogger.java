package com.android.keyguard.logging;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class KeyguardUpdateMonitorLogger {
    public final LogBuffer logBuffer;

    public KeyguardUpdateMonitorLogger(LogBuffer logBuffer) {
        this.logBuffer = logBuffer;
    }

    public final void d(String str) {
        LogBuffer.log$default(this.logBuffer, "KeyguardUpdateMonitorLog", LogLevel.DEBUG, str);
    }

    public final void logException(String str, Exception exc) {
        LogLevel logLevel = LogLevel.ERROR;
        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda7 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda7 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda7(str);
        LogBuffer logBuffer = this.logBuffer;
        logBuffer.commit(logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda7, exc));
    }

    public final void logKeyguardShowingChanged(boolean z, boolean z2, boolean z3) {
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(24);
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = z2;
        logMessageImpl.bool3 = z3;
        logBuffer.commit(obtain);
    }

    public final void logRetryAfterFpErrorWithDelay(int i, int i2, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(8);
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int1 = i;
        logMessageImpl.int2 = i2;
        logMessageImpl.str1 = String.valueOf(str);
        logBuffer.commit(obtain);
    }

    public final void logTrustUsuallyManagedUpdated(String str, int i, boolean z, boolean z2) {
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(8);
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int1 = i;
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = z2;
        logMessageImpl.str1 = str;
        logBuffer.commit(obtain);
    }

    public final void v(String str) {
        LogBuffer.log$default(this.logBuffer, "KeyguardUpdateMonitorLog", LogLevel.VERBOSE, str);
    }
}
