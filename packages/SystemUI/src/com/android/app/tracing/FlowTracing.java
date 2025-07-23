package com.android.app.tracing;

import com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel$$ExternalSyntheticLambda0;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$IntRef;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FlowTracing {
    public static final FlowTracing INSTANCE = new FlowTracing();
    public static final AtomicInteger counter = new AtomicInteger(0);

    private FlowTracing() {
    }

    public static FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 traceAsCounter$default(FlowTracing flowTracing, Flow flow, String str, Function1 function1) {
        flowTracing.getClass();
        return new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(flow, new FlowTracing$traceAsCounter$2(str, function1, null));
    }

    public static FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 traceEmissionCount$default(FlowTracing flowTracing, Flow flow, String str) {
        flowTracing.getClass();
        return new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(flow, new FlowTracing$traceEmissionCount$1(new Ref$IntRef(), LazyKt__LazyJVMKt.lazy(new FlowTracing$$ExternalSyntheticLambda0(str, false)), null));
    }

    public static FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 tracedConflatedCallbackFlow(String str, Function2 function2) {
        return new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.buffer$default(FlowKt.callbackFlow(new FlowTracing$tracedConflatedCallbackFlow$1(str, function2, null)), -1, 2), new FlowTracing$traceEmissionCount$1(new Ref$IntRef(), LazyKt__LazyJVMKt.lazy(new FlowTracing$$ExternalSyntheticLambda0(str, true)), null));
    }

    public static FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 traceEmissionCount$default(FlowTracing flowTracing, ChannelFlowTransformLatest channelFlowTransformLatest, final KeyguardQuickAffordancesCombinedViewModel$$ExternalSyntheticLambda0 keyguardQuickAffordancesCombinedViewModel$$ExternalSyntheticLambda0) {
        flowTracing.getClass();
        return new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(channelFlowTransformLatest, new FlowTracing$traceEmissionCount$2(new Ref$IntRef(), LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.app.tracing.FlowTracing$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                FlowTracing flowTracing2 = FlowTracing.INSTANCE;
                return KeyguardQuickAffordancesCombinedViewModel$$ExternalSyntheticLambda0.this.invoke() + "#emissionCount";
            }
        }), null));
    }
}
