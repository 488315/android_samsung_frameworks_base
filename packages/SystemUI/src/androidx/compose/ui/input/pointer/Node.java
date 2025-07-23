package androidx.compose.ui.input.pointer;

import androidx.collection.LongSparseArray;
import androidx.collection.MutableObjectList;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.input.pointer.util.PointerIdArray;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.node.PointerInputModifierNode;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Node extends NodeParent {
    public NodeCoordinator coordinates;
    public final Modifier.Node modifierNode;
    public PointerEvent pointerEvent;
    public boolean wasIn;
    public final PointerIdArray pointerIds = new PointerIdArray();
    public final LongSparseArray relevantChanges = new LongSparseArray(2);
    public boolean isIn = true;
    public boolean hasExited = true;

    public Node(Modifier.Node node) {
        this.modifierNode = node;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:119:0x027c  */
    /* JADX WARN: Type inference failed for: r4v38 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9, types: [int] */
    /* JADX WARN: Type inference failed for: r5v0, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r5v1, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r5v10, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r5v29 */
    /* JADX WARN: Type inference failed for: r5v30, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r5v31, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v32 */
    /* JADX WARN: Type inference failed for: r5v33 */
    /* JADX WARN: Type inference failed for: r5v34 */
    /* JADX WARN: Type inference failed for: r5v35 */
    /* JADX WARN: Type inference failed for: r5v36 */
    /* JADX WARN: Type inference failed for: r5v37 */
    /* JADX WARN: Type inference failed for: r6v4, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v14 */
    /* JADX WARN: Type inference failed for: r8v15, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r8v16 */
    /* JADX WARN: Type inference failed for: r8v17 */
    /* JADX WARN: Type inference failed for: r8v18, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r8v20 */
    /* JADX WARN: Type inference failed for: r8v21 */
    /* JADX WARN: Type inference failed for: r8v22 */
    /* JADX WARN: Type inference failed for: r8v23 */
    @Override // androidx.compose.ui.input.pointer.NodeParent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean buildCache(androidx.collection.LongSparseArray r56, androidx.compose.ui.layout.LayoutCoordinates r57, androidx.compose.ui.input.pointer.InternalPointerEvent r58, boolean r59) {
        /*
            Method dump skipped, instructions count: 793
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.input.pointer.Node.buildCache(androidx.collection.LongSparseArray, androidx.compose.ui.layout.LayoutCoordinates, androidx.compose.ui.input.pointer.InternalPointerEvent, boolean):boolean");
    }

    @Override // androidx.compose.ui.input.pointer.NodeParent
    public final void cleanUpHits(InternalPointerEvent internalPointerEvent) {
        super.cleanUpHits(internalPointerEvent);
        PointerEvent pointerEvent = this.pointerEvent;
        if (pointerEvent == null) {
            return;
        }
        this.wasIn = this.isIn;
        List list = pointerEvent.changes;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            PointerInputChange pointerInputChange = (PointerInputChange) list.get(i);
            boolean z = pointerInputChange.pressed;
            long j = pointerInputChange.id;
            boolean m588activeHoverEvent0FcD4WY = internalPointerEvent.m588activeHoverEvent0FcD4WY(j);
            boolean z2 = this.isIn;
            if ((!z && !m588activeHoverEvent0FcD4WY) || (!z && !z2)) {
                this.pointerIds.remove(j);
            }
        }
        this.isIn = false;
        int i2 = pointerEvent.type;
        PointerEventType.Companion.getClass();
        this.hasExited = i2 == PointerEventType.Exit;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r8v1, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v2, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Type inference failed for: r8v5, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r8v6, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r8v7 */
    /* JADX WARN: Type inference failed for: r8v8 */
    /* JADX WARN: Type inference failed for: r8v9 */
    public final void dispatchCancel() {
        MutableVector mutableVector = this.children;
        Object[] objArr = mutableVector.content;
        int i = mutableVector.size;
        for (int i2 = 0; i2 < i; i2++) {
            ((Node) objArr[i2]).dispatchCancel();
        }
        DelegatingNode delegatingNode = this.modifierNode;
        ?? r1 = 0;
        while (delegatingNode != 0) {
            if (delegatingNode instanceof PointerInputModifierNode) {
                ((PointerInputModifierNode) delegatingNode).onCancelPointerInput();
            } else if ((delegatingNode.kindSet & 16) != 0 && (delegatingNode instanceof DelegatingNode)) {
                Modifier.Node node = delegatingNode.delegate;
                int i3 = 0;
                r1 = r1;
                delegatingNode = delegatingNode;
                while (node != null) {
                    if ((node.kindSet & 16) != 0) {
                        i3++;
                        r1 = r1;
                        if (i3 == 1) {
                            delegatingNode = node;
                        } else {
                            if (r1 == 0) {
                                r1 = new MutableVector(new Modifier.Node[16], 0);
                            }
                            if (delegatingNode != 0) {
                                r1.add(delegatingNode);
                                delegatingNode = 0;
                            }
                            r1.add(node);
                        }
                    }
                    node = node.child;
                    r1 = r1;
                    delegatingNode = delegatingNode;
                }
                if (i3 == 1) {
                }
            }
            delegatingNode = DelegatableNodeKt.access$pop(r1);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v4, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r8v5, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r8v6 */
    /* JADX WARN: Type inference failed for: r8v7 */
    /* JADX WARN: Type inference failed for: r8v8 */
    /* JADX WARN: Type inference failed for: r8v9 */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v10 */
    /* JADX WARN: Type inference failed for: r9v11 */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v3, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r9v4 */
    /* JADX WARN: Type inference failed for: r9v5 */
    /* JADX WARN: Type inference failed for: r9v6, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r9v8 */
    /* JADX WARN: Type inference failed for: r9v9 */
    public final boolean dispatchFinalEventPass(InternalPointerEvent internalPointerEvent) {
        LongSparseArray longSparseArray = this.relevantChanges;
        boolean z = false;
        z = false;
        if (!(longSparseArray.size() == 0)) {
            Modifier.Node node = this.modifierNode;
            if (node.isAttached) {
                PointerEvent pointerEvent = this.pointerEvent;
                pointerEvent.getClass();
                NodeCoordinator nodeCoordinator = this.coordinates;
                nodeCoordinator.getClass();
                long j = nodeCoordinator.measuredSize;
                DelegatingNode delegatingNode = node;
                ?? r9 = 0;
                while (delegatingNode != 0) {
                    if (delegatingNode instanceof PointerInputModifierNode) {
                        ((PointerInputModifierNode) delegatingNode).mo16onPointerEventH0pRuoY(pointerEvent, PointerEventPass.Final, j);
                    } else if ((delegatingNode.kindSet & 16) != 0 && (delegatingNode instanceof DelegatingNode)) {
                        Modifier.Node node2 = delegatingNode.delegate;
                        int i = 0;
                        delegatingNode = delegatingNode;
                        r9 = r9;
                        while (node2 != null) {
                            if ((node2.kindSet & 16) != 0) {
                                i++;
                                r9 = r9;
                                if (i == 1) {
                                    delegatingNode = node2;
                                } else {
                                    if (r9 == 0) {
                                        r9 = new MutableVector(new Modifier.Node[16], 0);
                                    }
                                    if (delegatingNode != 0) {
                                        r9.add(delegatingNode);
                                        delegatingNode = 0;
                                    }
                                    r9.add(node2);
                                }
                            }
                            node2 = node2.child;
                            delegatingNode = delegatingNode;
                            r9 = r9;
                        }
                        if (i == 1) {
                        }
                    }
                    delegatingNode = DelegatableNodeKt.access$pop(r9);
                }
                if (node.isAttached) {
                    MutableVector mutableVector = this.children;
                    Object[] objArr = mutableVector.content;
                    int i2 = mutableVector.size;
                    for (int i3 = 0; i3 < i2; i3++) {
                        ((Node) objArr[i3]).dispatchFinalEventPass(internalPointerEvent);
                    }
                }
                z = true;
            }
        }
        cleanUpHits(internalPointerEvent);
        longSparseArray.clear();
        this.coordinates = null;
        return z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v4, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r0v5, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r0v9, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r13v10 */
    /* JADX WARN: Type inference failed for: r13v11 */
    /* JADX WARN: Type inference failed for: r13v12 */
    /* JADX WARN: Type inference failed for: r13v13 */
    /* JADX WARN: Type inference failed for: r13v2 */
    /* JADX WARN: Type inference failed for: r13v3 */
    /* JADX WARN: Type inference failed for: r13v4 */
    /* JADX WARN: Type inference failed for: r13v5, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r13v6 */
    /* JADX WARN: Type inference failed for: r13v7 */
    /* JADX WARN: Type inference failed for: r13v8, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r7v10, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r7v11, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v15 */
    /* JADX WARN: Type inference failed for: r7v16 */
    /* JADX WARN: Type inference failed for: r7v17 */
    /* JADX WARN: Type inference failed for: r7v9 */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v4, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v6 */
    /* JADX WARN: Type inference failed for: r8v7, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r8v9 */
    public final boolean dispatchMainEventPass(InternalPointerEvent internalPointerEvent, boolean z) {
        if (!(this.relevantChanges.size() == 0)) {
            DelegatingNode delegatingNode = this.modifierNode;
            if (delegatingNode.isAttached) {
                PointerEvent pointerEvent = this.pointerEvent;
                pointerEvent.getClass();
                NodeCoordinator nodeCoordinator = this.coordinates;
                nodeCoordinator.getClass();
                long j = nodeCoordinator.measuredSize;
                DelegatingNode delegatingNode2 = delegatingNode;
                ?? r8 = 0;
                while (delegatingNode2 != 0) {
                    if (delegatingNode2 instanceof PointerInputModifierNode) {
                        ((PointerInputModifierNode) delegatingNode2).mo16onPointerEventH0pRuoY(pointerEvent, PointerEventPass.Initial, j);
                    } else if ((delegatingNode2.kindSet & 16) != 0 && (delegatingNode2 instanceof DelegatingNode)) {
                        Modifier.Node node = delegatingNode2.delegate;
                        int i = 0;
                        delegatingNode2 = delegatingNode2;
                        r8 = r8;
                        while (node != null) {
                            if ((node.kindSet & 16) != 0) {
                                i++;
                                r8 = r8;
                                if (i == 1) {
                                    delegatingNode2 = node;
                                } else {
                                    if (r8 == 0) {
                                        r8 = new MutableVector(new Modifier.Node[16], 0);
                                    }
                                    if (delegatingNode2 != 0) {
                                        r8.add(delegatingNode2);
                                        delegatingNode2 = 0;
                                    }
                                    r8.add(node);
                                }
                            }
                            node = node.child;
                            delegatingNode2 = delegatingNode2;
                            r8 = r8;
                        }
                        if (i == 1) {
                        }
                    }
                    delegatingNode2 = DelegatableNodeKt.access$pop(r8);
                }
                if (delegatingNode.isAttached) {
                    MutableVector mutableVector = this.children;
                    Object[] objArr = mutableVector.content;
                    int i2 = mutableVector.size;
                    for (int i3 = 0; i3 < i2; i3++) {
                        Node node2 = (Node) objArr[i3];
                        this.coordinates.getClass();
                        node2.dispatchMainEventPass(internalPointerEvent, z);
                    }
                }
                if (delegatingNode.isAttached) {
                    ?? r13 = 0;
                    while (delegatingNode != 0) {
                        if (delegatingNode instanceof PointerInputModifierNode) {
                            ((PointerInputModifierNode) delegatingNode).mo16onPointerEventH0pRuoY(pointerEvent, PointerEventPass.Main, j);
                        } else if ((delegatingNode.kindSet & 16) != 0 && (delegatingNode instanceof DelegatingNode)) {
                            Modifier.Node node3 = delegatingNode.delegate;
                            int i4 = 0;
                            delegatingNode = delegatingNode;
                            r13 = r13;
                            while (node3 != null) {
                                if ((node3.kindSet & 16) != 0) {
                                    i4++;
                                    r13 = r13;
                                    if (i4 == 1) {
                                        delegatingNode = node3;
                                    } else {
                                        if (r13 == 0) {
                                            r13 = new MutableVector(new Modifier.Node[16], 0);
                                        }
                                        if (delegatingNode != 0) {
                                            r13.add(delegatingNode);
                                            delegatingNode = 0;
                                        }
                                        r13.add(node3);
                                    }
                                }
                                node3 = node3.child;
                                delegatingNode = delegatingNode;
                                r13 = r13;
                            }
                            if (i4 == 1) {
                            }
                        }
                        delegatingNode = DelegatableNodeKt.access$pop(r13);
                    }
                }
                return true;
            }
        }
        return false;
    }

    public final void removeInvalidPointerIdsAndChanges(long j, MutableObjectList mutableObjectList) {
        PointerIdArray pointerIdArray = this.pointerIds;
        if (pointerIdArray.contains(j) && mutableObjectList.indexOf(this) < 0) {
            pointerIdArray.remove(j);
            this.relevantChanges.remove(j);
        }
        MutableVector mutableVector = this.children;
        Object[] objArr = mutableVector.content;
        int i = mutableVector.size;
        for (int i2 = 0; i2 < i; i2++) {
            ((Node) objArr[i2]).removeInvalidPointerIdsAndChanges(j, mutableObjectList);
        }
    }

    public final String toString() {
        return "Node(modifierNode=" + this.modifierNode + ", children=" + this.children + ", pointerIds=" + this.pointerIds + ')';
    }
}
