package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableContainer;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
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
        int i = ObjectHelper.$r8$clinit;
        ScheduledRunnable scheduledRunnable = new ScheduledRunnable(runnable, disposableContainer);
        if (disposableContainer == null || disposableContainer.add(scheduledRunnable)) {
            try {
                scheduledRunnable.setFuture(j <= 0 ? this.executor.submit((Callable) scheduledRunnable) : this.executor.schedule((Callable) scheduledRunnable, j, timeUnit));
                return scheduledRunnable;
            } catch (RejectedExecutionException e) {
                if (disposableContainer != null) {
                    disposableContainer.remove(scheduledRunnable);
                }
                RxJavaPlugins.onError(e);
            }
        }
        return scheduledRunnable;
    }

    @Override // io.reactivex.Scheduler.Worker
    public final Disposable schedule(Runnable runnable, long j, TimeUnit timeUnit) {
        return this.disposed ? EmptyDisposable.INSTANCE : scheduleActual(runnable, j, timeUnit, null);
    }
}
