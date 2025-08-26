package com.android.systemui.media.controls.ui.controller;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;

/* loaded from: classes2.dex */
public final class MediaViewLogger {
    public final LogBuffer buffer;

    public MediaViewLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logMediaLocation(int i, int i2, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        MediaViewLogger$$ExternalSyntheticLambda0 mediaViewLogger$$ExternalSyntheticLambda0 = new MediaViewLogger$$ExternalSyntheticLambda0(1);
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MediaView", logLevel, mediaViewLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str;
        logMessageImpl.int1 = i;
        logMessageImpl.int2 = i2;
        logBuffer.commit(logMessageObtain);
    }

    public final void logMediaSize(int i, int i2, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        MediaViewLogger$$ExternalSyntheticLambda0 mediaViewLogger$$ExternalSyntheticLambda0 = new MediaViewLogger$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MediaView", logLevel, mediaViewLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = str;
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = i;
        logMessageImpl.int2 = i2;
        logBuffer.commit(logMessageObtain);
    }
}
