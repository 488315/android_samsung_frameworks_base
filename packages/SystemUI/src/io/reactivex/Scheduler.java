package io.reactivex;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.schedulers.NewThreadWorker;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public abstract class Scheduler {

    public final class DisposeTask implements Disposable, Runnable {
        public final Runnable decoratedRun;
        public Thread runner;
        public final Worker w;

        public DisposeTask(Runnable runnable, Worker worker) {
            this.decoratedRun = runnable;
            this.w = worker;
        }

        @Override // io.reactivex.disposables.Disposable
        public final void dispose() {
            if (this.runner == Thread.currentThread()) {
                Worker worker = this.w;
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
            this.w.dispose();
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
        Worker workerCreateWorker = createWorker();
        int i = ObjectHelper.$r8$clinit;
        DisposeTask disposeTask = new DisposeTask(runnable, workerCreateWorker);
        workerCreateWorker.schedule(disposeTask, j, timeUnit);
        return disposeTask;
    }
}
