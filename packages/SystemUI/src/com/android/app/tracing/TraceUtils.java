package com.android.app.tracing;

import android.os.Trace;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final class TraceUtils {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        new TraceUtils();
    }

    private TraceUtils() {
    }

    public static final Object trace(String str, Function0 function0) {
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice(str);
        }
        try {
            return function0.invoke();
        } finally {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }
}
