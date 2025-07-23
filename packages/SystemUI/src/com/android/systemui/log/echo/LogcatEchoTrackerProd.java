package com.android.systemui.log.echo;

import com.android.systemui.log.LogcatEchoTracker;
import com.android.systemui.log.core.LogLevel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class LogcatEchoTrackerProd implements LogcatEchoTracker {
    @Override // com.android.systemui.log.LogcatEchoTracker
    public final boolean isBufferLoggable(LogLevel logLevel, String str) {
        return logLevel.compareTo(LogLevel.WARNING) >= 0;
    }

    @Override // com.android.systemui.log.LogcatEchoTracker
    public final boolean isTagLoggable(LogLevel logLevel, String str) {
        return logLevel.compareTo(LogLevel.WARNING) >= 0;
    }
}
