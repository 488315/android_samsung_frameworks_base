package androidx.constraintlayout.core.motion.utils;

import android.support.v4.media.AbstractC0000x2c234b15;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class SplineSet {
    public int count;
    public CurveFit mCurveFit;
    public String mType;
    public int[] mTimePoints = new int[10];
    public float[] mValues = new float[10];

    public final float get(float f) {
        return (float) this.mCurveFit.getPos(f);
    }

    public void setPoint(float f, int i) {
        int[] iArr = this.mTimePoints;
        if (iArr.length < this.count + 1) {
            this.mTimePoints = Arrays.copyOf(iArr, iArr.length * 2);
            float[] fArr = this.mValues;
            this.mValues = Arrays.copyOf(fArr, fArr.length * 2);
        }
        int[] iArr2 = this.mTimePoints;
        int i2 = this.count;
        iArr2[i2] = i;
        this.mValues[i2] = f;
        this.count = i2 + 1;
    }

    public void setup(int i) {
        int i2;
        int i3 = this.count;
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
            i4 = i5 - 1;
            int i7 = iArr2[i4];
            if (i6 < i7) {
                int i8 = iArr[i7];
                int i9 = i6;
                int i10 = i9;
                while (i9 < i7) {
                    int i11 = iArr[i9];
                    if (i11 <= i8) {
                        int i12 = iArr[i10];
                        iArr[i10] = i11;
                        iArr[i9] = i12;
                        float f = fArr[i10];
                        fArr[i10] = fArr[i9];
                        fArr[i9] = f;
                        i10++;
                    }
                    i9++;
                }
                int i13 = iArr[i10];
                iArr[i10] = iArr[i7];
                iArr[i7] = i13;
                float f2 = fArr[i10];
                fArr[i10] = fArr[i7];
                fArr[i7] = f2;
                int i14 = i4 + 1;
                iArr2[i4] = i10 - 1;
                int i15 = i14 + 1;
                iArr2[i14] = i6;
                int i16 = i15 + 1;
                iArr2[i15] = i7;
                i4 = i16 + 1;
                iArr2[i16] = i10 + 1;
            }
        }
        int i17 = 1;
        for (int i18 = 1; i18 < this.count; i18++) {
            int[] iArr3 = this.mTimePoints;
            if (iArr3[i18 - 1] != iArr3[i18]) {
                i17++;
            }
        }
        double[] dArr = new double[i17];
        double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, i17, 1);
        int i19 = 0;
        for (0; i2 < this.count; i2 + 1) {
            if (i2 > 0) {
                int[] iArr4 = this.mTimePoints;
                i2 = iArr4[i2] == iArr4[i2 - 1] ? i2 + 1 : 0;
            }
            dArr[i19] = this.mTimePoints[i2] * 0.01d;
            dArr2[i19][0] = this.mValues[i2];
            i19++;
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
