package androidx.compose.ui.node;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.AndroidPaint;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.PaintingStyle;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.input.pointer.PointerType;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.AlignmentLine;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.platform.AndroidComposeView;
import java.util.HashMap;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class InnerNodeCoordinator extends NodeCoordinator {
    public static final AndroidPaint innerBoundsPaint;
    public LookaheadDelegate lookaheadDelegate;
    public final TailModifierNode tail;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    final class LookaheadDelegateImpl extends LookaheadDelegate {
        public LookaheadDelegateImpl(InnerNodeCoordinator innerNodeCoordinator) {
            super(innerNodeCoordinator);
        }

        @Override // androidx.compose.ui.node.LookaheadCapablePlaceable
        public final int calculateAlignmentLine(AlignmentLine alignmentLine) {
            LookaheadPassDelegate lookaheadPassDelegate = this.coordinator.layoutNode.layoutDelegate.lookaheadPassDelegate;
            lookaheadPassDelegate.getClass();
            boolean z = lookaheadPassDelegate.duringAlignmentLinesQuery;
            LookaheadAlignmentLines lookaheadAlignmentLines = lookaheadPassDelegate.alignmentLines;
            if (!z) {
                LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = lookaheadPassDelegate.layoutNodeLayoutDelegate;
                if (layoutNodeLayoutDelegate.layoutState == LayoutNode.LayoutState.LookaheadMeasuring) {
                    lookaheadAlignmentLines.usedByModifierMeasurement = true;
                    if (lookaheadAlignmentLines.dirty) {
                        layoutNodeLayoutDelegate.lookaheadLayoutPending = true;
                        layoutNodeLayoutDelegate.lookaheadLayoutPendingForAlignment = true;
                    }
                } else {
                    lookaheadAlignmentLines.usedByModifierLayout = true;
                }
            }
            LookaheadDelegate lookaheadDelegate = lookaheadPassDelegate.getInnerCoordinator().lookaheadDelegate;
            if (lookaheadDelegate != null) {
                lookaheadDelegate.isPlacingForAlignment = true;
            }
            lookaheadPassDelegate.layoutChildren();
            LookaheadDelegate lookaheadDelegate2 = lookaheadPassDelegate.getInnerCoordinator().lookaheadDelegate;
            if (lookaheadDelegate2 != null) {
                lookaheadDelegate2.isPlacingForAlignment = false;
            }
            Integer num = (Integer) ((HashMap) lookaheadAlignmentLines.alignmentLineMap).get(alignmentLine);
            int iIntValue = num != null ? num.intValue() : Integer.MIN_VALUE;
            this.cachedAlignmentLinesMap.set(iIntValue, alignmentLine);
            return iIntValue;
        }

        @Override // androidx.compose.ui.node.LookaheadDelegate, androidx.compose.ui.layout.IntrinsicMeasurable
        public final int maxIntrinsicHeight(int i) {
            IntrinsicsPolicy orCreateIntrinsicsPolicy = this.coordinator.layoutNode.getOrCreateIntrinsicsPolicy();
            MeasurePolicy measurePolicyState = orCreateIntrinsicsPolicy.getMeasurePolicyState();
            LayoutNode layoutNode = orCreateIntrinsicsPolicy.layoutNode;
            return measurePolicyState.maxIntrinsicHeight(layoutNode.nodes.outerCoordinator, layoutNode.getChildLookaheadMeasurables$ui_release(), i);
        }

        @Override // androidx.compose.ui.node.LookaheadDelegate, androidx.compose.ui.layout.IntrinsicMeasurable
        public final int maxIntrinsicWidth(int i) {
            IntrinsicsPolicy orCreateIntrinsicsPolicy = this.coordinator.layoutNode.getOrCreateIntrinsicsPolicy();
            MeasurePolicy measurePolicyState = orCreateIntrinsicsPolicy.getMeasurePolicyState();
            LayoutNode layoutNode = orCreateIntrinsicsPolicy.layoutNode;
            return measurePolicyState.maxIntrinsicWidth(layoutNode.nodes.outerCoordinator, layoutNode.getChildLookaheadMeasurables$ui_release(), i);
        }

        @Override // androidx.compose.ui.layout.Measurable
        /* renamed from: measure-BRTryo0 */
        public final Placeable mo610measureBRTryo0(long j) {
            m627setMeasurementConstraintsBRTryo0(j);
            NodeCoordinator nodeCoordinator = this.coordinator;
            MutableVector mutableVector = nodeCoordinator.layoutNode.get_children$ui_release();
            Object[] objArr = mutableVector.content;
            int i = mutableVector.size;
            for (int i2 = 0; i2 < i; i2++) {
                LookaheadPassDelegate lookaheadPassDelegate = ((LayoutNode) objArr[i2]).layoutDelegate.lookaheadPassDelegate;
                lookaheadPassDelegate.getClass();
                lookaheadPassDelegate.measuredByParent = LayoutNode.UsageByParent.NotUsed;
            }
            LayoutNode layoutNode = nodeCoordinator.layoutNode;
            LookaheadDelegate.access$set_measureResult(this, layoutNode.measurePolicy.mo3measure3p2s80s(this, layoutNode.getChildLookaheadMeasurables$ui_release(), j));
            return this;
        }

        @Override // androidx.compose.ui.node.LookaheadDelegate, androidx.compose.ui.layout.IntrinsicMeasurable
        public final int minIntrinsicHeight(int i) {
            IntrinsicsPolicy orCreateIntrinsicsPolicy = this.coordinator.layoutNode.getOrCreateIntrinsicsPolicy();
            MeasurePolicy measurePolicyState = orCreateIntrinsicsPolicy.getMeasurePolicyState();
            LayoutNode layoutNode = orCreateIntrinsicsPolicy.layoutNode;
            return measurePolicyState.minIntrinsicHeight(layoutNode.nodes.outerCoordinator, layoutNode.getChildLookaheadMeasurables$ui_release(), i);
        }

        @Override // androidx.compose.ui.node.LookaheadDelegate, androidx.compose.ui.layout.IntrinsicMeasurable
        public final int minIntrinsicWidth(int i) {
            IntrinsicsPolicy orCreateIntrinsicsPolicy = this.coordinator.layoutNode.getOrCreateIntrinsicsPolicy();
            MeasurePolicy measurePolicyState = orCreateIntrinsicsPolicy.getMeasurePolicyState();
            LayoutNode layoutNode = orCreateIntrinsicsPolicy.layoutNode;
            return measurePolicyState.minIntrinsicWidth(layoutNode.nodes.outerCoordinator, layoutNode.getChildLookaheadMeasurables$ui_release(), i);
        }

        @Override // androidx.compose.ui.node.LookaheadDelegate
        public final void placeChildren() {
            LookaheadPassDelegate lookaheadPassDelegate = this.coordinator.layoutNode.layoutDelegate.lookaheadPassDelegate;
            lookaheadPassDelegate.getClass();
            lookaheadPassDelegate.onNodePlaced$ui_release();
        }
    }

    static {
        new Companion(null);
        AndroidPaint androidPaint = new AndroidPaint();
        Color.Companion.getClass();
        androidPaint.m440setColor8_81llA(Color.Red);
        androidPaint.setStrokeWidth(1.0f);
        PaintingStyle.Companion.getClass();
        androidPaint.m444setStylek9PVt8s(PaintingStyle.Stroke);
        innerBoundsPaint = androidPaint;
    }

    public InnerNodeCoordinator(LayoutNode layoutNode) {
        super(layoutNode);
        TailModifierNode tailModifierNode = new TailModifierNode();
        this.tail = tailModifierNode;
        tailModifierNode.coordinator = this;
        this.lookaheadDelegate = layoutNode.lookaheadRoot != null ? new LookaheadDelegateImpl(this) : null;
    }

    @Override // androidx.compose.ui.node.LookaheadCapablePlaceable
    public final int calculateAlignmentLine(AlignmentLine alignmentLine) {
        LookaheadDelegate lookaheadDelegate = this.lookaheadDelegate;
        if (lookaheadDelegate != null) {
            return lookaheadDelegate.calculateAlignmentLine(alignmentLine);
        }
        MeasurePassDelegate measurePassDelegate = this.layoutNode.layoutDelegate.measurePassDelegate;
        boolean z = measurePassDelegate.duringAlignmentLinesQuery;
        LayoutNodeAlignmentLines layoutNodeAlignmentLines = measurePassDelegate.alignmentLines;
        if (!z) {
            if (measurePassDelegate.layoutNodeLayoutDelegate.layoutState == LayoutNode.LayoutState.Measuring) {
                layoutNodeAlignmentLines.usedByModifierMeasurement = true;
                if (layoutNodeAlignmentLines.dirty) {
                    measurePassDelegate.layoutPending = true;
                    measurePassDelegate.layoutPendingForAlignment = true;
                }
            } else {
                layoutNodeAlignmentLines.usedByModifierLayout = true;
            }
        }
        measurePassDelegate.getInnerCoordinator().isPlacingForAlignment = true;
        measurePassDelegate.layoutChildren();
        measurePassDelegate.getInnerCoordinator().isPlacingForAlignment = false;
        Integer num = (Integer) ((HashMap) layoutNodeAlignmentLines.alignmentLineMap).get(alignmentLine);
        if (num != null) {
            return num.intValue();
        }
        return Integer.MIN_VALUE;
    }

    @Override // androidx.compose.ui.node.NodeCoordinator
    public final void ensureLookaheadDelegateCreated() {
        if (this.lookaheadDelegate == null) {
            this.lookaheadDelegate = new LookaheadDelegateImpl(this);
        }
    }

    @Override // androidx.compose.ui.node.NodeCoordinator
    public final LookaheadDelegate getLookaheadDelegate() {
        return this.lookaheadDelegate;
    }

    @Override // androidx.compose.ui.node.NodeCoordinator
    public final Modifier.Node getTail() {
        return this.tail;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:94:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r2v10, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r2v9, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v11 */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v3, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    @Override // androidx.compose.ui.node.NodeCoordinator
    /* renamed from: hitTestChild-qzLsGqo, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void mo640hitTestChildqzLsGqo(NodeCoordinator.HitTestSource hitTestSource, long j, HitTestResult hitTestResult, int i, boolean z) {
        int i2;
        boolean z2;
        boolean z3;
        boolean z4;
        long j2 = j;
        LayoutNode layoutNode = this.layoutNode;
        if (hitTestSource.shouldHitTestChildren(layoutNode)) {
            if (m679withinLayerBoundsk4lQ0M(j2)) {
                i2 = i;
                z2 = z;
                z3 = true;
            } else {
                PointerType.Companion.getClass();
                i2 = i;
                if (i2 == PointerType.Touch && (Float.floatToRawIntBits(m668distanceInMinimumTouchTargettz77jQw(j2, m670getMinimumTouchTargetSizeNHjbRc())) & Integer.MAX_VALUE) < 2139095040) {
                    z3 = true;
                    z2 = false;
                }
            }
            if (z3) {
                return;
            }
            int i3 = hitTestResult.hitDepth;
            MutableVector zSortedChildren = layoutNode.getZSortedChildren();
            Object[] objArr = zSortedChildren.content;
            int i4 = zSortedChildren.size - 1;
            loop0: while (i4 >= 0) {
                LayoutNode layoutNode2 = (LayoutNode) objArr[i4];
                if (layoutNode2.isPlaced()) {
                    int i5 = i2;
                    z4 = z2;
                    hitTestSource.mo680childHitTestqzLsGqo(layoutNode2, j2, hitTestResult, i5, z4);
                    long jM639findBestHitDistancefn2tFes = hitTestResult.m639findBestHitDistancefn2tFes();
                    if (DistanceAndFlags.m636getDistanceimpl(jM639findBestHitDistancefn2tFes) < 0.0f && DistanceAndFlags.m638isInLayerimpl(jM639findBestHitDistancefn2tFes) && !DistanceAndFlags.m637isInExpandedBoundsimpl(jM639findBestHitDistancefn2tFes)) {
                        NodeCoordinator nodeCoordinator = layoutNode2.nodes.outerCoordinator;
                        nodeCoordinator.getClass();
                        Modifier.Node nodeHeadNode = nodeCoordinator.headNode(NodeKindKt.m683getIncludeSelfInTraversalH91voCI(16));
                        if (nodeHeadNode == null || !nodeHeadNode.isAttached) {
                            break;
                        }
                        if (!nodeHeadNode.node.isAttached) {
                            InlineClassHelperKt.throwIllegalStateException("visitLocalDescendants called on an unattached node");
                        }
                        Modifier.Node node = nodeHeadNode.node;
                        if ((node.aggregateChildKindSet & 16) == 0) {
                            break;
                        }
                        while (node != null) {
                            if ((node.kindSet & 16) != 0) {
                                DelegatingNode delegatingNodeAccess$pop = node;
                                ?? mutableVector = 0;
                                while (delegatingNodeAccess$pop != 0) {
                                    if (delegatingNodeAccess$pop instanceof PointerInputModifierNode) {
                                        if (((PointerInputModifierNode) delegatingNodeAccess$pop).sharePointerInputWithSiblings()) {
                                            hitTestResult.hitDepth = hitTestResult.values._size - 1;
                                        }
                                    } else if ((delegatingNodeAccess$pop.kindSet & 16) != 0 && (delegatingNodeAccess$pop instanceof DelegatingNode)) {
                                        Modifier.Node node2 = delegatingNodeAccess$pop.delegate;
                                        int i6 = 0;
                                        delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                                        mutableVector = mutableVector;
                                        while (node2 != null) {
                                            if ((node2.kindSet & 16) != 0) {
                                                i6++;
                                                mutableVector = mutableVector;
                                                if (i6 == 1) {
                                                    delegatingNodeAccess$pop = node2;
                                                } else {
                                                    if (mutableVector == 0) {
                                                        mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                                    }
                                                    if (delegatingNodeAccess$pop != 0) {
                                                        mutableVector.add(delegatingNodeAccess$pop);
                                                        delegatingNodeAccess$pop = 0;
                                                    }
                                                    mutableVector.add(node2);
                                                }
                                            }
                                            node2 = node2.child;
                                            delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                                            mutableVector = mutableVector;
                                        }
                                        if (i6 == 1) {
                                        }
                                    }
                                    delegatingNodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector);
                                }
                            }
                            node = node.child;
                        }
                        break loop0;
                    }
                } else {
                    z4 = z2;
                }
                i4--;
                j2 = j;
                z2 = z4;
                i2 = i;
            }
            hitTestResult.hitDepth = i3;
            return;
        }
        i2 = i;
        z2 = z;
        z3 = false;
        if (z3) {
        }
    }

    @Override // androidx.compose.ui.layout.IntrinsicMeasurable
    public final int maxIntrinsicHeight(int i) {
        IntrinsicsPolicy orCreateIntrinsicsPolicy = this.layoutNode.getOrCreateIntrinsicsPolicy();
        MeasurePolicy measurePolicyState = orCreateIntrinsicsPolicy.getMeasurePolicyState();
        LayoutNode layoutNode = orCreateIntrinsicsPolicy.layoutNode;
        return measurePolicyState.maxIntrinsicHeight(layoutNode.nodes.outerCoordinator, layoutNode.getChildMeasurables$ui_release(), i);
    }

    @Override // androidx.compose.ui.layout.IntrinsicMeasurable
    public final int maxIntrinsicWidth(int i) {
        IntrinsicsPolicy orCreateIntrinsicsPolicy = this.layoutNode.getOrCreateIntrinsicsPolicy();
        MeasurePolicy measurePolicyState = orCreateIntrinsicsPolicy.getMeasurePolicyState();
        LayoutNode layoutNode = orCreateIntrinsicsPolicy.layoutNode;
        return measurePolicyState.maxIntrinsicWidth(layoutNode.nodes.outerCoordinator, layoutNode.getChildMeasurables$ui_release(), i);
    }

    @Override // androidx.compose.ui.layout.Measurable
    /* renamed from: measure-BRTryo0 */
    public final Placeable mo610measureBRTryo0(long j) {
        if (this.forceMeasureWithLookaheadConstraints) {
            LookaheadDelegate lookaheadDelegate = this.lookaheadDelegate;
            lookaheadDelegate.getClass();
            j = lookaheadDelegate.measurementConstraints;
        }
        m627setMeasurementConstraintsBRTryo0(j);
        LayoutNode layoutNode = this.layoutNode;
        MutableVector mutableVector = layoutNode.get_children$ui_release();
        Object[] objArr = mutableVector.content;
        int i = mutableVector.size;
        for (int i2 = 0; i2 < i; i2++) {
            ((LayoutNode) objArr[i2]).layoutDelegate.measurePassDelegate.measuredByParent = LayoutNode.UsageByParent.NotUsed;
        }
        setMeasureResult$ui_release(layoutNode.measurePolicy.mo3measure3p2s80s(this, layoutNode.getChildMeasurables$ui_release(), j));
        onMeasured();
        return this;
    }

    @Override // androidx.compose.ui.layout.IntrinsicMeasurable
    public final int minIntrinsicHeight(int i) {
        IntrinsicsPolicy orCreateIntrinsicsPolicy = this.layoutNode.getOrCreateIntrinsicsPolicy();
        MeasurePolicy measurePolicyState = orCreateIntrinsicsPolicy.getMeasurePolicyState();
        LayoutNode layoutNode = orCreateIntrinsicsPolicy.layoutNode;
        return measurePolicyState.minIntrinsicHeight(layoutNode.nodes.outerCoordinator, layoutNode.getChildMeasurables$ui_release(), i);
    }

    @Override // androidx.compose.ui.layout.IntrinsicMeasurable
    public final int minIntrinsicWidth(int i) {
        IntrinsicsPolicy orCreateIntrinsicsPolicy = this.layoutNode.getOrCreateIntrinsicsPolicy();
        MeasurePolicy measurePolicyState = orCreateIntrinsicsPolicy.getMeasurePolicyState();
        LayoutNode layoutNode = orCreateIntrinsicsPolicy.layoutNode;
        return measurePolicyState.minIntrinsicWidth(layoutNode.nodes.outerCoordinator, layoutNode.getChildMeasurables$ui_release(), i);
    }

    @Override // androidx.compose.ui.node.NodeCoordinator
    public final void performDraw(Canvas canvas, GraphicsLayer graphicsLayer) {
        LayoutNode layoutNode = this.layoutNode;
        Owner ownerRequireOwner = LayoutNodeKt.requireOwner(layoutNode);
        MutableVector zSortedChildren = layoutNode.getZSortedChildren();
        Object[] objArr = zSortedChildren.content;
        int i = zSortedChildren.size;
        for (int i2 = 0; i2 < i; i2++) {
            LayoutNode layoutNode2 = (LayoutNode) objArr[i2];
            if (layoutNode2.isPlaced()) {
                layoutNode2.draw$ui_release(canvas, graphicsLayer);
            }
        }
        if (((AndroidComposeView) ownerRequireOwner).showLayoutBounds) {
            long j = this.measuredSize;
            canvas.drawRect(0.5f, 0.5f, ((int) (j >> 32)) - 0.5f, ((int) (j & 4294967295L)) - 0.5f, innerBoundsPaint);
        }
    }

    @Override // androidx.compose.ui.node.NodeCoordinator, androidx.compose.ui.layout.Placeable
    /* renamed from: placeAt-f8xVGno */
    public final void mo625placeAtf8xVGno(long j, float f, GraphicsLayer graphicsLayer) {
        super.mo625placeAtf8xVGno(j, f, graphicsLayer);
        if (this.isShallowPlacing) {
            return;
        }
        this.layoutNode.layoutDelegate.measurePassDelegate.onNodePlaced$ui_release();
    }

    @Override // androidx.compose.ui.node.NodeCoordinator, androidx.compose.ui.layout.Placeable
    /* renamed from: placeAt-f8xVGno */
    public final void mo611placeAtf8xVGno(long j, float f, Function1 function1) {
        super.mo611placeAtf8xVGno(j, f, function1);
        if (this.isShallowPlacing) {
            return;
        }
        this.layoutNode.layoutDelegate.measurePassDelegate.onNodePlaced$ui_release();
    }
}
