package kotlinx.coroutines.flow;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot;
import kotlinx.coroutines.flow.internal.FusibleFlow;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;
import kotlinx.coroutines.internal.Symbol;

/* loaded from: classes4.dex */
public final class StateFlowImpl extends AbstractSharedFlow implements MutableStateFlow, CancellableFlow, FusibleFlow {
    public final AtomicRef _state;
    public int sequence;

    /* renamed from: kotlinx.coroutines.flow.StateFlowImpl$collect$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return StateFlowImpl.this.collect(null, this);
        }
    }

    public StateFlowImpl(Object obj) {
        this._state = AtomicFU.atomic(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00b1 A[Catch: all -> 0x0076, TryCatch #1 {all -> 0x0076, blocks: (B:35:0x00ab, B:37:0x00b1, B:40:0x00b8, B:41:0x00bc, B:43:0x00bf, B:54:0x00e4, B:57:0x00f4, B:59:0x0114, B:60:0x011b, B:63:0x0124, B:45:0x00c5, B:49:0x00cc, B:24:0x0072, B:34:0x009c, B:29:0x0085, B:31:0x0089), top: B:69:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00bf A[Catch: all -> 0x0076, TryCatch #1 {all -> 0x0076, blocks: (B:35:0x00ab, B:37:0x00b1, B:40:0x00b8, B:41:0x00bc, B:43:0x00bf, B:54:0x00e4, B:57:0x00f4, B:59:0x0114, B:60:0x011b, B:63:0x0124, B:45:0x00c5, B:49:0x00cc, B:24:0x0072, B:34:0x009c, B:29:0x0085, B:31:0x0089), top: B:69:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00f4 A[Catch: all -> 0x0076, TryCatch #1 {all -> 0x0076, blocks: (B:35:0x00ab, B:37:0x00b1, B:40:0x00b8, B:41:0x00bc, B:43:0x00bf, B:54:0x00e4, B:57:0x00f4, B:59:0x0114, B:60:0x011b, B:63:0x0124, B:45:0x00c5, B:49:0x00cc, B:24:0x0072, B:34:0x009c, B:29:0x0085, B:31:0x0089), top: B:69:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r2v0, types: [int] */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v12, types: [kotlinx.coroutines.flow.StateFlowSlot] */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v2, types: [kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot] */
    /* JADX WARN: Type inference failed for: r2v6, types: [java.lang.Object, kotlinx.coroutines.flow.StateFlowSlot] */
    /* JADX WARN: Type inference failed for: r2v7, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r8v0, types: [kotlinx.coroutines.flow.internal.AbstractSharedFlow] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:56:0x00f3 -> B:35:0x00ab). Please report as a decompilation issue!!! */
    @Override // kotlinx.coroutines.flow.Flow
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object collect(FlowCollector flowCollector, Continuation continuation) throws Throwable {
        AnonymousClass1 anonymousClass1;
        ?? r8;
        Throwable th;
        Job job;
        Object obj;
        StateFlowImpl stateFlowImpl;
        FlowCollector flowCollector2;
        StateFlowSlot stateFlowSlot;
        Object obj2;
        Object andSet;
        Object obj3;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object obj4 = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        ?? r2 = anonymousClass1.label;
        try {
            if (r2 == 0) {
                ResultKt.throwOnFailure(obj4);
                StateFlowSlot stateFlowSlot2 = (StateFlowSlot) allocateSlot();
                r2 = stateFlowSlot2;
                if (flowCollector instanceof SubscribedFlowCollector) {
                    anonymousClass1.L$0 = this;
                    anonymousClass1.L$1 = flowCollector;
                    anonymousClass1.L$2 = stateFlowSlot2;
                    anonymousClass1.label = 1;
                    r2 = stateFlowSlot2;
                    if (((SubscribedFlowCollector) flowCollector).onSubscription(anonymousClass1) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
            } else if (r2 != 1) {
                try {
                    if (r2 == 2) {
                        obj = anonymousClass1.L$4;
                        job = (Job) anonymousClass1.L$3;
                        StateFlowSlot stateFlowSlot3 = (StateFlowSlot) anonymousClass1.L$2;
                        flowCollector2 = (FlowCollector) anonymousClass1.L$1;
                        stateFlowImpl = (StateFlowImpl) anonymousClass1.L$0;
                        ResultKt.throwOnFailure(obj4);
                        stateFlowSlot = stateFlowSlot3;
                        obj2 = obj;
                        this = stateFlowImpl;
                        r2 = stateFlowSlot;
                        AtomicReference atomicReference = r2._state;
                        Symbol symbol = StateFlowKt.NONE;
                        andSet = atomicReference.getAndSet(symbol);
                        andSet.getClass();
                        if (andSet != StateFlowKt.PENDING) {
                        }
                        Object obj5 = this._state.value;
                        if (job != null) {
                        }
                        if (obj2 != null) {
                        }
                        if (obj5 != NullSurrogateKt.NULL) {
                        }
                        anonymousClass1.L$0 = this;
                        anonymousClass1.L$1 = flowCollector2;
                        anonymousClass1.L$2 = r2;
                        anonymousClass1.L$3 = job;
                        anonymousClass1.L$4 = obj5;
                        anonymousClass1.label = 2;
                        if (flowCollector2.emit(obj3, anonymousClass1) != coroutineSingletons) {
                        }
                    } else {
                        if (r2 != 3) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        Object obj6 = anonymousClass1.L$4;
                        job = (Job) anonymousClass1.L$3;
                        r2 = (StateFlowSlot) anonymousClass1.L$2;
                        flowCollector2 = (FlowCollector) anonymousClass1.L$1;
                        StateFlowImpl stateFlowImpl2 = (StateFlowImpl) anonymousClass1.L$0;
                        ResultKt.throwOnFailure(obj4);
                        obj2 = obj6;
                        this = stateFlowImpl2;
                        Object obj52 = this._state.value;
                        if (job != null && !job.isActive()) {
                            throw job.getCancellationException();
                        }
                        if (obj2 != null) {
                            r2 = r2;
                            if (obj2.equals(obj52)) {
                                AtomicReference atomicReference2 = r2._state;
                                Symbol symbol2 = StateFlowKt.NONE;
                                andSet = atomicReference2.getAndSet(symbol2);
                                andSet.getClass();
                                if (andSet != StateFlowKt.PENDING) {
                                    anonymousClass1.L$0 = this;
                                    anonymousClass1.L$1 = flowCollector2;
                                    anonymousClass1.L$2 = r2;
                                    anonymousClass1.L$3 = job;
                                    anonymousClass1.L$4 = obj2;
                                    anonymousClass1.label = 3;
                                    CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(anonymousClass1), 1);
                                    cancellableContinuationImpl.initCancellability();
                                    if (!r2._state.compareAndSet(symbol2, cancellableContinuationImpl)) {
                                        int i2 = Result.$r8$clinit;
                                        cancellableContinuationImpl.resumeWith(Unit.INSTANCE);
                                    }
                                    Object result = cancellableContinuationImpl.getResult();
                                    if (result != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                        if (Unit.INSTANCE != coroutineSingletons) {
                                        }
                                    } else if (result != coroutineSingletons) {
                                    }
                                }
                                Object obj522 = this._state.value;
                                if (job != null) {
                                    throw job.getCancellationException();
                                }
                                if (obj2 != null) {
                                }
                            }
                            return coroutineSingletons;
                        }
                        obj3 = obj522 != NullSurrogateKt.NULL ? null : obj522;
                        anonymousClass1.L$0 = this;
                        anonymousClass1.L$1 = flowCollector2;
                        anonymousClass1.L$2 = r2;
                        anonymousClass1.L$3 = job;
                        anonymousClass1.L$4 = obj522;
                        anonymousClass1.label = 2;
                        if (flowCollector2.emit(obj3, anonymousClass1) != coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        stateFlowImpl = this;
                        obj = obj522;
                        stateFlowSlot = r2;
                        obj2 = obj;
                        this = stateFlowImpl;
                        r2 = stateFlowSlot;
                        AtomicReference atomicReference22 = r2._state;
                        Symbol symbol22 = StateFlowKt.NONE;
                        andSet = atomicReference22.getAndSet(symbol22);
                        andSet.getClass();
                        if (andSet != StateFlowKt.PENDING) {
                        }
                        Object obj5222 = this._state.value;
                        if (job != null) {
                        }
                        if (obj2 != null) {
                        }
                        if (obj5222 != NullSurrogateKt.NULL) {
                        }
                        anonymousClass1.L$0 = this;
                        anonymousClass1.L$1 = flowCollector2;
                        anonymousClass1.L$2 = r2;
                        anonymousClass1.L$3 = job;
                        anonymousClass1.L$4 = obj5222;
                        anonymousClass1.label = 2;
                        if (flowCollector2.emit(obj3, anonymousClass1) != coroutineSingletons) {
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    r8.freeSlot(r2);
                    throw th;
                }
            } else {
                StateFlowSlot stateFlowSlot4 = (StateFlowSlot) anonymousClass1.L$2;
                flowCollector = (FlowCollector) anonymousClass1.L$1;
                this = (StateFlowImpl) anonymousClass1.L$0;
                ResultKt.throwOnFailure(obj4);
                r2 = stateFlowSlot4;
            }
            flowCollector2 = flowCollector;
            job = (Job) anonymousClass1.getContext().get(Job.Key);
            obj2 = null;
            Object obj52222 = this._state.value;
            if (job != null) {
            }
            if (obj2 != null) {
            }
            if (obj52222 != NullSurrogateKt.NULL) {
            }
            anonymousClass1.L$0 = this;
            anonymousClass1.L$1 = flowCollector2;
            anonymousClass1.L$2 = r2;
            anonymousClass1.L$3 = job;
            anonymousClass1.L$4 = obj52222;
            anonymousClass1.label = 2;
            if (flowCollector2.emit(obj3, anonymousClass1) != coroutineSingletons) {
            }
        } catch (Throwable th3) {
            r8 = this;
            th = th3;
        }
    }

    @Override // kotlinx.coroutines.flow.MutableStateFlow
    public final boolean compareAndSet(Object obj, Object obj2) {
        if (obj == null) {
            obj = NullSurrogateKt.NULL;
        }
        if (obj2 == null) {
            obj2 = NullSurrogateKt.NULL;
        }
        return updateState(obj, obj2);
    }

    @Override // kotlinx.coroutines.flow.internal.AbstractSharedFlow
    public final AbstractSharedFlowSlot createSlot() {
        return new StateFlowSlot();
    }

    @Override // kotlinx.coroutines.flow.internal.AbstractSharedFlow
    public final AbstractSharedFlowSlot[] createSlotArray() {
        return new StateFlowSlot[2];
    }

    @Override // kotlinx.coroutines.flow.MutableSharedFlow, kotlinx.coroutines.flow.FlowCollector
    public final Object emit(Object obj, Continuation continuation) {
        setValue(obj);
        return Unit.INSTANCE;
    }

    @Override // kotlinx.coroutines.flow.internal.FusibleFlow
    public final Flow fuse(CoroutineContext coroutineContext, int i, BufferOverflow bufferOverflow) {
        Symbol symbol = StateFlowKt.NONE;
        return (((i < 0 || i >= 2) && i != -2) || bufferOverflow != BufferOverflow.DROP_OLDEST) ? SharedFlowKt.fuseSharedFlow(this, coroutineContext, i, bufferOverflow) : this;
    }

    @Override // kotlinx.coroutines.flow.SharedFlow
    public final List getReplayCache() {
        return Collections.singletonList(getValue());
    }

    @Override // kotlinx.coroutines.flow.MutableStateFlow, kotlinx.coroutines.flow.StateFlow
    public final Object getValue() {
        Symbol symbol = NullSurrogateKt.NULL;
        Object obj = this._state.value;
        if (obj == symbol) {
            return null;
        }
        return obj;
    }

    @Override // kotlinx.coroutines.flow.MutableSharedFlow
    public final void resetReplayCache() {
        throw new UnsupportedOperationException("MutableStateFlow.resetReplayCache is not supported");
    }

    @Override // kotlinx.coroutines.flow.MutableStateFlow
    public final void setValue(Object obj) {
        if (obj == null) {
            obj = NullSurrogateKt.NULL;
        }
        updateState(null, obj);
    }

    @Override // kotlinx.coroutines.flow.MutableSharedFlow
    public final boolean tryEmit(Object obj) {
        setValue(obj);
        return true;
    }

    public final boolean updateState(Object obj, Object obj2) {
        int i;
        AbstractSharedFlowSlot[] abstractSharedFlowSlotArr;
        Symbol symbol;
        synchronized (this) {
            Object obj3 = this._state.value;
            if (obj != null && !Intrinsics.areEqual(obj3, obj)) {
                return false;
            }
            if (Intrinsics.areEqual(obj3, obj2)) {
                return true;
            }
            this._state.setValue(obj2);
            int i2 = this.sequence;
            if ((i2 & 1) != 0) {
                this.sequence = i2 + 2;
                return true;
            }
            int i3 = i2 + 1;
            this.sequence = i3;
            AbstractSharedFlowSlot[] abstractSharedFlowSlotArr2 = this.slots;
            Unit unit = Unit.INSTANCE;
            while (true) {
                StateFlowSlot[] stateFlowSlotArr = (StateFlowSlot[]) abstractSharedFlowSlotArr2;
                if (stateFlowSlotArr != null) {
                    for (StateFlowSlot stateFlowSlot : stateFlowSlotArr) {
                        if (stateFlowSlot != null) {
                            AtomicReference atomicReference = stateFlowSlot._state;
                            while (true) {
                                Object obj4 = atomicReference.get();
                                if (obj4 != null && obj4 != (symbol = StateFlowKt.PENDING)) {
                                    Symbol symbol2 = StateFlowKt.NONE;
                                    if (obj4 != symbol2) {
                                        if (stateFlowSlot._state.compareAndSet(obj4, symbol2)) {
                                            int i4 = Result.$r8$clinit;
                                            ((CancellableContinuationImpl) obj4).resumeWith(Unit.INSTANCE);
                                            break;
                                        }
                                    } else {
                                        if (stateFlowSlot._state.compareAndSet(obj4, symbol)) {
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                synchronized (this) {
                    i = this.sequence;
                    if (i == i3) {
                        this.sequence = i3 + 1;
                        return true;
                    }
                    abstractSharedFlowSlotArr = this.slots;
                    Unit unit2 = Unit.INSTANCE;
                }
                abstractSharedFlowSlotArr2 = abstractSharedFlowSlotArr;
                i3 = i;
            }
        }
    }
}
