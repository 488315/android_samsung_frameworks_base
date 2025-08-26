package androidx.constraintlayout.core.motion.utils;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/* loaded from: classes.dex */
public abstract class KeyCycleOscillator {
    public CycleOscillator mCycleOscillator;
    public String mType;
    public int mWaveShape = 0;
    public String mWaveString = null;
    public int mVariesBy = 0;
    public final ArrayList mWavePoints = new ArrayList();

    public class CycleOscillator {
        public CurveFit mCurveFit;
        public final float[] mOffsetArr;
        public final Oscillator mOscillator;
        public final float[] mPeriod;
        public final float[] mPhaseArr;
        public final double[] mPosition;
        public double[] mSplineSlopeCache;
        public double[] mSplineValueCache;
        public final float[] mValues;

        public CycleOscillator(int i, String str, int i2, int i3) {
            Oscillator oscillator = new Oscillator();
            this.mOscillator = oscillator;
            oscillator.mType = i;
            if (str != null) {
                double[] dArr = new double[str.length() / 2];
                int iIndexOf = str.indexOf(40) + 1;
                int iIndexOf2 = str.indexOf(44, iIndexOf);
                char c = 0;
                int i4 = 0;
                while (iIndexOf2 != -1) {
                    dArr[i4] = Double.parseDouble(str.substring(iIndexOf, iIndexOf2).trim());
                    iIndexOf = iIndexOf2 + 1;
                    iIndexOf2 = str.indexOf(44, iIndexOf);
                    i4++;
                }
                dArr[i4] = Double.parseDouble(str.substring(iIndexOf, str.indexOf(41, iIndexOf)).trim());
                double[] dArrCopyOf = Arrays.copyOf(dArr, i4 + 1);
                int length = (dArrCopyOf.length * 3) - 2;
                int length2 = dArrCopyOf.length - 1;
                double d = 1.0d;
                double d2 = 1.0d / length2;
                double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, length, 1);
                double[] dArr3 = new double[length];
                int i5 = 0;
                while (i5 < dArrCopyOf.length) {
                    double d3 = dArrCopyOf[i5];
                    int i6 = i5 + length2;
                    dArr2[i6][c] = d3;
                    char c2 = c;
                    double d4 = d;
                    double d5 = i5 * d2;
                    dArr3[i6] = d5;
                    if (i5 > 0) {
                        int i7 = (length2 * 2) + i5;
                        dArr2[i7][c2] = d3 + d4;
                        dArr3[i7] = d5 + d4;
                        int i8 = i5 - 1;
                        dArr2[i8][c2] = (d3 - d4) - d2;
                        dArr3[i8] = (d5 - 1.0d) - d2;
                    }
                    i5++;
                    c = c2;
                    d = d4;
                }
                oscillator.mCustomCurve = new MonotonicCurveFit(dArr3, dArr2);
            }
            this.mValues = new float[i3];
            this.mPosition = new double[i3];
            this.mPeriod = new float[i3];
            this.mOffsetArr = new float[i3];
            this.mPhaseArr = new float[i3];
            float[] fArr = new float[i3];
        }
    }

    public class WavePoint {
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
            dArr[0] = cycleOscillator.mOffsetArr[0];
            dArr[1] = cycleOscillator.mPhaseArr[0];
            dArr[2] = cycleOscillator.mValues[0];
        }
        double[] dArr2 = cycleOscillator.mSplineValueCache;
        return (float) ((cycleOscillator.mOscillator.getValue(f, dArr2[1]) * cycleOscillator.mSplineValueCache[2]) + dArr2[0]);
    }

    public final float getSlope(float f) {
        char c;
        char c2;
        double slope;
        double d;
        double dSignum;
        double dSin;
        CycleOscillator cycleOscillator = this.mCycleOscillator;
        CurveFit curveFit = cycleOscillator.mCurveFit;
        double d2 = 0.0d;
        if (curveFit != null) {
            double d3 = f;
            curveFit.getSlope(d3, cycleOscillator.mSplineSlopeCache);
            cycleOscillator.mCurveFit.getPos(d3, cycleOscillator.mSplineValueCache);
        } else {
            double[] dArr = cycleOscillator.mSplineSlopeCache;
            dArr[0] = 0.0d;
            dArr[1] = 0.0d;
            dArr[2] = 0.0d;
        }
        double d4 = f;
        double d5 = cycleOscillator.mSplineValueCache[1];
        Oscillator oscillator = cycleOscillator.mOscillator;
        double value = oscillator.getValue(d4, d5);
        double d6 = cycleOscillator.mSplineValueCache[1];
        double d7 = cycleOscillator.mSplineSlopeCache[1];
        double p = oscillator.getP(d4) + d6;
        if (d4 <= 0.0d) {
            c = 2;
            c2 = 0;
        } else if (d4 >= 1.0d) {
            c = 2;
            c2 = 0;
            d2 = 1.0d;
        } else {
            int iBinarySearch = Arrays.binarySearch(oscillator.mPosition, d4);
            if (iBinarySearch < 0) {
                iBinarySearch = (-iBinarySearch) - 1;
            }
            float[] fArr = oscillator.mPeriod;
            float f2 = fArr[iBinarySearch];
            int i = iBinarySearch - 1;
            float f3 = fArr[i];
            c = 2;
            c2 = 0;
            double d8 = f2 - f3;
            double[] dArr2 = oscillator.mPosition;
            double d9 = dArr2[iBinarySearch];
            double d10 = dArr2[i];
            double d11 = d8 / (d9 - d10);
            d2 = (f3 - (d11 * d10)) + (d4 * d11);
        }
        double d12 = d2 + d7;
        double d13 = 2.0d;
        switch (oscillator.mType) {
            case 1:
                slope = 0.0d;
                break;
            case 2:
                d = d12 * 4.0d;
                dSignum = Math.signum((((p * 4.0d) + 3.0d) % 4.0d) - 2.0d);
                slope = d * dSignum;
                break;
            case 3:
                slope = d12 * 2.0d;
                break;
            case 4:
                dSin = -d12;
                slope = dSin * d13;
                break;
            case 5:
                d13 = (-6.283185307179586d) * d12;
                dSin = Math.sin(6.283185307179586d * p);
                slope = dSin * d13;
                break;
            case 6:
                slope = d12 * 4.0d * ((((p * 4.0d) + 2.0d) % 4.0d) - 2.0d);
                break;
            case 7:
                slope = oscillator.mCustomCurve.getSlope(p % 1.0d);
                break;
            default:
                d = d12 * 6.283185307179586d;
                dSignum = Math.cos(6.283185307179586d * p);
                slope = d * dSignum;
                break;
        }
        double[] dArr3 = cycleOscillator.mSplineSlopeCache;
        return (float) ((slope * cycleOscillator.mSplineValueCache[c]) + (value * dArr3[c]) + dArr3[c2]);
    }

    public final void setup() {
        int i;
        int size = this.mWavePoints.size();
        if (size == 0) {
            return;
        }
        Collections.sort(this.mWavePoints, new Comparator(this) { // from class: androidx.constraintlayout.core.motion.utils.KeyCycleOscillator.1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Integer.compare(((WavePoint) obj).mPosition, ((WavePoint) obj2).mPosition);
            }
        });
        double[] dArr = new double[size];
        int i2 = 2;
        int i3 = 1;
        int i4 = 3;
        int i5 = 0;
        double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, size, 3);
        this.mCycleOscillator = new CycleOscillator(this.mWaveShape, this.mWaveString, this.mVariesBy, size);
        ArrayList arrayList = this.mWavePoints;
        int size2 = arrayList.size();
        int i6 = 0;
        int i7 = 0;
        while (i7 < size2) {
            Object obj = arrayList.get(i7);
            i7++;
            WavePoint wavePoint = (WavePoint) obj;
            float f = wavePoint.mPeriod;
            dArr[i6] = f * 0.01d;
            double[] dArr3 = dArr2[i6];
            float f2 = wavePoint.mValue;
            int i8 = i4;
            int i9 = i5;
            dArr3[i9] = f2;
            float f3 = wavePoint.mOffset;
            int i10 = i2;
            double[][] dArr4 = dArr2;
            dArr3[i3] = f3;
            float f4 = wavePoint.mPhase;
            int i11 = size2;
            dArr3[i10] = f4;
            CycleOscillator cycleOscillator = this.mCycleOscillator;
            cycleOscillator.mPosition[i6] = wavePoint.mPosition / 100.0d;
            cycleOscillator.mPeriod[i6] = f;
            cycleOscillator.mOffsetArr[i6] = f3;
            cycleOscillator.mPhaseArr[i6] = f4;
            cycleOscillator.mValues[i6] = f2;
            i6++;
            size2 = i11;
            i3 = i3;
            i4 = i8;
            i5 = i9;
            dArr2 = dArr4;
            i2 = i10;
        }
        int i12 = i2;
        double[][] dArr5 = dArr2;
        int i13 = i3;
        int i14 = i5;
        CycleOscillator cycleOscillator2 = this.mCycleOscillator;
        double[] dArr6 = cycleOscillator2.mPosition;
        int length = dArr6.length;
        int[] iArr = new int[i12];
        iArr[i13] = i4;
        iArr[i14] = length;
        double[][] dArr7 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, iArr);
        float[] fArr = cycleOscillator2.mValues;
        cycleOscillator2.mSplineValueCache = new double[fArr.length + i12];
        cycleOscillator2.mSplineSlopeCache = new double[fArr.length + i12];
        double d = dArr6[i14];
        double d2 = 0.0d;
        float[] fArr2 = cycleOscillator2.mPeriod;
        Oscillator oscillator = cycleOscillator2.mOscillator;
        if (d > 0.0d) {
            oscillator.addPoint(0.0d, fArr2[i14]);
        }
        int length2 = dArr6.length - i13;
        if (dArr6[length2] < 1.0d) {
            oscillator.addPoint(1.0d, fArr2[length2]);
        }
        for (int i15 = i14; i15 < dArr7.length; i15++) {
            double[] dArr8 = dArr7[i15];
            dArr8[i14] = cycleOscillator2.mOffsetArr[i15];
            dArr8[i13] = cycleOscillator2.mPhaseArr[i15];
            dArr8[2] = fArr[i15];
            oscillator.addPoint(dArr6[i15], fArr2[i15]);
        }
        double d3 = 0.0d;
        int i16 = i14;
        while (true) {
            if (i16 >= oscillator.mPeriod.length) {
                break;
            }
            d3 += r7[i16];
            i16++;
        }
        double d4 = 0.0d;
        int i17 = i13;
        while (true) {
            float[] fArr3 = oscillator.mPeriod;
            if (i17 >= fArr3.length) {
                break;
            }
            int i18 = i17 - 1;
            float f5 = (fArr3[i18] + fArr3[i17]) / 2.0f;
            double[] dArr9 = oscillator.mPosition;
            d4 = ((dArr9[i17] - dArr9[i18]) * f5) + d4;
            i17++;
        }
        int i19 = i14;
        while (true) {
            float[] fArr4 = oscillator.mPeriod;
            if (i19 >= fArr4.length) {
                break;
            }
            fArr4[i19] = fArr4[i19] * ((float) (d3 / d4));
            i19++;
            d2 = d2;
        }
        oscillator.mArea[i14] = d2;
        int i20 = i13;
        while (true) {
            float[] fArr5 = oscillator.mPeriod;
            if (i20 >= fArr5.length) {
                break;
            }
            int i21 = i20 - 1;
            float f6 = (fArr5[i21] + fArr5[i20]) / 2.0f;
            double[] dArr10 = oscillator.mPosition;
            double d5 = dArr10[i20] - dArr10[i21];
            double[] dArr11 = oscillator.mArea;
            dArr11[i20] = (d5 * f6) + dArr11[i21];
            i20++;
        }
        if (dArr6.length > i13) {
            i = i14;
            cycleOscillator2.mCurveFit = CurveFit.get(i, dArr6, dArr7);
        } else {
            i = i14;
            cycleOscillator2.mCurveFit = null;
        }
        CurveFit.get(i, dArr, dArr5);
    }

    public final String toString() {
        String string = this.mType;
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        ArrayList arrayList = this.mWavePoints;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(string, "[");
            sbM.append(((WavePoint) obj).mPosition);
            sbM.append(" , ");
            sbM.append(decimalFormat.format(r4.mValue));
            sbM.append("] ");
            string = sbM.toString();
        }
        return string;
    }

    public void setCustom(ConstraintAttribute constraintAttribute) {
    }
}
