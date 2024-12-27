package com.android.systemui.util.concurrency;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.Choreographer;
import com.android.systemui.Flags;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function0;

public final class SysUIConcurrencyModule {
    public static final int $stable = 0;
    private static final long BG_SLOW_DELIVERY_THRESHOLD = 1000;
    private static final long BG_SLOW_DISPATCH_THRESHOLD = 1000;
    private static final long BROADCAST_SLOW_DELIVERY_THRESHOLD = 1000;
    private static final long BROADCAST_SLOW_DISPATCH_THRESHOLD = 1000;
    public static final SysUIConcurrencyModule INSTANCE = new SysUIConcurrencyModule();
    private static final long LONG_SLOW_DELIVERY_THRESHOLD = 2500;
    private static final long LONG_SLOW_DISPATCH_THRESHOLD = 2500;
    private static final long NOTIFICATION_INFLATION_SLOW_DELIVERY_THRESHOLD = 1000;
    private static final long NOTIFICATION_INFLATION_SLOW_DISPATCH_THRESHOLD = 1000;

    private SysUIConcurrencyModule() {
    }

    @BackPanelUiThread
    public final UiThreadContext provideBackPanelUiThreadContext(Looper looper, Handler handler, Executor executor) {
        Flags.FEATURE_FLAGS.getClass();
        return new UiThreadContext(looper, handler, executor, (Choreographer) UiThreadContextKt.runWithScissors(handler, new Function0() { // from class: com.android.systemui.util.concurrency.SysUIConcurrencyModule$provideBackPanelUiThreadContext$2
            @Override // kotlin.jvm.functions.Function0
            public final Choreographer invoke() {
                return Choreographer.getInstance();
            }
        }));
    }

    public final DelayableExecutor provideBackgroundDelayableExecutor(Looper looper) {
        return new ExecutorImpl(looper);
    }

    public final Executor provideBackgroundExecutor(Looper looper) {
        return new ExecutorImpl(looper);
    }

    public final RepeatableExecutor provideBackgroundRepeatableExecutor(DelayableExecutor delayableExecutor) {
        return new RepeatableExecutorImpl(delayableExecutor);
    }

    public final Handler provideBgHandler(Looper looper) {
        return new Handler(looper);
    }

    public final Looper provideBgLooper() {
        HandlerThread handlerThread = new HandlerThread("SysUiBg", 10);
        handlerThread.start();
        handlerThread.getLooper().setSlowLogThresholdMs(1000L, 1000L);
        return handlerThread.getLooper();
    }

    public final Executor provideBroadcastRunningExecutor(Looper looper) {
        return new ExecutorImpl(looper);
    }

    public final Looper provideBroadcastRunningLooper() {
        HandlerThread handlerThread = new HandlerThread("BroadcastRunning", 10);
        handlerThread.start();
        handlerThread.getLooper().setSlowLogThresholdMs(1000L, 1000L);
        return handlerThread.getLooper();
    }

    public final DelayableExecutor provideLongRunningDelayableExecutor(Looper looper) {
        return new ExecutorImpl(looper);
    }

    public final Executor provideLongRunningExecutor(Looper looper) {
        return new ExecutorImpl(looper);
    }

    public final Looper provideLongRunningLooper() {
        HandlerThread handlerThread = new HandlerThread("SysUiLng", 10);
        handlerThread.start();
        handlerThread.getLooper().setSlowLogThresholdMs(2500L, 2500L);
        return handlerThread.getLooper();
    }

    public final RepeatableExecutor provideMainRepeatableExecutor(DelayableExecutor delayableExecutor) {
        return new RepeatableExecutorImpl(delayableExecutor);
    }

    public final Executor provideNotifInflationExecutor(Looper looper) {
        return new ExecutorImpl(looper);
    }

    public final Looper provideNotifInflationLooper(Looper looper) {
        Flags.FEATURE_FLAGS.getClass();
        HandlerThread handlerThread = new HandlerThread("NotifInflation", 10);
        handlerThread.start();
        Looper looper2 = handlerThread.getLooper();
        looper2.setSlowLogThresholdMs(1000L, 1000L);
        return looper2;
    }

    public final Handler provideTimeTickHandler() {
        HandlerThread handlerThread = new HandlerThread("TimeTick");
        handlerThread.start();
        return new Handler(handlerThread.getLooper());
    }

    public final MessageRouter providesBackgroundMessageRouter(DelayableExecutor delayableExecutor) {
        return new MessageRouterImpl(delayableExecutor);
    }

    public final MessageRouter providesMainMessageRouter(DelayableExecutor delayableExecutor) {
        return new MessageRouterImpl(delayableExecutor);
    }
}
