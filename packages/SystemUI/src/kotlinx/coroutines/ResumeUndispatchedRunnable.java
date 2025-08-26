package kotlinx.coroutines;

import kotlin.Unit;

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
