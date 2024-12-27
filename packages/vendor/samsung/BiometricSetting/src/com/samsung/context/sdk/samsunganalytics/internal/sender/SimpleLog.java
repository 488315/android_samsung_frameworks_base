package com.samsung.context.sdk.samsunganalytics.internal.sender;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final class SimpleLog {
    public String _id;
    public String data;
    public long timestamp;
    public LogType type;

    public SimpleLog(long j, String str, LogType logType) {
        this("", j, str, logType);
    }

    public SimpleLog(String str, long j, String str2, LogType logType) {
        this._id = str;
        this.timestamp = j;
        this.data = str2;
        this.type = logType;
    }
}
