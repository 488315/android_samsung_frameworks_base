package com.android.systemui.util.concurrency;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import java.util.concurrent.Executor;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class ThreadFactoryImpl implements ThreadFactory {
    @Override // com.android.systemui.util.concurrency.ThreadFactory
    public DelayableExecutor buildDelayableExecutorOnHandler(Handler handler) {
        return buildDelayableExecutorOnLooper(handler.getLooper());
    }

    @Override // com.android.systemui.util.concurrency.ThreadFactory
    public DelayableExecutor buildDelayableExecutorOnLooper(Looper looper) {
        return new ExecutorImpl(looper);
    }

    @Override // com.android.systemui.util.concurrency.ThreadFactory
    public DelayableExecutor buildDelayableExecutorOnNewThread(String str) {
        HandlerThread handlerThread = new HandlerThread(str);
        handlerThread.start();
        return buildDelayableExecutorOnLooper(handlerThread.getLooper());
    }

    @Override // com.android.systemui.util.concurrency.ThreadFactory
    public Executor buildExecutorOnNewThread(String str) {
        return buildDelayableExecutorOnNewThread(str);
    }

    @Override // com.android.systemui.util.concurrency.ThreadFactory
    public Handler buildHandlerOnNewThread(String str) {
        return new Handler(buildLooperOnNewThread(str));
    }

    @Override // com.android.systemui.util.concurrency.ThreadFactory
    public Looper buildLooperOnNewThread(String str) {
        HandlerThread handlerThread = new HandlerThread(str);
        handlerThread.start();
        return handlerThread.getLooper();
    }
}
