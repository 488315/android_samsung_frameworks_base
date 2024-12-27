package com.android.internal.protolog.common;

/* loaded from: classes5.dex */
public interface IProtoLog {
    boolean isEnabled(IProtoLogGroup iProtoLogGroup, LogLevel logLevel);

    boolean isProtoEnabled();

    void log(
            LogLevel logLevel,
            IProtoLogGroup iProtoLogGroup,
            long j,
            int i,
            String str,
            Object[] objArr);

    int startLoggingToLogcat(String[] strArr, ILogger iLogger);

    int stopLoggingToLogcat(String[] strArr, ILogger iLogger);
}
