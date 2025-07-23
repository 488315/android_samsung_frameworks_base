package com.android.systemui.volume.shared;

import com.android.settingslib.volume.shared.AudioLogger;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeLogger implements AudioLogger {
    public final LogBuffer logBuffer;

    public VolumeLogger(LogBuffer logBuffer) {
        this.logBuffer = logBuffer;
    }

    public final void onAudioSharingAvailabilityRequestedError(String str, String str2) {
        LogLevel logLevel = LogLevel.WARNING;
        VolumeLogger$$ExternalSyntheticLambda0 volumeLogger$$ExternalSyntheticLambda0 = new VolumeLogger$$ExternalSyntheticLambda0(2);
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("SysUI_Volume", logLevel, volumeLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str1 = str2;
        logBuffer.commit(obtain);
    }
}
