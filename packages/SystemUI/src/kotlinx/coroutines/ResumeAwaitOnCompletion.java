package kotlinx.coroutines;

import kotlin.Result;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ResumeAwaitOnCompletion extends JobNode {
    public final CancellableContinuationImpl continuation;

    public ResumeAwaitOnCompletion(CancellableContinuationImpl cancellableContinuationImpl) {
        this.continuation = cancellableContinuationImpl;
    }

    @Override // kotlinx.coroutines.JobNode
    public final boolean getOnCancelling() {
        return false;
    }

    @Override // kotlinx.coroutines.JobNode
    public final void invoke(Throwable th) {
        JobSupport jobSupport = this.job;
        if (jobSupport == null) {
            jobSupport = null;
        }
        Object obj = jobSupport._state.value;
        if (obj instanceof CompletedExceptionally) {
            CancellableContinuationImpl cancellableContinuationImpl = this.continuation;
            int i = Result.$r8$clinit;
            cancellableContinuationImpl.resumeWith(new Result.Failure(((CompletedExceptionally) obj).cause));
        } else {
            CancellableContinuationImpl cancellableContinuationImpl2 = this.continuation;
            int i2 = Result.$r8$clinit;
            cancellableContinuationImpl2.resumeWith(JobSupportKt.unboxState(obj));
        }
    }
}
