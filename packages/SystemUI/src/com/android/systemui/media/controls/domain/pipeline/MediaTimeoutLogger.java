package com.android.systemui.media.controls.domain.pipeline;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;

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
        LogMessage logMessageObtain = logBuffer.obtain("MediaTimeout", logLevel, mediaTimeoutLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = str;
        ((LogMessageImpl) logMessageObtain).str2 = str2;
        logBuffer.commit(logMessageObtain);
    }
}
