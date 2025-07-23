package com.android.systemui.util.kotlin;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class JavaAdapter {
    public static final int $stable = 8;
    private final CoroutineScope scope;

    public JavaAdapter(CoroutineScope coroutineScope) {
        this.scope = coroutineScope;
    }

    public static StateFlow stateInApp$default(JavaAdapter javaAdapter, Flow flow, Object obj, SharingStarted sharingStarted, int i, Object obj2) {
        if ((i & 4) != 0) {
            SharingStarted.Companion.getClass();
            sharingStarted = SharingStarted.Companion.Eagerly;
        }
        return javaAdapter.stateInApp(flow, obj, sharingStarted);
    }

    public final <T> Job alwaysCollectFlow(Flow flow, Consumer<T> consumer) {
        return CoroutineTracingKt.launchTraced$default(this.scope, null, null, new JavaAdapter$alwaysCollectFlow$1(flow, consumer, null), 7);
    }

    public final <T, R> Job callSuspend(Function2 function2, T t, Function1 function1, Function1 function12, Function1 function13) {
        return CoroutineTracingKt.launchTraced$default(this.scope, null, null, new JavaAdapter$callSuspend$1(function2, t, function12, function13, function1, null), 7);
    }

    public final <T> StateFlow stateInApp(Flow flow, T t) {
        return stateInApp$default(this, flow, t, null, 4, null);
    }

    public final <T> StateFlow stateInApp(Flow flow, T t, SharingStarted sharingStarted) {
        return kotlinx.coroutines.flow.FlowKt.stateIn(flow, this.scope, sharingStarted, t);
    }
}
