package com.android.app.tracing;

import android.os.Trace;
import java.util.concurrent.ThreadLocalRandom;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class FlowTracing$$ExternalSyntheticLambda1 implements Function0 {
    public final /* synthetic */ String f$0;
    public final /* synthetic */ Function0 f$1;

    public /* synthetic */ FlowTracing$$ExternalSyntheticLambda1(String str, Function0 function0) {
        this.f$0 = str;
        this.f$1 = function0;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        boolean isEnabled;
        FlowTracing flowTracing = FlowTracing.INSTANCE;
        boolean isEnabled2 = Trace.isEnabled();
        String str = this.f$0;
        Function0 function0 = this.f$1;
        if (isEnabled2) {
            int i = TraceUtils.$r8$clinit;
            String concat = str.concat("#TracedAwaitClose");
            int nextInt = ThreadLocalRandom.current().nextInt();
            Trace.asyncTraceForTrackBegin(4096L, "FlowTracing", concat, nextInt);
            try {
                isEnabled = Trace.isEnabled();
                if (isEnabled) {
                    TraceUtilsKt.beginSlice(str.concat("#TracedAwaitClose"));
                }
                try {
                    function0.invoke();
                    Unit unit = Unit.INSTANCE;
                    if (isEnabled) {
                        TraceUtilsKt.endSlice();
                    }
                } finally {
                }
            } finally {
                Trace.asyncTraceForTrackEnd(4096L, "FlowTracing", nextInt);
            }
        } else {
            isEnabled = Trace.isEnabled();
            if (isEnabled) {
                TraceUtilsKt.beginSlice(str.concat("#TracedAwaitClose"));
            }
            try {
                function0.invoke();
                Unit unit2 = Unit.INSTANCE;
            } finally {
                if (isEnabled) {
                    TraceUtilsKt.endSlice();
                }
            }
        }
        return Unit.INSTANCE;
    }
}
