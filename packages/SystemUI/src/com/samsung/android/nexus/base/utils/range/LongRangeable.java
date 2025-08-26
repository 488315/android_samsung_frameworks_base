package com.samsung.android.nexus.base.utils.range;

/* loaded from: classes4.dex */
public class LongRangeable extends Rangeable {
    public long mDelta;
    public long mMax;
    public long mMin;

    public LongRangeable(long j) {
        this.mMin = j;
        this.mMax = j;
        onRangeUpdated();
    }

    public final Object clone() {
        return new LongRangeable(this);
    }

    public final long get() {
        if (this.mIsSingleValue) {
            return this.mMin;
        }
        return this.mMin + ((long) (Rangeable.sRandom.get() * this.mDelta));
    }

    public final void onRangeUpdated() {
        long j = this.mMax;
        long j2 = this.mMin;
        this.mDelta = j - j2;
        this.mIsSingleValue = j == j2;
    }

    public final String toString() {
        return "LongRangeable{mMin=" + this.mMin + ", mMax=" + this.mMax + ", mDelta=" + this.mDelta + '}';
    }

    public LongRangeable(long j, long j2) {
        this.mMin = j;
        this.mMax = j2;
        onRangeUpdated();
    }

    public LongRangeable(LongRangeable longRangeable) {
        this.mMin = longRangeable.mMin;
        this.mMax = longRangeable.mMax;
        onRangeUpdated();
    }
}
