package com.samsung.android.nexus.base.utils.range;

import androidx.compose.animation.AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class FloatRangeable extends Rangeable {
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
        StringBuilder sb = new StringBuilder("FloatRangeable{mMin=");
        sb.append(this.mMin);
        sb.append(", mMax=");
        sb.append(this.mMax);
        sb.append(", mDelta=");
        return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.mDelta, '}');
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
