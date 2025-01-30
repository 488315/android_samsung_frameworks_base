package kotlinx.coroutines.flow;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.internal.ChannelFlowOperatorImpl;
import kotlinx.coroutines.flow.internal.FusibleFlow;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ReadonlySharedFlow implements SharedFlow, Flow, FusibleFlow {
    public final /* synthetic */ SharedFlow $$delegate_0;

    public ReadonlySharedFlow(SharedFlow sharedFlow, Job job) {
        this.$$delegate_0 = sharedFlow;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        return this.$$delegate_0.collect(flowCollector, continuation);
    }

    @Override // kotlinx.coroutines.flow.internal.FusibleFlow
    public final Flow fuse(CoroutineContext coroutineContext, int i, BufferOverflow bufferOverflow) {
        Symbol symbol = SharedFlowKt.NO_VALUE;
        return ((i == 0 || i == -3) && bufferOverflow == BufferOverflow.SUSPEND) ? this : new ChannelFlowOperatorImpl(this, coroutineContext, i, bufferOverflow);
    }
}
