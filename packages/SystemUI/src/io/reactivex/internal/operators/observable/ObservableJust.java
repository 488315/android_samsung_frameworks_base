package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
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
