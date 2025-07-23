package kotlinx.coroutines.flow;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.internal.Ref$IntRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class FlowKt__LimitKt$drop$$inlined$unsafeFlow$1 implements Flow {
    public final /* synthetic */ int $count$inlined;
    public final /* synthetic */ Flow $this_drop$inlined;

    public FlowKt__LimitKt$drop$$inlined$unsafeFlow$1(Flow flow, int i) {
        this.$this_drop$inlined = flow;
        this.$count$inlined = i;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object collect = this.$this_drop$inlined.collect(new FlowKt__LimitKt$drop$2$1(new Ref$IntRef(), this.$count$inlined, flowCollector), continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }
}
