package kotlinx.coroutines;

import java.util.concurrent.Future;

/* loaded from: classes4.dex */
public final class CancelFutureOnCancel implements CancelHandler {
    public final Future future;

    public CancelFutureOnCancel(Future<?> future) {
        this.future = future;
    }

    @Override // kotlinx.coroutines.CancelHandler
    public final void invoke(Throwable th) {
        this.future.cancel(false);
    }

    public final String toString() {
        return "CancelFutureOnCancel[" + this.future + "]";
    }
}
