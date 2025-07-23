package kotlinx.coroutines;

import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.ThreadContextKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class DispatchedTaskKt {
    public static final void resume(CancellableContinuationImpl cancellableContinuationImpl, Continuation continuation, boolean z) {
        Object successfulResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host;
        Object obj = cancellableContinuationImpl._state.value;
        Throwable exceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = cancellableContinuationImpl.getExceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(obj);
        if (exceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != null) {
            int i = Result.$r8$clinit;
            successfulResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = new Result.Failure(exceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host);
        } else {
            int i2 = Result.$r8$clinit;
            successfulResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = cancellableContinuationImpl.getSuccessfulResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(obj);
        }
        if (!z) {
            continuation.resumeWith(successfulResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host);
            return;
        }
        DispatchedContinuation dispatchedContinuation = (DispatchedContinuation) continuation;
        Continuation continuation2 = dispatchedContinuation.continuation;
        Object obj2 = dispatchedContinuation.countOrElement;
        CoroutineContext context = continuation2.getContext();
        Object updateThreadContext = ThreadContextKt.updateThreadContext(context, obj2);
        UndispatchedCoroutine updateUndispatchedCompletion = updateThreadContext != ThreadContextKt.NO_THREAD_ELEMENTS ? CoroutineContextKt.updateUndispatchedCompletion(continuation2, context, updateThreadContext) : null;
        try {
            dispatchedContinuation.continuation.resumeWith(successfulResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host);
            Unit unit = Unit.INSTANCE;
            if (updateUndispatchedCompletion == null || updateUndispatchedCompletion.clearThreadContext()) {
                ThreadContextKt.restoreThreadContext(context, updateThreadContext);
            }
        } catch (Throwable th) {
            if (updateUndispatchedCompletion == null || updateUndispatchedCompletion.clearThreadContext()) {
                ThreadContextKt.restoreThreadContext(context, updateThreadContext);
            }
            throw th;
        }
    }
}
