package kotlinx.coroutines.flow;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$BooleanRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class FlowKt__LimitKt$dropWhile$$inlined$unsafeFlow$1 implements Flow {
    public final /* synthetic */ Function2 $predicate$inlined;
    public final /* synthetic */ Flow $this_dropWhile$inlined;

    public FlowKt__LimitKt$dropWhile$$inlined$unsafeFlow$1(Flow flow, Function2 function2) {
        this.$this_dropWhile$inlined = flow;
        this.$predicate$inlined = function2;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object collect = this.$this_dropWhile$inlined.collect(new FlowKt__LimitKt$dropWhile$1$1(new Ref$BooleanRef(), flowCollector, this.$predicate$inlined), continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }
}
