package com.android.systemui.log;

import com.android.systemui.log.core.LogLevel;

public interface LogcatEchoTracker {
    boolean isBufferLoggable(LogLevel logLevel, String str);

    boolean isTagLoggable(LogLevel logLevel, String str);
}
