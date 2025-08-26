package androidx.compose.animation.core;

import androidx.compose.animation.AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.BezierKt;
import androidx.compose.ui.util.MathHelpersKt;

/* loaded from: classes.dex */
public final class CubicBezierEasing implements Easing {
    public final float a;
    public final float b;
    public final float c;
    public final float d;
    public final float max;
    public final float min;

    public CubicBezierEasing(float f, float f2, float f3, float f4) {
        int iWriteValidRootInUnitRange;
        this.a = f;
        this.b = f2;
        this.c = f3;
        this.d = f4;
        if (!((Float.isNaN(f) || Float.isNaN(f2) || Float.isNaN(f3) || Float.isNaN(f4)) ? false : true)) {
            StringBuilder sbM = CubicBezierEasing$$ExternalSyntheticOutline0.m("Parameters to CubicBezierEasing cannot be NaN. Actual parameters are: ", f, ", ", f2, ", ");
            sbM.append(f3);
            sbM.append(", ");
            sbM.append(f4);
            sbM.append('.');
            PreconditionsKt.throwIllegalArgumentException(sbM.toString());
        }
        float[] fArr = new float[5];
        float f5 = (f2 - 0.0f) * 3.0f;
        float f6 = (f4 - f2) * 3.0f;
        float f7 = (1.0f - f4) * 3.0f;
        double d = f5;
        double d2 = f6;
        double d3 = f7;
        double d4 = d2 * 2.0d;
        double d5 = (d - d4) + d3;
        if (d5 == 0.0d) {
            iWriteValidRootInUnitRange = d2 == d3 ? 0 : BezierKt.writeValidRootInUnitRange((float) ((d4 - d3) / (d4 - (d3 * 2.0d))), fArr, 0);
        } else {
            double d6 = -Math.sqrt((d2 * d2) - (d3 * d));
            double d7 = (-d) + d2;
            int iWriteValidRootInUnitRange2 = BezierKt.writeValidRootInUnitRange((float) ((-(d6 + d7)) / d5), fArr, 0);
            int iWriteValidRootInUnitRange3 = BezierKt.writeValidRootInUnitRange((float) ((d6 - d7) / d5), fArr, iWriteValidRootInUnitRange2) + iWriteValidRootInUnitRange2;
            if (iWriteValidRootInUnitRange3 > 1) {
                float f8 = fArr[0];
                float f9 = fArr[1];
                if (f8 > f9) {
                    fArr[0] = f9;
                    fArr[1] = f8;
                } else if (f8 == f9) {
                    iWriteValidRootInUnitRange = iWriteValidRootInUnitRange3 - 1;
                }
                iWriteValidRootInUnitRange = iWriteValidRootInUnitRange3;
            } else {
                iWriteValidRootInUnitRange = iWriteValidRootInUnitRange3;
            }
        }
        float f10 = (f6 - f5) * 2.0f;
        int iWriteValidRootInUnitRange4 = BezierKt.writeValidRootInUnitRange((-f10) / (((f7 - f6) * 2.0f) - f10), fArr, iWriteValidRootInUnitRange) + iWriteValidRootInUnitRange;
        float fMin = Math.min(0.0f, 1.0f);
        float fMax = Math.max(0.0f, 1.0f);
        for (int i = 0; i < iWriteValidRootInUnitRange4; i++) {
            float f11 = fArr[i];
            float f12 = (((((((((f2 - f4) * 3.0f) + 1.0f) - 0.0f) * f11) + (((f4 - (f2 * 2.0f)) + 0.0f) * 3.0f)) * f11) + f5) * f11) + 0.0f;
            fMin = Math.min(fMin, f12);
            fMax = Math.max(fMax, f12);
        }
        long jFloatToRawIntBits = (Float.floatToRawIntBits(fMin) << 32) | (Float.floatToRawIntBits(fMax) & 4294967295L);
        this.min = Float.intBitsToFloat((int) (jFloatToRawIntBits >> 32));
        this.max = Float.intBitsToFloat((int) (jFloatToRawIntBits & 4294967295L));
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof CubicBezierEasing)) {
            return false;
        }
        CubicBezierEasing cubicBezierEasing = (CubicBezierEasing) obj;
        return this.a == cubicBezierEasing.a && this.b == cubicBezierEasing.b && this.c == cubicBezierEasing.c && this.d == cubicBezierEasing.d;
    }

    public final int hashCode() {
        return Float.hashCode(this.d) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.c, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.b, Float.hashCode(this.a) * 31, 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("CubicBezierEasing(a=");
        sb.append(this.a);
        sb.append(", b=");
        sb.append(this.b);
        sb.append(", c=");
        sb.append(this.c);
        sb.append(", d=");
        return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.d, ')');
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0092 A[PHI: r3
      0x0092: PHI (r3v27 float) = (r3v5 float), (r3v16 float), (r3v21 float), (r3v31 float), (r3v36 float) binds: [B:128:0x0236, B:117:0x0206, B:92:0x01bb, B:47:0x00e5, B:22:0x008e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0162 A[PHI: r12
      0x0162: PHI (r12v41 float) = (r12v25 float), (r12v36 float) binds: [B:68:0x0160, B:81:0x0191] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // androidx.compose.animation.core.Easing
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final float transform(float f) {
        float f2;
        if (f <= 0.0f || f >= 1.0f) {
            return f;
        }
        float fMax = Math.max(f, 1.1920929E-7f);
        float f3 = this.a;
        float f4 = this.c;
        float f5 = f4 - fMax;
        double d = 0.0f - fMax;
        float f6 = 0.0f;
        double d2 = ((d - ((f3 - fMax) * 2.0d)) + f5) * 3.0d;
        double d3 = (r7 - r5) * 3.0d;
        double d4 = ((r7 - f5) * 3.0d) + (-r5) + (1.0f - fMax);
        float f7 = Float.NaN;
        if (Math.abs(d4 - 0.0d) >= 1.0E-7d) {
            double d5 = d2 / d4;
            double d6 = d3 / d4;
            double d7 = d / d4;
            double d8 = ((d6 * 3.0d) - (d5 * d5)) / 9.0d;
            double d9 = ((d7 * 27.0d) + ((((2.0d * d5) * d5) * d5) - ((9.0d * d5) * d6))) / 54.0d;
            double d10 = d8 * d8 * d8;
            double d11 = (d9 * d9) + d10;
            double d12 = d5 / 3.0d;
            if (d11 < 0.0d) {
                double dSqrt = Math.sqrt(-d10);
                double d13 = (-d9) / dSqrt;
                if (d13 < -1.0d) {
                    d13 = -1.0d;
                }
                if (d13 > 1.0d) {
                    d13 = 1.0d;
                }
                double dAcos = Math.acos(d13);
                double dFastCbrt = MathHelpersKt.fastCbrt((float) dSqrt) * 2.0f;
                float fCos = (float) ((Math.cos(dAcos / 3.0d) * dFastCbrt) - d12);
                float f8 = fCos < 0.0f ? 0.0f : fCos;
                if (f8 > 1.0f) {
                    f8 = 1.0f;
                }
                if (Math.abs(f8 - fCos) > 1.05E-6f) {
                    f8 = Float.NaN;
                }
                if (Float.isNaN(f8)) {
                    float fCos2 = (float) ((Math.cos((6.283185307179586d + dAcos) / 3.0d) * dFastCbrt) - d12);
                    f8 = fCos2 < 0.0f ? 0.0f : fCos2;
                    if (f8 > 1.0f) {
                        f8 = 1.0f;
                    }
                    if (Math.abs(f8 - fCos2) > 1.05E-6f) {
                        f8 = Float.NaN;
                    }
                    if (Float.isNaN(f8)) {
                        float fCos3 = (float) ((Math.cos((dAcos + 12.566370614359172d) / 3.0d) * dFastCbrt) - d12);
                        if (fCos3 >= 0.0f) {
                            f6 = fCos3;
                        }
                        f2 = f6 > 1.0f ? 1.0f : f6;
                        if (Math.abs(f2 - fCos3) <= 1.05E-6f) {
                        }
                    }
                } else {
                    f7 = f8;
                }
            } else if (d11 == 0.0d) {
                float f9 = -MathHelpersKt.fastCbrt((float) d9);
                float f10 = (float) d12;
                float f11 = (f9 * 2.0f) - f10;
                float f12 = f11 < 0.0f ? 0.0f : f11;
                if (f12 > 1.0f) {
                    f12 = 1.0f;
                }
                if (Math.abs(f12 - f11) > 1.05E-6f) {
                    f12 = Float.NaN;
                }
                if (Float.isNaN(f12)) {
                    float f13 = (-f9) - f10;
                    if (f13 >= 0.0f) {
                        f6 = f13;
                    }
                    f2 = f6 > 1.0f ? 1.0f : f6;
                    if (Math.abs(f2 - f13) <= 1.05E-6f) {
                    }
                } else {
                    f7 = f12;
                }
            } else {
                double dSqrt2 = Math.sqrt(d11);
                float fFastCbrt = (float) ((MathHelpersKt.fastCbrt((float) ((-d9) + dSqrt2)) - MathHelpersKt.fastCbrt((float) (d9 + dSqrt2))) - d12);
                if (fFastCbrt >= 0.0f) {
                    f6 = fFastCbrt;
                }
                f2 = f6 > 1.0f ? 1.0f : f6;
                if (Math.abs(f2 - fFastCbrt) <= 1.05E-6f) {
                }
            }
        } else if (Math.abs(d2 - 0.0d) >= 1.0E-7d) {
            double dSqrt3 = Math.sqrt((d3 * d3) - ((4.0d * d2) * d));
            double d14 = d2 * 2.0d;
            float f14 = (float) ((dSqrt3 - d3) / d14);
            float f15 = f14 < 0.0f ? 0.0f : f14;
            if (f15 > 1.0f) {
                f15 = 1.0f;
            }
            if (Math.abs(f15 - f14) > 1.05E-6f) {
                f15 = Float.NaN;
            }
            if (Float.isNaN(f15)) {
                float f16 = (float) (((-d3) - dSqrt3) / d14);
                if (f16 >= 0.0f) {
                    f6 = f16;
                }
                f2 = f6 > 1.0f ? 1.0f : f6;
                if (Math.abs(f2 - f16) <= 1.05E-6f) {
                }
            } else {
                f7 = f15;
            }
        } else if (Math.abs(d3 - 0.0d) >= 1.0E-7d) {
            float f17 = (float) ((-d) / d3);
            if (f17 >= 0.0f) {
                f6 = f17;
            }
            f2 = f6 > 1.0f ? 1.0f : f6;
            if (Math.abs(f2 - f17) <= 1.05E-6f) {
                f7 = f2;
            }
        }
        boolean zIsNaN = Float.isNaN(f7);
        float f18 = this.d;
        float f19 = this.b;
        if (!zIsNaN) {
            float f20 = ((((((f19 - f18) + 0.33333334f) * f7) + (f18 - (2.0f * f19))) * f7) + f19) * 3.0f * f7;
            float f21 = this.min;
            if (f20 < f21) {
                f20 = f21;
            }
            float f22 = this.max;
            return f20 > f22 ? f22 : f20;
        }
        throw new IllegalArgumentException("The cubic curve with parameters (" + f3 + ", " + f19 + ", " + f4 + ", " + f18 + ") has no solution at " + f);
    }
}
