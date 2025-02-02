package androidx.constraintlayout.core.motion.utils;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.Arrays;

/* loaded from: classes.dex */
public abstract class SplineSet {
    private int mCount;
    protected CurveFit mCurveFit;
    private String mType;
    protected int[] mTimePoints = new int[10];
    protected float[] mValues = new float[10];

    private static class Sort {
        static void doubleQuickSort(int[] iArr, float[] fArr, int i) {
            int[] iArr2 = new int[iArr.length + 10];
            iArr2[0] = i;
            iArr2[1] = 0;
            int i2 = 2;
            while (i2 > 0) {
                int i3 = i2 - 1;
                int i4 = iArr2[i3];
                i2 = i3 - 1;
                int i5 = iArr2[i2];
                if (i4 < i5) {
                    int i6 = iArr[i5];
                    int i7 = i4;
                    int i8 = i7;
                    while (i7 < i5) {
                        int i9 = iArr[i7];
                        if (i9 <= i6) {
                            int i10 = iArr[i8];
                            iArr[i8] = i9;
                            iArr[i7] = i10;
                            float f = fArr[i8];
                            fArr[i8] = fArr[i7];
                            fArr[i7] = f;
                            i8++;
                        }
                        i7++;
                    }
                    int i11 = iArr[i8];
                    iArr[i8] = iArr[i5];
                    iArr[i5] = i11;
                    float f2 = fArr[i8];
                    fArr[i8] = fArr[i5];
                    fArr[i5] = f2;
                    int i12 = i2 + 1;
                    iArr2[i2] = i8 - 1;
                    int i13 = i12 + 1;
                    iArr2[i12] = i4;
                    int i14 = i13 + 1;
                    iArr2[i13] = i5;
                    i2 = i14 + 1;
                    iArr2[i14] = i8 + 1;
                }
            }
        }
    }

    public final float get(float f) {
        return (float) this.mCurveFit.getPos(f);
    }

    public void setPoint(int i, float f) {
        int[] iArr = this.mTimePoints;
        if (iArr.length < this.mCount + 1) {
            this.mTimePoints = Arrays.copyOf(iArr, iArr.length * 2);
            float[] fArr = this.mValues;
            this.mValues = Arrays.copyOf(fArr, fArr.length * 2);
        }
        int[] iArr2 = this.mTimePoints;
        int i2 = this.mCount;
        iArr2[i2] = i;
        this.mValues[i2] = f;
        this.mCount = i2 + 1;
    }

    public final void setType(String str) {
        this.mType = str;
    }

    public void setup(int i) {
        int i2;
        int i3 = this.mCount;
        if (i3 == 0) {
            return;
        }
        Sort.doubleQuickSort(this.mTimePoints, this.mValues, i3 - 1);
        int i4 = 1;
        for (int i5 = 1; i5 < this.mCount; i5++) {
            int[] iArr = this.mTimePoints;
            if (iArr[i5 - 1] != iArr[i5]) {
                i4++;
            }
        }
        double[] dArr = new double[i4];
        double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, i4, 1);
        int i6 = 0;
        for (0; i2 < this.mCount; i2 + 1) {
            if (i2 > 0) {
                int[] iArr2 = this.mTimePoints;
                i2 = iArr2[i2] == iArr2[i2 - 1] ? i2 + 1 : 0;
            }
            dArr[i6] = this.mTimePoints[i2] * 0.01d;
            dArr2[i6][0] = this.mValues[i2];
            i6++;
        }
        this.mCurveFit = CurveFit.get(i, dArr, dArr2);
    }

    public final String toString() {
        String str = this.mType;
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        for (int i = 0; i < this.mCount; i++) {
            str = str + "[" + this.mTimePoints[i] + " , " + decimalFormat.format(this.mValues[i]) + "] ";
        }
        return str;
    }
}
