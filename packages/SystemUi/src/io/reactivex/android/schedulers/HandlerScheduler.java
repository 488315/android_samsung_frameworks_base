package io.reactivex.android.schedulers;

import android.os.Handler;
import android.os.Message;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class HandlerScheduler extends Scheduler {
    public final boolean async;
    public final Handler handler;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class HandlerWorker extends Scheduler.Worker {
        public final boolean async;
        public volatile boolean disposed;
        public final Handler handler;

        public HandlerWorker(Handler handler, boolean z) {
            this.handler = handler;
            this.async = z;
        }

        @Override // io.reactivex.disposables.Disposable
        public final void dispose() {
            this.disposed = true;
            this.handler.removeCallbacksAndMessages(this);
        }

        @Override // io.reactivex.Scheduler.Worker
        public final Disposable schedule(Runnable runnable, long j, TimeUnit timeUnit) {
            if (runnable == null) {
                throw new NullPointerException("run == null");
            }
            if (timeUnit == null) {
                throw new NullPointerException("unit == null");
            }
            if (this.disposed) {
                return EmptyDisposable.INSTANCE;
            }
            RxJavaPlugins.onSchedule(runnable);
            ScheduledRunnable scheduledRunnable = new ScheduledRunnable(this.handler, runnable);
            Message obtain = Message.obtain(this.handler, scheduledRunnable);
            obtain.obj = this;
            if (this.async) {
                obtain.setAsynchronous(true);
            }
            this.handler.sendMessageDelayed(obtain, timeUnit.toMillis(j));
            if (!this.disposed) {
                return scheduledRunnable;
            }
            this.handler.removeCallbacks(scheduledRunnable);
            return EmptyDisposable.INSTANCE;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ScheduledRunnable implements Runnable, Disposable {
        public final Runnable delegate;
        public final Handler handler;

        public ScheduledRunnable(Handler handler, Runnable runnable) {
            this.handler = handler;
            this.delegate = runnable;
        }

        @Override // io.reactivex.disposables.Disposable
        public final void dispose() {
            this.handler.removeCallbacks(this);
        }

        @Override // java.lang.Runnable
        public final void run() {
            try {
                this.delegate.run();
            } catch (Throwable th) {
                RxJavaPlugins.onError(th);
            }
        }
    }

    public HandlerScheduler(Handler handler, boolean z) {
        this.handler = handler;
        this.async = z;
    }

    @Override // io.reactivex.Scheduler
    public final Scheduler.Worker createWorker() {
        return new HandlerWorker(this.handler, this.async);
    }

    @Override // io.reactivex.Scheduler
    public final Disposable scheduleDirect(Runnable runnable, long j, TimeUnit timeUnit) {
        if (timeUnit == null) {
            throw new NullPointerException("unit == null");
        }
        RxJavaPlugins.onSchedule(runnable);
        Handler handler = this.handler;
        ScheduledRunnable scheduledRunnable = new ScheduledRunnable(handler, runnable);
        Message obtain = Message.obtain(handler, scheduledRunnable);
        if (this.async) {
            obtain.setAsynchronous(true);
        }
        handler.sendMessageDelayed(obtain, timeUnit.toMillis(j));
        return scheduledRunnable;
    }
}
