package kotlinx.coroutines;

import kotlin.jvm.functions.Function1;
import kotlinx.atomicfu.AtomicBoolean;
import kotlinx.atomicfu.AtomicFU;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            this.handler.mo779invoke(th);
        }
    }
}
