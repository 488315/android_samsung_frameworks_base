package com.android.internal.widget.remotecompose.core.operations.utilities.easing;

import java.lang.reflect.Array;

/* loaded from: classes5.dex */
public class StepCurve extends Easing {
    private static final boolean DEBUG = false;
    MonotonicCurveFit mCurveFit;

    public StepCurve(float[] params, int offset, int len) {
        this.mCurveFit = genSpline(params, offset, len);
    }

    private static MonotonicCurveFit genSpline(float[] values, int off, int arrayLen) {
        int length = (arrayLen * 3) - 2;
        int len = arrayLen - 1;
        double gap = 1.0d / len;
        double[][] points = (double[][]) Array.newInstance((Class<?>) Double.TYPE, length, 1);
        double[] time = new double[length];
        for (int i = 0; i < arrayLen; i++) {
            double v = values[i + off];
            points[i + len][0] = v;
            time[i + len] = i * gap;
            if (i > 0) {
                points[(len * 2) + i][0] = v + 1.0d;
                time[(len * 2) + i] = (i * gap) + 1.0d;
                points[i - 1][0] = (v - 1.0d) - gap;
                time[i - 1] = ((i * gap) - 1.0d) - gap;
            }
        }
        MonotonicCurveFit ms = new MonotonicCurveFit(time, points);
        return ms;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.easing.Easing
    public float getDiff(float x) {
        return (float) this.mCurveFit.getSlope(x, 0);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.easing.Easing
    public float get(float x) {
        return (float) this.mCurveFit.getPos(x, 0);
    }
}
