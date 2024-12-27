package com.android.systemui.util.concurrency;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public abstract class GlobalConcurrencyModule {
    public static final String PRE_HANDLER = "pre_handler";

    @Deprecated
    public static Handler provideHandler() {
        return new Handler();
    }

    public static DelayableExecutor provideMainDelayableExecutor(Looper looper) {
        return new ExecutorImpl(looper);
    }

    public static Executor provideMainExecutor(Context context) {
        return context.getMainExecutor();
    }

    public static Handler provideMainHandler(Looper looper) {
        return new Handler(looper);
    }

    public static Looper provideMainLooper() {
        return Looper.getMainLooper();
    }

    public static Executor provideUiBackgroundExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    public abstract ThreadFactory bindExecutorFactory(ThreadFactoryImpl threadFactoryImpl);

    public abstract Execution provideExecution(ExecutionImpl executionImpl);
}
