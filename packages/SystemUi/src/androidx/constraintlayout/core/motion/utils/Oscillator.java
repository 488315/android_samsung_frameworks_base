package androidx.constraintlayout.core.motion.utils;

import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Oscillator {
    public double[] mArea;
    public MonotonicCurveFit mCustomCurve;
    public int mType;
    public float[] mPeriod = new float[0];
    public double[] mPosition = new double[0];
    public final double PI2 = 6.283185307179586d;

    public final void addPoint(double d, float f) {
        int length = this.mPeriod.length + 1;
        int binarySearch = Arrays.binarySearch(this.mPosition, d);
        if (binarySearch < 0) {
            binarySearch = (-binarySearch) - 1;
        }
        this.mPosition = Arrays.copyOf(this.mPosition, length);
        this.mPeriod = Arrays.copyOf(this.mPeriod, length);
        this.mArea = new double[length];
        double[] dArr = this.mPosition;
        System.arraycopy(dArr, binarySearch, dArr, binarySearch + 1, (length - binarySearch) - 1);
        this.mPosition[binarySearch] = d;
        this.mPeriod[binarySearch] = f;
    }

    public final double getP(double d) {
        if (d < 0.0d) {
            d = 0.0d;
        } else if (d > 1.0d) {
            d = 1.0d;
        }
        int binarySearch = Arrays.binarySearch(this.mPosition, d);
        if (binarySearch > 0) {
            return 1.0d;
        }
        if (binarySearch == 0) {
            return 0.0d;
        }
        int i = (-binarySearch) - 1;
        float[] fArr = this.mPeriod;
        float f = fArr[i];
        int i2 = i - 1;
        float f2 = fArr[i2];
        double d2 = f - f2;
        double[] dArr = this.mPosition;
        double d3 = dArr[i];
        double d4 = dArr[i2];
        double d5 = d2 / (d3 - d4);
        return ((((d * d) - (d4 * d4)) * d5) / 2.0d) + ((d - d4) * (f2 - (d5 * d4))) + this.mArea[i2];
    }

    public final double getValue(double d, double d2) {
        double p = getP(d) + d2;
        int i = this.mType;
        double d3 = this.PI2;
        switch (i) {
            case 1:
                return Math.signum(0.5d - (p % 1.0d));
            case 2:
                return 1.0d - Math.abs((((p * 4.0d) + 1.0d) % 4.0d) - 2.0d);
            case 3:
                return (((p * 2.0d) + 1.0d) % 2.0d) - 1.0d;
            case 4:
                return 1.0d - (((p * 2.0d) + 1.0d) % 2.0d);
            case 5:
                return Math.cos((d2 + p) * d3);
            case 6:
                double abs = 1.0d - Math.abs(((p * 4.0d) % 4.0d) - 2.0d);
                return 1.0d - (abs * abs);
            case 7:
                return this.mCustomCurve.getPos(p % 1.0d);
            default:
                return Math.sin(d3 * p);
        }
    }

    public final String toString() {
        return "pos =" + Arrays.toString(this.mPosition) + " period=" + Arrays.toString(this.mPeriod);
    }
}
