package com.samsung.context.sdk.samsunganalytics.internal.sender;

/* loaded from: classes4.dex */
public class SimpleLog {
    public String _id;
    public String data;
    public long timestamp;
    public LogType type;

    public SimpleLog() {
    }

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
