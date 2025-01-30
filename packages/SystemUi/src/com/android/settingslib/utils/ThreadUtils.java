package com.android.settingslib.utils;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ThreadUtils {
    public static volatile Thread sMainThread;
    public static volatile Handler sMainThreadHandler;
    public static volatile ExecutorService sThreadExecutor;

    public static boolean isMainThread() {
        if (sMainThread == null) {
            sMainThread = Looper.getMainLooper().getThread();
        }
        return Thread.currentThread() == sMainThread;
    }

    public static Future postOnBackgroundThread(Runnable runnable) {
        ExecutorService executorService;
        synchronized (ThreadUtils.class) {
            if (sThreadExecutor == null) {
                sThreadExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            }
            executorService = sThreadExecutor;
        }
        return executorService.submit(runnable);
    }

    public static void postOnMainThread(Runnable runnable) {
        if (sMainThreadHandler == null) {
            sMainThreadHandler = new Handler(Looper.getMainLooper());
        }
        sMainThreadHandler.post(runnable);
    }
}
