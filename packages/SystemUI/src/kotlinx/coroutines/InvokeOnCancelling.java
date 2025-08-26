package kotlinx.coroutines;

import kotlin.jvm.functions.Function1;
import kotlinx.atomicfu.AtomicBoolean;
import kotlinx.atomicfu.AtomicFU;

/* loaded from: classes4.dex */
public final class InvokeOnCancelling extends JobNode {
    public final AtomicBoolean _invoked = AtomicFU.atomic(false);
    public final Function1 handler;

    public InvokeOnCancelling(Function1 function1) {
        this.handler = function1;
    }

    @Override // kotlinx.coroutines.JobNode
    public final boolean getOnCancelling() {
        return true;
    }

    @Override // kotlinx.coroutines.JobNode
    public final void invoke(Throwable th) {
        if (this._invoked.compareAndSet()) {
            this.handler.mo781invoke(th);
        }
    }
}
