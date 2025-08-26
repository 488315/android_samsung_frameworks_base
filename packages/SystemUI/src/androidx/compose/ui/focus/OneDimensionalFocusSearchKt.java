package androidx.compose.ui.focus;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.BeyondBoundsLayout;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.NodeChain;
import androidx.compose.ui.platform.AndroidComposeView;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes.dex */
public abstract class OneDimensionalFocusSearchKt {

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FocusStateImpl.values().length];
            try {
                iArr[FocusStateImpl.ActiveParent.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FocusStateImpl.Active.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[FocusStateImpl.Captured.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[FocusStateImpl.Inactive.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x009b A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final boolean backwardFocusSearch(FocusTargetNode focusTargetNode, Function1 function1) {
        FocusStateImpl focusState = focusTargetNode.getFocusState();
        int[] iArr = WhenMappings.$EnumSwitchMapping$0;
        int i = iArr[focusState.ordinal()];
        if (i != 1) {
            if (i == 2 || i == 3) {
                return pickChildForBackwardSearch(focusTargetNode, function1);
            }
            if (i != 4) {
                throw new NoWhenBranchMatchedException();
            }
            if (!pickChildForBackwardSearch(focusTargetNode, function1)) {
                if (!(focusTargetNode.fetchFocusProperties$ui_release().canFocus ? ((Boolean) function1.mo781invoke(focusTargetNode)).booleanValue() : false)) {
                    return false;
                }
            }
            return true;
        }
        FocusTargetNode activeChild = FocusTraversalKt.getActiveChild(focusTargetNode);
        if (activeChild == null) {
            throw new IllegalStateException("ActiveParent must have a focusedChild");
        }
        int i2 = iArr[activeChild.getFocusState().ordinal()];
        if (i2 == 1) {
            if (!backwardFocusSearch(activeChild, function1)) {
                FocusDirection.Companion.getClass();
                if (m384generateAndSearchChildren4C6V_qg(focusTargetNode, activeChild, FocusDirection.Previous, function1) || (activeChild.fetchFocusProperties$ui_release().canFocus && ((Boolean) function1.mo781invoke(activeChild)).booleanValue())) {
                }
            }
            return true;
        }
        if (i2 == 2 || i2 == 3) {
            FocusDirection.Companion.getClass();
            return m384generateAndSearchChildren4C6V_qg(focusTargetNode, activeChild, FocusDirection.Previous, function1);
        }
        if (i2 != 4) {
            throw new NoWhenBranchMatchedException();
        }
        throw new IllegalStateException("ActiveParent must have a focusedChild");
    }

    public static final boolean forwardFocusSearch(FocusTargetNode focusTargetNode, Function1 function1) {
        int i = WhenMappings.$EnumSwitchMapping$0[focusTargetNode.getFocusState().ordinal()];
        if (i != 1) {
            if (i == 2 || i == 3) {
                return pickChildForForwardSearch(focusTargetNode, function1);
            }
            if (i == 4) {
                return focusTargetNode.fetchFocusProperties$ui_release().canFocus ? ((Boolean) function1.mo781invoke(focusTargetNode)).booleanValue() : pickChildForForwardSearch(focusTargetNode, function1);
            }
            throw new NoWhenBranchMatchedException();
        }
        FocusTargetNode activeChild = FocusTraversalKt.getActiveChild(focusTargetNode);
        if (activeChild == null) {
            throw new IllegalStateException("ActiveParent must have a focusedChild");
        }
        if (!forwardFocusSearch(activeChild, function1)) {
            FocusDirection.Companion.getClass();
            if (!m384generateAndSearchChildren4C6V_qg(focusTargetNode, activeChild, FocusDirection.Next, function1)) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: generateAndSearchChildren-4C6V_qg, reason: not valid java name */
    public static final boolean m384generateAndSearchChildren4C6V_qg(final FocusTargetNode focusTargetNode, final FocusTargetNode focusTargetNode2, final int i, final Function1 function1) {
        if (m385searchChildren4C6V_qg(focusTargetNode, focusTargetNode2, i, function1)) {
            return true;
        }
        final FocusTransactionManager focusTransactionManagerRequireTransactionManager = FocusTargetNodeKt.requireTransactionManager(focusTargetNode);
        final int i2 = focusTransactionManagerRequireTransactionManager.generation;
        final FocusTargetNode focusTargetNode3 = ((AndroidComposeView) DelegatableNodeKt.requireOwner(focusTargetNode)).focusOwner.activeFocusTargetNode;
        Boolean bool = (Boolean) BeyondBoundsLayoutKt.m367searchBeyondBoundsOMvw8(focusTargetNode, i, new Function1() { // from class: androidx.compose.ui.focus.OneDimensionalFocusSearchKt$generateAndSearchChildren$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                BeyondBoundsLayout.BeyondBoundsScope beyondBoundsScope = (BeyondBoundsLayout.BeyondBoundsScope) obj;
                if (i2 != focusTransactionManagerRequireTransactionManager.generation || (ComposeUiFlags.isTrackFocusEnabled && focusTargetNode3 != ((AndroidComposeView) DelegatableNodeKt.requireOwner(focusTargetNode)).focusOwner.activeFocusTargetNode)) {
                    return Boolean.TRUE;
                }
                boolean zM385searchChildren4C6V_qg = OneDimensionalFocusSearchKt.m385searchChildren4C6V_qg(focusTargetNode, focusTargetNode2, i, function1);
                Boolean boolValueOf = Boolean.valueOf(zM385searchChildren4C6V_qg);
                if (zM385searchChildren4C6V_qg || !beyondBoundsScope.getHasMoreContent()) {
                    return boolValueOf;
                }
                return null;
            }
        });
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    public static final boolean pickChildForBackwardSearch(FocusTargetNode focusTargetNode, Function1 function1) {
        MutableVector mutableVector = new MutableVector(new FocusTargetNode[16], 0);
        if (!focusTargetNode.node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("visitChildren called on an unattached node");
        }
        MutableVector mutableVector2 = new MutableVector(new Modifier.Node[16], 0);
        Modifier.Node node = focusTargetNode.node;
        Modifier.Node node2 = node.child;
        if (node2 == null) {
            DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector2, node);
        } else {
            mutableVector2.add(node2);
        }
        while (true) {
            int i = mutableVector2.size;
            if (i == 0) {
                break;
            }
            Modifier.Node nodeAccess$pop = (Modifier.Node) mutableVector2.removeAt(i - 1);
            if ((nodeAccess$pop.aggregateChildKindSet & 1024) == 0) {
                DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector2, nodeAccess$pop);
            } else {
                while (true) {
                    if (nodeAccess$pop == null) {
                        break;
                    }
                    if ((nodeAccess$pop.kindSet & 1024) != 0) {
                        MutableVector mutableVector3 = null;
                        while (nodeAccess$pop != null) {
                            if (nodeAccess$pop instanceof FocusTargetNode) {
                                mutableVector.add((FocusTargetNode) nodeAccess$pop);
                            } else if ((nodeAccess$pop.kindSet & 1024) != 0 && (nodeAccess$pop instanceof DelegatingNode)) {
                                int i2 = 0;
                                for (Modifier.Node node3 = ((DelegatingNode) nodeAccess$pop).delegate; node3 != null; node3 = node3.child) {
                                    if ((node3.kindSet & 1024) != 0) {
                                        i2++;
                                        if (i2 == 1) {
                                            nodeAccess$pop = node3;
                                        } else {
                                            if (mutableVector3 == null) {
                                                mutableVector3 = new MutableVector(new Modifier.Node[16], 0);
                                            }
                                            if (nodeAccess$pop != null) {
                                                mutableVector3.add(nodeAccess$pop);
                                                nodeAccess$pop = null;
                                            }
                                            mutableVector3.add(node3);
                                        }
                                    }
                                }
                                if (i2 == 1) {
                                }
                            }
                            nodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector3);
                        }
                    } else {
                        nodeAccess$pop = nodeAccess$pop.child;
                    }
                }
            }
        }
        mutableVector.sortWith(FocusableChildrenComparator.INSTANCE);
        int i3 = mutableVector.size - 1;
        Object[] objArr = mutableVector.content;
        if (i3 < objArr.length) {
            while (i3 >= 0) {
                FocusTargetNode focusTargetNode2 = (FocusTargetNode) objArr[i3];
                if (FocusTraversalKt.isEligibleForFocusSearch(focusTargetNode2) && backwardFocusSearch(focusTargetNode2, function1)) {
                    return true;
                }
                i3--;
            }
        }
        return false;
    }

    public static final boolean pickChildForForwardSearch(FocusTargetNode focusTargetNode, Function1 function1) {
        MutableVector mutableVector = new MutableVector(new FocusTargetNode[16], 0);
        if (!focusTargetNode.node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("visitChildren called on an unattached node");
        }
        MutableVector mutableVector2 = new MutableVector(new Modifier.Node[16], 0);
        Modifier.Node node = focusTargetNode.node;
        Modifier.Node node2 = node.child;
        if (node2 == null) {
            DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector2, node);
        } else {
            mutableVector2.add(node2);
        }
        while (true) {
            int i = mutableVector2.size;
            if (i == 0) {
                break;
            }
            Modifier.Node nodeAccess$pop = (Modifier.Node) mutableVector2.removeAt(i - 1);
            if ((nodeAccess$pop.aggregateChildKindSet & 1024) == 0) {
                DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector2, nodeAccess$pop);
            } else {
                while (true) {
                    if (nodeAccess$pop == null) {
                        break;
                    }
                    if ((nodeAccess$pop.kindSet & 1024) != 0) {
                        MutableVector mutableVector3 = null;
                        while (nodeAccess$pop != null) {
                            if (nodeAccess$pop instanceof FocusTargetNode) {
                                mutableVector.add((FocusTargetNode) nodeAccess$pop);
                            } else if ((nodeAccess$pop.kindSet & 1024) != 0 && (nodeAccess$pop instanceof DelegatingNode)) {
                                int i2 = 0;
                                for (Modifier.Node node3 = ((DelegatingNode) nodeAccess$pop).delegate; node3 != null; node3 = node3.child) {
                                    if ((node3.kindSet & 1024) != 0) {
                                        i2++;
                                        if (i2 == 1) {
                                            nodeAccess$pop = node3;
                                        } else {
                                            if (mutableVector3 == null) {
                                                mutableVector3 = new MutableVector(new Modifier.Node[16], 0);
                                            }
                                            if (nodeAccess$pop != null) {
                                                mutableVector3.add(nodeAccess$pop);
                                                nodeAccess$pop = null;
                                            }
                                            mutableVector3.add(node3);
                                        }
                                    }
                                }
                                if (i2 == 1) {
                                }
                            }
                            nodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector3);
                        }
                    } else {
                        nodeAccess$pop = nodeAccess$pop.child;
                    }
                }
            }
        }
        mutableVector.sortWith(FocusableChildrenComparator.INSTANCE);
        Object[] objArr = mutableVector.content;
        int i3 = mutableVector.size;
        for (int i4 = 0; i4 < i3; i4++) {
            FocusTargetNode focusTargetNode2 = (FocusTargetNode) objArr[i4];
            if (FocusTraversalKt.isEligibleForFocusSearch(focusTargetNode2) && forwardFocusSearch(focusTargetNode2, function1)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:71:0x0102, code lost:
    
        return true;
     */
    /* JADX WARN: Removed duplicated region for block: B:126:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x01a5 A[EDGE_INSN: B:155:0x01a5->B:124:0x01a5 BREAK  A[LOOP:5: B:86:0x013a->B:160:0x013a], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x013c  */
    /* renamed from: searchChildren-4C6V_qg, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final boolean m385searchChildren4C6V_qg(FocusTargetNode focusTargetNode, FocusTargetNode focusTargetNode2, int i, Function1 function1) {
        Modifier.Node node;
        LayoutNode layoutNodeRequireLayoutNode;
        NodeChain nodeChain;
        if (focusTargetNode.getFocusState() != FocusStateImpl.ActiveParent) {
            throw new IllegalStateException("This function should only be used within a parent that has focus.");
        }
        MutableVector mutableVector = new MutableVector(new FocusTargetNode[16], 0);
        if (!focusTargetNode.node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("visitChildren called on an unattached node");
        }
        MutableVector mutableVector2 = new MutableVector(new Modifier.Node[16], 0);
        Modifier.Node node2 = focusTargetNode.node;
        Modifier.Node node3 = node2.child;
        if (node3 == null) {
            DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector2, node2);
        } else {
            mutableVector2.add(node3);
        }
        while (true) {
            int i2 = mutableVector2.size;
            node = null;
            if (i2 == 0) {
                break;
            }
            Modifier.Node nodeAccess$pop = (Modifier.Node) mutableVector2.removeAt(i2 - 1);
            if ((nodeAccess$pop.aggregateChildKindSet & 1024) == 0) {
                DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector2, nodeAccess$pop);
            } else {
                while (true) {
                    if (nodeAccess$pop == null) {
                        break;
                    }
                    if ((nodeAccess$pop.kindSet & 1024) != 0) {
                        MutableVector mutableVector3 = null;
                        while (nodeAccess$pop != null) {
                            if (nodeAccess$pop instanceof FocusTargetNode) {
                                mutableVector.add((FocusTargetNode) nodeAccess$pop);
                            } else if ((nodeAccess$pop.kindSet & 1024) != 0 && (nodeAccess$pop instanceof DelegatingNode)) {
                                int i3 = 0;
                                for (Modifier.Node node4 = ((DelegatingNode) nodeAccess$pop).delegate; node4 != null; node4 = node4.child) {
                                    if ((node4.kindSet & 1024) != 0) {
                                        i3++;
                                        if (i3 == 1) {
                                            nodeAccess$pop = node4;
                                        } else {
                                            if (mutableVector3 == null) {
                                                mutableVector3 = new MutableVector(new Modifier.Node[16], 0);
                                            }
                                            if (nodeAccess$pop != null) {
                                                mutableVector3.add(nodeAccess$pop);
                                                nodeAccess$pop = null;
                                            }
                                            mutableVector3.add(node4);
                                        }
                                    }
                                }
                                if (i3 == 1) {
                                }
                            }
                            nodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector3);
                        }
                    } else {
                        nodeAccess$pop = nodeAccess$pop.child;
                    }
                }
            }
        }
        mutableVector.sortWith(FocusableChildrenComparator.INSTANCE);
        FocusDirection.Companion.getClass();
        if (i != FocusDirection.Next) {
            if (i != FocusDirection.Previous) {
                throw new IllegalStateException("This function should only be used for 1-D focus search");
            }
            IntRange intRangeUntil = RangesKt___RangesKt.until(0, mutableVector.size);
            int i4 = intRangeUntil.first;
            int i5 = intRangeUntil.last;
            if (i4 <= i5) {
                boolean z = false;
                while (true) {
                    if (z) {
                        FocusTargetNode focusTargetNode3 = (FocusTargetNode) mutableVector.content[i5];
                        if (FocusTraversalKt.isEligibleForFocusSearch(focusTargetNode3) && backwardFocusSearch(focusTargetNode3, function1)) {
                            break;
                        }
                    }
                    if (Intrinsics.areEqual(mutableVector.content[i5], focusTargetNode2)) {
                        z = true;
                    }
                    if (i5 == i4) {
                        break;
                    }
                    i5--;
                }
            }
            FocusDirection.Companion.getClass();
            if (i != FocusDirection.Next) {
                if (!focusTargetNode.node.isAttached) {
                }
                Modifier.Node node5 = focusTargetNode.node.parent;
                layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(focusTargetNode);
                loop5: while (true) {
                    if (layoutNodeRequireLayoutNode == null) {
                    }
                }
                if (node != null) {
                }
            }
            return false;
        }
        IntRange intRangeUntil2 = RangesKt___RangesKt.until(0, mutableVector.size);
        int i6 = intRangeUntil2.first;
        int i7 = intRangeUntil2.last;
        if (i6 <= i7) {
            boolean z2 = false;
            while (true) {
                if (z2) {
                    FocusTargetNode focusTargetNode4 = (FocusTargetNode) mutableVector.content[i6];
                    if (FocusTraversalKt.isEligibleForFocusSearch(focusTargetNode4) && forwardFocusSearch(focusTargetNode4, function1)) {
                        break;
                    }
                }
                if (Intrinsics.areEqual(mutableVector.content[i6], focusTargetNode2)) {
                    z2 = true;
                }
                if (i6 == i7) {
                    break;
                }
                i6++;
            }
        }
        FocusDirection.Companion.getClass();
        if (i != FocusDirection.Next && focusTargetNode.fetchFocusProperties$ui_release().canFocus) {
            if (!focusTargetNode.node.isAttached) {
                InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
            }
            Modifier.Node node52 = focusTargetNode.node.parent;
            layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(focusTargetNode);
            loop5: while (true) {
                if (layoutNodeRequireLayoutNode == null) {
                    break;
                }
                if ((layoutNodeRequireLayoutNode.nodes.head.aggregateChildKindSet & 1024) != 0) {
                    while (node52 != null) {
                        if ((node52.kindSet & 1024) != 0) {
                            Modifier.Node nodeAccess$pop2 = node52;
                            MutableVector mutableVector4 = null;
                            while (nodeAccess$pop2 != null) {
                                if (nodeAccess$pop2 instanceof FocusTargetNode) {
                                    node = nodeAccess$pop2;
                                    break loop5;
                                }
                                if ((nodeAccess$pop2.kindSet & 1024) != 0 && (nodeAccess$pop2 instanceof DelegatingNode)) {
                                    int i8 = 0;
                                    for (Modifier.Node node6 = ((DelegatingNode) nodeAccess$pop2).delegate; node6 != null; node6 = node6.child) {
                                        if ((node6.kindSet & 1024) != 0) {
                                            i8++;
                                            if (i8 == 1) {
                                                nodeAccess$pop2 = node6;
                                            } else {
                                                if (mutableVector4 == null) {
                                                    mutableVector4 = new MutableVector(new Modifier.Node[16], 0);
                                                }
                                                if (nodeAccess$pop2 != null) {
                                                    mutableVector4.add(nodeAccess$pop2);
                                                    nodeAccess$pop2 = null;
                                                }
                                                mutableVector4.add(node6);
                                            }
                                        }
                                    }
                                    if (i8 == 1) {
                                    }
                                }
                                nodeAccess$pop2 = DelegatableNodeKt.access$pop(mutableVector4);
                            }
                        }
                        node52 = node52.parent;
                    }
                }
                layoutNodeRequireLayoutNode = layoutNodeRequireLayoutNode.getParent$ui_release();
                node52 = (layoutNodeRequireLayoutNode == null || (nodeChain = layoutNodeRequireLayoutNode.nodes) == null) ? null : nodeChain.tail;
            }
            if (node != null) {
                return ((Boolean) function1.mo781invoke(focusTargetNode)).booleanValue();
            }
        }
        return false;
    }
}
