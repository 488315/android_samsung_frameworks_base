package androidx.constraintlayout.core.motion.utils;

import android.support.v4.media.AbstractC0000x2c234b15;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class KeyCycleOscillator {
    public CycleOscillator mCycleOscillator;
    public String mType;
    public int mWaveShape = 0;
    public String mWaveString = null;
    public int mVariesBy = 0;
    public final ArrayList mWavePoints = new ArrayList();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CycleOscillator {
        public CurveFit mCurveFit;
        public final float[] mOffset;
        public final Oscillator mOscillator;
        public final float[] mPeriod;
        public final float[] mPhase;
        public final double[] mPosition;
        public double[] mSplineSlopeCache;
        public double[] mSplineValueCache;
        public final float[] mValues;

        public CycleOscillator(int i, String str, int i2, int i3) {
            long j;
            char c;
            Oscillator oscillator = new Oscillator();
            this.mOscillator = oscillator;
            oscillator.mType = i;
            if (str != null) {
                double[] dArr = new double[str.length() / 2];
                int indexOf = str.indexOf(40) + 1;
                int indexOf2 = str.indexOf(44, indexOf);
                char c2 = 0;
                int i4 = 0;
                while (indexOf2 != -1) {
                    dArr[i4] = Double.parseDouble(str.substring(indexOf, indexOf2).trim());
                    indexOf = indexOf2 + 1;
                    indexOf2 = str.indexOf(44, indexOf);
                    i4++;
                }
                dArr[i4] = Double.parseDouble(str.substring(indexOf, str.indexOf(41, indexOf)).trim());
                double[] copyOf = Arrays.copyOf(dArr, i4 + 1);
                int length = (copyOf.length * 3) - 2;
                int length2 = copyOf.length - 1;
                double d = 1.0d / length2;
                double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, length, 1);
                double[] dArr3 = new double[length];
                int i5 = 0;
                while (i5 < copyOf.length) {
                    double d2 = copyOf[i5];
                    int i6 = i5 + length2;
                    dArr2[i6][c2] = d2;
                    double d3 = i5 * d;
                    dArr3[i6] = d3;
                    if (i5 > 0) {
                        int i7 = (length2 * 2) + i5;
                        j = 4607182418800017408L;
                        c = 0;
                        dArr2[i7][0] = d2 + 1.0d;
                        dArr3[i7] = d3 + 1.0d;
                        int i8 = i5 - 1;
                        dArr2[i8][0] = (d2 - 1.0d) - d;
                        dArr3[i8] = (d3 - 1.0d) - d;
                    } else {
                        j = 4607182418800017408L;
                        c = 0;
                    }
                    i5++;
                    c2 = c;
                }
                oscillator.mCustomCurve = new MonotonicCurveFit(dArr3, dArr2);
            }
            this.mValues = new float[i3];
            this.mPosition = new double[i3];
            this.mPeriod = new float[i3];
            this.mOffset = new float[i3];
            this.mPhase = new float[i3];
            float[] fArr = new float[i3];
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class WavePoint {
        public final float mOffset;
        public final float mPeriod;
        public final float mPhase;
        public final int mPosition;
        public final float mValue;

        public WavePoint(int i, float f, float f2, float f3, float f4) {
            this.mPosition = i;
            this.mValue = f4;
            this.mOffset = f2;
            this.mPeriod = f;
            this.mPhase = f3;
        }
    }

    public final float get(float f) {
        CycleOscillator cycleOscillator = this.mCycleOscillator;
        CurveFit curveFit = cycleOscillator.mCurveFit;
        if (curveFit != null) {
            curveFit.getPos(f, cycleOscillator.mSplineValueCache);
        } else {
            double[] dArr = cycleOscillator.mSplineValueCache;
            dArr[0] = cycleOscillator.mOffset[0];
            dArr[1] = cycleOscillator.mPhase[0];
            dArr[2] = cycleOscillator.mValues[0];
        }
        double[] dArr2 = cycleOscillator.mSplineValueCache;
        return (float) ((cycleOscillator.mOscillator.getValue(f, dArr2[1]) * cycleOscillator.mSplineValueCache[2]) + dArr2[0]);
    }

    public final float getSlope(float f) {
        double d;
        double d2;
        double signum;
        CycleOscillator cycleOscillator = this.mCycleOscillator;
        CurveFit curveFit = cycleOscillator.mCurveFit;
        double d3 = 0.0d;
        if (curveFit != null) {
            double d4 = f;
            curveFit.getSlope(d4, cycleOscillator.mSplineSlopeCache);
            cycleOscillator.mCurveFit.getPos(d4, cycleOscillator.mSplineValueCache);
        } else {
            double[] dArr = cycleOscillator.mSplineSlopeCache;
            dArr[0] = 0.0d;
            dArr[1] = 0.0d;
            dArr[2] = 0.0d;
        }
        double d5 = f;
        double d6 = cycleOscillator.mSplineValueCache[1];
        Oscillator oscillator = cycleOscillator.mOscillator;
        double value = oscillator.getValue(d5, d6);
        double d7 = cycleOscillator.mSplineValueCache[1];
        double d8 = cycleOscillator.mSplineSlopeCache[1];
        double p = oscillator.getP(d5) + d7;
        if (d5 <= 0.0d) {
            d5 = 1.0E-5d;
        } else if (d5 >= 1.0d) {
            d5 = 0.999999d;
        }
        int binarySearch = Arrays.binarySearch(oscillator.mPosition, d5);
        if (binarySearch <= 0) {
            if (binarySearch != 0) {
                int i = (-binarySearch) - 1;
                float[] fArr = oscillator.mPeriod;
                float f2 = fArr[i];
                int i2 = i - 1;
                float f3 = fArr[i2];
                double d9 = f2 - f3;
                double[] dArr2 = oscillator.mPosition;
                double d10 = dArr2[i];
                double d11 = dArr2[i2];
                double d12 = d9 / (d10 - d11);
                d3 = (f3 - (d12 * d11)) + (d5 * d12);
            } else {
                d3 = 0.0d;
            }
        }
        double d13 = d3 + d8;
        int i3 = oscillator.mType;
        double d14 = oscillator.PI2;
        switch (i3) {
            case 1:
                d = 0.0d;
                break;
            case 2:
                d2 = d13 * 4.0d;
                signum = Math.signum((((p * 4.0d) + 3.0d) % 4.0d) - 2.0d);
                d = d2 * signum;
                break;
            case 3:
                d = d13 * 2.0d;
                break;
            case 4:
                d = (-d13) * 2.0d;
                break;
            case 5:
                signum = (-d14) * d13;
                d2 = Math.sin(d14 * p);
                d = d2 * signum;
                break;
            case 6:
                d = d13 * 4.0d * ((((p * 4.0d) + 2.0d) % 4.0d) - 2.0d);
                break;
            case 7:
                d = oscillator.mCustomCurve.getSlope(p % 1.0d);
                break;
            default:
                d2 = d13 * d14;
                signum = Math.cos(d14 * p);
                d = d2 * signum;
                break;
        }
        double[] dArr3 = cycleOscillator.mSplineSlopeCache;
        return (float) ((d * cycleOscillator.mSplineValueCache[2]) + (value * dArr3[2]) + dArr3[0]);
    }

    public final void setup() {
        int i;
        ArrayList arrayList = this.mWavePoints;
        int size = arrayList.size();
        if (size == 0) {
            return;
        }
        Collections.sort(arrayList, new Comparator(this) { // from class: androidx.constraintlayout.core.motion.utils.KeyCycleOscillator.1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Integer.compare(((WavePoint) obj).mPosition, ((WavePoint) obj2).mPosition);
            }
        });
        double[] dArr = new double[size];
        double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, size, 3);
        this.mCycleOscillator = new CycleOscillator(this.mWaveShape, this.mWaveString, this.mVariesBy, size);
        Iterator it = arrayList.iterator();
        char c = 0;
        int i2 = 0;
        while (it.hasNext()) {
            WavePoint wavePoint = (WavePoint) it.next();
            float f = wavePoint.mPeriod;
            dArr[i2] = f * 0.01d;
            double[] dArr3 = dArr2[i2];
            float f2 = wavePoint.mValue;
            dArr3[c] = f2;
            float f3 = wavePoint.mOffset;
            dArr3[1] = f3;
            float f4 = wavePoint.mPhase;
            dArr3[2] = f4;
            CycleOscillator cycleOscillator = this.mCycleOscillator;
            cycleOscillator.mPosition[i2] = wavePoint.mPosition / 100.0d;
            cycleOscillator.mPeriod[i2] = f;
            cycleOscillator.mOffset[i2] = f3;
            cycleOscillator.mPhase[i2] = f4;
            cycleOscillator.mValues[i2] = f2;
            i2++;
            dArr2 = dArr2;
            dArr = dArr;
            c = 0;
        }
        double[] dArr4 = dArr;
        double[][] dArr5 = dArr2;
        CycleOscillator cycleOscillator2 = this.mCycleOscillator;
        double[] dArr6 = cycleOscillator2.mPosition;
        double[][] dArr7 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, dArr6.length, 3);
        float[] fArr = cycleOscillator2.mValues;
        cycleOscillator2.mSplineValueCache = new double[fArr.length + 2];
        cycleOscillator2.mSplineSlopeCache = new double[fArr.length + 2];
        double d = dArr6[0];
        float[] fArr2 = cycleOscillator2.mPeriod;
        Oscillator oscillator = cycleOscillator2.mOscillator;
        if (d > 0.0d) {
            oscillator.addPoint(0.0d, fArr2[0]);
        }
        int length = dArr6.length - 1;
        if (dArr6[length] < 1.0d) {
            oscillator.addPoint(1.0d, fArr2[length]);
        }
        for (int i3 = 0; i3 < dArr7.length; i3++) {
            double[] dArr8 = dArr7[i3];
            dArr8[0] = cycleOscillator2.mOffset[i3];
            dArr8[1] = cycleOscillator2.mPhase[i3];
            dArr8[2] = fArr[i3];
            oscillator.addPoint(dArr6[i3], fArr2[i3]);
        }
        int i4 = 0;
        double d2 = 0.0d;
        while (true) {
            if (i4 >= oscillator.mPeriod.length) {
                break;
            }
            d2 += r6[i4];
            i4++;
        }
        int i5 = 1;
        double d3 = 0.0d;
        while (true) {
            float[] fArr3 = oscillator.mPeriod;
            if (i5 >= fArr3.length) {
                break;
            }
            int i6 = i5 - 1;
            float f5 = (fArr3[i6] + fArr3[i5]) / 2.0f;
            double[] dArr9 = oscillator.mPosition;
            d3 = ((dArr9[i5] - dArr9[i6]) * f5) + d3;
            i5++;
        }
        int i7 = 0;
        while (true) {
            float[] fArr4 = oscillator.mPeriod;
            if (i7 >= fArr4.length) {
                break;
            }
            fArr4[i7] = (float) ((d2 / d3) * fArr4[i7]);
            i7++;
        }
        oscillator.mArea[0] = 0.0d;
        int i8 = 1;
        while (true) {
            float[] fArr5 = oscillator.mPeriod;
            if (i8 >= fArr5.length) {
                break;
            }
            int i9 = i8 - 1;
            float f6 = (fArr5[i9] + fArr5[i8]) / 2.0f;
            double[] dArr10 = oscillator.mPosition;
            double d4 = dArr10[i8] - dArr10[i9];
            double[] dArr11 = oscillator.mArea;
            dArr11[i8] = (d4 * f6) + dArr11[i9];
            i8++;
        }
        if (dArr6.length > 1) {
            i = 0;
            cycleOscillator2.mCurveFit = CurveFit.get(0, dArr6, dArr7);
        } else {
            i = 0;
            cycleOscillator2.mCurveFit = null;
        }
        CurveFit.get(i, dArr4, dArr5);
    }

    public final String toString() {
        String str = this.mType;
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        Iterator it = this.mWavePoints.iterator();
        while (it.hasNext()) {
            WavePoint wavePoint = (WavePoint) it.next();
            StringBuilder m2m = AbstractC0000x2c234b15.m2m(str, "[");
            m2m.append(wavePoint.mPosition);
            m2m.append(" , ");
            m2m.append(decimalFormat.format(wavePoint.mValue));
            m2m.append("] ");
            str = m2m.toString();
        }
        return str;
    }

    public void setCustom(ConstraintAttribute constraintAttribute) {
    }
}
