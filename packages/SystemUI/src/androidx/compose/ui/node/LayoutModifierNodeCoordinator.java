package androidx.compose.ui.node;

import androidx.collection.MutableObjectIntMap;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.AndroidPaint;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.PaintingStyle;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.layout.AlignmentLine;
import androidx.compose.ui.layout.ApproachLayoutModifierNode;
import androidx.compose.ui.layout.ApproachMeasureScopeImpl;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.IntSize;
import java.util.Map;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class LayoutModifierNodeCoordinator extends NodeCoordinator {
    public static final AndroidPaint modifierBoundsPaint;
    public ApproachMeasureScopeImpl approachMeasureScope;
    public LayoutModifierNode layoutModifierNode;
    public Constraints lookaheadConstraints;
    public LookaheadDelegate lookaheadDelegate;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    final class LookaheadDelegateForLayoutModifierNode extends LookaheadDelegate {
        public LookaheadDelegateForLayoutModifierNode() {
            super(LayoutModifierNodeCoordinator.this);
        }

        @Override // androidx.compose.ui.node.LookaheadCapablePlaceable
        public final int calculateAlignmentLine(AlignmentLine alignmentLine) {
            int iAccess$calculateAlignmentAndPlaceChildAsNeeded = LayoutModifierNodeCoordinatorKt.access$calculateAlignmentAndPlaceChildAsNeeded(this, alignmentLine);
            this.cachedAlignmentLinesMap.set(iAccess$calculateAlignmentAndPlaceChildAsNeeded, alignmentLine);
            return iAccess$calculateAlignmentAndPlaceChildAsNeeded;
        }

        @Override // androidx.compose.ui.node.LookaheadDelegate, androidx.compose.ui.layout.IntrinsicMeasurable
        public final int maxIntrinsicHeight(int i) {
            LayoutModifierNodeCoordinator layoutModifierNodeCoordinator = LayoutModifierNodeCoordinator.this;
            LayoutModifierNode layoutModifierNode = layoutModifierNodeCoordinator.layoutModifierNode;
            NodeCoordinator nodeCoordinator = layoutModifierNodeCoordinator.wrapped;
            nodeCoordinator.getClass();
            LookaheadDelegate lookaheadDelegate = nodeCoordinator.getLookaheadDelegate();
            lookaheadDelegate.getClass();
            return layoutModifierNode.maxIntrinsicHeight(this, lookaheadDelegate, i);
        }

        @Override // androidx.compose.ui.node.LookaheadDelegate, androidx.compose.ui.layout.IntrinsicMeasurable
        public final int maxIntrinsicWidth(int i) {
            LayoutModifierNodeCoordinator layoutModifierNodeCoordinator = LayoutModifierNodeCoordinator.this;
            LayoutModifierNode layoutModifierNode = layoutModifierNodeCoordinator.layoutModifierNode;
            NodeCoordinator nodeCoordinator = layoutModifierNodeCoordinator.wrapped;
            nodeCoordinator.getClass();
            LookaheadDelegate lookaheadDelegate = nodeCoordinator.getLookaheadDelegate();
            lookaheadDelegate.getClass();
            return layoutModifierNode.maxIntrinsicWidth(this, lookaheadDelegate, i);
        }

        @Override // androidx.compose.ui.layout.Measurable
        /* renamed from: measure-BRTryo0 */
        public final Placeable mo610measureBRTryo0(long j) {
            m627setMeasurementConstraintsBRTryo0(j);
            Constraints constraintsM815boximpl = Constraints.m815boximpl(j);
            LayoutModifierNodeCoordinator layoutModifierNodeCoordinator = LayoutModifierNodeCoordinator.this;
            layoutModifierNodeCoordinator.lookaheadConstraints = constraintsM815boximpl;
            LayoutModifierNode layoutModifierNode = layoutModifierNodeCoordinator.layoutModifierNode;
            NodeCoordinator nodeCoordinator = layoutModifierNodeCoordinator.wrapped;
            nodeCoordinator.getClass();
            LookaheadDelegate lookaheadDelegate = nodeCoordinator.getLookaheadDelegate();
            lookaheadDelegate.getClass();
            LookaheadDelegate.access$set_measureResult(this, layoutModifierNode.mo4measure3p2s80s(this, lookaheadDelegate, j));
            return this;
        }

        @Override // androidx.compose.ui.node.LookaheadDelegate, androidx.compose.ui.layout.IntrinsicMeasurable
        public final int minIntrinsicHeight(int i) {
            LayoutModifierNodeCoordinator layoutModifierNodeCoordinator = LayoutModifierNodeCoordinator.this;
            LayoutModifierNode layoutModifierNode = layoutModifierNodeCoordinator.layoutModifierNode;
            NodeCoordinator nodeCoordinator = layoutModifierNodeCoordinator.wrapped;
            nodeCoordinator.getClass();
            LookaheadDelegate lookaheadDelegate = nodeCoordinator.getLookaheadDelegate();
            lookaheadDelegate.getClass();
            return layoutModifierNode.minIntrinsicHeight(this, lookaheadDelegate, i);
        }

        @Override // androidx.compose.ui.node.LookaheadDelegate, androidx.compose.ui.layout.IntrinsicMeasurable
        public final int minIntrinsicWidth(int i) {
            LayoutModifierNodeCoordinator layoutModifierNodeCoordinator = LayoutModifierNodeCoordinator.this;
            LayoutModifierNode layoutModifierNode = layoutModifierNodeCoordinator.layoutModifierNode;
            NodeCoordinator nodeCoordinator = layoutModifierNodeCoordinator.wrapped;
            nodeCoordinator.getClass();
            LookaheadDelegate lookaheadDelegate = nodeCoordinator.getLookaheadDelegate();
            lookaheadDelegate.getClass();
            return layoutModifierNode.minIntrinsicWidth(this, lookaheadDelegate, i);
        }
    }

    static {
        new Companion(null);
        AndroidPaint androidPaint = new AndroidPaint();
        Color.Companion.getClass();
        androidPaint.m440setColor8_81llA(Color.Blue);
        androidPaint.setStrokeWidth(1.0f);
        PaintingStyle.Companion.getClass();
        androidPaint.m444setStylek9PVt8s(PaintingStyle.Stroke);
        modifierBoundsPaint = androidPaint;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public LayoutModifierNodeCoordinator(LayoutNode layoutNode, LayoutModifierNode layoutModifierNode) {
        super(layoutNode);
        this.layoutModifierNode = layoutModifierNode;
        this.lookaheadDelegate = layoutNode.lookaheadRoot != null ? new LookaheadDelegateForLayoutModifierNode() : null;
        this.approachMeasureScope = (((Modifier.Node) layoutModifierNode).node.kindSet & 512) != 0 ? new ApproachMeasureScopeImpl(this, (ApproachLayoutModifierNode) layoutModifierNode) : null;
    }

    @Override // androidx.compose.ui.node.LookaheadCapablePlaceable
    public final int calculateAlignmentLine(AlignmentLine alignmentLine) {
        LookaheadDelegate lookaheadDelegate = this.lookaheadDelegate;
        if (lookaheadDelegate == null) {
            return LayoutModifierNodeCoordinatorKt.access$calculateAlignmentAndPlaceChildAsNeeded(this, alignmentLine);
        }
        MutableObjectIntMap mutableObjectIntMap = lookaheadDelegate.cachedAlignmentLinesMap;
        int iFindKeyIndex = mutableObjectIntMap.findKeyIndex(alignmentLine);
        if (iFindKeyIndex >= 0) {
            return mutableObjectIntMap.values[iFindKeyIndex];
        }
        return Integer.MIN_VALUE;
    }

    @Override // androidx.compose.ui.node.NodeCoordinator
    public final void ensureLookaheadDelegateCreated() {
        if (this.lookaheadDelegate == null) {
            this.lookaheadDelegate = new LookaheadDelegateForLayoutModifierNode();
        }
    }

    @Override // androidx.compose.ui.node.NodeCoordinator
    public final LookaheadDelegate getLookaheadDelegate() {
        return this.lookaheadDelegate;
    }

    @Override // androidx.compose.ui.node.NodeCoordinator
    public final Modifier.Node getTail() {
        return ((Modifier.Node) this.layoutModifierNode).node;
    }

    @Override // androidx.compose.ui.layout.IntrinsicMeasurable
    public final int maxIntrinsicHeight(int i) {
        ApproachMeasureScopeImpl approachMeasureScopeImpl = this.approachMeasureScope;
        if (approachMeasureScopeImpl != null) {
            ApproachLayoutModifierNode approachLayoutModifierNode = approachMeasureScopeImpl.approachNode;
            NodeCoordinator nodeCoordinator = this.wrapped;
            nodeCoordinator.getClass();
            return approachLayoutModifierNode.maxApproachIntrinsicHeight(approachMeasureScopeImpl, nodeCoordinator, i);
        }
        LayoutModifierNode layoutModifierNode = this.layoutModifierNode;
        NodeCoordinator nodeCoordinator2 = this.wrapped;
        nodeCoordinator2.getClass();
        return layoutModifierNode.maxIntrinsicHeight(this, nodeCoordinator2, i);
    }

    @Override // androidx.compose.ui.layout.IntrinsicMeasurable
    public final int maxIntrinsicWidth(int i) {
        ApproachMeasureScopeImpl approachMeasureScopeImpl = this.approachMeasureScope;
        if (approachMeasureScopeImpl != null) {
            ApproachLayoutModifierNode approachLayoutModifierNode = approachMeasureScopeImpl.approachNode;
            NodeCoordinator nodeCoordinator = this.wrapped;
            nodeCoordinator.getClass();
            return approachLayoutModifierNode.maxApproachIntrinsicWidth(approachMeasureScopeImpl, nodeCoordinator, i);
        }
        LayoutModifierNode layoutModifierNode = this.layoutModifierNode;
        NodeCoordinator nodeCoordinator2 = this.wrapped;
        nodeCoordinator2.getClass();
        return layoutModifierNode.maxIntrinsicWidth(this, nodeCoordinator2, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x006d  */
    @Override // androidx.compose.ui.layout.Measurable
    /* renamed from: measure-BRTryo0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Placeable mo610measureBRTryo0(long j) {
        final MeasureResult measureResultMo4measure3p2s80s;
        Constraints constraints;
        if (this.forceMeasureWithLookaheadConstraints) {
            Constraints constraints2 = this.lookaheadConstraints;
            if (constraints2 == null) {
                throw new IllegalArgumentException("Lookahead constraints cannot be null in approach pass.");
            }
            j = constraints2.value;
        }
        m627setMeasurementConstraintsBRTryo0(j);
        ApproachMeasureScopeImpl approachMeasureScopeImpl = this.approachMeasureScope;
        if (approachMeasureScopeImpl != null) {
            ApproachLayoutModifierNode approachLayoutModifierNode = approachMeasureScopeImpl.approachNode;
            boolean z = approachLayoutModifierNode.mo606isMeasurementApproachInProgressozmzZPI(approachMeasureScopeImpl.mo604getLookaheadSizeYbymL2g()) || (constraints = this.lookaheadConstraints) == null || j != constraints.value;
            approachMeasureScopeImpl.approachMeasureRequired = z;
            if (!z) {
                NodeCoordinator nodeCoordinator = this.wrapped;
                nodeCoordinator.getClass();
                nodeCoordinator.forceMeasureWithLookaheadConstraints = true;
            }
            NodeCoordinator nodeCoordinator2 = this.wrapped;
            nodeCoordinator2.getClass();
            measureResultMo4measure3p2s80s = approachLayoutModifierNode.mo605approachMeasure3p2s80s(approachMeasureScopeImpl, nodeCoordinator2, j);
            NodeCoordinator nodeCoordinator3 = this.wrapped;
            nodeCoordinator3.getClass();
            nodeCoordinator3.forceMeasureWithLookaheadConstraints = false;
            int width = measureResultMo4measure3p2s80s.getWidth();
            LookaheadDelegate lookaheadDelegate = this.lookaheadDelegate;
            lookaheadDelegate.getClass();
            if (width == lookaheadDelegate.width) {
                int height = measureResultMo4measure3p2s80s.getHeight();
                LookaheadDelegate lookaheadDelegate2 = this.lookaheadDelegate;
                lookaheadDelegate2.getClass();
                boolean z2 = height == lookaheadDelegate2.height;
                if (!approachMeasureScopeImpl.approachMeasureRequired) {
                    NodeCoordinator nodeCoordinator4 = this.wrapped;
                    nodeCoordinator4.getClass();
                    long j2 = nodeCoordinator4.measuredSize;
                    NodeCoordinator nodeCoordinator5 = this.wrapped;
                    nodeCoordinator5.getClass();
                    LookaheadDelegate lookaheadDelegate3 = nodeCoordinator5.getLookaheadDelegate();
                    if (IntSize.m862equalsimpl(j2, lookaheadDelegate3 != null ? IntSize.m861boximpl(lookaheadDelegate3.m652getSizeYbymL2g$ui_release()) : null) && !z2) {
                        measureResultMo4measure3p2s80s = new MeasureResult(this) { // from class: androidx.compose.ui.node.LayoutModifierNodeCoordinator$measure$1$1$1$1
                            public final int height;
                            public final int width;

                            {
                                LookaheadDelegate lookaheadDelegate4 = this.lookaheadDelegate;
                                lookaheadDelegate4.getClass();
                                this.width = lookaheadDelegate4.width;
                                LookaheadDelegate lookaheadDelegate5 = this.lookaheadDelegate;
                                lookaheadDelegate5.getClass();
                                this.height = lookaheadDelegate5.height;
                            }

                            @Override // androidx.compose.ui.layout.MeasureResult
                            public final Map getAlignmentLines() {
                                return this.$$delegate_0.getAlignmentLines();
                            }

                            @Override // androidx.compose.ui.layout.MeasureResult
                            public final int getHeight() {
                                return this.height;
                            }

                            @Override // androidx.compose.ui.layout.MeasureResult
                            public final Function1 getRulers() {
                                return this.$$delegate_0.getRulers();
                            }

                            @Override // androidx.compose.ui.layout.MeasureResult
                            public final int getWidth() {
                                return this.width;
                            }

                            @Override // androidx.compose.ui.layout.MeasureResult
                            public final void placeChildren() {
                                this.$$delegate_0.placeChildren();
                            }
                        };
                    }
                }
            }
        } else {
            LayoutModifierNode layoutModifierNode = this.layoutModifierNode;
            NodeCoordinator nodeCoordinator6 = this.wrapped;
            nodeCoordinator6.getClass();
            measureResultMo4measure3p2s80s = layoutModifierNode.mo4measure3p2s80s(this, nodeCoordinator6, j);
        }
        setMeasureResult$ui_release(measureResultMo4measure3p2s80s);
        onMeasured();
        return this;
    }

    @Override // androidx.compose.ui.layout.IntrinsicMeasurable
    public final int minIntrinsicHeight(int i) {
        ApproachMeasureScopeImpl approachMeasureScopeImpl = this.approachMeasureScope;
        if (approachMeasureScopeImpl != null) {
            ApproachLayoutModifierNode approachLayoutModifierNode = approachMeasureScopeImpl.approachNode;
            NodeCoordinator nodeCoordinator = this.wrapped;
            nodeCoordinator.getClass();
            return approachLayoutModifierNode.minApproachIntrinsicHeight(approachMeasureScopeImpl, nodeCoordinator, i);
        }
        LayoutModifierNode layoutModifierNode = this.layoutModifierNode;
        NodeCoordinator nodeCoordinator2 = this.wrapped;
        nodeCoordinator2.getClass();
        return layoutModifierNode.minIntrinsicHeight(this, nodeCoordinator2, i);
    }

    @Override // androidx.compose.ui.layout.IntrinsicMeasurable
    public final int minIntrinsicWidth(int i) {
        ApproachMeasureScopeImpl approachMeasureScopeImpl = this.approachMeasureScope;
        if (approachMeasureScopeImpl != null) {
            ApproachLayoutModifierNode approachLayoutModifierNode = approachMeasureScopeImpl.approachNode;
            NodeCoordinator nodeCoordinator = this.wrapped;
            nodeCoordinator.getClass();
            return approachLayoutModifierNode.minApproachIntrinsicWidth(approachMeasureScopeImpl, nodeCoordinator, i);
        }
        LayoutModifierNode layoutModifierNode = this.layoutModifierNode;
        NodeCoordinator nodeCoordinator2 = this.wrapped;
        nodeCoordinator2.getClass();
        return layoutModifierNode.minIntrinsicWidth(this, nodeCoordinator2, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x005b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onAfterPlaceAt$1() {
        boolean z;
        if (this.isShallowPlacing) {
            return;
        }
        onPlaced();
        ApproachMeasureScopeImpl approachMeasureScopeImpl = this.approachMeasureScope;
        if (approachMeasureScopeImpl != null) {
            ApproachLayoutModifierNode approachLayoutModifierNode = approachMeasureScopeImpl.approachNode;
            Placeable.PlacementScope placementScope = this.placementScope;
            LookaheadDelegate lookaheadDelegate = this.lookaheadDelegate;
            lookaheadDelegate.getClass();
            if (approachLayoutModifierNode.isPlacementApproachInProgress(placementScope, lookaheadDelegate.lookaheadLayoutCoordinates) || approachMeasureScopeImpl.approachMeasureRequired) {
                z = false;
                NodeCoordinator nodeCoordinator = this.wrapped;
                nodeCoordinator.getClass();
                nodeCoordinator.forcePlaceWithLookaheadOffset = z;
            } else {
                long j = this.measuredSize;
                LookaheadDelegate lookaheadDelegate2 = this.lookaheadDelegate;
                if (IntSize.m862equalsimpl(j, lookaheadDelegate2 != null ? IntSize.m861boximpl(lookaheadDelegate2.m652getSizeYbymL2g$ui_release()) : null)) {
                    NodeCoordinator nodeCoordinator2 = this.wrapped;
                    nodeCoordinator2.getClass();
                    long j2 = nodeCoordinator2.measuredSize;
                    NodeCoordinator nodeCoordinator3 = this.wrapped;
                    nodeCoordinator3.getClass();
                    LookaheadDelegate lookaheadDelegate3 = nodeCoordinator3.getLookaheadDelegate();
                    if (IntSize.m862equalsimpl(j2, lookaheadDelegate3 != null ? IntSize.m861boximpl(lookaheadDelegate3.m652getSizeYbymL2g$ui_release()) : null)) {
                        z = true;
                    }
                    NodeCoordinator nodeCoordinator4 = this.wrapped;
                    nodeCoordinator4.getClass();
                    nodeCoordinator4.forcePlaceWithLookaheadOffset = z;
                }
            }
        }
        getMeasureResult$ui_release().placeChildren();
        NodeCoordinator nodeCoordinator5 = this.wrapped;
        nodeCoordinator5.getClass();
        nodeCoordinator5.forcePlaceWithLookaheadOffset = false;
    }

    @Override // androidx.compose.ui.node.NodeCoordinator
    public final void performDraw(Canvas canvas, GraphicsLayer graphicsLayer) {
        NodeCoordinator nodeCoordinator = this.wrapped;
        nodeCoordinator.getClass();
        nodeCoordinator.draw(canvas, graphicsLayer);
        if (((AndroidComposeView) LayoutNodeKt.requireOwner(this.layoutNode)).showLayoutBounds) {
            long j = this.measuredSize;
            canvas.drawRect(0.5f, 0.5f, ((int) (j >> 32)) - 0.5f, ((int) (j & 4294967295L)) - 0.5f, modifierBoundsPaint);
        }
    }

    @Override // androidx.compose.ui.node.NodeCoordinator, androidx.compose.ui.layout.Placeable
    /* renamed from: placeAt-f8xVGno */
    public final void mo625placeAtf8xVGno(long j, float f, GraphicsLayer graphicsLayer) {
        super.mo625placeAtf8xVGno(j, f, graphicsLayer);
        onAfterPlaceAt$1();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void setLayoutModifierNode$ui_release(LayoutModifierNode layoutModifierNode) {
        if (!layoutModifierNode.equals(this.layoutModifierNode)) {
            if ((((Modifier.Node) layoutModifierNode).node.kindSet & 512) != 0) {
                ApproachLayoutModifierNode approachLayoutModifierNode = (ApproachLayoutModifierNode) layoutModifierNode;
                ApproachMeasureScopeImpl approachMeasureScopeImpl = this.approachMeasureScope;
                if (approachMeasureScopeImpl != null) {
                    approachMeasureScopeImpl.approachNode = approachLayoutModifierNode;
                } else {
                    approachMeasureScopeImpl = new ApproachMeasureScopeImpl(this, approachLayoutModifierNode);
                }
                this.approachMeasureScope = approachMeasureScopeImpl;
            } else {
                this.approachMeasureScope = null;
            }
        }
        this.layoutModifierNode = layoutModifierNode;
    }

    @Override // androidx.compose.ui.node.NodeCoordinator, androidx.compose.ui.layout.Placeable
    /* renamed from: placeAt-f8xVGno */
    public final void mo611placeAtf8xVGno(long j, float f, Function1 function1) {
        super.mo611placeAtf8xVGno(j, f, function1);
        onAfterPlaceAt$1();
    }
}
