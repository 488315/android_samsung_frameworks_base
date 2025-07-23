package kotlinx.coroutines.flow;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.atomicfu.AtomicBoolean;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.TraceBase;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.flow.internal.ChannelFlow;
import kotlinx.coroutines.flow.internal.SendingCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ChannelAsFlow extends ChannelFlow {
    public final ReceiveChannel channel;
    public final boolean consume;
    public final AtomicBoolean consumed;

    public /* synthetic */ ChannelAsFlow(ReceiveChannel receiveChannel, boolean z, CoroutineContext coroutineContext, int i, BufferOverflow bufferOverflow, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(receiveChannel, z, (i2 & 4) != 0 ? EmptyCoroutineContext.INSTANCE : coroutineContext, (i2 & 8) != 0 ? -3 : i, (i2 & 16) != 0 ? BufferOverflow.SUSPEND : bufferOverflow);
    }

    @Override // kotlinx.coroutines.flow.internal.ChannelFlow
    public final String additionalToStringProps() {
        return "channel=" + this.channel;
    }

    @Override // kotlinx.coroutines.flow.internal.ChannelFlow, kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        if (this.capacity != -3) {
            Object collect = super.collect(flowCollector, continuation);
            return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
        }
        markConsumed();
        Object emitAllImpl$FlowKt__ChannelsKt = FlowKt__ChannelsKt.emitAllImpl$FlowKt__ChannelsKt(flowCollector, this.channel, this.consume, continuation);
        return emitAllImpl$FlowKt__ChannelsKt == CoroutineSingletons.COROUTINE_SUSPENDED ? emitAllImpl$FlowKt__ChannelsKt : Unit.INSTANCE;
    }

    @Override // kotlinx.coroutines.flow.internal.ChannelFlow
    public final Object collectTo(ProducerScope producerScope, Continuation continuation) {
        Object emitAllImpl$FlowKt__ChannelsKt = FlowKt__ChannelsKt.emitAllImpl$FlowKt__ChannelsKt(new SendingCollector(producerScope), this.channel, this.consume, continuation);
        return emitAllImpl$FlowKt__ChannelsKt == CoroutineSingletons.COROUTINE_SUSPENDED ? emitAllImpl$FlowKt__ChannelsKt : Unit.INSTANCE;
    }

    @Override // kotlinx.coroutines.flow.internal.ChannelFlow
    public final ChannelFlow create(CoroutineContext coroutineContext, int i, BufferOverflow bufferOverflow) {
        return new ChannelAsFlow(this.channel, this.consume, coroutineContext, i, bufferOverflow);
    }

    @Override // kotlinx.coroutines.flow.internal.ChannelFlow
    public final Flow dropChannelOperators() {
        return new ChannelAsFlow(this.channel, this.consume, null, 0, null, 28, null);
    }

    public final void markConsumed() {
        if (this.consume) {
            AtomicBoolean atomicBoolean = this.consumed;
            atomicBoolean.getClass();
            int andSet = AtomicBoolean.FU.getAndSet(atomicBoolean, 1);
            TraceBase.None none = TraceBase.None.INSTANCE;
            TraceBase traceBase = atomicBoolean.trace;
            if (traceBase != none) {
                traceBase.getClass();
            }
            if (andSet == 1) {
                throw new IllegalStateException("ReceiveChannel.consumeAsFlow can be collected just once");
            }
        }
    }

    @Override // kotlinx.coroutines.flow.internal.ChannelFlow
    public final ReceiveChannel produceImpl(CoroutineScope coroutineScope) {
        markConsumed();
        return this.capacity == -3 ? this.channel : super.produceImpl(coroutineScope);
    }

    public ChannelAsFlow(ReceiveChannel receiveChannel, boolean z, CoroutineContext coroutineContext, int i, BufferOverflow bufferOverflow) {
        super(coroutineContext, i, bufferOverflow);
        this.channel = receiveChannel;
        this.consume = z;
        this.consumed = AtomicFU.atomic(false);
    }
}
