package androidx.compose.ui.geometry;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.util.MathHelpersKt;

/* loaded from: classes.dex */
public abstract class OffsetKt {
    public static final long Offset(float f, float f2) {
        long jFloatToRawIntBits = (Float.floatToRawIntBits(f2) & 4294967295L) | (Float.floatToRawIntBits(f) << 32);
        Offset.Companion companion = Offset.Companion;
        return jFloatToRawIntBits;
    }

    /* renamed from: lerp-Wko1d7g, reason: not valid java name */
    public static final long m406lerpWko1d7g(long j, long j2, float f) {
        float fLerp = MathHelpersKt.lerp(Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j2 >> 32)), f);
        float fLerp2 = MathHelpersKt.lerp(Float.intBitsToFloat((int) (j & 4294967295L)), Float.intBitsToFloat((int) (j2 & 4294967295L)), f);
        long jFloatToRawIntBits = (Float.floatToRawIntBits(fLerp) << 32) | (Float.floatToRawIntBits(fLerp2) & 4294967295L);
        Offset.Companion companion = Offset.Companion;
        return jFloatToRawIntBits;
    }
}
