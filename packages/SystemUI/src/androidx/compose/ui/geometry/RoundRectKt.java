package androidx.compose.ui.geometry;

import androidx.compose.ui.geometry.CornerRadius;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class RoundRectKt {
    /* renamed from: RoundRect-gG7oq9Y, reason: not valid java name */
    public static final RoundRect m412RoundRectgG7oq9Y(float f, float f2, float f3, float f4, long j) {
        float intBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
        float intBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L));
        long floatToRawIntBits = (Float.floatToRawIntBits(intBitsToFloat) << 32) | (4294967295L & Float.floatToRawIntBits(intBitsToFloat2));
        CornerRadius.Companion companion = CornerRadius.Companion;
        return new RoundRect(f, f2, f3, f4, floatToRawIntBits, floatToRawIntBits, floatToRawIntBits, floatToRawIntBits, null);
    }

    public static final boolean isSimple(RoundRect roundRect) {
        long j = roundRect.topLeftCornerRadius;
        return (j >>> 32) == (4294967295L & j) && j == roundRect.topRightCornerRadius && j == roundRect.bottomRightCornerRadius && j == roundRect.bottomLeftCornerRadius;
    }
}
