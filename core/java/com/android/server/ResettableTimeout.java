package com.android.server;

import android.p009os.ConditionVariable;
import android.p009os.SystemClock;

/* loaded from: classes5.dex */
abstract class ResettableTimeout {
    private ConditionVariable mLock = new ConditionVariable();
    private volatile long mOffAt;
    private volatile boolean mOffCalled;
    private Thread mThread;

    public abstract void off();

    /* renamed from: on */
    public abstract void m237on(boolean z);

    ResettableTimeout() {
    }

    /* renamed from: go */
    public void m236go(long milliseconds) {
        boolean alreadyOn;
        synchronized (this) {
            this.mOffAt = SystemClock.uptimeMillis() + milliseconds;
            Thread thread = this.mThread;
            if (thread == null) {
                alreadyOn = false;
                this.mLock.close();
                C4832T c4832t = new C4832T();
                this.mThread = c4832t;
                c4832t.start();
                this.mLock.block();
                this.mOffCalled = false;
            } else {
                thread.interrupt();
                alreadyOn = true;
            }
            m237on(alreadyOn);
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

    /* renamed from: com.android.server.ResettableTimeout$T */
    private class C4832T extends Thread {
        private C4832T() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            long diff;
            ResettableTimeout.this.mLock.open();
            while (true) {
                synchronized (this) {
                    diff = ResettableTimeout.this.mOffAt - SystemClock.uptimeMillis();
                    if (diff <= 0) {
                        ResettableTimeout.this.mOffCalled = true;
                        ResettableTimeout.this.off();
                        ResettableTimeout.this.mThread = null;
                        return;
                    }
                }
                try {
                    sleep(diff);
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
