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
    private static DsmsThreadPoolExecutor sInstance;
    private boolean isPaused;
    private ReentrantLock pauseLock;
    private Condition unpaused;

    public static synchronized DsmsThreadPoolExecutor getInstance() {
        if (sInstance == null) {
            sInstance = new DsmsThreadPoolExecutor();
        }
        return sInstance;
    }

    private DsmsThreadPoolExecutor() {
        super(4, 20, 500L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue(500));
        this.isPaused = true;
        ReentrantLock reentrantLock = new ReentrantLock();
        this.pauseLock = reentrantLock;
        this.unpaused = reentrantLock.newCondition();
        super.setRejectedExecutionHandler(new RejectedThread());
    }

    @Override // java.util.concurrent.ThreadPoolExecutor
    public void beforeExecute(Thread thread, Runnable runnable) {
        super.beforeExecute(thread, runnable);
        this.pauseLock.lock();
        while (this.isPaused) {
            try {
                this.unpaused.await();
            } catch (InterruptedException unused) {
                thread.interrupt();
                return;
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
        private RejectedThread() {
        }

        @Override // java.util.concurrent.RejectedExecutionHandler
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            DsmsLog.w(DsmsThreadPoolExecutor.SUBTAG, "Queue already full");
        }
    }
}
