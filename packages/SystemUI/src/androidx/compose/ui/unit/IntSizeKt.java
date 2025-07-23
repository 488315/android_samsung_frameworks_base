package androidx.compose.ui.unit;

import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.unit.IntSize;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class IntSizeKt {
    public static final long IntSize(int i, int i2) {
        long j = (i2 & 4294967295L) | (i << 32);
        IntSize.Companion companion = IntSize.Companion;
        return j;
    }

    /* renamed from: toIntSize-uvyYCjk, reason: not valid java name */
    public static final long m863toIntSizeuvyYCjk(long j) {
        long intBitsToFloat = (((int) Float.intBitsToFloat((int) (j & 4294967295L))) & 4294967295L) | (((int) Float.intBitsToFloat((int) (j >> 32))) << 32);
        IntSize.Companion companion = IntSize.Companion;
        return intBitsToFloat;
    }

    /* renamed from: toSize-ozmzZPI, reason: not valid java name */
    public static final long m864toSizeozmzZPI(long j) {
        long floatToRawIntBits = (Float.floatToRawIntBits((int) (j & 4294967295L)) & 4294967295L) | (Float.floatToRawIntBits((int) (j >> 32)) << 32);
        Size.Companion companion = Size.Companion;
        return floatToRawIntBits;
    }
}
