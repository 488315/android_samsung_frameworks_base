package kotlinx.coroutines.internal;

import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import java.util.concurrent.CancellationException;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CompletedExceptionally;
import kotlinx.coroutines.CompletedWithCancellation;
import kotlinx.coroutines.CoroutineContextKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.EventLoop;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobSupport;
import kotlinx.coroutines.ThreadLocalEventLoop;
import kotlinx.coroutines.UndispatchedCoroutine;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class DispatchedContinuationKt {
    public static final Symbol UNDEFINED = new Symbol(PeripheralBarcodeConstants.Symbology.UNDEFINED);
    public static final Symbol REUSABLE_CLAIMED = new Symbol("REUSABLE_CLAIMED");

    /* JADX WARN: Finally extract failed */
    public static final void resumeCancellableWith(Continuation continuation, Object obj, Function1 function1) {
        if (!(continuation instanceof DispatchedContinuation)) {
            continuation.resumeWith(obj);
            return;
        }
        DispatchedContinuation dispatchedContinuation = (DispatchedContinuation) continuation;
        Throwable m2859exceptionOrNullimpl = Result.m2859exceptionOrNullimpl(obj);
        boolean z = false;
        Object completedWithCancellation = m2859exceptionOrNullimpl == null ? function1 != null ? new CompletedWithCancellation(obj, function1) : obj : new CompletedExceptionally(m2859exceptionOrNullimpl, false, 2, null);
        CoroutineDispatcher coroutineDispatcher = dispatchedContinuation.dispatcher;
        dispatchedContinuation.getContext();
        if (coroutineDispatcher.isDispatchNeeded()) {
            dispatchedContinuation._state = completedWithCancellation;
            dispatchedContinuation.resumeMode = 1;
            dispatchedContinuation.dispatcher.dispatch(dispatchedContinuation.getContext(), dispatchedContinuation);
            return;
        }
        ThreadLocalEventLoop.INSTANCE.getClass();
        EventLoop m295x4695df28 = ThreadLocalEventLoop.m295x4695df28();
        if (m295x4695df28.isUnconfinedLoopActive()) {
            dispatchedContinuation._state = completedWithCancellation;
            dispatchedContinuation.resumeMode = 1;
            m295x4695df28.dispatchUnconfined(dispatchedContinuation);
            return;
        }
        m295x4695df28.incrementUseCount(true);
        try {
            Job job = (Job) dispatchedContinuation.getContext().get(Job.Key);
            if (job != null && !job.isActive()) {
                CancellationException cancellationException = ((JobSupport) job).getCancellationException();
                dispatchedContinuation.mo283xd43a9d22(completedWithCancellation, cancellationException);
                dispatchedContinuation.resumeWith(new Result.Failure(cancellationException));
                z = true;
            }
            if (!z) {
                Continuation continuation2 = dispatchedContinuation.continuation;
                Object obj2 = dispatchedContinuation.countOrElement;
                CoroutineContext context = continuation2.getContext();
                Object updateThreadContext = ThreadContextKt.updateThreadContext(context, obj2);
                UndispatchedCoroutine updateUndispatchedCompletion = updateThreadContext != ThreadContextKt.NO_THREAD_ELEMENTS ? CoroutineContextKt.updateUndispatchedCompletion(continuation2, context, updateThreadContext) : null;
                try {
                    dispatchedContinuation.continuation.resumeWith(obj);
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
            while (m295x4695df28.processUnconfinedEvent()) {
            }
        } finally {
            try {
            } finally {
            }
        }
    }
}
