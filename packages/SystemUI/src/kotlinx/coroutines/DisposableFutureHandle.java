package kotlinx.coroutines;

import java.util.concurrent.Future;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
