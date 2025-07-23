package io.reactivex.disposables;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
final class RunnableDisposable extends ReferenceDisposable<Runnable> {
    private static final long serialVersionUID = -8219729196779211169L;

    public RunnableDisposable(Runnable runnable) {
        super(runnable);
    }

    @Override // io.reactivex.disposables.ReferenceDisposable
    public final void onDisposed(Object obj) {
        ((Runnable) obj).run();
    }

    @Override // java.util.concurrent.atomic.AtomicReference
    public final String toString() {
        StringBuilder sb = new StringBuilder("RunnableDisposable(disposed=");
        sb.append(get() == null);
        sb.append(", ");
        sb.append(get());
        sb.append(")");
        return sb.toString();
    }
}
