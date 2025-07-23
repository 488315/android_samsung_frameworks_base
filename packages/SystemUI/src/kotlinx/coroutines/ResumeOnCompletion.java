package kotlinx.coroutines;

import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ResumeOnCompletion extends JobNode {
    public final Continuation continuation;

    public ResumeOnCompletion(Continuation continuation) {
        this.continuation = continuation;
    }

    @Override // kotlinx.coroutines.JobNode
    public final boolean getOnCancelling() {
        return false;
    }

    @Override // kotlinx.coroutines.JobNode
    public final void invoke(Throwable th) {
        int i = Result.$r8$clinit;
        this.continuation.resumeWith(Unit.INSTANCE);
    }
}
