package androidx.constraintlayout.core.motion.utils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class LinearCurveFit extends CurveFit {
    public final boolean mExtrapolate = true;
    public final double[] mSlopeTemp;
    public final double[] mT;
    public final double[][] mY;

    public LinearCurveFit(double[] dArr, double[][] dArr2) {
        int length = dArr2[0].length;
        this.mSlopeTemp = new double[length];
        this.mT = dArr;
        this.mY = dArr2;
        if (length <= 2) {
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
        double[] dArr2 = this.mT;
        int length = dArr2.length;
        double[][] dArr3 = this.mY;
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void getSlope(double r10, double[] r12) {
        /*
            r9 = this;
            double[] r0 = r9.mT
            int r1 = r0.length
            double[][] r9 = r9.mY
            r2 = 0
            r3 = r9[r2]
            int r3 = r3.length
            r4 = r0[r2]
            int r6 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r6 > 0) goto L11
        Lf:
            r10 = r4
            goto L1a
        L11:
            int r4 = r1 + (-1)
            r4 = r0[r4]
            int r6 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r6 < 0) goto L1a
            goto Lf
        L1a:
            r4 = r2
        L1b:
            int r5 = r1 + (-1)
            if (r4 >= r5) goto L3d
            int r5 = r4 + 1
            r6 = r0[r5]
            int r8 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
            if (r8 > 0) goto L3b
            r10 = r0[r4]
            double r6 = r6 - r10
        L2a:
            if (r2 >= r3) goto L3d
            r10 = r9[r4]
            r10 = r10[r2]
            r0 = r9[r5]
            r0 = r0[r2]
            double r0 = r0 - r10
            double r0 = r0 / r6
            r12[r2] = r0
            int r2 = r2 + 1
            goto L2a
        L3b:
            r4 = r5
            goto L1b
        L3d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.motion.utils.LinearCurveFit.getSlope(double, double[]):void");
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public final double[] getTimePoints() {
        return this.mT;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0012, code lost:
    
        if (r9 >= r3) goto L4;
     */
    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final double getSlope(double r9) {
        /*
            r8 = this;
            double[] r0 = r8.mT
            int r1 = r0.length
            r2 = 0
            r3 = r0[r2]
            int r5 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r5 >= 0) goto Lc
        La:
            r9 = r3
            goto L15
        Lc:
            int r3 = r1 + (-1)
            r3 = r0[r3]
            int r5 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r5 < 0) goto L15
            goto La
        L15:
            r3 = r2
        L16:
            int r4 = r1 + (-1)
            if (r3 >= r4) goto L34
            int r4 = r3 + 1
            r5 = r0[r4]
            int r7 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1))
            if (r7 > 0) goto L32
            r9 = r0[r3]
            double r5 = r5 - r9
            double[][] r8 = r8.mY
            r9 = r8[r3]
            r9 = r9[r2]
            r8 = r8[r4]
            r0 = r8[r2]
            double r0 = r0 - r9
            double r0 = r0 / r5
            return r0
        L32:
            r3 = r4
            goto L16
        L34:
            r8 = 0
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.motion.utils.LinearCurveFit.getSlope(double):double");
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public final void getPos(double d, float[] fArr) {
        double[] dArr = this.mT;
        int length = dArr.length;
        double[][] dArr2 = this.mY;
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
        double[] dArr = this.mT;
        int length = dArr.length;
        boolean z = this.mExtrapolate;
        double[][] dArr2 = this.mY;
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
