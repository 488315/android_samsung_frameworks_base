package kotlinx.coroutines;

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
