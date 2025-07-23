package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.EmptyDisposable;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class IoScheduler extends Scheduler {
    public static final RxThreadFactory EVICTOR_THREAD_FACTORY;
    public static final CachedWorkerPool NONE;
    public static final ThreadWorker SHUTDOWN_THREAD_WORKER;
    public static final RxThreadFactory WORKER_THREAD_FACTORY;
    public final AtomicReference pool;
    public static final TimeUnit KEEP_ALIVE_UNIT = TimeUnit.SECONDS;
    public static final long KEEP_ALIVE_TIME = Long.getLong("rx2.io-keep-alive-time", 60).longValue();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CachedWorkerPool implements Runnable {
        public final CompositeDisposable allWorkers;
        public final ScheduledExecutorService evictorService;
        public final Future evictorTask;
        public final ConcurrentLinkedQueue expiringWorkerQueue;
        public final long keepAliveTime;
        public final ThreadFactory threadFactory;

        public CachedWorkerPool(long j, TimeUnit timeUnit, ThreadFactory threadFactory) {
            CachedWorkerPool cachedWorkerPool;
            ScheduledExecutorService scheduledExecutorService;
            ScheduledFuture<?> scheduledFuture;
            long nanos = timeUnit != null ? timeUnit.toNanos(j) : 0L;
            this.keepAliveTime = nanos;
            this.expiringWorkerQueue = new ConcurrentLinkedQueue();
            this.allWorkers = new CompositeDisposable();
            this.threadFactory = threadFactory;
            if (timeUnit != null) {
                scheduledExecutorService = Executors.newScheduledThreadPool(1, IoScheduler.EVICTOR_THREAD_FACTORY);
                cachedWorkerPool = this;
                scheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(cachedWorkerPool, nanos, nanos, TimeUnit.NANOSECONDS);
            } else {
                cachedWorkerPool = this;
                scheduledExecutorService = null;
                scheduledFuture = null;
            }
            cachedWorkerPool.evictorService = scheduledExecutorService;
            cachedWorkerPool.evictorTask = scheduledFuture;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (this.expiringWorkerQueue.isEmpty()) {
                return;
            }
            long nanoTime = System.nanoTime();
            Iterator it = this.expiringWorkerQueue.iterator();
            while (it.hasNext()) {
                ThreadWorker threadWorker = (ThreadWorker) it.next();
                if (threadWorker.expirationTime > nanoTime) {
                    return;
                }
                if (this.expiringWorkerQueue.remove(threadWorker)) {
                    this.allWorkers.remove(threadWorker);
                }
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class EventLoopWorker extends Scheduler.Worker {
        public final CachedWorkerPool pool;
        public final ThreadWorker threadWorker;
        public final AtomicBoolean once = new AtomicBoolean();
        public final CompositeDisposable tasks = new CompositeDisposable();

        public EventLoopWorker(CachedWorkerPool cachedWorkerPool) {
            ThreadWorker threadWorker;
            ThreadWorker threadWorker2;
            this.pool = cachedWorkerPool;
            if (cachedWorkerPool.allWorkers.disposed) {
                threadWorker2 = IoScheduler.SHUTDOWN_THREAD_WORKER;
            } else {
                while (true) {
                    if (cachedWorkerPool.expiringWorkerQueue.isEmpty()) {
                        threadWorker = new ThreadWorker(cachedWorkerPool.threadFactory);
                        cachedWorkerPool.allWorkers.add(threadWorker);
                        break;
                    } else {
                        threadWorker = (ThreadWorker) cachedWorkerPool.expiringWorkerQueue.poll();
                        if (threadWorker != null) {
                            break;
                        }
                    }
                }
                threadWorker2 = threadWorker;
            }
            this.threadWorker = threadWorker2;
        }

        @Override // io.reactivex.disposables.Disposable
        public final void dispose() {
            if (this.once.compareAndSet(false, true)) {
                this.tasks.dispose();
                CachedWorkerPool cachedWorkerPool = this.pool;
                cachedWorkerPool.getClass();
                long nanoTime = System.nanoTime() + cachedWorkerPool.keepAliveTime;
                ThreadWorker threadWorker = this.threadWorker;
                threadWorker.expirationTime = nanoTime;
                cachedWorkerPool.expiringWorkerQueue.offer(threadWorker);
            }
        }

        @Override // io.reactivex.Scheduler.Worker
        public final Disposable schedule(Runnable runnable, long j, TimeUnit timeUnit) {
            return this.tasks.disposed ? EmptyDisposable.INSTANCE : this.threadWorker.scheduleActual(runnable, j, timeUnit, this.tasks);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ThreadWorker extends NewThreadWorker {
        public long expirationTime;

        public ThreadWorker(ThreadFactory threadFactory) {
            super(threadFactory);
            this.expirationTime = 0L;
        }
    }

    static {
        ThreadWorker threadWorker = new ThreadWorker(new RxThreadFactory("RxCachedThreadSchedulerShutdown"));
        SHUTDOWN_THREAD_WORKER = threadWorker;
        threadWorker.dispose();
        int max = Math.max(1, Math.min(10, Integer.getInteger("rx2.io-priority", 5).intValue()));
        RxThreadFactory rxThreadFactory = new RxThreadFactory("RxCachedThreadScheduler", max);
        WORKER_THREAD_FACTORY = rxThreadFactory;
        EVICTOR_THREAD_FACTORY = new RxThreadFactory("RxCachedWorkerPoolEvictor", max);
        CachedWorkerPool cachedWorkerPool = new CachedWorkerPool(0L, null, rxThreadFactory);
        NONE = cachedWorkerPool;
        cachedWorkerPool.allWorkers.dispose();
        Future future = cachedWorkerPool.evictorTask;
        if (future != null) {
            future.cancel(true);
        }
        ScheduledExecutorService scheduledExecutorService = cachedWorkerPool.evictorService;
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdownNow();
        }
    }

    public IoScheduler() {
        this(WORKER_THREAD_FACTORY);
    }

    @Override // io.reactivex.Scheduler
    public final Scheduler.Worker createWorker() {
        return new EventLoopWorker((CachedWorkerPool) this.pool.get());
    }

    public IoScheduler(ThreadFactory threadFactory) {
        CachedWorkerPool cachedWorkerPool = NONE;
        AtomicReference atomicReference = new AtomicReference(cachedWorkerPool);
        this.pool = atomicReference;
        CachedWorkerPool cachedWorkerPool2 = new CachedWorkerPool(KEEP_ALIVE_TIME, KEEP_ALIVE_UNIT, threadFactory);
        if (atomicReference.compareAndSet(cachedWorkerPool, cachedWorkerPool2)) {
            return;
        }
        cachedWorkerPool2.allWorkers.dispose();
        Future future = cachedWorkerPool2.evictorTask;
        if (future != null) {
            future.cancel(true);
        }
        ScheduledExecutorService scheduledExecutorService = cachedWorkerPool2.evictorService;
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdownNow();
        }
    }
}
