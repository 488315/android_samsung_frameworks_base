package kotlinx.coroutines.flow;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class DistinctFlowImpl implements Flow {
    public final Function2 areEquivalent;
    public final Function1 keySelector;
    public final Flow upstream;

    public DistinctFlowImpl(Flow flow, Function1 function1, Function2 function2) {
        this.upstream = flow;
        this.keySelector = function1;
        this.areEquivalent = function2;
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [T, kotlinx.coroutines.internal.Symbol] */
    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ref$ObjectRef.element = NullSurrogateKt.NULL;
        Object collect = this.upstream.collect(new DistinctFlowImpl$collect$2(this, ref$ObjectRef, flowCollector), continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }
}
