package androidx.compose.foundation.layout;

import androidx.compose.ui.layout.IntrinsicMeasurable;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.node.LookaheadCapablePlaceable;
import androidx.compose.ui.unit.Constraints;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class IntrinsicWidthNode extends IntrinsicSizeModifier {
    public boolean enforceIncoming;
    public IntrinsicSize width;

    public IntrinsicWidthNode(IntrinsicSize intrinsicSize, boolean z) {
        this.width = intrinsicSize;
        this.enforceIncoming = z;
    }

    @Override // androidx.compose.foundation.layout.IntrinsicSizeModifier
    /* renamed from: calculateContentConstraints-l58MMJ0 */
    public final long mo113calculateContentConstraintsl58MMJ0(Measurable measurable, long j) {
        int minIntrinsicWidth = this.width == IntrinsicSize.Min ? measurable.minIntrinsicWidth(Constraints.m820getMaxHeightimpl(j)) : measurable.maxIntrinsicWidth(Constraints.m820getMaxHeightimpl(j));
        if (minIntrinsicWidth < 0) {
            minIntrinsicWidth = 0;
        }
        Constraints.Companion.getClass();
        return Constraints.Companion.m828fixedWidthOenEA2s(minIntrinsicWidth);
    }

    @Override // androidx.compose.foundation.layout.IntrinsicSizeModifier
    public final boolean getEnforceIncoming() {
        return this.enforceIncoming;
    }

    @Override // androidx.compose.foundation.layout.IntrinsicSizeModifier, androidx.compose.ui.node.LayoutModifierNode
    public final int maxIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        return this.width == IntrinsicSize.Min ? intrinsicMeasurable.minIntrinsicWidth(i) : intrinsicMeasurable.maxIntrinsicWidth(i);
    }

    @Override // androidx.compose.foundation.layout.IntrinsicSizeModifier, androidx.compose.ui.node.LayoutModifierNode
    public final int minIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        return this.width == IntrinsicSize.Min ? intrinsicMeasurable.minIntrinsicWidth(i) : intrinsicMeasurable.maxIntrinsicWidth(i);
    }
}
