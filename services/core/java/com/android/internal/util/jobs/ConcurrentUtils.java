package com.android.internal.util.jobs;

import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Process;
import android.util.Slog;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentUtils {
    public static final Executor DIRECT_EXECUTOR = new DirectExecutor();

    public final class DirectExecutor implements Executor {
        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            runnable.run();
        }

        public final String toString() {
            return "DIRECT_EXECUTOR";
        }
    }

    private ConcurrentUtils() {}

    public static ExecutorService newFixedThreadPool(int i, final String str, final int i2) {
        return Executors.newFixedThreadPool(
                i,
                new ThreadFactory() { // from class:
                                      // com.android.internal.util.jobs.ConcurrentUtils.1
                    public final AtomicInteger threadNum = new AtomicInteger(0);

                    @Override // java.util.concurrent.ThreadFactory
                    public final Thread newThread(final Runnable runnable) {
                        return new Thread(
                                str
                                        + this.threadNum
                                                .incrementAndGet()) { // from class:
                                                                      // com.android.internal.util.jobs.ConcurrentUtils.1.1
                            @Override // java.lang.Thread, java.lang.Runnable
                            public final void run() {
                                Process.setThreadPriority(i2);
                                runnable.run();
                            }
                        };
                    }
                });
    }

    public static void waitForCountDownNoInterrupt(
            CountDownLatch countDownLatch, long j, String str) {
        try {
            if (countDownLatch.await(j, TimeUnit.MILLISECONDS)) {
                return;
            }
            throw new IllegalStateException(str + " timed out.");
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                            str, " interrupted."));
        }
    }

    public static Object waitForFutureNoInterrupt(Future future, String str) {
        try {
            return future.get();
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                            str, " interrupted"));
        } catch (ExecutionException e) {
            throw new RuntimeException(
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, " failed"), e);
        }
    }

    public static void wtfIfLockHeld(String str, Object obj) {
        if (Thread.holdsLock(obj)) {
            Slog.wtf(str, "Lock mustn't be held");
        }
    }

    public static void wtfIfLockNotHeld(String str, Object obj) {
        if (Thread.holdsLock(obj)) {
            return;
        }
        Slog.wtf(str, "Lock must be held");
    }
}
