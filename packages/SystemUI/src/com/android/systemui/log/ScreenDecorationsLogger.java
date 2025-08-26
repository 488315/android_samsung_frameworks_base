package com.android.systemui.log;

import android.graphics.RectF;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;

/* loaded from: classes2.dex */
public final class ScreenDecorationsLogger {
    public final LogBuffer logBuffer;

    public ScreenDecorationsLogger(LogBuffer logBuffer) {
        this.logBuffer = logBuffer;
    }

    public final void boundingRect(RectF rectF, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        ScreenDecorationsLogger$$ExternalSyntheticLambda0 screenDecorationsLogger$$ExternalSyntheticLambda0 = new ScreenDecorationsLogger$$ExternalSyntheticLambda0(7);
        LogBuffer logBuffer = this.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("ScreenDecorationsLog", logLevel, screenDecorationsLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = rectF.toShortString();
        logBuffer.commit(logMessageObtain);
    }
}
