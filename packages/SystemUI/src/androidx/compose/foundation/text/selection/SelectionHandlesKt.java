package androidx.compose.foundation.text.selection;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.unit.Dp;

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
    public static final long m235getAdjustedCoordinatesk4lQ0M(long j) {
        float fIntBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L)) - 1.0f;
        long jFloatToRawIntBits = (Float.floatToRawIntBits(fIntBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(fIntBitsToFloat) << 32);
        Offset.Companion companion = Offset.Companion;
        return jFloatToRawIntBits;
    }
}
