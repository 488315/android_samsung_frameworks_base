package androidx.compose.ui.node;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.layout.PlaceableKt;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.OnPositionedDispatcher;
import androidx.compose.ui.node.Owner;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.unit.Constraints;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

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

    /* JADX WARN: Removed duplicated region for block: B:9:0x0018  */
    /* renamed from: doLookaheadRemeasure-sdFAvZA, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean m657doLookaheadRemeasuresdFAvZA(LayoutNode layoutNode, Constraints constraints) throws Throwable {
        boolean zM656remeasureBRTryo0;
        LayoutNode layoutNode2 = layoutNode.lookaheadRoot;
        if (layoutNode2 == null) {
            return false;
        }
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = layoutNode.layoutDelegate;
        if (constraints == null) {
            LookaheadPassDelegate lookaheadPassDelegate = layoutNodeLayoutDelegate.lookaheadPassDelegate;
            Constraints constraints2 = lookaheadPassDelegate != null ? lookaheadPassDelegate.lookaheadConstraints : null;
            if (constraints2 != null && layoutNode2 != null) {
                lookaheadPassDelegate.getClass();
                zM656remeasureBRTryo0 = lookaheadPassDelegate.m656remeasureBRTryo0(constraints2.value);
            }
        } else if (layoutNode2 != null) {
            LookaheadPassDelegate lookaheadPassDelegate2 = layoutNodeLayoutDelegate.lookaheadPassDelegate;
            lookaheadPassDelegate2.getClass();
            zM656remeasureBRTryo0 = lookaheadPassDelegate2.m656remeasureBRTryo0(constraints.value);
        } else {
            zM656remeasureBRTryo0 = false;
        }
        LayoutNode parent$ui_release = layoutNode.getParent$ui_release();
        if (zM656remeasureBRTryo0 && parent$ui_release != null) {
            if (parent$ui_release.lookaheadRoot == null) {
                LayoutNode.requestRemeasure$ui_release$default(parent$ui_release, false, 3);
                return zM656remeasureBRTryo0;
            }
            if (layoutNode.getMeasuredByParentInLookahead$ui_release() == LayoutNode.UsageByParent.InMeasureBlock) {
                LayoutNode.requestLookaheadRemeasure$ui_release$default(parent$ui_release, false, 3);
                return zM656remeasureBRTryo0;
            }
            if (layoutNode.getMeasuredByParentInLookahead$ui_release() == LayoutNode.UsageByParent.InLayoutBlock) {
                parent$ui_release.requestLookaheadRelayout$ui_release(false);
            }
        }
        return zM656remeasureBRTryo0;
    }

    /* renamed from: doRemeasure-sdFAvZA, reason: not valid java name */
    public static boolean m658doRemeasuresdFAvZA(LayoutNode layoutNode, Constraints constraints) {
        boolean zM644remeasure_Sx5XlM$ui_release = constraints != null ? layoutNode.m644remeasure_Sx5XlM$ui_release(constraints) : LayoutNode.m641remeasure_Sx5XlM$ui_release$default(layoutNode);
        LayoutNode parent$ui_release = layoutNode.getParent$ui_release();
        if (zM644remeasure_Sx5XlM$ui_release && parent$ui_release != null) {
            if (layoutNode.getMeasuredByParent$ui_release() == LayoutNode.UsageByParent.InMeasureBlock) {
                LayoutNode.requestRemeasure$ui_release$default(parent$ui_release, false, 3);
                return zM644remeasure_Sx5XlM$ui_release;
            }
            if (layoutNode.getMeasuredByParent$ui_release() == LayoutNode.UsageByParent.InLayoutBlock) {
                parent$ui_release.requestRelayout$ui_release(false);
            }
        }
        return zM644remeasure_Sx5XlM$ui_release;
    }

    public static boolean getMeasureAffectsParent(LayoutNode layoutNode) {
        return layoutNode.getMeasuredByParent$ui_release() == LayoutNode.UsageByParent.InMeasureBlock || layoutNode.layoutDelegate.measurePassDelegate.alignmentLines.getRequired$ui_release();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void dispatchOnPositionedCallbacks(boolean z) {
        LayoutNode[] layoutNodeArr;
        OnPositionedDispatcher onPositionedDispatcher = this.onPositionedDispatcher;
        if (z) {
            MutableVector mutableVector = onPositionedDispatcher.layoutNodes;
            mutableVector.clear();
            LayoutNode layoutNode = this.root;
            mutableVector.add(layoutNode);
            layoutNode.needsOnPositionedDispatch = true;
        }
        onPositionedDispatcher.getClass();
        OnPositionedDispatcher.Companion.DepthComparator depthComparator = OnPositionedDispatcher.Companion.DepthComparator.INSTANCE;
        MutableVector mutableVector2 = onPositionedDispatcher.layoutNodes;
        mutableVector2.sortWith(depthComparator);
        int i = mutableVector2.size;
        LayoutNode[] layoutNodeArr2 = onPositionedDispatcher.cachedNodes;
        if (layoutNodeArr2 != null) {
            int length = layoutNodeArr2.length;
            layoutNodeArr = layoutNodeArr2;
            if (length < i) {
                layoutNodeArr = new LayoutNode[Math.max(16, i)];
            }
        }
        onPositionedDispatcher.cachedNodes = null;
        for (int i2 = 0; i2 < i; i2++) {
            layoutNodeArr[i2] = mutableVector2.content[i2];
        }
        mutableVector2.clear();
        for (int i3 = i - 1; -1 < i3; i3--) {
            LayoutNode layoutNode2 = layoutNodeArr[i3];
            layoutNode2.getClass();
            if (layoutNode2.needsOnPositionedDispatch) {
                OnPositionedDispatcher.dispatchHierarchy(layoutNode2);
            }
        }
        onPositionedDispatcher.cachedNodes = layoutNodeArr;
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
                boolean zIsOutMostLookaheadRoot = LayoutNodeLayoutDelegateKt.isOutMostLookaheadRoot(layoutNode2);
                LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = layoutNode2.layoutDelegate;
                if (zIsOutMostLookaheadRoot && !z) {
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
        Object[] objArr = 0;
        Object[] objArr2 = 0;
        Object[] objArr3 = 0;
        Object[] objArr4 = 0;
        if (this.rootConstraints != null) {
            this.duringMeasureLayout = true;
            this.duringFullMeasureLayoutPass = true;
            try {
                if (depthSortedSetsForDifferentPasses.isNotEmpty()) {
                    z = false;
                    while (true) {
                        boolean zIsNotEmpty = depthSortedSetsForDifferentPasses.isNotEmpty();
                        DepthSortedSet depthSortedSet = depthSortedSetsForDifferentPasses.lookaheadSet;
                        if (!zIsNotEmpty) {
                            break;
                        }
                        boolean zIsEmpty = depthSortedSet.set.isEmpty();
                        boolean z2 = !zIsEmpty;
                        if (zIsEmpty) {
                            DepthSortedSet depthSortedSet2 = depthSortedSetsForDifferentPasses.set;
                            LayoutNode layoutNode3 = (LayoutNode) depthSortedSet2.set.first();
                            depthSortedSet2.remove(layoutNode3);
                            layoutNode = layoutNode3;
                        } else {
                            layoutNode = (LayoutNode) depthSortedSet.set.first();
                            depthSortedSet.remove(layoutNode);
                        }
                        boolean zRemeasureAndRelayoutIfNeeded = remeasureAndRelayoutIfNeeded(layoutNode, z2, true);
                        if (layoutNode == layoutNode2 && zRemeasureAndRelayoutIfNeeded) {
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
        Object[] objArr5 = mutableVector.content;
        int i = mutableVector.size;
        for (int i2 = 0; i2 < i; i2++) {
            ((Owner.OnLayoutCompletedListener) objArr5[i2]).onLayoutComplete();
        }
        mutableVector.clear();
        return z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: measureAndLayout-0kLqBqw, reason: not valid java name */
    public final void m659measureAndLayout0kLqBqw(LayoutNode layoutNode, long j) {
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
        Object[] objArr = 0;
        Object[] objArr2 = 0;
        Object[] objArr3 = 0;
        Object[] objArr4 = 0;
        if (this.rootConstraints != null) {
            this.duringMeasureLayout = true;
            this.duringFullMeasureLayoutPass = false;
            try {
                DepthSortedSetsForDifferentPasses depthSortedSetsForDifferentPasses = this.relayoutNodes;
                depthSortedSetsForDifferentPasses.lookaheadSet.remove(layoutNode);
                depthSortedSetsForDifferentPasses.set.remove(layoutNode);
                if ((m657doLookaheadRemeasuresdFAvZA(layoutNode, Constraints.m815boximpl(j)) || layoutNode.layoutDelegate.lookaheadLayoutPending) && Intrinsics.areEqual(layoutNode.isPlacedInLookahead(), Boolean.TRUE)) {
                    layoutNode.lookaheadReplace$ui_release();
                }
                ensureSubtreeLookaheadReplaced(layoutNode);
                m658doRemeasuresdFAvZA(layoutNode, Constraints.m815boximpl(j));
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
        Object[] objArr5 = mutableVector.content;
        int i = mutableVector.size;
        for (int i2 = 0; i2 < i; i2++) {
            ((Owner.OnLayoutCompletedListener) objArr5[i2]).onLayoutComplete();
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
        boolean zM657doLookaheadRemeasuresdFAvZA;
        Placeable.PlacementScope PlacementScope;
        InnerNodeCoordinator innerNodeCoordinator;
        LayoutNode parent$ui_release;
        LookaheadPassDelegate lookaheadPassDelegate;
        LookaheadAlignmentLines lookaheadAlignmentLines;
        LookaheadPassDelegate lookaheadPassDelegate2;
        LookaheadAlignmentLines lookaheadAlignmentLines2;
        if (!layoutNode.isDeactivated) {
            boolean zIsPlaced = layoutNode.isPlaced();
            LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = layoutNode.layoutDelegate;
            if (zIsPlaced || layoutNodeLayoutDelegate.measurePassDelegate.isPlacedByParent || ((layoutNode.getMeasurePending$ui_release() && getMeasureAffectsParent(layoutNode)) || Intrinsics.areEqual(layoutNode.isPlacedInLookahead(), Boolean.TRUE) || ((layoutNodeLayoutDelegate.lookaheadMeasurePending && (layoutNode.getMeasuredByParentInLookahead$ui_release() == LayoutNode.UsageByParent.InMeasureBlock || ((lookaheadPassDelegate2 = layoutNodeLayoutDelegate.lookaheadPassDelegate) != null && (lookaheadAlignmentLines2 = lookaheadPassDelegate2.alignmentLines) != null && lookaheadAlignmentLines2.getRequired$ui_release()))) || layoutNodeLayoutDelegate.measurePassDelegate.alignmentLines.getRequired$ui_release() || ((lookaheadPassDelegate = layoutNodeLayoutDelegate.lookaheadPassDelegate) != null && (lookaheadAlignmentLines = lookaheadPassDelegate.alignmentLines) != null && lookaheadAlignmentLines.getRequired$ui_release())))) {
                LayoutNode layoutNode2 = this.root;
                if (layoutNode == layoutNode2) {
                    constraints = this.rootConstraints;
                    constraints.getClass();
                } else {
                    constraints = null;
                }
                if (z) {
                    zM657doLookaheadRemeasuresdFAvZA = layoutNodeLayoutDelegate.lookaheadMeasurePending ? m657doLookaheadRemeasuresdFAvZA(layoutNode, constraints) : false;
                    if (z2 && ((zM657doLookaheadRemeasuresdFAvZA || layoutNodeLayoutDelegate.lookaheadLayoutPending) && Intrinsics.areEqual(layoutNode.isPlacedInLookahead(), Boolean.TRUE))) {
                        layoutNode.lookaheadReplace$ui_release();
                    }
                } else {
                    boolean zM658doRemeasuresdFAvZA = layoutNode.getMeasurePending$ui_release() ? m658doRemeasuresdFAvZA(layoutNode, constraints) : false;
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
                    zM657doLookaheadRemeasuresdFAvZA = zM658doRemeasuresdFAvZA;
                }
                drainPostponedMeasureRequests();
                return zM657doLookaheadRemeasuresdFAvZA;
            }
        }
        return false;
    }

    public final void remeasureLookaheadRootsInSubtree(LayoutNode layoutNode) throws Throwable {
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

    public final void remeasureOnly(LayoutNode layoutNode, boolean z) throws Throwable {
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
            m657doLookaheadRemeasuresdFAvZA(layoutNode, constraints);
        } else {
            m658doRemeasuresdFAvZA(layoutNode, constraints);
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
    public final void m660updateRootConstraintsBRTryo0(long j) {
        Constraints constraints = this.rootConstraints;
        if (constraints == null ? false : Constraints.m817equalsimpl0(constraints.value, j)) {
            return;
        }
        if (this.duringMeasureLayout) {
            InlineClassHelperKt.throwIllegalArgumentException("updateRootConstraints called while measuring");
        }
        this.rootConstraints = Constraints.m815boximpl(j);
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
