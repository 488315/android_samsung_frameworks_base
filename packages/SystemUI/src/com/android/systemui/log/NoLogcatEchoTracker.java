package com.android.systemui.log;

import com.android.systemui.log.core.LogLevel;

public final class NoLogcatEchoTracker implements LogcatEchoTracker {
    @Override // com.android.systemui.log.LogcatEchoTracker
    public final boolean isBufferLoggable(LogLevel logLevel, String str) {
        return false;
    }

    @Override // com.android.systemui.log.LogcatEchoTracker
    public final boolean isTagLoggable(LogLevel logLevel, String str) {
        return false;
    }
}
