package androidx.compose.ui.focus;

import androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsInfo;
import androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsModifierNode;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.BeyondBoundsLayout;
import androidx.compose.ui.modifier.ProvidableModifierLocal;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.NodeChain;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;

/* loaded from: classes.dex */
public abstract class BeyondBoundsLayoutKt {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:52:0x009e  */
    /* JADX WARN: Type inference failed for: r5v0, types: [T, androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsInfo$Interval, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v0, types: [T, androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsInfo$Interval, java.lang.Object] */
    /* renamed from: searchBeyondBounds--OM-vw8, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m367searchBeyondBoundsOMvw8(FocusTargetNode focusTargetNode, int i, Function1 function1) {
        int i2;
        Object objMo781invoke;
        Modifier.Node nodeAccess$pop;
        final int i3;
        NodeChain nodeChain;
        if (!focusTargetNode.node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
        }
        Modifier.Node node = focusTargetNode.node.parent;
        LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(focusTargetNode);
        loop0: while (true) {
            i2 = 0;
            objMo781invoke = null;
            if (layoutNodeRequireLayoutNode == null) {
                nodeAccess$pop = null;
                break;
            }
            if ((layoutNodeRequireLayoutNode.nodes.head.aggregateChildKindSet & 1024) != 0) {
                while (node != null) {
                    if ((node.kindSet & 1024) != 0) {
                        nodeAccess$pop = node;
                        MutableVector mutableVector = null;
                        while (nodeAccess$pop != null) {
                            if (nodeAccess$pop instanceof FocusTargetNode) {
                                break loop0;
                            }
                            if ((nodeAccess$pop.kindSet & 1024) != 0 && (nodeAccess$pop instanceof DelegatingNode)) {
                                int i4 = 0;
                                for (Modifier.Node node2 = ((DelegatingNode) nodeAccess$pop).delegate; node2 != null; node2 = node2.child) {
                                    if ((node2.kindSet & 1024) != 0) {
                                        i4++;
                                        if (i4 == 1) {
                                            nodeAccess$pop = node2;
                                        } else {
                                            if (mutableVector == null) {
                                                mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                            }
                                            if (nodeAccess$pop != null) {
                                                mutableVector.add(nodeAccess$pop);
                                                nodeAccess$pop = null;
                                            }
                                            mutableVector.add(node2);
                                        }
                                    }
                                }
                                if (i4 == 1) {
                                }
                            }
                            nodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector);
                        }
                    }
                    node = node.parent;
                }
            }
            layoutNodeRequireLayoutNode = layoutNodeRequireLayoutNode.getParent$ui_release();
            node = (layoutNodeRequireLayoutNode == null || (nodeChain = layoutNodeRequireLayoutNode.nodes) == null) ? null : nodeChain.tail;
        }
        FocusTargetNode focusTargetNode2 = (FocusTargetNode) nodeAccess$pop;
        if (focusTargetNode2 != null) {
            ProvidableModifierLocal providableModifierLocal = androidx.compose.ui.layout.BeyondBoundsLayoutKt.ModifierLocalBeyondBoundsLayout;
            if (!Intrinsics.areEqual((BeyondBoundsLayout) focusTargetNode2.getCurrent(providableModifierLocal), (BeyondBoundsLayout) focusTargetNode.getCurrent(providableModifierLocal))) {
                BeyondBoundsLayout beyondBoundsLayout = (BeyondBoundsLayout) focusTargetNode.getCurrent(androidx.compose.ui.layout.BeyondBoundsLayoutKt.ModifierLocalBeyondBoundsLayout);
                if (beyondBoundsLayout != null) {
                    FocusDirection.Companion.getClass();
                    if (i == FocusDirection.Up) {
                        BeyondBoundsLayout.LayoutDirection.Companion.getClass();
                        i3 = BeyondBoundsLayout.LayoutDirection.Above;
                    } else if (i == FocusDirection.Down) {
                        BeyondBoundsLayout.LayoutDirection.Companion.getClass();
                        i3 = BeyondBoundsLayout.LayoutDirection.Below;
                    } else if (i == FocusDirection.Left) {
                        BeyondBoundsLayout.LayoutDirection.Companion.getClass();
                        i3 = BeyondBoundsLayout.LayoutDirection.Left;
                    } else if (i == FocusDirection.Right) {
                        BeyondBoundsLayout.LayoutDirection.Companion.getClass();
                        i3 = BeyondBoundsLayout.LayoutDirection.Right;
                    } else if (i == FocusDirection.Next) {
                        BeyondBoundsLayout.LayoutDirection.Companion.getClass();
                        i3 = BeyondBoundsLayout.LayoutDirection.After;
                    } else {
                        if (i != FocusDirection.Previous) {
                            throw new IllegalStateException("Unsupported direction for beyond bounds layout");
                        }
                        BeyondBoundsLayout.LayoutDirection.Companion.getClass();
                        i3 = BeyondBoundsLayout.LayoutDirection.Before;
                    }
                    final LazyLayoutBeyondBoundsModifierNode lazyLayoutBeyondBoundsModifierNode = (LazyLayoutBeyondBoundsModifierNode) beyondBoundsLayout;
                    if (lazyLayoutBeyondBoundsModifierNode.state.getItemCount() <= 0 || !lazyLayoutBeyondBoundsModifierNode.state.getHasVisibleItems() || !lazyLayoutBeyondBoundsModifierNode.isAttached) {
                        return function1.mo781invoke(LazyLayoutBeyondBoundsModifierNode.emptyBeyondBoundsScope);
                    }
                    int lastPlacedIndex = lazyLayoutBeyondBoundsModifierNode.m166isForward4vf7U8o(i3) ? lazyLayoutBeyondBoundsModifierNode.state.getLastPlacedIndex() : lazyLayoutBeyondBoundsModifierNode.state.getFirstPlacedIndex();
                    final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                    LazyLayoutBeyondBoundsInfo lazyLayoutBeyondBoundsInfo = lazyLayoutBeyondBoundsModifierNode.beyondBoundsInfo;
                    lazyLayoutBeyondBoundsInfo.getClass();
                    ?? interval = new LazyLayoutBeyondBoundsInfo.Interval(lastPlacedIndex, lastPlacedIndex);
                    lazyLayoutBeyondBoundsInfo.beyondBoundsItems.add(interval);
                    ref$ObjectRef.element = interval;
                    int iItemsPerViewport = lazyLayoutBeyondBoundsModifierNode.state.itemsPerViewport() * 2;
                    int itemCount = lazyLayoutBeyondBoundsModifierNode.state.getItemCount();
                    if (iItemsPerViewport > itemCount) {
                        iItemsPerViewport = itemCount;
                    }
                    while (objMo781invoke == null && lazyLayoutBeyondBoundsModifierNode.m165hasMoreContentFR3nfPY((LazyLayoutBeyondBoundsInfo.Interval) ref$ObjectRef.element, i3) && i2 < iItemsPerViewport) {
                        LazyLayoutBeyondBoundsInfo.Interval interval2 = (LazyLayoutBeyondBoundsInfo.Interval) ref$ObjectRef.element;
                        int i5 = interval2.start;
                        boolean zM166isForward4vf7U8o = lazyLayoutBeyondBoundsModifierNode.m166isForward4vf7U8o(i3);
                        int i6 = interval2.end;
                        if (zM166isForward4vf7U8o) {
                            i6++;
                        } else {
                            i5--;
                        }
                        LazyLayoutBeyondBoundsInfo lazyLayoutBeyondBoundsInfo2 = lazyLayoutBeyondBoundsModifierNode.beyondBoundsInfo;
                        lazyLayoutBeyondBoundsInfo2.getClass();
                        ?? interval3 = new LazyLayoutBeyondBoundsInfo.Interval(i5, i6);
                        lazyLayoutBeyondBoundsInfo2.beyondBoundsItems.add(interval3);
                        lazyLayoutBeyondBoundsModifierNode.beyondBoundsInfo.beyondBoundsItems.remove((LazyLayoutBeyondBoundsInfo.Interval) ref$ObjectRef.element);
                        ref$ObjectRef.element = interval3;
                        i2++;
                        DelegatableNodeKt.requireLayoutNode(lazyLayoutBeyondBoundsModifierNode).forceRemeasure();
                        objMo781invoke = function1.mo781invoke(new BeyondBoundsLayout.BeyondBoundsScope() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsModifierNode$layout$2
                            /* JADX WARN: Multi-variable type inference failed */
                            @Override // androidx.compose.ui.layout.BeyondBoundsLayout.BeyondBoundsScope
                            public final boolean getHasMoreContent() {
                                LazyLayoutBeyondBoundsInfo.Interval interval4 = (LazyLayoutBeyondBoundsInfo.Interval) ref$ObjectRef.element;
                                LazyLayoutBeyondBoundsModifierNode$Companion$emptyBeyondBoundsScope$1 lazyLayoutBeyondBoundsModifierNode$Companion$emptyBeyondBoundsScope$1 = LazyLayoutBeyondBoundsModifierNode.emptyBeyondBoundsScope;
                                return lazyLayoutBeyondBoundsModifierNode.m165hasMoreContentFR3nfPY(interval4, i3);
                            }
                        });
                    }
                    lazyLayoutBeyondBoundsModifierNode.beyondBoundsInfo.beyondBoundsItems.remove((LazyLayoutBeyondBoundsInfo.Interval) ref$ObjectRef.element);
                    DelegatableNodeKt.requireLayoutNode(lazyLayoutBeyondBoundsModifierNode).forceRemeasure();
                    return objMo781invoke;
                }
            }
        }
        return null;
    }
}
