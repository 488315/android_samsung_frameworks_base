package kotlinx.coroutines;

import kotlin.jvm.functions.Function1;

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
        this.handler.mo781invoke(th);
    }
}
