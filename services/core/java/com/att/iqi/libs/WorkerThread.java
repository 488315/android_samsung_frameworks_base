package com.att.iqi.libs;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class WorkerThread extends HandlerThread {
    private static final long SLOW_DELIVERY_THRESHOLD_MS = 30000;
    private static final long SLOW_DISPATCH_THRESHOLD_MS = 10000;
    private static Handler sHandler;
    private static WorkerThread sInstance;

    private WorkerThread() {
        super("worker.bg", 10);
    }

    private static void ensureThreadLocked() {
        if (sInstance == null) {
            WorkerThread workerThread = new WorkerThread();
            sInstance = workerThread;
            workerThread.start();
            Looper looper = sInstance.getLooper();
            looper.setTraceTag(524288L);
            looper.setSlowLogThresholdMs(SLOW_DISPATCH_THRESHOLD_MS, 30000L);
            sHandler = new Handler(sInstance.getLooper());
        }
    }

    public static WorkerThread get() {
        WorkerThread workerThread;
        synchronized (WorkerThread.class) {
            ensureThreadLocked();
            workerThread = sInstance;
        }
        return workerThread;
    }

    public static Handler getHandler() {
        Handler handler;
        synchronized (WorkerThread.class) {
            ensureThreadLocked();
            handler = sHandler;
        }
        return handler;
    }
}
