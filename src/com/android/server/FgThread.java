package com.android.server;

import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import java.util.concurrent.Executor;

/* loaded from: classes6.dex */
public final class FgThread extends ServiceThread {
    private static final long SLOW_DELIVERY_THRESHOLD_MS = 200;
    private static final long SLOW_DISPATCH_THRESHOLD_MS = 100;
    private static Handler sHandler;
    private static HandlerExecutor sHandlerExecutor;
    private static FgThread sInstance;

    private FgThread() {
        super("android.fg", 0, true);
    }

    private static void ensureThreadLocked() {
        if (sInstance == null) {
            FgThread fgThread = new FgThread();
            sInstance = fgThread;
            fgThread.start();
            Looper looper = sInstance.getLooper();
            looper.setTraceTag(524288L);
            looper.setSlowLogThresholdMs(SLOW_DISPATCH_THRESHOLD_MS, 200L);
            sHandler = makeSharedHandler(sInstance.getLooper());
            sHandlerExecutor = new HandlerExecutor(sHandler);
        }
    }

    public static FgThread get() {
        FgThread fgThread;
        synchronized (FgThread.class) {
            ensureThreadLocked();
            fgThread = sInstance;
        }
        return fgThread;
    }

    public static Handler getHandler() {
        Handler handler;
        synchronized (FgThread.class) {
            ensureThreadLocked();
            handler = sHandler;
        }
        return handler;
    }

    public static Executor getExecutor() {
        HandlerExecutor handlerExecutor;
        synchronized (FgThread.class) {
            ensureThreadLocked();
            handlerExecutor = sHandlerExecutor;
        }
        return handlerExecutor;
    }
}
