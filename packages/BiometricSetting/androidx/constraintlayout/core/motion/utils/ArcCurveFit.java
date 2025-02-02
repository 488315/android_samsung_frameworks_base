package androidx.constraintlayout.core.motion.utils;

import java.util.Arrays;

/* loaded from: classes.dex */
public final class ArcCurveFit extends CurveFit {
    Arc[] mArcs;
    private final double[] mTime;

    private static class Arc {
        private static double[] sOurPercent = new double[91];
        double mArcDistance;
        double mArcVelocity;
        double mEllipseA;
        double mEllipseB;
        double mEllipseCenterX;
        double mEllipseCenterY;
        boolean mLinear;
        double[] mLut;
        double mOneOverDeltaTime;
        double mTime1;
        double mTime2;
        double mTmpCosAngle;
        double mTmpSinAngle;
        boolean mVertical;
        double mX1;
        double mX2;
        double mY1;
        double mY2;

        Arc(int i, double d, double d2, double d3, double d4, double d5, double d6) {
            double[] dArr;
            this.mLinear = false;
            double d7 = d5 - d3;
            double d8 = d6 - d4;
            if (i == 1) {
                this.mVertical = true;
            } else if (i == 4) {
                this.mVertical = d8 > 0.0d;
            } else if (i != 5) {
                this.mVertical = false;
            } else {
                this.mVertical = d8 < 0.0d;
            }
            this.mTime1 = d;
            this.mTime2 = d2;
            this.mOneOverDeltaTime = 1.0d / (d2 - d);
            if (3 == i) {
                this.mLinear = true;
            }
            if (this.mLinear || Math.abs(d7) < 0.001d || Math.abs(d8) < 0.001d) {
                this.mLinear = true;
                this.mX1 = d3;
                this.mX2 = d5;
                this.mY1 = d4;
                this.mY2 = d6;
                double hypot = Math.hypot(d8, d7);
                this.mArcDistance = hypot;
                this.mArcVelocity = hypot * this.mOneOverDeltaTime;
                double d9 = this.mTime2;
                double d10 = this.mTime1;
                this.mEllipseCenterX = d7 / (d9 - d10);
                this.mEllipseCenterY = d8 / (d9 - d10);
                return;
            }
            this.mLut = new double[101];
            boolean z = this.mVertical;
            this.mEllipseA = (z ? -1 : 1) * d7;
            this.mEllipseB = d8 * (z ? 1 : -1);
            this.mEllipseCenterX = z ? d5 : d3;
            this.mEllipseCenterY = z ? d4 : d6;
            double d11 = d4 - d6;
            double d12 = 0.0d;
            double d13 = 0.0d;
            double d14 = 0.0d;
            int i2 = 0;
            while (true) {
                dArr = sOurPercent;
                if (i2 >= 91) {
                    break;
                }
                double d15 = d11;
                double radians = Math.toRadians((i2 * 90.0d) / 90);
                double sin = Math.sin(radians) * d7;
                double cos = Math.cos(radians) * d15;
                if (i2 > 0) {
                    d12 += Math.hypot(sin - d13, cos - d14);
                    dArr[i2] = d12;
                }
                i2++;
                d14 = cos;
                d13 = sin;
                d11 = d15;
            }
            this.mArcDistance = d12;
            for (int i3 = 0; i3 < 91; i3++) {
                dArr[i3] = dArr[i3] / d12;
            }
            int i4 = 0;
            while (true) {
                if (i4 >= this.mLut.length) {
                    this.mArcVelocity = this.mArcDistance * this.mOneOverDeltaTime;
                    return;
                }
                double length = i4 / (r1.length - 1);
                int binarySearch = Arrays.binarySearch(dArr, length);
                if (binarySearch >= 0) {
                    this.mLut[i4] = binarySearch / 90;
                } else if (binarySearch == -1) {
                    this.mLut[i4] = 0.0d;
                } else {
                    int i5 = -binarySearch;
                    int i6 = i5 - 2;
                    double d16 = dArr[i6];
                    this.mLut[i4] = (((length - d16) / (dArr[i5 - 1] - d16)) + i6) / 90;
                }
                i4++;
            }
        }

        final double getDX() {
            double d = this.mEllipseA * this.mTmpCosAngle;
            double hypot = this.mArcVelocity / Math.hypot(d, (-this.mEllipseB) * this.mTmpSinAngle);
            if (this.mVertical) {
                d = -d;
            }
            return d * hypot;
        }

        final double getDY() {
            double d = this.mEllipseA * this.mTmpCosAngle;
            double d2 = (-this.mEllipseB) * this.mTmpSinAngle;
            double hypot = this.mArcVelocity / Math.hypot(d, d2);
            return this.mVertical ? (-d2) * hypot : d2 * hypot;
        }

        public final double getLinearX(double d) {
            double d2 = (d - this.mTime1) * this.mOneOverDeltaTime;
            double d3 = this.mX1;
            return ((this.mX2 - d3) * d2) + d3;
        }

        public final double getLinearY(double d) {
            double d2 = (d - this.mTime1) * this.mOneOverDeltaTime;
            double d3 = this.mY1;
            return ((this.mY2 - d3) * d2) + d3;
        }

        final double getX() {
            return (this.mEllipseA * this.mTmpSinAngle) + this.mEllipseCenterX;
        }

        final double getY() {
            return (this.mEllipseB * this.mTmpCosAngle) + this.mEllipseCenterY;
        }

        final void setPoint(double d) {
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

    /* JADX WARN: Code restructure failed: missing block: B:16:0x002c, code lost:
    
        if (r5 == 1) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ArcCurveFit(int[] iArr, double[] dArr, double[][] dArr2) {
        this.mTime = dArr;
        this.mArcs = new Arc[dArr.length - 1];
        int i = 0;
        int i2 = 1;
        int i3 = 1;
        while (true) {
            Arc[] arcArr = this.mArcs;
            if (i >= arcArr.length) {
                return;
            }
            int i4 = iArr[i];
            int i5 = 3;
            if (i4 != 0) {
                if (i4 != 1) {
                    if (i4 != 2) {
                        if (i4 != 3) {
                            i5 = 4;
                            if (i4 != 4) {
                                i5 = 5;
                                if (i4 != 5) {
                                    double d = dArr[i];
                                    int i6 = i + 1;
                                    double d2 = dArr[i6];
                                    double[] dArr3 = dArr2[i];
                                    double d3 = dArr3[0];
                                    double d4 = dArr3[1];
                                    double[] dArr4 = dArr2[i6];
                                    arcArr[i] = new Arc(i3, d, d2, d3, d4, dArr4[0], dArr4[1]);
                                    i = i6;
                                }
                            }
                        }
                    }
                    i2 = 2;
                    i3 = i2;
                    double d5 = dArr[i];
                    int i62 = i + 1;
                    double d22 = dArr[i62];
                    double[] dArr32 = dArr2[i];
                    double d32 = dArr32[0];
                    double d42 = dArr32[1];
                    double[] dArr42 = dArr2[i62];
                    arcArr[i] = new Arc(i3, d5, d22, d32, d42, dArr42[0], dArr42[1]);
                    i = i62;
                }
                i2 = 1;
                i3 = i2;
                double d52 = dArr[i];
                int i622 = i + 1;
                double d222 = dArr[i622];
                double[] dArr322 = dArr2[i];
                double d322 = dArr322[0];
                double d422 = dArr322[1];
                double[] dArr422 = dArr2[i622];
                arcArr[i] = new Arc(i3, d52, d222, d322, d422, dArr422[0], dArr422[1]);
                i = i622;
            }
            i3 = i5;
            double d522 = dArr[i];
            int i6222 = i + 1;
            double d2222 = dArr[i6222];
            double[] dArr3222 = dArr2[i];
            double d3222 = dArr3222[0];
            double d4222 = dArr3222[1];
            double[] dArr4222 = dArr2[i6222];
            arcArr[i] = new Arc(i3, d522, d2222, d3222, d4222, dArr4222[0], dArr4222[1]);
            i = i6222;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public final void getPos(double d, double[] dArr) {
        Arc[] arcArr = this.mArcs;
        Arc arc = arcArr[0];
        double d2 = arc.mTime1;
        if (d < d2) {
            double d3 = d - d2;
            if (arc.mLinear) {
                double linearX = arc.getLinearX(d2);
                Arc arc2 = this.mArcs[0];
                dArr[0] = (arc2.mEllipseCenterX * d3) + linearX;
                dArr[1] = (d3 * this.mArcs[0].mEllipseCenterY) + arc2.getLinearY(d2);
                return;
            }
            arc.setPoint(d2);
            dArr[0] = (this.mArcs[0].getDX() * d3) + this.mArcs[0].getX();
            dArr[1] = (d3 * this.mArcs[0].getDY()) + this.mArcs[0].getY();
            return;
        }
        if (d > arcArr[arcArr.length - 1].mTime2) {
            double d4 = arcArr[arcArr.length - 1].mTime2;
            double d5 = d - d4;
            int length = arcArr.length - 1;
            Arc arc3 = arcArr[length];
            if (arc3.mLinear) {
                double linearX2 = arc3.getLinearX(d4);
                Arc arc4 = this.mArcs[length];
                dArr[0] = (arc4.mEllipseCenterX * d5) + linearX2;
                dArr[1] = (d5 * this.mArcs[length].mEllipseCenterY) + arc4.getLinearY(d4);
                return;
            }
            arc3.setPoint(d);
            dArr[0] = (this.mArcs[length].getDX() * d5) + this.mArcs[length].getX();
            dArr[1] = (d5 * this.mArcs[length].getDY()) + this.mArcs[length].getY();
            return;
        }
        int i = 0;
        while (true) {
            Arc[] arcArr2 = this.mArcs;
            if (i >= arcArr2.length) {
                return;
            }
            Arc arc5 = arcArr2[i];
            if (d <= arc5.mTime2) {
                if (arc5.mLinear) {
                    dArr[0] = arc5.getLinearX(d);
                    dArr[1] = this.mArcs[i].getLinearY(d);
                    return;
                } else {
                    arc5.setPoint(d);
                    dArr[0] = this.mArcs[i].getX();
                    dArr[1] = this.mArcs[i].getY();
                    return;
                }
            }
            i++;
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
        int i = 0;
        while (true) {
            Arc[] arcArr2 = this.mArcs;
            if (i >= arcArr2.length) {
                return;
            }
            Arc arc = arcArr2[i];
            if (d <= arc.mTime2) {
                if (arc.mLinear) {
                    dArr[0] = arc.mEllipseCenterX;
                    dArr[1] = arc.mEllipseCenterY;
                    return;
                } else {
                    arc.setPoint(d);
                    dArr[0] = this.mArcs[i].getDX();
                    dArr[1] = this.mArcs[i].getDY();
                    return;
                }
            }
            i++;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public final double[] getTimePoints() {
        return this.mTime;
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public final double getSlope(double d) {
        Arc[] arcArr = this.mArcs;
        int i = 0;
        double d2 = arcArr[0].mTime1;
        if (d < d2) {
            d = d2;
        }
        if (d > arcArr[arcArr.length - 1].mTime2) {
            d = arcArr[arcArr.length - 1].mTime2;
        }
        while (true) {
            Arc[] arcArr2 = this.mArcs;
            if (i >= arcArr2.length) {
                return Double.NaN;
            }
            Arc arc = arcArr2[i];
            if (d <= arc.mTime2) {
                if (arc.mLinear) {
                    return arc.mEllipseCenterX;
                }
                arc.setPoint(d);
                return this.mArcs[i].getDX();
            }
            i++;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public final void getPos(double d, float[] fArr) {
        Arc[] arcArr = this.mArcs;
        Arc arc = arcArr[0];
        double d2 = arc.mTime1;
        if (d < d2) {
            double d3 = d - d2;
            if (arc.mLinear) {
                double linearX = arc.getLinearX(d2);
                Arc arc2 = this.mArcs[0];
                fArr[0] = (float) ((arc2.mEllipseCenterX * d3) + linearX);
                fArr[1] = (float) ((d3 * this.mArcs[0].mEllipseCenterY) + arc2.getLinearY(d2));
                return;
            }
            arc.setPoint(d2);
            fArr[0] = (float) ((this.mArcs[0].getDX() * d3) + this.mArcs[0].getX());
            fArr[1] = (float) ((d3 * this.mArcs[0].getDY()) + this.mArcs[0].getY());
            return;
        }
        if (d > arcArr[arcArr.length - 1].mTime2) {
            double d4 = arcArr[arcArr.length - 1].mTime2;
            double d5 = d - d4;
            int length = arcArr.length - 1;
            Arc arc3 = arcArr[length];
            if (arc3.mLinear) {
                double linearX2 = arc3.getLinearX(d4);
                Arc arc4 = this.mArcs[length];
                fArr[0] = (float) ((arc4.mEllipseCenterX * d5) + linearX2);
                fArr[1] = (float) ((d5 * this.mArcs[length].mEllipseCenterY) + arc4.getLinearY(d4));
                return;
            }
            arc3.setPoint(d);
            fArr[0] = (float) this.mArcs[length].getX();
            fArr[1] = (float) this.mArcs[length].getY();
            return;
        }
        int i = 0;
        while (true) {
            Arc[] arcArr2 = this.mArcs;
            if (i >= arcArr2.length) {
                return;
            }
            Arc arc5 = arcArr2[i];
            if (d <= arc5.mTime2) {
                if (arc5.mLinear) {
                    fArr[0] = (float) arc5.getLinearX(d);
                    fArr[1] = (float) this.mArcs[i].getLinearY(d);
                    return;
                } else {
                    arc5.setPoint(d);
                    fArr[0] = (float) this.mArcs[i].getX();
                    fArr[1] = (float) this.mArcs[i].getY();
                    return;
                }
            }
            i++;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public final double getPos(double d) {
        Arc[] arcArr = this.mArcs;
        int i = 0;
        Arc arc = arcArr[0];
        double d2 = arc.mTime1;
        if (d < d2) {
            double d3 = d - d2;
            if (arc.mLinear) {
                return (d3 * this.mArcs[0].mEllipseCenterX) + arc.getLinearX(d2);
            }
            arc.setPoint(d2);
            return (d3 * this.mArcs[0].getDX()) + this.mArcs[0].getX();
        }
        if (d > arcArr[arcArr.length - 1].mTime2) {
            double d4 = arcArr[arcArr.length - 1].mTime2;
            int length = arcArr.length - 1;
            return ((d - d4) * this.mArcs[length].mEllipseCenterX) + arcArr[length].getLinearX(d4);
        }
        while (true) {
            Arc[] arcArr2 = this.mArcs;
            if (i >= arcArr2.length) {
                return Double.NaN;
            }
            Arc arc2 = arcArr2[i];
            if (d <= arc2.mTime2) {
                if (arc2.mLinear) {
                    return arc2.getLinearX(d);
                }
                arc2.setPoint(d);
                return this.mArcs[i].getX();
            }
            i++;
        }
    }
}
