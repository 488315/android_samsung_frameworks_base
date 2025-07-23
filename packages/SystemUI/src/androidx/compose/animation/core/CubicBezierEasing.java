package androidx.compose.animation.core;

import androidx.compose.animation.AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.BezierKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CubicBezierEasing implements Easing {
    public final float a;
    public final float b;
    public final float c;
    public final float d;
    public final float max;
    public final float min;

    public CubicBezierEasing(float f, float f2, float f3, float f4) {
        int i;
        this.a = f;
        this.b = f2;
        this.c = f3;
        this.d = f4;
        if (!((Float.isNaN(f) || Float.isNaN(f2) || Float.isNaN(f3) || Float.isNaN(f4)) ? false : true)) {
            StringBuilder m = CubicBezierEasing$$ExternalSyntheticOutline0.m("Parameters to CubicBezierEasing cannot be NaN. Actual parameters are: ", f, ", ", f2, ", ");
            m.append(f3);
            m.append(", ");
            m.append(f4);
            m.append('.');
            PreconditionsKt.throwIllegalArgumentException(m.toString());
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
            i = d2 == d3 ? 0 : BezierKt.writeValidRootInUnitRange((float) ((d4 - d3) / (d4 - (d3 * 2.0d))), fArr, 0);
        } else {
            double d6 = -Math.sqrt((d2 * d2) - (d3 * d));
            double d7 = (-d) + d2;
            int writeValidRootInUnitRange = BezierKt.writeValidRootInUnitRange((float) ((-(d6 + d7)) / d5), fArr, 0);
            int writeValidRootInUnitRange2 = BezierKt.writeValidRootInUnitRange((float) ((d6 - d7) / d5), fArr, writeValidRootInUnitRange) + writeValidRootInUnitRange;
            if (writeValidRootInUnitRange2 > 1) {
                float f8 = fArr[0];
                float f9 = fArr[1];
                if (f8 > f9) {
                    fArr[0] = f9;
                    fArr[1] = f8;
                } else if (f8 == f9) {
                    i = writeValidRootInUnitRange2 - 1;
                }
            }
            i = writeValidRootInUnitRange2;
        }
        float f10 = (f6 - f5) * 2.0f;
        int writeValidRootInUnitRange3 = BezierKt.writeValidRootInUnitRange((-f10) / (((f7 - f6) * 2.0f) - f10), fArr, i) + i;
        float min = Math.min(0.0f, 1.0f);
        float max = Math.max(0.0f, 1.0f);
        for (int i2 = 0; i2 < writeValidRootInUnitRange3; i2++) {
            float f11 = fArr[i2];
            float f12 = (((((((((f2 - f4) * 3.0f) + 1.0f) - 0.0f) * f11) + (((f4 - (f2 * 2.0f)) + 0.0f) * 3.0f)) * f11) + f5) * f11) + 0.0f;
            min = Math.min(min, f12);
            max = Math.max(max, f12);
        }
        long floatToRawIntBits = (Float.floatToRawIntBits(min) << 32) | (Float.floatToRawIntBits(max) & 4294967295L);
        this.min = Float.intBitsToFloat((int) (floatToRawIntBits >> 32));
        this.max = Float.intBitsToFloat((int) (floatToRawIntBits & 4294967295L));
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

    /* JADX WARN: Code restructure failed: missing block: B:117:0x0206, code lost:
    
        if (java.lang.Math.abs(r3 - r2) > 1.05E-6f) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x0236, code lost:
    
        if (java.lang.Math.abs(r3 - r2) > 1.05E-6f) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x008e, code lost:
    
        if (java.lang.Math.abs(r3 - r2) > 1.05E-6f) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0092, code lost:
    
        r15 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00e5, code lost:
    
        if (java.lang.Math.abs(r3 - r2) > 1.05E-6f) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x01bb, code lost:
    
        if (java.lang.Math.abs(r3 - r2) > 1.05E-6f) goto L129;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0242  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0261  */
    @Override // androidx.compose.animation.core.Easing
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final float transform(float r27) {
        /*
            Method dump skipped, instructions count: 658
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.core.CubicBezierEasing.transform(float):float");
    }
}
