package com.android.server;

import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;

import java.util.concurrent.Executor;

public final class PermissionThread extends ServiceThread {
    public static Handler sHandler;
    public static HandlerExecutor sHandlerExecutor;
    public static PermissionThread sInstance;
    public static final Object sLock = new Object();

    public static void ensureThreadLocked() {
        if (sInstance != null) {
            return;
        }
        PermissionThread permissionThread = new PermissionThread(0, "android.perm", true);
        sInstance = permissionThread;
        permissionThread.start();
        Looper looper = sInstance.getLooper();
        looper.setTraceTag(524288L);
        looper.setSlowLogThresholdMs(100L, 200L);
        sHandler = new Handler(sInstance.getLooper());
        sHandlerExecutor = new HandlerExecutor(sHandler);
    }

    public static Executor getExecutor() {
        HandlerExecutor handlerExecutor;
        synchronized (sLock) {
            ensureThreadLocked();
            handlerExecutor = sHandlerExecutor;
        }
        return handlerExecutor;
    }

    public static Handler getHandler() {
        Handler handler;
        synchronized (sLock) {
            ensureThreadLocked();
            handler = sHandler;
        }
        return handler;
    }
}
