package io.reactivex.internal.operators.observable;

import io.reactivex.Observer;
import io.reactivex.internal.fuseable.QueueDisposable;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ObservableScalarXMap$ScalarDisposable<T> extends AtomicInteger implements QueueDisposable, Runnable {
    private static final long serialVersionUID = 3880992722410194083L;
    final Observer observer;
    final T value;

    public ObservableScalarXMap$ScalarDisposable(Observer observer, T t) {
        this.observer = observer;
        this.value = t;
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public final void clear() {
        lazySet(3);
    }

    @Override // io.reactivex.disposables.Disposable
    public final void dispose() {
        set(3);
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public final boolean isEmpty() {
        return get() != 1;
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public final boolean offer(Object obj) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public final Object poll() {
        if (get() != 1) {
            return null;
        }
        lazySet(3);
        return this.value;
    }

    @Override // io.reactivex.internal.fuseable.QueueDisposable
    public final int requestFusion() {
        lazySet(1);
        return 1;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (get() == 0 && compareAndSet(0, 2)) {
            this.observer.onNext(this.value);
            if (get() == 2) {
                lazySet(3);
                this.observer.onComplete();
            }
        }
    }
}
