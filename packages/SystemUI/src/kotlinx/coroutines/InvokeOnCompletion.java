package kotlinx.coroutines;

import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class InvokeOnCompletion extends JobNode {
    public final Function1 handler;

    public InvokeOnCompletion(Function1 function1) {
        this.handler = function1;
    }

    @Override // kotlinx.coroutines.JobNode
    public final boolean getOnCancelling() {
        return false;
    }

    @Override // kotlinx.coroutines.JobNode
    public final void invoke(Throwable th) {
        this.handler.mo779invoke(th);
    }
}
