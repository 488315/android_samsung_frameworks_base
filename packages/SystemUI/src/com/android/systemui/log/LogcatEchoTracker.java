package com.android.systemui.log;

import com.android.systemui.log.core.LogLevel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface LogcatEchoTracker {
    boolean isBufferLoggable(LogLevel logLevel, String str);

    boolean isTagLoggable(LogLevel logLevel, String str);
}
