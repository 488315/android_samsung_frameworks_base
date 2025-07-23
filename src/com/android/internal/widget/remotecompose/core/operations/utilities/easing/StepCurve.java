package com.android.internal.widget.remotecompose.core.operations.utilities.easing;

import java.lang.reflect.Array;

/* loaded from: classes6.dex */
public class StepCurve extends Easing {
    private final MonotonicCurveFit mCurveFit;

    public StepCurve(float[] fArr, int i, int i2) {
        this.mCurveFit = genSpline(fArr, i, i2);
    }

    private static MonotonicCurveFit genSpline(float[] fArr, int i, int i2) {
        int i3 = (i2 * 3) - 2;
        int i4 = i2 - 1;
        double d = 1.0d / i4;
        double[][] dArr = (double[][]) Array.newInstance((Class<?>) Double.TYPE, i3, 1);
        double[] dArr2 = new double[i3];
        for (int i5 = 0; i5 < i2; i5++) {
            double d2 = fArr[i5 + i];
            int i6 = i5 + i4;
            dArr[i6][0] = d2;
            double d3 = i5 * d;
            dArr2[i6] = d3;
            if (i5 > 0) {
                int i7 = (i4 * 2) + i5;
                dArr[i7][0] = d2 + 1.0d;
                dArr2[i7] = d3 + 1.0d;
                int i8 = i5 - 1;
                dArr[i8][0] = (d2 - 1.0d) - d;
                dArr2[i8] = (d3 - 1.0d) - d;
            }
        }
        return new MonotonicCurveFit(dArr2, dArr);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.easing.Easing
    public float getDiff(float f) {
        if (f >= 0.0f && f <= 1.0f) {
            return (float) this.mCurveFit.getSlope(f, 0);
        }
        return 0.0f;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.easing.Easing
    public float get(float f) {
        if (f < 0.0f) {
            return 0.0f;
        }
        if (f > 1.0f) {
            return 1.0f;
        }
        return (float) this.mCurveFit.getPos(f, 0);
    }
}
