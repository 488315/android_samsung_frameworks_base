package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.observers.BasicIntQueueDisposable;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.schedulers.TrampolineScheduler;
import io.reactivex.plugins.RxJavaPlugins;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ObservableObserveOn extends AbstractObservableWithUpstream {
    public final int bufferSize;
    public final boolean delayError;
    public final Scheduler scheduler;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    final class ObserveOnObserver<T> extends BasicIntQueueDisposable<T> implements Observer, Runnable {
        private static final long serialVersionUID = 6576896619930983584L;
        final int bufferSize;
        final boolean delayError;
        volatile boolean disposed;
        volatile boolean done;
        final Observer downstream;
        Throwable error;
        boolean outputFused;
        SimpleQueue queue;
        int sourceMode;
        Disposable upstream;
        final Scheduler.Worker worker;

        public ObserveOnObserver(Observer observer, Scheduler.Worker worker, boolean z, int i) {
            this.downstream = observer;
            this.worker = worker;
            this.delayError = z;
            this.bufferSize = i;
        }

        public final boolean checkTerminated(boolean z, boolean z2, Observer observer) {
            if (this.disposed) {
                this.queue.clear();
                return true;
            }
            if (!z) {
                return false;
            }
            Throwable th = this.error;
            if (this.delayError) {
                if (!z2) {
                    return false;
                }
                this.disposed = true;
                if (th != null) {
                    observer.onError(th);
                } else {
                    observer.onComplete();
                }
                this.worker.dispose();
                return true;
            }
            if (th != null) {
                this.disposed = true;
                this.queue.clear();
                observer.onError(th);
                this.worker.dispose();
                return true;
            }
            if (!z2) {
                return false;
            }
            this.disposed = true;
            observer.onComplete();
            this.worker.dispose();
            return true;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public final void clear() {
            this.queue.clear();
        }

        @Override // io.reactivex.disposables.Disposable
        public final void dispose() {
            if (this.disposed) {
                return;
            }
            this.disposed = true;
            this.upstream.dispose();
            this.worker.dispose();
            if (getAndIncrement() == 0) {
                this.queue.clear();
            }
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public final boolean isEmpty() {
            return this.queue.isEmpty();
        }

        @Override // io.reactivex.Observer
        public final void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            if (getAndIncrement() == 0) {
                this.worker.schedule(this);
            }
        }

        @Override // io.reactivex.Observer
        public final void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.error = th;
            this.done = true;
            if (getAndIncrement() == 0) {
                this.worker.schedule(this);
            }
        }

        @Override // io.reactivex.Observer
        public final void onNext(Object obj) {
            if (this.done) {
                return;
            }
            if (this.sourceMode != 2) {
                this.queue.offer(obj);
            }
            if (getAndIncrement() == 0) {
                this.worker.schedule(this);
            }
        }

        @Override // io.reactivex.Observer
        public final void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                if (disposable instanceof QueueDisposable) {
                    QueueDisposable queueDisposable = (QueueDisposable) disposable;
                    int requestFusion = queueDisposable.requestFusion();
                    if (requestFusion == 1) {
                        this.sourceMode = requestFusion;
                        this.queue = queueDisposable;
                        this.done = true;
                        this.downstream.onSubscribe(this);
                        if (getAndIncrement() == 0) {
                            this.worker.schedule(this);
                            return;
                        }
                        return;
                    }
                    if (requestFusion == 2) {
                        this.sourceMode = requestFusion;
                        this.queue = queueDisposable;
                        this.downstream.onSubscribe(this);
                        return;
                    }
                }
                this.queue = new SpscLinkedArrayQueue(this.bufferSize);
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public final Object poll() {
            return this.queue.poll();
        }

        @Override // io.reactivex.internal.fuseable.QueueDisposable
        public final int requestFusion() {
            this.outputFused = true;
            return 2;
        }

        /* JADX WARN: Code restructure failed: missing block: B:43:0x0075, code lost:
        
            r3 = addAndGet(-r3);
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:0x007a, code lost:
        
            if (r3 != 0) goto L53;
         */
        /* JADX WARN: Code restructure failed: missing block: B:46:?, code lost:
        
            return;
         */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void run() {
            if (!this.outputFused) {
                SimpleQueue simpleQueue = this.queue;
                Observer observer = this.downstream;
                int i = 1;
                while (!checkTerminated(this.done, simpleQueue.isEmpty(), observer)) {
                    while (true) {
                        boolean z = this.done;
                        try {
                            Object poll = simpleQueue.poll();
                            boolean z2 = poll == null;
                            if (checkTerminated(z, z2, observer)) {
                                return;
                            }
                            if (z2) {
                                break;
                            } else {
                                observer.onNext(poll);
                            }
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            this.disposed = true;
                            this.upstream.dispose();
                            simpleQueue.clear();
                            observer.onError(th);
                            this.worker.dispose();
                            return;
                        }
                    }
                }
                return;
            }
            int i2 = 1;
            while (!this.disposed) {
                boolean z3 = this.done;
                Throwable th2 = this.error;
                if (!this.delayError && z3 && th2 != null) {
                    this.disposed = true;
                    this.downstream.onError(this.error);
                    this.worker.dispose();
                    return;
                }
                this.downstream.onNext(null);
                if (z3) {
                    this.disposed = true;
                    Throwable th3 = this.error;
                    if (th3 != null) {
                        this.downstream.onError(th3);
                    } else {
                        this.downstream.onComplete();
                    }
                    this.worker.dispose();
                    return;
                }
                i2 = addAndGet(-i2);
                if (i2 == 0) {
                    return;
                }
            }
        }
    }

    public ObservableObserveOn(ObservableSource observableSource, Scheduler scheduler, boolean z, int i) {
        super(observableSource);
        this.scheduler = scheduler;
        this.delayError = z;
        this.bufferSize = i;
    }

    @Override // io.reactivex.Observable
    public final void subscribeActual(Observer observer) {
        Scheduler scheduler = this.scheduler;
        boolean z = scheduler instanceof TrampolineScheduler;
        ObservableSource observableSource = this.source;
        if (z) {
            ((Observable) observableSource).subscribe(observer);
        } else {
            ((Observable) observableSource).subscribe(new ObserveOnObserver(observer, scheduler.createWorker(), this.delayError, this.bufferSize));
        }
    }
}
