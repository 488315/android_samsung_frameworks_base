package kotlinx.coroutines.flow.internal;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.intrinsics.UndispatchedKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class FlowCoroutineKt$scopedFlow$$inlined$unsafeFlow$1 implements Flow {
    public final /* synthetic */ Function3 $block$inlined;

    public FlowCoroutineKt$scopedFlow$$inlined$unsafeFlow$1(Function3 function3) {
        this.$block$inlined = function3;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        FlowCoroutineKt$scopedFlow$1$1 flowCoroutineKt$scopedFlow$1$1 = new FlowCoroutineKt$scopedFlow$1$1(this.$block$inlined, flowCollector, null);
        FlowCoroutine flowCoroutine = new FlowCoroutine(continuation.getContext(), continuation);
        Object startUndispatchedOrReturn = UndispatchedKt.startUndispatchedOrReturn(flowCoroutine, flowCoroutine, flowCoroutineKt$scopedFlow$1$1);
        return startUndispatchedOrReturn == CoroutineSingletons.COROUTINE_SUSPENDED ? startUndispatchedOrReturn : Unit.INSTANCE;
    }
}
