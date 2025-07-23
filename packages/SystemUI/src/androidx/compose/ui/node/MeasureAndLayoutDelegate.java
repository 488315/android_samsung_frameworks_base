package androidx.compose.ui.node;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.layout.PlaceableKt;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.Owner;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.unit.Constraints;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MeasureAndLayoutDelegate {
    public boolean duringFullMeasureLayoutPass;
    public boolean duringMeasureLayout;
    public final MutableVector onLayoutCompletedListeners;
    public final OnPositionedDispatcher onPositionedDispatcher;
    public final MutableVector postponedMeasureRequests;
    public final DepthSortedSetsForDifferentPasses relayoutNodes;
    public final LayoutNode root;
    public Constraints rootConstraints;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PostponedRequest {
        public final boolean isForced;
        public final boolean isLookahead;
        public final LayoutNode node;

        public PostponedRequest(LayoutNode layoutNode, boolean z, boolean z2) {
            this.node = layoutNode;
            this.isLookahead = z;
            this.isForced = z2;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

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
                iArr[LayoutNode.LayoutState.LookaheadLayingOut.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[LayoutNode.LayoutState.LayingOut.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[LayoutNode.LayoutState.Idle.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public MeasureAndLayoutDelegate(LayoutNode layoutNode) {
        this.root = layoutNode;
        Owner.Companion.getClass();
        this.relayoutNodes = new DepthSortedSetsForDifferentPasses(false);
        this.onPositionedDispatcher = new OnPositionedDispatcher();
        this.onLayoutCompletedListeners = new MutableVector(new Owner.OnLayoutCompletedListener[16], 0);
        this.postponedMeasureRequests = new MutableVector(new PostponedRequest[16], 0);
    }

    /* renamed from: doLookaheadRemeasure-sdFAvZA, reason: not valid java name */
    public static boolean m655doLookaheadRemeasuresdFAvZA(LayoutNode layoutNode, Constraints constraints) {
        boolean m654remeasureBRTryo0;
        LayoutNode layoutNode2 = layoutNode.lookaheadRoot;
        if (layoutNode2 == null) {
            return false;
        }
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = layoutNode.layoutDelegate;
        if (constraints != null) {
            if (layoutNode2 != null) {
                LookaheadPassDelegate lookaheadPassDelegate = layoutNodeLayoutDelegate.lookaheadPassDelegate;
                lookaheadPassDelegate.getClass();
                m654remeasureBRTryo0 = lookaheadPassDelegate.m654remeasureBRTryo0(constraints.value);
            }
            m654remeasureBRTryo0 = false;
        } else {
            LookaheadPassDelegate lookaheadPassDelegate2 = layoutNodeLayoutDelegate.lookaheadPassDelegate;
            Constraints constraints2 = lookaheadPassDelegate2 != null ? lookaheadPassDelegate2.lookaheadConstraints : null;
            if (constraints2 != null && layoutNode2 != null) {
                lookaheadPassDelegate2.getClass();
                m654remeasureBRTryo0 = lookaheadPassDelegate2.m654remeasureBRTryo0(constraints2.value);
            }
            m654remeasureBRTryo0 = false;
        }
        LayoutNode parent$ui_release = layoutNode.getParent$ui_release();
        if (m654remeasureBRTryo0 && parent$ui_release != null) {
            if (parent$ui_release.lookaheadRoot == null) {
                LayoutNode.requestRemeasure$ui_release$default(parent$ui_release, false, 3);
                return m654remeasureBRTryo0;
            }
            if (layoutNode.getMeasuredByParentInLookahead$ui_release() == LayoutNode.UsageByParent.InMeasureBlock) {
                LayoutNode.requestLookaheadRemeasure$ui_release$default(parent$ui_release, false, 3);
                return m654remeasureBRTryo0;
            }
            if (layoutNode.getMeasuredByParentInLookahead$ui_release() == LayoutNode.UsageByParent.InLayoutBlock) {
                parent$ui_release.requestLookaheadRelayout$ui_release(false);
            }
        }
        return m654remeasureBRTryo0;
    }

    /* renamed from: doRemeasure-sdFAvZA, reason: not valid java name */
    public static boolean m656doRemeasuresdFAvZA(LayoutNode layoutNode, Constraints constraints) {
        boolean m642remeasure_Sx5XlM$ui_release = constraints != null ? layoutNode.m642remeasure_Sx5XlM$ui_release(constraints) : LayoutNode.m639remeasure_Sx5XlM$ui_release$default(layoutNode);
        LayoutNode parent$ui_release = layoutNode.getParent$ui_release();
        if (m642remeasure_Sx5XlM$ui_release && parent$ui_release != null) {
            if (layoutNode.getMeasuredByParent$ui_release() == LayoutNode.UsageByParent.InMeasureBlock) {
                LayoutNode.requestRemeasure$ui_release$default(parent$ui_release, false, 3);
                return m642remeasure_Sx5XlM$ui_release;
            }
            if (layoutNode.getMeasuredByParent$ui_release() == LayoutNode.UsageByParent.InLayoutBlock) {
                parent$ui_release.requestRelayout$ui_release(false);
            }
        }
        return m642remeasure_Sx5XlM$ui_release;
    }

    public static boolean getMeasureAffectsParent(LayoutNode layoutNode) {
        return layoutNode.getMeasuredByParent$ui_release() == LayoutNode.UsageByParent.InMeasureBlock || layoutNode.layoutDelegate.measurePassDelegate.alignmentLines.getRequired$ui_release();
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0022, code lost:
    
        if (r3 < r5) goto L9;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dispatchOnPositionedCallbacks(boolean r6) {
        /*
            r5 = this;
            androidx.compose.ui.node.OnPositionedDispatcher r0 = r5.onPositionedDispatcher
            r1 = 1
            if (r6 == 0) goto L11
            androidx.compose.runtime.collection.MutableVector r6 = r0.layoutNodes
            r6.clear()
            androidx.compose.ui.node.LayoutNode r5 = r5.root
            r6.add(r5)
            r5.needsOnPositionedDispatch = r1
        L11:
            r0.getClass()
            androidx.compose.ui.node.OnPositionedDispatcher$Companion$DepthComparator r5 = androidx.compose.ui.node.OnPositionedDispatcher.Companion.DepthComparator.INSTANCE
            androidx.compose.runtime.collection.MutableVector r6 = r0.layoutNodes
            r6.sortWith(r5)
            int r5 = r6.size
            androidx.compose.ui.node.LayoutNode[] r2 = r0.cachedNodes
            if (r2 == 0) goto L24
            int r3 = r2.length
            if (r3 >= r5) goto L2c
        L24:
            r2 = 16
            int r2 = java.lang.Math.max(r2, r5)
            androidx.compose.ui.node.LayoutNode[] r2 = new androidx.compose.ui.node.LayoutNode[r2]
        L2c:
            r3 = 0
            r0.cachedNodes = r3
            r3 = 0
        L30:
            if (r3 >= r5) goto L3b
            java.lang.Object[] r4 = r6.content
            r4 = r4[r3]
            r2[r3] = r4
            int r3 = r3 + 1
            goto L30
        L3b:
            r6.clear()
            int r5 = r5 - r1
        L3f:
            r6 = -1
            if (r6 >= r5) goto L51
            r6 = r2[r5]
            r6.getClass()
            boolean r1 = r6.needsOnPositionedDispatch
            if (r1 == 0) goto L4e
            androidx.compose.ui.node.OnPositionedDispatcher.dispatchHierarchy(r6)
        L4e:
            int r5 = r5 + (-1)
            goto L3f
        L51:
            r0.cachedNodes = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.node.MeasureAndLayoutDelegate.dispatchOnPositionedCallbacks(boolean):void");
    }

    public final void drainPostponedMeasureRequests() {
        MutableVector mutableVector = this.postponedMeasureRequests;
        int i = mutableVector.size;
        if (i != 0) {
            Object[] objArr = mutableVector.content;
            for (int i2 = 0; i2 < i; i2++) {
                PostponedRequest postponedRequest = (PostponedRequest) objArr[i2];
                if (postponedRequest.node.isAttached()) {
                    boolean z = postponedRequest.isLookahead;
                    boolean z2 = postponedRequest.isForced;
                    LayoutNode layoutNode = postponedRequest.node;
                    if (z) {
                        LayoutNode.requestLookaheadRemeasure$ui_release$default(layoutNode, z2, 2);
                    } else {
                        LayoutNode.requestRemeasure$ui_release$default(layoutNode, z2, 2);
                    }
                }
            }
            mutableVector.clear();
        }
    }

    public final void ensureSubtreeLookaheadReplaced(LayoutNode layoutNode) {
        MutableVector mutableVector = layoutNode.get_children$ui_release();
        Object[] objArr = mutableVector.content;
        int i = mutableVector.size;
        for (int i2 = 0; i2 < i; i2++) {
            LayoutNode layoutNode2 = (LayoutNode) objArr[i2];
            if (Intrinsics.areEqual(layoutNode2.isPlacedInLookahead(), Boolean.TRUE) && !layoutNode2.isDeactivated) {
                if (this.relayoutNodes.contains(layoutNode2, true)) {
                    layoutNode2.lookaheadReplace$ui_release();
                }
                ensureSubtreeLookaheadReplaced(layoutNode2);
            }
        }
    }

    public final void forceMeasureTheSubtree(LayoutNode layoutNode, boolean z) {
        DepthSortedSetsForDifferentPasses depthSortedSetsForDifferentPasses = this.relayoutNodes;
        if ((z ? depthSortedSetsForDifferentPasses.lookaheadSet : depthSortedSetsForDifferentPasses.set).set.isEmpty()) {
            return;
        }
        if (!this.duringMeasureLayout) {
            InlineClassHelperKt.throwIllegalStateException("forceMeasureTheSubtree should be executed during the measureAndLayout pass");
        }
        if (z ? layoutNode.layoutDelegate.lookaheadMeasurePending : layoutNode.getMeasurePending$ui_release()) {
            InlineClassHelperKt.throwIllegalArgumentException("node not yet measured");
        }
        forceMeasureTheSubtreeInternal(layoutNode, z);
    }

    public final void forceMeasureTheSubtreeInternal(LayoutNode layoutNode, boolean z) {
        DepthSortedSetsForDifferentPasses depthSortedSetsForDifferentPasses;
        LookaheadPassDelegate lookaheadPassDelegate;
        LookaheadAlignmentLines lookaheadAlignmentLines;
        MutableVector mutableVector = layoutNode.get_children$ui_release();
        Object[] objArr = mutableVector.content;
        int i = mutableVector.size;
        int i2 = 0;
        while (true) {
            depthSortedSetsForDifferentPasses = this.relayoutNodes;
            if (i2 >= i) {
                break;
            }
            LayoutNode layoutNode2 = (LayoutNode) objArr[i2];
            if ((!z && getMeasureAffectsParent(layoutNode2)) || (z && (layoutNode2.getMeasuredByParentInLookahead$ui_release() == LayoutNode.UsageByParent.InMeasureBlock || ((lookaheadPassDelegate = layoutNode2.layoutDelegate.lookaheadPassDelegate) != null && (lookaheadAlignmentLines = lookaheadPassDelegate.alignmentLines) != null && lookaheadAlignmentLines.getRequired$ui_release())))) {
                boolean isOutMostLookaheadRoot = LayoutNodeLayoutDelegateKt.isOutMostLookaheadRoot(layoutNode2);
                LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = layoutNode2.layoutDelegate;
                if (isOutMostLookaheadRoot && !z) {
                    if (layoutNodeLayoutDelegate.lookaheadMeasurePending && depthSortedSetsForDifferentPasses.contains(layoutNode2, true)) {
                        remeasureAndRelayoutIfNeeded(layoutNode2, true, false);
                    } else {
                        forceMeasureTheSubtree(layoutNode2, true);
                    }
                }
                if ((z ? layoutNodeLayoutDelegate.lookaheadMeasurePending : layoutNode2.getMeasurePending$ui_release()) && depthSortedSetsForDifferentPasses.contains(layoutNode2, z)) {
                    remeasureAndRelayoutIfNeeded(layoutNode2, z, false);
                }
                if (!(z ? layoutNodeLayoutDelegate.lookaheadMeasurePending : layoutNode2.getMeasurePending$ui_release())) {
                    forceMeasureTheSubtreeInternal(layoutNode2, z);
                }
            }
            i2++;
        }
        if ((z ? layoutNode.layoutDelegate.lookaheadMeasurePending : layoutNode.getMeasurePending$ui_release()) && depthSortedSetsForDifferentPasses.contains(layoutNode, z)) {
            remeasureAndRelayoutIfNeeded(layoutNode, z, false);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean measureAndLayout(Function0 function0) {
        boolean z;
        LayoutNode layoutNode;
        DepthSortedSetsForDifferentPasses depthSortedSetsForDifferentPasses = this.relayoutNodes;
        LayoutNode layoutNode2 = this.root;
        if (!layoutNode2.isAttached()) {
            InlineClassHelperKt.throwIllegalArgumentException("performMeasureAndLayout called with unattached root");
        }
        if (!layoutNode2.isPlaced()) {
            InlineClassHelperKt.throwIllegalArgumentException("performMeasureAndLayout called with unplaced root");
        }
        if (this.duringMeasureLayout) {
            InlineClassHelperKt.throwIllegalArgumentException("performMeasureAndLayout called during measure layout");
        }
        byte b = 0;
        byte b2 = 0;
        byte b3 = 0;
        byte b4 = 0;
        if (this.rootConstraints != null) {
            this.duringMeasureLayout = true;
            this.duringFullMeasureLayoutPass = true;
            try {
                if (depthSortedSetsForDifferentPasses.isNotEmpty()) {
                    z = false;
                    while (true) {
                        boolean isNotEmpty = depthSortedSetsForDifferentPasses.isNotEmpty();
                        DepthSortedSet depthSortedSet = depthSortedSetsForDifferentPasses.lookaheadSet;
                        if (!isNotEmpty) {
                            break;
                        }
                        boolean isEmpty = depthSortedSet.set.isEmpty();
                        boolean z2 = !isEmpty;
                        if (isEmpty) {
                            DepthSortedSet depthSortedSet2 = depthSortedSetsForDifferentPasses.set;
                            LayoutNode layoutNode3 = (LayoutNode) depthSortedSet2.set.first();
                            depthSortedSet2.remove(layoutNode3);
                            layoutNode = layoutNode3;
                        } else {
                            layoutNode = (LayoutNode) depthSortedSet.set.first();
                            depthSortedSet.remove(layoutNode);
                        }
                        boolean remeasureAndRelayoutIfNeeded = remeasureAndRelayoutIfNeeded(layoutNode, z2, true);
                        if (layoutNode == layoutNode2 && remeasureAndRelayoutIfNeeded) {
                            z = true;
                        }
                    }
                    if (function0 != null) {
                        function0.invoke();
                    }
                } else {
                    z = false;
                }
            } finally {
            }
        } else {
            z = false;
        }
        MutableVector mutableVector = this.onLayoutCompletedListeners;
        Object[] objArr = mutableVector.content;
        int i = mutableVector.size;
        for (int i2 = 0; i2 < i; i2++) {
            ((Owner.OnLayoutCompletedListener) objArr[i2]).onLayoutComplete();
        }
        mutableVector.clear();
        return z;
    }

    /* renamed from: measureAndLayout-0kLqBqw, reason: not valid java name */
    public final void m657measureAndLayout0kLqBqw(LayoutNode layoutNode, long j) {
        if (layoutNode.isDeactivated) {
            return;
        }
        LayoutNode layoutNode2 = this.root;
        if (layoutNode.equals(layoutNode2)) {
            InlineClassHelperKt.throwIllegalArgumentException("measureAndLayout called on root");
        }
        if (!layoutNode2.isAttached()) {
            InlineClassHelperKt.throwIllegalArgumentException("performMeasureAndLayout called with unattached root");
        }
        if (!layoutNode2.isPlaced()) {
            InlineClassHelperKt.throwIllegalArgumentException("performMeasureAndLayout called with unplaced root");
        }
        if (this.duringMeasureLayout) {
            InlineClassHelperKt.throwIllegalArgumentException("performMeasureAndLayout called during measure layout");
        }
        byte b = 0;
        byte b2 = 0;
        byte b3 = 0;
        byte b4 = 0;
        if (this.rootConstraints != null) {
            this.duringMeasureLayout = true;
            this.duringFullMeasureLayoutPass = false;
            try {
                DepthSortedSetsForDifferentPasses depthSortedSetsForDifferentPasses = this.relayoutNodes;
                depthSortedSetsForDifferentPasses.lookaheadSet.remove(layoutNode);
                depthSortedSetsForDifferentPasses.set.remove(layoutNode);
                if ((m655doLookaheadRemeasuresdFAvZA(layoutNode, Constraints.m813boximpl(j)) || layoutNode.layoutDelegate.lookaheadLayoutPending) && Intrinsics.areEqual(layoutNode.isPlacedInLookahead(), Boolean.TRUE)) {
                    layoutNode.lookaheadReplace$ui_release();
                }
                ensureSubtreeLookaheadReplaced(layoutNode);
                m656doRemeasuresdFAvZA(layoutNode, Constraints.m813boximpl(j));
                if (layoutNode.getLayoutPending$ui_release() && layoutNode.isPlaced()) {
                    layoutNode.replace$ui_release();
                    this.onPositionedDispatcher.layoutNodes.add(layoutNode);
                    layoutNode.needsOnPositionedDispatch = true;
                }
                drainPostponedMeasureRequests();
            } finally {
            }
        }
        MutableVector mutableVector = this.onLayoutCompletedListeners;
        Object[] objArr = mutableVector.content;
        int i = mutableVector.size;
        for (int i2 = 0; i2 < i; i2++) {
            ((Owner.OnLayoutCompletedListener) objArr[i2]).onLayoutComplete();
        }
        mutableVector.clear();
    }

    public final void measureOnly() {
        DepthSortedSetsForDifferentPasses depthSortedSetsForDifferentPasses = this.relayoutNodes;
        if (depthSortedSetsForDifferentPasses.isNotEmpty()) {
            LayoutNode layoutNode = this.root;
            if (!layoutNode.isAttached()) {
                InlineClassHelperKt.throwIllegalArgumentException("performMeasureAndLayout called with unattached root");
            }
            if (!layoutNode.isPlaced()) {
                InlineClassHelperKt.throwIllegalArgumentException("performMeasureAndLayout called with unplaced root");
            }
            if (this.duringMeasureLayout) {
                InlineClassHelperKt.throwIllegalArgumentException("performMeasureAndLayout called during measure layout");
            }
            if (this.rootConstraints != null) {
                this.duringMeasureLayout = true;
                this.duringFullMeasureLayoutPass = false;
                try {
                    if (!depthSortedSetsForDifferentPasses.lookaheadSet.set.isEmpty()) {
                        if (layoutNode.lookaheadRoot != null) {
                            remeasureOnly(layoutNode, true);
                        } else {
                            remeasureLookaheadRootsInSubtree(layoutNode);
                        }
                    }
                    remeasureOnly(layoutNode, false);
                } catch (Throwable th) {
                    try {
                        throw th;
                    } finally {
                        this.duringMeasureLayout = false;
                        this.duringFullMeasureLayoutPass = false;
                    }
                }
            }
        }
    }

    public final boolean remeasureAndRelayoutIfNeeded(LayoutNode layoutNode, boolean z, boolean z2) {
        Constraints constraints;
        boolean z3;
        Placeable.PlacementScope PlacementScope;
        InnerNodeCoordinator innerNodeCoordinator;
        LayoutNode parent$ui_release;
        LookaheadPassDelegate lookaheadPassDelegate;
        LookaheadAlignmentLines lookaheadAlignmentLines;
        LookaheadPassDelegate lookaheadPassDelegate2;
        LookaheadAlignmentLines lookaheadAlignmentLines2;
        if (!layoutNode.isDeactivated) {
            boolean isPlaced = layoutNode.isPlaced();
            LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = layoutNode.layoutDelegate;
            if (isPlaced || layoutNodeLayoutDelegate.measurePassDelegate.isPlacedByParent || ((layoutNode.getMeasurePending$ui_release() && getMeasureAffectsParent(layoutNode)) || Intrinsics.areEqual(layoutNode.isPlacedInLookahead(), Boolean.TRUE) || ((layoutNodeLayoutDelegate.lookaheadMeasurePending && (layoutNode.getMeasuredByParentInLookahead$ui_release() == LayoutNode.UsageByParent.InMeasureBlock || ((lookaheadPassDelegate2 = layoutNodeLayoutDelegate.lookaheadPassDelegate) != null && (lookaheadAlignmentLines2 = lookaheadPassDelegate2.alignmentLines) != null && lookaheadAlignmentLines2.getRequired$ui_release()))) || layoutNodeLayoutDelegate.measurePassDelegate.alignmentLines.getRequired$ui_release() || ((lookaheadPassDelegate = layoutNodeLayoutDelegate.lookaheadPassDelegate) != null && (lookaheadAlignmentLines = lookaheadPassDelegate.alignmentLines) != null && lookaheadAlignmentLines.getRequired$ui_release())))) {
                LayoutNode layoutNode2 = this.root;
                if (layoutNode == layoutNode2) {
                    constraints = this.rootConstraints;
                    constraints.getClass();
                } else {
                    constraints = null;
                }
                if (z) {
                    z3 = layoutNodeLayoutDelegate.lookaheadMeasurePending ? m655doLookaheadRemeasuresdFAvZA(layoutNode, constraints) : false;
                    if (z2 && ((z3 || layoutNodeLayoutDelegate.lookaheadLayoutPending) && Intrinsics.areEqual(layoutNode.isPlacedInLookahead(), Boolean.TRUE))) {
                        layoutNode.lookaheadReplace$ui_release();
                    }
                } else {
                    boolean m656doRemeasuresdFAvZA = layoutNode.getMeasurePending$ui_release() ? m656doRemeasuresdFAvZA(layoutNode, constraints) : false;
                    if (z2 && layoutNode.getLayoutPending$ui_release() && (layoutNode == layoutNode2 || ((parent$ui_release = layoutNode.getParent$ui_release()) != null && parent$ui_release.isPlaced() && layoutNodeLayoutDelegate.measurePassDelegate.isPlacedByParent))) {
                        if (layoutNode == layoutNode2) {
                            if (layoutNode.intrinsicsUsageByParent == LayoutNode.UsageByParent.NotUsed) {
                                layoutNode.clearSubtreePlacementIntrinsicsUsage();
                            }
                            LayoutNode parent$ui_release2 = layoutNode.getParent$ui_release();
                            if (parent$ui_release2 == null || (innerNodeCoordinator = parent$ui_release2.nodes.innerCoordinator) == null || (PlacementScope = innerNodeCoordinator.placementScope) == null) {
                                PlacementScope = PlaceableKt.PlacementScope((AndroidComposeView) LayoutNodeKt.requireOwner(layoutNode));
                            }
                            PlacementScope.placeRelative(layoutNodeLayoutDelegate.measurePassDelegate, 0, 0, 0.0f);
                        } else {
                            layoutNode.replace$ui_release();
                        }
                        this.onPositionedDispatcher.layoutNodes.add(layoutNode);
                        layoutNode.needsOnPositionedDispatch = true;
                        ((AndroidComposeView) LayoutNodeKt.requireOwner(layoutNode)).rectManager.invalidateCallbacksFor(layoutNode);
                    }
                    z3 = m656doRemeasuresdFAvZA;
                }
                drainPostponedMeasureRequests();
                return z3;
            }
        }
        return false;
    }

    public final void remeasureLookaheadRootsInSubtree(LayoutNode layoutNode) {
        MutableVector mutableVector = layoutNode.get_children$ui_release();
        Object[] objArr = mutableVector.content;
        int i = mutableVector.size;
        for (int i2 = 0; i2 < i; i2++) {
            LayoutNode layoutNode2 = (LayoutNode) objArr[i2];
            if (getMeasureAffectsParent(layoutNode2)) {
                if (LayoutNodeLayoutDelegateKt.isOutMostLookaheadRoot(layoutNode2)) {
                    remeasureOnly(layoutNode2, true);
                } else {
                    remeasureLookaheadRootsInSubtree(layoutNode2);
                }
            }
        }
    }

    public final void remeasureOnly(LayoutNode layoutNode, boolean z) {
        Constraints constraints;
        if (layoutNode.isDeactivated) {
            return;
        }
        if (layoutNode == this.root) {
            constraints = this.rootConstraints;
            constraints.getClass();
        } else {
            constraints = null;
        }
        if (z) {
            m655doLookaheadRemeasuresdFAvZA(layoutNode, constraints);
        } else {
            m656doRemeasuresdFAvZA(layoutNode, constraints);
        }
    }

    public final boolean requestRemeasure(LayoutNode layoutNode, boolean z) {
        int i = WhenMappings.$EnumSwitchMapping$0[layoutNode.layoutDelegate.layoutState.ordinal()];
        if (i != 1 && i != 2) {
            if (i == 3 || i == 4) {
                this.postponedMeasureRequests.add(new PostponedRequest(layoutNode, false, z));
            } else {
                if (i != 5) {
                    throw new NoWhenBranchMatchedException();
                }
                if (!layoutNode.getMeasurePending$ui_release() || z) {
                    layoutNode.layoutDelegate.measurePassDelegate.measurePending = true;
                    if (layoutNode.isDeactivated || (!layoutNode.isPlaced() && (!layoutNode.getMeasurePending$ui_release() || !getMeasureAffectsParent(layoutNode)))) {
                        return false;
                    }
                    LayoutNode parent$ui_release = layoutNode.getParent$ui_release();
                    if (parent$ui_release == null || !parent$ui_release.getMeasurePending$ui_release()) {
                        this.relayoutNodes.add(layoutNode, false);
                    }
                    if (!this.duringFullMeasureLayoutPass) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /* renamed from: updateRootConstraints-BRTryo0, reason: not valid java name */
    public final void m658updateRootConstraintsBRTryo0(long j) {
        Constraints constraints = this.rootConstraints;
        if (constraints == null ? false : Constraints.m815equalsimpl0(constraints.value, j)) {
            return;
        }
        if (this.duringMeasureLayout) {
            InlineClassHelperKt.throwIllegalArgumentException("updateRootConstraints called while measuring");
        }
        this.rootConstraints = Constraints.m813boximpl(j);
        LayoutNode layoutNode = this.root;
        LayoutNode layoutNode2 = layoutNode.lookaheadRoot;
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = layoutNode.layoutDelegate;
        if (layoutNode2 != null) {
            layoutNodeLayoutDelegate.lookaheadMeasurePending = true;
        }
        layoutNodeLayoutDelegate.measurePassDelegate.measurePending = true;
        this.relayoutNodes.add(layoutNode, layoutNode2 != null);
    }
}
