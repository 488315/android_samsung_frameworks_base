package com.google.common.util.concurrent;

import com.google.common.util.concurrent.FluentFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes4.dex */
public final class TimeoutFuture extends FluentFuture.TrustedFuture {
    public ListenableFuture delegateRef;
    public ScheduledFuture timer;

    public final class Fire implements Runnable {
        public TimeoutFuture timeoutFutureRef;

        public Fire(TimeoutFuture timeoutFuture) {
            this.timeoutFutureRef = timeoutFuture;
        }

        @Override // java.lang.Runnable
        public final void run() {
            ListenableFuture listenableFuture;
            TimeoutFuture timeoutFuture = this.timeoutFutureRef;
            if (timeoutFuture == null || (listenableFuture = timeoutFuture.delegateRef) == null) {
                return;
            }
            this.timeoutFutureRef = null;
            if (listenableFuture.isDone()) {
                timeoutFuture.setFuture(listenableFuture);
                return;
            }
            try {
                ScheduledFuture scheduledFuture = timeoutFuture.timer;
                timeoutFuture.timer = null;
                String str = "Timed out";
                if (scheduledFuture != null) {
                    try {
                        long jAbs = Math.abs(scheduledFuture.getDelay(TimeUnit.MILLISECONDS));
                        if (jAbs > 10) {
                            str = "Timed out (timeout delayed by " + jAbs + " ms after scheduled time)";
                        }
                    } catch (Throwable th) {
                        timeoutFuture.setException(new TimeoutFutureException(str));
                        throw th;
                    }
                }
                timeoutFuture.setException(new TimeoutFutureException(str + ": " + listenableFuture));
            } finally {
                listenableFuture.cancel(true);
            }
        }
    }

    final class TimeoutFutureException extends TimeoutException {
        @Override // java.lang.Throwable
        public final synchronized Throwable fillInStackTrace() {
            setStackTrace(new StackTraceElement[0]);
            return this;
        }

        private TimeoutFutureException(String str) {
            super(str);
        }
    }

    private TimeoutFuture(ListenableFuture listenableFuture) {
        listenableFuture.getClass();
        this.delegateRef = listenableFuture;
    }

    public static TimeoutFuture create(SettableFuture settableFuture, ScheduledExecutorService scheduledExecutorService) {
        TimeUnit timeUnit = TimeUnit.SECONDS;
        TimeoutFuture timeoutFuture = new TimeoutFuture(settableFuture);
        Fire fire = new Fire(timeoutFuture);
        timeoutFuture.timer = scheduledExecutorService.schedule(fire, 5L, timeUnit);
        settableFuture.addListener(fire, DirectExecutor.INSTANCE);
        return timeoutFuture;
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    public final void afterDone() {
        maybePropagateCancellationTo(this.delegateRef);
        ScheduledFuture scheduledFuture = this.timer;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
        this.delegateRef = null;
        this.timer = null;
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    public final String pendingToString() {
        ListenableFuture listenableFuture = this.delegateRef;
        ScheduledFuture scheduledFuture = this.timer;
        if (listenableFuture == null) {
            return null;
        }
        String str = "inputFuture=[" + listenableFuture + "]";
        if (scheduledFuture != null) {
            long delay = scheduledFuture.getDelay(TimeUnit.MILLISECONDS);
            if (delay > 0) {
                return str + ", remaining delay=[" + delay + " ms]";
            }
        }
        return str;
    }
}
