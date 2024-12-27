package com.android.server.am;

import android.os.Trace;

public final class LowMemDetector {
    public boolean mAvailable;
    public final Object mPressureStateLock = new Object();
    public int mPressureState = 0;

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

    public native int waitForPressure();
}
