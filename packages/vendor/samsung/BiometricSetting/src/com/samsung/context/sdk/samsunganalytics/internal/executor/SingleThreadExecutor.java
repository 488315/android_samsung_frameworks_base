package com.samsung.context.sdk.samsunganalytics.internal.executor;

import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public final class SingleThreadExecutor {
    public static ExecutorService executorService;
    public static SingleThreadExecutor singleThreadExecutor;

    /* renamed from: com.samsung.context.sdk.samsunganalytics.internal.executor.SingleThreadExecutor$1, reason: invalid class name */
    public final class AnonymousClass1 implements ThreadFactory {
        @Override // java.util.concurrent.ThreadFactory
        public final Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setPriority(1);
            thread.setDaemon(true);
            Debug.LogD("newThread on Executor");
            return thread;
        }
    }

    public static void execute(final AsyncTaskClient asyncTaskClient) {
        executorService.submit(
                new Runnable() { // from class:
                                 // com.samsung.context.sdk.samsunganalytics.internal.executor.SingleThreadExecutor.2
                    @Override // java.lang.Runnable
                    public final void run() {
                        AsyncTaskClient.this.run();
                        AsyncTaskClient.this.onFinish();
                    }
                });
    }

    public static SingleThreadExecutor getInstance() {
        if (singleThreadExecutor == null) {
            SingleThreadExecutor singleThreadExecutor2 = new SingleThreadExecutor();
            executorService = Executors.newSingleThreadExecutor(new AnonymousClass1());
            singleThreadExecutor = singleThreadExecutor2;
        }
        return singleThreadExecutor;
    }
}
