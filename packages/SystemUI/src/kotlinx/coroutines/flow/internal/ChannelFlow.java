package kotlinx.coroutines.flow.internal;

import java.util.ArrayList;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineContextKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.channels.ProducerCoroutine;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class ChannelFlow implements FusibleFlow {
    public final int capacity;
    public final CoroutineContext context;
    public final BufferOverflow onBufferOverflow;

    public ChannelFlow(CoroutineContext coroutineContext, int i, BufferOverflow bufferOverflow) {
        this.context = coroutineContext;
        this.capacity = i;
        this.onBufferOverflow = bufferOverflow;
    }

    public String additionalToStringProps() {
        return null;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new ChannelFlow$collect$2(flowCollector, this, null), continuation);
        return coroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? coroutineScope : Unit.INSTANCE;
    }

    public abstract Object collectTo(ProducerScope producerScope, Continuation continuation);

    public abstract ChannelFlow create(CoroutineContext coroutineContext, int i, BufferOverflow bufferOverflow);

    public Flow dropChannelOperators() {
        return null;
    }

    @Override // kotlinx.coroutines.flow.internal.FusibleFlow
    public final Flow fuse(CoroutineContext coroutineContext, int i, BufferOverflow bufferOverflow) {
        CoroutineContext coroutineContext2 = this.context;
        CoroutineContext plus = coroutineContext.plus(coroutineContext2);
        BufferOverflow bufferOverflow2 = BufferOverflow.SUSPEND;
        BufferOverflow bufferOverflow3 = this.onBufferOverflow;
        int i2 = this.capacity;
        if (bufferOverflow == bufferOverflow2) {
            if (i2 != -3) {
                if (i != -3) {
                    if (i2 != -2) {
                        if (i != -2) {
                            i += i2;
                            if (i < 0) {
                                i = Integer.MAX_VALUE;
                            }
                        }
                    }
                }
                i = i2;
            }
            bufferOverflow = bufferOverflow3;
        }
        return (Intrinsics.areEqual(plus, coroutineContext2) && i == i2 && bufferOverflow == bufferOverflow3) ? this : create(plus, i, bufferOverflow);
    }

    public ReceiveChannel produceImpl(CoroutineScope coroutineScope) {
        int i = this.capacity;
        if (i == -3) {
            i = -2;
        }
        CoroutineStart coroutineStart = CoroutineStart.ATOMIC;
        Function2 channelFlow$collectToFun$1 = new ChannelFlow$collectToFun$1(this, null);
        ProducerCoroutine producerCoroutine = new ProducerCoroutine(CoroutineContextKt.newCoroutineContext(coroutineScope, this.context), ChannelKt.Channel$default(i, this.onBufferOverflow, null, 4));
        producerCoroutine.start(coroutineStart, producerCoroutine, channelFlow$collectToFun$1);
        return producerCoroutine;
    }

    public String toString() {
        ArrayList arrayList = new ArrayList(4);
        String additionalToStringProps = additionalToStringProps();
        if (additionalToStringProps != null) {
            arrayList.add(additionalToStringProps);
        }
        EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
        CoroutineContext coroutineContext = this.context;
        if (coroutineContext != emptyCoroutineContext) {
            arrayList.add("context=" + coroutineContext);
        }
        int i = this.capacity;
        if (i != -3) {
            arrayList.add("capacity=" + i);
        }
        BufferOverflow bufferOverflow = BufferOverflow.SUSPEND;
        BufferOverflow bufferOverflow2 = this.onBufferOverflow;
        if (bufferOverflow2 != bufferOverflow) {
            arrayList.add("onBufferOverflow=" + bufferOverflow2);
        }
        return getClass().getSimpleName() + "[" + CollectionsKt___CollectionsKt.joinToString$default(arrayList, ", ", null, null, null, 62) + "]";
    }
}
