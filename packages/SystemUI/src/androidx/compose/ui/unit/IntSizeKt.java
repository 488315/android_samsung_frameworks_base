package androidx.compose.ui.unit;

import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.unit.IntSize;

/* loaded from: classes.dex */
public abstract class IntSizeKt {
    public static final long IntSize(int i, int i2) {
        long j = (i2 & 4294967295L) | (i << 32);
        IntSize.Companion companion = IntSize.Companion;
        return j;
    }

    /* renamed from: toIntSize-uvyYCjk, reason: not valid java name */
    public static final long m865toIntSizeuvyYCjk(long j) {
        long jIntBitsToFloat = (((int) Float.intBitsToFloat((int) (j & 4294967295L))) & 4294967295L) | (((int) Float.intBitsToFloat((int) (j >> 32))) << 32);
        IntSize.Companion companion = IntSize.Companion;
        return jIntBitsToFloat;
    }

    /* renamed from: toSize-ozmzZPI, reason: not valid java name */
    public static final long m866toSizeozmzZPI(long j) {
        long jFloatToRawIntBits = (Float.floatToRawIntBits((int) (j & 4294967295L)) & 4294967295L) | (Float.floatToRawIntBits((int) (j >> 32)) << 32);
        Size.Companion companion = Size.Companion;
        return jFloatToRawIntBits;
    }
}
