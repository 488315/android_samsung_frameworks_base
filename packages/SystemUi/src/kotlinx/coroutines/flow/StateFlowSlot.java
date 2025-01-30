package kotlinx.coroutines.flow;

import kotlin.coroutines.Continuation;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.flow.internal.AbstractSharedFlow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowKt;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class StateFlowSlot extends AbstractSharedFlowSlot {
    public final AtomicRef _state = AtomicFU.atomic((Object) null);

    @Override // kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot
    public final boolean allocateLocked(AbstractSharedFlow abstractSharedFlow) {
        if (this._state.value != null) {
            return false;
        }
        this._state.setValue(StateFlowKt.NONE);
        return true;
    }

    @Override // kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot
    public final Continuation[] freeLocked(AbstractSharedFlow abstractSharedFlow) {
        this._state.setValue(null);
        return AbstractSharedFlowKt.EMPTY_RESUMES;
    }
}
