package com.android.systemui.util;

import android.os.Trace;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class TraceUtils {
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Runnable namedRunnable(String str, Function0 function0) {
            return new TraceUtils$Companion$namedRunnable$1(str, function0);
        }

        public final Runnable traceRunnable(final String str, final Function0 function0) {
            return new Runnable() { // from class: com.android.systemui.util.TraceUtils$Companion$traceRunnable$1
                @Override // java.lang.Runnable
                public final void run() {
                    String str2 = str;
                    Function0 function02 = function0;
                    if (!Trace.isTagEnabled(4096L)) {
                        function02.invoke();
                        return;
                    }
                    Trace.traceBegin(4096L, str2);
                    try {
                        function02.invoke();
                        Unit unit = Unit.INSTANCE;
                    } finally {
                        Trace.traceEnd(4096L);
                    }
                }
            };
        }

        private Companion() {
        }
    }
}
