package com.android.internal.telephony.util;

import android.os.Handler;
import android.os.HandlerThread;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public final class WorkerThread extends HandlerThread {
    private static volatile Handler sHandler;
    private static volatile android.os.HandlerExecutor sHandlerExecutor;
    private static volatile WorkerThread sInstance;
    private static final Object sLock = new Object();
    private CountDownLatch mInitLock;

    private WorkerThread() {
        super("android.telephony.worker");
        this.mInitLock = new CountDownLatch(1);
    }

    private static void ensureThread() {
        if (sInstance != null) {
            return;
        }
        synchronized (sLock) {
            if (sInstance != null) {
                return;
            }
            WorkerThread workerThread = new WorkerThread();
            workerThread.start();
            try {
                workerThread.mInitLock.await();
            } catch (InterruptedException unused) {
            }
            sHandler = new Handler(workerThread.getLooper(), null, false, true);
            sHandlerExecutor = new android.os.HandlerExecutor(sHandler);
            sInstance = workerThread;
        }
    }

    @Override // android.os.HandlerThread
    protected void onLooperPrepared() {
        this.mInitLock.countDown();
    }

    public static HandlerThread get() {
        ensureThread();
        return sInstance;
    }

    public static Handler getHandler() {
        ensureThread();
        return sHandler;
    }

    public static Executor getExecutor() {
        ensureThread();
        return sHandlerExecutor;
    }

    public static void reset() {
        synchronized (sLock) {
            if (sInstance == null) {
                return;
            }
            sInstance.quitSafely();
            sInstance = null;
            sHandler = null;
            sHandlerExecutor = null;
            ensureThread();
        }
    }
}
