package com.android.systemui.log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LogcatEchoTrackerProd implements LogcatEchoTracker {
    private final boolean logInBackgroundThread;

    @Override // com.android.systemui.log.LogcatEchoTracker
    public boolean getLogInBackgroundThread() {
        return this.logInBackgroundThread;
    }

    @Override // com.android.systemui.log.LogcatEchoTracker
    public boolean isBufferLoggable(String str, LogLevel logLevel) {
        return logLevel.compareTo(LogLevel.WARNING) >= 0;
    }

    @Override // com.android.systemui.log.LogcatEchoTracker
    public boolean isTagLoggable(String str, LogLevel logLevel) {
        return logLevel.compareTo(LogLevel.WARNING) >= 0;
    }
}
