package kotlinx.coroutines;

import java.util.concurrent.Future;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
