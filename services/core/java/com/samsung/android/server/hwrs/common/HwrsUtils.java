package com.samsung.android.server.hwrs.common;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public abstract class HwrsUtils {
    public static ThreadPoolExecutor sExecutorIO;
    public static Handler sHandler;

    public static void start() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(64, 64, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), new ThreadFactory() { // from class: com.samsung.android.server.hwrs.common.HwrsUtils$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                Thread lambda$start$0;
                lambda$start$0 = HwrsUtils.lambda$start$0(runnable);
                return lambda$start$0;
            }
        });
        sExecutorIO = threadPoolExecutor;
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        sHandler = new Handler(Looper.getMainLooper());
    }

    public static /* synthetic */ Thread lambda$start$0(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setPriority(4);
        thread.setDaemon(true);
        thread.setName("Executor IO:" + thread.getId());
        return thread;
    }

    public static void executeOnMain(Runnable runnable) {
        if (Looper.getMainLooper().equals(Looper.myLooper())) {
            runnable.run();
        } else {
            sHandler.post(runnable);
        }
    }
}
