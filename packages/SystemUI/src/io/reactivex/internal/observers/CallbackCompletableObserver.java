package io.reactivex.internal.observers;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class CallbackCompletableObserver extends AtomicReference<Disposable> implements CompletableObserver, Disposable, Consumer {
    private static final long serialVersionUID = -4361286194466301354L;
    final Action onComplete;
    final Consumer onError;

    public CallbackCompletableObserver(Action action) {
        this.onError = this;
        this.onComplete = action;
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        RxJavaPlugins.onError(new OnErrorNotImplementedException((Throwable) obj));
    }

    @Override // io.reactivex.disposables.Disposable
    public final void dispose() {
        DisposableHelper.dispose(this);
    }

    public final void onComplete() {
        try {
            this.onComplete.run();
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            RxJavaPlugins.onError(th);
        }
        lazySet(DisposableHelper.DISPOSED);
    }

    public CallbackCompletableObserver(Consumer consumer, Action action) {
        this.onError = consumer;
        this.onComplete = action;
    }
}
