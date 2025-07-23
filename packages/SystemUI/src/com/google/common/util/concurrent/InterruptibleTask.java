package com.google.common.util.concurrent;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.AbstractOwnableSynchronizer;
import java.util.concurrent.locks.LockSupport;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
abstract class InterruptibleTask<T> extends AtomicReference<Runnable> implements Runnable {
    public static final DoNothingRunnable DONE;
    public static final DoNothingRunnable PARKED;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class Blocker extends AbstractOwnableSynchronizer implements Runnable {
        private final InterruptibleTask<?> task;

        public final String toString() {
            return this.task.toString();
        }

        private Blocker(InterruptibleTask<?> interruptibleTask) {
            this.task = interruptibleTask;
        }

        @Override // java.lang.Runnable
        public final void run() {
        }
    }

    static {
        DONE = new DoNothingRunnable();
        PARKED = new DoNothingRunnable();
    }

    public abstract void afterRanInterruptiblyFailure(Throwable th);

    public abstract void afterRanInterruptiblySuccess(Object obj);

    public abstract boolean isDone();

    @Override // java.lang.Runnable
    public final void run() {
        Thread currentThread = Thread.currentThread();
        Object obj = null;
        if (compareAndSet(null, currentThread)) {
            boolean isDone = isDone();
            if (!isDone) {
                try {
                    obj = runInterruptibly();
                } catch (Throwable th) {
                    try {
                        if (th instanceof InterruptedException) {
                            Thread.currentThread().interrupt();
                        }
                        if (!compareAndSet(currentThread, DONE)) {
                            waitForInterrupt(currentThread);
                        }
                        if (isDone) {
                            return;
                        }
                        afterRanInterruptiblyFailure(th);
                        return;
                    } finally {
                        if (!compareAndSet(currentThread, DONE)) {
                            waitForInterrupt(currentThread);
                        }
                        if (!isDone) {
                            afterRanInterruptiblySuccess(null);
                        }
                    }
                }
            }
        }
    }

    public abstract Object runInterruptibly();

    public abstract String toPendingString();

    @Override // java.util.concurrent.atomic.AtomicReference
    public final String toString() {
        String str;
        Runnable runnable = get();
        if (runnable == DONE) {
            str = "running=[DONE]";
        } else if (runnable instanceof Blocker) {
            str = "running=[INTERRUPTED]";
        } else if (runnable instanceof Thread) {
            str = "running=[RUNNING ON " + ((Thread) runnable).getName() + "]";
        } else {
            str = "running=[NOT STARTED YET]";
        }
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, ", ");
        m.append(toPendingString());
        return m.toString();
    }

    public final void waitForInterrupt(Thread thread) {
        Runnable runnable = get();
        Blocker blocker = null;
        boolean z = false;
        int i = 0;
        while (true) {
            boolean z2 = runnable instanceof Blocker;
            if (!z2 && runnable != PARKED) {
                break;
            }
            if (z2) {
                blocker = (Blocker) runnable;
            }
            i++;
            if (i > 1000) {
                DoNothingRunnable doNothingRunnable = PARKED;
                if (runnable == doNothingRunnable || compareAndSet(runnable, doNothingRunnable)) {
                    z = Thread.interrupted() || z;
                    LockSupport.park(blocker);
                }
            } else {
                Thread.yield();
            }
            runnable = get();
        }
        if (z) {
            thread.interrupt();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DoNothingRunnable implements Runnable {
        private DoNothingRunnable() {
        }

        @Override // java.lang.Runnable
        public final void run() {
        }
    }
}
