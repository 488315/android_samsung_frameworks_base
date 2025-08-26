package com.android.internal.protolog.common;

import java.util.List;

/* loaded from: classes4.dex */
public interface IProtoLog {
    List<IProtoLogGroup> getRegisteredGroups();

    boolean isEnabled(IProtoLogGroup iProtoLogGroup, LogLevel logLevel);

    boolean isProtoEnabled();

    void log(LogLevel logLevel, IProtoLogGroup iProtoLogGroup, long j, int i, Object[] objArr);

    void log(LogLevel logLevel, IProtoLogGroup iProtoLogGroup, String str, Object... objArr);

    int startLoggingToLogcat(String[] strArr, ILogger iLogger);

    int stopLoggingToLogcat(String[] strArr, ILogger iLogger);
}
