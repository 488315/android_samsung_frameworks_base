package com.samsung.android.server.audio;

import android.os.SystemClock;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
