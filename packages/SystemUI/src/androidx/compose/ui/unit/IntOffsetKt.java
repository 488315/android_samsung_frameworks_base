package androidx.compose.ui.unit;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.unit.IntOffset;

/* loaded from: classes.dex */
public abstract class IntOffsetKt {
    /* renamed from: plus-Nv-tHpc, reason: not valid java name */
    public static final long m855plusNvtHpc(long j, long j2) {
        float fIntBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
        IntOffset.Companion companion = IntOffset.Companion;
        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L)) + ((int) (j2 & 4294967295L));
        long jFloatToRawIntBits = (Float.floatToRawIntBits(fIntBitsToFloat + ((int) (j2 >> 32))) << 32) | (Float.floatToRawIntBits(fIntBitsToFloat2) & 4294967295L);
        Offset.Companion companion2 = Offset.Companion;
        return jFloatToRawIntBits;
    }

    /* renamed from: round-k-4lQ0M, reason: not valid java name */
    public static final long m856roundk4lQ0M(long j) {
        long jRound = (Math.round(Float.intBitsToFloat((int) (j & 4294967295L))) & 4294967295L) | (Math.round(Float.intBitsToFloat((int) (j >> 32))) << 32);
        IntOffset.Companion companion = IntOffset.Companion;
        return jRound;
    }
}
