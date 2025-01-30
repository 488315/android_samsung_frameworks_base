package com.samsung.context.sdk.samsunganalytics.internal.executor;

import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SingleThreadExecutor {
    public static ExecutorService executorService;
    public static SingleThreadExecutor singleThreadExecutor;

    public SingleThreadExecutor() {
        executorService = Executors.newSingleThreadExecutor(new ThreadFactory(this) { // from class: com.samsung.context.sdk.samsunganalytics.internal.executor.SingleThreadExecutor.1
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable);
                thread.setPriority(1);
                thread.setDaemon(true);
                Debug.LogD("newThread on Executor");
                return thread;
            }
        });
    }

    public static SingleThreadExecutor getInstance() {
        if (singleThreadExecutor == null) {
            singleThreadExecutor = new SingleThreadExecutor();
        }
        return singleThreadExecutor;
    }

    public final void execute(final AsyncTaskClient asyncTaskClient) {
        executorService.submit(new Runnable(this) { // from class: com.samsung.context.sdk.samsunganalytics.internal.executor.SingleThreadExecutor.2
            @Override // java.lang.Runnable
            public final void run() {
                asyncTaskClient.run();
                asyncTaskClient.onFinish();
            }
        });
    }
}
