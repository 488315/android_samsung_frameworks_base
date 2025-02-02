package androidx.constraintlayout.core.motion.utils;

/* loaded from: classes.dex */
public final class LinearCurveFit extends CurveFit {
    double[] mSlopeTemp;

    /* renamed from: mT */
    private double[] f2mT;

    /* renamed from: mY */
    private double[][] f3mY;

    public LinearCurveFit(double[] dArr, double[][] dArr2) {
        int length = dArr2[0].length;
        this.mSlopeTemp = new double[length];
        this.f2mT = dArr;
        this.f3mY = dArr2;
        if (length > 2) {
            double d = 0.0d;
            int i = 0;
            while (i < dArr.length) {
                double d2 = dArr2[i][0];
                if (i > 0) {
                    double d3 = d2 - d;
                    Math.hypot(d3, d3);
                }
                i++;
                d = d2;
            }
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public final void getPos(double d, double[] dArr) {
        double[] dArr2 = this.f2mT;
        int length = dArr2.length;
        int i = 0;
        int length2 = this.f3mY[0].length;
        double d2 = dArr2[0];
        if (d <= d2) {
            getSlope(d2, this.mSlopeTemp);
            for (int i2 = 0; i2 < length2; i2++) {
                dArr[i2] = ((d - this.f2mT[0]) * this.mSlopeTemp[i2]) + this.f3mY[0][i2];
            }
            return;
        }
        int i3 = length - 1;
        double d3 = dArr2[i3];
        if (d >= d3) {
            getSlope(d3, this.mSlopeTemp);
            while (i < length2) {
                dArr[i] = ((d - this.f2mT[i3]) * this.mSlopeTemp[i]) + this.f3mY[i3][i];
                i++;
            }
            return;
        }
        int i4 = 0;
        while (i4 < length - 1) {
            if (d == this.f2mT[i4]) {
                for (int i5 = 0; i5 < length2; i5++) {
                    dArr[i5] = this.f3mY[i4][i5];
                }
            }
            double[] dArr3 = this.f2mT;
            int i6 = i4 + 1;
            double d4 = dArr3[i6];
            if (d < d4) {
                double d5 = dArr3[i4];
                double d6 = (d - d5) / (d4 - d5);
                while (i < length2) {
                    double[][] dArr4 = this.f3mY;
                    dArr[i] = (dArr4[i6][i] * d6) + ((1.0d - d6) * dArr4[i4][i]);
                    i++;
                }
                return;
            }
            i4 = i6;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0017, code lost:
    
        if (r11 >= r4) goto L4;
     */
    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void getSlope(double d, double[] dArr) {
        double[] dArr2 = this.f2mT;
        int length = dArr2.length;
        int length2 = this.f3mY[0].length;
        double d2 = dArr2[0];
        if (d > d2) {
            d2 = dArr2[length - 1];
        }
        d = d2;
        int i = 0;
        while (i < length - 1) {
            double[] dArr3 = this.f2mT;
            int i2 = i + 1;
            double d3 = dArr3[i2];
            if (d <= d3) {
                double d4 = d3 - dArr3[i];
                for (int i3 = 0; i3 < length2; i3++) {
                    double[][] dArr4 = this.f3mY;
                    dArr[i3] = (dArr4[i2][i3] - dArr4[i][i3]) / d4;
                }
                return;
            }
            i = i2;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public final double[] getTimePoints() {
        return this.f2mT;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0012, code lost:
    
        if (r9 >= r3) goto L4;
     */
    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final double getSlope(double d) {
        double[] dArr = this.f2mT;
        int length = dArr.length;
        double d2 = dArr[0];
        if (d >= d2) {
            d2 = dArr[length - 1];
        }
        d = d2;
        int i = 0;
        while (i < length - 1) {
            double[] dArr2 = this.f2mT;
            int i2 = i + 1;
            double d3 = dArr2[i2];
            if (d <= d3) {
                double d4 = d3 - dArr2[i];
                double[][] dArr3 = this.f3mY;
                return (dArr3[i2][0] - dArr3[i][0]) / d4;
            }
            i = i2;
        }
        return 0.0d;
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public final void getPos(double d, float[] fArr) {
        double[] dArr = this.f2mT;
        int length = dArr.length;
        int i = 0;
        int length2 = this.f3mY[0].length;
        double d2 = dArr[0];
        if (d <= d2) {
            getSlope(d2, this.mSlopeTemp);
            for (int i2 = 0; i2 < length2; i2++) {
                fArr[i2] = (float) (((d - this.f2mT[0]) * this.mSlopeTemp[i2]) + this.f3mY[0][i2]);
            }
            return;
        }
        int i3 = length - 1;
        double d3 = dArr[i3];
        if (d >= d3) {
            getSlope(d3, this.mSlopeTemp);
            while (i < length2) {
                fArr[i] = (float) (((d - this.f2mT[i3]) * this.mSlopeTemp[i]) + this.f3mY[i3][i]);
                i++;
            }
            return;
        }
        int i4 = 0;
        while (i4 < i3) {
            if (d == this.f2mT[i4]) {
                for (int i5 = 0; i5 < length2; i5++) {
                    fArr[i5] = (float) this.f3mY[i4][i5];
                }
            }
            double[] dArr2 = this.f2mT;
            int i6 = i4 + 1;
            double d4 = dArr2[i6];
            if (d < d4) {
                double d5 = dArr2[i4];
                double d6 = (d - d5) / (d4 - d5);
                while (i < length2) {
                    double[][] dArr3 = this.f3mY;
                    fArr[i] = (float) ((dArr3[i6][i] * d6) + ((1.0d - d6) * dArr3[i4][i]));
                    i++;
                }
                return;
            }
            i4 = i6;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public final double getPos(double d) {
        double[] dArr = this.f2mT;
        int length = dArr.length;
        double d2 = dArr[0];
        if (d <= d2) {
            return (getSlope(d2) * (d - d2)) + this.f3mY[0][0];
        }
        int i = length - 1;
        double d3 = dArr[i];
        if (d >= d3) {
            return (getSlope(d3) * (d - d3)) + this.f3mY[i][0];
        }
        int i2 = 0;
        while (i2 < i) {
            double[] dArr2 = this.f2mT;
            double d4 = dArr2[i2];
            if (d == d4) {
                return this.f3mY[i2][0];
            }
            int i3 = i2 + 1;
            double d5 = dArr2[i3];
            if (d < d5) {
                double d6 = (d - d4) / (d5 - d4);
                double[][] dArr3 = this.f3mY;
                return (dArr3[i3][0] * d6) + ((1.0d - d6) * dArr3[i2][0]);
            }
            i2 = i3;
        }
        return 0.0d;
    }
}
