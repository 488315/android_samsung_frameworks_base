package com.android.systemui.log;

import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class FaceAuthenticationLogger {
    public final LogBuffer logBuffer;

    public FaceAuthenticationLogger(LogBuffer logBuffer) {
        this.logBuffer = logBuffer;
    }

    public final void ignoredFaceAuthTrigger(FaceAuthUiEvent faceAuthUiEvent, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        FaceAuthenticationLogger$$ExternalSyntheticLambda0 faceAuthenticationLogger$$ExternalSyntheticLambda0 = new FaceAuthenticationLogger$$ExternalSyntheticLambda0(10);
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("DeviceEntryFaceAuthRepositoryLog", logLevel, faceAuthenticationLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = String.valueOf(faceAuthUiEvent != null ? faceAuthUiEvent.getReason() : null);
        logMessageImpl.str2 = str;
        logBuffer.commit(obtain);
    }
}
