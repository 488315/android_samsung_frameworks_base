package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;

/* loaded from: classes4.dex */
public interface Delay {
    DisposableHandle invokeOnTimeout(long j, Runnable runnable, CoroutineContext coroutineContext);

    void scheduleResumeAfterDelay(long j, CancellableContinuationImpl cancellableContinuationImpl);
}
