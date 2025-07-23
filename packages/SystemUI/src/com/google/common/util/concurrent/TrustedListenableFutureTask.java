package com.google.common.util.concurrent;

import com.google.common.util.concurrent.FluentFuture;
import com.google.common.util.concurrent.InterruptibleTask;
import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.locks.LockSupport;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class TrustedListenableFutureTask extends FluentFuture.TrustedFuture implements RunnableFuture {
    public volatile InterruptibleTask task;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class TrustedFutureInterruptibleAsyncTask extends InterruptibleTask<ListenableFuture> {
        private final AsyncCallable callable;

        public TrustedFutureInterruptibleAsyncTask(AsyncCallable asyncCallable) {
            asyncCallable.getClass();
        }

        @Override // com.google.common.util.concurrent.InterruptibleTask
        public final void afterRanInterruptiblyFailure(Throwable th) {
            TrustedListenableFutureTask.this.setException(th);
        }

        @Override // com.google.common.util.concurrent.InterruptibleTask
        public final void afterRanInterruptiblySuccess(Object obj) {
            TrustedListenableFutureTask.this.setFuture((ListenableFuture) obj);
        }

        @Override // com.google.common.util.concurrent.InterruptibleTask
        public final boolean isDone() {
            return TrustedListenableFutureTask.this.isDone();
        }

        @Override // com.google.common.util.concurrent.InterruptibleTask
        public final Object runInterruptibly() {
            throw null;
        }

        @Override // com.google.common.util.concurrent.InterruptibleTask
        public final String toPendingString() {
            throw null;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class TrustedFutureInterruptibleTask extends InterruptibleTask<Object> {
        private final Callable<Object> callable;

        public TrustedFutureInterruptibleTask(Callable<Object> callable) {
            callable.getClass();
            this.callable = callable;
        }

        @Override // com.google.common.util.concurrent.InterruptibleTask
        public final void afterRanInterruptiblyFailure(Throwable th) {
            TrustedListenableFutureTask.this.setException(th);
        }

        @Override // com.google.common.util.concurrent.InterruptibleTask
        public final void afterRanInterruptiblySuccess(Object obj) {
            TrustedListenableFutureTask.this.set(obj);
        }

        @Override // com.google.common.util.concurrent.InterruptibleTask
        public final boolean isDone() {
            return TrustedListenableFutureTask.this.isDone();
        }

        @Override // com.google.common.util.concurrent.InterruptibleTask
        public final Object runInterruptibly() {
            return this.callable.call();
        }

        @Override // com.google.common.util.concurrent.InterruptibleTask
        public final String toPendingString() {
            return this.callable.toString();
        }
    }

    public TrustedListenableFutureTask(Callable<Object> callable) {
        this.task = new TrustedFutureInterruptibleTask(callable);
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    public final void afterDone() {
        InterruptibleTask interruptibleTask;
        if (wasInterrupted() && (interruptibleTask = this.task) != null) {
            Runnable runnable = interruptibleTask.get();
            if (runnable instanceof Thread) {
                InterruptibleTask.Blocker blocker = new InterruptibleTask.Blocker();
                blocker.setExclusiveOwnerThread(Thread.currentThread());
                if (interruptibleTask.compareAndSet(runnable, blocker)) {
                    try {
                        ((Thread) runnable).interrupt();
                    } finally {
                        if (interruptibleTask.getAndSet(InterruptibleTask.DONE) == InterruptibleTask.PARKED) {
                            LockSupport.unpark((Thread) runnable);
                        }
                    }
                }
            }
        }
        this.task = null;
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    public final String pendingToString() {
        InterruptibleTask interruptibleTask = this.task;
        if (interruptibleTask == null) {
            return super.pendingToString();
        }
        return "task=[" + interruptibleTask + "]";
    }

    @Override // java.util.concurrent.RunnableFuture, java.lang.Runnable
    public final void run() {
        InterruptibleTask interruptibleTask = this.task;
        if (interruptibleTask != null) {
            interruptibleTask.run();
        }
        this.task = null;
    }

    public TrustedListenableFutureTask(AsyncCallable asyncCallable) {
        this.task = new TrustedFutureInterruptibleAsyncTask(asyncCallable);
    }
}
