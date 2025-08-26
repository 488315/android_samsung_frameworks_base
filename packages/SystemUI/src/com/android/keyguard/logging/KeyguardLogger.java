package com.android.keyguard.logging;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;

/* loaded from: classes.dex */
public final class KeyguardLogger {
    public final LogBuffer buffer;

    public KeyguardLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void log(String str, LogLevel logLevel, String str2, Object obj) {
        KeyguardLogger$$ExternalSyntheticLambda0 keyguardLogger$$ExternalSyntheticLambda0 = new KeyguardLogger$$ExternalSyntheticLambda0(5);
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain(str, logLevel, keyguardLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str2;
        logMessageImpl.str2 = obj.toString();
        logBuffer.commit(logMessageObtain);
    }

    public final void logBiometricMessage(String str, Integer num, String str2) {
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardLogger$$ExternalSyntheticLambda0 keyguardLogger$$ExternalSyntheticLambda0 = new KeyguardLogger$$ExternalSyntheticLambda0(4);
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardLog", logLevel, keyguardLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = String.valueOf(num);
        logMessageImpl.str3 = str2;
        logBuffer.commit(logMessageObtain);
    }

    public final void showingUnlockRippleAt(int i, int i2, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardLogger$$ExternalSyntheticLambda0 keyguardLogger$$ExternalSyntheticLambda0 = new KeyguardLogger$$ExternalSyntheticLambda0(2);
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("AuthRippleController", logLevel, keyguardLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).int1 = i;
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int2 = i2;
        logMessageImpl.str1 = str;
        logBuffer.commit(logMessageObtain);
    }
}
