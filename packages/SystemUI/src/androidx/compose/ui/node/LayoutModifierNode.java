package androidx.compose.ui.node;

import androidx.compose.ui.layout.IntrinsicMeasurable;
import androidx.compose.ui.layout.IntrinsicsMeasureScope;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.node.NodeMeasuringIntrinsics;
import androidx.compose.ui.unit.ConstraintsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface LayoutModifierNode extends DelegatableNode {
    default int maxIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        NodeMeasuringIntrinsics nodeMeasuringIntrinsics = NodeMeasuringIntrinsics.INSTANCE;
        LayoutModifierNode$maxIntrinsicHeight$1 layoutModifierNode$maxIntrinsicHeight$1 = new LayoutModifierNode$maxIntrinsicHeight$1(this);
        nodeMeasuringIntrinsics.getClass();
        NodeMeasuringIntrinsics.DefaultIntrinsicMeasurable defaultIntrinsicMeasurable = new NodeMeasuringIntrinsics.DefaultIntrinsicMeasurable(intrinsicMeasurable, NodeMeasuringIntrinsics.IntrinsicMinMax.Max, NodeMeasuringIntrinsics.IntrinsicWidthHeight.Height);
        long Constraints$default = ConstraintsKt.Constraints$default(0, i, 0, 0, 13);
        return layoutModifierNode$maxIntrinsicHeight$1.this$0.mo4measure3p2s80s(new IntrinsicsMeasureScope(lookaheadCapablePlaceable, lookaheadCapablePlaceable.getLayoutDirection()), defaultIntrinsicMeasurable, Constraints$default).getHeight();
    }

    default int maxIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        NodeMeasuringIntrinsics nodeMeasuringIntrinsics = NodeMeasuringIntrinsics.INSTANCE;
        LayoutModifierNode$maxIntrinsicWidth$1 layoutModifierNode$maxIntrinsicWidth$1 = new LayoutModifierNode$maxIntrinsicWidth$1(this);
        nodeMeasuringIntrinsics.getClass();
        NodeMeasuringIntrinsics.DefaultIntrinsicMeasurable defaultIntrinsicMeasurable = new NodeMeasuringIntrinsics.DefaultIntrinsicMeasurable(intrinsicMeasurable, NodeMeasuringIntrinsics.IntrinsicMinMax.Max, NodeMeasuringIntrinsics.IntrinsicWidthHeight.Width);
        long Constraints$default = ConstraintsKt.Constraints$default(0, 0, 0, i, 7);
        return layoutModifierNode$maxIntrinsicWidth$1.this$0.mo4measure3p2s80s(new IntrinsicsMeasureScope(lookaheadCapablePlaceable, lookaheadCapablePlaceable.getLayoutDirection()), defaultIntrinsicMeasurable, Constraints$default).getWidth();
    }

    /* renamed from: measure-3p2s80s */
    MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j);

    default int minIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        NodeMeasuringIntrinsics nodeMeasuringIntrinsics = NodeMeasuringIntrinsics.INSTANCE;
        LayoutModifierNode$minIntrinsicHeight$1 layoutModifierNode$minIntrinsicHeight$1 = new LayoutModifierNode$minIntrinsicHeight$1(this);
        nodeMeasuringIntrinsics.getClass();
        NodeMeasuringIntrinsics.DefaultIntrinsicMeasurable defaultIntrinsicMeasurable = new NodeMeasuringIntrinsics.DefaultIntrinsicMeasurable(intrinsicMeasurable, NodeMeasuringIntrinsics.IntrinsicMinMax.Min, NodeMeasuringIntrinsics.IntrinsicWidthHeight.Height);
        long Constraints$default = ConstraintsKt.Constraints$default(0, i, 0, 0, 13);
        return layoutModifierNode$minIntrinsicHeight$1.this$0.mo4measure3p2s80s(new IntrinsicsMeasureScope(lookaheadCapablePlaceable, lookaheadCapablePlaceable.getLayoutDirection()), defaultIntrinsicMeasurable, Constraints$default).getHeight();
    }

    default int minIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        NodeMeasuringIntrinsics nodeMeasuringIntrinsics = NodeMeasuringIntrinsics.INSTANCE;
        LayoutModifierNode$minIntrinsicWidth$1 layoutModifierNode$minIntrinsicWidth$1 = new LayoutModifierNode$minIntrinsicWidth$1(this);
        nodeMeasuringIntrinsics.getClass();
        NodeMeasuringIntrinsics.DefaultIntrinsicMeasurable defaultIntrinsicMeasurable = new NodeMeasuringIntrinsics.DefaultIntrinsicMeasurable(intrinsicMeasurable, NodeMeasuringIntrinsics.IntrinsicMinMax.Min, NodeMeasuringIntrinsics.IntrinsicWidthHeight.Width);
        long Constraints$default = ConstraintsKt.Constraints$default(0, 0, 0, i, 7);
        return layoutModifierNode$minIntrinsicWidth$1.this$0.mo4measure3p2s80s(new IntrinsicsMeasureScope(lookaheadCapablePlaceable, lookaheadCapablePlaceable.getLayoutDirection()), defaultIntrinsicMeasurable, Constraints$default).getWidth();
    }
}
