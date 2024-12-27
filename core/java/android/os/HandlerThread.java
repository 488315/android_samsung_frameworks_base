package android.os;

import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class HandlerThread extends Thread {
    private volatile Executor mExecutor;
    private volatile Handler mHandler;
    Looper mLooper;
    int mPriority;
    int mTid;

    public HandlerThread(String name) {
        super(name);
        this.mTid = -1;
        this.mPriority = 0;
        onCreated();
    }

    public HandlerThread(String name, int priority) {
        super(name);
        this.mTid = -1;
        this.mPriority = priority;
        onCreated();
    }

    protected void onCreated() {
    }

    protected void onCreated$ravenwood() {
        setDaemon(true);
    }

    protected void onLooperPrepared() {
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        this.mTid = Process.myTid();
        Looper.prepare();
        synchronized (this) {
            this.mLooper = Looper.myLooper();
            notifyAll();
        }
        Process.setThreadPriority(this.mPriority);
        onLooperPrepared();
        Looper.loop();
        this.mTid = -1;
    }

    public Looper getLooper() {
        if (!isAlive()) {
            return null;
        }
        boolean wasInterrupted = false;
        synchronized (this) {
            while (isAlive() && this.mLooper == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    wasInterrupted = true;
                }
            }
        }
        if (wasInterrupted) {
            Thread.currentThread().interrupt();
        }
        return this.mLooper;
    }

    public Handler getThreadHandler() {
        if (this.mHandler == null) {
            this.mHandler = new Handler(getLooper());
        }
        return this.mHandler;
    }

    public Executor getThreadExecutor() {
        if (this.mExecutor == null) {
            this.mExecutor = new HandlerExecutor(getThreadHandler());
        }
        return this.mExecutor;
    }

    public boolean quit() {
        Looper looper = getLooper();
        if (looper != null) {
            looper.quit();
            return true;
        }
        return false;
    }

    public boolean quitSafely() {
        Looper looper = getLooper();
        if (looper != null) {
            looper.quitSafely();
            return true;
        }
        return false;
    }

    public int getThreadId() {
        return this.mTid;
    }
}
