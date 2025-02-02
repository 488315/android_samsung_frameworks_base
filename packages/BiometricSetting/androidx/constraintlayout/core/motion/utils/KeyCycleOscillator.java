package androidx.constraintlayout.core.motion.utils;

import androidx.constraintlayout.widget.ConstraintAttribute;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/* loaded from: classes.dex */
public abstract class KeyCycleOscillator {
    private CycleOscillator mCycleOscillator;
    private String mType;
    private int mWaveShape = 0;
    private String mWaveString = null;
    public int mVariesBy = 0;
    ArrayList<WavePoint> mWavePoints = new ArrayList<>();

    /* renamed from: androidx.constraintlayout.core.motion.utils.KeyCycleOscillator$1 */
    final class C00771 implements Comparator<WavePoint> {
        @Override // java.util.Comparator
        public final int compare(WavePoint wavePoint, WavePoint wavePoint2) {
            return Integer.compare(wavePoint.mPosition, wavePoint2.mPosition);
        }
    }

    static class CycleOscillator {
        CurveFit mCurveFit;
        float[] mOffsetArr;
        Oscillator mOscillator;
        float[] mPeriod;
        float[] mPhaseArr;
        double[] mPosition;
        double[] mSplineSlopeCache;
        double[] mSplineValueCache;
        float[] mValues;

        CycleOscillator(int i, int i2, String str) {
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
                int i3 = 0;
                while (indexOf2 != -1) {
                    dArr[i3] = Double.parseDouble(str.substring(indexOf, indexOf2).trim());
                    indexOf = indexOf2 + 1;
                    indexOf2 = str.indexOf(44, indexOf);
                    i3++;
                }
                dArr[i3] = Double.parseDouble(str.substring(indexOf, str.indexOf(41, indexOf)).trim());
                double[] copyOf = Arrays.copyOf(dArr, i3 + 1);
                int length = (copyOf.length * 3) - 2;
                int length2 = copyOf.length - 1;
                double d = 1.0d / length2;
                double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, length, 1);
                double[] dArr3 = new double[length];
                int i4 = 0;
                while (i4 < copyOf.length) {
                    double d2 = copyOf[i4];
                    int i5 = i4 + length2;
                    dArr2[i5][c2] = d2;
                    double d3 = i4 * d;
                    dArr3[i5] = d3;
                    if (i4 > 0) {
                        int i6 = (length2 * 2) + i4;
                        j = 4607182418800017408L;
                        c = 0;
                        dArr2[i6][0] = d2 + 1.0d;
                        dArr3[i6] = d3 + 1.0d;
                        int i7 = i4 - 1;
                        dArr2[i7][0] = (d2 - 1.0d) - d;
                        dArr3[i7] = (d3 - 1.0d) - d;
                    } else {
                        j = 4607182418800017408L;
                        c = 0;
                    }
                    i4++;
                    c2 = c;
                }
                oscillator.mCustomCurve = new MonotonicCurveFit(dArr3, dArr2);
            }
            this.mValues = new float[i2];
            this.mPosition = new double[i2];
            this.mPeriod = new float[i2];
            this.mOffsetArr = new float[i2];
            this.mPhaseArr = new float[i2];
            float[] fArr = new float[i2];
        }
    }

    static class WavePoint {
        float mOffset;
        float mPeriod;
        float mPhase;
        int mPosition;
        float mValue;

        WavePoint(int i, float f, float f2, float f3, float f4) {
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
        double d;
        double d2;
        double d3;
        double signum;
        CycleOscillator cycleOscillator = this.mCycleOscillator;
        CurveFit curveFit = cycleOscillator.mCurveFit;
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
        double value = cycleOscillator.mOscillator.getValue(d5, cycleOscillator.mSplineValueCache[1]);
        Oscillator oscillator = cycleOscillator.mOscillator;
        double d6 = cycleOscillator.mSplineValueCache[1];
        double d7 = cycleOscillator.mSplineSlopeCache[1];
        double p = d6 + oscillator.getP(d5);
        if (d5 <= 0.0d) {
            d5 = 1.0E-5d;
        } else if (d5 >= 1.0d) {
            d5 = 0.999999d;
        }
        int binarySearch = Arrays.binarySearch(oscillator.mPosition, d5);
        if (binarySearch > 0) {
            d = 0.0d;
        } else if (binarySearch != 0) {
            int i = (-binarySearch) - 1;
            float[] fArr = oscillator.mPeriod;
            float f2 = fArr[i];
            int i2 = i - 1;
            float f3 = fArr[i2];
            double d8 = f2 - f3;
            double[] dArr2 = oscillator.mPosition;
            double d9 = dArr2[i];
            double d10 = dArr2[i2];
            double d11 = d8 / (d9 - d10);
            d = (f3 - (d11 * d10)) + (d5 * d11);
        } else {
            d = 0.0d;
        }
        double d12 = d + d7;
        switch (oscillator.mType) {
            case 1:
                d2 = 0.0d;
                break;
            case 2:
                d3 = d12 * 4.0d;
                signum = Math.signum((((p * 4.0d) + 3.0d) % 4.0d) - 2.0d);
                d2 = signum * d3;
                break;
            case 3:
                d2 = d12 * 2.0d;
                break;
            case 4:
                d2 = (-d12) * 2.0d;
                break;
            case 5:
                d3 = d12 * (-6.283185307179586d);
                signum = Math.sin(p * 6.283185307179586d);
                d2 = signum * d3;
                break;
            case 6:
                d2 = ((((p * 4.0d) + 2.0d) % 4.0d) - 2.0d) * d12 * 4.0d;
                break;
            case 7:
                d2 = oscillator.mCustomCurve.getSlope(p % 1.0d);
                break;
            default:
                d3 = d12 * 6.283185307179586d;
                signum = Math.cos(p * 6.283185307179586d);
                d2 = signum * d3;
                break;
        }
        double[] dArr3 = cycleOscillator.mSplineSlopeCache;
        return (float) ((d2 * cycleOscillator.mSplineValueCache[2]) + (value * dArr3[2]) + dArr3[0]);
    }

    public final void setPoint(int i, int i2, String str, int i3, float f, float f2, float f3, float f4, ConstraintAttribute constraintAttribute) {
        this.mWavePoints.add(new WavePoint(i, f, f2, f3, f4));
        if (i3 != -1) {
            this.mVariesBy = i3;
        }
        this.mWaveShape = i2;
        setCustom(constraintAttribute);
        this.mWaveString = str;
    }

    public final void setType(String str) {
        this.mType = str;
    }

    public final void setup() {
        int i;
        int size = this.mWavePoints.size();
        if (size == 0) {
            return;
        }
        Collections.sort(this.mWavePoints, new C00771());
        double[] dArr = new double[size];
        double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, size, 3);
        this.mCycleOscillator = new CycleOscillator(this.mWaveShape, size, this.mWaveString);
        Iterator<WavePoint> it = this.mWavePoints.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            WavePoint next = it.next();
            float f = next.mPeriod;
            dArr[i2] = f * 0.01d;
            double[] dArr3 = dArr2[i2];
            float f2 = next.mValue;
            dArr3[0] = f2;
            float f3 = next.mOffset;
            dArr3[1] = f3;
            float f4 = next.mPhase;
            dArr3[2] = f4;
            CycleOscillator cycleOscillator = this.mCycleOscillator;
            cycleOscillator.mPosition[i2] = next.mPosition / 100.0d;
            cycleOscillator.mPeriod[i2] = f;
            cycleOscillator.mOffsetArr[i2] = f3;
            cycleOscillator.mPhaseArr[i2] = f4;
            cycleOscillator.mValues[i2] = f2;
            i2++;
            dArr2 = dArr2;
        }
        double[][] dArr4 = dArr2;
        CycleOscillator cycleOscillator2 = this.mCycleOscillator;
        double[][] dArr5 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, cycleOscillator2.mPosition.length, 3);
        float[] fArr = cycleOscillator2.mValues;
        cycleOscillator2.mSplineValueCache = new double[fArr.length + 2];
        cycleOscillator2.mSplineSlopeCache = new double[fArr.length + 2];
        if (cycleOscillator2.mPosition[0] > 0.0d) {
            cycleOscillator2.mOscillator.addPoint(0.0d, cycleOscillator2.mPeriod[0]);
        }
        double[] dArr6 = cycleOscillator2.mPosition;
        int length = dArr6.length - 1;
        if (dArr6[length] < 1.0d) {
            cycleOscillator2.mOscillator.addPoint(1.0d, cycleOscillator2.mPeriod[length]);
        }
        for (int i3 = 0; i3 < dArr5.length; i3++) {
            double[] dArr7 = dArr5[i3];
            dArr7[0] = cycleOscillator2.mOffsetArr[i3];
            dArr7[1] = cycleOscillator2.mPhaseArr[i3];
            dArr7[2] = cycleOscillator2.mValues[i3];
            cycleOscillator2.mOscillator.addPoint(cycleOscillator2.mPosition[i3], cycleOscillator2.mPeriod[i3]);
        }
        Oscillator oscillator = cycleOscillator2.mOscillator;
        int i4 = 0;
        double d = 0.0d;
        while (true) {
            if (i4 >= oscillator.mPeriod.length) {
                break;
            }
            d += r10[i4];
            i4++;
        }
        double d2 = 0.0d;
        int i5 = 1;
        while (true) {
            float[] fArr2 = oscillator.mPeriod;
            if (i5 >= fArr2.length) {
                break;
            }
            int i6 = i5 - 1;
            float f5 = (fArr2[i6] + fArr2[i5]) / 2.0f;
            double[] dArr8 = oscillator.mPosition;
            d2 = ((dArr8[i5] - dArr8[i6]) * f5) + d2;
            i5++;
        }
        int i7 = 0;
        while (true) {
            float[] fArr3 = oscillator.mPeriod;
            if (i7 >= fArr3.length) {
                break;
            }
            fArr3[i7] = fArr3[i7] * ((float) (d / d2));
            i7++;
        }
        oscillator.mArea[0] = 0.0d;
        int i8 = 1;
        while (true) {
            float[] fArr4 = oscillator.mPeriod;
            if (i8 >= fArr4.length) {
                break;
            }
            int i9 = i8 - 1;
            float f6 = (fArr4[i9] + fArr4[i8]) / 2.0f;
            double[] dArr9 = oscillator.mPosition;
            double d3 = dArr9[i8] - dArr9[i9];
            double[] dArr10 = oscillator.mArea;
            dArr10[i8] = (d3 * f6) + dArr10[i9];
            i8++;
        }
        double[] dArr11 = cycleOscillator2.mPosition;
        if (dArr11.length > 1) {
            i = 0;
            cycleOscillator2.mCurveFit = CurveFit.get(0, dArr11, dArr5);
        } else {
            i = 0;
            cycleOscillator2.mCurveFit = null;
        }
        CurveFit.get(i, dArr, dArr4);
    }

    public final String toString() {
        String str = this.mType;
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        Iterator<WavePoint> it = this.mWavePoints.iterator();
        while (it.hasNext()) {
            str = str + "[" + it.next().mPosition + " , " + decimalFormat.format(r2.mValue) + "] ";
        }
        return str;
    }

    public final void setPoint(int i, int i2, String str, int i3, float f, float f2, float f3, float f4) {
        this.mWavePoints.add(new WavePoint(i, f, f2, f3, f4));
        if (i3 != -1) {
            this.mVariesBy = i3;
        }
        this.mWaveShape = i2;
        this.mWaveString = str;
    }

    protected void setCustom(ConstraintAttribute constraintAttribute) {
    }
}
