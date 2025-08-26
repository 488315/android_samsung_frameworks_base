package androidx.compose.ui.node;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.AlignmentLine;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.layout.PlaceableKt;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import java.util.List;
import kotlin.Unit;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final class LookaheadPassDelegate extends Placeable implements Measurable, AlignmentLinesOwner, MotionReferencePlacementDelegate {
    public final MutableVector _childDelegates;
    public PlacedState _placedState;
    public final LookaheadAlignmentLines alignmentLines;
    public boolean childDelegatesDirty;
    public boolean duringAlignmentLinesQuery;
    public GraphicsLayer lastExplicitLayer;
    public Function1 lastLayerBlock;
    public long lastPosition;
    public boolean layingOutChildren;
    public final LayoutNodeLayoutDelegate layoutNodeLayoutDelegate;
    public Constraints lookaheadConstraints;
    public boolean measuredOnce;
    public boolean onNodePlacedCalled;
    public Object parentData;
    public boolean parentDataDirty;
    public boolean placedOnce;
    public boolean relayoutWithoutParentInProgress;
    public int previousPlaceOrder = Integer.MAX_VALUE;
    public int placeOrder = Integer.MAX_VALUE;
    public LayoutNode.UsageByParent measuredByParent = LayoutNode.UsageByParent.NotUsed;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    final class PlacedState {
        public static final /* synthetic */ PlacedState[] $VALUES;
        public static final PlacedState IsNotPlaced;
        public static final PlacedState IsPlacedInApproach;
        public static final PlacedState IsPlacedInLookahead;

        static {
            PlacedState placedState = new PlacedState("IsPlacedInLookahead", 0);
            IsPlacedInLookahead = placedState;
            PlacedState placedState2 = new PlacedState("IsPlacedInApproach", 1);
            IsPlacedInApproach = placedState2;
            PlacedState placedState3 = new PlacedState("IsNotPlaced", 2);
            IsNotPlaced = placedState3;
            PlacedState[] placedStateArr = {placedState, placedState2, placedState3};
            $VALUES = placedStateArr;
            EnumEntriesKt.enumEntries(placedStateArr);
        }

        private PlacedState(String str, int i) {
        }

        public static PlacedState valueOf(String str) {
            return (PlacedState) Enum.valueOf(PlacedState.class, str);
        }

        public static PlacedState[] values() {
            return (PlacedState[]) $VALUES.clone();
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[LayoutNode.LayoutState.values().length];
            try {
                iArr[LayoutNode.LayoutState.LookaheadMeasuring.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LayoutNode.LayoutState.Measuring.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[LayoutNode.LayoutState.LayingOut.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[LayoutNode.LayoutState.LookaheadLayingOut.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[LayoutNode.UsageByParent.values().length];
            try {
                iArr2[LayoutNode.UsageByParent.InMeasureBlock.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[LayoutNode.UsageByParent.InLayoutBlock.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public LookaheadPassDelegate(LayoutNodeLayoutDelegate layoutNodeLayoutDelegate) {
        this.layoutNodeLayoutDelegate = layoutNodeLayoutDelegate;
        IntOffset.Companion.getClass();
        this.lastPosition = 0L;
        this._placedState = PlacedState.IsNotPlaced;
        this.alignmentLines = new LookaheadAlignmentLines(this);
        this._childDelegates = new MutableVector(new LookaheadPassDelegate[16], 0);
        this.childDelegatesDirty = true;
        this.parentDataDirty = true;
        this.parentData = layoutNodeLayoutDelegate.measurePassDelegate.parentData;
    }

    @Override // androidx.compose.ui.node.AlignmentLinesOwner
    public final void forEachChildAlignmentLinesOwner(Function1 function1) {
        MutableVector mutableVector = this.layoutNodeLayoutDelegate.layoutNode.get_children$ui_release();
        Object[] objArr = mutableVector.content;
        int i = mutableVector.size;
        for (int i2 = 0; i2 < i; i2++) {
            LookaheadPassDelegate lookaheadPassDelegate = ((LayoutNode) objArr[i2]).layoutDelegate.lookaheadPassDelegate;
            lookaheadPassDelegate.getClass();
            function1.mo781invoke(lookaheadPassDelegate);
        }
    }

    @Override // androidx.compose.ui.layout.Measured
    public final int get(AlignmentLine alignmentLine) {
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutNodeLayoutDelegate;
        LayoutNode parent$ui_release = layoutNodeLayoutDelegate.layoutNode.getParent$ui_release();
        LayoutNode.LayoutState layoutState = parent$ui_release != null ? parent$ui_release.layoutDelegate.layoutState : null;
        LayoutNode.LayoutState layoutState2 = LayoutNode.LayoutState.LookaheadMeasuring;
        LookaheadAlignmentLines lookaheadAlignmentLines = this.alignmentLines;
        if (layoutState == layoutState2) {
            lookaheadAlignmentLines.usedDuringParentMeasurement = true;
        } else {
            LayoutNode parent$ui_release2 = layoutNodeLayoutDelegate.layoutNode.getParent$ui_release();
            if ((parent$ui_release2 != null ? parent$ui_release2.layoutDelegate.layoutState : null) == LayoutNode.LayoutState.LookaheadLayingOut) {
                lookaheadAlignmentLines.usedDuringParentLayout = true;
            }
        }
        this.duringAlignmentLinesQuery = true;
        LookaheadDelegate lookaheadDelegate = layoutNodeLayoutDelegate.getOuterCoordinator().getLookaheadDelegate();
        lookaheadDelegate.getClass();
        int i = lookaheadDelegate.get(alignmentLine);
        this.duringAlignmentLinesQuery = false;
        return i;
    }

    @Override // androidx.compose.ui.node.AlignmentLinesOwner
    public final AlignmentLines getAlignmentLines() {
        return this.alignmentLines;
    }

    @Override // androidx.compose.ui.node.AlignmentLinesOwner
    public final InnerNodeCoordinator getInnerCoordinator() {
        return this.layoutNodeLayoutDelegate.layoutNode.nodes.innerCoordinator;
    }

    @Override // androidx.compose.ui.layout.Placeable
    public final int getMeasuredHeight() {
        LookaheadDelegate lookaheadDelegate = this.layoutNodeLayoutDelegate.getOuterCoordinator().getLookaheadDelegate();
        lookaheadDelegate.getClass();
        return lookaheadDelegate.getMeasuredHeight();
    }

    @Override // androidx.compose.ui.layout.Placeable
    public final int getMeasuredWidth() {
        LookaheadDelegate lookaheadDelegate = this.layoutNodeLayoutDelegate.getOuterCoordinator().getLookaheadDelegate();
        lookaheadDelegate.getClass();
        return lookaheadDelegate.getMeasuredWidth();
    }

    @Override // androidx.compose.ui.node.AlignmentLinesOwner
    public final AlignmentLinesOwner getParentAlignmentLinesOwner() {
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate;
        LayoutNode parent$ui_release = this.layoutNodeLayoutDelegate.layoutNode.getParent$ui_release();
        if (parent$ui_release == null || (layoutNodeLayoutDelegate = parent$ui_release.layoutDelegate) == null) {
            return null;
        }
        return layoutNodeLayoutDelegate.lookaheadPassDelegate;
    }

    @Override // androidx.compose.ui.layout.Measured, androidx.compose.ui.layout.IntrinsicMeasurable
    public final Object getParentData() {
        return this.parentData;
    }

    @Override // androidx.compose.ui.node.AlignmentLinesOwner
    public final boolean isPlaced() {
        return this._placedState != PlacedState.IsNotPlaced;
    }

    @Override // androidx.compose.ui.node.AlignmentLinesOwner
    public final void layoutChildren() {
        this.layingOutChildren = true;
        LookaheadAlignmentLines lookaheadAlignmentLines = this.alignmentLines;
        lookaheadAlignmentLines.recalculateQueryOwner();
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutNodeLayoutDelegate;
        boolean z = layoutNodeLayoutDelegate.lookaheadLayoutPending;
        LayoutNode layoutNode = layoutNodeLayoutDelegate.layoutNode;
        if (z) {
            MutableVector mutableVector = layoutNode.get_children$ui_release();
            Object[] objArr = mutableVector.content;
            int i = mutableVector.size;
            for (int i2 = 0; i2 < i; i2++) {
                LayoutNode layoutNode2 = (LayoutNode) objArr[i2];
                if (layoutNode2.layoutDelegate.lookaheadMeasurePending && layoutNode2.getMeasuredByParentInLookahead$ui_release() == LayoutNode.UsageByParent.InMeasureBlock) {
                    LayoutNodeLayoutDelegate layoutNodeLayoutDelegate2 = layoutNode2.layoutDelegate;
                    LookaheadPassDelegate lookaheadPassDelegate = layoutNodeLayoutDelegate2.lookaheadPassDelegate;
                    lookaheadPassDelegate.getClass();
                    LookaheadPassDelegate lookaheadPassDelegate2 = layoutNodeLayoutDelegate2.lookaheadPassDelegate;
                    Constraints constraints = lookaheadPassDelegate2 != null ? lookaheadPassDelegate2.lookaheadConstraints : null;
                    constraints.getClass();
                    if (lookaheadPassDelegate.m656remeasureBRTryo0(constraints.value)) {
                        LayoutNode.requestLookaheadRemeasure$ui_release$default(layoutNode, false, 7);
                    }
                }
            }
        }
        final LookaheadDelegate lookaheadDelegate = getInnerCoordinator().lookaheadDelegate;
        lookaheadDelegate.getClass();
        if (layoutNodeLayoutDelegate.lookaheadLayoutPendingForAlignment || (!this.duringAlignmentLinesQuery && !lookaheadDelegate.isPlacingForAlignment && layoutNodeLayoutDelegate.lookaheadLayoutPending)) {
            layoutNodeLayoutDelegate.lookaheadLayoutPending = false;
            LayoutNode.LayoutState layoutState = layoutNodeLayoutDelegate.layoutState;
            layoutNodeLayoutDelegate.layoutState = LayoutNode.LayoutState.LookaheadLayingOut;
            Owner ownerRequireOwner = LayoutNodeKt.requireOwner(layoutNode);
            layoutNodeLayoutDelegate.setLookaheadCoordinatesAccessedDuringPlacement(false);
            OwnerSnapshotObserver ownerSnapshotObserver = ((AndroidComposeView) ownerRequireOwner).snapshotObserver;
            Function0 function0 = new Function0() { // from class: androidx.compose.ui.node.LookaheadPassDelegate.layoutChildren.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    LayoutNodeLayoutDelegate layoutNodeLayoutDelegate3 = LookaheadPassDelegate.this.layoutNodeLayoutDelegate;
                    layoutNodeLayoutDelegate3.nextChildLookaheadPlaceOrder = 0;
                    MutableVector mutableVector2 = layoutNodeLayoutDelegate3.layoutNode.get_children$ui_release();
                    Object[] objArr2 = mutableVector2.content;
                    int i3 = mutableVector2.size;
                    for (int i4 = 0; i4 < i3; i4++) {
                        LookaheadPassDelegate lookaheadPassDelegate3 = ((LayoutNode) objArr2[i4]).layoutDelegate.lookaheadPassDelegate;
                        lookaheadPassDelegate3.getClass();
                        lookaheadPassDelegate3.previousPlaceOrder = lookaheadPassDelegate3.placeOrder;
                        lookaheadPassDelegate3.placeOrder = Integer.MAX_VALUE;
                        if (lookaheadPassDelegate3.measuredByParent == LayoutNode.UsageByParent.InLayoutBlock) {
                            lookaheadPassDelegate3.measuredByParent = LayoutNode.UsageByParent.NotUsed;
                        }
                    }
                    LookaheadPassDelegate.this.forEachChildAlignmentLinesOwner(new Function1() { // from class: androidx.compose.ui.node.LookaheadPassDelegate.layoutChildren.1.1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            ((AlignmentLinesOwner) obj).getAlignmentLines().usedDuringParentLayout = false;
                            return Unit.INSTANCE;
                        }
                    });
                    LookaheadDelegate lookaheadDelegate2 = LookaheadPassDelegate.this.getInnerCoordinator().lookaheadDelegate;
                    if (lookaheadDelegate2 != null) {
                        boolean z2 = lookaheadDelegate2.isPlacingForAlignment;
                        List children$ui_release = LookaheadPassDelegate.this.layoutNodeLayoutDelegate.layoutNode.getChildren$ui_release();
                        int size = children$ui_release.size();
                        for (int i5 = 0; i5 < size; i5++) {
                            LookaheadDelegate lookaheadDelegate3 = ((LayoutNode) children$ui_release.get(i5)).nodes.outerCoordinator.getLookaheadDelegate();
                            if (lookaheadDelegate3 != null) {
                                lookaheadDelegate3.isPlacingForAlignment = z2;
                            }
                        }
                    }
                    lookaheadDelegate.getMeasureResult$ui_release().placeChildren();
                    if (LookaheadPassDelegate.this.getInnerCoordinator().lookaheadDelegate != null) {
                        List children$ui_release2 = LookaheadPassDelegate.this.layoutNodeLayoutDelegate.layoutNode.getChildren$ui_release();
                        int size2 = children$ui_release2.size();
                        for (int i6 = 0; i6 < size2; i6++) {
                            LookaheadDelegate lookaheadDelegate4 = ((LayoutNode) children$ui_release2.get(i6)).nodes.outerCoordinator.getLookaheadDelegate();
                            if (lookaheadDelegate4 != null) {
                                lookaheadDelegate4.isPlacingForAlignment = false;
                            }
                        }
                    }
                    MutableVector mutableVector3 = LookaheadPassDelegate.this.layoutNodeLayoutDelegate.layoutNode.get_children$ui_release();
                    Object[] objArr3 = mutableVector3.content;
                    int i7 = mutableVector3.size;
                    for (int i8 = 0; i8 < i7; i8++) {
                        LookaheadPassDelegate lookaheadPassDelegate4 = ((LayoutNode) objArr3[i8]).layoutDelegate.lookaheadPassDelegate;
                        lookaheadPassDelegate4.getClass();
                        int i9 = lookaheadPassDelegate4.previousPlaceOrder;
                        int i10 = lookaheadPassDelegate4.placeOrder;
                        if (i9 != i10 && i10 == Integer.MAX_VALUE) {
                            lookaheadPassDelegate4.markNodeAndSubtreeAsNotPlaced$ui_release(true);
                        }
                    }
                    LookaheadPassDelegate.this.forEachChildAlignmentLinesOwner(new Function1() { // from class: androidx.compose.ui.node.LookaheadPassDelegate.layoutChildren.1.4
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
            ownerSnapshotObserver.getClass();
            if (layoutNode.lookaheadRoot != null) {
                ownerSnapshotObserver.observeReads$ui_release(layoutNode, ownerSnapshotObserver.onCommitAffectingLookahead, function0);
            } else {
                ownerSnapshotObserver.observeReads$ui_release(layoutNode, ownerSnapshotObserver.onCommitAffectingLayout, function0);
            }
            layoutNodeLayoutDelegate.layoutState = layoutState;
            if (layoutNodeLayoutDelegate.lookaheadCoordinatesAccessedDuringPlacement && lookaheadDelegate.isPlacingForAlignment) {
                requestLayout();
            }
            layoutNodeLayoutDelegate.lookaheadLayoutPendingForAlignment = false;
        }
        if (lookaheadAlignmentLines.usedDuringParentLayout) {
            lookaheadAlignmentLines.previousUsedDuringParentLayout = true;
        }
        if (lookaheadAlignmentLines.dirty && lookaheadAlignmentLines.getRequired$ui_release()) {
            lookaheadAlignmentLines.recalculate();
        }
        this.layingOutChildren = false;
    }

    public final void markNodeAndSubtreeAsNotPlaced$ui_release(boolean z) {
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutNodeLayoutDelegate;
        if (z && layoutNodeLayoutDelegate.detachedFromParentLookaheadPlacement) {
            return;
        }
        if (z || layoutNodeLayoutDelegate.detachedFromParentLookaheadPlacement) {
            this._placedState = PlacedState.IsNotPlaced;
            MutableVector mutableVector = layoutNodeLayoutDelegate.layoutNode.get_children$ui_release();
            Object[] objArr = mutableVector.content;
            int i = mutableVector.size;
            for (int i2 = 0; i2 < i; i2++) {
                LookaheadPassDelegate lookaheadPassDelegate = ((LayoutNode) objArr[i2]).layoutDelegate.lookaheadPassDelegate;
                lookaheadPassDelegate.getClass();
                lookaheadPassDelegate.markNodeAndSubtreeAsNotPlaced$ui_release(true);
            }
        }
    }

    public final void markNodeAndSubtreeAsPlaced() {
        PlacedState placedState = this._placedState;
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutNodeLayoutDelegate;
        if (layoutNodeLayoutDelegate.detachedFromParentLookaheadPlacement) {
            this._placedState = PlacedState.IsPlacedInApproach;
        } else {
            this._placedState = PlacedState.IsPlacedInLookahead;
        }
        PlacedState placedState2 = PlacedState.IsPlacedInLookahead;
        LayoutNode layoutNode = layoutNodeLayoutDelegate.layoutNode;
        if (placedState != placedState2 && layoutNodeLayoutDelegate.lookaheadMeasurePending) {
            LayoutNode.requestLookaheadRemeasure$ui_release$default(layoutNode, true, 6);
        }
        MutableVector mutableVector = layoutNode.get_children$ui_release();
        Object[] objArr = mutableVector.content;
        int i = mutableVector.size;
        for (int i2 = 0; i2 < i; i2++) {
            LayoutNode layoutNode2 = (LayoutNode) objArr[i2];
            LookaheadPassDelegate lookaheadPassDelegate = layoutNode2.layoutDelegate.lookaheadPassDelegate;
            if (lookaheadPassDelegate == null) {
                throw new IllegalArgumentException("Error: Child node's lookahead pass delegate cannot be null when in a lookahead scope.");
            }
            if (lookaheadPassDelegate.placeOrder != Integer.MAX_VALUE) {
                lookaheadPassDelegate.markNodeAndSubtreeAsPlaced();
                LayoutNode.rescheduleRemeasureOrRelayout$ui_release(layoutNode2);
            }
        }
    }

    @Override // androidx.compose.ui.layout.IntrinsicMeasurable
    public final int maxIntrinsicHeight(int i) {
        onIntrinsicsQueried();
        LookaheadDelegate lookaheadDelegate = this.layoutNodeLayoutDelegate.getOuterCoordinator().getLookaheadDelegate();
        lookaheadDelegate.getClass();
        return lookaheadDelegate.maxIntrinsicHeight(i);
    }

    @Override // androidx.compose.ui.layout.IntrinsicMeasurable
    public final int maxIntrinsicWidth(int i) {
        onIntrinsicsQueried();
        LookaheadDelegate lookaheadDelegate = this.layoutNodeLayoutDelegate.getOuterCoordinator().getLookaheadDelegate();
        lookaheadDelegate.getClass();
        return lookaheadDelegate.maxIntrinsicWidth(i);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0025  */
    @Override // androidx.compose.ui.layout.Measurable
    /* renamed from: measure-BRTryo0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Placeable mo610measureBRTryo0(long j) {
        LayoutNode.UsageByParent usageByParent;
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutNodeLayoutDelegate;
        LayoutNode parent$ui_release = layoutNodeLayoutDelegate.layoutNode.getParent$ui_release();
        if ((parent$ui_release != null ? parent$ui_release.layoutDelegate.layoutState : null) == LayoutNode.LayoutState.LookaheadMeasuring) {
            layoutNodeLayoutDelegate.detachedFromParentLookaheadPass = false;
        } else {
            LayoutNode parent$ui_release2 = layoutNodeLayoutDelegate.layoutNode.getParent$ui_release();
            if ((parent$ui_release2 != null ? parent$ui_release2.layoutDelegate.layoutState : null) == LayoutNode.LayoutState.LookaheadLayingOut) {
            }
        }
        LayoutNode layoutNode = layoutNodeLayoutDelegate.layoutNode;
        LayoutNode parent$ui_release3 = layoutNode.getParent$ui_release();
        if (parent$ui_release3 != null) {
            if (this.measuredByParent != LayoutNode.UsageByParent.NotUsed && !layoutNode.canMultiMeasure) {
                InlineClassHelperKt.throwIllegalStateException("measure() may not be called multiple times on the same Measurable. If you want to get the content size of the Measurable before calculating the final constraints, please use methods like minIntrinsicWidth()/maxIntrinsicWidth() and minIntrinsicHeight()/maxIntrinsicHeight()");
            }
            LayoutNodeLayoutDelegate layoutNodeLayoutDelegate2 = parent$ui_release3.layoutDelegate;
            int i = WhenMappings.$EnumSwitchMapping$0[layoutNodeLayoutDelegate2.layoutState.ordinal()];
            if (i == 1 || i == 2) {
                usageByParent = LayoutNode.UsageByParent.InMeasureBlock;
            } else {
                if (i != 3 && i != 4) {
                    throw new IllegalStateException("Measurable could be only measured from the parent's measure or layout block. Parents state is " + layoutNodeLayoutDelegate2.layoutState);
                }
                usageByParent = LayoutNode.UsageByParent.InLayoutBlock;
            }
            this.measuredByParent = usageByParent;
        } else {
            this.measuredByParent = LayoutNode.UsageByParent.NotUsed;
        }
        LayoutNode layoutNode2 = layoutNodeLayoutDelegate.layoutNode;
        if (layoutNode2.intrinsicsUsageByParent == LayoutNode.UsageByParent.NotUsed) {
            layoutNode2.clearSubtreeIntrinsicsUsage$ui_release();
        }
        m656remeasureBRTryo0(j);
        return this;
    }

    @Override // androidx.compose.ui.layout.IntrinsicMeasurable
    public final int minIntrinsicHeight(int i) {
        onIntrinsicsQueried();
        LookaheadDelegate lookaheadDelegate = this.layoutNodeLayoutDelegate.getOuterCoordinator().getLookaheadDelegate();
        lookaheadDelegate.getClass();
        return lookaheadDelegate.minIntrinsicHeight(i);
    }

    @Override // androidx.compose.ui.layout.IntrinsicMeasurable
    public final int minIntrinsicWidth(int i) {
        onIntrinsicsQueried();
        LookaheadDelegate lookaheadDelegate = this.layoutNodeLayoutDelegate.getOuterCoordinator().getLookaheadDelegate();
        lookaheadDelegate.getClass();
        return lookaheadDelegate.minIntrinsicWidth(i);
    }

    public final void notifyChildrenUsingLookaheadCoordinatesWhilePlacing() {
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutNodeLayoutDelegate;
        if (layoutNodeLayoutDelegate.childrenAccessingLookaheadCoordinatesDuringPlacement > 0) {
            MutableVector mutableVector = layoutNodeLayoutDelegate.layoutNode.get_children$ui_release();
            Object[] objArr = mutableVector.content;
            int i = mutableVector.size;
            for (int i2 = 0; i2 < i; i2++) {
                LayoutNode layoutNode = (LayoutNode) objArr[i2];
                LayoutNodeLayoutDelegate layoutNodeLayoutDelegate2 = layoutNode.layoutDelegate;
                if ((layoutNodeLayoutDelegate2.lookaheadCoordinatesAccessedDuringPlacement || layoutNodeLayoutDelegate2.lookaheadCoordinatesAccessedDuringModifierPlacement) && !layoutNodeLayoutDelegate2.lookaheadLayoutPending) {
                    layoutNode.requestLookaheadRelayout$ui_release(false);
                }
                LookaheadPassDelegate lookaheadPassDelegate = layoutNodeLayoutDelegate2.lookaheadPassDelegate;
                if (lookaheadPassDelegate != null) {
                    lookaheadPassDelegate.notifyChildrenUsingLookaheadCoordinatesWhilePlacing();
                }
            }
        }
    }

    public final void onIntrinsicsQueried() {
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutNodeLayoutDelegate;
        LayoutNode.requestLookaheadRemeasure$ui_release$default(layoutNodeLayoutDelegate.layoutNode, false, 7);
        LayoutNode layoutNode = layoutNodeLayoutDelegate.layoutNode;
        LayoutNode parent$ui_release = layoutNode.getParent$ui_release();
        if (parent$ui_release == null || layoutNode.intrinsicsUsageByParent != LayoutNode.UsageByParent.NotUsed) {
            return;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[parent$ui_release.layoutDelegate.layoutState.ordinal()];
        layoutNode.intrinsicsUsageByParent = i != 2 ? i != 3 ? parent$ui_release.intrinsicsUsageByParent : LayoutNode.UsageByParent.InLayoutBlock : LayoutNode.UsageByParent.InMeasureBlock;
    }

    public final void onNodePlaced$ui_release() {
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate;
        LayoutNode.LayoutState layoutState;
        this.onNodePlacedCalled = true;
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate2 = this.layoutNodeLayoutDelegate;
        LayoutNode parent$ui_release = layoutNodeLayoutDelegate2.layoutNode.getParent$ui_release();
        PlacedState placedState = this._placedState;
        if ((placedState != PlacedState.IsPlacedInLookahead && !layoutNodeLayoutDelegate2.detachedFromParentLookaheadPlacement) || (placedState != PlacedState.IsPlacedInApproach && layoutNodeLayoutDelegate2.detachedFromParentLookaheadPlacement)) {
            markNodeAndSubtreeAsPlaced();
            if (this.relayoutWithoutParentInProgress && parent$ui_release != null) {
                parent$ui_release.requestLookaheadRelayout$ui_release(false);
            }
        }
        if (parent$ui_release == null) {
            this.placeOrder = 0;
        } else if (!this.relayoutWithoutParentInProgress && ((layoutState = (layoutNodeLayoutDelegate = parent$ui_release.layoutDelegate).layoutState) == LayoutNode.LayoutState.LayingOut || layoutState == LayoutNode.LayoutState.LookaheadLayingOut)) {
            if (this.placeOrder != Integer.MAX_VALUE) {
                InlineClassHelperKt.throwIllegalStateException("Place was called on a node which was placed already");
            }
            int i = layoutNodeLayoutDelegate.nextChildLookaheadPlaceOrder;
            this.placeOrder = i;
            layoutNodeLayoutDelegate.nextChildLookaheadPlaceOrder = i + 1;
        }
        layoutChildren();
    }

    @Override // androidx.compose.ui.layout.Placeable
    /* renamed from: placeAt-f8xVGno */
    public final void mo611placeAtf8xVGno(long j, float f, Function1 function1) throws Throwable {
        m655placeSelfMLgxB_4$1(j, null, function1);
    }

    /* renamed from: placeSelf-MLgxB_4$1, reason: not valid java name */
    public final void m655placeSelfMLgxB_4$1(final long j, GraphicsLayer graphicsLayer, Function1 function1) throws Throwable {
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutNodeLayoutDelegate;
        LayoutNode layoutNode = layoutNodeLayoutDelegate.layoutNode;
        LayoutNode layoutNode2 = layoutNodeLayoutDelegate.layoutNode;
        try {
            LayoutNode parent$ui_release = layoutNode.getParent$ui_release();
            LayoutNode.LayoutState layoutState = parent$ui_release != null ? parent$ui_release.layoutDelegate.layoutState : null;
            LayoutNode.LayoutState layoutState2 = LayoutNode.LayoutState.LookaheadLayingOut;
            if (layoutState == layoutState2) {
                layoutNodeLayoutDelegate.detachedFromParentLookaheadPlacement = false;
            }
            if (layoutNode2.isDeactivated) {
                InlineClassHelperKt.throwIllegalArgumentException("place is called on a deactivated node");
            }
            layoutNodeLayoutDelegate.layoutState = layoutState2;
            this.placedOnce = true;
            this.onNodePlacedCalled = false;
            if (!IntOffset.m851equalsimpl0(j, this.lastPosition)) {
                if (layoutNodeLayoutDelegate.lookaheadCoordinatesAccessedDuringModifierPlacement || layoutNodeLayoutDelegate.lookaheadCoordinatesAccessedDuringPlacement) {
                    layoutNodeLayoutDelegate.lookaheadLayoutPending = true;
                }
                notifyChildrenUsingLookaheadCoordinatesWhilePlacing();
            }
            final Owner ownerRequireOwner = LayoutNodeKt.requireOwner(layoutNode2);
            if (layoutNodeLayoutDelegate.lookaheadLayoutPending || !isPlaced()) {
                layoutNodeLayoutDelegate.setLookaheadCoordinatesAccessedDuringModifierPlacement(false);
                this.alignmentLines.usedByModifierLayout = false;
                OwnerSnapshotObserver ownerSnapshotObserver = ((AndroidComposeView) ownerRequireOwner).snapshotObserver;
                Function0 function0 = new Function0() { // from class: androidx.compose.ui.node.LookaheadPassDelegate$placeSelf$1$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    /* JADX WARN: Removed duplicated region for block: B:11:0x0026  */
                    @Override // kotlin.jvm.functions.Function0
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke() {
                        LookaheadDelegate lookaheadDelegate;
                        Placeable.PlacementScope PlacementScope = null;
                        if (!LayoutNodeLayoutDelegateKt.isOutMostLookaheadRoot(this.this$0.layoutNodeLayoutDelegate.layoutNode)) {
                            LayoutNodeLayoutDelegate layoutNodeLayoutDelegate2 = this.this$0.layoutNodeLayoutDelegate;
                            if (layoutNodeLayoutDelegate2.detachedFromParentLookaheadPlacement) {
                                NodeCoordinator nodeCoordinator = this.this$0.layoutNodeLayoutDelegate.getOuterCoordinator().wrappedBy;
                                if (nodeCoordinator != null) {
                                    PlacementScope = nodeCoordinator.placementScope;
                                }
                            } else {
                                NodeCoordinator nodeCoordinator2 = layoutNodeLayoutDelegate2.getOuterCoordinator().wrappedBy;
                                if (nodeCoordinator2 != null && (lookaheadDelegate = nodeCoordinator2.getLookaheadDelegate()) != null) {
                                    PlacementScope = lookaheadDelegate.placementScope;
                                }
                            }
                        }
                        if (PlacementScope == null) {
                            AndroidComposeView androidComposeView = (AndroidComposeView) ownerRequireOwner;
                            androidComposeView.getClass();
                            PlacementScope = PlaceableKt.PlacementScope(androidComposeView);
                        }
                        LookaheadPassDelegate lookaheadPassDelegate = this.this$0;
                        long j2 = j;
                        LookaheadDelegate lookaheadDelegate2 = lookaheadPassDelegate.layoutNodeLayoutDelegate.getOuterCoordinator().getLookaheadDelegate();
                        lookaheadDelegate2.getClass();
                        Placeable.PlacementScope.m628place70tqf50$default(PlacementScope, lookaheadDelegate2, j2);
                        return Unit.INSTANCE;
                    }
                };
                ownerSnapshotObserver.getClass();
                if (layoutNode2.lookaheadRoot != null) {
                    ownerSnapshotObserver.observeReads$ui_release(layoutNode2, ownerSnapshotObserver.onCommitAffectingLayoutModifierInLookahead, function0);
                } else {
                    ownerSnapshotObserver.observeReads$ui_release(layoutNode2, ownerSnapshotObserver.onCommitAffectingLayoutModifier, function0);
                }
            } else {
                LookaheadDelegate lookaheadDelegate = layoutNodeLayoutDelegate.getOuterCoordinator().getLookaheadDelegate();
                lookaheadDelegate.getClass();
                lookaheadDelegate.m653placeSelfgyyYBs(IntOffset.m853plusqkQi6aY(j, lookaheadDelegate.apparentToRealOffset));
                onNodePlaced$ui_release();
            }
            this.lastPosition = j;
            this.lastLayerBlock = function1;
            this.lastExplicitLayer = graphicsLayer;
            layoutNodeLayoutDelegate.layoutState = LayoutNode.LayoutState.Idle;
            Unit unit = Unit.INSTANCE;
        } catch (Throwable th) {
            layoutNode.rethrowWithComposeStackTrace(th);
            throw null;
        }
    }

    /* renamed from: remeasure-BRTryo0, reason: not valid java name */
    public final boolean m656remeasureBRTryo0(long j) throws Throwable {
        long j2;
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutNodeLayoutDelegate;
        LayoutNode layoutNode = layoutNodeLayoutDelegate.layoutNode;
        LayoutNode layoutNode2 = layoutNodeLayoutDelegate.layoutNode;
        try {
            if (layoutNode.isDeactivated) {
                InlineClassHelperKt.throwIllegalArgumentException("measure is called on a deactivated node");
            }
            LayoutNode parent$ui_release = layoutNode2.getParent$ui_release();
            layoutNode2.canMultiMeasure = layoutNode2.canMultiMeasure || (parent$ui_release != null && parent$ui_release.canMultiMeasure);
            if (!layoutNode2.layoutDelegate.lookaheadMeasurePending) {
                Constraints constraints = this.lookaheadConstraints;
                if (constraints == null ? false : Constraints.m817equalsimpl0(constraints.value, j)) {
                    AndroidComposeView androidComposeView = layoutNode2.owner;
                    if (androidComposeView != null) {
                        androidComposeView.forceMeasureTheSubtree(layoutNode2, true);
                    }
                    layoutNode2.resetSubtreeIntrinsicsUsage$ui_release();
                    return false;
                }
            }
            this.lookaheadConstraints = Constraints.m815boximpl(j);
            m627setMeasurementConstraintsBRTryo0(j);
            this.alignmentLines.usedByModifierMeasurement = false;
            forEachChildAlignmentLinesOwner(new Function1() { // from class: androidx.compose.ui.node.LookaheadPassDelegate$remeasure$1$2
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    ((AlignmentLinesOwner) obj).getAlignmentLines().usedDuringParentMeasurement = false;
                    return Unit.INSTANCE;
                }
            });
            if (this.measuredOnce) {
                j2 = this.measuredSize;
            } else {
                long j3 = Integer.MIN_VALUE;
                j2 = (j3 & 4294967295L) | (j3 << 32);
                IntSize.Companion companion = IntSize.Companion;
            }
            this.measuredOnce = true;
            LookaheadDelegate lookaheadDelegate = layoutNodeLayoutDelegate.getOuterCoordinator().getLookaheadDelegate();
            if (!(lookaheadDelegate != null)) {
                InlineClassHelperKt.throwIllegalStateException("Lookahead result from lookaheadRemeasure cannot be null");
            }
            layoutNodeLayoutDelegate.m650performLookaheadMeasureBRTryo0$ui_release(j);
            IntSize.Companion companion2 = IntSize.Companion;
            m626setMeasuredSizeozmzZPI((lookaheadDelegate.height & 4294967295L) | (lookaheadDelegate.width << 32));
            return (((int) (j2 >> 32)) == lookaheadDelegate.width && ((int) (j2 & 4294967295L)) == lookaheadDelegate.height) ? false : true;
        } catch (Throwable th) {
            layoutNode.rethrowWithComposeStackTrace(th);
            throw null;
        }
    }

    @Override // androidx.compose.ui.node.AlignmentLinesOwner
    public final void requestLayout() {
        LayoutNode layoutNode = this.layoutNodeLayoutDelegate.layoutNode;
        LayoutNode.Companion companion = LayoutNode.Companion;
        layoutNode.requestLookaheadRelayout$ui_release(false);
    }

    @Override // androidx.compose.ui.node.AlignmentLinesOwner
    public final void requestMeasure() {
        LayoutNode.requestLookaheadRemeasure$ui_release$default(this.layoutNodeLayoutDelegate.layoutNode, false, 7);
    }

    @Override // androidx.compose.ui.node.MotionReferencePlacementDelegate
    public final void updatePlacedUnderMotionFrameOfReference(boolean z) {
        LookaheadDelegate lookaheadDelegate;
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = this.layoutNodeLayoutDelegate;
        LookaheadDelegate lookaheadDelegate2 = layoutNodeLayoutDelegate.getOuterCoordinator().getLookaheadDelegate();
        if (Boolean.valueOf(z).equals(lookaheadDelegate2 != null ? Boolean.valueOf(lookaheadDelegate2.isPlacedUnderMotionFrameOfReference) : null) || (lookaheadDelegate = layoutNodeLayoutDelegate.getOuterCoordinator().getLookaheadDelegate()) == null) {
            return;
        }
        lookaheadDelegate.isPlacedUnderMotionFrameOfReference = z;
    }

    @Override // androidx.compose.ui.layout.Placeable
    /* renamed from: placeAt-f8xVGno */
    public final void mo625placeAtf8xVGno(long j, float f, GraphicsLayer graphicsLayer) throws Throwable {
        m655placeSelfMLgxB_4$1(j, graphicsLayer, null);
    }
}
