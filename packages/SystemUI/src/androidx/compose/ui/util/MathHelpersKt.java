package androidx.compose.ui.util;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class MathHelpersKt {
    public static final float fastCbrt(float f) {
        float intBitsToFloat = Float.intBitsToFloat(((int) ((Float.floatToRawIntBits(f) & 8589934591L) / 3)) + 709952852);
        float f2 = intBitsToFloat - ((intBitsToFloat - (f / (intBitsToFloat * intBitsToFloat))) * 0.33333334f);
        return f2 - ((f2 - (f / (f2 * f2))) * 0.33333334f);
    }

    public static final float lerp(float f, float f2, float f3) {
        return (f3 * f2) + ((1 - f3) * f);
    }

    public static final int lerp(float f, int i, int i2) {
        return i + ((int) Math.round((i2 - i) * f));
    }
}
