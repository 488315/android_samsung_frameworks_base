package kotlinx.coroutines.flow.internal;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.intrinsics.UndispatchedKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class CombineKt {
    public static final Object combineInternal(Flow[] flowArr, Function0 function0, Function3 function3, FlowCollector flowCollector, Continuation continuation) {
        CombineKt$combineInternal$2 combineKt$combineInternal$2 = new CombineKt$combineInternal$2(flowArr, function0, function3, flowCollector, null);
        FlowCoroutine flowCoroutine = new FlowCoroutine(continuation.getContext(), continuation);
        Object startUndispatchedOrReturn = UndispatchedKt.startUndispatchedOrReturn(flowCoroutine, flowCoroutine, combineKt$combineInternal$2);
        return startUndispatchedOrReturn == CoroutineSingletons.COROUTINE_SUSPENDED ? startUndispatchedOrReturn : Unit.INSTANCE;
    }
}
