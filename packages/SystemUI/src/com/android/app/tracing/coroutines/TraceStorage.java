package com.android.app.tracing.coroutines;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TraceStorage {
    public TraceData data;
    public int contIndex = -1;
    public byte[] openSliceCount = new byte[4];
    public int[] continuationIds = null;

    public TraceStorage(TraceData traceData) {
        this.data = traceData;
    }
}
