package kotlinx.coroutines;

import kotlin.Unit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ResumeUndispatchedRunnable implements Runnable {
    public final CancellableContinuation continuation;
    public final CoroutineDispatcher dispatcher;

    public ResumeUndispatchedRunnable(CoroutineDispatcher coroutineDispatcher, CancellableContinuation cancellableContinuation) {
        this.dispatcher = coroutineDispatcher;
        this.continuation = cancellableContinuation;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.continuation.resumeUndispatched(this.dispatcher, Unit.INSTANCE);
    }
}
