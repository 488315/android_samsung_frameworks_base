package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface Delay {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class DefaultImpls {
        public static DisposableHandle invokeOnTimeout(long j, Runnable runnable, CoroutineContext coroutineContext) {
            return DefaultExecutorKt.DefaultDelay.invokeOnTimeout(j, runnable, coroutineContext);
        }
    }

    DisposableHandle invokeOnTimeout(long j, Runnable runnable, CoroutineContext coroutineContext);

    void scheduleResumeAfterDelay(long j, CancellableContinuationImpl cancellableContinuationImpl);
}
