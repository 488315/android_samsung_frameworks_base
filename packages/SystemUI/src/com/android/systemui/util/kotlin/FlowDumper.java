package com.android.systemui.util.kotlin;

import android.util.IndentingPrintWriter;
import com.android.systemui.Dumpable;
import com.android.systemui.util.DumpUtilsKt;
import java.io.PrintWriter;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface FlowDumper extends Dumpable {
    @Override // com.android.systemui.Dumpable
    default void dump(PrintWriter printWriter, String[] strArr) {
        dumpFlows(DumpUtilsKt.asIndenting(printWriter));
    }

    void dumpFlows(IndentingPrintWriter indentingPrintWriter);

    <T, F extends SharedFlow> F dumpReplayCache(F f, String str);

    <T, F extends StateFlow> F dumpValue(F f, String str);

    <T> Flow dumpWhileCollecting(Flow flow, String str);
}
