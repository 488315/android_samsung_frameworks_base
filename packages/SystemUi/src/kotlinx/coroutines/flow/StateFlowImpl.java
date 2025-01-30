package kotlinx.coroutines.flow;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobSupport;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot;
import kotlinx.coroutines.flow.internal.ChannelFlowOperatorImpl;
import kotlinx.coroutines.flow.internal.FusibleFlow;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class StateFlowImpl extends AbstractSharedFlow implements MutableStateFlow, Flow, FusibleFlow {
    public final AtomicRef _state;
    public int sequence;

    public StateFlowImpl(Object obj) {
        this._state = AtomicFU.atomic(obj);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x00c4, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r13, r8) != false) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00aa, code lost:
    
        if (kotlin.Unit.INSTANCE == r1) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00aa, code lost:
    
        if (0 == 0) goto L60;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00b0 A[Catch: all -> 0x0133, TryCatch #1 {all -> 0x0133, blocks: (B:15:0x00aa, B:17:0x00b0, B:20:0x00b7, B:21:0x00bd, B:25:0x00c0, B:27:0x00e7, B:31:0x00fe, B:33:0x011e, B:34:0x0125, B:39:0x012e, B:43:0x00c6, B:46:0x00cd, B:14:0x009c), top: B:13:0x009c }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00df A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /* JADX WARN: Type inference failed for: r2v0, types: [int] */
    /* JADX WARN: Type inference failed for: r2v6, types: [kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot] */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r8v1, types: [kotlinx.coroutines.flow.internal.AbstractSharedFlow] */
    @Override // kotlinx.coroutines.flow.Flow
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        StateFlowImpl$collect$1 stateFlowImpl$collect$1;
        CoroutineSingletons coroutineSingletons;
        ?? r2;
        StateFlowSlot stateFlowSlot;
        StateFlowSlot stateFlowSlot2;
        Throwable th;
        StateFlowImpl stateFlowImpl;
        Job job;
        Object obj;
        StateFlowImpl stateFlowImpl2;
        FlowCollector flowCollector2;
        Job job2;
        Object obj2;
        Object andSet;
        Object obj3;
        try {
            if (continuation instanceof StateFlowImpl$collect$1) {
                stateFlowImpl$collect$1 = (StateFlowImpl$collect$1) continuation;
                int i = stateFlowImpl$collect$1.label;
                if ((i & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                    stateFlowImpl$collect$1.label = i - VideoPlayer.MEDIA_ERROR_SYSTEM;
                    Object obj4 = stateFlowImpl$collect$1.result;
                    coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    r2 = stateFlowImpl$collect$1.label;
                    if (r2 != 0) {
                        ResultKt.throwOnFailure(obj4);
                        stateFlowSlot = (StateFlowSlot) allocateSlot();
                        try {
                            if (flowCollector instanceof SubscribedFlowCollector) {
                                stateFlowImpl$collect$1.L$0 = this;
                                stateFlowImpl$collect$1.L$1 = flowCollector;
                                stateFlowImpl$collect$1.L$2 = stateFlowSlot;
                                stateFlowImpl$collect$1.label = 1;
                                if (((SubscribedFlowCollector) flowCollector).onSubscription(stateFlowImpl$collect$1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            }
                            stateFlowSlot2 = stateFlowSlot;
                        } catch (Throwable th2) {
                            th = th2;
                            stateFlowImpl = this;
                            th = th;
                            ?? r8 = stateFlowImpl;
                            r2 = stateFlowSlot;
                            r8.freeSlot(r2);
                            throw th;
                        }
                    } else if (r2 != 1) {
                        try {
                            if (r2 == 2) {
                                obj = stateFlowImpl$collect$1.L$4;
                                job = (Job) stateFlowImpl$collect$1.L$3;
                                stateFlowSlot2 = (StateFlowSlot) stateFlowImpl$collect$1.L$2;
                                flowCollector2 = (FlowCollector) stateFlowImpl$collect$1.L$1;
                                stateFlowImpl2 = (StateFlowImpl) stateFlowImpl$collect$1.L$0;
                                ResultKt.throwOnFailure(obj4);
                                job2 = job;
                                obj2 = obj;
                                this = stateFlowImpl2;
                                stateFlowSlot2.getClass();
                                Symbol symbol = StateFlowKt.NONE;
                                andSet = stateFlowSlot2._state.getAndSet(symbol);
                                Intrinsics.checkNotNull(andSet);
                                if (andSet == StateFlowKt.PENDING) {
                                }
                                Object obj5 = this._state.value;
                                if (job2 != null) {
                                }
                                if (obj5 == NullSurrogateKt.NULL) {
                                }
                                stateFlowImpl$collect$1.L$0 = this;
                                stateFlowImpl$collect$1.L$1 = flowCollector2;
                                stateFlowImpl$collect$1.L$2 = stateFlowSlot2;
                                stateFlowImpl$collect$1.L$3 = job2;
                                stateFlowImpl$collect$1.L$4 = obj5;
                                stateFlowImpl$collect$1.label = 2;
                                if (flowCollector2.emit(obj3, stateFlowImpl$collect$1) == coroutineSingletons) {
                                }
                            } else {
                                if (r2 != 3) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                Object obj6 = stateFlowImpl$collect$1.L$4;
                                Job job3 = (Job) stateFlowImpl$collect$1.L$3;
                                stateFlowSlot2 = (StateFlowSlot) stateFlowImpl$collect$1.L$2;
                                flowCollector2 = (FlowCollector) stateFlowImpl$collect$1.L$1;
                                StateFlowImpl stateFlowImpl3 = (StateFlowImpl) stateFlowImpl$collect$1.L$0;
                                ResultKt.throwOnFailure(obj4);
                                job2 = job3;
                                obj2 = obj6;
                                this = stateFlowImpl3;
                                Object obj52 = this._state.value;
                                if (job2 != null && !job2.isActive()) {
                                    throw ((JobSupport) job2).getCancellationException();
                                }
                                obj3 = obj52 == NullSurrogateKt.NULL ? null : obj52;
                                stateFlowImpl$collect$1.L$0 = this;
                                stateFlowImpl$collect$1.L$1 = flowCollector2;
                                stateFlowImpl$collect$1.L$2 = stateFlowSlot2;
                                stateFlowImpl$collect$1.L$3 = job2;
                                stateFlowImpl$collect$1.L$4 = obj52;
                                stateFlowImpl$collect$1.label = 2;
                                if (flowCollector2.emit(obj3, stateFlowImpl$collect$1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                                job = job2;
                                stateFlowImpl2 = this;
                                obj = obj52;
                                job2 = job;
                                obj2 = obj;
                                this = stateFlowImpl2;
                                stateFlowSlot2.getClass();
                                Symbol symbol2 = StateFlowKt.NONE;
                                andSet = stateFlowSlot2._state.getAndSet(symbol2);
                                Intrinsics.checkNotNull(andSet);
                                if (andSet == StateFlowKt.PENDING) {
                                    if (1 == 0) {
                                        stateFlowImpl$collect$1.L$0 = this;
                                        stateFlowImpl$collect$1.L$1 = flowCollector2;
                                        stateFlowImpl$collect$1.L$2 = stateFlowSlot2;
                                        stateFlowImpl$collect$1.L$3 = job2;
                                        stateFlowImpl$collect$1.L$4 = obj2;
                                        stateFlowImpl$collect$1.label = 3;
                                        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(stateFlowImpl$collect$1), 1);
                                        cancellableContinuationImpl.initCancellability();
                                        if (!stateFlowSlot2._state.compareAndSet(symbol2, cancellableContinuationImpl)) {
                                            int i2 = Result.$r8$clinit;
                                            cancellableContinuationImpl.resumeWith(Unit.INSTANCE);
                                        }
                                        Object result = cancellableContinuationImpl.getResult();
                                        if (result == CoroutineSingletons.COROUTINE_SUSPENDED) {
                                            if (result == coroutineSingletons) {
                                                return coroutineSingletons;
                                            }
                                        }
                                    }
                                }
                                Object obj522 = this._state.value;
                                if (job2 != null) {
                                    throw ((JobSupport) job2).getCancellationException();
                                }
                                if (obj522 == NullSurrogateKt.NULL) {
                                }
                                stateFlowImpl$collect$1.L$0 = this;
                                stateFlowImpl$collect$1.L$1 = flowCollector2;
                                stateFlowImpl$collect$1.L$2 = stateFlowSlot2;
                                stateFlowImpl$collect$1.L$3 = job2;
                                stateFlowImpl$collect$1.L$4 = obj522;
                                stateFlowImpl$collect$1.label = 2;
                                if (flowCollector2.emit(obj3, stateFlowImpl$collect$1) == coroutineSingletons) {
                                }
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            r8.freeSlot(r2);
                            throw th;
                        }
                    } else {
                        StateFlowSlot stateFlowSlot3 = (StateFlowSlot) stateFlowImpl$collect$1.L$2;
                        flowCollector = (FlowCollector) stateFlowImpl$collect$1.L$1;
                        stateFlowImpl = (StateFlowImpl) stateFlowImpl$collect$1.L$0;
                        try {
                            ResultKt.throwOnFailure(obj4);
                            stateFlowSlot2 = stateFlowSlot3;
                            this = stateFlowImpl;
                        } catch (Throwable th4) {
                            th = th4;
                            stateFlowSlot = stateFlowSlot3;
                            th = th;
                            ?? r82 = stateFlowImpl;
                            r2 = stateFlowSlot;
                            r82.freeSlot(r2);
                            throw th;
                        }
                    }
                    job2 = (Job) stateFlowImpl$collect$1.getContext().get(Job.Key);
                    flowCollector2 = flowCollector;
                    obj2 = null;
                    Object obj5222 = this._state.value;
                    if (job2 != null) {
                    }
                    if (obj5222 == NullSurrogateKt.NULL) {
                    }
                    stateFlowImpl$collect$1.L$0 = this;
                    stateFlowImpl$collect$1.L$1 = flowCollector2;
                    stateFlowImpl$collect$1.L$2 = stateFlowSlot2;
                    stateFlowImpl$collect$1.L$3 = job2;
                    stateFlowImpl$collect$1.L$4 = obj5222;
                    stateFlowImpl$collect$1.label = 2;
                    if (flowCollector2.emit(obj3, stateFlowImpl$collect$1) == coroutineSingletons) {
                    }
                }
            }
            job2 = (Job) stateFlowImpl$collect$1.getContext().get(Job.Key);
            flowCollector2 = flowCollector;
            obj2 = null;
            Object obj52222 = this._state.value;
            if (job2 != null) {
            }
            if (obj52222 == NullSurrogateKt.NULL) {
            }
            stateFlowImpl$collect$1.L$0 = this;
            stateFlowImpl$collect$1.L$1 = flowCollector2;
            stateFlowImpl$collect$1.L$2 = stateFlowSlot2;
            stateFlowImpl$collect$1.L$3 = job2;
            stateFlowImpl$collect$1.L$4 = obj52222;
            stateFlowImpl$collect$1.label = 2;
            if (flowCollector2.emit(obj3, stateFlowImpl$collect$1) == coroutineSingletons) {
            }
        } catch (Throwable th5) {
            th = th5;
            stateFlowSlot = stateFlowSlot2;
            stateFlowImpl = this;
            th = th;
            ?? r822 = stateFlowImpl;
            r2 = stateFlowSlot;
            r822.freeSlot(r2);
            throw th;
        }
        stateFlowImpl$collect$1 = new StateFlowImpl$collect$1(this, continuation);
        Object obj42 = stateFlowImpl$collect$1.result;
        coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        r2 = stateFlowImpl$collect$1.label;
        if (r2 != 0) {
        }
    }

    @Override // kotlinx.coroutines.flow.internal.AbstractSharedFlow
    public final AbstractSharedFlowSlot createSlot() {
        return new StateFlowSlot();
    }

    @Override // kotlinx.coroutines.flow.internal.AbstractSharedFlow
    public final AbstractSharedFlowSlot[] createSlotArray() {
        return new StateFlowSlot[2];
    }

    @Override // kotlinx.coroutines.flow.FlowCollector
    public final Object emit(Object obj, Continuation continuation) {
        setValue(obj);
        return Unit.INSTANCE;
    }

    @Override // kotlinx.coroutines.flow.internal.FusibleFlow
    public final Flow fuse(CoroutineContext coroutineContext, int i, BufferOverflow bufferOverflow) {
        Symbol symbol = StateFlowKt.NONE;
        if (((i >= 0 && i < 2) || i == -2) && bufferOverflow == BufferOverflow.DROP_OLDEST) {
            return this;
        }
        Symbol symbol2 = SharedFlowKt.NO_VALUE;
        return ((i == 0 || i == -3) && bufferOverflow == BufferOverflow.SUSPEND) ? this : new ChannelFlowOperatorImpl(this, coroutineContext, i, bufferOverflow);
    }

    @Override // kotlinx.coroutines.flow.StateFlow
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

    public final void setValue(Object obj) {
        int i;
        AbstractSharedFlowSlot[] abstractSharedFlowSlotArr;
        Symbol symbol;
        if (obj == null) {
            obj = NullSurrogateKt.NULL;
        }
        synchronized (this) {
            if (Intrinsics.areEqual(this._state.value, obj)) {
                return;
            }
            this._state.setValue(obj);
            int i2 = this.sequence;
            if ((i2 & 1) != 0) {
                this.sequence = i2 + 2;
                return;
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
                            AtomicRef atomicRef = stateFlowSlot._state;
                            while (true) {
                                Object obj2 = atomicRef.value;
                                if (obj2 != null && obj2 != (symbol = StateFlowKt.PENDING)) {
                                    Symbol symbol2 = StateFlowKt.NONE;
                                    if (obj2 != symbol2) {
                                        if (stateFlowSlot._state.compareAndSet(obj2, symbol2)) {
                                            int i4 = Result.$r8$clinit;
                                            ((CancellableContinuationImpl) obj2).resumeWith(Unit.INSTANCE);
                                            break;
                                        }
                                    } else {
                                        if (stateFlowSlot._state.compareAndSet(obj2, symbol)) {
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
                        return;
                    } else {
                        abstractSharedFlowSlotArr = this.slots;
                        Unit unit2 = Unit.INSTANCE;
                    }
                }
                abstractSharedFlowSlotArr2 = abstractSharedFlowSlotArr;
                i3 = i;
            }
        }
    }

    @Override // kotlinx.coroutines.flow.MutableSharedFlow
    public final boolean tryEmit(Object obj) {
        setValue(obj);
        return true;
    }
}
