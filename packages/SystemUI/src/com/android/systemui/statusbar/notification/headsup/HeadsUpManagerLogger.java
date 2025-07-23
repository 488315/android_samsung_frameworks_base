package com.android.systemui.statusbar.notification.headsup;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class HeadsUpManagerLogger {
    public final LogBuffer buffer;

    public HeadsUpManagerLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logAvalancheDelete(String str, String str2, String str3, boolean z) {
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpManagerLogger$$ExternalSyntheticLambda0 headsUpManagerLogger$$ExternalSyntheticLambda0 = new HeadsUpManagerLogger$$ExternalSyntheticLambda0(13);
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = str2;
        logMessageImpl.str3 = str3;
        logMessageImpl.bool1 = z;
        logBuffer.commit(obtain);
    }

    public final void logAvalancheDuration(String str, RemainingDuration$UpdatedDuration remainingDuration$UpdatedDuration, String str2, String str3) {
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpManagerLogger$$ExternalSyntheticLambda0 headsUpManagerLogger$$ExternalSyntheticLambda0 = new HeadsUpManagerLogger$$ExternalSyntheticLambda0(19);
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.int1 = remainingDuration$UpdatedDuration.duration;
        logMessageImpl.str2 = str2;
        logMessageImpl.str3 = str3;
        logBuffer.commit(obtain);
    }

    public final void logAvalancheStage(String str, String str2) {
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpManagerLogger$$ExternalSyntheticLambda0 headsUpManagerLogger$$ExternalSyntheticLambda0 = new HeadsUpManagerLogger$$ExternalSyntheticLambda0(6);
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = str2;
        logBuffer.commit(obtain);
    }

    public final void logAvalancheUpdate(String str, String str2, String str3, boolean z) {
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpManagerLogger$$ExternalSyntheticLambda0 headsUpManagerLogger$$ExternalSyntheticLambda0 = new HeadsUpManagerLogger$$ExternalSyntheticLambda0(8);
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) obtain).str1 = str;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str2 = str2;
        logMessageImpl.str3 = str3;
        logMessageImpl.bool1 = z;
        logBuffer.commit(obtain);
    }
}
