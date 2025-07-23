package androidx.compose.animation.core;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SpringSimulation {
    public float finalPosition;
    public double naturalFreq = Math.sqrt(50.0d);
    public float dampingRatio = 1.0f;

    public SpringSimulation(float f) {
        this.finalPosition = f;
    }

    /* renamed from: updateValues-IJZedt4$animation_core, reason: not valid java name */
    public final long m11updateValuesIJZedt4$animation_core(float f, float f2, long j) {
        double sin;
        double cos;
        double exp;
        double exp2;
        float f3 = f - this.finalPosition;
        double d = j / 1000.0d;
        float f4 = this.dampingRatio;
        double d2 = f4 * f4;
        double d3 = this.naturalFreq;
        double d4 = (-f4) * d3;
        if (f4 > 1.0f) {
            double sqrt = Math.sqrt(d2 - 1) * d3;
            double d5 = d4 + sqrt;
            double d6 = d4 - sqrt;
            double d7 = f3;
            double d8 = ((d6 * d7) - f2) / (d6 - d5);
            double d9 = d7 - d8;
            double d10 = d6 * d;
            double d11 = d * d5;
            sin = (Math.exp(d11) * d8) + (Math.exp(d10) * d9);
            exp = Math.exp(d10) * d9 * d6;
            exp2 = Math.exp(d11) * d8 * d5;
        } else {
            if (f4 != 1.0f) {
                double d12 = 1;
                double sqrt2 = Math.sqrt(d12 - d2) * d3;
                double d13 = f3;
                double d14 = (((-d4) * d13) + f2) * (d12 / sqrt2);
                double d15 = sqrt2 * d;
                double d16 = d * d4;
                sin = ((Math.sin(d15) * d14) + (Math.cos(d15) * d13)) * Math.exp(d16);
                cos = (((Math.cos(d15) * sqrt2 * d14) + (Math.sin(d15) * (-sqrt2) * d13)) * Math.exp(d16)) + (d4 * sin);
                return (Float.floatToRawIntBits((float) cos) & 4294967295L) | (Float.floatToRawIntBits((float) (sin + this.finalPosition)) << 32);
            }
            double d17 = f3;
            double d18 = (d3 * d17) + f2;
            double d19 = (-d3) * d;
            double d20 = (d * d18) + d17;
            sin = Math.exp(d19) * d20;
            exp = Math.exp(d19) * d20 * (-this.naturalFreq);
            exp2 = Math.exp(d19) * d18;
        }
        cos = exp2 + exp;
        return (Float.floatToRawIntBits((float) cos) & 4294967295L) | (Float.floatToRawIntBits((float) (sin + this.finalPosition)) << 32);
    }
}
