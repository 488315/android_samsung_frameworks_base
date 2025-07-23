package com.android.systemui.media.controls.ui.controller;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        LogMessage obtain = logBuffer.obtain("MediaView", logLevel, mediaViewLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.int1 = i;
        logMessageImpl.int2 = i2;
        logBuffer.commit(obtain);
    }

    public final void logMediaSize(int i, int i2, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        MediaViewLogger$$ExternalSyntheticLambda0 mediaViewLogger$$ExternalSyntheticLambda0 = new MediaViewLogger$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaView", logLevel, mediaViewLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) obtain).str1 = str;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int1 = i;
        logMessageImpl.int2 = i2;
        logBuffer.commit(obtain);
    }
}
