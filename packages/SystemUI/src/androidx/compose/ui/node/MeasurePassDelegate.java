package androidx.compose.ui.node;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.AlignmentLine;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.layout.PlaceableKt;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.LookaheadPassDelegate;
import androidx.compose.ui.node.Owner;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class MeasurePassDelegate extends Placeable implements Measurable, AlignmentLinesOwner, MotionReferencePlacementDelegate {
    public final MutableVector _childDelegates;
    public final LayoutNodeAlignmentLines alignmentLines;
    public boolean childDelegatesDirty;
    public boolean duringAlignmentLinesQuery;
    public boolean isPlaced;
    public boolean isPlacedByParent;
    public GraphicsLayer lastExplicitLayer;
    public Function1 lastLayerBlock;
    public long lastPosition;
    public float lastZIndex;
    public boolean layingOutChildren;
    public final Function0 layoutChildrenBlock;
    public final LayoutNodeLayoutDelegate layoutNodeLayoutDelegate;
    public boolean layoutPending;
    public boolean layoutPendingForAlignment;
    public boolean measurePending;
    public boolean measuredOnce;
    public boolean needsCoordinatesUpdate;
    public boolean onNodePlacedCalled;
    public Object parentData;
    public boolean parentDataDirty;
    public final Function0 performMeasureBlock;
    public long performMeasureConstraints;
    public final Function0 placeOuterCoordinatorBlock;
    public GraphicsLayer placeOuterCoordinatorLayer;
    public Function1 placeOuterCoordinatorLayerBlock;
    public long placeOuterCoordinatorPosition;
    public float placeOuterCoordinatorZIndex;
    public boolean placedOnce;
    public boolean relayoutWithoutParentInProgress;
    public float zIndex;
    public int previousPlaceOrder = Integer.MAX_VALUE;
    public int placeOrder = Integer.MAX_VALUE;
    public LayoutNode.UsageByParent measuredByParent = LayoutNode.UsageByParent.NotUsed;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[LayoutNode.LayoutState.values().length];
            try {
                iArr[LayoutNode.LayoutState.Measuring.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LayoutNode.LayoutState.LayingOut.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[LayoutNode.UsageByParent.values().length];
            try {
                iArr2[LayoutNode.UsageByParent.InMeasureBlock.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[LayoutNode.UsageByParent.InLayoutBlock.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public MeasurePassDelegate(LayoutNodeLayoutDelegate layoutNodeLayoutDelegate) {
        this.layoutNodeLayoutDelegate = layoutNodeLayoutDelegate;
        IntOffset.Companion.getClass();
        this.lastPosition = 0L;
        this.parentDataDirty = true;
        this.alignmentLines = new LayoutNodeAlignmentLines(this);
        this._childDelegates = new MutableVector(new MeasurePassDelegate[16], 0);
        this.childDelegatesDirty = true;
        this.performMeasureConstraints = ConstraintsKt.Constraints$default(0, 0, 0, 0, 15);
        this.performMeasureBlock = new Function0() { // from class: androidx.compose.ui.node.MeasurePassDelegate$performMeasureBlock$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                this.this$0.layoutNodeLayoutDelegate.getOuterCoordinator().mo610measureBRTryo0(this.this$0.performMeasureConstraints);
                return Unit.INSTANCE;
            }
        };
        this.layoutChildrenBlock = new Function0() { // from class: androidx.compose.ui.node.MeasurePassDelegate$layoutChildrenBlock$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                LayoutNodeLayoutDelegate layoutNodeLayoutDelegate2 = this.this$0.layoutNodeLayoutDelegate;
                layoutNodeLayoutDelegate2.nextChildPlaceOrder = 0;
                MutableVector mutableVector = layoutNodeLayoutDelegate2.layoutNode.get_children$ui_release();
                Object[] objArr = mutableVector.content;
                int i = mutableVector.size;
                for (int i2 = 0; i2 < i; i2++) {
                    MeasurePassDelegate measurePassDelegate = ((LayoutNode) objArr[i2]).layoutDelegate.measurePassDelegate;
                    measurePassDelegate.previousPlaceOrder = measurePassDelegate.placeOrder;
                    measurePassDelegate.placeOrder = Integer.MAX_VALUE;
                    measurePassDelegate.isPlacedByParent = false;
                    if (measurePassDelegate.measuredByParent == LayoutNode.UsageByParent.InLayoutBlock) {
                        measurePassDelegate.measuredByParent = LayoutNode.UsageByParent.NotUsed;
                    }
                }
                this.this$0.forEachChildAlignmentLinesOwner(new Function1() { // from class: androidx.compose.ui.node.MeasurePassDelegate$layoutChildrenBlock$1.1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        ((AlignmentLinesOwner) obj).getAlignmentLines().usedDuringParentLayout = false;
                        return Unit.INSTANCE;
                    }
                });
                this.this$0.getInnerCoordinator().getMeasureResult$ui_release().placeChildren();
                LayoutNode layoutNode = this.this$0.layoutNodeLayoutDelegate.layoutNode;
                MutableVector mutableVector2 = layoutNode.get_children$ui_release();
                Object[] objArr2 = mutableVector2.content;
                int i3 = mutableVector2.size;
                for (int i4 = 0; i4 < i3; i4++) {
                    LayoutNode layoutNode2 = (LayoutNode) objArr2[i4];
                    if (layoutNode2.layoutDelegate.measurePassDelegate.previousPlaceOrder != layoutNode2.getPlaceOrder$ui_release()) {
                        layoutNode.onZSortedChildrenInvalidated$ui_release();
                        layoutNode.invalidateLayer$ui_release();
                        if (layoutNode2.getPlaceOrder$ui_release() == Integer.MAX_VALUE) {
                            LayoutNodeLayoutDelegate layoutNodeLayoutDelegate3 = layoutNode2.layoutDelegate;
                            if (layoutNodeLayoutDelegate3.detachedFromParentLookaheadPlacement) {
                                LookaheadPassDelegate lookaheadPassDelegate = layoutNodeLayoutDelegate3.lookaheadPassDelegate;
                                lookaheadPassDelegate.getClass();
                                lookaheadPassDelegate.markNodeAndSubtreeAsNotPlaced$ui_release(false);
                            }
                            layoutNodeLayoutDelegate3.measurePassDelegate.markSubtreeAsNotPlaced();
                        }
                    }
                }
                this.this$0.forEachChildAlignmentLinesOwner(new Function1() { // from class: androidx.compose.ui.node.MeasurePassDelegate$layoutChildrenBlock$1.2
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        AlignmentLinesOwner alignmentLinesOwner = (AlignmentLinesOwner) obj;
                        alignmentLinesOwner.getAlignmentLines().previousUsedDuringParentLayout = alignmentLinesOwner.getAlignmentLines().usedDuringParentLayout;
                        return Unit.INSTANCE;
                    }
                });
                return Unit.INSTANCE;
            }
        };
        this.placeOuterCoordinatorPosition = 0L;
        this.placeOuterCoordinatorBlock = new Function0() { // from class: androidx.compose.ui.node.MeasurePassDelegate$placeOuterCoordinatorBlock$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Placeable.PlacementScope PlacementScope;
                NodeCoordinator nodeCoordinator = this.this$0.layoutNodeLayoutDelegate.getOuterCoordinator().wrappedBy;
                if (nodeCoordinator == null || (PlacementScope = nodeCoordinator.placementScope) == null) {
                    PlacementScope = PlaceableKt.PlacementScope((AndroidComposeView) LayoutNodeKt.requireOwner(this.this$0.layoutNodeLayoutDelegate.layoutNode));
                }
                MeasurePassDelegate measurePassDelegate = this.this$0;
                Function1 function1 = measurePassDelegate.placeOuterCoordinatorLayerBlock;
                GraphicsLayer graphicsLayer = measurePassDelegate.placeOuterCoordinatorLayer;
                LayoutNodeLayoutDelegate layoutNodeLayoutDelegate2 = measurePassDelegate.layoutNodeLayoutDelegate;
                if (graphicsLayer != null) {
                    NodeCoordinator outerCoordinator = layoutNodeLayoutDelegate2.getOuterCoordinator();
                    long j = measurePassDelegate.placeOuterCoordinatorPosition;
                    float f = measurePassDelegate.placeOuterCoordinatorZIndex;
                    Placeable.PlacementScope.access$handleMotionFrameOfReferencePlacement(PlacementScope, outerCoordinator);
                    outerCoordinator.mo625placeAtf8xVGno(IntOffset.m853plusqkQi6aY(j, outerCoordinator.apparentToRealOffset), f, graphicsLayer);
                } else if (function1 == null) {
                    NodeCoordinator outerCoordinator2 = layoutNodeLayoutDelegate2.getOuterCoordinator();
                    long j2 = measurePassDelegate.placeOuterCoordinatorPosition;
                    float f2 = measurePassDelegate.placeOuterCoordinatorZIndex;
                    Placeable.PlacementScope.access$handleMotionFrameOfReferencePlacement(PlacementScope, outerCoordinator2);
                    outerCoordinator2.mo611placeAtf8xVGno(IntOffset.m853plusqkQi6aY(j2, outerCoordinator2.apparentToRealOffset), f2, (Function1) null);
                } else {
                    NodeCoordinator outerCoordinator3 = layoutNodeLayoutDelegate2.getOuterCoordinator();
                    long j3 = measurePassDelegate.placeOuterCoordinatorPosition;
                    float f3 = measurePassDelegate.placeOuterCoordinatorZIndex;
                    Placeable.PlacementScope.access$handleMotionFrameOfReferencePlacement(PlacementScope, outerCoordinator3);
                    outerCoordinator3.mo611placeAtf8xVGno(IntOffset.m853plusqkQi6aY(j3, outerCoordinator3.apparentToRealOffset), f3, function1);
                }
                return Unit.INSTANCE;
            }
        };
    }

    @Override // androidx.compose.ui.node.AlignmentLinesOwner
    public final void forEachChildAlignmentLinesOwner(Function1 function1) {
        MutableVector mutableVector = this.layoutNodeLayoutDelegate.layoutNode.get_children$ui_release();
        Object[] objArr = mutableVector.content;
        int i = mutableVector.size;
        for (int i2 = 0; i2 < i; i2++) {
            function1.mo781invoke(((LayoutNode) objArr[i2]).layoutDelegate.measurePassDelegate);
        }
    }

    @Override // androidx.compose.ui.layout.Measured
    public final int get(AlignmentLine alignmentLine) {
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutNodeLayoutDelegate;
        LayoutNode parent$ui_release = layoutNodeLayoutDelegate.layoutNode.getParent$ui_release();
        LayoutNode.LayoutState layoutState = parent$ui_release != null ? parent$ui_release.layoutDelegate.layoutState : null;
        LayoutNode.LayoutState layoutState2 = LayoutNode.LayoutState.Measuring;
        LayoutNodeAlignmentLines layoutNodeAlignmentLines = this.alignmentLines;
        if (layoutState == layoutState2) {
            layoutNodeAlignmentLines.usedDuringParentMeasurement = true;
        } else {
            LayoutNode parent$ui_release2 = layoutNodeLayoutDelegate.layoutNode.getParent$ui_release();
            if ((parent$ui_release2 != null ? parent$ui_release2.layoutDelegate.layoutState : null) == LayoutNode.LayoutState.LayingOut) {
                layoutNodeAlignmentLines.usedDuringParentLayout = true;
            }
        }
        this.duringAlignmentLinesQuery = true;
        int i = layoutNodeLayoutDelegate.getOuterCoordinator().get(alignmentLine);
        this.duringAlignmentLinesQuery = false;
        return i;
    }

    @Override // androidx.compose.ui.node.AlignmentLinesOwner
    public final AlignmentLines getAlignmentLines() {
        return this.alignmentLines;
    }

    public final List getChildDelegates$ui_release() {
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutNodeLayoutDelegate;
        layoutNodeLayoutDelegate.layoutNode.updateChildrenIfDirty$ui_release();
        boolean z = this.childDelegatesDirty;
        MutableVector mutableVector = this._childDelegates;
        if (!z) {
            return mutableVector.asMutableList();
        }
        LayoutNode layoutNode = layoutNodeLayoutDelegate.layoutNode;
        MutableVector mutableVector2 = layoutNode.get_children$ui_release();
        Object[] objArr = mutableVector2.content;
        int i = mutableVector2.size;
        for (int i2 = 0; i2 < i; i2++) {
            LayoutNode layoutNode2 = (LayoutNode) objArr[i2];
            if (mutableVector.size <= i2) {
                mutableVector.add(layoutNode2.layoutDelegate.measurePassDelegate);
            } else {
                MeasurePassDelegate measurePassDelegate = layoutNode2.layoutDelegate.measurePassDelegate;
                Object[] objArr2 = mutableVector.content;
                Object obj = objArr2[i2];
                objArr2[i2] = measurePassDelegate;
            }
        }
        mutableVector.removeRange(layoutNode.getChildren$ui_release().size(), mutableVector.size);
        this.childDelegatesDirty = false;
        return mutableVector.asMutableList();
    }

    @Override // androidx.compose.ui.node.AlignmentLinesOwner
    public final InnerNodeCoordinator getInnerCoordinator() {
        return this.layoutNodeLayoutDelegate.layoutNode.nodes.innerCoordinator;
    }

    @Override // androidx.compose.ui.layout.Placeable
    public final int getMeasuredHeight() {
        return this.layoutNodeLayoutDelegate.getOuterCoordinator().getMeasuredHeight();
    }

    @Override // androidx.compose.ui.layout.Placeable
    public final int getMeasuredWidth() {
        return this.layoutNodeLayoutDelegate.getOuterCoordinator().getMeasuredWidth();
    }

    @Override // androidx.compose.ui.node.AlignmentLinesOwner
    public final AlignmentLinesOwner getParentAlignmentLinesOwner() {
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate;
        LayoutNode parent$ui_release = this.layoutNodeLayoutDelegate.layoutNode.getParent$ui_release();
        if (parent$ui_release == null || (layoutNodeLayoutDelegate = parent$ui_release.layoutDelegate) == null) {
            return null;
        }
        return layoutNodeLayoutDelegate.measurePassDelegate;
    }

    @Override // androidx.compose.ui.layout.Measured, androidx.compose.ui.layout.IntrinsicMeasurable
    public final Object getParentData() {
        return this.parentData;
    }

    @Override // androidx.compose.ui.node.AlignmentLinesOwner
    public final boolean isPlaced() {
        return this.isPlaced;
    }

    @Override // androidx.compose.ui.node.AlignmentLinesOwner
    public final void layoutChildren() {
        this.layingOutChildren = true;
        LayoutNodeAlignmentLines layoutNodeAlignmentLines = this.alignmentLines;
        layoutNodeAlignmentLines.recalculateQueryOwner();
        boolean z = this.layoutPending;
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutNodeLayoutDelegate;
        if (z) {
            MutableVector mutableVector = layoutNodeLayoutDelegate.layoutNode.get_children$ui_release();
            Object[] objArr = mutableVector.content;
            int i = mutableVector.size;
            for (int i2 = 0; i2 < i; i2++) {
                LayoutNode layoutNode = (LayoutNode) objArr[i2];
                if (layoutNode.getMeasurePending$ui_release() && layoutNode.getMeasuredByParent$ui_release() == LayoutNode.UsageByParent.InMeasureBlock && LayoutNode.m641remeasure_Sx5XlM$ui_release$default(layoutNode)) {
                    LayoutNode.requestRemeasure$ui_release$default(layoutNodeLayoutDelegate.layoutNode, false, 7);
                }
            }
        }
        if (this.layoutPendingForAlignment || (!this.duringAlignmentLinesQuery && !getInnerCoordinator().isPlacingForAlignment && this.layoutPending)) {
            this.layoutPending = false;
            LayoutNode.LayoutState layoutState = layoutNodeLayoutDelegate.layoutState;
            layoutNodeLayoutDelegate.layoutState = LayoutNode.LayoutState.LayingOut;
            layoutNodeLayoutDelegate.setCoordinatesAccessedDuringPlacement(false);
            LayoutNode layoutNode2 = layoutNodeLayoutDelegate.layoutNode;
            OwnerSnapshotObserver ownerSnapshotObserver = ((AndroidComposeView) LayoutNodeKt.requireOwner(layoutNode2)).snapshotObserver;
            ownerSnapshotObserver.observeReads$ui_release(layoutNode2, ownerSnapshotObserver.onCommitAffectingLayout, this.layoutChildrenBlock);
            layoutNodeLayoutDelegate.layoutState = layoutState;
            if (getInnerCoordinator().isPlacingForAlignment && layoutNodeLayoutDelegate.coordinatesAccessedDuringPlacement) {
                requestLayout();
            }
            this.layoutPendingForAlignment = false;
        }
        if (layoutNodeAlignmentLines.usedDuringParentLayout) {
            layoutNodeAlignmentLines.previousUsedDuringParentLayout = true;
        }
        if (layoutNodeAlignmentLines.dirty && layoutNodeAlignmentLines.getRequired$ui_release()) {
            layoutNodeAlignmentLines.recalculate();
        }
        this.layingOutChildren = false;
    }

    public final void markNodeAndSubtreeAsPlaced$1() {
        boolean z = this.isPlaced;
        this.isPlaced = true;
        LayoutNode layoutNode = this.layoutNodeLayoutDelegate.layoutNode;
        if (!z) {
            layoutNode.nodes.innerCoordinator.onPlaced();
            if (layoutNode.getMeasurePending$ui_release()) {
                LayoutNode.requestRemeasure$ui_release$default(layoutNode, true, 6);
            } else if (layoutNode.layoutDelegate.lookaheadMeasurePending) {
                LayoutNode.requestLookaheadRemeasure$ui_release$default(layoutNode, true, 6);
            }
        }
        NodeChain nodeChain = layoutNode.nodes;
        NodeCoordinator nodeCoordinator = nodeChain.innerCoordinator.wrapped;
        for (NodeCoordinator nodeCoordinator2 = nodeChain.outerCoordinator; !Intrinsics.areEqual(nodeCoordinator2, nodeCoordinator) && nodeCoordinator2 != null; nodeCoordinator2 = nodeCoordinator2.wrapped) {
            if (nodeCoordinator2.lastLayerDrawingWasSkipped) {
                nodeCoordinator2.invalidateLayer();
            }
        }
        MutableVector mutableVector = layoutNode.get_children$ui_release();
        Object[] objArr = mutableVector.content;
        int i = mutableVector.size;
        for (int i2 = 0; i2 < i; i2++) {
            LayoutNode layoutNode2 = (LayoutNode) objArr[i2];
            if (layoutNode2.getPlaceOrder$ui_release() != Integer.MAX_VALUE) {
                layoutNode2.layoutDelegate.measurePassDelegate.markNodeAndSubtreeAsPlaced$1();
                LayoutNode.rescheduleRemeasureOrRelayout$ui_release(layoutNode2);
            }
        }
    }

    public final void markSubtreeAsNotPlaced() {
        if (this.isPlaced) {
            this.isPlaced = false;
            LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutNodeLayoutDelegate;
            NodeChain nodeChain = layoutNodeLayoutDelegate.layoutNode.nodes;
            NodeCoordinator nodeCoordinator = nodeChain.innerCoordinator.wrapped;
            for (NodeCoordinator nodeCoordinator2 = nodeChain.outerCoordinator; !Intrinsics.areEqual(nodeCoordinator2, nodeCoordinator) && nodeCoordinator2 != null; nodeCoordinator2 = nodeCoordinator2.wrapped) {
                Modifier.Node nodeHeadNode = nodeCoordinator2.headNode(NodeKindKt.m683getIncludeSelfInTraversalH91voCI(1048576));
                if (nodeHeadNode != null && (nodeHeadNode.node.aggregateChildKindSet & 1048576) != 0) {
                    boolean zM683getIncludeSelfInTraversalH91voCI = NodeKindKt.m683getIncludeSelfInTraversalH91voCI(1048576);
                    Modifier.Node tail = nodeCoordinator2.getTail();
                    if (zM683getIncludeSelfInTraversalH91voCI || (tail = tail.parent) != null) {
                        for (Modifier.Node nodeHeadNode2 = nodeCoordinator2.headNode(zM683getIncludeSelfInTraversalH91voCI); nodeHeadNode2 != null && (nodeHeadNode2.aggregateChildKindSet & 1048576) != 0; nodeHeadNode2 = nodeHeadNode2.child) {
                            if ((nodeHeadNode2.kindSet & 1048576) != 0) {
                                Modifier.Node nodeAccess$pop = nodeHeadNode2;
                                MutableVector mutableVector = null;
                                while (nodeAccess$pop != null) {
                                    if ((nodeAccess$pop.kindSet & 1048576) != 0 && (nodeAccess$pop instanceof DelegatingNode)) {
                                        int i = 0;
                                        for (Modifier.Node node = ((DelegatingNode) nodeAccess$pop).delegate; node != null; node = node.child) {
                                            if ((node.kindSet & 1048576) != 0) {
                                                i++;
                                                if (i == 1) {
                                                    nodeAccess$pop = node;
                                                } else {
                                                    if (mutableVector == null) {
                                                        mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                                    }
                                                    if (nodeAccess$pop != null) {
                                                        mutableVector.add(nodeAccess$pop);
                                                        nodeAccess$pop = null;
                                                    }
                                                    mutableVector.add(node);
                                                }
                                            }
                                        }
                                        if (i == 1) {
                                        }
                                    }
                                    nodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector);
                                }
                            }
                            if (nodeHeadNode2 == tail) {
                                break;
                            }
                        }
                    }
                }
                if (nodeCoordinator2.layer != null) {
                    if (nodeCoordinator2.explicitLayer != null) {
                        nodeCoordinator2.explicitLayer = null;
                    }
                    nodeCoordinator2.updateLayerBlock(null, false);
                    nodeCoordinator2.layoutNode.requestRelayout$ui_release(false);
                }
            }
            MutableVector mutableVector2 = layoutNodeLayoutDelegate.layoutNode.get_children$ui_release();
            Object[] objArr = mutableVector2.content;
            int i2 = mutableVector2.size;
            for (int i3 = 0; i3 < i2; i3++) {
                ((LayoutNode) objArr[i3]).layoutDelegate.measurePassDelegate.markSubtreeAsNotPlaced();
            }
        }
    }

    @Override // androidx.compose.ui.layout.IntrinsicMeasurable
    public final int maxIntrinsicHeight(int i) {
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutNodeLayoutDelegate;
        if (!LayoutNodeLayoutDelegateKt.isOutMostLookaheadRoot(layoutNodeLayoutDelegate.layoutNode)) {
            onIntrinsicsQueried$1();
            return layoutNodeLayoutDelegate.getOuterCoordinator().maxIntrinsicHeight(i);
        }
        LookaheadPassDelegate lookaheadPassDelegate = layoutNodeLayoutDelegate.lookaheadPassDelegate;
        lookaheadPassDelegate.getClass();
        return lookaheadPassDelegate.maxIntrinsicHeight(i);
    }

    @Override // androidx.compose.ui.layout.IntrinsicMeasurable
    public final int maxIntrinsicWidth(int i) {
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutNodeLayoutDelegate;
        if (!LayoutNodeLayoutDelegateKt.isOutMostLookaheadRoot(layoutNodeLayoutDelegate.layoutNode)) {
            onIntrinsicsQueried$1();
            return layoutNodeLayoutDelegate.getOuterCoordinator().maxIntrinsicWidth(i);
        }
        LookaheadPassDelegate lookaheadPassDelegate = layoutNodeLayoutDelegate.lookaheadPassDelegate;
        lookaheadPassDelegate.getClass();
        return lookaheadPassDelegate.maxIntrinsicWidth(i);
    }

    @Override // androidx.compose.ui.layout.Measurable
    /* renamed from: measure-BRTryo0 */
    public final Placeable mo610measureBRTryo0(long j) throws Throwable {
        LayoutNode.UsageByParent usageByParent;
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutNodeLayoutDelegate;
        LayoutNode layoutNode = layoutNodeLayoutDelegate.layoutNode;
        LayoutNode.UsageByParent usageByParent2 = layoutNode.intrinsicsUsageByParent;
        LayoutNode.UsageByParent usageByParent3 = LayoutNode.UsageByParent.NotUsed;
        if (usageByParent2 == usageByParent3) {
            layoutNode.clearSubtreeIntrinsicsUsage$ui_release();
        }
        if (LayoutNodeLayoutDelegateKt.isOutMostLookaheadRoot(layoutNodeLayoutDelegate.layoutNode)) {
            LookaheadPassDelegate lookaheadPassDelegate = layoutNodeLayoutDelegate.lookaheadPassDelegate;
            lookaheadPassDelegate.getClass();
            lookaheadPassDelegate.measuredByParent = usageByParent3;
            lookaheadPassDelegate.mo610measureBRTryo0(j);
        }
        LayoutNode layoutNode2 = layoutNodeLayoutDelegate.layoutNode;
        LayoutNode parent$ui_release = layoutNode2.getParent$ui_release();
        if (parent$ui_release != null) {
            if (this.measuredByParent != usageByParent3 && !layoutNode2.canMultiMeasure) {
                InlineClassHelperKt.throwIllegalStateException("measure() may not be called multiple times on the same Measurable. If you want to get the content size of the Measurable before calculating the final constraints, please use methods like minIntrinsicWidth()/maxIntrinsicWidth() and minIntrinsicHeight()/maxIntrinsicHeight()");
            }
            LayoutNodeLayoutDelegate layoutNodeLayoutDelegate2 = parent$ui_release.layoutDelegate;
            int i = WhenMappings.$EnumSwitchMapping$0[layoutNodeLayoutDelegate2.layoutState.ordinal()];
            if (i == 1) {
                usageByParent = LayoutNode.UsageByParent.InMeasureBlock;
            } else {
                if (i != 2) {
                    throw new IllegalStateException("Measurable could be only measured from the parent's measure or layout block. Parents state is " + layoutNodeLayoutDelegate2.layoutState);
                }
                usageByParent = LayoutNode.UsageByParent.InLayoutBlock;
            }
            this.measuredByParent = usageByParent;
        } else {
            this.measuredByParent = usageByParent3;
        }
        m664remeasureBRTryo0(j);
        return this;
    }

    @Override // androidx.compose.ui.layout.IntrinsicMeasurable
    public final int minIntrinsicHeight(int i) {
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutNodeLayoutDelegate;
        if (!LayoutNodeLayoutDelegateKt.isOutMostLookaheadRoot(layoutNodeLayoutDelegate.layoutNode)) {
            onIntrinsicsQueried$1();
            return layoutNodeLayoutDelegate.getOuterCoordinator().minIntrinsicHeight(i);
        }
        LookaheadPassDelegate lookaheadPassDelegate = layoutNodeLayoutDelegate.lookaheadPassDelegate;
        lookaheadPassDelegate.getClass();
        return lookaheadPassDelegate.minIntrinsicHeight(i);
    }

    @Override // androidx.compose.ui.layout.IntrinsicMeasurable
    public final int minIntrinsicWidth(int i) {
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutNodeLayoutDelegate;
        if (!LayoutNodeLayoutDelegateKt.isOutMostLookaheadRoot(layoutNodeLayoutDelegate.layoutNode)) {
            onIntrinsicsQueried$1();
            return layoutNodeLayoutDelegate.getOuterCoordinator().minIntrinsicWidth(i);
        }
        LookaheadPassDelegate lookaheadPassDelegate = layoutNodeLayoutDelegate.lookaheadPassDelegate;
        lookaheadPassDelegate.getClass();
        return lookaheadPassDelegate.minIntrinsicWidth(i);
    }

    public final void notifyChildrenUsingCoordinatesWhilePlacing() {
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutNodeLayoutDelegate;
        if (layoutNodeLayoutDelegate.childrenAccessingCoordinatesDuringPlacement > 0) {
            MutableVector mutableVector = layoutNodeLayoutDelegate.layoutNode.get_children$ui_release();
            Object[] objArr = mutableVector.content;
            int i = mutableVector.size;
            for (int i2 = 0; i2 < i; i2++) {
                LayoutNode layoutNode = (LayoutNode) objArr[i2];
                LayoutNodeLayoutDelegate layoutNodeLayoutDelegate2 = layoutNode.layoutDelegate;
                boolean z = layoutNodeLayoutDelegate2.coordinatesAccessedDuringPlacement;
                MeasurePassDelegate measurePassDelegate = layoutNodeLayoutDelegate2.measurePassDelegate;
                if ((z || layoutNodeLayoutDelegate2.coordinatesAccessedDuringModifierPlacement) && !measurePassDelegate.layoutPending) {
                    layoutNode.requestRelayout$ui_release(false);
                }
                measurePassDelegate.notifyChildrenUsingCoordinatesWhilePlacing();
            }
        }
    }

    public final void onIntrinsicsQueried$1() {
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutNodeLayoutDelegate;
        LayoutNode.requestRemeasure$ui_release$default(layoutNodeLayoutDelegate.layoutNode, false, 7);
        LayoutNode layoutNode = layoutNodeLayoutDelegate.layoutNode;
        LayoutNode parent$ui_release = layoutNode.getParent$ui_release();
        if (parent$ui_release == null || layoutNode.intrinsicsUsageByParent != LayoutNode.UsageByParent.NotUsed) {
            return;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[parent$ui_release.layoutDelegate.layoutState.ordinal()];
        layoutNode.intrinsicsUsageByParent = i != 1 ? i != 2 ? parent$ui_release.intrinsicsUsageByParent : LayoutNode.UsageByParent.InLayoutBlock : LayoutNode.UsageByParent.InMeasureBlock;
    }

    public final void onNodePlaced$ui_release() {
        this.onNodePlacedCalled = true;
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutNodeLayoutDelegate;
        LayoutNode parent$ui_release = layoutNodeLayoutDelegate.layoutNode.getParent$ui_release();
        float f = getInnerCoordinator().zIndex;
        LayoutNode layoutNode = layoutNodeLayoutDelegate.layoutNode;
        NodeChain nodeChain = layoutNode.nodes;
        for (NodeCoordinator nodeCoordinator = nodeChain.outerCoordinator; nodeCoordinator != nodeChain.innerCoordinator; nodeCoordinator = nodeCoordinator.wrapped) {
            f += ((LayoutModifierNodeCoordinator) nodeCoordinator).zIndex;
        }
        if (f != this.zIndex) {
            this.zIndex = f;
            if (parent$ui_release != null) {
                parent$ui_release.onZSortedChildrenInvalidated$ui_release();
            }
            if (parent$ui_release != null) {
                parent$ui_release.invalidateLayer$ui_release();
            }
        }
        if (this.isPlaced) {
            layoutNode.nodes.innerCoordinator.onPlaced();
        } else {
            if (parent$ui_release != null) {
                parent$ui_release.invalidateLayer$ui_release();
            }
            markNodeAndSubtreeAsPlaced$1();
            if (this.relayoutWithoutParentInProgress && parent$ui_release != null) {
                parent$ui_release.requestRelayout$ui_release(false);
            }
        }
        if (parent$ui_release == null) {
            this.placeOrder = 0;
        } else if (!this.relayoutWithoutParentInProgress) {
            LayoutNodeLayoutDelegate layoutNodeLayoutDelegate2 = parent$ui_release.layoutDelegate;
            if (layoutNodeLayoutDelegate2.layoutState == LayoutNode.LayoutState.LayingOut) {
                if (this.placeOrder != Integer.MAX_VALUE) {
                    InlineClassHelperKt.throwIllegalStateException("Place was called on a node which was placed already");
                }
                int i = layoutNodeLayoutDelegate2.nextChildPlaceOrder;
                this.placeOrder = i;
                layoutNodeLayoutDelegate2.nextChildPlaceOrder = i + 1;
            }
        }
        layoutChildren();
    }

    /* renamed from: performMeasure-BRTryo0$ui_release, reason: not valid java name */
    public final void m661performMeasureBRTryo0$ui_release(long j) {
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutNodeLayoutDelegate;
        LayoutNode.LayoutState layoutState = layoutNodeLayoutDelegate.layoutState;
        LayoutNode.LayoutState layoutState2 = LayoutNode.LayoutState.Idle;
        if (layoutState != layoutState2) {
            InlineClassHelperKt.throwIllegalStateException("layout state is not idle before measure starts");
        }
        this.performMeasureConstraints = j;
        LayoutNode.LayoutState layoutState3 = LayoutNode.LayoutState.Measuring;
        layoutNodeLayoutDelegate.layoutState = layoutState3;
        this.measurePending = false;
        LayoutNode layoutNode = layoutNodeLayoutDelegate.layoutNode;
        OwnerSnapshotObserver ownerSnapshotObserver = ((AndroidComposeView) LayoutNodeKt.requireOwner(layoutNode)).snapshotObserver;
        ownerSnapshotObserver.observeReads$ui_release(layoutNode, ownerSnapshotObserver.onCommitAffectingMeasure, this.performMeasureBlock);
        if (layoutNodeLayoutDelegate.layoutState == layoutState3) {
            this.layoutPending = true;
            this.layoutPendingForAlignment = true;
            layoutNodeLayoutDelegate.layoutState = layoutState2;
        }
    }

    @Override // androidx.compose.ui.layout.Placeable
    /* renamed from: placeAt-f8xVGno */
    public final void mo611placeAtf8xVGno(long j, float f, Function1 function1) throws Throwable {
        m663placeSelfMLgxB_4$2(j, f, function1, null);
    }

    /* renamed from: placeOuterCoordinator-MLgxB_4, reason: not valid java name */
    public final void m662placeOuterCoordinatorMLgxB_4(long j, float f, Function1 function1, GraphicsLayer graphicsLayer) {
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutNodeLayoutDelegate;
        if (layoutNodeLayoutDelegate.layoutNode.isDeactivated) {
            InlineClassHelperKt.throwIllegalArgumentException("place is called on a deactivated node");
        }
        layoutNodeLayoutDelegate.layoutState = LayoutNode.LayoutState.LayingOut;
        boolean z = !this.placedOnce;
        this.lastPosition = j;
        this.lastZIndex = f;
        this.lastLayerBlock = function1;
        this.lastExplicitLayer = graphicsLayer;
        this.placedOnce = true;
        this.onNodePlacedCalled = false;
        LayoutNode layoutNode = layoutNodeLayoutDelegate.layoutNode;
        AndroidComposeView androidComposeView = (AndroidComposeView) LayoutNodeKt.requireOwner(layoutNode);
        androidComposeView.rectManager.m722onLayoutPositionChanged70tqf50(layoutNode, j, z);
        if (this.layoutPending || !this.isPlaced) {
            this.alignmentLines.usedByModifierLayout = false;
            layoutNodeLayoutDelegate.setCoordinatesAccessedDuringModifierPlacement(false);
            this.placeOuterCoordinatorLayerBlock = function1;
            this.placeOuterCoordinatorPosition = j;
            this.placeOuterCoordinatorZIndex = f;
            this.placeOuterCoordinatorLayer = graphicsLayer;
            OwnerSnapshotObserver ownerSnapshotObserver = androidComposeView.snapshotObserver;
            ownerSnapshotObserver.observeReads$ui_release(layoutNode, ownerSnapshotObserver.onCommitAffectingLayoutModifier, this.placeOuterCoordinatorBlock);
        } else {
            NodeCoordinator outerCoordinator = layoutNodeLayoutDelegate.getOuterCoordinator();
            outerCoordinator.m676placeSelfMLgxB_4(IntOffset.m853plusqkQi6aY(j, outerCoordinator.apparentToRealOffset), f, function1, graphicsLayer);
            onNodePlaced$ui_release();
        }
        layoutNodeLayoutDelegate.layoutState = LayoutNode.LayoutState.Idle;
    }

    /* renamed from: placeSelf-MLgxB_4$2, reason: not valid java name */
    public final void m663placeSelfMLgxB_4$2(long j, float f, Function1 function1, GraphicsLayer graphicsLayer) throws Throwable {
        boolean z;
        Placeable.PlacementScope PlacementScope;
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutNodeLayoutDelegate;
        LayoutNode layoutNode = layoutNodeLayoutDelegate.layoutNode;
        boolean z2 = true;
        try {
            this.isPlacedByParent = true;
            if (!IntOffset.m851equalsimpl0(j, this.lastPosition) || this.needsCoordinatesUpdate) {
                if (layoutNodeLayoutDelegate.coordinatesAccessedDuringModifierPlacement || layoutNodeLayoutDelegate.coordinatesAccessedDuringPlacement || this.needsCoordinatesUpdate) {
                    this.layoutPending = true;
                    this.needsCoordinatesUpdate = false;
                }
                notifyChildrenUsingCoordinatesWhilePlacing();
            }
            LookaheadPassDelegate lookaheadPassDelegate = layoutNodeLayoutDelegate.lookaheadPassDelegate;
            if (lookaheadPassDelegate != null) {
                LayoutNodeLayoutDelegate layoutNodeLayoutDelegate2 = lookaheadPassDelegate.layoutNodeLayoutDelegate;
                if (LayoutNodeLayoutDelegateKt.isOutMostLookaheadRoot(layoutNodeLayoutDelegate2.layoutNode)) {
                    z = true;
                } else {
                    if (lookaheadPassDelegate._placedState == LookaheadPassDelegate.PlacedState.IsNotPlaced && !layoutNodeLayoutDelegate2.detachedFromParentLookaheadPass) {
                        layoutNodeLayoutDelegate2.detachedFromParentLookaheadPlacement = true;
                    }
                    z = layoutNodeLayoutDelegate2.detachedFromParentLookaheadPlacement;
                }
                if (z) {
                    NodeCoordinator nodeCoordinator = layoutNodeLayoutDelegate.getOuterCoordinator().wrappedBy;
                    LayoutNode layoutNode2 = layoutNodeLayoutDelegate.layoutNode;
                    if (nodeCoordinator == null || (PlacementScope = nodeCoordinator.placementScope) == null) {
                        PlacementScope = PlaceableKt.PlacementScope((AndroidComposeView) LayoutNodeKt.requireOwner(layoutNode2));
                    }
                    LookaheadPassDelegate lookaheadPassDelegate2 = layoutNodeLayoutDelegate.lookaheadPassDelegate;
                    lookaheadPassDelegate2.getClass();
                    LayoutNode parent$ui_release = layoutNode2.getParent$ui_release();
                    if (parent$ui_release != null) {
                        parent$ui_release.layoutDelegate.nextChildLookaheadPlaceOrder = 0;
                    }
                    lookaheadPassDelegate2.placeOrder = Integer.MAX_VALUE;
                    PlacementScope.place(lookaheadPassDelegate2, (int) (j >> 32), (int) (4294967295L & j), 0.0f);
                }
            }
            LookaheadPassDelegate lookaheadPassDelegate3 = layoutNodeLayoutDelegate.lookaheadPassDelegate;
            if (lookaheadPassDelegate3 == null || lookaheadPassDelegate3.placedOnce) {
                z2 = false;
            }
            if (z2) {
                InlineClassHelperKt.throwIllegalStateException("Error: Placement happened before lookahead.");
            }
            m662placeOuterCoordinatorMLgxB_4(j, f, function1, graphicsLayer);
            Unit unit = Unit.INSTANCE;
        } catch (Throwable th) {
            layoutNode.rethrowWithComposeStackTrace(th);
            throw null;
        }
    }

    /* renamed from: remeasure-BRTryo0, reason: not valid java name */
    public final boolean m664remeasureBRTryo0(long j) throws Throwable {
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutNodeLayoutDelegate;
        LayoutNode layoutNode = layoutNodeLayoutDelegate.layoutNode;
        LayoutNode layoutNode2 = layoutNodeLayoutDelegate.layoutNode;
        try {
            if (layoutNode.isDeactivated) {
                InlineClassHelperKt.throwIllegalArgumentException("measure is called on a deactivated node");
            }
            Owner ownerRequireOwner = LayoutNodeKt.requireOwner(layoutNode2);
            LayoutNode parent$ui_release = layoutNode2.getParent$ui_release();
            boolean z = true;
            layoutNode2.canMultiMeasure = layoutNode2.canMultiMeasure || (parent$ui_release != null && parent$ui_release.canMultiMeasure);
            if (!layoutNode2.getMeasurePending$ui_release() && Constraints.m817equalsimpl0(this.measurementConstraints, j)) {
                Owner.Companion companion = Owner.Companion;
                ((AndroidComposeView) ownerRequireOwner).forceMeasureTheSubtree(layoutNode2, false);
                layoutNode2.resetSubtreeIntrinsicsUsage$ui_release();
                return false;
            }
            this.alignmentLines.usedByModifierMeasurement = false;
            forEachChildAlignmentLinesOwner(new Function1() { // from class: androidx.compose.ui.node.MeasurePassDelegate$remeasure$1$2
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    ((AlignmentLinesOwner) obj).getAlignmentLines().usedDuringParentMeasurement = false;
                    return Unit.INSTANCE;
                }
            });
            this.measuredOnce = true;
            long j2 = layoutNodeLayoutDelegate.getOuterCoordinator().measuredSize;
            m627setMeasurementConstraintsBRTryo0(j);
            m661performMeasureBRTryo0$ui_release(j);
            if (IntSize.m863equalsimpl0(layoutNodeLayoutDelegate.getOuterCoordinator().measuredSize, j2) && layoutNodeLayoutDelegate.getOuterCoordinator().width == this.width && layoutNodeLayoutDelegate.getOuterCoordinator().height == this.height) {
                z = false;
            }
            m626setMeasuredSizeozmzZPI((layoutNodeLayoutDelegate.getOuterCoordinator().height & 4294967295L) | (layoutNodeLayoutDelegate.getOuterCoordinator().width << 32));
            return z;
        } catch (Throwable th) {
            layoutNode.rethrowWithComposeStackTrace(th);
            throw null;
        }
    }

    @Override // androidx.compose.ui.node.AlignmentLinesOwner
    public final void requestLayout() {
        LayoutNode layoutNode = this.layoutNodeLayoutDelegate.layoutNode;
        LayoutNode.Companion companion = LayoutNode.Companion;
        layoutNode.requestRelayout$ui_release(false);
    }

    @Override // androidx.compose.ui.node.AlignmentLinesOwner
    public final void requestMeasure() {
        LayoutNode.requestRemeasure$ui_release$default(this.layoutNodeLayoutDelegate.layoutNode, false, 7);
    }

    @Override // androidx.compose.ui.node.MotionReferencePlacementDelegate
    public final void updatePlacedUnderMotionFrameOfReference(boolean z) {
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutNodeLayoutDelegate;
        if (z != layoutNodeLayoutDelegate.getOuterCoordinator().isPlacedUnderMotionFrameOfReference) {
            layoutNodeLayoutDelegate.getOuterCoordinator().isPlacedUnderMotionFrameOfReference = z;
            this.needsCoordinatesUpdate = true;
        }
    }

    @Override // androidx.compose.ui.layout.Placeable
    /* renamed from: placeAt-f8xVGno */
    public final void mo625placeAtf8xVGno(long j, float f, GraphicsLayer graphicsLayer) throws Throwable {
        m663placeSelfMLgxB_4$2(j, f, null, graphicsLayer);
    }
}
