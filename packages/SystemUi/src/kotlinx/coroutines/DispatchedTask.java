package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.ThreadContextKt;
import kotlinx.coroutines.scheduling.Task;
import kotlinx.coroutines.scheduling.TaskContext;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class DispatchedTask extends Task {
    public int resumeMode;

    public DispatchedTask(int i) {
        this.resumeMode = i;
    }

    /* renamed from: getDelegate$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public abstract Continuation mo285xefbb4835();

    /* renamed from: getExceptionalResult$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public Throwable mo286xd8447f2f(Object obj) {
        CompletedExceptionally completedExceptionally = obj instanceof CompletedExceptionally ? (CompletedExceptionally) obj : null;
        if (completedExceptionally != null) {
            return completedExceptionally.cause;
        }
        return null;
    }

    public final void handleFatalException(Throwable th, Throwable th2) {
        if (th == null && th2 == null) {
            return;
        }
        if (th != null && th2 != null) {
            ExceptionsKt__ExceptionsKt.addSuppressed(th, th2);
        }
        if (th == null) {
            th = th2;
        }
        Intrinsics.checkNotNull(th);
        CoroutineExceptionHandlerKt.handleCoroutineException(new CoroutinesInternalError("Fatal exception in coroutines machinery for " + this + ". Please read KDoc to 'handleFatalException' method and report this incident to maintainers", th), mo285xefbb4835().getContext());
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0039, code lost:
    
        r6 = (kotlinx.coroutines.Job) r6.get(kotlinx.coroutines.Job.Key);
     */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        Object failure;
        Object failure2;
        TaskContext taskContext = this.taskContext;
        try {
            DispatchedContinuation dispatchedContinuation = (DispatchedContinuation) mo285xefbb4835();
            Continuation continuation = dispatchedContinuation.continuation;
            Object obj = dispatchedContinuation.countOrElement;
            CoroutineContext context = continuation.getContext();
            Object updateThreadContext = ThreadContextKt.updateThreadContext(context, obj);
            UndispatchedCoroutine updateUndispatchedCompletion = updateThreadContext != ThreadContextKt.NO_THREAD_ELEMENTS ? CoroutineContextKt.updateUndispatchedCompletion(continuation, context, updateThreadContext) : null;
            try {
                CoroutineContext context2 = continuation.getContext();
                Object mo288x99f628c6 = mo288x99f628c6();
                Throwable mo286xd8447f2f = mo286xd8447f2f(mo288x99f628c6);
                if (mo286xd8447f2f == null) {
                    int i = this.resumeMode;
                    boolean z = true;
                    if (i != 1 && i != 2) {
                        z = false;
                    }
                }
                Job job = null;
                if (job != null && !job.isActive()) {
                    CancellationException cancellationException = ((JobSupport) job).getCancellationException();
                    mo283xd43a9d22(mo288x99f628c6, cancellationException);
                    int i2 = Result.$r8$clinit;
                    continuation.resumeWith(new Result.Failure(cancellationException));
                } else if (mo286xd8447f2f != null) {
                    int i3 = Result.$r8$clinit;
                    continuation.resumeWith(new Result.Failure(mo286xd8447f2f));
                } else {
                    int i4 = Result.$r8$clinit;
                    continuation.resumeWith(mo287x119202a3(mo288x99f628c6));
                }
                Unit unit = Unit.INSTANCE;
                try {
                    taskContext.getClass();
                    failure2 = Unit.INSTANCE;
                } catch (Throwable th) {
                    int i5 = Result.$r8$clinit;
                    failure2 = new Result.Failure(th);
                }
                handleFatalException(null, Result.m2859exceptionOrNullimpl(failure2));
            } finally {
                if (updateUndispatchedCompletion == null || updateUndispatchedCompletion.clearThreadContext()) {
                    ThreadContextKt.restoreThreadContext(context, updateThreadContext);
                }
            }
        } catch (Throwable th2) {
            try {
                int i6 = Result.$r8$clinit;
                taskContext.getClass();
                failure = Unit.INSTANCE;
            } catch (Throwable th3) {
                int i7 = Result.$r8$clinit;
                failure = new Result.Failure(th3);
            }
            handleFatalException(th2, Result.m2859exceptionOrNullimpl(failure));
        }
    }

    /* renamed from: takeState$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public abstract Object mo288x99f628c6();

    /* renamed from: getSuccessfulResult$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public Object mo287x119202a3(Object obj) {
        return obj;
    }

    /* renamed from: cancelCompletedResult$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public void mo283xd43a9d22(Object obj, Throwable th) {
    }
}
