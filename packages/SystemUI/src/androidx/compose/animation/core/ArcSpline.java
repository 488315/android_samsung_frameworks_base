package androidx.compose.animation.core;

import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ArcSpline {
    public final Arc[][] arcs;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                float hypot = (float) Math.hypot(f9, f8);
                this.arcDistance = hypot;
                this.arcVelocity = hypot * f12;
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
                float f17 = 0.0f;
                float f18 = 0.0f;
                int i4 = 1;
                while (true) {
                    i2 = i3;
                    float f19 = f16;
                    double radians = (float) Math.toRadians((i4 * 90.0d) / 90);
                    float sin = ((float) Math.sin(radians)) * f13;
                    float cos = ((float) Math.cos(radians)) * f14;
                    f7 = f10;
                    f17 += (float) Math.hypot(sin - f18, cos - f19);
                    fArr[i4] = f17;
                    if (i4 == 90) {
                        break;
                    }
                    i4++;
                    f18 = sin;
                    f10 = f7;
                    f16 = cos;
                    i3 = i2;
                }
                this.arcDistance = f17;
                int i5 = i2;
                while (true) {
                    fArr[i5] = fArr[i5] / f17;
                    if (i5 == 90) {
                        break;
                    } else {
                        i5++;
                    }
                }
                float[] fArr2 = this.lut;
                int length = fArr2.length;
                for (int i6 = 0; i6 < length; i6++) {
                    float f20 = i6 / 100.0f;
                    int binarySearch = Arrays.binarySearch(fArr, 0, 91, f20);
                    if (binarySearch >= 0) {
                        fArr2[i6] = binarySearch / f15;
                    } else if (binarySearch == -1) {
                        fArr2[i6] = f7;
                    } else {
                        int i7 = -binarySearch;
                        int i8 = i7 - 2;
                        float f21 = i8;
                        float f22 = fArr[i8];
                        fArr2[i6] = (((f20 - f22) / (fArr[i7 - 1] - f22)) + f21) / f15;
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
            float f3 = 0.0f;
            if (f2 > 0.0f) {
                f3 = 1.0f;
                if (f2 < 1.0f) {
                    float f4 = f2 * 100;
                    int i = (int) f4;
                    float[] fArr = this.lut;
                    float f5 = fArr[i];
                    f3 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(fArr[i + 1], f5, f4 - i, f5);
                }
            }
            double d = f3 * 1.5707964f;
            this.tmpSinAngle = (float) Math.sin(d);
            this.tmpCosAngle = (float) Math.cos(d);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0026, code lost:
    
        if (r6 == 1) goto L18;
     */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0044 A[LOOP:1: B:14:0x0042->B:15:0x0044, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ArcSpline(int[] r23, float[] r24, float[][] r25) {
        /*
            r22 = this;
            r0 = r24
            r22.<init>()
            int r1 = r0.length
            r2 = 1
            int r1 = r1 - r2
            androidx.compose.animation.core.ArcSpline$Arc[][] r3 = new androidx.compose.animation.core.ArcSpline.Arc[r1][]
            r4 = 0
            r6 = r2
            r7 = r6
            r5 = r4
        Le:
            if (r5 >= r1) goto L73
            r8 = r23[r5]
            r9 = 2
            r10 = 3
            if (r8 == 0) goto L24
            if (r8 == r2) goto L2d
            if (r8 == r9) goto L2b
            if (r8 == r10) goto L26
            r10 = 4
            if (r8 == r10) goto L24
            r10 = 5
            if (r8 == r10) goto L24
            r11 = r7
            goto L2f
        L24:
            r11 = r10
            goto L2f
        L26:
            if (r6 != r2) goto L2d
            goto L2b
        L29:
            r11 = r6
            goto L2f
        L2b:
            r6 = r9
            goto L29
        L2d:
            r6 = r2
            goto L29
        L2f:
            r7 = r25[r5]
            int r8 = r5 + 1
            r18 = r25[r8]
            r12 = r0[r5]
            r13 = r0[r8]
            int r10 = r7.length
            int r10 = r10 / r9
            int r14 = r7.length
            int r14 = r14 % r9
            int r9 = r14 + r10
            androidx.compose.animation.core.ArcSpline$Arc[] r10 = new androidx.compose.animation.core.ArcSpline.Arc[r9]
            r14 = r4
        L42:
            if (r14 >= r9) goto L6c
            int r15 = r14 * 2
            r16 = r10
            androidx.compose.animation.core.ArcSpline$Arc r10 = new androidx.compose.animation.core.ArcSpline$Arc
            r17 = r14
            r14 = r7[r15]
            int r19 = r15 + 1
            r20 = r15
            r15 = r7[r19]
            r20 = r18[r20]
            r19 = r18[r19]
            r21 = r19
            r19 = r16
            r16 = r20
            r20 = r17
            r17 = r21
            r10.<init>(r11, r12, r13, r14, r15, r16, r17)
            r19[r20] = r10
            int r14 = r20 + 1
            r10 = r19
            goto L42
        L6c:
            r19 = r10
            r3[r5] = r19
            r5 = r8
            r7 = r11
            goto Le
        L73:
            r5 = r22
            r5.arcs = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.ArcSpline.<init>(int[], float[], float[][]):void");
    }
}
