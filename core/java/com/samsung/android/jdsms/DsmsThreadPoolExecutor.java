package com.samsung.android.jdsms;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes6.dex */
public final class DsmsThreadPoolExecutor extends ThreadPoolExecutor {
    private static final int KEEP_ALIVE_TIME_MS = 500;
    private static final int MAXIMUM_THREADS = 20;
    private static final int MINIMUM_THREADS = 4;
    private static final int QUEUE_POOL_SIZE = 500;
    private static final String SUBTAG = "DsmsThreadPoolExecutor";
    private static DsmsThreadPoolExecutor sInstance = null;
    private boolean isPaused;
    private ReentrantLock pauseLock;
    private Condition unpaused;

    public static synchronized DsmsThreadPoolExecutor getInstance() {
        DsmsThreadPoolExecutor dsmsThreadPoolExecutor;
        synchronized (DsmsThreadPoolExecutor.class) {
            if (sInstance == null) {
                sInstance = new DsmsThreadPoolExecutor();
            }
            dsmsThreadPoolExecutor = sInstance;
        }
        return dsmsThreadPoolExecutor;
    }

    private DsmsThreadPoolExecutor() {
        super(4, 20, 500L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue(500));
        this.isPaused = true;
        this.pauseLock = new ReentrantLock();
        this.unpaused = this.pauseLock.newCondition();
        super.setRejectedExecutionHandler(new RejectedThread());
    }

    @Override // java.util.concurrent.ThreadPoolExecutor
    public void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        this.pauseLock.lock();
        while (this.isPaused) {
            try {
                try {
                    this.unpaused.await();
                } catch (InterruptedException e) {
                    t.interrupt();
                }
            } finally {
                this.pauseLock.unlock();
            }
        }
    }

    public void resume() {
        this.pauseLock.lock();
        try {
            if (this.isPaused && !isShutdown()) {
                DsmsLog.d(SUBTAG, "Resuming");
                this.isPaused = false;
                this.unpaused.signalAll();
            }
        } finally {
            this.pauseLock.unlock();
        }
    }

    private static final class RejectedThread implements RejectedExecutionHandler {
        private RejectedThread() {}

        @Override // java.util.concurrent.RejectedExecutionHandler
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            DsmsLog.w(DsmsThreadPoolExecutor.SUBTAG, "Queue already full");
        }
    }
}
