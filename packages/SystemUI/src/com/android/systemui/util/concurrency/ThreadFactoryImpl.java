package com.android.systemui.util.concurrency;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import java.util.concurrent.Executor;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
