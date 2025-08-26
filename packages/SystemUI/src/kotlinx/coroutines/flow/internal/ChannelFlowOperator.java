package kotlinx.coroutines.flow.internal;

import java.io.IOException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineContextKt;
import kotlinx.coroutines.CoroutineContextKt$$ExternalSyntheticLambda0;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.internal.ThreadContextKt;

/* loaded from: classes4.dex */
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
            Boolean bool = Boolean.FALSE;
            CoroutineContextKt$$ExternalSyntheticLambda0 coroutineContextKt$$ExternalSyntheticLambda0 = new CoroutineContextKt$$ExternalSyntheticLambda0(0);
            CoroutineContext coroutineContext = this.context;
            CoroutineContext coroutineContextPlus = !((Boolean) coroutineContext.fold(bool, coroutineContextKt$$ExternalSyntheticLambda0)).booleanValue() ? context.plus(coroutineContext) : CoroutineContextKt.foldCopies(context, coroutineContext, false);
            if (Intrinsics.areEqual(coroutineContextPlus, context)) {
                Object objFlowCollect = flowCollect(flowCollector, continuation);
                return objFlowCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objFlowCollect : Unit.INSTANCE;
            }
            ContinuationInterceptor.Key key = ContinuationInterceptor.Key;
            if (Intrinsics.areEqual(coroutineContextPlus.get(key), context.get(key))) {
                CoroutineContext context2 = continuation.getContext();
                if (!(flowCollector instanceof SendingCollector) && !(flowCollector instanceof NopCollector)) {
                    flowCollector = new UndispatchedContextCollector(flowCollector, context2);
                }
                Object objWithContextUndispatched = ChannelFlowKt.withContextUndispatched(coroutineContextPlus, flowCollector, ThreadContextKt.threadContextElements(coroutineContextPlus), new ChannelFlowOperator$collectWithContextUndispatched$2(this, null), continuation);
                return objWithContextUndispatched == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContextUndispatched : Unit.INSTANCE;
            }
        }
        Object objCollect = super.collect(flowCollector, continuation);
        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
    }

    @Override // kotlinx.coroutines.flow.internal.ChannelFlow
    public final Object collectTo(ProducerScope producerScope, Continuation continuation) {
        Object objFlowCollect = flowCollect(new SendingCollector(producerScope), continuation);
        return objFlowCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objFlowCollect : Unit.INSTANCE;
    }

    public abstract Object flowCollect(FlowCollector flowCollector, Continuation continuation);

    @Override // kotlinx.coroutines.flow.internal.ChannelFlow
    public final String toString() throws IOException {
        return this.flow + " -> " + super.toString();
    }
}
