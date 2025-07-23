package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class TrampolineScheduler extends Scheduler {
    public static final /* synthetic */ int $r8$clinit = 0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SleepingRunnable implements Runnable {
        public final long execTime;
        public final Runnable run;
        public final TrampolineWorker worker;

        public SleepingRunnable(Runnable runnable, TrampolineWorker trampolineWorker, long j) {
            this.run = runnable;
            this.worker = trampolineWorker;
            this.execTime = j;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (this.worker.disposed) {
                return;
            }
            TrampolineWorker trampolineWorker = this.worker;
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            trampolineWorker.getClass();
            long convert = timeUnit.convert(System.currentTimeMillis(), timeUnit);
            long j = this.execTime;
            if (j > convert) {
                try {
                    Thread.sleep(j - convert);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    RxJavaPlugins.onError(e);
                    return;
                }
            }
            if (this.worker.disposed) {
                return;
            }
            this.run.run();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TimedRunnable implements Comparable {
        public final int count;
        public volatile boolean disposed;
        public final long execTime;
        public final Runnable run;

        public TimedRunnable(Runnable runnable, Long l, int i) {
            this.run = runnable;
            this.execTime = l.longValue();
            this.count = i;
        }

        @Override // java.lang.Comparable
        public final int compareTo(Object obj) {
            TimedRunnable timedRunnable = (TimedRunnable) obj;
            long j = this.execTime;
            long j2 = timedRunnable.execTime;
            int i = ObjectHelper.$r8$clinit;
            int i2 = j < j2 ? -1 : j > j2 ? 1 : 0;
            if (i2 != 0) {
                return i2;
            }
            int i3 = this.count;
            int i4 = timedRunnable.count;
            if (i3 < i4) {
                return -1;
            }
            return i3 > i4 ? 1 : 0;
        }
    }

    static {
        new TrampolineScheduler();
    }

    @Override // io.reactivex.Scheduler
    public final Scheduler.Worker createWorker() {
        return new TrampolineWorker();
    }

    @Override // io.reactivex.Scheduler
    public final Disposable scheduleDirect(Runnable runnable, long j, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(j);
            int i = ObjectHelper.$r8$clinit;
            runnable.run();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            RxJavaPlugins.onError(e);
        }
        return EmptyDisposable.INSTANCE;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TrampolineWorker extends Scheduler.Worker {
        public volatile boolean disposed;
        public final PriorityBlockingQueue queue = new PriorityBlockingQueue();
        public final AtomicInteger wip = new AtomicInteger();
        public final AtomicInteger counter = new AtomicInteger();

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class AppendToQueueTask implements Runnable {
            public final TimedRunnable timedRunnable;

            public AppendToQueueTask(TimedRunnable timedRunnable) {
                this.timedRunnable = timedRunnable;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.timedRunnable.disposed = true;
                TrampolineWorker.this.queue.remove(this.timedRunnable);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public final void dispose() {
            this.disposed = true;
        }

        public final Disposable enqueue(Runnable runnable, long j) {
            if (this.disposed) {
                return EmptyDisposable.INSTANCE;
            }
            TimedRunnable timedRunnable = new TimedRunnable(runnable, Long.valueOf(j), this.counter.incrementAndGet());
            this.queue.add(timedRunnable);
            if (this.wip.getAndIncrement() != 0) {
                return Disposables.fromRunnable(new AppendToQueueTask(timedRunnable));
            }
            int i = 1;
            while (!this.disposed) {
                TimedRunnable timedRunnable2 = (TimedRunnable) this.queue.poll();
                if (timedRunnable2 == null) {
                    i = this.wip.addAndGet(-i);
                    if (i == 0) {
                        return EmptyDisposable.INSTANCE;
                    }
                } else if (!timedRunnable2.disposed) {
                    timedRunnable2.run.run();
                }
            }
            this.queue.clear();
            return EmptyDisposable.INSTANCE;
        }

        @Override // io.reactivex.Scheduler.Worker
        public final void schedule(Runnable runnable) {
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            enqueue(runnable, timeUnit.convert(System.currentTimeMillis(), timeUnit));
        }

        @Override // io.reactivex.Scheduler.Worker
        public final Disposable schedule(Runnable runnable, long j, TimeUnit timeUnit) {
            TimeUnit timeUnit2 = TimeUnit.MILLISECONDS;
            long millis = timeUnit.toMillis(j) + timeUnit2.convert(System.currentTimeMillis(), timeUnit2);
            return enqueue(new SleepingRunnable(runnable, this, millis), millis);
        }
    }
}
