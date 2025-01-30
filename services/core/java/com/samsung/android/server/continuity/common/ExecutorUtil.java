package com.samsung.android.server.continuity.common;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public abstract class ExecutorUtil {
    public static ThreadPoolExecutor sExecutorIO;
    public static Handler sHandler;

    public static void start() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(64, 64, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), new ThreadFactory() { // from class: com.samsung.android.server.continuity.common.ExecutorUtil$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                Thread lambda$start$0;
                lambda$start$0 = ExecutorUtil.lambda$start$0(runnable);
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

    public static void executeOnIO(Runnable runnable) {
        sExecutorIO.execute(wrapRunnable(runnable));
    }

    public static void executeOnMain(Runnable runnable) {
        if (Looper.getMainLooper().equals(Looper.myLooper())) {
            runnable.run();
        } else {
            sHandler.post(runnable);
        }
    }

    public static ThrowExceptionRunnable wrapRunnable(Runnable runnable) {
        return new ThrowExceptionRunnable(runnable);
    }

    public class ThrowExceptionRunnable implements Runnable {
        public final Runnable mTask;

        public ThrowExceptionRunnable(Runnable runnable) {
            this.mTask = runnable;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.mTask.run();
            } catch (Throwable th) {
                Log.w("[MCF_DS_SYS]_ExecutorUtil", "run - Error in running " + this.mTask.toString() + " on " + Thread.currentThread().getName() + ", " + th.getMessage());
                th.printStackTrace();
            }
        }
    }
}
