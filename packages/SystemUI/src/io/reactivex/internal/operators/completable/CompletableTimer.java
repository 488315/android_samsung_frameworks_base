package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.observers.CallbackCompletableObserver;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class CompletableTimer extends Completable {
    public final long delay;
    public final Scheduler scheduler;
    public final TimeUnit unit;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class TimerDisposable extends AtomicReference<Disposable> implements Disposable, Runnable {
        private static final long serialVersionUID = 3167244060586201109L;
        final CompletableObserver downstream;

        public TimerDisposable(CompletableObserver completableObserver) {
            this.downstream = completableObserver;
        }

        @Override // io.reactivex.disposables.Disposable
        public final void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // java.lang.Runnable
        public final void run() {
            ((CallbackCompletableObserver) this.downstream).onComplete();
        }
    }

    public CompletableTimer(long j, TimeUnit timeUnit, Scheduler scheduler) {
        this.delay = j;
        this.unit = timeUnit;
        this.scheduler = scheduler;
    }

    @Override // io.reactivex.Completable
    public final void subscribeActual(CallbackCompletableObserver callbackCompletableObserver) {
        Disposable disposable;
        TimerDisposable timerDisposable = new TimerDisposable(callbackCompletableObserver);
        DisposableHelper.setOnce(callbackCompletableObserver, timerDisposable);
        Disposable scheduleDirect = this.scheduler.scheduleDirect(timerDisposable, this.delay, this.unit);
        do {
            disposable = timerDisposable.get();
            if (disposable == DisposableHelper.DISPOSED) {
                if (scheduleDirect != null) {
                    scheduleDirect.dispose();
                    return;
                }
                return;
            }
        } while (!timerDisposable.compareAndSet(disposable, scheduleDirect));
    }
}
