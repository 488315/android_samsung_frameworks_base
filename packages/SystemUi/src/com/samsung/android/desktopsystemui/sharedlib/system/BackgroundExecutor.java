package com.samsung.android.desktopsystemui.sharedlib.system;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class BackgroundExecutor {
    private static final BackgroundExecutor sInstance = new BackgroundExecutor();
    private final ExecutorService mExecutorService = Executors.newFixedThreadPool(2);

    public static BackgroundExecutor get() {
        return sInstance;
    }

    public <T> Future<T> submit(Callable<T> callable) {
        return this.mExecutorService.submit(callable);
    }

    public Future<?> submit(Runnable runnable) {
        return this.mExecutorService.submit(runnable);
    }

    public <T> Future<T> submit(Runnable runnable, T t) {
        return this.mExecutorService.submit(runnable, t);
    }
}
