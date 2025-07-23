package androidx.compose.ui.node;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.AlignmentLine;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.IntOffset;
import java.util.List;
import kotlin.Unit;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            function1.mo779invoke(lookaheadPassDelegate);
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
                    if (lookaheadPassDelegate.m654remeasureBRTryo0(constraints.value)) {
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
            Owner requireOwner = LayoutNodeKt.requireOwner(layoutNode);
            layoutNodeLayoutDelegate.setLookaheadCoordinatesAccessedDuringPlacement(false);
            OwnerSnapshotObserver ownerSnapshotObserver = ((AndroidComposeView) requireOwner).snapshotObserver;
            Function0 function0 = new Function0() { // from class: androidx.compose.ui.node.LookaheadPassDelegate$layoutChildren$1
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
                    LookaheadPassDelegate.this.forEachChildAlignmentLinesOwner(new Function1() { // from class: androidx.compose.ui.node.LookaheadPassDelegate$layoutChildren$1.1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj) {
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
                    LookaheadPassDelegate.this.forEachChildAlignmentLinesOwner(new Function1() { // from class: androidx.compose.ui.node.LookaheadPassDelegate$layoutChildren$1.4
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj) {
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

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0023, code lost:
    
        if ((r1 != null ? r1.layoutDelegate.layoutState : null) == androidx.compose.ui.node.LayoutNode.LayoutState.LookaheadLayingOut) goto L13;
     */
    @Override // androidx.compose.ui.layout.Measurable
    /* renamed from: measure-BRTryo0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final androidx.compose.ui.layout.Placeable mo608measureBRTryo0(long r6) {
        /*
            r5 = this;
            androidx.compose.ui.node.LayoutNodeLayoutDelegate r0 = r5.layoutNodeLayoutDelegate
            androidx.compose.ui.node.LayoutNode r1 = r0.layoutNode
            androidx.compose.ui.node.LayoutNode r1 = r1.getParent$ui_release()
            r2 = 0
            if (r1 == 0) goto L10
            androidx.compose.ui.node.LayoutNodeLayoutDelegate r1 = r1.layoutDelegate
            androidx.compose.ui.node.LayoutNode$LayoutState r1 = r1.layoutState
            goto L11
        L10:
            r1 = r2
        L11:
            androidx.compose.ui.node.LayoutNode$LayoutState r3 = androidx.compose.ui.node.LayoutNode.LayoutState.LookaheadMeasuring
            if (r1 == r3) goto L25
            androidx.compose.ui.node.LayoutNode r1 = r0.layoutNode
            androidx.compose.ui.node.LayoutNode r1 = r1.getParent$ui_release()
            if (r1 == 0) goto L21
            androidx.compose.ui.node.LayoutNodeLayoutDelegate r1 = r1.layoutDelegate
            androidx.compose.ui.node.LayoutNode$LayoutState r2 = r1.layoutState
        L21:
            androidx.compose.ui.node.LayoutNode$LayoutState r1 = androidx.compose.ui.node.LayoutNode.LayoutState.LookaheadLayingOut
            if (r2 != r1) goto L28
        L25:
            r1 = 0
            r0.detachedFromParentLookaheadPass = r1
        L28:
            androidx.compose.ui.node.LayoutNode r1 = r0.layoutNode
            androidx.compose.ui.node.LayoutNode r2 = r1.getParent$ui_release()
            if (r2 == 0) goto L77
            androidx.compose.ui.node.LayoutNode$UsageByParent r3 = r5.measuredByParent
            androidx.compose.ui.node.LayoutNode$UsageByParent r4 = androidx.compose.ui.node.LayoutNode.UsageByParent.NotUsed
            if (r3 == r4) goto L40
            boolean r1 = r1.canMultiMeasure
            if (r1 == 0) goto L3b
            goto L40
        L3b:
            java.lang.String r1 = "measure() may not be called multiple times on the same Measurable. If you want to get the content size of the Measurable before calculating the final constraints, please use methods like minIntrinsicWidth()/maxIntrinsicWidth() and minIntrinsicHeight()/maxIntrinsicHeight()"
            androidx.compose.ui.internal.InlineClassHelperKt.throwIllegalStateException(r1)
        L40:
            androidx.compose.ui.node.LayoutNodeLayoutDelegate r1 = r2.layoutDelegate
            androidx.compose.ui.node.LayoutNode$LayoutState r2 = r1.layoutState
            int[] r3 = androidx.compose.ui.node.LookaheadPassDelegate.WhenMappings.$EnumSwitchMapping$0
            int r2 = r2.ordinal()
            r2 = r3[r2]
            r3 = 1
            if (r2 == r3) goto L72
            r3 = 2
            if (r2 == r3) goto L72
            r3 = 3
            if (r2 == r3) goto L6f
            r3 = 4
            if (r2 != r3) goto L59
            goto L6f
        L59:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "Measurable could be only measured from the parent's measure or layout block. Parents state is "
            r6.<init>(r7)
            androidx.compose.ui.node.LayoutNode$LayoutState r7 = r1.layoutState
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6)
            throw r5
        L6f:
            androidx.compose.ui.node.LayoutNode$UsageByParent r1 = androidx.compose.ui.node.LayoutNode.UsageByParent.InLayoutBlock
            goto L74
        L72:
            androidx.compose.ui.node.LayoutNode$UsageByParent r1 = androidx.compose.ui.node.LayoutNode.UsageByParent.InMeasureBlock
        L74:
            r5.measuredByParent = r1
            goto L7b
        L77:
            androidx.compose.ui.node.LayoutNode$UsageByParent r1 = androidx.compose.ui.node.LayoutNode.UsageByParent.NotUsed
            r5.measuredByParent = r1
        L7b:
            androidx.compose.ui.node.LayoutNode r0 = r0.layoutNode
            androidx.compose.ui.node.LayoutNode$UsageByParent r1 = r0.intrinsicsUsageByParent
            androidx.compose.ui.node.LayoutNode$UsageByParent r2 = androidx.compose.ui.node.LayoutNode.UsageByParent.NotUsed
            if (r1 != r2) goto L86
            r0.clearSubtreeIntrinsicsUsage$ui_release()
        L86:
            r5.m654remeasureBRTryo0(r6)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.node.LookaheadPassDelegate.mo608measureBRTryo0(long):androidx.compose.ui.layout.Placeable");
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
    public final void mo609placeAtf8xVGno(long j, float f, Function1 function1) {
        m653placeSelfMLgxB_4$1(j, null, function1);
    }

    /* renamed from: placeSelf-MLgxB_4$1, reason: not valid java name */
    public final void m653placeSelfMLgxB_4$1(final long j, GraphicsLayer graphicsLayer, Function1 function1) {
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
            if (!IntOffset.m849equalsimpl0(j, this.lastPosition)) {
                if (layoutNodeLayoutDelegate.lookaheadCoordinatesAccessedDuringModifierPlacement || layoutNodeLayoutDelegate.lookaheadCoordinatesAccessedDuringPlacement) {
                    layoutNodeLayoutDelegate.lookaheadLayoutPending = true;
                }
                notifyChildrenUsingLookaheadCoordinatesWhilePlacing();
            }
            final Owner requireOwner = LayoutNodeKt.requireOwner(layoutNode2);
            if (layoutNodeLayoutDelegate.lookaheadLayoutPending || !isPlaced()) {
                layoutNodeLayoutDelegate.setLookaheadCoordinatesAccessedDuringModifierPlacement(false);
                this.alignmentLines.usedByModifierLayout = false;
                OwnerSnapshotObserver ownerSnapshotObserver = ((AndroidComposeView) requireOwner).snapshotObserver;
                Function0 function0 = new Function0() { // from class: androidx.compose.ui.node.LookaheadPassDelegate$placeSelf$1$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    /* JADX WARN: Removed duplicated region for block: B:11:0x0036  */
                    @Override // kotlin.jvm.functions.Function0
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object invoke() {
                        /*
                            r4 = this;
                            androidx.compose.ui.node.LookaheadPassDelegate r0 = androidx.compose.ui.node.LookaheadPassDelegate.this
                            androidx.compose.ui.node.LayoutNodeLayoutDelegate r0 = r0.layoutNodeLayoutDelegate
                            androidx.compose.ui.node.LayoutNode r0 = r0.layoutNode
                            boolean r0 = androidx.compose.ui.node.LayoutNodeLayoutDelegateKt.isOutMostLookaheadRoot(r0)
                            r1 = 0
                            if (r0 != 0) goto L26
                            androidx.compose.ui.node.LookaheadPassDelegate r0 = androidx.compose.ui.node.LookaheadPassDelegate.this
                            androidx.compose.ui.node.LayoutNodeLayoutDelegate r0 = r0.layoutNodeLayoutDelegate
                            boolean r2 = r0.detachedFromParentLookaheadPlacement
                            if (r2 != 0) goto L26
                            androidx.compose.ui.node.NodeCoordinator r0 = r0.getOuterCoordinator()
                            androidx.compose.ui.node.NodeCoordinator r0 = r0.wrappedBy
                            if (r0 == 0) goto L34
                            androidx.compose.ui.node.LookaheadDelegate r0 = r0.getLookaheadDelegate()
                            if (r0 == 0) goto L34
                            androidx.compose.ui.layout.Placeable$PlacementScope r1 = r0.placementScope
                            goto L34
                        L26:
                            androidx.compose.ui.node.LookaheadPassDelegate r0 = androidx.compose.ui.node.LookaheadPassDelegate.this
                            androidx.compose.ui.node.LayoutNodeLayoutDelegate r0 = r0.layoutNodeLayoutDelegate
                            androidx.compose.ui.node.NodeCoordinator r0 = r0.getOuterCoordinator()
                            androidx.compose.ui.node.NodeCoordinator r0 = r0.wrappedBy
                            if (r0 == 0) goto L34
                            androidx.compose.ui.layout.Placeable$PlacementScope r1 = r0.placementScope
                        L34:
                            if (r1 != 0) goto L41
                            androidx.compose.ui.node.Owner r0 = r2
                            androidx.compose.ui.platform.AndroidComposeView r0 = (androidx.compose.ui.platform.AndroidComposeView) r0
                            r0.getClass()
                            androidx.compose.ui.layout.Placeable$PlacementScope r1 = androidx.compose.ui.layout.PlaceableKt.PlacementScope(r0)
                        L41:
                            androidx.compose.ui.node.LookaheadPassDelegate r0 = androidx.compose.ui.node.LookaheadPassDelegate.this
                            long r2 = r3
                            androidx.compose.ui.node.LayoutNodeLayoutDelegate r4 = r0.layoutNodeLayoutDelegate
                            androidx.compose.ui.node.NodeCoordinator r4 = r4.getOuterCoordinator()
                            androidx.compose.ui.node.LookaheadDelegate r4 = r4.getLookaheadDelegate()
                            r4.getClass()
                            androidx.compose.ui.layout.Placeable.PlacementScope.m626place70tqf50$default(r1, r4, r2)
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.node.LookaheadPassDelegate$placeSelf$1$2.invoke():java.lang.Object");
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
                lookaheadDelegate.m651placeSelfgyyYBs(IntOffset.m851plusqkQi6aY(j, lookaheadDelegate.apparentToRealOffset));
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

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f A[Catch: all -> 0x0010, TryCatch #0 {all -> 0x0010, blocks: (B:3:0x0006, B:5:0x000a, B:6:0x0013, B:9:0x001f, B:13:0x0027, B:15:0x002f, B:20:0x003e, B:22:0x0042, B:23:0x0045, B:26:0x0035, B:27:0x0049, B:29:0x0066, B:30:0x0072, B:34:0x0083, B:35:0x0088, B:37:0x00a0, B:42:0x0069), top: B:2:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0066 A[Catch: all -> 0x0010, TryCatch #0 {all -> 0x0010, blocks: (B:3:0x0006, B:5:0x000a, B:6:0x0013, B:9:0x001f, B:13:0x0027, B:15:0x002f, B:20:0x003e, B:22:0x0042, B:23:0x0045, B:26:0x0035, B:27:0x0049, B:29:0x0066, B:30:0x0072, B:34:0x0083, B:35:0x0088, B:37:0x00a0, B:42:0x0069), top: B:2:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0083 A[Catch: all -> 0x0010, TryCatch #0 {all -> 0x0010, blocks: (B:3:0x0006, B:5:0x000a, B:6:0x0013, B:9:0x001f, B:13:0x0027, B:15:0x002f, B:20:0x003e, B:22:0x0042, B:23:0x0045, B:26:0x0035, B:27:0x0049, B:29:0x0066, B:30:0x0072, B:34:0x0083, B:35:0x0088, B:37:0x00a0, B:42:0x0069), top: B:2:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0069 A[Catch: all -> 0x0010, TryCatch #0 {all -> 0x0010, blocks: (B:3:0x0006, B:5:0x000a, B:6:0x0013, B:9:0x001f, B:13:0x0027, B:15:0x002f, B:20:0x003e, B:22:0x0042, B:23:0x0045, B:26:0x0035, B:27:0x0049, B:29:0x0066, B:30:0x0072, B:34:0x0083, B:35:0x0088, B:37:0x00a0, B:42:0x0069), top: B:2:0x0006 }] */
    /* renamed from: remeasure-BRTryo0, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean m654remeasureBRTryo0(long r13) {
        /*
            r12 = this;
            androidx.compose.ui.node.LayoutNodeLayoutDelegate r0 = r12.layoutNodeLayoutDelegate
            androidx.compose.ui.node.LayoutNode r1 = r0.layoutNode
            androidx.compose.ui.node.LayoutNode r2 = r0.layoutNode
            boolean r3 = r1.isDeactivated     // Catch: java.lang.Throwable -> L10
            if (r3 == 0) goto L13
            java.lang.String r3 = "measure is called on a deactivated node"
            androidx.compose.ui.internal.InlineClassHelperKt.throwIllegalArgumentException(r3)     // Catch: java.lang.Throwable -> L10
            goto L13
        L10:
            r12 = move-exception
            goto Laa
        L13:
            androidx.compose.ui.node.LayoutNode r3 = r2.getParent$ui_release()     // Catch: java.lang.Throwable -> L10
            boolean r4 = r2.canMultiMeasure     // Catch: java.lang.Throwable -> L10
            r5 = 1
            r6 = 0
            if (r4 != 0) goto L26
            if (r3 == 0) goto L24
            boolean r3 = r3.canMultiMeasure     // Catch: java.lang.Throwable -> L10
            if (r3 == 0) goto L24
            goto L26
        L24:
            r3 = r6
            goto L27
        L26:
            r3 = r5
        L27:
            r2.canMultiMeasure = r3     // Catch: java.lang.Throwable -> L10
            androidx.compose.ui.node.LayoutNodeLayoutDelegate r3 = r2.layoutDelegate     // Catch: java.lang.Throwable -> L10
            boolean r3 = r3.lookaheadMeasurePending     // Catch: java.lang.Throwable -> L10
            if (r3 != 0) goto L49
            androidx.compose.ui.unit.Constraints r3 = r12.lookaheadConstraints     // Catch: java.lang.Throwable -> L10
            if (r3 != 0) goto L35
            r3 = r6
            goto L3b
        L35:
            long r3 = r3.value     // Catch: java.lang.Throwable -> L10
            boolean r3 = androidx.compose.ui.unit.Constraints.m815equalsimpl0(r3, r13)     // Catch: java.lang.Throwable -> L10
        L3b:
            if (r3 != 0) goto L3e
            goto L49
        L3e:
            androidx.compose.ui.platform.AndroidComposeView r12 = r2.owner     // Catch: java.lang.Throwable -> L10
            if (r12 == 0) goto L45
            r12.forceMeasureTheSubtree(r2, r5)     // Catch: java.lang.Throwable -> L10
        L45:
            r2.resetSubtreeIntrinsicsUsage$ui_release()     // Catch: java.lang.Throwable -> L10
            return r6
        L49:
            androidx.compose.ui.unit.Constraints r2 = androidx.compose.ui.unit.Constraints.m813boximpl(r13)     // Catch: java.lang.Throwable -> L10
            r12.lookaheadConstraints = r2     // Catch: java.lang.Throwable -> L10
            r12.m625setMeasurementConstraintsBRTryo0(r13)     // Catch: java.lang.Throwable -> L10
            androidx.compose.ui.node.LookaheadAlignmentLines r2 = r12.alignmentLines     // Catch: java.lang.Throwable -> L10
            r2.usedByModifierMeasurement = r6     // Catch: java.lang.Throwable -> L10
            androidx.compose.ui.node.LookaheadPassDelegate$remeasure$1$2 r2 = new kotlin.jvm.functions.Function1() { // from class: androidx.compose.ui.node.LookaheadPassDelegate$remeasure$1$2
                static {
                    /*
                        androidx.compose.ui.node.LookaheadPassDelegate$remeasure$1$2 r0 = new androidx.compose.ui.node.LookaheadPassDelegate$remeasure$1$2
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:androidx.compose.ui.node.LookaheadPassDelegate$remeasure$1$2) androidx.compose.ui.node.LookaheadPassDelegate$remeasure$1$2.INSTANCE androidx.compose.ui.node.LookaheadPassDelegate$remeasure$1$2
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.node.LookaheadPassDelegate$remeasure$1$2.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 1
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.node.LookaheadPassDelegate$remeasure$1$2.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final java.lang.Object mo779invoke(java.lang.Object r1) {
                    /*
                        r0 = this;
                        androidx.compose.ui.node.AlignmentLinesOwner r1 = (androidx.compose.ui.node.AlignmentLinesOwner) r1
                        androidx.compose.ui.node.AlignmentLines r0 = r1.getAlignmentLines()
                        r1 = 0
                        r0.usedDuringParentMeasurement = r1
                        kotlin.Unit r0 = kotlin.Unit.INSTANCE
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.node.LookaheadPassDelegate$remeasure$1$2.mo779invoke(java.lang.Object):java.lang.Object");
                }
            }     // Catch: java.lang.Throwable -> L10
            r12.forEachChildAlignmentLinesOwner(r2)     // Catch: java.lang.Throwable -> L10
            boolean r2 = r12.measuredOnce     // Catch: java.lang.Throwable -> L10
            r3 = 4294967295(0xffffffff, double:2.1219957905E-314)
            r7 = 32
            if (r2 == 0) goto L69
            long r8 = r12.measuredSize     // Catch: java.lang.Throwable -> L10
            goto L72
        L69:
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            long r8 = (long) r2     // Catch: java.lang.Throwable -> L10
            long r10 = r8 << r7
            long r8 = r8 & r3
            long r8 = r8 | r10
            androidx.compose.ui.unit.IntSize$Companion r2 = androidx.compose.ui.unit.IntSize.Companion     // Catch: java.lang.Throwable -> L10
        L72:
            r12.measuredOnce = r5     // Catch: java.lang.Throwable -> L10
            androidx.compose.ui.node.NodeCoordinator r2 = r0.getOuterCoordinator()     // Catch: java.lang.Throwable -> L10
            androidx.compose.ui.node.LookaheadDelegate r2 = r2.getLookaheadDelegate()     // Catch: java.lang.Throwable -> L10
            if (r2 == 0) goto L80
            r10 = r5
            goto L81
        L80:
            r10 = r6
        L81:
            if (r10 != 0) goto L88
            java.lang.String r10 = "Lookahead result from lookaheadRemeasure cannot be null"
            androidx.compose.ui.internal.InlineClassHelperKt.throwIllegalStateException(r10)     // Catch: java.lang.Throwable -> L10
        L88:
            r0.m648performLookaheadMeasureBRTryo0$ui_release(r13)     // Catch: java.lang.Throwable -> L10
            int r13 = r2.width     // Catch: java.lang.Throwable -> L10
            int r14 = r2.height     // Catch: java.lang.Throwable -> L10
            long r10 = (long) r13     // Catch: java.lang.Throwable -> L10
            long r10 = r10 << r7
            long r13 = (long) r14     // Catch: java.lang.Throwable -> L10
            long r13 = r13 & r3
            long r13 = r13 | r10
            androidx.compose.ui.unit.IntSize$Companion r0 = androidx.compose.ui.unit.IntSize.Companion     // Catch: java.lang.Throwable -> L10
            r12.m624setMeasuredSizeozmzZPI(r13)     // Catch: java.lang.Throwable -> L10
            long r12 = r8 >> r7
            int r12 = (int) r12     // Catch: java.lang.Throwable -> L10
            int r13 = r2.width     // Catch: java.lang.Throwable -> L10
            if (r12 != r13) goto La9
            long r12 = r8 & r3
            int r12 = (int) r12     // Catch: java.lang.Throwable -> L10
            int r13 = r2.height     // Catch: java.lang.Throwable -> L10
            if (r12 == r13) goto La8
            goto La9
        La8:
            return r6
        La9:
            return r5
        Laa:
            r1.rethrowWithComposeStackTrace(r12)
            r12 = 0
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.node.LookaheadPassDelegate.m654remeasureBRTryo0(long):boolean");
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
    public final void mo623placeAtf8xVGno(long j, float f, GraphicsLayer graphicsLayer) {
        m653placeSelfMLgxB_4$1(j, graphicsLayer, null);
    }
}
