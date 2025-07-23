package com.android.systemui.log.core;

import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface MessageBuffer {
    static /* synthetic */ LogMessage obtain$default(MessageBuffer messageBuffer, String str, LogLevel logLevel, Function1 function1, Throwable th, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: obtain");
        }
        if ((i & 8) != 0) {
            th = null;
        }
        return messageBuffer.obtain(str, logLevel, function1, th);
    }

    void commit(LogMessage logMessage);

    LogMessage obtain(String str, LogLevel logLevel, Function1 function1, Throwable th);
}
