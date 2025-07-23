package com.android.settingslib.utils;

import android.os.Handler;
import android.os.Looper;
import com.google.common.util.concurrent.AbstractListeningExecutorService;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.concurrent.Executors;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ThreadUtils {
    public static volatile ListeningExecutorService sListeningService;
    public static volatile Thread sMainThread;
    public static volatile Handler sMainThreadHandler;

    public static synchronized ListeningExecutorService getBackgroundExecutor() {
        ListeningExecutorService listeningExecutorService;
        synchronized (ThreadUtils.class) {
            try {
                if (sListeningService == null) {
                    sListeningService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
                }
                listeningExecutorService = sListeningService;
            } catch (Throwable th) {
                throw th;
            }
        }
        return listeningExecutorService;
    }

    public static boolean isMainThread() {
        if (sMainThread == null) {
            sMainThread = Looper.getMainLooper().getThread();
        }
        return Thread.currentThread() == sMainThread;
    }

    public static ListenableFuture postOnBackgroundThread(Runnable runnable) {
        return ((AbstractListeningExecutorService) getBackgroundExecutor()).submit(runnable);
    }

    public static void postOnMainThread(Runnable runnable) {
        if (sMainThreadHandler == null) {
            sMainThreadHandler = new Handler(Looper.getMainLooper());
        }
        sMainThreadHandler.post(runnable);
    }
}
