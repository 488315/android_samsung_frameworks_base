package androidx.compose.ui.focus;

import androidx.collection.MutableScatterSet;
import androidx.collection.ScatterSetKt;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.node.DelegatableNode;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.NodeChain;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FocusInvalidationManager {
    public final Function0 activeFocusTargetNodeFetcher;
    public final Function0 invalidateOwnerFocusState;
    public boolean isInvalidationScheduled;
    public final Function1 onRequestApplyChangesListener;
    public final Function0 rootFocusStateFetcher;
    public final MutableScatterSet focusTargetNodes = ScatterSetKt.mutableScatterSetOf();
    public final MutableScatterSet focusEventNodes = ScatterSetKt.mutableScatterSetOf();
    public final List focusTargetNodesLegacy = new ArrayList();
    public final List focusEventNodesLegacy = new ArrayList();
    public final List focusPropertiesNodesLegacy = new ArrayList();
    public final List focusTargetsWithInvalidatedFocusEventsLegacy = new ArrayList();

    public FocusInvalidationManager(Function1 function1, Function0 function0, Function0 function02, Function0 function03) {
        this.onRequestApplyChangesListener = function1;
        this.invalidateOwnerFocusState = function0;
        this.rootFocusStateFetcher = function02;
        this.activeFocusTargetNodeFetcher = function03;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void access$invalidateNodes(FocusInvalidationManager focusInvalidationManager) {
        int i;
        int i2;
        FocusStateImpl focusStateImpl;
        int i3;
        NodeChain nodeChain;
        char c;
        focusInvalidationManager.getClass();
        boolean z = ComposeUiFlags.isTrackFocusEnabled;
        int i4 = 1;
        Function0 function0 = focusInvalidationManager.invalidateOwnerFocusState;
        int i5 = 0;
        if (z) {
            FocusTargetNode focusTargetNode = (FocusTargetNode) focusInvalidationManager.activeFocusTargetNodeFetcher.invoke();
            MutableScatterSet mutableScatterSet = focusInvalidationManager.focusTargetNodes;
            MutableScatterSet mutableScatterSet2 = focusInvalidationManager.focusEventNodes;
            if (focusTargetNode == null) {
                Object[] objArr = mutableScatterSet2.elements;
                long[] jArr = mutableScatterSet2.metadata;
                int length = jArr.length - 2;
                if (length >= 0) {
                    int i6 = 0;
                    while (true) {
                        long j = jArr[i6];
                        char c2 = 7;
                        if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i7 = 8 - ((~(i6 - length)) >>> 31);
                            int i8 = 0;
                            while (i8 < i7) {
                                if ((j & 255) < 128) {
                                    c = c2;
                                    ((FocusEventModifierNode) objArr[(i6 << 3) + i8]).onFocusEvent(FocusStateImpl.Inactive);
                                } else {
                                    c = c2;
                                }
                                j >>= 8;
                                i8++;
                                c2 = c;
                            }
                            if (i7 != 8) {
                                break;
                            }
                        }
                        if (i6 == length) {
                            break;
                        } else {
                            i6++;
                        }
                    }
                }
            } else if (focusTargetNode.isAttached) {
                if (mutableScatterSet.contains(focusTargetNode)) {
                    focusTargetNode.invalidateFocus$ui_release();
                }
                if (!focusTargetNode.node.isAttached) {
                    InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
                }
                Modifier.Node node = focusTargetNode.node;
                LayoutNode requireLayoutNode = DelegatableNodeKt.requireLayoutNode(focusTargetNode);
                boolean z2 = false;
                while (requireLayoutNode != null) {
                    if ((requireLayoutNode.nodes.head.aggregateChildKindSet & 5120) != 0) {
                        while (node != null) {
                            if ((node.kindSet & 5120) != 0) {
                                if ((node instanceof FocusTargetNode) && node != focusTargetNode) {
                                    z2 = true;
                                }
                                if ((node instanceof FocusEventModifierNode) && mutableScatterSet2.contains(node)) {
                                    if (z2) {
                                        ((FocusEventModifierNode) node).onFocusEvent(FocusStateImpl.ActiveParent);
                                    } else {
                                        ((FocusEventModifierNode) node).onFocusEvent(focusTargetNode.getFocusState());
                                    }
                                    mutableScatterSet2.remove(node);
                                }
                            }
                            node = node.parent;
                        }
                    }
                    requireLayoutNode = requireLayoutNode.getParent$ui_release();
                    node = (requireLayoutNode == null || (nodeChain = requireLayoutNode.nodes) == null) ? null : nodeChain.tail;
                }
                Object[] objArr2 = mutableScatterSet2.elements;
                long[] jArr2 = mutableScatterSet2.metadata;
                int length2 = jArr2.length - 2;
                if (length2 >= 0) {
                    int i9 = 0;
                    while (true) {
                        long j2 = jArr2[i9];
                        if ((((~j2) << 7) & j2 & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i10 = 8 - ((~(i9 - length2)) >>> 31);
                            for (int i11 = 0; i11 < i10; i11++) {
                                if ((j2 & 255) < 128) {
                                    ((FocusEventModifierNode) objArr2[(i9 << 3) + i11]).onFocusEvent(FocusStateImpl.Inactive);
                                }
                                j2 >>= 8;
                            }
                            if (i10 != 8) {
                                break;
                            }
                        }
                        if (i9 == length2) {
                            break;
                        } else {
                            i9++;
                        }
                    }
                }
            }
            function0.invoke();
            mutableScatterSet.clear();
            mutableScatterSet2.clear();
            focusInvalidationManager.isInvalidationScheduled = false;
            return;
        }
        if (!((FocusStateImpl) ((FocusState) focusInvalidationManager.rootFocusStateFetcher.invoke())).getHasFocus()) {
            ArrayList arrayList = (ArrayList) focusInvalidationManager.focusEventNodesLegacy;
            int size = arrayList.size();
            for (int i12 = 0; i12 < size; i12++) {
                ((FocusEventModifierNode) arrayList.get(i12)).onFocusEvent(FocusStateImpl.Inactive);
            }
            ArrayList arrayList2 = (ArrayList) focusInvalidationManager.focusTargetNodesLegacy;
            int size2 = arrayList2.size();
            while (i5 < size2) {
                FocusTargetNode focusTargetNode2 = (FocusTargetNode) arrayList2.get(i5);
                if (focusTargetNode2.isAttached && !focusTargetNode2.isInitialized$ui_release()) {
                    focusTargetNode2.initializeFocusState$ui_release(FocusStateImpl.Inactive);
                }
                i5++;
            }
            ((ArrayList) focusInvalidationManager.focusTargetNodesLegacy).clear();
            ((ArrayList) focusInvalidationManager.focusEventNodesLegacy).clear();
            ((ArrayList) focusInvalidationManager.focusPropertiesNodesLegacy).clear();
            ((ArrayList) focusInvalidationManager.focusTargetsWithInvalidatedFocusEventsLegacy).clear();
            function0.invoke();
            return;
        }
        ArrayList arrayList3 = (ArrayList) focusInvalidationManager.focusPropertiesNodesLegacy;
        int size3 = arrayList3.size();
        for (int i13 = 0; i13 < size3; i13++) {
            Modifier.Node node2 = (Modifier.Node) ((FocusPropertiesModifierNode) arrayList3.get(i13));
            Modifier.Node node3 = node2.node;
            if (node3.isAttached) {
                MutableVector mutableVector = null;
                while (node3 != null) {
                    if (node3 instanceof FocusTargetNode) {
                        ((ArrayList) focusInvalidationManager.focusTargetNodesLegacy).add((FocusTargetNode) node3);
                    } else if ((node3.kindSet & 1024) != 0 && (node3 instanceof DelegatingNode)) {
                        int i14 = 0;
                        for (Modifier.Node node4 = ((DelegatingNode) node3).delegate; node4 != null; node4 = node4.child) {
                            if ((node4.kindSet & 1024) != 0) {
                                i14++;
                                if (i14 == 1) {
                                    node3 = node4;
                                } else {
                                    if (mutableVector == null) {
                                        mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                    }
                                    if (node3 != null) {
                                        mutableVector.add(node3);
                                        node3 = null;
                                    }
                                    mutableVector.add(node4);
                                }
                            }
                        }
                        if (i14 == 1) {
                        }
                    }
                    node3 = DelegatableNodeKt.access$pop(mutableVector);
                }
                if (!node2.node.isAttached) {
                    InlineClassHelperKt.throwIllegalStateException("visitChildren called on an unattached node");
                }
                MutableVector mutableVector2 = new MutableVector(new Modifier.Node[16], 0);
                Modifier.Node node5 = node2.node;
                Modifier.Node node6 = node5.child;
                if (node6 == null) {
                    DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector2, node5);
                } else {
                    mutableVector2.add(node6);
                }
                while (true) {
                    int i15 = mutableVector2.size;
                    if (i15 != 0) {
                        Modifier.Node node7 = (Modifier.Node) mutableVector2.removeAt(i15 - 1);
                        if ((node7.aggregateChildKindSet & 1024) == 0) {
                            DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector2, node7);
                        } else {
                            while (true) {
                                if (node7 == null) {
                                    break;
                                }
                                if ((node7.kindSet & 1024) != 0) {
                                    MutableVector mutableVector3 = null;
                                    while (node7 != null) {
                                        if (node7 instanceof FocusTargetNode) {
                                            ((ArrayList) focusInvalidationManager.focusTargetNodesLegacy).add((FocusTargetNode) node7);
                                        } else if ((node7.kindSet & 1024) != 0 && (node7 instanceof DelegatingNode)) {
                                            int i16 = 0;
                                            for (Modifier.Node node8 = ((DelegatingNode) node7).delegate; node8 != null; node8 = node8.child) {
                                                if ((node8.kindSet & 1024) != 0) {
                                                    i16++;
                                                    if (i16 == 1) {
                                                        node7 = node8;
                                                    } else {
                                                        if (mutableVector3 == null) {
                                                            mutableVector3 = new MutableVector(new Modifier.Node[16], 0);
                                                        }
                                                        if (node7 != null) {
                                                            mutableVector3.add(node7);
                                                            node7 = null;
                                                        }
                                                        mutableVector3.add(node8);
                                                    }
                                                }
                                            }
                                            if (i16 == 1) {
                                            }
                                        }
                                        node7 = DelegatableNodeKt.access$pop(mutableVector3);
                                    }
                                } else {
                                    node7 = node7.child;
                                }
                            }
                        }
                    }
                }
            }
        }
        ((ArrayList) focusInvalidationManager.focusPropertiesNodesLegacy).clear();
        ArrayList arrayList4 = (ArrayList) focusInvalidationManager.focusEventNodesLegacy;
        int size4 = arrayList4.size();
        int i17 = 0;
        while (i17 < size4) {
            FocusEventModifierNode focusEventModifierNode = (FocusEventModifierNode) arrayList4.get(i17);
            Modifier.Node node9 = (Modifier.Node) focusEventModifierNode;
            Modifier.Node node10 = node9.node;
            if (node10.isAttached) {
                int i18 = i4;
                int i19 = i5;
                FocusTargetNode focusTargetNode3 = null;
                MutableVector mutableVector4 = null;
                while (node10 != null) {
                    if (node10 instanceof FocusTargetNode) {
                        FocusTargetNode focusTargetNode4 = (FocusTargetNode) node10;
                        if (focusTargetNode3 != null) {
                            i19 = i4;
                        }
                        if (((ArrayList) focusInvalidationManager.focusTargetNodesLegacy).contains(focusTargetNode4)) {
                            ((ArrayList) focusInvalidationManager.focusTargetsWithInvalidatedFocusEventsLegacy).add(focusTargetNode4);
                            i18 = i5;
                        }
                        focusTargetNode3 = focusTargetNode4;
                    } else if ((node10.kindSet & 1024) != 0 && (node10 instanceof DelegatingNode)) {
                        Modifier.Node node11 = ((DelegatingNode) node10).delegate;
                        while (node11 != null) {
                            if ((node11.kindSet & 1024) != 0) {
                                i5++;
                                if (i5 == i4) {
                                    node10 = node11;
                                } else {
                                    if (mutableVector4 == null) {
                                        mutableVector4 = new MutableVector(new Modifier.Node[16], 0);
                                    }
                                    if (node10 != null) {
                                        mutableVector4.add(node10);
                                        node10 = null;
                                    }
                                    mutableVector4.add(node11);
                                }
                            }
                            node11 = node11.child;
                            i4 = 1;
                        }
                        if (i5 == i4) {
                            i5 = 0;
                        }
                    }
                    node10 = DelegatableNodeKt.access$pop(mutableVector4);
                    i4 = 1;
                    i5 = 0;
                }
                if (!node9.node.isAttached) {
                    InlineClassHelperKt.throwIllegalStateException("visitChildren called on an unattached node");
                }
                MutableVector mutableVector5 = new MutableVector(new Modifier.Node[16], 0);
                Modifier.Node node12 = node9.node;
                Modifier.Node node13 = node12.child;
                if (node13 == null) {
                    DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector5, node12);
                } else {
                    mutableVector5.add(node13);
                }
                while (true) {
                    int i20 = mutableVector5.size;
                    if (i20 == 0) {
                        break;
                    }
                    Modifier.Node node14 = (Modifier.Node) mutableVector5.removeAt(i20 - 1);
                    if ((node14.aggregateChildKindSet & 1024) == 0) {
                        DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector5, node14);
                    } else {
                        while (node14 != null) {
                            if ((node14.kindSet & 1024) != 0) {
                                MutableVector mutableVector6 = null;
                                while (node14 != null) {
                                    if (node14 instanceof FocusTargetNode) {
                                        FocusTargetNode focusTargetNode5 = (FocusTargetNode) node14;
                                        if (focusTargetNode3 != null) {
                                            i19 = 1;
                                        }
                                        if (((ArrayList) focusInvalidationManager.focusTargetNodesLegacy).contains(focusTargetNode5)) {
                                            ((ArrayList) focusInvalidationManager.focusTargetsWithInvalidatedFocusEventsLegacy).add(focusTargetNode5);
                                            i3 = 0;
                                        } else {
                                            i3 = i18;
                                        }
                                        focusTargetNode3 = focusTargetNode5;
                                        i18 = i3;
                                    } else if ((node14.kindSet & 1024) != 0 && (node14 instanceof DelegatingNode)) {
                                        int i21 = 0;
                                        for (Modifier.Node node15 = ((DelegatingNode) node14).delegate; node15 != null; node15 = node15.child) {
                                            if ((node15.kindSet & 1024) != 0) {
                                                i21++;
                                                if (i21 == 1) {
                                                    node14 = node15;
                                                } else {
                                                    if (mutableVector6 == null) {
                                                        mutableVector6 = new MutableVector(new Modifier.Node[16], 0);
                                                    }
                                                    if (node14 != null) {
                                                        mutableVector6.add(node14);
                                                        node14 = null;
                                                    }
                                                    mutableVector6.add(node15);
                                                }
                                            }
                                        }
                                        if (i21 != 1) {
                                            node14 = DelegatableNodeKt.access$pop(mutableVector6);
                                        }
                                    }
                                    node14 = DelegatableNodeKt.access$pop(mutableVector6);
                                }
                            } else {
                                node14 = node14.child;
                            }
                        }
                    }
                }
                i = 1;
                i2 = 0;
                if (i18 != 0) {
                    if (i19 != 0) {
                        focusStateImpl = FocusEventModifierNodeKt.getFocusState(focusEventModifierNode);
                    } else if (focusTargetNode3 == null || (focusStateImpl = focusTargetNode3.getFocusState()) == null) {
                        focusStateImpl = FocusStateImpl.Inactive;
                    }
                    focusEventModifierNode.onFocusEvent(focusStateImpl);
                }
            } else {
                focusEventModifierNode.onFocusEvent(FocusStateImpl.Inactive);
                i = i4;
                i2 = i5;
            }
            i17++;
            i4 = i;
            i5 = i2;
        }
        ((ArrayList) focusInvalidationManager.focusEventNodesLegacy).clear();
        ArrayList arrayList5 = (ArrayList) focusInvalidationManager.focusTargetNodesLegacy;
        int size5 = arrayList5.size();
        while (i5 < size5) {
            FocusTargetNode focusTargetNode6 = (FocusTargetNode) arrayList5.get(i5);
            if (focusTargetNode6.isAttached) {
                FocusStateImpl focusState = focusTargetNode6.getFocusState();
                focusTargetNode6.invalidateFocus$ui_release();
                if (focusState != focusTargetNode6.getFocusState() || ((ArrayList) focusInvalidationManager.focusTargetsWithInvalidatedFocusEventsLegacy).contains(focusTargetNode6)) {
                    focusTargetNode6.dispatchFocusCallbacks$ui_release();
                }
            }
            i5++;
        }
        ((ArrayList) focusInvalidationManager.focusTargetNodesLegacy).clear();
        ((ArrayList) focusInvalidationManager.focusTargetsWithInvalidatedFocusEventsLegacy).clear();
        function0.invoke();
        if (!((ArrayList) focusInvalidationManager.focusPropertiesNodesLegacy).isEmpty()) {
            InlineClassHelperKt.throwIllegalStateException("Unprocessed FocusProperties nodes");
        }
        if (!((ArrayList) focusInvalidationManager.focusEventNodesLegacy).isEmpty()) {
            InlineClassHelperKt.throwIllegalStateException("Unprocessed FocusEvent nodes");
        }
        if (((ArrayList) focusInvalidationManager.focusTargetNodesLegacy).isEmpty()) {
            return;
        }
        InlineClassHelperKt.throwIllegalStateException("Unprocessed FocusTarget nodes");
    }

    public final boolean hasPendingInvalidation() {
        return ComposeUiFlags.isTrackFocusEnabled ? this.isInvalidationScheduled : (((ArrayList) this.focusTargetNodesLegacy).isEmpty() && ((ArrayList) this.focusPropertiesNodesLegacy).isEmpty() && ((ArrayList) this.focusEventNodesLegacy).isEmpty()) ? false : true;
    }

    public final void scheduleInvalidation(MutableScatterSet mutableScatterSet, DelegatableNode delegatableNode) {
        if (!mutableScatterSet.add(delegatableNode) || this.isInvalidationScheduled) {
            return;
        }
        this.onRequestApplyChangesListener.mo779invoke(new FocusInvalidationManager$setUpOnRequestApplyChangesListener$1(this));
        this.isInvalidationScheduled = true;
    }

    public final void scheduleInvalidationLegacy(List list, Object obj) {
        if (list.add(obj)) {
            if (((ArrayList) this.focusPropertiesNodesLegacy).size() + ((ArrayList) this.focusEventNodesLegacy).size() + ((ArrayList) this.focusTargetNodesLegacy).size() == 1) {
                this.onRequestApplyChangesListener.mo779invoke(new FocusInvalidationManager$scheduleInvalidationLegacy$1(this));
            }
        }
    }
}
