package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SingleScheduler extends Scheduler {
    public static final RxThreadFactory SINGLE_THREAD_FACTORY;
    public final AtomicReference executor;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ScheduledWorker extends Scheduler.Worker {
        public volatile boolean disposed;
        public final ScheduledExecutorService executor;
        public final CompositeDisposable tasks = new CompositeDisposable();

        public ScheduledWorker(ScheduledExecutorService scheduledExecutorService) {
            this.executor = scheduledExecutorService;
        }

        @Override // io.reactivex.disposables.Disposable
        public final void dispose() {
            if (this.disposed) {
                return;
            }
            this.disposed = true;
            this.tasks.dispose();
        }

        @Override // io.reactivex.Scheduler.Worker
        public final Disposable schedule(Runnable runnable, long j, TimeUnit timeUnit) {
            if (this.disposed) {
                return EmptyDisposable.INSTANCE;
            }
            RxJavaPlugins.onSchedule(runnable);
            ScheduledRunnable scheduledRunnable = new ScheduledRunnable(runnable, this.tasks);
            this.tasks.add(scheduledRunnable);
            try {
                scheduledRunnable.setFuture(j <= 0 ? this.executor.submit((Callable) scheduledRunnable) : this.executor.schedule((Callable) scheduledRunnable, j, timeUnit));
                return scheduledRunnable;
            } catch (RejectedExecutionException e) {
                dispose();
                RxJavaPlugins.onError(e);
                return EmptyDisposable.INSTANCE;
            }
        }
    }

    static {
        Executors.newScheduledThreadPool(0).shutdown();
        SINGLE_THREAD_FACTORY = new RxThreadFactory("RxSingleScheduler", Math.max(1, Math.min(10, Integer.getInteger("rx2.single-priority", 5).intValue())), true);
    }

    public SingleScheduler() {
        this(SINGLE_THREAD_FACTORY);
    }

    @Override // io.reactivex.Scheduler
    public final Scheduler.Worker createWorker() {
        return new ScheduledWorker((ScheduledExecutorService) this.executor.get());
    }

    @Override // io.reactivex.Scheduler
    public final Disposable scheduleDirect(Runnable runnable, long j, TimeUnit timeUnit) {
        RxJavaPlugins.onSchedule(runnable);
        ScheduledDirectTask scheduledDirectTask = new ScheduledDirectTask(runnable);
        AtomicReference atomicReference = this.executor;
        try {
            scheduledDirectTask.setFuture(j <= 0 ? ((ScheduledExecutorService) atomicReference.get()).submit(scheduledDirectTask) : ((ScheduledExecutorService) atomicReference.get()).schedule(scheduledDirectTask, j, timeUnit));
            return scheduledDirectTask;
        } catch (RejectedExecutionException e) {
            RxJavaPlugins.onError(e);
            return EmptyDisposable.INSTANCE;
        }
    }

    public SingleScheduler(ThreadFactory threadFactory) {
        AtomicReference atomicReference = new AtomicReference();
        this.executor = atomicReference;
        boolean z = SchedulerPoolFactory.PURGE_ENABLED;
        ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(1, threadFactory);
        if (SchedulerPoolFactory.PURGE_ENABLED && (newScheduledThreadPool instanceof ScheduledThreadPoolExecutor)) {
            ((ConcurrentHashMap) SchedulerPoolFactory.POOLS).put((ScheduledThreadPoolExecutor) newScheduledThreadPool, newScheduledThreadPool);
        }
        atomicReference.lazySet(newScheduledThreadPool);
    }
}
