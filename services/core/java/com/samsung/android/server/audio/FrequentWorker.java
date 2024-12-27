package com.samsung.android.server.audio;

import android.os.SystemClock;

public abstract class FrequentWorker {
    public Object mCachedValue;
    public int mPeriodMs = 0;
    public long mLastProcessTime = 0;

    public abstract Object func();

    public final Object runOrSkip() {
        long uptimeMillis = SystemClock.uptimeMillis();
        if (uptimeMillis - this.mLastProcessTime < this.mPeriodMs) {
            return this.mCachedValue;
        }
        this.mLastProcessTime = uptimeMillis;
        Object func = func();
        this.mCachedValue = func;
        return func;
    }
}
