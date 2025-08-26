package kotlinx.coroutines.flow;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;

/* loaded from: classes4.dex */
public final class FlowKt__MergeKt$flattenConcat$$inlined$unsafeFlow$1 implements Flow {
    public final /* synthetic */ Flow $this_flattenConcat$inlined;

    public FlowKt__MergeKt$flattenConcat$$inlined$unsafeFlow$1(Flow flow) {
        this.$this_flattenConcat$inlined = flow;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object objCollect = this.$this_flattenConcat$inlined.collect(new FlowKt__MergeKt$flattenConcat$1$1(flowCollector), continuation);
        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
    }
}
