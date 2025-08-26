package androidx.compose.animation.core;

import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class ArcSpline {
    public final Arc[][] arcs;

    public final class Arc {
        public final float arcDistance;
        public final float arcVelocity;
        public final float ellipseA;
        public final float ellipseB;
        public final float ellipseCenterX;
        public final float ellipseCenterY;
        public final boolean isLinear;
        public final float[] lut;
        public final float oneOverDeltaTime;
        public final float time1;
        public final float time2;
        public float tmpCosAngle;
        public float tmpSinAngle;
        public final float vertical;
        public final float x1;
        public final float x2;
        public final float y1;
        public final float y2;

        public Arc(int i, float f, float f2, float f3, float f4, float f5, float f6) {
            boolean z;
            int i2;
            float f7;
            this.time1 = f;
            this.time2 = f2;
            this.x1 = f3;
            this.y1 = f4;
            this.x2 = f5;
            this.y2 = f6;
            float f8 = f5 - f3;
            float f9 = f6 - f4;
            float f10 = 0.0f;
            int i3 = 1;
            boolean z2 = i == 1 || (i == 4 ? f9 > 0.0f : !(i != 5 || f9 >= 0.0f));
            float f11 = z2 ? -1.0f : 1.0f;
            this.vertical = f11;
            float f12 = 1 / (f2 - f);
            this.oneOverDeltaTime = f12;
            this.lut = new float[101];
            boolean z3 = i == 3;
            if (z3 || Math.abs(f8) < 0.001f || Math.abs(f9) < 0.001f) {
                float fHypot = (float) Math.hypot(f9, f8);
                this.arcDistance = fHypot;
                this.arcVelocity = fHypot * f12;
                this.ellipseCenterX = f8 * f12;
                this.ellipseCenterY = f9 * f12;
                this.ellipseA = Float.NaN;
                this.ellipseB = Float.NaN;
                z = true;
            } else {
                this.ellipseA = f8 * f11;
                this.ellipseB = f9 * (-f11);
                this.ellipseCenterX = z2 ? f5 : f3;
                this.ellipseCenterY = z2 ? f4 : f6;
                float f13 = f5 - f3;
                float f14 = f4 - f6;
                float[] fArr = ArcSplineKt.OurPercentCache;
                float f15 = 90;
                float f16 = f14;
                float fHypot2 = 0.0f;
                float f17 = 0.0f;
                int i4 = 1;
                while (true) {
                    i2 = i3;
                    float f18 = f16;
                    double radians = (float) Math.toRadians((i4 * 90.0d) / 90);
                    float fSin = ((float) Math.sin(radians)) * f13;
                    float fCos = ((float) Math.cos(radians)) * f14;
                    f7 = f10;
                    fHypot2 += (float) Math.hypot(fSin - f17, fCos - f18);
                    fArr[i4] = fHypot2;
                    if (i4 == 90) {
                        break;
                    }
                    i4++;
                    f17 = fSin;
                    f10 = f7;
                    f16 = fCos;
                    i3 = i2;
                }
                this.arcDistance = fHypot2;
                int i5 = i2;
                while (true) {
                    fArr[i5] = fArr[i5] / fHypot2;
                    if (i5 == 90) {
                        break;
                    } else {
                        i5++;
                    }
                }
                float[] fArr2 = this.lut;
                int length = fArr2.length;
                for (int i6 = 0; i6 < length; i6++) {
                    float f19 = i6 / 100.0f;
                    int iBinarySearch = Arrays.binarySearch(fArr, 0, 91, f19);
                    if (iBinarySearch >= 0) {
                        fArr2[i6] = iBinarySearch / f15;
                    } else if (iBinarySearch == -1) {
                        fArr2[i6] = f7;
                    } else {
                        int i7 = -iBinarySearch;
                        int i8 = i7 - 2;
                        float f20 = i8;
                        float f21 = fArr[i8];
                        fArr2[i6] = (((f19 - f21) / (fArr[i7 - 1] - f21)) + f20) / f15;
                    }
                }
                this.arcVelocity = this.arcDistance * this.oneOverDeltaTime;
                z = z3;
            }
            this.isLinear = z;
        }

        public final float calcDX() {
            float f = this.ellipseA * this.tmpCosAngle;
            return f * this.vertical * (this.arcVelocity / ((float) Math.hypot(f, (-this.ellipseB) * this.tmpSinAngle)));
        }

        public final float calcDY() {
            float f = this.ellipseA * this.tmpCosAngle;
            float f2 = (-this.ellipseB) * this.tmpSinAngle;
            return f2 * this.vertical * (this.arcVelocity / ((float) Math.hypot(f, f2)));
        }

        public final void setPoint(float f) {
            float f2 = (this.vertical == -1.0f ? this.time2 - f : f - this.time1) * this.oneOverDeltaTime;
            float fM$1 = 0.0f;
            if (f2 > 0.0f) {
                fM$1 = 1.0f;
                if (f2 < 1.0f) {
                    float f3 = f2 * 100;
                    int i = (int) f3;
                    float[] fArr = this.lut;
                    float f4 = fArr[i];
                    fM$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(fArr[i + 1], f4, f3 - i, f4);
                }
            }
            double d = fM$1 * 1.5707964f;
            this.tmpSinAngle = (float) Math.sin(d);
            this.tmpCosAngle = (float) Math.cos(d);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0024 A[PHI: r10
      0x0024: PHI (r10v1 int) = (r10v0 int), (r10v8 int), (r10v9 int) binds: [B:5:0x0014, B:10:0x001d, B:12:0x0020] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x002d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ArcSpline(int[] iArr, float[] fArr, float[][] fArr2) {
        int i;
        int length = fArr.length - 1;
        Arc[][] arcArr = new Arc[length][];
        int i2 = 1;
        int i3 = 1;
        int i4 = 0;
        while (i4 < length) {
            int i5 = iArr[i4];
            int i6 = 3;
            if (i5 == 0) {
                i = i6;
            } else if (i5 == 1) {
                i2 = 1;
                i = i2;
            } else {
                if (i5 != 2) {
                    if (i5 != 3) {
                        i6 = 4;
                        if (i5 != 4) {
                            i6 = 5;
                            if (i5 != 5) {
                                i = i3;
                            }
                        }
                    } else {
                        if (i2 == 1) {
                        }
                        i = i2;
                    }
                }
                i2 = 2;
                i = i2;
            }
            float[] fArr3 = fArr2[i4];
            int i7 = i4 + 1;
            float[] fArr4 = fArr2[i7];
            float f = fArr[i4];
            float f2 = fArr[i7];
            int length2 = (fArr3.length % 2) + (fArr3.length / 2);
            Arc[] arcArr2 = new Arc[length2];
            int i8 = 0;
            while (i8 < length2) {
                int i9 = i8 * 2;
                Arc[] arcArr3 = arcArr2;
                int i10 = i8;
                int i11 = i9 + 1;
                arcArr3[i10] = new Arc(i, f, f2, fArr3[i9], fArr3[i11], fArr4[i9], fArr4[i11]);
                i8 = i10 + 1;
                arcArr2 = arcArr3;
            }
            arcArr[i4] = arcArr2;
            i4 = i7;
            i3 = i;
        }
        this.arcs = arcArr;
    }
}
