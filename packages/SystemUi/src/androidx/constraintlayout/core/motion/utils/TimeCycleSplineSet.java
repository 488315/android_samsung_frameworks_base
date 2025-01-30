package androidx.constraintlayout.core.motion.utils;

import android.support.v4.media.AbstractC0000x2c234b15;
import java.lang.reflect.Array;
import java.text.DecimalFormat;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class TimeCycleSplineSet {
    public int count;
    public long last_time;
    public CurveFit mCurveFit;
    public String mType;
    public int mWaveShape = 0;
    public final int[] mTimePoints = new int[10];
    public final float[][] mValues = (float[][]) Array.newInstance((Class<?>) Float.TYPE, 10, 3);
    public final float[] mCache = new float[3];
    public boolean mContinue = false;
    public float last_cycle = Float.NaN;

    public final float calcWave(float f) {
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

    public void setPoint(float f, float f2, float f3, int i, int i2) {
        int i3 = this.count;
        this.mTimePoints[i3] = i;
        float[] fArr = this.mValues[i3];
        fArr[0] = f;
        fArr[1] = f2;
        fArr[2] = f3;
        this.mWaveShape = Math.max(this.mWaveShape, i2);
        this.count++;
    }

    public void setup(int i) {
        float[][] fArr;
        int i2 = this.count;
        if (i2 == 0) {
            System.err.println("Error no points added to " + this.mType);
            return;
        }
        int[] iArr = this.mTimePoints;
        int[] iArr2 = new int[iArr.length + 10];
        iArr2[0] = i2 - 1;
        iArr2[1] = 0;
        int i3 = 2;
        while (true) {
            fArr = this.mValues;
            if (i3 <= 0) {
                break;
            }
            int i4 = i3 - 1;
            int i5 = iArr2[i4];
            i3 = i4 - 1;
            int i6 = iArr2[i3];
            if (i5 < i6) {
                int i7 = iArr[i6];
                int i8 = i5;
                int i9 = i8;
                while (i8 < i6) {
                    int i10 = iArr[i8];
                    if (i10 <= i7) {
                        int i11 = iArr[i9];
                        iArr[i9] = i10;
                        iArr[i8] = i11;
                        float[] fArr2 = fArr[i9];
                        fArr[i9] = fArr[i8];
                        fArr[i8] = fArr2;
                        i9++;
                    }
                    i8++;
                }
                int i12 = iArr[i9];
                iArr[i9] = iArr[i6];
                iArr[i6] = i12;
                float[] fArr3 = fArr[i9];
                fArr[i9] = fArr[i6];
                fArr[i6] = fArr3;
                int i13 = i3 + 1;
                iArr2[i3] = i9 - 1;
                int i14 = i13 + 1;
                iArr2[i13] = i5;
                int i15 = i14 + 1;
                iArr2[i14] = i6;
                i3 = i15 + 1;
                iArr2[i15] = i9 + 1;
            }
        }
        int i16 = 0;
        for (int i17 = 1; i17 < iArr.length; i17++) {
            if (iArr[i17] != iArr[i17 - 1]) {
                i16++;
            }
        }
        if (i16 == 0) {
            i16 = 1;
        }
        double[] dArr = new double[i16];
        double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, i16, 3);
        int i18 = 0;
        for (int i19 = 0; i19 < this.count; i19++) {
            if (i19 <= 0 || iArr[i19] != iArr[i19 - 1]) {
                dArr[i18] = iArr[i19] * 0.01d;
                double[] dArr3 = dArr2[i18];
                float[] fArr4 = fArr[i19];
                dArr3[0] = fArr4[0];
                dArr3[1] = fArr4[1];
                dArr3[2] = fArr4[2];
                i18++;
            }
        }
        this.mCurveFit = CurveFit.get(i, dArr, dArr2);
    }

    public final String toString() {
        String str = this.mType;
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        for (int i = 0; i < this.count; i++) {
            StringBuilder m2m = AbstractC0000x2c234b15.m2m(str, "[");
            m2m.append(this.mTimePoints[i]);
            m2m.append(" , ");
            m2m.append(decimalFormat.format(this.mValues[i]));
            m2m.append("] ");
            str = m2m.toString();
        }
        return str;
    }
}
