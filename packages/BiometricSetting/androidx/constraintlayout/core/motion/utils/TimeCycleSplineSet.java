package androidx.constraintlayout.core.motion.utils;

import java.lang.reflect.Array;
import java.text.DecimalFormat;

/* loaded from: classes.dex */
public abstract class TimeCycleSplineSet {
    protected int mCount;
    protected CurveFit mCurveFit;
    protected long mLastTime;
    protected String mType;
    protected int mWaveShape = 0;
    protected int[] mTimePoints = new int[10];
    protected float[][] mValues = (float[][]) Array.newInstance((Class<?>) Float.TYPE, 10, 3);
    protected float[] mCache = new float[3];
    protected boolean mContinue = false;
    protected float mLastCycle = Float.NaN;

    protected static class Sort {
        static void doubleQuickSort(int[] iArr, float[][] fArr, int i) {
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
                            float[] fArr2 = fArr[i8];
                            fArr[i8] = fArr[i7];
                            fArr[i7] = fArr2;
                            i8++;
                        }
                        i7++;
                    }
                    int i11 = iArr[i8];
                    iArr[i8] = iArr[i5];
                    iArr[i5] = i11;
                    float[] fArr3 = fArr[i8];
                    fArr[i8] = fArr[i5];
                    fArr[i5] = fArr3;
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

    protected final float calcWave(float f) {
        switch (this.mWaveShape) {
            case 1:
                return Math.signum(f * 6.2831855f);
            case 2:
                return 1.0f - Math.abs(f);
            case 3:
                return (((f * 2.0f) + 1.0f) % 2.0f) - 1.0f;
            case 4:
                return 1.0f - (((f * 2.0f) + 1.0f) % 2.0f);
            case 5:
                return (float) Math.cos(f * 6.2831855f);
            case 6:
                float abs = 1.0f - Math.abs(((f * 4.0f) % 4.0f) - 2.0f);
                return 1.0f - (abs * abs);
            default:
                return (float) Math.sin(f * 6.2831855f);
        }
    }

    public void setPoint(int i, float f, float f2, int i2, float f3) {
        int[] iArr = this.mTimePoints;
        int i3 = this.mCount;
        iArr[i3] = i;
        float[] fArr = this.mValues[i3];
        fArr[0] = f;
        fArr[1] = f2;
        fArr[2] = f3;
        this.mWaveShape = Math.max(this.mWaveShape, i2);
        this.mCount++;
    }

    protected final void setStartTime(long j) {
        this.mLastTime = j;
    }

    public final void setType(String str) {
        this.mType = str;
    }

    public void setup(int i) {
        int i2;
        int i3 = this.mCount;
        if (i3 == 0) {
            System.err.println("Error no points added to " + this.mType);
            return;
        }
        Sort.doubleQuickSort(this.mTimePoints, this.mValues, i3 - 1);
        int i4 = 0;
        int i5 = 1;
        while (true) {
            int[] iArr = this.mTimePoints;
            if (i5 >= iArr.length) {
                break;
            }
            if (iArr[i5] != iArr[i5 - 1]) {
                i4++;
            }
            i5++;
        }
        if (i4 == 0) {
            i4 = 1;
        }
        double[] dArr = new double[i4];
        double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, i4, 3);
        int i6 = 0;
        for (0; i2 < this.mCount; i2 + 1) {
            if (i2 > 0) {
                int[] iArr2 = this.mTimePoints;
                i2 = iArr2[i2] == iArr2[i2 - 1] ? i2 + 1 : 0;
            }
            dArr[i6] = this.mTimePoints[i2] * 0.01d;
            double[] dArr3 = dArr2[i6];
            float[] fArr = this.mValues[i2];
            dArr3[0] = fArr[0];
            dArr3[1] = fArr[1];
            dArr3[2] = fArr[2];
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
