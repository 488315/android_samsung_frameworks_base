package androidx.compose.ui.geometry;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.util.MathHelpersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class OffsetKt {
    public static final long Offset(float f, float f2) {
        long floatToRawIntBits = (Float.floatToRawIntBits(f2) & 4294967295L) | (Float.floatToRawIntBits(f) << 32);
        Offset.Companion companion = Offset.Companion;
        return floatToRawIntBits;
    }

    /* renamed from: lerp-Wko1d7g, reason: not valid java name */
    public static final long m404lerpWko1d7g(long j, long j2, float f) {
        float lerp = MathHelpersKt.lerp(Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j2 >> 32)), f);
        float lerp2 = MathHelpersKt.lerp(Float.intBitsToFloat((int) (j & 4294967295L)), Float.intBitsToFloat((int) (j2 & 4294967295L)), f);
        long floatToRawIntBits = (Float.floatToRawIntBits(lerp) << 32) | (Float.floatToRawIntBits(lerp2) & 4294967295L);
        Offset.Companion companion = Offset.Companion;
        return floatToRawIntBits;
    }
}
