package androidx.compose.foundation.text.selection;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.unit.Dp;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SelectionHandlesKt {
    public static final float HandleHeight;
    public static final float HandleWidth;
    public static final SemanticsPropertyKey SelectionHandleInfoKey;

    static {
        float f = 25;
        Dp.Companion companion = Dp.Companion;
        HandleWidth = f;
        HandleHeight = f;
        SelectionHandleInfoKey = new SemanticsPropertyKey("SelectionHandleInfo", null, 2, null);
    }

    /* renamed from: getAdjustedCoordinates-k-4lQ0M, reason: not valid java name */
    public static final long m234getAdjustedCoordinatesk4lQ0M(long j) {
        float intBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
        float intBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L)) - 1.0f;
        long floatToRawIntBits = (Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(intBitsToFloat) << 32);
        Offset.Companion companion = Offset.Companion;
        return floatToRawIntBits;
    }
}
