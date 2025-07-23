package androidx.compose.ui.unit;

import androidx.compose.ui.unit.DpSize;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class DpKt {
    /* renamed from: DpSize-YgX7TsA, reason: not valid java name */
    public static final long m838DpSizeYgX7TsA(float f, float f2) {
        long floatToRawIntBits = (Float.floatToRawIntBits(f2) & 4294967295L) | (Float.floatToRawIntBits(f) << 32);
        DpSize.Companion companion = DpSize.Companion;
        return floatToRawIntBits;
    }
}
