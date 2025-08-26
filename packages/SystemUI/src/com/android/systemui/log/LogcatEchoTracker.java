package com.android.systemui.log;

import com.android.systemui.log.core.LogLevel;

/* loaded from: classes2.dex */
public interface LogcatEchoTracker {
    boolean isBufferLoggable(LogLevel logLevel, String str);

    boolean isTagLoggable(LogLevel logLevel, String str);
}
