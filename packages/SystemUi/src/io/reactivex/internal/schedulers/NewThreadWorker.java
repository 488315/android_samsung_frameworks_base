package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableContainer;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class NewThreadWorker extends Scheduler.Worker {
    public volatile boolean disposed;
    public final ScheduledExecutorService executor;

    public NewThreadWorker(ThreadFactory threadFactory) {
        boolean z = SchedulerPoolFactory.PURGE_ENABLED;
        ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(1, threadFactory);
        if (SchedulerPoolFactory.PURGE_ENABLED && (newScheduledThreadPool instanceof ScheduledThreadPoolExecutor)) {
            ((ConcurrentHashMap) SchedulerPoolFactory.POOLS).put((ScheduledThreadPoolExecutor) newScheduledThreadPool, newScheduledThreadPool);
        }
        this.executor = newScheduledThreadPool;
    }

    @Override // io.reactivex.disposables.Disposable
    public final void dispose() {
        if (this.disposed) {
            return;
        }
        this.disposed = true;
        this.executor.shutdownNow();
    }

    @Override // io.reactivex.Scheduler.Worker
    public final void schedule(Runnable runnable) {
        schedule(runnable, 0L, null);
    }

    public final ScheduledRunnable scheduleActual(Runnable runnable, long j, TimeUnit timeUnit, DisposableContainer disposableContainer) {
        RxJavaPlugins.onSchedule(runnable);
        ScheduledRunnable scheduledRunnable = new ScheduledRunnable(runnable, disposableContainer);
        if (disposableContainer != null && !disposableContainer.add(scheduledRunnable)) {
            return scheduledRunnable;
        }
        ScheduledExecutorService scheduledExecutorService = this.executor;
        try {
            scheduledRunnable.setFuture(j <= 0 ? scheduledExecutorService.submit((Callable) scheduledRunnable) : scheduledExecutorService.schedule((Callable) scheduledRunnable, j, timeUnit));
        } catch (RejectedExecutionException e) {
            if (disposableContainer != null) {
                disposableContainer.remove(scheduledRunnable);
            }
            RxJavaPlugins.onError(e);
        }
        return scheduledRunnable;
    }

    @Override // io.reactivex.Scheduler.Worker
    public final Disposable schedule(Runnable runnable, long j, TimeUnit timeUnit) {
        return this.disposed ? EmptyDisposable.INSTANCE : scheduleActual(runnable, j, timeUnit, null);
    }
}
