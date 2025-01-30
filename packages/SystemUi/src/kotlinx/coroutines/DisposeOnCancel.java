package kotlinx.coroutines;

import kotlin.Unit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class DisposeOnCancel extends CancelHandler {
    public final DisposableHandle handle;

    public DisposeOnCancel(DisposableHandle disposableHandle) {
        this.handle = disposableHandle;
    }

    @Override // kotlin.jvm.functions.Function1
    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((Throwable) obj);
        return Unit.INSTANCE;
    }

    public final String toString() {
        return "DisposeOnCancel[" + this.handle + "]";
    }

    @Override // kotlinx.coroutines.CancelHandlerBase
    public final void invoke(Throwable th) {
        this.handle.dispose();
    }
}
