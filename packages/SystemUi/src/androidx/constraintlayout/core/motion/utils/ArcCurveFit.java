package androidx.constraintlayout.core.motion.utils;

import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ArcCurveFit extends CurveFit {
    public final Arc[] mArcs;
    public final boolean mExtrapolate = true;
    public final double[] mTime;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Arc {
        public static final double[] ourPercent = new double[91];
        public final boolean linear;
        public double mArcDistance;
        public final double mArcVelocity;
        public final double mEllipseA;
        public final double mEllipseB;
        public final double mEllipseCenterX;
        public final double mEllipseCenterY;
        public final double[] mLut;
        public final double mOneOverDeltaTime;
        public final double mTime1;
        public final double mTime2;
        public double mTmpCosAngle;
        public double mTmpSinAngle;
        public final boolean mVertical;
        public final double mX1;
        public final double mX2;
        public final double mY1;
        public final double mY2;

        public Arc(int i, double d, double d2, double d3, double d4, double d5, double d6) {
            double[] dArr;
            double d7 = d3;
            this.linear = false;
            boolean z = i == 1;
            this.mVertical = z;
            this.mTime1 = d;
            this.mTime2 = d2;
            double d8 = 1.0d / (d2 - d);
            this.mOneOverDeltaTime = d8;
            if (3 == i) {
                this.linear = true;
            }
            double d9 = d5 - d7;
            double d10 = d6 - d4;
            if (this.linear || Math.abs(d9) < 0.001d || Math.abs(d10) < 0.001d) {
                this.linear = true;
                this.mX1 = d7;
                this.mX2 = d5;
                this.mY1 = d4;
                this.mY2 = d6;
                double hypot = Math.hypot(d10, d9);
                this.mArcDistance = hypot;
                this.mArcVelocity = hypot * d8;
                this.mEllipseCenterX = d9 / (d2 - d);
                this.mEllipseCenterY = d10 / (d2 - d);
                return;
            }
            this.mLut = new double[101];
            this.mEllipseA = (z ? -1 : 1) * d9;
            this.mEllipseB = d10 * (z ? 1 : -1);
            this.mEllipseCenterX = z ? d5 : d7;
            this.mEllipseCenterY = z ? d4 : d6;
            double d11 = d4 - d6;
            int i2 = 0;
            double d12 = 0.0d;
            double d13 = 0.0d;
            double d14 = 0.0d;
            while (true) {
                dArr = ourPercent;
                if (i2 >= 91) {
                    break;
                }
                double d15 = d9;
                double radians = Math.toRadians((i2 * 90.0d) / 90);
                double sin = Math.sin(radians) * d15;
                double cos = Math.cos(radians) * d11;
                if (i2 > 0) {
                    d12 += Math.hypot(sin - d13, cos - d14);
                    dArr[i2] = d12;
                }
                i2++;
                d14 = cos;
                d13 = sin;
                d9 = d15;
            }
            this.mArcDistance = d12;
            for (int i3 = 0; i3 < 91; i3++) {
                dArr[i3] = dArr[i3] / d12;
            }
            int i4 = 0;
            while (true) {
                double[] dArr2 = this.mLut;
                if (i4 >= dArr2.length) {
                    this.mArcVelocity = this.mArcDistance * this.mOneOverDeltaTime;
                    return;
                }
                double length = i4 / (dArr2.length - 1);
                int binarySearch = Arrays.binarySearch(dArr, length);
                if (binarySearch >= 0) {
                    dArr2[i4] = binarySearch / 90;
                } else if (binarySearch == -1) {
                    dArr2[i4] = 0.0d;
                } else {
                    int i5 = -binarySearch;
                    int i6 = i5 - 2;
                    double d16 = dArr[i6];
                    dArr2[i4] = (((length - d16) / (dArr[i5 - 1] - d16)) + i6) / 90;
                }
                i4++;
            }
        }

        public final double getDX() {
            double d = this.mEllipseA * this.mTmpCosAngle;
            double hypot = this.mArcVelocity / Math.hypot(d, (-this.mEllipseB) * this.mTmpSinAngle);
            if (this.mVertical) {
                d = -d;
            }
            return d * hypot;
        }

        public final double getDY() {
            double d = this.mEllipseA * this.mTmpCosAngle;
            double d2 = (-this.mEllipseB) * this.mTmpSinAngle;
            double hypot = this.mArcVelocity / Math.hypot(d, d2);
            return this.mVertical ? (-d2) * hypot : d2 * hypot;
        }

        public final double getLinearX(double d) {
            double d2 = (d - this.mTime1) * this.mOneOverDeltaTime;
            double d3 = this.mX2;
            double d4 = this.mX1;
            return ((d3 - d4) * d2) + d4;
        }

        public final double getLinearY(double d) {
            double d2 = (d - this.mTime1) * this.mOneOverDeltaTime;
            double d3 = this.mY2;
            double d4 = this.mY1;
            return ((d3 - d4) * d2) + d4;
        }

        public final double getX() {
            return (this.mEllipseA * this.mTmpSinAngle) + this.mEllipseCenterX;
        }

        public final double getY() {
            return (this.mEllipseB * this.mTmpCosAngle) + this.mEllipseCenterY;
        }

        public final void setPoint(double d) {
            double d2 = (this.mVertical ? this.mTime2 - d : d - this.mTime1) * this.mOneOverDeltaTime;
            double d3 = 0.0d;
            if (d2 > 0.0d) {
                d3 = 1.0d;
                if (d2 < 1.0d) {
                    double[] dArr = this.mLut;
                    double length = d2 * (dArr.length - 1);
                    int i = (int) length;
                    double d4 = dArr[i];
                    d3 = ((dArr[i + 1] - d4) * (length - i)) + d4;
                }
            }
            double d5 = d3 * 1.5707963267948966d;
            this.mTmpSinAngle = Math.sin(d5);
            this.mTmpCosAngle = Math.cos(d5);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0028, code lost:
    
        if (r5 == 1) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ArcCurveFit(int[] iArr, double[] dArr, double[][] dArr2) {
        this.mTime = dArr;
        this.mArcs = new Arc[dArr.length - 1];
        int i = 1;
        int i2 = 1;
        int i3 = 0;
        while (true) {
            Arc[] arcArr = this.mArcs;
            if (i3 >= arcArr.length) {
                return;
            }
            int i4 = iArr[i3];
            if (i4 != 0) {
                if (i4 != 1) {
                    if (i4 != 2) {
                        if (i4 == 3) {
                        }
                    }
                    i = 2;
                    i2 = i;
                }
                i = 1;
                i2 = i;
            } else {
                i2 = 3;
            }
            double d = dArr[i3];
            int i5 = i3 + 1;
            double d2 = dArr[i5];
            double[] dArr3 = dArr2[i3];
            double d3 = dArr3[0];
            double d4 = dArr3[1];
            double[] dArr4 = dArr2[i5];
            arcArr[i3] = new Arc(i2, d, d2, d3, d4, dArr4[0], dArr4[1]);
            i3 = i5;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public final void getPos(double d, double[] dArr) {
        boolean z = this.mExtrapolate;
        Arc[] arcArr = this.mArcs;
        if (z) {
            Arc arc = arcArr[0];
            double d2 = arc.mTime1;
            if (d < d2) {
                double d3 = d - d2;
                if (arc.linear) {
                    double linearX = arc.getLinearX(d2);
                    Arc arc2 = arcArr[0];
                    dArr[0] = (arc2.mEllipseCenterX * d3) + linearX;
                    dArr[1] = (d3 * arcArr[0].mEllipseCenterY) + arc2.getLinearY(d2);
                    return;
                }
                arc.setPoint(d2);
                dArr[0] = (arcArr[0].getDX() * d3) + arcArr[0].getX();
                dArr[1] = (arcArr[0].getDY() * d3) + arcArr[0].getY();
                return;
            }
            if (d > arcArr[arcArr.length - 1].mTime2) {
                double d4 = arcArr[arcArr.length - 1].mTime2;
                double d5 = d - d4;
                int length = arcArr.length - 1;
                Arc arc3 = arcArr[length];
                if (arc3.linear) {
                    double linearX2 = arc3.getLinearX(d4);
                    Arc arc4 = arcArr[length];
                    dArr[0] = (arc4.mEllipseCenterX * d5) + linearX2;
                    dArr[1] = (d5 * arcArr[length].mEllipseCenterY) + arc4.getLinearY(d4);
                    return;
                }
                arc3.setPoint(d);
                dArr[0] = (arcArr[length].getDX() * d5) + arcArr[length].getX();
                dArr[1] = (arcArr[length].getDY() * d5) + arcArr[length].getY();
                return;
            }
        } else {
            double d6 = arcArr[0].mTime1;
            if (d < d6) {
                d = d6;
            }
            if (d > arcArr[arcArr.length - 1].mTime2) {
                d = arcArr[arcArr.length - 1].mTime2;
            }
        }
        for (int i = 0; i < arcArr.length; i++) {
            Arc arc5 = arcArr[i];
            if (d <= arc5.mTime2) {
                if (arc5.linear) {
                    dArr[0] = arc5.getLinearX(d);
                    dArr[1] = arcArr[i].getLinearY(d);
                    return;
                } else {
                    arc5.setPoint(d);
                    dArr[0] = arcArr[i].getX();
                    dArr[1] = arcArr[i].getY();
                    return;
                }
            }
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public final void getSlope(double d, double[] dArr) {
        Arc[] arcArr = this.mArcs;
        double d2 = arcArr[0].mTime1;
        if (d < d2) {
            d = d2;
        } else if (d > arcArr[arcArr.length - 1].mTime2) {
            d = arcArr[arcArr.length - 1].mTime2;
        }
        for (int i = 0; i < arcArr.length; i++) {
            Arc arc = arcArr[i];
            if (d <= arc.mTime2) {
                if (arc.linear) {
                    dArr[0] = arc.mEllipseCenterX;
                    dArr[1] = arc.mEllipseCenterY;
                    return;
                } else {
                    arc.setPoint(d);
                    dArr[0] = arcArr[i].getDX();
                    dArr[1] = arcArr[i].getDY();
                    return;
                }
            }
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public final double[] getTimePoints() {
        return this.mTime;
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public final double getSlope(double d) {
        Arc[] arcArr = this.mArcs;
        double d2 = arcArr[0].mTime1;
        if (d < d2) {
            d = d2;
        }
        if (d > arcArr[arcArr.length - 1].mTime2) {
            d = arcArr[arcArr.length - 1].mTime2;
        }
        for (int i = 0; i < arcArr.length; i++) {
            Arc arc = arcArr[i];
            if (d <= arc.mTime2) {
                if (arc.linear) {
                    return arc.mEllipseCenterX;
                }
                arc.setPoint(d);
                return arcArr[i].getDX();
            }
        }
        return Double.NaN;
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public final void getPos(double d, float[] fArr) {
        boolean z = this.mExtrapolate;
        Arc[] arcArr = this.mArcs;
        if (z) {
            Arc arc = arcArr[0];
            double d2 = arc.mTime1;
            if (d < d2) {
                double d3 = d - d2;
                if (arc.linear) {
                    double linearX = arc.getLinearX(d2);
                    Arc arc2 = arcArr[0];
                    fArr[0] = (float) ((arc2.mEllipseCenterX * d3) + linearX);
                    fArr[1] = (float) ((d3 * arcArr[0].mEllipseCenterY) + arc2.getLinearY(d2));
                    return;
                }
                arc.setPoint(d2);
                fArr[0] = (float) ((arcArr[0].getDX() * d3) + arcArr[0].getX());
                fArr[1] = (float) ((arcArr[0].getDY() * d3) + arcArr[0].getY());
                return;
            }
            if (d > arcArr[arcArr.length - 1].mTime2) {
                double d4 = arcArr[arcArr.length - 1].mTime2;
                double d5 = d - d4;
                int length = arcArr.length - 1;
                Arc arc3 = arcArr[length];
                if (arc3.linear) {
                    double linearX2 = arc3.getLinearX(d4);
                    Arc arc4 = arcArr[length];
                    fArr[0] = (float) ((arc4.mEllipseCenterX * d5) + linearX2);
                    fArr[1] = (float) ((d5 * arcArr[length].mEllipseCenterY) + arc4.getLinearY(d4));
                    return;
                }
                arc3.setPoint(d);
                fArr[0] = (float) arcArr[length].getX();
                fArr[1] = (float) arcArr[length].getY();
                return;
            }
        } else {
            double d6 = arcArr[0].mTime1;
            if (d < d6) {
                d = d6;
            } else if (d > arcArr[arcArr.length - 1].mTime2) {
                d = arcArr[arcArr.length - 1].mTime2;
            }
        }
        for (int i = 0; i < arcArr.length; i++) {
            Arc arc5 = arcArr[i];
            if (d <= arc5.mTime2) {
                if (arc5.linear) {
                    fArr[0] = (float) arc5.getLinearX(d);
                    fArr[1] = (float) arcArr[i].getLinearY(d);
                    return;
                } else {
                    arc5.setPoint(d);
                    fArr[0] = (float) arcArr[i].getX();
                    fArr[1] = (float) arcArr[i].getY();
                    return;
                }
            }
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public final double getPos(double d) {
        boolean z = this.mExtrapolate;
        Arc[] arcArr = this.mArcs;
        if (z) {
            Arc arc = arcArr[0];
            double d2 = arc.mTime1;
            if (d < d2) {
                double d3 = d - d2;
                if (arc.linear) {
                    return (d3 * arcArr[0].mEllipseCenterX) + arc.getLinearX(d2);
                }
                arc.setPoint(d2);
                return (arcArr[0].getDX() * d3) + arcArr[0].getX();
            }
            if (d > arcArr[arcArr.length - 1].mTime2) {
                double d4 = arcArr[arcArr.length - 1].mTime2;
                double d5 = d - d4;
                int length = arcArr.length - 1;
                return (d5 * arcArr[length].mEllipseCenterX) + arcArr[length].getLinearX(d4);
            }
        } else {
            double d6 = arcArr[0].mTime1;
            if (d < d6) {
                d = d6;
            } else if (d > arcArr[arcArr.length - 1].mTime2) {
                d = arcArr[arcArr.length - 1].mTime2;
            }
        }
        for (int i = 0; i < arcArr.length; i++) {
            Arc arc2 = arcArr[i];
            if (d <= arc2.mTime2) {
                if (arc2.linear) {
                    return arc2.getLinearX(d);
                }
                arc2.setPoint(d);
                return arcArr[i].getX();
            }
        }
        return Double.NaN;
    }
}
