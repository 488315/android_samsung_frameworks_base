package androidx.compose.ui.unit;

import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.unit.IntOffset;

/* loaded from: classes.dex */
public abstract class IntRectKt {
    /* renamed from: IntRect-VbeCjmY, reason: not valid java name */
    public static final IntRect m860IntRectVbeCjmY(long j, long j2) {
        IntOffset.Companion companion = IntOffset.Companion;
        int i = (int) (j >> 32);
        int i2 = (int) (j & 4294967295L);
        return new IntRect(i, i2, ((int) (j2 >> 32)) + i, ((int) (j2 & 4294967295L)) + i2);
    }

    public static final IntRect roundToIntRect(Rect rect) {
        return new IntRect(Math.round(rect.left), Math.round(rect.top), Math.round(rect.right), Math.round(rect.bottom));
    }

    public static final Rect toRect(IntRect intRect) {
        return new Rect(intRect.left, intRect.top, intRect.right, intRect.bottom);
    }
}
