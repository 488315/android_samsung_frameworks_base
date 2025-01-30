package androidx.constraintlayout.core.motion.utils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LinearCurveFit extends CurveFit {
    public final boolean mExtrapolate = true;
    public final double[] mSlopeTemp;

    /* renamed from: mT */
    public final double[] f7mT;

    /* renamed from: mY */
    public final double[][] f8mY;

    public LinearCurveFit(double[] dArr, double[][] dArr2) {
        int length = dArr.length;
        int length2 = dArr2[0].length;
        this.mSlopeTemp = new double[length2];
        this.f7mT = dArr;
        this.f8mY = dArr2;
        if (length2 <= 2) {
            return;
        }
        double d = 0.0d;
        int i = 0;
        while (true) {
            double d2 = d;
            if (i >= dArr.length) {
                return;
            }
            double d3 = dArr2[i][0];
            if (i > 0) {
                Math.hypot(d3 - d, d3 - d2);
            }
            i++;
            d = d3;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public final void getPos(double d, double[] dArr) {
        double[] dArr2 = this.f7mT;
        int length = dArr2.length;
        double[][] dArr3 = this.f8mY;
        int i = 0;
        int length2 = dArr3[0].length;
        if (this.mExtrapolate) {
            double d2 = dArr2[0];
            double[] dArr4 = this.mSlopeTemp;
            if (d <= d2) {
                getSlope(d2, dArr4);
                for (int i2 = 0; i2 < length2; i2++) {
                    dArr[i2] = ((d - dArr2[0]) * dArr4[i2]) + dArr3[0][i2];
                }
                return;
            }
            int i3 = length - 1;
            double d3 = dArr2[i3];
            if (d >= d3) {
                getSlope(d3, dArr4);
                while (i < length2) {
                    dArr[i] = ((d - dArr2[i3]) * dArr4[i]) + dArr3[i3][i];
                    i++;
                }
                return;
            }
        } else {
            if (d <= dArr2[0]) {
                for (int i4 = 0; i4 < length2; i4++) {
                    dArr[i4] = dArr3[0][i4];
                }
                return;
            }
            int i5 = length - 1;
            if (d >= dArr2[i5]) {
                while (i < length2) {
                    dArr[i] = dArr3[i5][i];
                    i++;
                }
                return;
            }
        }
        int i6 = 0;
        while (i6 < length - 1) {
            if (d == dArr2[i6]) {
                for (int i7 = 0; i7 < length2; i7++) {
                    dArr[i7] = dArr3[i6][i7];
                }
            }
            int i8 = i6 + 1;
            double d4 = dArr2[i8];
            if (d < d4) {
                double d5 = dArr2[i6];
                double d6 = (d - d5) / (d4 - d5);
                while (i < length2) {
                    dArr[i] = (dArr3[i8][i] * d6) + ((1.0d - d6) * dArr3[i6][i]);
                    i++;
                }
                return;
            }
            i6 = i8;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0017, code lost:
    
        if (r10 >= r4) goto L4;
     */
    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void getSlope(double d, double[] dArr) {
        double[] dArr2 = this.f7mT;
        int length = dArr2.length;
        double[][] dArr3 = this.f8mY;
        int length2 = dArr3[0].length;
        double d2 = dArr2[0];
        if (d > d2) {
            d2 = dArr2[length - 1];
        }
        d = d2;
        int i = 0;
        while (i < length - 1) {
            int i2 = i + 1;
            double d3 = dArr2[i2];
            if (d <= d3) {
                double d4 = d3 - dArr2[i];
                for (int i3 = 0; i3 < length2; i3++) {
                    dArr[i3] = (dArr3[i2][i3] - dArr3[i][i3]) / d4;
                }
                return;
            }
            i = i2;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public final double[] getTimePoints() {
        return this.f7mT;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0012, code lost:
    
        if (r9 >= r3) goto L4;
     */
    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final double getSlope(double d) {
        double[] dArr = this.f7mT;
        int length = dArr.length;
        double d2 = dArr[0];
        if (d >= d2) {
            d2 = dArr[length - 1];
        }
        d = d2;
        int i = 0;
        while (i < length - 1) {
            int i2 = i + 1;
            double d3 = dArr[i2];
            if (d <= d3) {
                double d4 = d3 - dArr[i];
                double[][] dArr2 = this.f8mY;
                return (dArr2[i2][0] - dArr2[i][0]) / d4;
            }
            i = i2;
        }
        return 0.0d;
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public final void getPos(double d, float[] fArr) {
        double[] dArr = this.f7mT;
        int length = dArr.length;
        double[][] dArr2 = this.f8mY;
        int i = 0;
        int length2 = dArr2[0].length;
        if (this.mExtrapolate) {
            double d2 = dArr[0];
            double[] dArr3 = this.mSlopeTemp;
            if (d <= d2) {
                getSlope(d2, dArr3);
                for (int i2 = 0; i2 < length2; i2++) {
                    fArr[i2] = (float) (((d - dArr[0]) * dArr3[i2]) + dArr2[0][i2]);
                }
                return;
            }
            int i3 = length - 1;
            double d3 = dArr[i3];
            if (d >= d3) {
                getSlope(d3, dArr3);
                while (i < length2) {
                    fArr[i] = (float) (((d - dArr[i3]) * dArr3[i]) + dArr2[i3][i]);
                    i++;
                }
                return;
            }
        } else {
            if (d <= dArr[0]) {
                for (int i4 = 0; i4 < length2; i4++) {
                    fArr[i4] = (float) dArr2[0][i4];
                }
                return;
            }
            int i5 = length - 1;
            if (d >= dArr[i5]) {
                while (i < length2) {
                    fArr[i] = (float) dArr2[i5][i];
                    i++;
                }
                return;
            }
        }
        int i6 = 0;
        while (i6 < length - 1) {
            if (d == dArr[i6]) {
                for (int i7 = 0; i7 < length2; i7++) {
                    fArr[i7] = (float) dArr2[i6][i7];
                }
            }
            int i8 = i6 + 1;
            double d4 = dArr[i8];
            if (d < d4) {
                double d5 = dArr[i6];
                double d6 = (d - d5) / (d4 - d5);
                while (i < length2) {
                    fArr[i] = (float) ((dArr2[i8][i] * d6) + ((1.0d - d6) * dArr2[i6][i]));
                    i++;
                }
                return;
            }
            i6 = i8;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public final double getPos(double d) {
        double[] dArr = this.f7mT;
        int length = dArr.length;
        boolean z = this.mExtrapolate;
        double[][] dArr2 = this.f8mY;
        if (z) {
            double d2 = dArr[0];
            if (d <= d2) {
                return (getSlope(d2) * (d - d2)) + dArr2[0][0];
            }
            int i = length - 1;
            double d3 = dArr[i];
            if (d >= d3) {
                return (getSlope(d3) * (d - d3)) + dArr2[i][0];
            }
        } else {
            if (d <= dArr[0]) {
                return dArr2[0][0];
            }
            int i2 = length - 1;
            if (d >= dArr[i2]) {
                return dArr2[i2][0];
            }
        }
        int i3 = 0;
        while (i3 < length - 1) {
            double d4 = dArr[i3];
            if (d == d4) {
                return dArr2[i3][0];
            }
            int i4 = i3 + 1;
            double d5 = dArr[i4];
            if (d < d5) {
                double d6 = (d - d4) / (d5 - d4);
                return (dArr2[i4][0] * d6) + ((1.0d - d6) * dArr2[i3][0]);
            }
            i3 = i4;
        }
        return 0.0d;
    }
}
