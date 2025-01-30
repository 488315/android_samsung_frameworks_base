package com.samsung.android.nexus.base.utils.range;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class FloatRangeable extends Rangeable {
    public float mDelta;
    public float mMax;
    public float mMin;

    public FloatRangeable(float f) {
        this.mMin = f;
        this.mMax = f;
        onRangeUpdated();
    }

    public final Object clone() {
        return new FloatRangeable(this);
    }

    public final float get() {
        if (this.mIsSingleValue) {
            return this.mMin;
        }
        return (Rangeable.sRandom.get() * this.mDelta) + this.mMin;
    }

    public final void onRangeUpdated() {
        float f = this.mMax;
        float f2 = this.mMin;
        this.mDelta = f - f2;
        this.mIsSingleValue = f == f2;
    }

    public final void set(FloatRangeable floatRangeable) {
        this.mMin = floatRangeable.mMin;
        this.mMax = floatRangeable.mMax;
        onRangeUpdated();
    }

    public final String toString() {
        return "FloatRangeable{mMin=" + this.mMin + ", mMax=" + this.mMax + ", mDelta=" + this.mDelta + '}';
    }

    public FloatRangeable(float f, float f2) {
        this.mMin = f;
        this.mMax = f2;
        onRangeUpdated();
    }

    public FloatRangeable(FloatRangeable floatRangeable) {
        set(floatRangeable);
    }
}
