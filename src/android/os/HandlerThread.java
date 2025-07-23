package android.os;

import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class HandlerThread extends Thread {
    private volatile Executor mExecutor;
    private volatile Handler mHandler;
    Looper mLooper;
    int mPriority;
    int mTid;

    protected void onCreated() {
    }

    protected void onLooperPrepared() {
    }

    public HandlerThread(String str) {
        super(str);
        this.mTid = -1;
        this.mPriority = 0;
        onCreated();
    }

    public HandlerThread(String str, int i) {
        super(str);
        this.mTid = -1;
        this.mPriority = i;
        onCreated();
    }

    protected void onCreated$ravenwood() {
        setDaemon(true);
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
        boolean z;
        if (!isAlive()) {
            return null;
        }
        synchronized (this) {
            z = false;
            while (isAlive() && this.mLooper == null) {
                try {
                    wait();
                } catch (InterruptedException unused) {
                    z = true;
                }
            }
        }
        if (z) {
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
        if (looper == null) {
            return false;
        }
        looper.quit();
        return true;
    }

    public boolean quitSafely() {
        Looper looper = getLooper();
        if (looper == null) {
            return false;
        }
        looper.quitSafely();
        return true;
    }

    public int getThreadId() {
        return this.mTid;
    }
}
