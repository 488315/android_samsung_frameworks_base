package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.disposables.ListCompositeDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ComputationScheduler extends Scheduler {
    public static final int MAX_THREADS;
    public static final FixedSchedulerPool NONE;
    public static final PoolWorker SHUTDOWN_WORKER;
    public static final RxThreadFactory THREAD_FACTORY;
    public final AtomicReference pool;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class FixedSchedulerPool {
        public final int cores;
        public final PoolWorker[] eventLoops;
        public long n;

        public FixedSchedulerPool(int i, ThreadFactory threadFactory) {
            this.cores = i;
            this.eventLoops = new PoolWorker[i];
            for (int i2 = 0; i2 < i; i2++) {
                this.eventLoops[i2] = new PoolWorker(threadFactory);
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PoolWorker extends NewThreadWorker {
        public PoolWorker(ThreadFactory threadFactory) {
            super(threadFactory);
        }
    }

    static {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        int intValue = Integer.getInteger("rx2.computation-threads", 0).intValue();
        if (intValue > 0 && intValue <= availableProcessors) {
            availableProcessors = intValue;
        }
        MAX_THREADS = availableProcessors;
        PoolWorker poolWorker = new PoolWorker(new RxThreadFactory("RxComputationShutdown"));
        SHUTDOWN_WORKER = poolWorker;
        poolWorker.dispose();
        RxThreadFactory rxThreadFactory = new RxThreadFactory("RxComputationThreadPool", Math.max(1, Math.min(10, Integer.getInteger("rx2.computation-priority", 5).intValue())), true);
        THREAD_FACTORY = rxThreadFactory;
        FixedSchedulerPool fixedSchedulerPool = new FixedSchedulerPool(0, rxThreadFactory);
        NONE = fixedSchedulerPool;
        for (PoolWorker poolWorker2 : fixedSchedulerPool.eventLoops) {
            poolWorker2.dispose();
        }
    }

    public ComputationScheduler() {
        this(THREAD_FACTORY);
    }

    @Override // io.reactivex.Scheduler
    public final Scheduler.Worker createWorker() {
        PoolWorker poolWorker;
        FixedSchedulerPool fixedSchedulerPool = (FixedSchedulerPool) this.pool.get();
        int i = fixedSchedulerPool.cores;
        if (i == 0) {
            poolWorker = SHUTDOWN_WORKER;
        } else {
            long j = fixedSchedulerPool.n;
            fixedSchedulerPool.n = 1 + j;
            poolWorker = fixedSchedulerPool.eventLoops[(int) (j % i)];
        }
        return new EventLoopWorker(poolWorker);
    }

    @Override // io.reactivex.Scheduler
    public final Disposable scheduleDirect(Runnable runnable, long j, TimeUnit timeUnit) {
        PoolWorker poolWorker;
        FixedSchedulerPool fixedSchedulerPool = (FixedSchedulerPool) this.pool.get();
        int i = fixedSchedulerPool.cores;
        if (i == 0) {
            poolWorker = SHUTDOWN_WORKER;
        } else {
            long j2 = fixedSchedulerPool.n;
            fixedSchedulerPool.n = 1 + j2;
            poolWorker = fixedSchedulerPool.eventLoops[(int) (j2 % i)];
        }
        poolWorker.getClass();
        int i2 = ObjectHelper.$r8$clinit;
        ScheduledDirectTask scheduledDirectTask = new ScheduledDirectTask(runnable);
        try {
            scheduledDirectTask.setFuture(j <= 0 ? poolWorker.executor.submit(scheduledDirectTask) : poolWorker.executor.schedule(scheduledDirectTask, j, timeUnit));
            return scheduledDirectTask;
        } catch (RejectedExecutionException e) {
            RxJavaPlugins.onError(e);
            return EmptyDisposable.INSTANCE;
        }
    }

    public ComputationScheduler(ThreadFactory threadFactory) {
        FixedSchedulerPool fixedSchedulerPool = NONE;
        AtomicReference atomicReference = new AtomicReference(fixedSchedulerPool);
        this.pool = atomicReference;
        FixedSchedulerPool fixedSchedulerPool2 = new FixedSchedulerPool(MAX_THREADS, threadFactory);
        if (atomicReference.compareAndSet(fixedSchedulerPool, fixedSchedulerPool2)) {
            return;
        }
        for (PoolWorker poolWorker : fixedSchedulerPool2.eventLoops) {
            poolWorker.dispose();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class EventLoopWorker extends Scheduler.Worker {
        public final ListCompositeDisposable both;
        public volatile boolean disposed;
        public final PoolWorker poolWorker;
        public final ListCompositeDisposable serial;
        public final CompositeDisposable timed;

        public EventLoopWorker(PoolWorker poolWorker) {
            this.poolWorker = poolWorker;
            ListCompositeDisposable listCompositeDisposable = new ListCompositeDisposable();
            this.serial = listCompositeDisposable;
            CompositeDisposable compositeDisposable = new CompositeDisposable();
            this.timed = compositeDisposable;
            ListCompositeDisposable listCompositeDisposable2 = new ListCompositeDisposable();
            this.both = listCompositeDisposable2;
            listCompositeDisposable2.add(listCompositeDisposable);
            listCompositeDisposable2.add(compositeDisposable);
        }

        @Override // io.reactivex.disposables.Disposable
        public final void dispose() {
            if (this.disposed) {
                return;
            }
            this.disposed = true;
            this.both.dispose();
        }

        @Override // io.reactivex.Scheduler.Worker
        public final void schedule(Runnable runnable) {
            if (this.disposed) {
                EmptyDisposable emptyDisposable = EmptyDisposable.INSTANCE;
            } else {
                this.poolWorker.scheduleActual(runnable, 0L, TimeUnit.MILLISECONDS, this.serial);
            }
        }

        @Override // io.reactivex.Scheduler.Worker
        public final Disposable schedule(Runnable runnable, long j, TimeUnit timeUnit) {
            if (this.disposed) {
                return EmptyDisposable.INSTANCE;
            }
            return this.poolWorker.scheduleActual(runnable, j, timeUnit, this.timed);
        }
    }
}
