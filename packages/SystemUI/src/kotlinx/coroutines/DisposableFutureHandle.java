package kotlinx.coroutines;

import java.util.concurrent.Future;

/* loaded from: classes4.dex */
public final class DisposableFutureHandle implements DisposableHandle {
    public final Future future;

    public DisposableFutureHandle(Future<?> future) {
        this.future = future;
    }

    @Override // kotlinx.coroutines.DisposableHandle
    public final void dispose() {
        this.future.cancel(false);
    }

    public final String toString() {
        return "DisposableFutureHandle[" + this.future + "]";
    }
}
