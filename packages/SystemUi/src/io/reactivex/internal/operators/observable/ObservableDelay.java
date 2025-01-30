package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.SerializedObserver;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ObservableDelay extends AbstractObservableWithUpstream {
    public final long delay;
    public final boolean delayError;
    public final Scheduler scheduler;
    public final TimeUnit unit;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DelayObserver implements Observer, Disposable {
        public final long delay;
        public final boolean delayError;
        public final Observer downstream;
        public final TimeUnit unit;
        public Disposable upstream;

        /* renamed from: w */
        public final Scheduler.Worker f653w;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class OnComplete implements Runnable {
            public OnComplete() {
            }

            @Override // java.lang.Runnable
            public final void run() {
                try {
                    DelayObserver.this.downstream.onComplete();
                } finally {
                    DelayObserver.this.f653w.dispose();
                }
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                    DelayObserver.this.f653w.dispose();
                }
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class OnNext implements Runnable {

            /* renamed from: t */
            public final Object f654t;

            public OnNext(Object obj) {
                this.f654t = obj;
            }

            @Override // java.lang.Runnable
            public final void run() {
                DelayObserver.this.downstream.onNext(this.f654t);
            }
        }

        public DelayObserver(Observer observer, long j, TimeUnit timeUnit, Scheduler.Worker worker, boolean z) {
            this.downstream = observer;
            this.delay = j;
            this.unit = timeUnit;
            this.f653w = worker;
            this.delayError = z;
        }

        @Override // io.reactivex.disposables.Disposable
        public final void dispose() {
            this.upstream.dispose();
            this.f653w.dispose();
        }

        @Override // io.reactivex.Observer
        public final void onComplete() {
            this.f653w.schedule(new OnComplete(), this.delay, this.unit);
        }

        @Override // io.reactivex.Observer
        public final void onError(Throwable th) {
            this.f653w.schedule(new OnError(th), this.delayError ? this.delay : 0L, this.unit);
        }

        @Override // io.reactivex.Observer
        public final void onNext(Object obj) {
            this.f653w.schedule(new OnNext(obj), this.delay, this.unit);
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
        ((Observable) this.source).subscribe(new DelayObserver(this.delayError ? observer : new SerializedObserver(observer), this.delay, this.unit, this.scheduler.createWorker(), this.delayError));
    }
}
