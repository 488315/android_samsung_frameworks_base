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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class DispatchedContinuationKt {
    public static final Symbol UNDEFINED = new Symbol(PeripheralBarcodeConstants.Symbology.UNDEFINED);
    public static final Symbol REUSABLE_CLAIMED = new Symbol("REUSABLE_CLAIMED");

    public static final void resumeCancellableWith(Object obj, Continuation continuation) {
        if (!(continuation instanceof DispatchedContinuation)) {
            continuation.resumeWith(obj);
            return;
        }
        DispatchedContinuation dispatchedContinuation = (DispatchedContinuation) continuation;
        Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(obj);
        Object completedExceptionally = m3422exceptionOrNullimpl == null ? obj : new CompletedExceptionally(m3422exceptionOrNullimpl, false, 2, null);
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
            Job job = (Job) dispatchedContinuation.continuation.getContext().get(Job.Key);
            if (job == null || job.isActive()) {
                Continuation continuation2 = dispatchedContinuation.continuation;
                Object obj2 = dispatchedContinuation.countOrElement;
                CoroutineContext context = continuation2.getContext();
                Object updateThreadContext = ThreadContextKt.updateThreadContext(context, obj2);
                UndispatchedCoroutine updateUndispatchedCompletion = updateThreadContext != ThreadContextKt.NO_THREAD_ELEMENTS ? CoroutineContextKt.updateUndispatchedCompletion(continuation2, context, updateThreadContext) : null;
                try {
                    dispatchedContinuation.continuation.resumeWith(obj);
                    Unit unit = Unit.INSTANCE;
                } finally {
                    if (updateUndispatchedCompletion == null || updateUndispatchedCompletion.clearThreadContext()) {
                        ThreadContextKt.restoreThreadContext(context, updateThreadContext);
                    }
                }
            } else {
                dispatchedContinuation.resumeWith(new Result.Failure(job.getCancellationException()));
            }
            while (eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.processUnconfinedEvent()) {
            }
        } finally {
            try {
            } finally {
            }
        }
    }

    public static final void safeDispatch(CoroutineDispatcher coroutineDispatcher, CoroutineContext coroutineContext, Runnable runnable) {
        try {
            coroutineDispatcher.dispatch(coroutineContext, runnable);
        } catch (Throwable th) {
            throw new DispatchException(th, coroutineDispatcher, coroutineContext);
        }
    }

    public static final boolean safeIsDispatchNeeded(CoroutineDispatcher coroutineDispatcher, CoroutineContext coroutineContext) {
        try {
            return coroutineDispatcher.isDispatchNeeded(coroutineContext);
        } catch (Throwable th) {
            throw new DispatchException(th, coroutineDispatcher, coroutineContext);
        }
    }
}
