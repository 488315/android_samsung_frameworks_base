package androidx.compose.foundation.text.modifiers;

import androidx.compose.foundation.text.TextDelegateKt;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Constraints;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LayoutUtilsKt {
    /* renamed from: finalConstraints-tfFHcEY, reason: not valid java name */
    public static final long m221finalConstraintstfFHcEY(long j, boolean z, int i, float f) {
        Constraints.Companion companion = Constraints.Companion;
        int m821getMaxWidthimpl = ((z || m222isEllipsisMW5ApA(i)) && Constraints.m817getHasBoundedWidthimpl(j)) ? Constraints.m821getMaxWidthimpl(j) : Integer.MAX_VALUE;
        if (Constraints.m823getMinWidthimpl(j) != m821getMaxWidthimpl) {
            m821getMaxWidthimpl = RangesKt___RangesKt.coerceIn(TextDelegateKt.ceilToIntPx(f), Constraints.m823getMinWidthimpl(j), m821getMaxWidthimpl);
        }
        int m820getMaxHeightimpl = Constraints.m820getMaxHeightimpl(j);
        companion.getClass();
        return Constraints.Companion.m826fitPrioritizingWidthZbe2FdA(0, m821getMaxWidthimpl, 0, m820getMaxHeightimpl);
    }

    /* renamed from: isEllipsis-MW5-ApA, reason: not valid java name */
    public static final boolean m222isEllipsisMW5ApA(int i) {
        TextOverflow.Companion companion = TextOverflow.Companion;
        companion.getClass();
        if (i == TextOverflow.Ellipsis) {
            return true;
        }
        companion.getClass();
        if (i == TextOverflow.StartEllipsis) {
            return true;
        }
        companion.getClass();
        return i == TextOverflow.MiddleEllipsis;
    }
}
