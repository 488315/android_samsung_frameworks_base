package androidx.compose.ui.unit;

import androidx.compose.ui.unit.DpSize;

/* loaded from: classes.dex */
public abstract class DpKt {
    /* renamed from: DpSize-YgX7TsA, reason: not valid java name */
    public static final long m840DpSizeYgX7TsA(float f, float f2) {
        long jFloatToRawIntBits = (Float.floatToRawIntBits(f2) & 4294967295L) | (Float.floatToRawIntBits(f) << 32);
        DpSize.Companion companion = DpSize.Companion;
        return jFloatToRawIntBits;
    }
}
