package com.android.server;

import android.os.ConditionVariable;
import android.os.SystemClock;

/* loaded from: classes6.dex */
abstract class ResettableTimeout {
    private ConditionVariable mLock = new ConditionVariable();
    private volatile long mOffAt;
    private volatile boolean mOffCalled;
    private Thread mThread;

    public abstract void off();

    public abstract void on(boolean z);

    ResettableTimeout() {
    }

    public void go(long j) {
        boolean z;
        synchronized (this) {
            this.mOffAt = SystemClock.uptimeMillis() + j;
            Thread thread = this.mThread;
            if (thread == null) {
                this.mLock.close();
                T t = new T();
                this.mThread = t;
                t.start();
                this.mLock.block();
                z = false;
                this.mOffCalled = false;
            } else {
                thread.interrupt();
                z = true;
            }
            on(z);
        }
    }

    public void cancel() {
        synchronized (this) {
            this.mOffAt = 0L;
            Thread thread = this.mThread;
            if (thread != null) {
                thread.interrupt();
                this.mThread = null;
            }
            if (!this.mOffCalled) {
                this.mOffCalled = true;
                off();
            }
        }
    }

    private class T extends Thread {
        private T() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            long uptimeMillis;
            ResettableTimeout.this.mLock.open();
            while (true) {
                synchronized (this) {
                    uptimeMillis = ResettableTimeout.this.mOffAt - SystemClock.uptimeMillis();
                    if (uptimeMillis <= 0) {
                        ResettableTimeout.this.mOffCalled = true;
                        ResettableTimeout.this.off();
                        ResettableTimeout.this.mThread = null;
                        return;
                    }
                }
                try {
                    sleep(uptimeMillis);
                } catch (InterruptedException unused) {
                }
            }
        }
    }
}
