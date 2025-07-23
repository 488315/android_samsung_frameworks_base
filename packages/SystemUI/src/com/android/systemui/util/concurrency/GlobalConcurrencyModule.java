package com.android.systemui.util.concurrency;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class GlobalConcurrencyModule {
    public static final String PRE_HANDLER = "pre_handler";

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
        return provideUiBackgroundExecutorService();
    }

    public static ExecutorService provideUiBackgroundExecutorService() {
        return Executors.newSingleThreadExecutor();
    }

    public abstract ThreadFactory bindExecutorFactory(ThreadFactoryImpl threadFactoryImpl);

    public abstract Execution provideExecution(ExecutionImpl executionImpl);
}
