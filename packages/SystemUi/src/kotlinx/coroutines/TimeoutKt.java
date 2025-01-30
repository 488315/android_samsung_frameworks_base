package kotlinx.coroutines;

import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.TypeIntrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class TimeoutKt {
    public static final Object setupTimeout(TimeoutCoroutine timeoutCoroutine, Function2 function2) {
        Object completedExceptionally;
        Object m294xe12a6b8b;
        timeoutCoroutine.invokeOnCompletion(new DisposeOnCompletion(DelayKt.getDelay(timeoutCoroutine.uCont.getContext()).invokeOnTimeout(timeoutCoroutine.time, timeoutCoroutine, timeoutCoroutine.context)));
        try {
            TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, function2);
            completedExceptionally = function2.invoke(timeoutCoroutine, timeoutCoroutine);
        } catch (Throwable th) {
            completedExceptionally = new CompletedExceptionally(th, false, 2, null);
        }
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (completedExceptionally == coroutineSingletons || (m294xe12a6b8b = timeoutCoroutine.m294xe12a6b8b(completedExceptionally)) == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
            return coroutineSingletons;
        }
        if (m294xe12a6b8b instanceof CompletedExceptionally) {
            Throwable th2 = ((CompletedExceptionally) m294xe12a6b8b).cause;
            if (((th2 instanceof TimeoutCancellationException) && ((TimeoutCancellationException) th2).coroutine == timeoutCoroutine) ? false : true) {
                throw th2;
            }
            if (completedExceptionally instanceof CompletedExceptionally) {
                throw ((CompletedExceptionally) completedExceptionally).cause;
            }
        } else {
            completedExceptionally = JobSupportKt.unboxState(m294xe12a6b8b);
        }
        return completedExceptionally;
    }
}
