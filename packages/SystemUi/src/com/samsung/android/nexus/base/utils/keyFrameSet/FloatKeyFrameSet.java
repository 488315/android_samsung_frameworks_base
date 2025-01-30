package com.samsung.android.nexus.base.utils.keyFrameSet;

import android.view.animation.Interpolator;
import com.samsung.android.nexus.base.utils.Log;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class FloatKeyFrameSet extends KeyFrameSet {
    public float[] mDeltas;
    public float[] mValues;

    public FloatKeyFrameSet() {
    }

    public final Object clone() {
        return new FloatKeyFrameSet(this);
    }

    public final float get(float f) {
        float interpolation;
        Interpolator[] interpolatorArr;
        int i = -1;
        for (int i2 = 0; i2 < this.length - 1 && this.fractionPositions[i2] <= f; i2++) {
            i = i2;
        }
        if (i < 0) {
            Log.m261e(this.TAG, "getSectionIndex: -1 : " + f + " " + this.fractionPositions[0]);
        }
        float f2 = this.mValues[i];
        float f3 = this.mDeltas[i];
        if (i < 0) {
            interpolation = 0.0f;
        } else if (i >= this.length - 1) {
            interpolation = 1.0f;
        } else {
            float[] fArr = this.fractionPositions;
            float f4 = fArr[i];
            float f5 = (f - f4) / (fArr[i + 1] - f4);
            Interpolator interpolator = this.interpolator;
            if (interpolator == null && (interpolatorArr = this.interpolators) != null) {
                interpolator = interpolatorArr[i];
            }
            interpolation = interpolator != null ? interpolator.getInterpolation(f5) : f5;
        }
        return (f3 * interpolation) + f2;
    }

    public final void init(float[] fArr) {
        int i = this.length - 1;
        this.mValues = new float[i];
        this.mDeltas = new float[i];
        int i2 = 0;
        float f = fArr[0];
        while (i2 < i) {
            int i3 = i2 + 1;
            float f2 = fArr[i3];
            this.mValues[i2] = f;
            this.mDeltas[i2] = f2 - f;
            i2 = i3;
            f = f2;
        }
    }

    public final String toString() {
        return "FloatKeyFrameSet{mValues=" + Arrays.toString(this.mValues) + ", mDeltas=" + Arrays.toString(this.mDeltas) + ", length=" + this.length + ", fractionPositions=" + Arrays.toString(this.fractionPositions) + ", interpolators=" + Arrays.toString(this.interpolators) + ", interpolator=" + this.interpolator + '}';
    }

    public FloatKeyFrameSet(FloatKeyFrameSet floatKeyFrameSet) {
        super(floatKeyFrameSet);
        if (floatKeyFrameSet == null) {
            return;
        }
        int length = floatKeyFrameSet.mValues.length;
        float[] fArr = new float[length];
        this.mValues = fArr;
        this.mDeltas = new float[floatKeyFrameSet.mDeltas.length];
        System.arraycopy(floatKeyFrameSet.mValues, 0, fArr, 0, length);
        float[] fArr2 = floatKeyFrameSet.mDeltas;
        float[] fArr3 = this.mDeltas;
        System.arraycopy(fArr2, 0, fArr3, 0, fArr3.length);
    }

    public FloatKeyFrameSet(float[] fArr, float[] fArr2) {
        super(fArr);
        init(fArr2);
    }

    public FloatKeyFrameSet(float[] fArr, Interpolator interpolator, float[] fArr2) {
        super(fArr, interpolator);
        init(fArr2);
    }

    public FloatKeyFrameSet(float[] fArr, Interpolator[] interpolatorArr, float[] fArr2) {
        super(fArr, interpolatorArr);
        init(fArr2);
    }
}
