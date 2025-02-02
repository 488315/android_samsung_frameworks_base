package com.samsung.context.sdk.samsunganalytics.internal.sender;

/* loaded from: classes.dex */
public final class SimpleLog {
    private String _id;
    private String data;
    private long timestamp;
    private LogType type;

    public SimpleLog() {
    }

    public final String getData() {
        return this.data;
    }

    public final String getId() {
        return this._id;
    }

    public final long getTimestamp() {
        return this.timestamp;
    }

    public final LogType getType() {
        return this.type;
    }

    public final void setData(String str) {
        this.data = str;
    }

    public final void setId(String str) {
        this._id = str;
    }

    public final void setTimestamp(long j) {
        this.timestamp = j;
    }

    public final void setType(LogType logType) {
        this.type = logType;
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
