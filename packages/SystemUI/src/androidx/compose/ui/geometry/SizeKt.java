package androidx.compose.ui.geometry;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Size;

/* loaded from: classes.dex */
public abstract class SizeKt {
    public static final long Size(float f, float f2) {
        long jFloatToRawIntBits = (Float.floatToRawIntBits(f2) & 4294967295L) | (Float.floatToRawIntBits(f) << 32);
        Size.Companion companion = Size.Companion;
        return jFloatToRawIntBits;
    }

    /* renamed from: getCenter-uvyYCjk, reason: not valid java name */
    public static final long m422getCenteruvyYCjk(long j) {
        float fIntBitsToFloat = Float.intBitsToFloat((int) (j >> 32)) / 2.0f;
        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L)) / 2.0f;
        long jFloatToRawIntBits = (Float.floatToRawIntBits(fIntBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(fIntBitsToFloat) << 32);
        Offset.Companion companion = Offset.Companion;
        return jFloatToRawIntBits;
    }

    /* renamed from: toRect-uvyYCjk, reason: not valid java name */
    public static final Rect m423toRectuvyYCjk(long j) {
        Offset.Companion.getClass();
        return RectKt.m413Recttz77jQw(0L, j);
    }
}
