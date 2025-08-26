package com.android.systemui.volume.shared;

import com.android.settingslib.volume.shared.AudioLogger;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;

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
        LogMessage logMessageObtain = logBuffer.obtain("SysUI_Volume", logLevel, volumeLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str1 = str2;
        logBuffer.commit(logMessageObtain);
    }
}
