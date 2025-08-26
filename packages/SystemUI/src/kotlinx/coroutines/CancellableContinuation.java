package kotlinx.coroutines;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.internal.Symbol;

/* loaded from: classes4.dex */
public interface CancellableContinuation extends Continuation {
    boolean cancel(Throwable th);

    void completeResume(Object obj);

    void invokeOnCancellation(Function1 function1);

    boolean isActive();

    boolean isCancelled$1();

    boolean isCompleted();

    void resume(Object obj, Function3 function3);

    void resume(Unit unit, Function1 function1);

    void resumeUndispatched(CoroutineDispatcher coroutineDispatcher, Unit unit);

    Symbol tryResume(Object obj, Function3 function3);

    Symbol tryResumeWithException(Throwable th);
}
