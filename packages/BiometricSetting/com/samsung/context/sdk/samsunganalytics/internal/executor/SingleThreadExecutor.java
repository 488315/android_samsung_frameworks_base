package com.samsung.context.sdk.samsunganalytics.internal.executor;

import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/* loaded from: classes.dex */
public final class SingleThreadExecutor {
    private static ExecutorService executorService;
    private static SingleThreadExecutor singleThreadExecutor;

    /* renamed from: com.samsung.context.sdk.samsunganalytics.internal.executor.SingleThreadExecutor$1 */
    final class ThreadFactoryC03041 implements ThreadFactory {
        @Override // java.util.concurrent.ThreadFactory
        public final Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setPriority(1);
            thread.setDaemon(true);
            Debug.LogD("newThread on Executor");
            return thread;
        }
    }

    public SingleThreadExecutor() {
        executorService = Executors.newSingleThreadExecutor(new ThreadFactoryC03041());
    }

    public static SingleThreadExecutor getInstance() {
        if (singleThreadExecutor == null) {
            singleThreadExecutor = new SingleThreadExecutor();
        }
        return singleThreadExecutor;
    }

    public final void execute(final AsyncTaskClient asyncTaskClient) {
        executorService.submit(new Runnable() { // from class: com.samsung.context.sdk.samsunganalytics.internal.executor.SingleThreadExecutor.2
            @Override // java.lang.Runnable
            public final void run() {
                AsyncTaskClient.this.run();
                AsyncTaskClient.this.onFinish();
            }
        });
    }
}
