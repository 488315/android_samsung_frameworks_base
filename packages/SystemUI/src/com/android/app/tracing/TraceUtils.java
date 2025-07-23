package com.android.app.tracing;

import android.os.Trace;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TraceUtils {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        new TraceUtils();
    }

    private TraceUtils() {
    }

    public static final Object trace(String str, Function0 function0) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice(str);
        }
        try {
            return function0.invoke();
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }
}
