package com.android.server;

import android.os.Handler;
import android.os.HandlerExecutor;

import java.util.concurrent.Executor;

public final class IoThread extends ServiceThread {
    public static Handler sHandler;
    public static HandlerExecutor sHandlerExecutor;
    public static IoThread sInstance;

    public static void ensureThreadLocked() {
        if (sInstance == null) {
            IoThread ioThread = new IoThread(0, "android.io", true);
            sInstance = ioThread;
            ioThread.start();
            sInstance.getLooper().setTraceTag(524288L);
            sHandler = ServiceThread.makeSharedHandler(sInstance.getLooper());
            sHandlerExecutor = new HandlerExecutor(sHandler);
        }
    }

    public static IoThread get() {
        IoThread ioThread;
        synchronized (IoThread.class) {
            ensureThreadLocked();
            ioThread = sInstance;
        }
        return ioThread;
    }

    public static Executor getExecutor() {
        HandlerExecutor handlerExecutor;
        synchronized (IoThread.class) {
            ensureThreadLocked();
            handlerExecutor = sHandlerExecutor;
        }
        return handlerExecutor;
    }

    public static Handler getHandler() {
        Handler handler;
        synchronized (IoThread.class) {
            ensureThreadLocked();
            handler = sHandler;
        }
        return handler;
    }
}
