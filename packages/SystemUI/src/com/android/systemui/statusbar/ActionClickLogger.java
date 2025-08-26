package com.android.systemui.statusbar;

import android.app.PendingIntent;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;

/* loaded from: classes3.dex */
public final class ActionClickLogger {
    public final LogBuffer buffer;

    public ActionClickLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logInitialClick(PendingIntent pendingIntent, Integer num, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        ActionClickLogger$$ExternalSyntheticLambda0 actionClickLogger$$ExternalSyntheticLambda0 = new ActionClickLogger$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("ActionClickLogger", logLevel, actionClickLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = str;
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str3 = pendingIntent.toString();
        logMessageImpl.int1 = num != null ? num.intValue() : Integer.MIN_VALUE;
        logBuffer.commit(logMessageObtain);
    }

    public final void logStartingIntentWithDefaultHandler(PendingIntent pendingIntent, Integer num, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        ActionClickLogger$$ExternalSyntheticLambda0 actionClickLogger$$ExternalSyntheticLambda0 = new ActionClickLogger$$ExternalSyntheticLambda0(4);
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("ActionClickLogger", logLevel, actionClickLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = pendingIntent.toString();
        logMessageImpl.int1 = num != null ? num.intValue() : Integer.MIN_VALUE;
        logBuffer.commit(logMessageObtain);
    }
}
