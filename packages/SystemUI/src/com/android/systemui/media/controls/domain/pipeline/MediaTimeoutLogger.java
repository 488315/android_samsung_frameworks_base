package com.android.systemui.media.controls.domain.pipeline;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaTimeoutLogger {
    public final LogBuffer buffer;

    public MediaTimeoutLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logCustomFromTimeout(String str, String str2) {
        LogLevel logLevel = LogLevel.VERBOSE;
        MediaTimeoutLogger$$ExternalSyntheticLambda0 mediaTimeoutLogger$$ExternalSyntheticLambda0 = new MediaTimeoutLogger$$ExternalSyntheticLambda0(4);
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MediaTimeout", logLevel, mediaTimeoutLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) obtain).str1 = str;
        ((LogMessageImpl) obtain).str2 = str2;
        logBuffer.commit(obtain);
    }
}
