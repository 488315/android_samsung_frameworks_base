package androidx.compose.ui.text;

import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.internal.InlineClassHelperKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class TextRangeKt {
    public static final long TextRange(int i, int i2) {
        if (i < 0 || i2 < 0) {
            InlineClassHelperKt.throwIllegalArgumentException("start and end cannot be negative. [start: " + i + ", end: " + i2 + ']');
        }
        long j = (i2 & 4294967295L) | (i << 32);
        TextRange.Companion companion = TextRange.Companion;
        return j;
    }

    /* renamed from: coerceIn-8ffj60Q, reason: not valid java name */
    public static final long m753coerceIn8ffj60Q(int i, long j) {
        TextRange.Companion companion = TextRange.Companion;
        int i2 = (int) (j >> 32);
        int i3 = i2 < 0 ? 0 : i2;
        if (i3 > i) {
            i3 = i;
        }
        int i4 = (int) (4294967295L & j);
        int i5 = i4 >= 0 ? i4 : 0;
        if (i5 <= i) {
            i = i5;
        }
        return (i3 == i2 && i == i4) ? j : TextRange(i3, i);
    }
}
