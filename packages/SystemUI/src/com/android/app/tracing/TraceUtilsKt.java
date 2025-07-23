package com.android.app.tracing;

import android.os.Trace;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class TraceUtilsKt {
    public static final void beginSlice(String str) {
        Trace.traceBegin(4096L, str);
    }

    public static final void endSlice() {
        Trace.traceEnd(4096L);
    }
}
