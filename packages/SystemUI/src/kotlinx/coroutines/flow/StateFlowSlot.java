package kotlinx.coroutines.flow;

import java.util.concurrent.atomic.AtomicReference;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.internal.AbstractSharedFlow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowKt;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class StateFlowSlot extends AbstractSharedFlowSlot {
    public final AtomicReference _state = new AtomicReference(null);

    @Override // kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot
    public final boolean allocateLocked(AbstractSharedFlow abstractSharedFlow) {
        if (this._state.get() != null) {
            return false;
        }
        this._state.set(StateFlowKt.NONE);
        return true;
    }

    @Override // kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot
    public final Continuation[] freeLocked(AbstractSharedFlow abstractSharedFlow) {
        this._state.set(null);
        return AbstractSharedFlowKt.EMPTY_RESUMES;
    }
}
