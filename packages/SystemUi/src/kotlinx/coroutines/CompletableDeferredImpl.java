package kotlinx.coroutines;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.JobSupport;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectBuilderImpl;
import kotlinx.coroutines.selects.SelectClause1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class CompletableDeferredImpl extends JobSupport implements CompletableDeferred, SelectClause1 {
    public CompletableDeferredImpl(Job job) {
        super(true);
        initParentJob(job);
    }

    public final Object await(Continuation continuation) {
        Object unboxState;
        while (true) {
            Object m293x8adbf455 = m293x8adbf455();
            if (m293x8adbf455 instanceof Incomplete) {
                if (startInternal(m293x8adbf455) >= 0) {
                    JobSupport.AwaitContinuation awaitContinuation = new JobSupport.AwaitContinuation(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), this);
                    awaitContinuation.initCancellability();
                    awaitContinuation.invokeOnCancellation(new DisposeOnCancel(invokeOnCompletion(new ResumeAwaitOnCompletion(awaitContinuation))));
                    unboxState = awaitContinuation.getResult();
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    break;
                }
            } else {
                if (m293x8adbf455 instanceof CompletedExceptionally) {
                    throw ((CompletedExceptionally) m293x8adbf455).cause;
                }
                unboxState = JobSupportKt.unboxState(m293x8adbf455);
            }
        }
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        return unboxState;
    }

    @Override // kotlinx.coroutines.selects.SelectClause1
    public final void registerSelectClause1(SelectBuilderImpl selectBuilderImpl, Function2 function2) {
        Object m293x8adbf455;
        do {
            m293x8adbf455 = m293x8adbf455();
            if (selectBuilderImpl.isSelected()) {
                return;
            }
            if (!(m293x8adbf455 instanceof Incomplete)) {
                if (selectBuilderImpl.trySelect()) {
                    if (m293x8adbf455 instanceof CompletedExceptionally) {
                        selectBuilderImpl.resumeSelectWithException(((CompletedExceptionally) m293x8adbf455).cause);
                        return;
                    } else {
                        UndispatchedKt.startCoroutineUnintercepted(selectBuilderImpl, function2, JobSupportKt.unboxState(m293x8adbf455));
                        return;
                    }
                }
                return;
            }
        } while (startInternal(m293x8adbf455) != 0);
        selectBuilderImpl.disposeOnSelect(invokeOnCompletion(new SelectAwaitOnCompletion(selectBuilderImpl, function2)));
    }
}
