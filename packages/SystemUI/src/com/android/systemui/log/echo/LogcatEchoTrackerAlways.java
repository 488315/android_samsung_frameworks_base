package com.android.systemui.log.echo;

import com.android.systemui.log.LogcatEchoTracker;
import com.android.systemui.log.core.LogLevel;

/* loaded from: classes2.dex */
public final class LogcatEchoTrackerAlways implements LogcatEchoTracker {
    public static final LogcatEchoTrackerAlways INSTANCE = new LogcatEchoTrackerAlways();

    private LogcatEchoTrackerAlways() {
    }

    @Override // com.android.systemui.log.LogcatEchoTracker
    public final boolean isBufferLoggable(LogLevel logLevel, String str) {
        return true;
    }

    @Override // com.android.systemui.log.LogcatEchoTracker
    public final boolean isTagLoggable(LogLevel logLevel, String str) {
        return true;
    }
}
