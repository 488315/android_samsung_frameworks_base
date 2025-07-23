package com.google.android.setupcompat.internal;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ExecutorProvider {
    public static final ExecutorProvider setupCompatServiceInvoker = new ExecutorProvider(createSizeBoundedExecutor("SetupCompatServiceInvoker", 50));
    public final Executor executor;
    public Executor injectedExecutor;

    private ExecutorProvider(Executor executor) {
        this.executor = executor;
    }

    public static ExecutorService createSizeBoundedExecutor(final String str, int i) {
        return new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS, new ArrayBlockingQueue(i), new ThreadFactory() { // from class: com.google.android.setupcompat.internal.ExecutorProvider$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                String str2 = str;
                ExecutorProvider executorProvider = ExecutorProvider.setupCompatServiceInvoker;
                return new Thread(runnable, str2);
            }
        });
    }

    public static void resetExecutors() {
        setupCompatServiceInvoker.injectedExecutor = null;
    }

    public void injectExecutor(Executor executor) {
        this.injectedExecutor = executor;
    }
}
