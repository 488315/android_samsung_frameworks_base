package com.android.internal.widget.remotecompose.core.operations.utilities.easing;

import android.hardware.scontext.SContextConstants;

/* loaded from: classes6.dex */
public class MonotonicSpline {
    private static final String TAG = "MonotonicCurveFit";
    private boolean mExtrapolate = true;
    float[] mSlopeTemp;
    private float[] mT;
    private float[] mTangent;
    private float[] mY;

    private static float diff(float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = f2 * f2;
        float f8 = f2 * 6.0f;
        float f9 = (((((-6.0f) * f7) * f4) + (f4 * f8)) + ((6.0f * f7) * f3)) - (f8 * f3);
        float f10 = 3.0f * f;
        return ((((f9 + ((f10 * f6) * f7)) + ((f10 * f5) * f7)) - (((2.0f * f) * f6) * f2)) - (((4.0f * f) * f5) * f2)) + (f * f5);
    }

    private static float interpolate(float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = f2 * f2;
        float f8 = f7 * f2;
        float f9 = 3.0f * f7;
        float f10 = f6 * f;
        float f11 = ((((((-2.0f) * f8) * f4) + (f4 * f9)) + ((f8 * 2.0f) * f3)) - (f9 * f3)) + f3 + (f10 * f8);
        float f12 = f * f5;
        return (((f11 + (f8 * f12)) - (f10 * f7)) - (((f * 2.0f) * f5) * f7)) + (f12 * f2);
    }

    public MonotonicSpline(float[] fArr, float[] fArr2) {
        if (fArr == null) {
            int length = fArr2.length;
            float[] fArr3 = new float[length];
            for (int i = 0; i < length; i++) {
                fArr3[i] = i / (length - 1);
            }
            fArr = fArr3;
        }
        this.mT = fArr;
        this.mY = fArr2;
        int length2 = fArr.length;
        this.mSlopeTemp = new float[1];
        int i2 = length2 - 1;
        float[] fArr4 = new float[i2];
        float[] fArr5 = new float[length2];
        int i3 = 0;
        while (i3 < i2) {
            int i4 = i3 + 1;
            float f = (fArr2[i4] - fArr2[i3]) / (fArr[i4] - fArr[i3]);
            fArr4[i3] = f;
            if (i3 == 0) {
                fArr5[i3] = f;
            } else {
                fArr5[i3] = (fArr4[i3 - 1] + f) * 0.5f;
            }
            i3 = i4;
        }
        fArr5[i2] = fArr4[length2 - 2];
        for (int i5 = 0; i5 < i2; i5++) {
            float f2 = fArr4[i5];
            if (f2 == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                fArr5[i5] = 0.0f;
                fArr5[i5 + 1] = 0.0f;
            } else {
                float f3 = fArr5[i5] / f2;
                int i6 = i5 + 1;
                float f4 = fArr5[i6] / f2;
                float fHypot = (float) Math.hypot(f3, f4);
                if (fHypot > 9.0d) {
                    float f5 = 3.0f / fHypot;
                    fArr5[i5] = f3 * f5 * fArr4[i5];
                    fArr5[i6] = f5 * f4 * fArr4[i5];
                }
            }
        }
        this.mTangent = fArr5;
    }

    public float[] getArray() {
        return this.mY;
    }

    public float getPos(float f) {
        float slope;
        float f2;
        float f3;
        float[] fArr = this.mT;
        int length = fArr.length;
        int i = 0;
        if (this.mExtrapolate) {
            float f4 = fArr[0];
            if (f <= f4) {
                slope = getSlope(f4);
                f2 = this.mY[0];
                f3 = this.mT[0];
            } else {
                int i2 = length - 1;
                float f5 = fArr[i2];
                if (f >= f5) {
                    slope = getSlope(f5);
                    f2 = this.mY[i2];
                    f3 = this.mT[i2];
                }
            }
            return f2 + ((f - f3) * slope);
        }
        if (f <= fArr[0]) {
            return this.mY[0];
        }
        int i3 = length - 1;
        if (f >= fArr[i3]) {
            return this.mY[i3];
        }
        while (i < length - 1) {
            float[] fArr2 = this.mT;
            float f6 = fArr2[i];
            if (f == f6) {
                float f7 = this.mY[i];
            }
            int i4 = i + 1;
            float f8 = fArr2[i4];
            if (f < f8) {
                float f9 = f8 - f6;
                float f10 = (f - f6) / f9;
                float[] fArr3 = this.mY;
                float f11 = fArr3[i];
                float f12 = fArr3[i4];
                float[] fArr4 = this.mTangent;
                return interpolate(f9, f10, f11, f12, fArr4[i], fArr4[i4]);
            }
            i = i4;
        }
        return 0.0f;
    }

    public float getSlope(float f) {
        float[] fArr = this.mT;
        int length = fArr.length;
        float f2 = fArr[0];
        if (f <= f2) {
            f = f2;
        } else {
            float f3 = fArr[length - 1];
            if (f >= f3) {
                f = f3;
            }
        }
        if (length - 1 <= 0) {
            return 0.0f;
        }
        float f4 = fArr[1];
        if (f > f4) {
            return 0.0f;
        }
        float f5 = f4 - f2;
        float f6 = (f - f2) / f5;
        float[] fArr2 = this.mY;
        float f7 = fArr2[0];
        float f8 = fArr2[1];
        float[] fArr3 = this.mTangent;
        return diff(f5, f6, f7, f8, fArr3[0], fArr3[1]) / f5;
    }

    public float[] getTimePoints() {
        return this.mT;
    }
}
