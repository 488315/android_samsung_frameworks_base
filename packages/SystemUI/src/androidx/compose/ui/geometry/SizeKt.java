package androidx.compose.ui.geometry;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Size;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SizeKt {
    public static final long Size(float f, float f2) {
        long floatToRawIntBits = (Float.floatToRawIntBits(f2) & 4294967295L) | (Float.floatToRawIntBits(f) << 32);
        Size.Companion companion = Size.Companion;
        return floatToRawIntBits;
    }

    /* renamed from: getCenter-uvyYCjk, reason: not valid java name */
    public static final long m420getCenteruvyYCjk(long j) {
        float intBitsToFloat = Float.intBitsToFloat((int) (j >> 32)) / 2.0f;
        float intBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L)) / 2.0f;
        long floatToRawIntBits = (Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(intBitsToFloat) << 32);
        Offset.Companion companion = Offset.Companion;
        return floatToRawIntBits;
    }

    /* renamed from: toRect-uvyYCjk, reason: not valid java name */
    public static final Rect m421toRectuvyYCjk(long j) {
        Offset.Companion.getClass();
        return RectKt.m411Recttz77jQw(0L, j);
    }
}
