package com.samsung.context.sdk.samsunganalytics.internal.sender;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SimpleLog {
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
