package io.reactivex.internal.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class LambdaObserver<T> extends AtomicReference<Disposable> implements Observer, Disposable {
    private static final long serialVersionUID = -7251123623727029452L;
    final Action onComplete;
    final Consumer onError;
    final Consumer onNext;
    final Consumer onSubscribe;

    public LambdaObserver(Consumer consumer, Consumer consumer2, Action action, Consumer consumer3) {
        this.onNext = consumer;
        this.onError = consumer2;
        this.onComplete = action;
        this.onSubscribe = consumer3;
    }

    @Override // io.reactivex.disposables.Disposable
    public final void dispose() {
        DisposableHelper.dispose(this);
    }

    @Override // io.reactivex.Observer
    public final void onComplete() {
        Disposable disposable = get();
        DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
        if (disposable == disposableHelper) {
            return;
        }
        lazySet(disposableHelper);
        try {
            this.onComplete.run();
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            RxJavaPlugins.onError(th);
        }
    }

    @Override // io.reactivex.Observer
    public final void onError(Throwable th) {
        Disposable disposable = get();
        DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
        if (disposable == disposableHelper) {
            RxJavaPlugins.onError(th);
            return;
        }
        lazySet(disposableHelper);
        try {
            this.onError.accept(th);
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            RxJavaPlugins.onError(new CompositeException(th, th2));
        }
    }

    @Override // io.reactivex.Observer
    public final void onNext(Object obj) {
        if (get() == DisposableHelper.DISPOSED) {
            return;
        }
        try {
            this.onNext.accept(obj);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            get().dispose();
            onError(th);
        }
    }

    @Override // io.reactivex.Observer
    public final void onSubscribe(Disposable disposable) {
        if (DisposableHelper.setOnce(this, disposable)) {
            try {
                this.onSubscribe.accept(this);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                disposable.dispose();
                onError(th);
            }
        }
    }
}
