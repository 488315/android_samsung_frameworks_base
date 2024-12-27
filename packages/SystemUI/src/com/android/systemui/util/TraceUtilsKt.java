package com.android.systemui.util;

import android.os.Trace;
import kotlin.jvm.functions.Function0;

public final class TraceUtilsKt {
    public static final <T> T traceSection(String str, Function0 function0) {
        if (!Trace.isTagEnabled(4096L)) {
            return (T) function0.invoke();
        }
        Trace.traceBegin(4096L, str);
        try {
            return (T) function0.invoke();
        } finally {
            Trace.traceEnd(4096L);
        }
    }
}
