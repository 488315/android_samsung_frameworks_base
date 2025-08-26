package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtils;

/* loaded from: classes3.dex */
public final class StackStateLogger {
    public final LogBuffer buffer;
    public final LogBuffer notificationRenderBuffer;

    public StackStateLogger(LogBuffer logBuffer, LogBuffer logBuffer2) {
        this.buffer = logBuffer;
        this.notificationRenderBuffer = logBuffer2;
    }

    public final void animationEnd(String str, String str2, boolean z) {
        LogLevel logLevel = LogLevel.INFO;
        StackStateLogger$$ExternalSyntheticLambda0 stackStateLogger$$ExternalSyntheticLambda0 = new StackStateLogger$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = this.notificationRenderBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("StackScroll", logLevel, stackStateLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = NotificationUtils.logKey(str);
        logMessageImpl.str2 = str2;
        logMessageImpl.bool1 = z;
        logBuffer.commit(logMessageObtain);
    }

    public final void animationStart(String str, String str2, boolean z) {
        LogLevel logLevel = LogLevel.INFO;
        StackStateLogger$$ExternalSyntheticLambda0 stackStateLogger$$ExternalSyntheticLambda0 = new StackStateLogger$$ExternalSyntheticLambda0(5);
        LogBuffer logBuffer = this.notificationRenderBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("StackScroll", logLevel, stackStateLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = NotificationUtils.logKey(str);
        logMessageImpl.str2 = str2;
        logMessageImpl.bool1 = z;
        logBuffer.commit(logMessageObtain);
    }
}
