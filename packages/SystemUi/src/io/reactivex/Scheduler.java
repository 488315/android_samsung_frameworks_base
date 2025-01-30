package io.reactivex;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.NewThreadWorker;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class Scheduler {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DisposeTask implements Disposable, Runnable {
        public final Runnable decoratedRun;
        public Thread runner;

        /* renamed from: w */
        public final Worker f652w;

        public DisposeTask(Runnable runnable, Worker worker) {
            this.decoratedRun = runnable;
            this.f652w = worker;
        }

        @Override // io.reactivex.disposables.Disposable
        public final void dispose() {
            if (this.runner == Thread.currentThread()) {
                Worker worker = this.f652w;
                if (worker instanceof NewThreadWorker) {
                    NewThreadWorker newThreadWorker = (NewThreadWorker) worker;
                    if (newThreadWorker.disposed) {
                        return;
                    }
                    newThreadWorker.disposed = true;
                    newThreadWorker.executor.shutdown();
                    return;
                }
            }
            this.f652w.dispose();
        }

        @Override // java.lang.Runnable
        public final void run() {
            this.runner = Thread.currentThread();
            try {
                this.decoratedRun.run();
            } finally {
                dispose();
                this.runner = null;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class Worker implements Disposable {
        public abstract Disposable schedule(Runnable runnable, long j, TimeUnit timeUnit);

        public void schedule(Runnable runnable) {
            schedule(runnable, 0L, TimeUnit.NANOSECONDS);
        }
    }

    static {
        TimeUnit.MINUTES.toNanos(Long.getLong("rx2.scheduler.drift-tolerance", 15L).longValue());
    }

    public abstract Worker createWorker();

    public Disposable scheduleDirect(Runnable runnable, long j, TimeUnit timeUnit) {
        Worker createWorker = createWorker();
        RxJavaPlugins.onSchedule(runnable);
        DisposeTask disposeTask = new DisposeTask(runnable, createWorker);
        createWorker.schedule(disposeTask, j, timeUnit);
        return disposeTask;
    }
}
