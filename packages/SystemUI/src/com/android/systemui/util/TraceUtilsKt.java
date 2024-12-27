package com.android.systemui.util;

import android.os.Trace;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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
