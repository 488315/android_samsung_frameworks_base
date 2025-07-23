package kotlinx.coroutines;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class DisposeOnCancel implements CancelHandler {
    public final DisposableHandle handle;

    public DisposeOnCancel(DisposableHandle disposableHandle) {
        this.handle = disposableHandle;
    }

    @Override // kotlinx.coroutines.CancelHandler
    public final void invoke(Throwable th) {
        this.handle.dispose();
    }

    public final String toString() {
        return "DisposeOnCancel[" + this.handle + "]";
    }
}
