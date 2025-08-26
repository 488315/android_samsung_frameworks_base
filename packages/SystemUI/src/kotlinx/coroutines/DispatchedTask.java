package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.ThreadContextKt;
import kotlinx.coroutines.scheduling.Task;

/* loaded from: classes4.dex */
public abstract class DispatchedTask extends Task {
    public int resumeMode;

    public DispatchedTask(int i) {
        this.resumeMode = i;
    }

    public abstract Continuation getDelegate$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();

    public Throwable getExceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Object obj) {
        CompletedExceptionally completedExceptionally = obj instanceof CompletedExceptionally ? (CompletedExceptionally) obj : null;
        if (completedExceptionally != null) {
            return completedExceptionally.cause;
        }
        return null;
    }

    public final void handleFatalException$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Throwable th) {
        CoroutineExceptionHandlerKt.handleCoroutineException(new CoroutinesInternalError("Fatal exception in coroutines machinery for " + this + ". Please read KDoc to 'handleFatalException' method and report this incident to maintainers", th), getDelegate$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host().getContext());
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            DispatchedContinuation dispatchedContinuation = (DispatchedContinuation) getDelegate$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
            Continuation continuation = dispatchedContinuation.continuation;
            Object obj = dispatchedContinuation.countOrElement;
            CoroutineContext context = continuation.getContext();
            Object objUpdateThreadContext = ThreadContextKt.updateThreadContext(context, obj);
            Job job = null;
            UndispatchedCoroutine undispatchedCoroutineUpdateUndispatchedCompletion = objUpdateThreadContext != ThreadContextKt.NO_THREAD_ELEMENTS ? CoroutineContextKt.updateUndispatchedCompletion(continuation, context, objUpdateThreadContext) : null;
            try {
                CoroutineContext context2 = continuation.getContext();
                Object objTakeState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = takeState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
                Throwable exceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = getExceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(objTakeState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host);
                if (exceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == null) {
                    int i = this.resumeMode;
                    boolean z = true;
                    if (i != 1 && i != 2) {
                        z = false;
                    }
                    if (z) {
                        job = (Job) context2.get(Job.Key);
                    }
                }
                if (job != null && !job.isActive()) {
                    CancellationException cancellationException = job.getCancellationException();
                    cancelCompletedResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(cancellationException);
                    int i2 = Result.$r8$clinit;
                    continuation.resumeWith(new Result.Failure(cancellationException));
                } else if (exceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != null) {
                    int i3 = Result.$r8$clinit;
                    continuation.resumeWith(new Result.Failure(exceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host));
                } else {
                    int i4 = Result.$r8$clinit;
                    continuation.resumeWith(getSuccessfulResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(objTakeState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host));
                }
                Unit unit = Unit.INSTANCE;
                if (undispatchedCoroutineUpdateUndispatchedCompletion == null || undispatchedCoroutineUpdateUndispatchedCompletion.clearThreadContext()) {
                    ThreadContextKt.restoreThreadContext(context, objUpdateThreadContext);
                }
            } catch (Throwable th) {
                if (undispatchedCoroutineUpdateUndispatchedCompletion == null || undispatchedCoroutineUpdateUndispatchedCompletion.clearThreadContext()) {
                    ThreadContextKt.restoreThreadContext(context, objUpdateThreadContext);
                }
                throw th;
            }
        } catch (DispatchException e) {
            CoroutineExceptionHandlerKt.handleCoroutineException(e.getCause(), getDelegate$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host().getContext());
        } catch (Throwable th2) {
            handleFatalException$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(th2);
        }
    }

    public abstract Object takeState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();

    public void cancelCompletedResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Throwable th) {
    }

    public Object getSuccessfulResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Object obj) {
        return obj;
    }
}
