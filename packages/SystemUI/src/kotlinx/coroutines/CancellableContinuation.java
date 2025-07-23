package kotlinx.coroutines;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
