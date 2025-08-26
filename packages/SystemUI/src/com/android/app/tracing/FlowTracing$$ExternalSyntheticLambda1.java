package com.android.app.tracing;

import android.os.Trace;
import java.util.concurrent.ThreadLocalRandom;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

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
        boolean zIsEnabled;
        FlowTracing flowTracing = FlowTracing.INSTANCE;
        boolean zIsEnabled2 = Trace.isEnabled();
        String str = this.f$0;
        Function0 function0 = this.f$1;
        if (zIsEnabled2) {
            int i = TraceUtils.$r8$clinit;
            String strConcat = str.concat("#TracedAwaitClose");
            int iNextInt = ThreadLocalRandom.current().nextInt();
            Trace.asyncTraceForTrackBegin(4096L, "FlowTracing", strConcat, iNextInt);
            try {
                zIsEnabled = Trace.isEnabled();
                if (zIsEnabled) {
                    TraceUtilsKt.beginSlice(str.concat("#TracedAwaitClose"));
                }
                try {
                    function0.invoke();
                    Unit unit = Unit.INSTANCE;
                    if (zIsEnabled) {
                        TraceUtilsKt.endSlice();
                    }
                } finally {
                }
            } finally {
                Trace.asyncTraceForTrackEnd(4096L, "FlowTracing", iNextInt);
            }
        } else {
            zIsEnabled = Trace.isEnabled();
            if (zIsEnabled) {
                TraceUtilsKt.beginSlice(str.concat("#TracedAwaitClose"));
            }
            try {
                function0.invoke();
                Unit unit2 = Unit.INSTANCE;
            } finally {
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
            }
        }
        return Unit.INSTANCE;
    }
}
