package kotlinx.coroutines;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.internal.ScopeCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SupervisorCoroutine extends ScopeCoroutine {
    public SupervisorCoroutine(CoroutineContext coroutineContext, Continuation continuation) {
        super(coroutineContext, continuation);
    }

    @Override // kotlinx.coroutines.JobSupport
    public final boolean childCancelled(Throwable th) {
        return false;
    }
}
