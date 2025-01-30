package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import java.util.concurrent.Callable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ObservableJust extends Observable implements Callable {
    public final Object value;

    public ObservableJust(Object obj) {
        this.value = obj;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        return this.value;
    }

    @Override // io.reactivex.Observable
    public final void subscribeActual(Observer observer) {
        ObservableScalarXMap$ScalarDisposable observableScalarXMap$ScalarDisposable = new ObservableScalarXMap$ScalarDisposable(observer, this.value);
        observer.onSubscribe(observableScalarXMap$ScalarDisposable);
        observableScalarXMap$ScalarDisposable.run();
    }
}
