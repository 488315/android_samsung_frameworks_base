package com.android.systemui.log.echo;

import com.android.systemui.log.LogcatEchoTracker;
import com.android.systemui.log.core.LogLevel;

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
