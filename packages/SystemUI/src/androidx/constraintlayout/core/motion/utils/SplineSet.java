package androidx.constraintlayout.core.motion.utils;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SplineSet {
    public int mCount;
    public CurveFit mCurveFit;
    public String mType;
    public int[] mTimePoints = new int[10];
    public float[] mValues = new float[10];

    public final float get(float f) {
        return (float) this.mCurveFit.getPos(f);
    }

    public void setPoint(float f, int i) {
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

    public void setup(int i) {
        int i2;
        int i3 = this.mCount;
        if (i3 == 0) {
            return;
        }
        int[] iArr = this.mTimePoints;
        float[] fArr = this.mValues;
        int[] iArr2 = new int[iArr.length + 10];
        iArr2[0] = i3 - 1;
        iArr2[1] = 0;
        int i4 = 2;
        while (i4 > 0) {
            int i5 = i4 - 1;
            int i6 = iArr2[i5];
            int i7 = i4 - 2;
            int i8 = iArr2[i7];
            if (i6 < i8) {
                int i9 = iArr[i8];
                int i10 = i6;
                int i11 = i10;
                while (i10 < i8) {
                    int i12 = iArr[i10];
                    if (i12 <= i9) {
                        int i13 = iArr[i11];
                        iArr[i11] = i12;
                        iArr[i10] = i13;
                        float f = fArr[i11];
                        fArr[i11] = fArr[i10];
                        fArr[i10] = f;
                        i11++;
                    }
                    i10++;
                }
                int i14 = iArr[i11];
                iArr[i11] = iArr[i8];
                iArr[i8] = i14;
                float f2 = fArr[i11];
                fArr[i11] = fArr[i8];
                fArr[i8] = f2;
                iArr2[i7] = i11 - 1;
                iArr2[i5] = i6;
                int i15 = i4 + 1;
                iArr2[i4] = i8;
                i4 += 2;
                iArr2[i15] = i11 + 1;
            } else {
                i4 = i7;
            }
        }
        int i16 = 1;
        for (int i17 = 1; i17 < this.mCount; i17++) {
            int[] iArr3 = this.mTimePoints;
            if (iArr3[i17 - 1] != iArr3[i17]) {
                i16++;
            }
        }
        double[] dArr = new double[i16];
        double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, i16, 1);
        int i18 = 0;
        for (0; i2 < this.mCount; i2 + 1) {
            if (i2 > 0) {
                int[] iArr4 = this.mTimePoints;
                i2 = iArr4[i2] == iArr4[i2 - 1] ? i2 + 1 : 0;
            }
            dArr[i18] = this.mTimePoints[i2] * 0.01d;
            dArr2[i18][0] = this.mValues[i2];
            i18++;
        }
        this.mCurveFit = CurveFit.get(i, dArr, dArr2);
    }

    public final String toString() {
        String str = this.mType;
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        for (int i = 0; i < this.mCount; i++) {
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, "[");
            m.append(this.mTimePoints[i]);
            m.append(" , ");
            m.append(decimalFormat.format(this.mValues[i]));
            m.append("] ");
            str = m.toString();
        }
        return str;
    }
}
