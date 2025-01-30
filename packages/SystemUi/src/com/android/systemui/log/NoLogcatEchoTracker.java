package com.android.systemui.log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NoLogcatEchoTracker implements LogcatEchoTracker {
    @Override // com.android.systemui.log.LogcatEchoTracker
    public final boolean getLogInBackgroundThread() {
        return false;
    }

    @Override // com.android.systemui.log.LogcatEchoTracker
    public final boolean isBufferLoggable(String str, LogLevel logLevel) {
        return false;
    }

    @Override // com.android.systemui.log.LogcatEchoTracker
    public final boolean isTagLoggable(String str, LogLevel logLevel) {
        return false;
    }
}
