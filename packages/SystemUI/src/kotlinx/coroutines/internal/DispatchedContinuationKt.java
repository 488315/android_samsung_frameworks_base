package kotlinx.coroutines.internal;

import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CompletedExceptionally;
import kotlinx.coroutines.CoroutineContextKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.DispatchException;
import kotlinx.coroutines.EventLoop;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.ThreadLocalEventLoop;
import kotlinx.coroutines.UndispatchedCoroutine;

/* loaded from: classes4.dex */
public abstract class DispatchedContinuationKt {
    public static final Symbol UNDEFINED = new Symbol(PeripheralBarcodeConstants.Symbology.UNDEFINED);
    public static final Symbol REUSABLE_CLAIMED = new Symbol("REUSABLE_CLAIMED");

    /* JADX WARN: Removed duplicated region for block: B:30:0x009c A[Catch: all -> 0x0077, DONT_GENERATE, TryCatch #0 {all -> 0x0077, blocks: (B:16:0x0054, B:18:0x0064, B:20:0x006a, B:31:0x009f, B:23:0x0079, B:25:0x0089, B:28:0x0096, B:30:0x009c, B:36:0x00ac, B:39:0x00b5, B:38:0x00b2, B:26:0x008d), top: B:48:0x0054, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void resumeCancellableWith(Object obj, Continuation continuation) {
        Job job;
        if (!(continuation instanceof DispatchedContinuation)) {
            continuation.resumeWith(obj);
            return;
        }
        DispatchedContinuation dispatchedContinuation = (DispatchedContinuation) continuation;
        Throwable thM3441exceptionOrNullimpl = Result.m3441exceptionOrNullimpl(obj);
        Object completedExceptionally = thM3441exceptionOrNullimpl == null ? obj : new CompletedExceptionally(thM3441exceptionOrNullimpl, false, 2, null);
        if (safeIsDispatchNeeded(dispatchedContinuation.dispatcher, dispatchedContinuation.continuation.getContext())) {
            dispatchedContinuation._state = completedExceptionally;
            dispatchedContinuation.resumeMode = 1;
            safeDispatch(dispatchedContinuation.dispatcher, dispatchedContinuation.continuation.getContext(), dispatchedContinuation);
            return;
        }
        ThreadLocalEventLoop.INSTANCE.getClass();
        EventLoop eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = ThreadLocalEventLoop.getEventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
        if (eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.useCount >= 4294967296L) {
            dispatchedContinuation._state = completedExceptionally;
            dispatchedContinuation.resumeMode = 1;
            eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.dispatchUnconfined(dispatchedContinuation);
            return;
        }
        eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.incrementUseCount(true);
        try {
            job = (Job) dispatchedContinuation.continuation.getContext().get(Job.Key);
        } finally {
            try {
            } finally {
            }
        }
        if (job == null || job.isActive()) {
            Continuation continuation2 = dispatchedContinuation.continuation;
            Object obj2 = dispatchedContinuation.countOrElement;
            CoroutineContext context = continuation2.getContext();
            Object objUpdateThreadContext = ThreadContextKt.updateThreadContext(context, obj2);
            UndispatchedCoroutine undispatchedCoroutineUpdateUndispatchedCompletion = objUpdateThreadContext != ThreadContextKt.NO_THREAD_ELEMENTS ? CoroutineContextKt.updateUndispatchedCompletion(continuation2, context, objUpdateThreadContext) : null;
            try {
                dispatchedContinuation.continuation.resumeWith(obj);
                Unit unit = Unit.INSTANCE;
            } finally {
                if (undispatchedCoroutineUpdateUndispatchedCompletion == null || undispatchedCoroutineUpdateUndispatchedCompletion.clearThreadContext()) {
                    ThreadContextKt.restoreThreadContext(context, objUpdateThreadContext);
                }
            }
        }
        dispatchedContinuation.resumeWith(new Result.Failure(job.getCancellationException()));
        while (eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.processUnconfinedEvent()) {
        }
    }

    public static final void safeDispatch(CoroutineDispatcher coroutineDispatcher, CoroutineContext coroutineContext, Runnable runnable) {
        try {
            coroutineDispatcher.dispatch(coroutineContext, runnable);
        } catch (Throwable th) {
            throw new DispatchException(th, coroutineDispatcher, coroutineContext);
        }
    }

    public static final boolean safeIsDispatchNeeded(CoroutineDispatcher coroutineDispatcher, CoroutineContext coroutineContext) throws DispatchException {
        try {
            return coroutineDispatcher.isDispatchNeeded(coroutineContext);
        } catch (Throwable th) {
            throw new DispatchException(th, coroutineDispatcher, coroutineContext);
        }
    }
}
