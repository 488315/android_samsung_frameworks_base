package kotlinx.coroutines.flow;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot;
import kotlinx.coroutines.flow.internal.FusibleFlow;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class StateFlowImpl extends AbstractSharedFlow implements MutableStateFlow, CancellableFlow, FusibleFlow {
    public final AtomicRef _state;
    public int sequence;

    public StateFlowImpl(Object obj) {
        this._state = AtomicFU.atomic(obj);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x00bf, code lost:
    
        r2 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00c3, code lost:
    
        if (r14.equals(r8) != false) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0126, code lost:
    
        if (r8 == r1) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00ab, code lost:
    
        if (kotlin.Unit.INSTANCE == r1) goto L65;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x00b1 A[Catch: all -> 0x0076, TryCatch #1 {all -> 0x0076, blocks: (B:13:0x00ab, B:15:0x00b1, B:18:0x00b8, B:19:0x00bc, B:23:0x00bf, B:25:0x00e4, B:28:0x00f4, B:30:0x0114, B:31:0x011b, B:36:0x0124, B:38:0x00c5, B:41:0x00cc, B:11:0x0072, B:12:0x009c, B:61:0x0085, B:63:0x0089), top: B:7:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00f4 A[Catch: all -> 0x0076, TryCatch #1 {all -> 0x0076, blocks: (B:13:0x00ab, B:15:0x00b1, B:18:0x00b8, B:19:0x00bc, B:23:0x00bf, B:25:0x00e4, B:28:0x00f4, B:30:0x0114, B:31:0x011b, B:36:0x0124, B:38:0x00c5, B:41:0x00cc, B:11:0x0072, B:12:0x009c, B:61:0x0085, B:63:0x0089), top: B:7:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0024  */
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
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:27:0x00f3 -> B:13:0x00ab). Please report as a decompilation issue!!! */
    @Override // kotlinx.coroutines.flow.Flow
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object collect(kotlinx.coroutines.flow.FlowCollector r13, kotlin.coroutines.Continuation r14) {
        /*
            Method dump skipped, instructions count: 301
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.StateFlowImpl.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
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
