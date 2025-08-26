package com.android.app.motiontool;

/* loaded from: classes.dex */
public final class UnknownTraceIdException extends Exception {
    private final int traceId;

    public UnknownTraceIdException(int i) {
        this.traceId = i;
    }

    public final int getTraceId() {
        return this.traceId;
    }
}
