package com.android.internal.util;

import android.os.SystemClock;

/* loaded from: classes4.dex */
public class TokenBucket {
    private int mAvailable;
    private final int mCapacity;
    private final int mFillDelta;
    private long mLastFill;

    public TokenBucket(int i, int i2, int i3) {
        this.mFillDelta = Preconditions.checkArgumentPositive(i, "deltaMs must be strictly positive");
        int checkArgumentPositive = Preconditions.checkArgumentPositive(i2, "capacity must be strictly positive");
        this.mCapacity = checkArgumentPositive;
        this.mAvailable = Math.min(Preconditions.checkArgumentNonnegative(i3), checkArgumentPositive);
        this.mLastFill = scaledTime();
    }

    public TokenBucket(int i, int i2) {
        this(i, i2, i2);
    }

    public void reset(int i) {
        Preconditions.checkArgumentNonnegative(i);
        this.mAvailable = Math.min(i, this.mCapacity);
        this.mLastFill = scaledTime();
    }

    public int capacity() {
        return this.mCapacity;
    }

    public int available() {
        fill();
        return this.mAvailable;
    }

    public boolean has() {
        fill();
        return this.mAvailable > 0;
    }

    public boolean get() {
        return get(1) == 1;
    }

    public int get(int i) {
        fill();
        if (i <= 0) {
            return 0;
        }
        int i2 = this.mAvailable;
        if (i > i2) {
            this.mAvailable = 0;
            return i2;
        }
        this.mAvailable = i2 - i;
        return i;
    }

    private void fill() {
        long scaledTime = scaledTime();
        this.mAvailable = Math.min(this.mCapacity, this.mAvailable + ((int) (scaledTime - this.mLastFill)));
        this.mLastFill = scaledTime;
    }

    private long scaledTime() {
        return SystemClock.elapsedRealtime() / this.mFillDelta;
    }
}
