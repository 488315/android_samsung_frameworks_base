package com.android.app.tracing.coroutines;

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
