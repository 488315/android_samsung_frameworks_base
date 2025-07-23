package kotlinx.coroutines.flow.internal;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class NoOpContinuation implements Continuation {
    public static final NoOpContinuation INSTANCE = new NoOpContinuation();
    public static final EmptyCoroutineContext context = EmptyCoroutineContext.INSTANCE;

    private NoOpContinuation() {
    }

    @Override // kotlin.coroutines.Continuation
    public final CoroutineContext getContext() {
        return context;
    }

    @Override // kotlin.coroutines.Continuation
    public final void resumeWith(Object obj) {
    }
}
