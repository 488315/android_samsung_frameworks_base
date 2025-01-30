package kotlinx.coroutines.flow.internal;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.internal.ThreadContextKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class UndispatchedContextCollector implements FlowCollector {
    public final Object countOrElement;
    public final CoroutineContext emitContext;
    public final Function2 emitRef;

    public UndispatchedContextCollector(FlowCollector flowCollector, CoroutineContext coroutineContext) {
        this.emitContext = coroutineContext;
        this.countOrElement = ThreadContextKt.threadContextElements(coroutineContext);
        this.emitRef = new UndispatchedContextCollector$emitRef$1(flowCollector, null);
    }

    @Override // kotlinx.coroutines.flow.FlowCollector
    public final Object emit(Object obj, Continuation continuation) {
        Object withContextUndispatched = ChannelFlowKt.withContextUndispatched(this.emitContext, obj, this.countOrElement, this.emitRef, continuation);
        return withContextUndispatched == CoroutineSingletons.COROUTINE_SUSPENDED ? withContextUndispatched : Unit.INSTANCE;
    }
}
