package kotlinx.coroutines;

import kotlin.Result;
import kotlin.Unit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ResumeAwaitOnCompletion extends JobNode {
    public final CancellableContinuationImpl continuation;

    public ResumeAwaitOnCompletion(CancellableContinuationImpl cancellableContinuationImpl) {
        this.continuation = cancellableContinuationImpl;
    }

    @Override // kotlin.jvm.functions.Function1
    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((Throwable) obj);
        return Unit.INSTANCE;
    }

    @Override // kotlinx.coroutines.CompletionHandlerBase
    public final void invoke(Throwable th) {
        Object m293x8adbf455 = getJob().m293x8adbf455();
        boolean z = m293x8adbf455 instanceof CompletedExceptionally;
        CancellableContinuationImpl cancellableContinuationImpl = this.continuation;
        if (z) {
            int i = Result.$r8$clinit;
            cancellableContinuationImpl.resumeWith(new Result.Failure(((CompletedExceptionally) m293x8adbf455).cause));
        } else {
            int i2 = Result.$r8$clinit;
            cancellableContinuationImpl.resumeWith(JobSupportKt.unboxState(m293x8adbf455));
        }
    }
}
