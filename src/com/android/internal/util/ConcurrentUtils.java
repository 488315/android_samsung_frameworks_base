package com.android.internal.util;

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

/* loaded from: classes4.dex */
public class ConcurrentUtils {
    public static final Executor DIRECT_EXECUTOR = new DirectExecutor();

    private ConcurrentUtils() {
    }

    public static ExecutorService newFixedThreadPool(int i, final String str, final int i2) {
        return Executors.newFixedThreadPool(i, new ThreadFactory() { // from class: com.android.internal.util.ConcurrentUtils.1
            private final AtomicInteger threadNum = new AtomicInteger(0);

            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(final Runnable runnable) {
                return new Thread(str + this.threadNum.incrementAndGet()) { // from class: com.android.internal.util.ConcurrentUtils.1.1
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() throws SecurityException, IllegalArgumentException {
                        Process.setThreadPriority(i2);
                        runnable.run();
                    }
                };
            }
        });
    }

    public static <T> T waitForFutureNoInterrupt(Future<T> future, String str) {
        try {
            return future.get();
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(str + " interrupted");
        } catch (ExecutionException e) {
            throw new RuntimeException(str + " failed", e);
        }
    }

    public static void waitForCountDownNoInterrupt(CountDownLatch countDownLatch, long j, String str) {
        try {
            if (countDownLatch.await(j, TimeUnit.MILLISECONDS)) {
                return;
            }
            throw new IllegalStateException(str + " timed out.");
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(str + " interrupted.");
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

    private static class DirectExecutor implements Executor {
        private DirectExecutor() {
        }

        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            runnable.run();
        }

        public String toString() {
            return "DIRECT_EXECUTOR";
        }
    }
}
