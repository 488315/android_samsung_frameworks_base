package kotlinx.coroutines;

import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.ThreadContextKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class DispatchedTaskKt {
    public static final void resume(DispatchedTask dispatchedTask, Continuation continuation, boolean z) {
        Object mo287x119202a3;
        Object mo288x99f628c6 = dispatchedTask.mo288x99f628c6();
        Throwable mo286xd8447f2f = dispatchedTask.mo286xd8447f2f(mo288x99f628c6);
        if (mo286xd8447f2f != null) {
            int i = Result.$r8$clinit;
            mo287x119202a3 = new Result.Failure(mo286xd8447f2f);
        } else {
            int i2 = Result.$r8$clinit;
            mo287x119202a3 = dispatchedTask.mo287x119202a3(mo288x99f628c6);
        }
        if (!z) {
            continuation.resumeWith(mo287x119202a3);
            return;
        }
        DispatchedContinuation dispatchedContinuation = (DispatchedContinuation) continuation;
        Continuation continuation2 = dispatchedContinuation.continuation;
        Object obj = dispatchedContinuation.countOrElement;
        CoroutineContext context = continuation2.getContext();
        Object updateThreadContext = ThreadContextKt.updateThreadContext(context, obj);
        UndispatchedCoroutine updateUndispatchedCompletion = updateThreadContext != ThreadContextKt.NO_THREAD_ELEMENTS ? CoroutineContextKt.updateUndispatchedCompletion(continuation2, context, updateThreadContext) : null;
        try {
            dispatchedContinuation.continuation.resumeWith(mo287x119202a3);
            Unit unit = Unit.INSTANCE;
        } finally {
            if (updateUndispatchedCompletion == null || updateUndispatchedCompletion.clearThreadContext()) {
                ThreadContextKt.restoreThreadContext(context, updateThreadContext);
            }
        }
    }
}
