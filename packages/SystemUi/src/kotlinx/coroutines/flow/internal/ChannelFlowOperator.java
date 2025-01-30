package kotlinx.coroutines.flow.internal;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.internal.ThreadContextKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class ChannelFlowOperator extends ChannelFlow {
    public final Flow flow;

    public ChannelFlowOperator(Flow flow, CoroutineContext coroutineContext, int i, BufferOverflow bufferOverflow) {
        super(coroutineContext, i, bufferOverflow);
        this.flow = flow;
    }

    @Override // kotlinx.coroutines.flow.internal.ChannelFlow, kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        if (this.capacity == -3) {
            CoroutineContext context = continuation.getContext();
            CoroutineContext plus = context.plus(this.context);
            if (Intrinsics.areEqual(plus, context)) {
                Object flowCollect = flowCollect(flowCollector, continuation);
                return flowCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? flowCollect : Unit.INSTANCE;
            }
            ContinuationInterceptor.Key key = ContinuationInterceptor.Key;
            if (Intrinsics.areEqual(plus.get(key), context.get(key))) {
                CoroutineContext context2 = continuation.getContext();
                if (!(flowCollector instanceof SendingCollector ? true : flowCollector instanceof NopCollector)) {
                    flowCollector = new UndispatchedContextCollector(flowCollector, context2);
                }
                Object withContextUndispatched = ChannelFlowKt.withContextUndispatched(plus, flowCollector, ThreadContextKt.threadContextElements(plus), new ChannelFlowOperator$collectWithContextUndispatched$2(this, null), continuation);
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (withContextUndispatched != coroutineSingletons) {
                    withContextUndispatched = Unit.INSTANCE;
                }
                return withContextUndispatched == coroutineSingletons ? withContextUndispatched : Unit.INSTANCE;
            }
        }
        Object collect = super.collect(flowCollector, continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }

    @Override // kotlinx.coroutines.flow.internal.ChannelFlow
    public final Object collectTo(ProducerScope producerScope, Continuation continuation) {
        Object flowCollect = flowCollect(new SendingCollector(producerScope), continuation);
        return flowCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? flowCollect : Unit.INSTANCE;
    }

    public abstract Object flowCollect(FlowCollector flowCollector, Continuation continuation);

    @Override // kotlinx.coroutines.flow.internal.ChannelFlow
    public final String toString() {
        return this.flow + " -> " + super.toString();
    }
}
