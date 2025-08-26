package androidx.compose.ui.input.pointer;

import androidx.collection.MutableLongObjectMap;
import androidx.collection.MutableObjectList;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.LayoutCoordinates;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class HitPathTracker {
    public final LayoutCoordinates rootCoordinates;
    public final NodeParent root = new NodeParent();
    public final MutableLongObjectMap hitPointerIdsAndNodes = new MutableLongObjectMap(10);

    public HitPathTracker(LayoutCoordinates layoutCoordinates) {
        this.rootCoordinates = layoutCoordinates;
    }

    /* renamed from: addHitPath-QJqDSyo, reason: not valid java name */
    public final void m588addHitPathQJqDSyo(long j, List list, boolean z) {
        long[] jArr;
        int i;
        Object obj;
        NodeParent nodeParent = this.root;
        MutableLongObjectMap mutableLongObjectMap = this.hitPointerIdsAndNodes;
        mutableLongObjectMap.clear();
        int size = list.size();
        NodeParent nodeParent2 = nodeParent;
        boolean z2 = true;
        for (int i2 = 0; i2 < size; i2++) {
            final Modifier.Node node = (Modifier.Node) list.get(i2);
            if (node.isAttached) {
                node.detachedListener = new Function0() { // from class: androidx.compose.ui.input.pointer.HitPathTracker$addHitPath$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        HitPathTracker hitPathTracker = this.this$0;
                        Modifier.Node node2 = node;
                        NodeParent nodeParent3 = hitPathTracker.root;
                        MutableObjectList mutableObjectList = nodeParent3.removeMatchingPointerInputModifierNodeList;
                        mutableObjectList.clear();
                        mutableObjectList.add(nodeParent3);
                        while (mutableObjectList.isNotEmpty()) {
                            NodeParent nodeParent4 = (NodeParent) mutableObjectList.removeAt(mutableObjectList._size - 1);
                            int i3 = 0;
                            while (true) {
                                MutableVector mutableVector = nodeParent4.children;
                                if (i3 < mutableVector.size) {
                                    Node node3 = (Node) mutableVector.content[i3];
                                    if (Intrinsics.areEqual(node3.modifierNode, node2)) {
                                        nodeParent4.children.remove(node3);
                                        node3.dispatchCancel();
                                    } else {
                                        mutableObjectList.add(node3);
                                        i3++;
                                    }
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                };
                if (z2) {
                    MutableVector mutableVector = nodeParent2.children;
                    Object[] objArr = mutableVector.content;
                    int i3 = mutableVector.size;
                    int i4 = 0;
                    while (true) {
                        if (i4 >= i3) {
                            obj = null;
                            break;
                        }
                        obj = objArr[i4];
                        if (Intrinsics.areEqual(((Node) obj).modifierNode, node)) {
                            break;
                        } else {
                            i4++;
                        }
                    }
                    Node node2 = (Node) obj;
                    if (node2 != null) {
                        node2.isIn = true;
                        node2.pointerIds.add(j);
                        Object mutableObjectList = mutableLongObjectMap.get(j);
                        if (mutableObjectList == null) {
                            mutableObjectList = new MutableObjectList(0, 1, null);
                            mutableLongObjectMap.set(j, mutableObjectList);
                        }
                        ((MutableObjectList) mutableObjectList).add(node2);
                        nodeParent2 = node2;
                    } else {
                        z2 = false;
                    }
                }
                Node node3 = new Node(node);
                node3.pointerIds.add(j);
                Object mutableObjectList2 = mutableLongObjectMap.get(j);
                if (mutableObjectList2 == null) {
                    mutableObjectList2 = new MutableObjectList(0, 1, null);
                    mutableLongObjectMap.set(j, mutableObjectList2);
                }
                ((MutableObjectList) mutableObjectList2).add(node3);
                nodeParent2.children.add(node3);
                nodeParent2 = node3;
            }
        }
        int i5 = 0;
        if (!z) {
            return;
        }
        long[] jArr2 = mutableLongObjectMap.keys;
        Object[] objArr2 = mutableLongObjectMap.values;
        long[] jArr3 = mutableLongObjectMap.metadata;
        int length = jArr3.length - 2;
        if (length < 0) {
            return;
        }
        int i6 = 0;
        while (true) {
            long j2 = jArr3[i6];
            if ((((~j2) << 7) & j2 & (-9187201950435737472L)) != -9187201950435737472L) {
                int i7 = 8;
                int i8 = 8 - ((~(i6 - length)) >>> 31);
                int i9 = i5;
                while (i9 < i8) {
                    if ((255 & j2) < 128) {
                        int i10 = (i6 << 3) + i9;
                        long j3 = jArr2[i10];
                        MutableObjectList mutableObjectList3 = (MutableObjectList) objArr2[i10];
                        MutableVector mutableVector2 = nodeParent.children;
                        Object[] objArr3 = mutableVector2.content;
                        int i11 = mutableVector2.size;
                        i = i7;
                        int i12 = 0;
                        while (i12 < i11) {
                            ((Node) objArr3[i12]).removeInvalidPointerIdsAndChanges(j3, mutableObjectList3);
                            i12++;
                            jArr2 = jArr2;
                        }
                    } else {
                        i = i7;
                    }
                    j2 >>= i;
                    i9++;
                    i7 = i;
                    jArr2 = jArr2;
                }
                jArr = jArr2;
                if (i8 != i7) {
                    return;
                }
            } else {
                jArr = jArr2;
            }
            if (i6 == length) {
                return;
            }
            i6++;
            jArr2 = jArr;
            i5 = 0;
        }
    }

    public final boolean dispatchChanges(InternalPointerEvent internalPointerEvent, boolean z) {
        NodeParent nodeParent = this.root;
        if (nodeParent.buildCache(internalPointerEvent.changes, this.rootCoordinates, internalPointerEvent, z)) {
            MutableVector mutableVector = nodeParent.children;
            Object[] objArr = mutableVector.content;
            int i = mutableVector.size;
            boolean z2 = false;
            for (int i2 = 0; i2 < i; i2++) {
                z2 = ((Node) objArr[i2]).dispatchMainEventPass(internalPointerEvent, z) || z2;
            }
            Object[] objArr2 = mutableVector.content;
            int i3 = mutableVector.size;
            boolean z3 = false;
            for (int i4 = 0; i4 < i3; i4++) {
                z3 = ((Node) objArr2[i4]).dispatchFinalEventPass(internalPointerEvent) || z3;
            }
            nodeParent.cleanUpHits(internalPointerEvent);
            if (z3 || z2) {
                return true;
            }
        }
        return false;
    }
}
