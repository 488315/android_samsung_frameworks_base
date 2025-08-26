package com.sec.android.diagmonagent.common.util.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/* loaded from: classes4.dex */
public class SingleThreadExecutor {
    public static ExecutorService executorService;
    public static SingleThreadExecutor singleThreadExecutor;

    public SingleThreadExecutor() {
        executorService = Executors.newSingleThreadExecutor(new ThreadFactory(this) { // from class: com.sec.android.diagmonagent.common.util.executor.SingleThreadExecutor.1
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable);
                thread.setPriority(1);
                thread.setDaemon(true);
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
        executorService.submit(new Runnable(this) { // from class: com.sec.android.diagmonagent.common.util.executor.SingleThreadExecutor.2
            @Override // java.lang.Runnable
            public final void run() {
                asyncTaskClient.run();
                asyncTaskClient.onFinish();
            }
        });
    }
}
