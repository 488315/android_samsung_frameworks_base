package com.samsung.android.nexus.base.utils.range;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class IntRangeable extends Rangeable {
    public int mDelta;
    public int mMax;
    public int mMin;

    public IntRangeable(int i) {
        this.mMin = i;
        this.mMax = i;
        onRangeUpdated();
    }

    public final Object clone() {
        return new IntRangeable(this);
    }

    public final void onRangeUpdated() {
        int i = this.mMax;
        int i2 = this.mMin;
        this.mDelta = i - i2;
        this.mIsSingleValue = i == i2;
    }

    public IntRangeable(int i, int i2) {
        this.mMin = i;
        this.mMax = i2;
        onRangeUpdated();
    }

    public IntRangeable(IntRangeable intRangeable) {
        this.mMin = intRangeable.mMin;
        this.mMax = intRangeable.mMax;
        onRangeUpdated();
    }
}
