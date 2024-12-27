package com.android.server.am;

import android.os.Trace;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class LowMemDetector {
    public boolean mAvailable;
    public final Object mPressureStateLock = new Object();
    public int mPressureState = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LowMemThread extends Thread {
        public boolean mIsTracingMemCriticalLow;

        public LowMemThread() {
            super("LowMemThread");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            while (true) {
                int waitForPressure = LowMemDetector.this.waitForPressure();
                boolean z = waitForPressure == 3;
                if (z && !this.mIsTracingMemCriticalLow) {
                    Trace.traceBegin(64L, "criticalLowMemory");
                } else if (!z && this.mIsTracingMemCriticalLow) {
                    Trace.traceEnd(64L);
                }
                this.mIsTracingMemCriticalLow = z;
                if (waitForPressure == -1) {
                    LowMemDetector.this.mAvailable = false;
                    return;
                } else {
                    synchronized (LowMemDetector.this.mPressureStateLock) {
                        LowMemDetector.this.mPressureState = waitForPressure;
                    }
                }
            }
        }
    }

    public LowMemDetector() {
        LowMemThread lowMemThread = new LowMemThread();
        if (init() != 0) {
            this.mAvailable = false;
        } else {
            this.mAvailable = true;
            lowMemThread.start();
        }
    }

    private native int init();

    /* JADX INFO: Access modifiers changed from: private */
    public native int waitForPressure();
}
