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
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

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

    /* renamed from: androidx.compose.ui.focus.FocusInvalidationManager$scheduleInvalidationLegacy$1, reason: invalid class name */
    final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function0 {
        public AnonymousClass1(Object obj) {
            super(0, obj, FocusInvalidationManager.class, "invalidateNodes", "invalidateNodes()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            FocusInvalidationManager.access$invalidateNodes((FocusInvalidationManager) this.receiver);
            return Unit.INSTANCE;
        }
    }

    public FocusInvalidationManager(Function1 function1, Function0 function0, Function0 function02, Function0 function03) {
        this.onRequestApplyChangesListener = function1;
        this.invalidateOwnerFocusState = function0;
        this.rootFocusStateFetcher = function02;
        this.activeFocusTargetNodeFetcher = function03;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0122  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void access$invalidateNodes(FocusInvalidationManager focusInvalidationManager) {
        int i;
        int i2;
        FocusStateImpl focusState;
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
                LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(focusTargetNode);
                boolean z2 = false;
                while (layoutNodeRequireLayoutNode != null) {
                    if ((layoutNodeRequireLayoutNode.nodes.head.aggregateChildKindSet & 5120) != 0) {
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
                    layoutNodeRequireLayoutNode = layoutNodeRequireLayoutNode.getParent$ui_release();
                    node = (layoutNodeRequireLayoutNode == null || (nodeChain = layoutNodeRequireLayoutNode.nodes) == null) ? null : nodeChain.tail;
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
                            } else if (i9 == length2) {
                                break;
                            } else {
                                i9++;
                            }
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
            Modifier.Node nodeAccess$pop = node2.node;
            if (nodeAccess$pop.isAttached) {
                MutableVector mutableVector = null;
                while (nodeAccess$pop != null) {
                    if (nodeAccess$pop instanceof FocusTargetNode) {
                        ((ArrayList) focusInvalidationManager.focusTargetNodesLegacy).add((FocusTargetNode) nodeAccess$pop);
                    } else if ((nodeAccess$pop.kindSet & 1024) != 0 && (nodeAccess$pop instanceof DelegatingNode)) {
                        int i14 = 0;
                        for (Modifier.Node node3 = ((DelegatingNode) nodeAccess$pop).delegate; node3 != null; node3 = node3.child) {
                            if ((node3.kindSet & 1024) != 0) {
                                i14++;
                                if (i14 == 1) {
                                    nodeAccess$pop = node3;
                                } else {
                                    if (mutableVector == null) {
                                        mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                    }
                                    if (nodeAccess$pop != null) {
                                        mutableVector.add(nodeAccess$pop);
                                        nodeAccess$pop = null;
                                    }
                                    mutableVector.add(node3);
                                }
                            }
                        }
                        if (i14 == 1) {
                        }
                    }
                    nodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector);
                }
                if (!node2.node.isAttached) {
                    InlineClassHelperKt.throwIllegalStateException("visitChildren called on an unattached node");
                }
                MutableVector mutableVector2 = new MutableVector(new Modifier.Node[16], 0);
                Modifier.Node node4 = node2.node;
                Modifier.Node node5 = node4.child;
                if (node5 == null) {
                    DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector2, node4);
                } else {
                    mutableVector2.add(node5);
                }
                while (true) {
                    int i15 = mutableVector2.size;
                    if (i15 != 0) {
                        Modifier.Node nodeAccess$pop2 = (Modifier.Node) mutableVector2.removeAt(i15 - 1);
                        if ((nodeAccess$pop2.aggregateChildKindSet & 1024) == 0) {
                            DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector2, nodeAccess$pop2);
                        } else {
                            while (true) {
                                if (nodeAccess$pop2 == null) {
                                    break;
                                }
                                if ((nodeAccess$pop2.kindSet & 1024) != 0) {
                                    MutableVector mutableVector3 = null;
                                    while (nodeAccess$pop2 != null) {
                                        if (nodeAccess$pop2 instanceof FocusTargetNode) {
                                            ((ArrayList) focusInvalidationManager.focusTargetNodesLegacy).add((FocusTargetNode) nodeAccess$pop2);
                                        } else if ((nodeAccess$pop2.kindSet & 1024) != 0 && (nodeAccess$pop2 instanceof DelegatingNode)) {
                                            int i16 = 0;
                                            for (Modifier.Node node6 = ((DelegatingNode) nodeAccess$pop2).delegate; node6 != null; node6 = node6.child) {
                                                if ((node6.kindSet & 1024) != 0) {
                                                    i16++;
                                                    if (i16 == 1) {
                                                        nodeAccess$pop2 = node6;
                                                    } else {
                                                        if (mutableVector3 == null) {
                                                            mutableVector3 = new MutableVector(new Modifier.Node[16], 0);
                                                        }
                                                        if (nodeAccess$pop2 != null) {
                                                            mutableVector3.add(nodeAccess$pop2);
                                                            nodeAccess$pop2 = null;
                                                        }
                                                        mutableVector3.add(node6);
                                                    }
                                                }
                                            }
                                            if (i16 == 1) {
                                            }
                                        }
                                        nodeAccess$pop2 = DelegatableNodeKt.access$pop(mutableVector3);
                                    }
                                } else {
                                    nodeAccess$pop2 = nodeAccess$pop2.child;
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
            Modifier.Node node7 = (Modifier.Node) focusEventModifierNode;
            Modifier.Node nodeAccess$pop3 = node7.node;
            if (nodeAccess$pop3.isAttached) {
                int i18 = i4;
                int i19 = i5;
                FocusTargetNode focusTargetNode3 = null;
                MutableVector mutableVector4 = null;
                while (nodeAccess$pop3 != null) {
                    if (nodeAccess$pop3 instanceof FocusTargetNode) {
                        FocusTargetNode focusTargetNode4 = (FocusTargetNode) nodeAccess$pop3;
                        if (focusTargetNode3 != null) {
                            i19 = i4;
                        }
                        if (((ArrayList) focusInvalidationManager.focusTargetNodesLegacy).contains(focusTargetNode4)) {
                            ((ArrayList) focusInvalidationManager.focusTargetsWithInvalidatedFocusEventsLegacy).add(focusTargetNode4);
                            i18 = i5;
                        }
                        focusTargetNode3 = focusTargetNode4;
                    } else {
                        if ((nodeAccess$pop3.kindSet & 1024) != 0 && (nodeAccess$pop3 instanceof DelegatingNode)) {
                            Modifier.Node node8 = ((DelegatingNode) nodeAccess$pop3).delegate;
                            while (node8 != null) {
                                if ((node8.kindSet & 1024) != 0) {
                                    i5++;
                                    if (i5 == i4) {
                                        nodeAccess$pop3 = node8;
                                    } else {
                                        if (mutableVector4 == null) {
                                            mutableVector4 = new MutableVector(new Modifier.Node[16], 0);
                                        }
                                        if (nodeAccess$pop3 != null) {
                                            mutableVector4.add(nodeAccess$pop3);
                                            nodeAccess$pop3 = null;
                                        }
                                        mutableVector4.add(node8);
                                    }
                                }
                                node8 = node8.child;
                                i4 = 1;
                            }
                            if (i5 == i4) {
                            }
                        }
                        i5 = 0;
                    }
                    nodeAccess$pop3 = DelegatableNodeKt.access$pop(mutableVector4);
                    i4 = 1;
                    i5 = 0;
                }
                if (!node7.node.isAttached) {
                    InlineClassHelperKt.throwIllegalStateException("visitChildren called on an unattached node");
                }
                MutableVector mutableVector5 = new MutableVector(new Modifier.Node[16], 0);
                Modifier.Node node9 = node7.node;
                Modifier.Node node10 = node9.child;
                if (node10 == null) {
                    DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector5, node9);
                } else {
                    mutableVector5.add(node10);
                }
                while (true) {
                    int i20 = mutableVector5.size;
                    if (i20 == 0) {
                        break;
                    }
                    Modifier.Node nodeAccess$pop4 = (Modifier.Node) mutableVector5.removeAt(i20 - 1);
                    if ((nodeAccess$pop4.aggregateChildKindSet & 1024) == 0) {
                        DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector5, nodeAccess$pop4);
                    } else {
                        while (nodeAccess$pop4 != null) {
                            if ((nodeAccess$pop4.kindSet & 1024) != 0) {
                                MutableVector mutableVector6 = null;
                                while (nodeAccess$pop4 != null) {
                                    if (nodeAccess$pop4 instanceof FocusTargetNode) {
                                        FocusTargetNode focusTargetNode5 = (FocusTargetNode) nodeAccess$pop4;
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
                                    } else if ((nodeAccess$pop4.kindSet & 1024) != 0 && (nodeAccess$pop4 instanceof DelegatingNode)) {
                                        int i21 = 0;
                                        for (Modifier.Node node11 = ((DelegatingNode) nodeAccess$pop4).delegate; node11 != null; node11 = node11.child) {
                                            if ((node11.kindSet & 1024) != 0) {
                                                i21++;
                                                if (i21 == 1) {
                                                    nodeAccess$pop4 = node11;
                                                } else {
                                                    if (mutableVector6 == null) {
                                                        mutableVector6 = new MutableVector(new Modifier.Node[16], 0);
                                                    }
                                                    if (nodeAccess$pop4 != null) {
                                                        mutableVector6.add(nodeAccess$pop4);
                                                        nodeAccess$pop4 = null;
                                                    }
                                                    mutableVector6.add(node11);
                                                }
                                            }
                                        }
                                        if (i21 != 1) {
                                            nodeAccess$pop4 = DelegatableNodeKt.access$pop(mutableVector6);
                                        }
                                    }
                                    nodeAccess$pop4 = DelegatableNodeKt.access$pop(mutableVector6);
                                }
                            } else {
                                nodeAccess$pop4 = nodeAccess$pop4.child;
                            }
                        }
                    }
                }
                i = 1;
                i2 = 0;
                if (i18 != 0) {
                    if (i19 != 0) {
                        focusState = FocusEventModifierNodeKt.getFocusState(focusEventModifierNode);
                    } else if (focusTargetNode3 == null || (focusState = focusTargetNode3.getFocusState()) == null) {
                        focusState = FocusStateImpl.Inactive;
                    }
                    focusEventModifierNode.onFocusEvent(focusState);
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
                FocusStateImpl focusState2 = focusTargetNode6.getFocusState();
                focusTargetNode6.invalidateFocus$ui_release();
                if (focusState2 != focusTargetNode6.getFocusState() || ((ArrayList) focusInvalidationManager.focusTargetsWithInvalidatedFocusEventsLegacy).contains(focusTargetNode6)) {
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
        this.onRequestApplyChangesListener.mo781invoke(new FocusInvalidationManager$setUpOnRequestApplyChangesListener$1(this));
        this.isInvalidationScheduled = true;
    }

    public final void scheduleInvalidationLegacy(List list, Object obj) {
        if (list.add(obj)) {
            if (((ArrayList) this.focusPropertiesNodesLegacy).size() + ((ArrayList) this.focusEventNodesLegacy).size() + ((ArrayList) this.focusTargetNodesLegacy).size() == 1) {
                this.onRequestApplyChangesListener.mo781invoke(new AnonymousClass1(this));
            }
        }
    }
}
