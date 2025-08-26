package com.android.app.tracing;

import android.os.Trace;

/* loaded from: classes.dex */
public abstract class TraceUtilsKt {
    public static final void beginSlice(String str) {
        Trace.traceBegin(4096L, str);
    }

    public static final void endSlice() {
        Trace.traceEnd(4096L);
    }
}
