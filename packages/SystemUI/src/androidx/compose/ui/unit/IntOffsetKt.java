package androidx.compose.ui.unit;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.unit.IntOffset;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class IntOffsetKt {
    /* renamed from: plus-Nv-tHpc, reason: not valid java name */
    public static final long m853plusNvtHpc(long j, long j2) {
        float intBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
        IntOffset.Companion companion = IntOffset.Companion;
        float intBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L)) + ((int) (j2 & 4294967295L));
        long floatToRawIntBits = (Float.floatToRawIntBits(intBitsToFloat + ((int) (j2 >> 32))) << 32) | (Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L);
        Offset.Companion companion2 = Offset.Companion;
        return floatToRawIntBits;
    }

    /* renamed from: round-k-4lQ0M, reason: not valid java name */
    public static final long m854roundk4lQ0M(long j) {
        long round = (Math.round(Float.intBitsToFloat((int) (j & 4294967295L))) & 4294967295L) | (Math.round(Float.intBitsToFloat((int) (j >> 32))) << 32);
        IntOffset.Companion companion = IntOffset.Companion;
        return round;
    }
}
