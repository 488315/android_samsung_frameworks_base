package androidx.compose.foundation.text.modifiers;

import androidx.compose.foundation.text.TextDelegateKt;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Constraints;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes.dex */
public abstract class LayoutUtilsKt {
    /* renamed from: finalConstraints-tfFHcEY, reason: not valid java name */
    public static final long m222finalConstraintstfFHcEY(long j, boolean z, int i, float f) {
        Constraints.Companion companion = Constraints.Companion;
        int iM823getMaxWidthimpl = ((z || m223isEllipsisMW5ApA(i)) && Constraints.m819getHasBoundedWidthimpl(j)) ? Constraints.m823getMaxWidthimpl(j) : Integer.MAX_VALUE;
        if (Constraints.m825getMinWidthimpl(j) != iM823getMaxWidthimpl) {
            iM823getMaxWidthimpl = RangesKt___RangesKt.coerceIn(TextDelegateKt.ceilToIntPx(f), Constraints.m825getMinWidthimpl(j), iM823getMaxWidthimpl);
        }
        int iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(j);
        companion.getClass();
        return Constraints.Companion.m828fitPrioritizingWidthZbe2FdA(0, iM823getMaxWidthimpl, 0, iM822getMaxHeightimpl);
    }

    /* renamed from: isEllipsis-MW5-ApA, reason: not valid java name */
    public static final boolean m223isEllipsisMW5ApA(int i) {
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
