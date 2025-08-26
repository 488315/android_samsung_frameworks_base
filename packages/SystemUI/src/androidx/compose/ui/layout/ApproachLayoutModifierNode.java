package androidx.compose.ui.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LayoutModifierNode;
import androidx.compose.ui.node.LookaheadDelegate;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.node.NodeMeasuringIntrinsics;
import androidx.compose.ui.unit.ConstraintsKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public interface ApproachLayoutModifierNode extends LayoutModifierNode {
    /* renamed from: approachMeasure-3p2s80s, reason: not valid java name */
    MeasureResult mo605approachMeasure3p2s80s(ApproachMeasureScope approachMeasureScope, Measurable measurable, long j);

    /* renamed from: isMeasurementApproachInProgress-ozmzZPI, reason: not valid java name */
    boolean mo606isMeasurementApproachInProgressozmzZPI(long j);

    default boolean isPlacementApproachInProgress(Placeable.PlacementScope placementScope, LayoutCoordinates layoutCoordinates) {
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    default int maxApproachIntrinsicHeight(ApproachIntrinsicMeasureScope approachIntrinsicMeasureScope, IntrinsicMeasurable intrinsicMeasurable, int i) {
        NodeCoordinator nodeCoordinator = ((Modifier.Node) this).node.coordinator;
        nodeCoordinator.getClass();
        LookaheadDelegate lookaheadDelegate = nodeCoordinator.getLookaheadDelegate();
        lookaheadDelegate.getClass();
        if (!lookaheadDelegate.getHasMeasureResult()) {
            return intrinsicMeasurable.maxIntrinsicHeight(i);
        }
        NodeMeasuringIntrinsics nodeMeasuringIntrinsics = NodeMeasuringIntrinsics.INSTANCE;
        NodeMeasuringIntrinsics.ApproachMeasureBlock approachMeasureBlock = new NodeMeasuringIntrinsics.ApproachMeasureBlock() { // from class: androidx.compose.ui.layout.ApproachLayoutModifierNode.maxApproachIntrinsicHeight.1
            @Override // androidx.compose.ui.node.NodeMeasuringIntrinsics.ApproachMeasureBlock
            /* renamed from: measure-3p2s80s, reason: not valid java name */
            public final MeasureResult mo607measure3p2s80s(ApproachIntrinsicsMeasureScope approachIntrinsicsMeasureScope, Measurable measurable, long j) {
                return ApproachLayoutModifierNode.this.mo605approachMeasure3p2s80s(approachIntrinsicsMeasureScope, measurable, j);
            }
        };
        nodeMeasuringIntrinsics.getClass();
        return approachMeasureBlock.mo607measure3p2s80s(new ApproachIntrinsicsMeasureScope(approachIntrinsicMeasureScope, approachIntrinsicMeasureScope.getLayoutDirection()), new NodeMeasuringIntrinsics.DefaultIntrinsicMeasurable(intrinsicMeasurable, NodeMeasuringIntrinsics.IntrinsicMinMax.Max, NodeMeasuringIntrinsics.IntrinsicWidthHeight.Height), ConstraintsKt.Constraints$default(0, i, 0, 0, 13)).getHeight();
    }

    /* JADX WARN: Multi-variable type inference failed */
    default int maxApproachIntrinsicWidth(ApproachIntrinsicMeasureScope approachIntrinsicMeasureScope, IntrinsicMeasurable intrinsicMeasurable, int i) {
        NodeCoordinator nodeCoordinator = ((Modifier.Node) this).node.coordinator;
        nodeCoordinator.getClass();
        LookaheadDelegate lookaheadDelegate = nodeCoordinator.getLookaheadDelegate();
        lookaheadDelegate.getClass();
        if (!lookaheadDelegate.getHasMeasureResult()) {
            return intrinsicMeasurable.maxIntrinsicWidth(i);
        }
        NodeMeasuringIntrinsics nodeMeasuringIntrinsics = NodeMeasuringIntrinsics.INSTANCE;
        NodeMeasuringIntrinsics.ApproachMeasureBlock approachMeasureBlock = new NodeMeasuringIntrinsics.ApproachMeasureBlock() { // from class: androidx.compose.ui.layout.ApproachLayoutModifierNode.maxApproachIntrinsicWidth.1
            @Override // androidx.compose.ui.node.NodeMeasuringIntrinsics.ApproachMeasureBlock
            /* renamed from: measure-3p2s80s */
            public final MeasureResult mo607measure3p2s80s(ApproachIntrinsicsMeasureScope approachIntrinsicsMeasureScope, Measurable measurable, long j) {
                return ApproachLayoutModifierNode.this.mo605approachMeasure3p2s80s(approachIntrinsicsMeasureScope, measurable, j);
            }
        };
        nodeMeasuringIntrinsics.getClass();
        return approachMeasureBlock.mo607measure3p2s80s(new ApproachIntrinsicsMeasureScope(approachIntrinsicMeasureScope, approachIntrinsicMeasureScope.getLayoutDirection()), new NodeMeasuringIntrinsics.DefaultIntrinsicMeasurable(intrinsicMeasurable, NodeMeasuringIntrinsics.IntrinsicMinMax.Max, NodeMeasuringIntrinsics.IntrinsicWidthHeight.Width), ConstraintsKt.Constraints$default(0, 0, 0, i, 7)).getWidth();
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    default MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(j);
        return measureScope.layout$1(placeableMo610measureBRTryo0.width, placeableMo610measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.ui.layout.ApproachLayoutModifierNode$measure$1$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                ((Placeable.PlacementScope) obj).place(placeableMo610measureBRTryo0, 0, 0, 0.0f);
                return Unit.INSTANCE;
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    default int minApproachIntrinsicHeight(ApproachIntrinsicMeasureScope approachIntrinsicMeasureScope, IntrinsicMeasurable intrinsicMeasurable, int i) {
        NodeCoordinator nodeCoordinator = ((Modifier.Node) this).node.coordinator;
        nodeCoordinator.getClass();
        LookaheadDelegate lookaheadDelegate = nodeCoordinator.getLookaheadDelegate();
        lookaheadDelegate.getClass();
        if (!lookaheadDelegate.getHasMeasureResult()) {
            return intrinsicMeasurable.minIntrinsicHeight(i);
        }
        NodeMeasuringIntrinsics nodeMeasuringIntrinsics = NodeMeasuringIntrinsics.INSTANCE;
        NodeMeasuringIntrinsics.ApproachMeasureBlock approachMeasureBlock = new NodeMeasuringIntrinsics.ApproachMeasureBlock() { // from class: androidx.compose.ui.layout.ApproachLayoutModifierNode.minApproachIntrinsicHeight.1
            @Override // androidx.compose.ui.node.NodeMeasuringIntrinsics.ApproachMeasureBlock
            /* renamed from: measure-3p2s80s */
            public final MeasureResult mo607measure3p2s80s(ApproachIntrinsicsMeasureScope approachIntrinsicsMeasureScope, Measurable measurable, long j) {
                return ApproachLayoutModifierNode.this.mo605approachMeasure3p2s80s(approachIntrinsicsMeasureScope, measurable, j);
            }
        };
        nodeMeasuringIntrinsics.getClass();
        return approachMeasureBlock.mo607measure3p2s80s(new ApproachIntrinsicsMeasureScope(approachIntrinsicMeasureScope, approachIntrinsicMeasureScope.getLayoutDirection()), new NodeMeasuringIntrinsics.DefaultIntrinsicMeasurable(intrinsicMeasurable, NodeMeasuringIntrinsics.IntrinsicMinMax.Min, NodeMeasuringIntrinsics.IntrinsicWidthHeight.Height), ConstraintsKt.Constraints$default(0, i, 0, 0, 13)).getHeight();
    }

    /* JADX WARN: Multi-variable type inference failed */
    default int minApproachIntrinsicWidth(ApproachIntrinsicMeasureScope approachIntrinsicMeasureScope, IntrinsicMeasurable intrinsicMeasurable, int i) {
        NodeCoordinator nodeCoordinator = ((Modifier.Node) this).node.coordinator;
        nodeCoordinator.getClass();
        LookaheadDelegate lookaheadDelegate = nodeCoordinator.getLookaheadDelegate();
        lookaheadDelegate.getClass();
        if (!lookaheadDelegate.getHasMeasureResult()) {
            return intrinsicMeasurable.minIntrinsicWidth(i);
        }
        NodeMeasuringIntrinsics nodeMeasuringIntrinsics = NodeMeasuringIntrinsics.INSTANCE;
        NodeMeasuringIntrinsics.ApproachMeasureBlock approachMeasureBlock = new NodeMeasuringIntrinsics.ApproachMeasureBlock() { // from class: androidx.compose.ui.layout.ApproachLayoutModifierNode.minApproachIntrinsicWidth.1
            @Override // androidx.compose.ui.node.NodeMeasuringIntrinsics.ApproachMeasureBlock
            /* renamed from: measure-3p2s80s */
            public final MeasureResult mo607measure3p2s80s(ApproachIntrinsicsMeasureScope approachIntrinsicsMeasureScope, Measurable measurable, long j) {
                return ApproachLayoutModifierNode.this.mo605approachMeasure3p2s80s(approachIntrinsicsMeasureScope, measurable, j);
            }
        };
        nodeMeasuringIntrinsics.getClass();
        return approachMeasureBlock.mo607measure3p2s80s(new ApproachIntrinsicsMeasureScope(approachIntrinsicMeasureScope, approachIntrinsicMeasureScope.getLayoutDirection()), new NodeMeasuringIntrinsics.DefaultIntrinsicMeasurable(intrinsicMeasurable, NodeMeasuringIntrinsics.IntrinsicMinMax.Min, NodeMeasuringIntrinsics.IntrinsicWidthHeight.Width), ConstraintsKt.Constraints$default(0, 0, 0, i, 7)).getWidth();
    }
}
