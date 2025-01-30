package com.android.systemui.log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface LogcatEchoTracker {
    boolean getLogInBackgroundThread();

    boolean isBufferLoggable(String str, LogLevel logLevel);

    boolean isTagLoggable(String str, LogLevel logLevel);
}
