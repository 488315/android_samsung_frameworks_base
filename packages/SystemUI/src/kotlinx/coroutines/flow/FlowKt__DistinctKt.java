package kotlinx.coroutines.flow;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract /* synthetic */ class FlowKt__DistinctKt {
    public static final FlowKt__DistinctKt$$ExternalSyntheticLambda0 defaultKeySelector = new FlowKt__DistinctKt$$ExternalSyntheticLambda0();
    public static final FlowKt__DistinctKt$$ExternalSyntheticLambda1 defaultAreEquivalent = new FlowKt__DistinctKt$$ExternalSyntheticLambda1();

    public static final DistinctFlowImpl distinctUntilChangedBy$FlowKt__DistinctKt(Flow flow, Function1 function1, Function2 function2) {
        if (flow instanceof DistinctFlowImpl) {
            DistinctFlowImpl distinctFlowImpl = (DistinctFlowImpl) flow;
            if (distinctFlowImpl.keySelector == function1 && distinctFlowImpl.areEquivalent == function2) {
                return (DistinctFlowImpl) flow;
            }
        }
        return new DistinctFlowImpl(flow, function1, function2);
    }
}
