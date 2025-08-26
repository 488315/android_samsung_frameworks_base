package androidx.compose.ui.node;

import androidx.compose.ui.layout.IntrinsicMeasurable;
import androidx.compose.ui.layout.IntrinsicsMeasureScope;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.node.NodeMeasuringIntrinsics;
import androidx.compose.ui.unit.ConstraintsKt;

/* loaded from: classes.dex */
public interface LayoutModifierNode extends DelegatableNode {

    /* renamed from: androidx.compose.ui.node.LayoutModifierNode$maxIntrinsicHeight$1, reason: invalid class name */
    final class AnonymousClass1 {
        public AnonymousClass1() {
        }
    }

    /* renamed from: androidx.compose.ui.node.LayoutModifierNode$maxIntrinsicWidth$1, reason: invalid class name and case insensitive filesystem */
    final class C07481 {
        public C07481() {
        }
    }

    /* renamed from: androidx.compose.ui.node.LayoutModifierNode$minIntrinsicHeight$1, reason: invalid class name and case insensitive filesystem */
    final class C07491 {
        public C07491() {
        }
    }

    /* renamed from: androidx.compose.ui.node.LayoutModifierNode$minIntrinsicWidth$1, reason: invalid class name and case insensitive filesystem */
    final class C07501 {
        public C07501() {
        }
    }

    default int maxIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        NodeMeasuringIntrinsics nodeMeasuringIntrinsics = NodeMeasuringIntrinsics.INSTANCE;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        nodeMeasuringIntrinsics.getClass();
        NodeMeasuringIntrinsics.DefaultIntrinsicMeasurable defaultIntrinsicMeasurable = new NodeMeasuringIntrinsics.DefaultIntrinsicMeasurable(intrinsicMeasurable, NodeMeasuringIntrinsics.IntrinsicMinMax.Max, NodeMeasuringIntrinsics.IntrinsicWidthHeight.Height);
        long jConstraints$default = ConstraintsKt.Constraints$default(0, i, 0, 0, 13);
        return LayoutModifierNode.this.mo4measure3p2s80s(new IntrinsicsMeasureScope(lookaheadCapablePlaceable, lookaheadCapablePlaceable.getLayoutDirection()), defaultIntrinsicMeasurable, jConstraints$default).getHeight();
    }

    default int maxIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        NodeMeasuringIntrinsics nodeMeasuringIntrinsics = NodeMeasuringIntrinsics.INSTANCE;
        C07481 c07481 = new C07481();
        nodeMeasuringIntrinsics.getClass();
        NodeMeasuringIntrinsics.DefaultIntrinsicMeasurable defaultIntrinsicMeasurable = new NodeMeasuringIntrinsics.DefaultIntrinsicMeasurable(intrinsicMeasurable, NodeMeasuringIntrinsics.IntrinsicMinMax.Max, NodeMeasuringIntrinsics.IntrinsicWidthHeight.Width);
        long jConstraints$default = ConstraintsKt.Constraints$default(0, 0, 0, i, 7);
        return LayoutModifierNode.this.mo4measure3p2s80s(new IntrinsicsMeasureScope(lookaheadCapablePlaceable, lookaheadCapablePlaceable.getLayoutDirection()), defaultIntrinsicMeasurable, jConstraints$default).getWidth();
    }

    /* renamed from: measure-3p2s80s */
    MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j);

    default int minIntrinsicHeight(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        NodeMeasuringIntrinsics nodeMeasuringIntrinsics = NodeMeasuringIntrinsics.INSTANCE;
        C07491 c07491 = new C07491();
        nodeMeasuringIntrinsics.getClass();
        NodeMeasuringIntrinsics.DefaultIntrinsicMeasurable defaultIntrinsicMeasurable = new NodeMeasuringIntrinsics.DefaultIntrinsicMeasurable(intrinsicMeasurable, NodeMeasuringIntrinsics.IntrinsicMinMax.Min, NodeMeasuringIntrinsics.IntrinsicWidthHeight.Height);
        long jConstraints$default = ConstraintsKt.Constraints$default(0, i, 0, 0, 13);
        return LayoutModifierNode.this.mo4measure3p2s80s(new IntrinsicsMeasureScope(lookaheadCapablePlaceable, lookaheadCapablePlaceable.getLayoutDirection()), defaultIntrinsicMeasurable, jConstraints$default).getHeight();
    }

    default int minIntrinsicWidth(LookaheadCapablePlaceable lookaheadCapablePlaceable, IntrinsicMeasurable intrinsicMeasurable, int i) {
        NodeMeasuringIntrinsics nodeMeasuringIntrinsics = NodeMeasuringIntrinsics.INSTANCE;
        C07501 c07501 = new C07501();
        nodeMeasuringIntrinsics.getClass();
        NodeMeasuringIntrinsics.DefaultIntrinsicMeasurable defaultIntrinsicMeasurable = new NodeMeasuringIntrinsics.DefaultIntrinsicMeasurable(intrinsicMeasurable, NodeMeasuringIntrinsics.IntrinsicMinMax.Min, NodeMeasuringIntrinsics.IntrinsicWidthHeight.Width);
        long jConstraints$default = ConstraintsKt.Constraints$default(0, 0, 0, i, 7);
        return LayoutModifierNode.this.mo4measure3p2s80s(new IntrinsicsMeasureScope(lookaheadCapablePlaceable, lookaheadCapablePlaceable.getLayoutDirection()), defaultIntrinsicMeasurable, jConstraints$default).getWidth();
    }
}
