package com.samsung.android.nexus.base.utils.range;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class IntRangeable extends Rangeable {
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
