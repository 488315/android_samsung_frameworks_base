package androidx.compose.ui.input.pointer;

import androidx.collection.LongSparseArray;
import androidx.collection.MutableObjectList;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.util.PointerIdArray;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.node.PointerInputModifierNode;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.EmptyList;

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
    /* JADX WARN: Removed duplicated region for block: B:123:0x027c  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x029f  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x0314 A[EDGE_INSN: B:165:0x0314->B:166:0x0316 BREAK  A[LOOP:8: B:159:0x02f2->B:163:0x030f]] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0176  */
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
    */
    public final boolean buildCache(LongSparseArray longSparseArray, LayoutCoordinates layoutCoordinates, InternalPointerEvent internalPointerEvent, boolean z) {
        LongSparseArray longSparseArray2;
        PointerIdArray pointerIdArray;
        Object obj;
        boolean z2;
        boolean z3;
        boolean z4;
        PointerEvent pointerEvent;
        int i;
        int i2;
        boolean z5;
        int i3;
        int i4;
        int i5;
        int i6;
        LayoutCoordinates layoutCoordinates2 = layoutCoordinates;
        boolean zBuildCache = super.buildCache(longSparseArray, layoutCoordinates, internalPointerEvent, z);
        DelegatingNode delegatingNodeAccess$pop = this.modifierNode;
        if (delegatingNodeAccess$pop.isAttached) {
            ?? mutableVector = 0;
            while (delegatingNodeAccess$pop != 0) {
                if (delegatingNodeAccess$pop instanceof PointerInputModifierNode) {
                    this.coordinates = DelegatableNodeKt.m634requireCoordinator64DMado((PointerInputModifierNode) delegatingNodeAccess$pop, 16);
                } else if ((delegatingNodeAccess$pop.kindSet & 16) != 0 && (delegatingNodeAccess$pop instanceof DelegatingNode)) {
                    Modifier.Node node = delegatingNodeAccess$pop.delegate;
                    int i7 = 0;
                    delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                    mutableVector = mutableVector;
                    while (node != null) {
                        if ((node.kindSet & 16) != 0) {
                            i7++;
                            mutableVector = mutableVector;
                            if (i7 == 1) {
                                delegatingNodeAccess$pop = node;
                            } else {
                                if (mutableVector == 0) {
                                    mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                }
                                if (delegatingNodeAccess$pop != 0) {
                                    mutableVector.add(delegatingNodeAccess$pop);
                                    delegatingNodeAccess$pop = 0;
                                }
                                mutableVector.add(node);
                            }
                        }
                        node = node.child;
                        delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                        mutableVector = mutableVector;
                    }
                    if (i7 == 1) {
                    }
                }
                delegatingNodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector);
            }
            if (this.coordinates != null) {
                int size = longSparseArray.size();
                int i8 = 0;
                while (true) {
                    longSparseArray2 = this.relevantChanges;
                    pointerIdArray = this.pointerIds;
                    if (i8 >= size) {
                        break;
                    }
                    long jKeyAt = longSparseArray.keyAt(i8);
                    PointerInputChange pointerInputChange = (PointerInputChange) longSparseArray.valueAt(i8);
                    if (pointerIdArray.contains(jKeyAt)) {
                        long j = pointerInputChange.previousPosition;
                        if ((((j & 9223372034707292159L) + 36028792732385279L) & (-9223372034707292160L)) == 0) {
                            long j2 = pointerInputChange.position;
                            if ((((j2 & 9223372034707292159L) + 36028792732385279L) & (-9223372034707292160L)) == 0) {
                                List list = pointerInputChange._historical;
                                if (list == null) {
                                    list = EmptyList.INSTANCE;
                                }
                                ArrayList arrayList = new ArrayList(list.size());
                                List list2 = pointerInputChange._historical;
                                if (list2 == null) {
                                    list2 = EmptyList.INSTANCE;
                                }
                                z5 = zBuildCache;
                                int size2 = list2.size();
                                i3 = size;
                                int i9 = 0;
                                while (i9 < size2) {
                                    int i10 = size2;
                                    HistoricalChange historicalChange = (HistoricalChange) list2.get(i9);
                                    long j3 = jKeyAt;
                                    long j4 = historicalChange.position;
                                    if ((((j4 & 9223372034707292159L) + 36028792732385279L) & (-9223372034707292160L)) == 0) {
                                        i5 = i9;
                                        NodeCoordinator nodeCoordinator = this.coordinates;
                                        nodeCoordinator.getClass();
                                        i6 = i8;
                                        arrayList.add(new HistoricalChange(historicalChange.uptimeMillis, nodeCoordinator.mo614localPositionOfS_NoaFU(layoutCoordinates2, j4, true), historicalChange.originalEventPosition, null));
                                    } else {
                                        i5 = i9;
                                        i6 = i8;
                                    }
                                    i9 = i5 + 1;
                                    size2 = i10;
                                    jKeyAt = j3;
                                    i8 = i6;
                                }
                                i4 = i8;
                                long j5 = jKeyAt;
                                NodeCoordinator nodeCoordinator2 = this.coordinates;
                                nodeCoordinator2.getClass();
                                long jMo614localPositionOfS_NoaFU = nodeCoordinator2.mo614localPositionOfS_NoaFU(layoutCoordinates2, j, true);
                                NodeCoordinator nodeCoordinator3 = this.coordinates;
                                nodeCoordinator3.getClass();
                                PointerInputChange pointerInputChange2 = new PointerInputChange(pointerInputChange.id, pointerInputChange.uptimeMillis, nodeCoordinator3.mo614localPositionOfS_NoaFU(layoutCoordinates2, j2, true), pointerInputChange.pressed, pointerInputChange.pressure, pointerInputChange.previousUptimeMillis, jMo614localPositionOfS_NoaFU, pointerInputChange.previousPressed, false, pointerInputChange.type, arrayList, pointerInputChange.scrollDelta, pointerInputChange.originalEventPosition, null);
                                PointerInputChange pointerInputChange3 = pointerInputChange.consumedDelegate;
                                if (pointerInputChange3 == null) {
                                    pointerInputChange3 = pointerInputChange;
                                }
                                pointerInputChange2.consumedDelegate = pointerInputChange3;
                                PointerInputChange pointerInputChange4 = pointerInputChange.consumedDelegate;
                                if (pointerInputChange4 != null) {
                                    pointerInputChange = pointerInputChange4;
                                }
                                pointerInputChange2.consumedDelegate = pointerInputChange;
                                longSparseArray2.put(j5, pointerInputChange2);
                            } else {
                                z5 = zBuildCache;
                                i3 = size;
                                i4 = i8;
                            }
                        }
                    }
                    i8 = i4 + 1;
                    layoutCoordinates2 = layoutCoordinates;
                    zBuildCache = z5;
                    size = i3;
                }
                boolean z6 = zBuildCache;
                if (longSparseArray2.size() == 0) {
                    pointerIdArray.size = 0;
                    this.children.clear();
                    return true;
                }
                int i11 = pointerIdArray.size;
                while (true) {
                    i11--;
                    if (-1 >= i11) {
                        break;
                    }
                    if (longSparseArray.indexOfKey(pointerIdArray.internalArray[i11]) < 0 && i11 < (i2 = pointerIdArray.size)) {
                        int i12 = i2 - 1;
                        int i13 = i11;
                        while (i13 < i12) {
                            long[] jArr = pointerIdArray.internalArray;
                            int i14 = i13 + 1;
                            jArr[i13] = jArr[i14];
                            i13 = i14;
                        }
                        pointerIdArray.size--;
                    }
                }
                ArrayList arrayList2 = new ArrayList(longSparseArray2.size());
                int size3 = longSparseArray2.size();
                for (int i15 = 0; i15 < size3; i15++) {
                    arrayList2.add(longSparseArray2.valueAt(i15));
                }
                PointerEvent pointerEvent2 = new PointerEvent(arrayList2, internalPointerEvent);
                List list3 = pointerEvent2.changes;
                int size4 = list3.size();
                int i16 = 0;
                while (true) {
                    if (i16 >= size4) {
                        obj = null;
                        break;
                    }
                    obj = list3.get(i16);
                    if (internalPointerEvent.m590activeHoverEvent0FcD4WY(((PointerInputChange) obj).id)) {
                        break;
                    }
                    i16++;
                }
                PointerInputChange pointerInputChange5 = (PointerInputChange) obj;
                if (pointerInputChange5 != null) {
                    boolean z7 = pointerInputChange5.pressed;
                    if (z) {
                        z2 = false;
                        if (!this.isIn && (z7 || pointerInputChange5.previousPressed)) {
                            NodeCoordinator nodeCoordinator4 = this.coordinates;
                            nodeCoordinator4.getClass();
                            long j6 = nodeCoordinator4.measuredSize;
                            long j7 = pointerInputChange5.position;
                            float fIntBitsToFloat = Float.intBitsToFloat((int) (j7 >> 32));
                            float fIntBitsToFloat2 = Float.intBitsToFloat((int) (j7 & 4294967295L));
                            z3 = true;
                            this.isIn = !((fIntBitsToFloat < 0.0f) | (fIntBitsToFloat > ((float) ((int) (j6 >> 32)))) | (fIntBitsToFloat2 < 0.0f) | (fIntBitsToFloat2 > ((float) ((int) (j6 & 4294967295L)))));
                        }
                        if (this.isIn == this.wasIn) {
                            int i17 = pointerEvent2.type;
                            PointerEventType.Companion.getClass();
                            if (i17 == PointerEventType.Move || (i = pointerEvent2.type) == PointerEventType.Enter || i == PointerEventType.Exit) {
                                pointerEvent2.type = this.isIn ? PointerEventType.Enter : PointerEventType.Exit;
                            } else {
                                int i18 = pointerEvent2.type;
                                PointerEventType.Companion.getClass();
                                if (i18 == PointerEventType.Enter && this.wasIn && !this.hasExited) {
                                    pointerEvent2.type = PointerEventType.Move;
                                } else if (pointerEvent2.type == PointerEventType.Exit && this.isIn && z7) {
                                    pointerEvent2.type = PointerEventType.Move;
                                }
                            }
                        }
                    } else {
                        z2 = false;
                        this.isIn = false;
                    }
                    z3 = true;
                    if (this.isIn == this.wasIn) {
                    }
                } else {
                    z2 = false;
                    z3 = true;
                }
                if (!z6) {
                    int i19 = pointerEvent2.type;
                    PointerEventType.Companion.getClass();
                    if (i19 != PointerEventType.Move || (pointerEvent = this.pointerEvent) == null || pointerEvent.changes.size() != pointerEvent2.changes.size()) {
                        z4 = z3;
                        break;
                    }
                    int size5 = pointerEvent2.changes.size();
                    for (?? r4 = z2; r4 < size5; r4++) {
                        if (!Offset.m398equalsimpl0(((PointerInputChange) pointerEvent.changes.get(r4)).position, ((PointerInputChange) pointerEvent2.changes.get(r4)).position)) {
                            z4 = z3;
                            break;
                        }
                    }
                    z4 = z2;
                }
                this.pointerEvent = pointerEvent2;
                return z4;
            }
        }
        return true;
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
            boolean zM590activeHoverEvent0FcD4WY = internalPointerEvent.m590activeHoverEvent0FcD4WY(j);
            boolean z2 = this.isIn;
            if ((!z && !zM590activeHoverEvent0FcD4WY) || (!z && !z2)) {
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
        DelegatingNode delegatingNodeAccess$pop = this.modifierNode;
        ?? mutableVector2 = 0;
        while (delegatingNodeAccess$pop != 0) {
            if (delegatingNodeAccess$pop instanceof PointerInputModifierNode) {
                ((PointerInputModifierNode) delegatingNodeAccess$pop).onCancelPointerInput();
            } else if ((delegatingNodeAccess$pop.kindSet & 16) != 0 && (delegatingNodeAccess$pop instanceof DelegatingNode)) {
                Modifier.Node node = delegatingNodeAccess$pop.delegate;
                int i3 = 0;
                mutableVector2 = mutableVector2;
                delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                while (node != null) {
                    if ((node.kindSet & 16) != 0) {
                        i3++;
                        mutableVector2 = mutableVector2;
                        if (i3 == 1) {
                            delegatingNodeAccess$pop = node;
                        } else {
                            if (mutableVector2 == 0) {
                                mutableVector2 = new MutableVector(new Modifier.Node[16], 0);
                            }
                            if (delegatingNodeAccess$pop != 0) {
                                mutableVector2.add(delegatingNodeAccess$pop);
                                delegatingNodeAccess$pop = 0;
                            }
                            mutableVector2.add(node);
                        }
                    }
                    node = node.child;
                    mutableVector2 = mutableVector2;
                    delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                }
                if (i3 == 1) {
                }
            }
            delegatingNodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector2);
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
                DelegatingNode delegatingNodeAccess$pop = node;
                ?? mutableVector = 0;
                while (delegatingNodeAccess$pop != 0) {
                    if (delegatingNodeAccess$pop instanceof PointerInputModifierNode) {
                        ((PointerInputModifierNode) delegatingNodeAccess$pop).mo16onPointerEventH0pRuoY(pointerEvent, PointerEventPass.Final, j);
                    } else if ((delegatingNodeAccess$pop.kindSet & 16) != 0 && (delegatingNodeAccess$pop instanceof DelegatingNode)) {
                        Modifier.Node node2 = delegatingNodeAccess$pop.delegate;
                        int i = 0;
                        delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                        mutableVector = mutableVector;
                        while (node2 != null) {
                            if ((node2.kindSet & 16) != 0) {
                                i++;
                                mutableVector = mutableVector;
                                if (i == 1) {
                                    delegatingNodeAccess$pop = node2;
                                } else {
                                    if (mutableVector == 0) {
                                        mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                    }
                                    if (delegatingNodeAccess$pop != 0) {
                                        mutableVector.add(delegatingNodeAccess$pop);
                                        delegatingNodeAccess$pop = 0;
                                    }
                                    mutableVector.add(node2);
                                }
                            }
                            node2 = node2.child;
                            delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                            mutableVector = mutableVector;
                        }
                        if (i == 1) {
                        }
                    }
                    delegatingNodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector);
                }
                if (node.isAttached) {
                    MutableVector mutableVector2 = this.children;
                    Object[] objArr = mutableVector2.content;
                    int i2 = mutableVector2.size;
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
            DelegatingNode delegatingNodeAccess$pop = this.modifierNode;
            if (delegatingNodeAccess$pop.isAttached) {
                PointerEvent pointerEvent = this.pointerEvent;
                pointerEvent.getClass();
                NodeCoordinator nodeCoordinator = this.coordinates;
                nodeCoordinator.getClass();
                long j = nodeCoordinator.measuredSize;
                DelegatingNode delegatingNodeAccess$pop2 = delegatingNodeAccess$pop;
                ?? mutableVector = 0;
                while (delegatingNodeAccess$pop2 != 0) {
                    if (delegatingNodeAccess$pop2 instanceof PointerInputModifierNode) {
                        ((PointerInputModifierNode) delegatingNodeAccess$pop2).mo16onPointerEventH0pRuoY(pointerEvent, PointerEventPass.Initial, j);
                    } else if ((delegatingNodeAccess$pop2.kindSet & 16) != 0 && (delegatingNodeAccess$pop2 instanceof DelegatingNode)) {
                        Modifier.Node node = delegatingNodeAccess$pop2.delegate;
                        int i = 0;
                        delegatingNodeAccess$pop2 = delegatingNodeAccess$pop2;
                        mutableVector = mutableVector;
                        while (node != null) {
                            if ((node.kindSet & 16) != 0) {
                                i++;
                                mutableVector = mutableVector;
                                if (i == 1) {
                                    delegatingNodeAccess$pop2 = node;
                                } else {
                                    if (mutableVector == 0) {
                                        mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                    }
                                    if (delegatingNodeAccess$pop2 != 0) {
                                        mutableVector.add(delegatingNodeAccess$pop2);
                                        delegatingNodeAccess$pop2 = 0;
                                    }
                                    mutableVector.add(node);
                                }
                            }
                            node = node.child;
                            delegatingNodeAccess$pop2 = delegatingNodeAccess$pop2;
                            mutableVector = mutableVector;
                        }
                        if (i == 1) {
                        }
                    }
                    delegatingNodeAccess$pop2 = DelegatableNodeKt.access$pop(mutableVector);
                }
                if (delegatingNodeAccess$pop.isAttached) {
                    MutableVector mutableVector2 = this.children;
                    Object[] objArr = mutableVector2.content;
                    int i2 = mutableVector2.size;
                    for (int i3 = 0; i3 < i2; i3++) {
                        Node node2 = (Node) objArr[i3];
                        this.coordinates.getClass();
                        node2.dispatchMainEventPass(internalPointerEvent, z);
                    }
                }
                if (delegatingNodeAccess$pop.isAttached) {
                    ?? mutableVector3 = 0;
                    while (delegatingNodeAccess$pop != 0) {
                        if (delegatingNodeAccess$pop instanceof PointerInputModifierNode) {
                            ((PointerInputModifierNode) delegatingNodeAccess$pop).mo16onPointerEventH0pRuoY(pointerEvent, PointerEventPass.Main, j);
                        } else if ((delegatingNodeAccess$pop.kindSet & 16) != 0 && (delegatingNodeAccess$pop instanceof DelegatingNode)) {
                            Modifier.Node node3 = delegatingNodeAccess$pop.delegate;
                            int i4 = 0;
                            delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                            mutableVector3 = mutableVector3;
                            while (node3 != null) {
                                if ((node3.kindSet & 16) != 0) {
                                    i4++;
                                    mutableVector3 = mutableVector3;
                                    if (i4 == 1) {
                                        delegatingNodeAccess$pop = node3;
                                    } else {
                                        if (mutableVector3 == 0) {
                                            mutableVector3 = new MutableVector(new Modifier.Node[16], 0);
                                        }
                                        if (delegatingNodeAccess$pop != 0) {
                                            mutableVector3.add(delegatingNodeAccess$pop);
                                            delegatingNodeAccess$pop = 0;
                                        }
                                        mutableVector3.add(node3);
                                    }
                                }
                                node3 = node3.child;
                                delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                                mutableVector3 = mutableVector3;
                            }
                            if (i4 == 1) {
                            }
                        }
                        delegatingNodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector3);
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
