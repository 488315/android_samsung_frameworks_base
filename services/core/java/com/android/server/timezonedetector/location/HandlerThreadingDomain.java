package com.android.server.timezonedetector.location;

import android.os.Handler;

import com.android.internal.util.Preconditions;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class HandlerThreadingDomain {
    public final Handler mHandler;
    public final Object mLockObject = new Object();

    public HandlerThreadingDomain(Handler handler) {
        Objects.requireNonNull(handler);
        this.mHandler = handler;
    }

    public final void assertCurrentThread() {
        Preconditions.checkState(Thread.currentThread() == this.mHandler.getLooper().getThread());
    }

    public final Object postAndWait(final Callable callable, long j) {
        Thread currentThread = Thread.currentThread();
        Handler handler = this.mHandler;
        Preconditions.checkState(currentThread != handler.getLooper().getThread());
        final AtomicReference atomicReference = new AtomicReference();
        final AtomicReference atomicReference2 = new AtomicReference();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        handler.post(
                new Runnable() { // from class:
                                 // com.android.server.timezonedetector.location.HandlerThreadingDomain$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AtomicReference atomicReference3 = atomicReference;
                        Callable callable2 = callable;
                        AtomicReference atomicReference4 = atomicReference2;
                        CountDownLatch countDownLatch2 = countDownLatch;
                        try {
                            try {
                                atomicReference3.set(callable2.call());
                            } catch (Exception e) {
                                atomicReference4.set(e);
                            }
                        } finally {
                            countDownLatch2.countDown();
                        }
                    }
                });
        try {
            if (!countDownLatch.await(j, TimeUnit.MILLISECONDS)) {
                throw new RuntimeException("Timed out");
            }
            if (atomicReference2.get() == null) {
                return atomicReference.get();
            }
            throw ((Exception) atomicReference2.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public final void postAndWait(final Runnable runnable, long j) {
        try {
            postAndWait(
                    new Callable() { // from class:
                                     // com.android.server.timezonedetector.location.ThreadingDomain$$ExternalSyntheticLambda0
                        @Override // java.util.concurrent.Callable
                        public final Object call() {
                            runnable.run();
                            return null;
                        }
                    },
                    j);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
