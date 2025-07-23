package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.SerializedObserver;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ObservableDelay extends AbstractObservableWithUpstream {
    public final long delay;
    public final boolean delayError;
    public final Scheduler scheduler;
    public final TimeUnit unit;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DelayObserver implements Observer, Disposable {
        public final long delay;
        public final boolean delayError;
        public final Observer downstream;
        public final TimeUnit unit;
        public Disposable upstream;
        public final Scheduler.Worker w;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class OnComplete implements Runnable {
            public OnComplete() {
            }

            @Override // java.lang.Runnable
            public final void run() {
                try {
                    DelayObserver.this.downstream.onComplete();
                } finally {
                    DelayObserver.this.w.dispose();
                }
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class OnError implements Runnable {
            public final Throwable throwable;

            public OnError(Throwable th) {
                this.throwable = th;
            }

            @Override // java.lang.Runnable
            public final void run() {
                try {
                    DelayObserver.this.downstream.onError(this.throwable);
                } finally {
                    DelayObserver.this.w.dispose();
                }
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class OnNext implements Runnable {
            public final Object t;

            public OnNext(Object obj) {
                this.t = obj;
            }

            @Override // java.lang.Runnable
            public final void run() {
                DelayObserver.this.downstream.onNext(this.t);
            }
        }

        public DelayObserver(Observer observer, long j, TimeUnit timeUnit, Scheduler.Worker worker, boolean z) {
            this.downstream = observer;
            this.delay = j;
            this.unit = timeUnit;
            this.w = worker;
            this.delayError = z;
        }

        @Override // io.reactivex.disposables.Disposable
        public final void dispose() {
            this.upstream.dispose();
            this.w.dispose();
        }

        @Override // io.reactivex.Observer
        public final void onComplete() {
            this.w.schedule(new OnComplete(), this.delay, this.unit);
        }

        @Override // io.reactivex.Observer
        public final void onError(Throwable th) {
            this.w.schedule(new OnError(th), this.delayError ? this.delay : 0L, this.unit);
        }

        @Override // io.reactivex.Observer
        public final void onNext(Object obj) {
            this.w.schedule(new OnNext(obj), this.delay, this.unit);
        }

        @Override // io.reactivex.Observer
        public final void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }
    }

    public ObservableDelay(ObservableSource observableSource, long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
        super(observableSource);
        this.delay = j;
        this.unit = timeUnit;
        this.scheduler = scheduler;
        this.delayError = z;
    }

    @Override // io.reactivex.Observable
    public final void subscribeActual(Observer observer) {
        Observer serializedObserver = this.delayError ? observer : new SerializedObserver(observer);
        ((Observable) this.source).subscribe(new DelayObserver(serializedObserver, this.delay, this.unit, this.scheduler.createWorker(), this.delayError));
    }
}
