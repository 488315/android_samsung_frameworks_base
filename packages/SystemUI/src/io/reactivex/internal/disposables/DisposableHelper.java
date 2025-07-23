package io.reactivex.internal.disposables;

import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.ProtocolViolationException;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public enum DisposableHelper implements Disposable {
    DISPOSED;

    public static boolean setOnce(AtomicReference atomicReference, Disposable disposable) {
        ObjectHelper.requireNonNull(disposable, "d is null");
        if (atomicReference.compareAndSet(null, disposable)) {
            return true;
        }
        disposable.dispose();
        if (atomicReference.get() == DISPOSED) {
            return false;
        }
        RxJavaPlugins.onError(new ProtocolViolationException("Disposable already set!"));
        return false;
    }

    public static boolean validate(Disposable disposable, Disposable disposable2) {
        if (disposable2 == null) {
            RxJavaPlugins.onError(new NullPointerException("next is null"));
            return false;
        }
        if (disposable == null) {
            return true;
        }
        disposable2.dispose();
        RxJavaPlugins.onError(new ProtocolViolationException("Disposable already set!"));
        return false;
    }

    @Override // io.reactivex.disposables.Disposable
    public final void dispose() {
    }

    public static void dispose(AtomicReference atomicReference) {
        Disposable disposable;
        Disposable disposable2 = (Disposable) atomicReference.get();
        DisposableHelper disposableHelper = DISPOSED;
        if (disposable2 == disposableHelper || (disposable = (Disposable) atomicReference.getAndSet(disposableHelper)) == disposableHelper || disposable == null) {
            return;
        }
        disposable.dispose();
    }
}
