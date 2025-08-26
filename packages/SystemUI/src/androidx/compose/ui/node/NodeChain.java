package androidx.compose.ui.node;

import androidx.collection.MutableObjectIntMap;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.internal.InlineClassHelperKt;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class NodeChain {
    public MutableVector buffer;
    public Differ cachedDiffer;
    public MutableVector current;
    public Modifier.Node head;
    public final InnerNodeCoordinator innerCoordinator;
    public final LayoutNode layoutNode;
    public NodeCoordinator outerCoordinator;
    public final TailModifierNode tail;

    final class Differ {
        public MutableVector after;
        public MutableVector before;
        public Modifier.Node node;
        public int offset;
        public boolean shouldAttachOnInsert;

        public Differ(Modifier.Node node, int i, MutableVector<Modifier.Element> mutableVector, MutableVector<Modifier.Element> mutableVector2, boolean z) {
            this.node = node;
            this.offset = i;
            this.before = mutableVector;
            this.after = mutableVector2;
            this.shouldAttachOnInsert = z;
        }

        public final boolean areItemsTheSame(int i, int i2) {
            MutableVector mutableVector = this.before;
            int i3 = this.offset;
            Modifier.Element element = (Modifier.Element) mutableVector.content[i + i3];
            Modifier.Element element2 = (Modifier.Element) this.after.content[i3 + i2];
            NodeChainKt$SentinelHead$1 nodeChainKt$SentinelHead$1 = NodeChainKt.SentinelHead;
            return Intrinsics.areEqual(element, element2) || element.getClass() == element2.getClass();
        }
    }

    public NodeChain(LayoutNode layoutNode) {
        this.layoutNode = layoutNode;
        InnerNodeCoordinator innerNodeCoordinator = new InnerNodeCoordinator(layoutNode);
        this.innerCoordinator = innerNodeCoordinator;
        this.outerCoordinator = innerNodeCoordinator;
        TailModifierNode tailModifierNode = innerNodeCoordinator.tail;
        this.tail = tailModifierNode;
        this.head = tailModifierNode;
    }

    public static final void access$propagateCoordinator(NodeChain nodeChain, Modifier.Node node, NodeCoordinator nodeCoordinator) {
        nodeChain.getClass();
        for (Modifier.Node node2 = node.parent; node2 != null; node2 = node2.parent) {
            if (node2 == NodeChainKt.SentinelHead) {
                LayoutNode parent$ui_release = nodeChain.layoutNode.getParent$ui_release();
                nodeCoordinator.wrappedBy = parent$ui_release != null ? parent$ui_release.nodes.innerCoordinator : null;
                nodeChain.outerCoordinator = nodeCoordinator;
                return;
            } else {
                if ((node2.kindSet & 2) != 0) {
                    return;
                }
                node2.updateCoordinator$ui_release(nodeCoordinator);
            }
        }
    }

    public static Modifier.Node createAndInsertNodeAsChild(Modifier.Element element, Modifier.Node node) {
        Modifier.Node backwardsCompatNode;
        if (element instanceof ModifierNodeElement) {
            backwardsCompatNode = ((ModifierNodeElement) element).create();
            backwardsCompatNode.kindSet = NodeKindKt.calculateNodeKindSetFromIncludingDelegates(backwardsCompatNode);
        } else {
            backwardsCompatNode = new BackwardsCompatNode(element);
        }
        if (backwardsCompatNode.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("A ModifierNodeElement cannot return an already attached node from create() ");
        }
        backwardsCompatNode.insertedNodeAwaitingAttachForInvalidation = true;
        Modifier.Node node2 = node.child;
        if (node2 != null) {
            node2.parent = backwardsCompatNode;
            backwardsCompatNode.child = node2;
        }
        node.child = backwardsCompatNode;
        backwardsCompatNode.parent = node;
        return backwardsCompatNode;
    }

    public static Modifier.Node detachAndRemoveNode(Modifier.Node node) {
        boolean z = node.isAttached;
        if (z) {
            MutableObjectIntMap mutableObjectIntMap = NodeKindKt.classToKindSetMap;
            if (!z) {
                InlineClassHelperKt.throwIllegalStateException("autoInvalidateRemovedNode called on unattached node");
            }
            NodeKindKt.autoInvalidateNodeIncludingDelegates(node, -1, 2);
            node.runDetachLifecycle$ui_release();
            node.markAsDetached$ui_release();
        }
        Modifier.Node node2 = node.child;
        Modifier.Node node3 = node.parent;
        if (node2 != null) {
            node2.parent = node3;
            node.child = null;
        }
        if (node3 != null) {
            node3.child = node2;
            node.parent = null;
        }
        node3.getClass();
        return node3;
    }

    public static void updateNode(Modifier.Element element, Modifier.Element element2, Modifier.Node node) {
        if ((element instanceof ModifierNodeElement) && (element2 instanceof ModifierNodeElement)) {
            NodeChainKt$SentinelHead$1 nodeChainKt$SentinelHead$1 = NodeChainKt.SentinelHead;
            ((ModifierNodeElement) element2).update(node);
            if (node.isAttached) {
                NodeKindKt.autoInvalidateUpdatedNode(node);
                return;
            } else {
                node.updatedNodeAwaitingAttachForInvalidation = true;
                return;
            }
        }
        if (!(node instanceof BackwardsCompatNode)) {
            InlineClassHelperKt.throwIllegalStateException("Unknown Modifier.Node type");
            return;
        }
        BackwardsCompatNode backwardsCompatNode = (BackwardsCompatNode) node;
        if (backwardsCompatNode.isAttached) {
            backwardsCompatNode.unInitializeModifier();
        }
        backwardsCompatNode.element = element2;
        backwardsCompatNode.kindSet = NodeKindKt.calculateNodeKindSetFrom(element2);
        if (backwardsCompatNode.isAttached) {
            backwardsCompatNode.initializeModifier(false);
        }
        if (node.isAttached) {
            NodeKindKt.autoInvalidateUpdatedNode(node);
        } else {
            node.updatedNodeAwaitingAttachForInvalidation = true;
        }
    }

    /* renamed from: has-H91voCI$ui_release, reason: not valid java name */
    public final boolean m665hasH91voCI$ui_release(int i) {
        return (this.head.aggregateChildKindSet & i) != 0;
    }

    public final void runAttachLifecycle() {
        InnerNodeCoordinator innerNodeCoordinator;
        NodeCoordinator nodeCoordinator = this.outerCoordinator;
        while (true) {
            innerNodeCoordinator = this.innerCoordinator;
            if (nodeCoordinator == innerNodeCoordinator) {
                break;
            }
            nodeCoordinator.onAttach();
            nodeCoordinator = nodeCoordinator.wrapped;
            nodeCoordinator.getClass();
        }
        innerNodeCoordinator.onAttach();
        for (Modifier.Node node = this.head; node != null; node = node.child) {
            node.runAttachLifecycle$ui_release();
            if (node.insertedNodeAwaitingAttachForInvalidation) {
                MutableObjectIntMap mutableObjectIntMap = NodeKindKt.classToKindSetMap;
                if (!node.isAttached) {
                    InlineClassHelperKt.throwIllegalStateException("autoInvalidateInsertedNode called on unattached node");
                }
                NodeKindKt.autoInvalidateNodeIncludingDelegates(node, -1, 1);
            }
            if (node.updatedNodeAwaitingAttachForInvalidation) {
                NodeKindKt.autoInvalidateUpdatedNode(node);
            }
            node.insertedNodeAwaitingAttachForInvalidation = false;
            node.updatedNodeAwaitingAttachForInvalidation = false;
        }
    }

    public final void runDetachLifecycle$ui_release() {
        for (Modifier.Node node = this.tail; node != null; node = node.parent) {
            if (node.isAttached) {
                node.runDetachLifecycle$ui_release();
            }
        }
        NodeCoordinator nodeCoordinator = this.outerCoordinator;
        NodeCoordinator nodeCoordinator2 = this.innerCoordinator;
        while (nodeCoordinator2 != nodeCoordinator) {
            OwnedLayer ownedLayer = nodeCoordinator2.layer;
            if (ownedLayer != null) {
                ownedLayer.destroy();
            }
            nodeCoordinator2.layer = null;
            nodeCoordinator2 = nodeCoordinator2.wrappedBy;
            nodeCoordinator2.getClass();
        }
        OwnedLayer ownedLayer2 = nodeCoordinator.layer;
        if (ownedLayer2 != null) {
            ownedLayer2.destroy();
        }
        nodeCoordinator.layer = null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:116:0x026b, code lost:
    
        r13 = r28 + 2;
        r9 = r24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x0273, code lost:
    
        r3 = r3 + 1;
        r9 = r35;
        r11 = r36;
        r13 = r26;
        r14 = r29;
        r21 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0151, code lost:
    
        r26 = r13;
        r29 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0157, code lost:
    
        if ((r22 & 1) != 0) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0159, code lost:
    
        r9 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x015c, code lost:
    
        r9 = r32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x015e, code lost:
    
        r13 = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x015f, code lost:
    
        if (r13 > r3) goto L183;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0161, code lost:
    
        if (r13 == r11) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0163, code lost:
    
        if (r13 == r3) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0165, code lost:
    
        r24 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0173, code lost:
    
        if (r29[(r13 + 1) + r20] >= r29[(r13 - 1) + r20]) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0176, code lost:
    
        r24 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0178, code lost:
    
        r9 = r29[(r13 - 1) + r20];
        r14 = r9 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0181, code lost:
    
        r24 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0183, code lost:
    
        r9 = r29[(r13 + 1) + r20];
        r14 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x018a, code lost:
    
        r23 = r10 - ((r8 - r14) - r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0190, code lost:
    
        if (r3 == 0) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0192, code lost:
    
        r25 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0195, code lost:
    
        r25 = r32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0197, code lost:
    
        if (r14 != r9) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0199, code lost:
    
        r27 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x019c, code lost:
    
        r27 = r32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x019e, code lost:
    
        r25 = r23 + (r25 & r27);
        r23 = r9;
        r9 = r23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x01a8, code lost:
    
        if (r14 <= r7) goto L189;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x01aa, code lost:
    
        if (r9 <= r12) goto L190;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x01ac, code lost:
    
        r27 = r9;
        r28 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x01ba, code lost:
    
        if (r0.areItemsTheSame(r14 - 1, r27 - 1) == false) goto L188;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x01bc, code lost:
    
        r14 = r14 - 1;
        r9 = r27 - 1;
        r13 = r28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x01c3, code lost:
    
        r27 = r9;
        r28 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x01c7, code lost:
    
        r29[r20 + r28] = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x01cb, code lost:
    
        if (r24 == 0) goto L184;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x01cd, code lost:
    
        r9 = r22 - r28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x01cf, code lost:
    
        if (r9 < r11) goto L185;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x01d1, code lost:
    
        if (r9 > r3) goto L186;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x01d7, code lost:
    
        if (r26[r20 + r9] < r14) goto L187;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x01d9, code lost:
    
        r15[r32] = r14;
        r21 = 1;
        r15[1] = r27;
        r15[r17] = r23;
        r15[r19] = r25;
        r15[4] = 1;
     */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x010c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0145  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void structuralUpdate(int i, MutableVector mutableVector, MutableVector mutableVector2, Modifier.Node node, boolean z) {
        int i2;
        MutableVector mutableVector3;
        MutableVector mutableVector4;
        int i3;
        NodeChain nodeChain;
        int i4;
        int i5;
        int[] iArr;
        int[] iArr2;
        int i6;
        int i7;
        char c;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12 = 3;
        char c2 = 2;
        int i13 = 1;
        Differ differ = this.cachedDiffer;
        if (differ == null) {
            i2 = i;
            mutableVector3 = mutableVector;
            mutableVector4 = mutableVector2;
            differ = new Differ(node, i2, mutableVector3, mutableVector4, z);
            this.cachedDiffer = differ;
        } else {
            i2 = i;
            mutableVector3 = mutableVector;
            mutableVector4 = mutableVector2;
            differ.node = node;
            differ.offset = i2;
            differ.before = mutableVector3;
            differ.after = mutableVector4;
            differ.shouldAttachOnInsert = z;
        }
        int i14 = mutableVector3.size - i2;
        int i15 = mutableVector4.size - i2;
        int i16 = ((i14 + i15) + 1) / 2;
        IntStack intStack = new IntStack(i16 * 3);
        IntStack intStack2 = new IntStack(i16 * 4);
        int i17 = 0;
        intStack2.pushRange(0, i14, 0, i15);
        int i18 = (i16 * 2) + 1;
        int[] iArr3 = new int[i18];
        int[] iArr4 = new int[i18];
        int[] iArr5 = new int[5];
        while (true) {
            int i19 = intStack2.lastIndex;
            if (i19 == 0) {
                break;
            }
            char c3 = c2;
            int[] iArr6 = intStack2.stack;
            int i20 = i19 - 1;
            intStack2.lastIndex = i20;
            int i21 = iArr6[i20];
            int i22 = i17;
            int i23 = i19 - 2;
            intStack2.lastIndex = i23;
            int i24 = iArr6[i23];
            int i25 = i12;
            int i26 = i19 - 3;
            intStack2.lastIndex = i26;
            int i27 = iArr6[i26];
            int i28 = i19 - 4;
            intStack2.lastIndex = i28;
            int i29 = iArr6[i28];
            int i30 = i27 - i29;
            int i31 = i18;
            int i32 = i21 - i24;
            if (i30 < i13 || i32 < i13) {
                iArr = iArr3;
                iArr2 = iArr4;
            } else {
                int i33 = i13;
                int i34 = ((i30 + i32) + 1) / 2;
                int i35 = i31 / 2;
                int i36 = i35 + 1;
                iArr3[i36] = i29;
                iArr4[i36] = i27;
                int i37 = i22;
                while (i37 < i34) {
                    int i38 = i30 - i32;
                    int i39 = i30;
                    int i40 = i34;
                    int i41 = i33;
                    if ((Math.abs(i38) & 1) == i41) {
                        i6 = i41;
                        i7 = i6;
                    } else {
                        i6 = i22;
                        i7 = i41;
                    }
                    int i42 = -i37;
                    int i43 = i6;
                    int i44 = i42;
                    while (true) {
                        if (i44 > i37) {
                            break;
                        }
                        if (i44 != i42) {
                            if (i44 != i37) {
                                i8 = i44;
                                iArr = iArr3;
                                if (iArr3[i44 + 1 + i35] > iArr[(i8 - 1) + i35]) {
                                }
                                int i45 = ((i10 - i29) + i24) - i8;
                                int i46 = i45 - ((i37 != 0 ? 1 : i22) & (i10 == i9 ? 1 : i22));
                                int i47 = i9;
                                i11 = i45;
                                while (i10 < i27 && i11 < i21 && differ.areItemsTheSame(i10, i11)) {
                                    i10++;
                                    i11++;
                                }
                                iArr[i35 + i8] = i10;
                                if (i43 != 0) {
                                    int i48 = i11;
                                    int i49 = i38 - i8;
                                    iArr2 = iArr4;
                                    if (i49 >= i42 + 1 && i49 <= i37 - 1 && iArr2[i35 + i49] <= i10) {
                                        iArr5[i22] = i47;
                                        iArr5[1] = i46;
                                        iArr5[c3] = i10;
                                        iArr5[i25] = i48;
                                        iArr5[4] = i22;
                                        c = 1;
                                        break;
                                    }
                                } else {
                                    iArr2 = iArr4;
                                }
                                i44 = i8 + 2;
                                iArr3 = iArr;
                                iArr4 = iArr2;
                                i7 = 1;
                            } else {
                                i8 = i44;
                                iArr = iArr3;
                            }
                            i9 = iArr[(i8 - 1) + i35];
                            i10 = i9 + 1;
                            int i452 = ((i10 - i29) + i24) - i8;
                            int i462 = i452 - ((i37 != 0 ? 1 : i22) & (i10 == i9 ? 1 : i22));
                            int i472 = i9;
                            i11 = i452;
                            while (i10 < i27) {
                                i10++;
                                i11++;
                            }
                            iArr[i35 + i8] = i10;
                            if (i43 != 0) {
                            }
                            i44 = i8 + 2;
                            iArr3 = iArr;
                            iArr4 = iArr2;
                            i7 = 1;
                        } else {
                            i8 = i44;
                            iArr = iArr3;
                        }
                        i9 = iArr[i8 + 1 + i35];
                        i10 = i9;
                        int i4522 = ((i10 - i29) + i24) - i8;
                        int i4622 = i4522 - ((i37 != 0 ? 1 : i22) & (i10 == i9 ? 1 : i22));
                        int i4722 = i9;
                        i11 = i4522;
                        while (i10 < i27) {
                        }
                        iArr[i35 + i8] = i10;
                        if (i43 != 0) {
                        }
                        i44 = i8 + 2;
                        iArr3 = iArr;
                        iArr4 = iArr2;
                        i7 = 1;
                    }
                    if (Math.min(iArr5[c3] - iArr5[i22], iArr5[i25] - iArr5[c]) > 0) {
                        int i50 = iArr5[i22];
                        int i51 = iArr5[c];
                        int i52 = iArr5[i25] - i51;
                        int iMin = iArr5[c3] - i50;
                        if (i52 != iMin) {
                            iMin = Math.min(iMin, i52);
                            int i53 = iArr5[4];
                            int i54 = i53 != 0 ? 1 : i22;
                            int i55 = iArr5[i25];
                            c = 1;
                            int i56 = iArr5[1];
                            int i57 = i55 - i56;
                            int i58 = iArr5[c3];
                            int i59 = iArr5[i22];
                            i50 += ((i57 > i58 - i59 ? 1 : i22) | i54) ^ 1;
                            i51 += ((i53 != 0 ? 1 : i22) | ((i55 - i56 > i58 - i59 ? 1 : i22) ^ 1)) ^ 1;
                        } else {
                            c = 1;
                        }
                        intStack.pushDiagonal(i50, i51, iMin);
                    }
                    intStack2.pushRange(i29, iArr5[i22], i24, iArr5[c]);
                    intStack2.pushRange(iArr5[c3], i27, iArr5[i25], i21);
                }
                iArr = iArr3;
                iArr2 = iArr4;
            }
            i17 = i22;
            i18 = i31;
            c2 = c3;
            i12 = i25;
            iArr3 = iArr;
            iArr4 = iArr2;
            i13 = 1;
        }
        int i60 = i12;
        int i61 = i17;
        int i62 = -1;
        int i63 = intStack.lastIndex;
        if (i63 % 3 != 0) {
            InlineClassHelperKt.throwIllegalStateException("Array size not a multiple of 3");
        }
        if (i63 > i60) {
            i3 = i61;
            intStack.quickSort(i3, i63 - i60);
        } else {
            i3 = i61;
        }
        intStack.pushDiagonal(i14, i15, i3);
        int i64 = i3;
        int i65 = i64;
        int i66 = i65;
        while (i64 < intStack.lastIndex) {
            int[] iArr7 = intStack.stack;
            int i67 = iArr7[i64];
            int i68 = iArr7[i64 + 2];
            int i69 = i67 - i68;
            int i70 = iArr7[i64 + 1] - i68;
            i64 += 3;
            while (true) {
                nodeChain = NodeChain.this;
                if (i65 >= i69) {
                    break;
                }
                Modifier.Node node2 = differ.node.child;
                node2.getClass();
                nodeChain.getClass();
                if ((node2.kindSet & 2) != 0) {
                    NodeCoordinator nodeCoordinator = node2.coordinator;
                    nodeCoordinator.getClass();
                    NodeCoordinator nodeCoordinator2 = nodeCoordinator.wrappedBy;
                    NodeCoordinator nodeCoordinator3 = nodeCoordinator.wrapped;
                    nodeCoordinator3.getClass();
                    if (nodeCoordinator2 != null) {
                        nodeCoordinator2.wrapped = nodeCoordinator3;
                    }
                    nodeCoordinator3.wrappedBy = nodeCoordinator2;
                    access$propagateCoordinator(nodeChain, differ.node, nodeCoordinator3);
                }
                differ.node = detachAndRemoveNode(node2);
                i65++;
            }
            while (i66 < i70) {
                int i71 = differ.offset + i66;
                Modifier.Node node3 = differ.node;
                Modifier.Element element = (Modifier.Element) differ.after.content[i71];
                nodeChain.getClass();
                Modifier.Node nodeCreateAndInsertNodeAsChild = createAndInsertNodeAsChild(element, node3);
                differ.node = nodeCreateAndInsertNodeAsChild;
                if (differ.shouldAttachOnInsert) {
                    Modifier.Node node4 = nodeCreateAndInsertNodeAsChild.child;
                    node4.getClass();
                    NodeCoordinator nodeCoordinator4 = node4.coordinator;
                    nodeCoordinator4.getClass();
                    LayoutModifierNode layoutModifierNodeAsLayoutModifierNode = DelegatableNodeKt.asLayoutModifierNode(differ.node);
                    if (layoutModifierNodeAsLayoutModifierNode != null) {
                        LayoutModifierNodeCoordinator layoutModifierNodeCoordinator = new LayoutModifierNodeCoordinator(nodeChain.layoutNode, layoutModifierNodeAsLayoutModifierNode);
                        differ.node.updateCoordinator$ui_release(layoutModifierNodeCoordinator);
                        access$propagateCoordinator(nodeChain, differ.node, layoutModifierNodeCoordinator);
                        layoutModifierNodeCoordinator.wrappedBy = nodeCoordinator4.wrappedBy;
                        layoutModifierNodeCoordinator.wrapped = nodeCoordinator4;
                        nodeCoordinator4.wrappedBy = layoutModifierNodeCoordinator;
                    } else {
                        differ.node.updateCoordinator$ui_release(nodeCoordinator4);
                    }
                    differ.node.markAsAttached$ui_release();
                    differ.node.runAttachLifecycle$ui_release();
                    Modifier.Node node5 = differ.node;
                    MutableObjectIntMap mutableObjectIntMap = NodeKindKt.classToKindSetMap;
                    if (!node5.isAttached) {
                        InlineClassHelperKt.throwIllegalStateException("autoInvalidateInsertedNode called on unattached node");
                    }
                    i4 = i62;
                    i5 = 1;
                    NodeKindKt.autoInvalidateNodeIncludingDelegates(node5, i4, 1);
                } else {
                    i4 = i62;
                    i5 = 1;
                    nodeCreateAndInsertNodeAsChild.insertedNodeAwaitingAttachForInvalidation = true;
                }
                i66 += i5;
                i62 = i4;
            }
            int i72 = i62;
            while (true) {
                int i73 = i68 - 1;
                if (i68 > 0) {
                    Modifier.Node node6 = differ.node.child;
                    node6.getClass();
                    differ.node = node6;
                    MutableVector mutableVector5 = differ.before;
                    int i74 = differ.offset;
                    Modifier.Element element2 = (Modifier.Element) mutableVector5.content[i74 + i65];
                    Modifier.Element element3 = (Modifier.Element) differ.after.content[i74 + i66];
                    if (Intrinsics.areEqual(element2, element3)) {
                        nodeChain.getClass();
                    } else {
                        Modifier.Node node7 = differ.node;
                        nodeChain.getClass();
                        updateNode(element2, element3, node7);
                    }
                    i65++;
                    i66++;
                    i68 = i73;
                }
            }
            i62 = i72;
        }
        int i75 = i3;
        for (Modifier.Node node8 = this.tail.parent; node8 != null && node8 != NodeChainKt.SentinelHead; node8 = node8.parent) {
            i75 |= node8.kindSet;
            node8.aggregateChildKindSet = i75;
        }
    }

    public final void syncCoordinators() {
        LayoutNode layoutNode;
        LayoutModifierNodeCoordinator layoutModifierNodeCoordinator;
        Modifier.Node node = this.tail.parent;
        NodeCoordinator nodeCoordinator = this.innerCoordinator;
        Modifier.Node node2 = node;
        while (true) {
            layoutNode = this.layoutNode;
            if (node2 == null) {
                break;
            }
            LayoutModifierNode layoutModifierNodeAsLayoutModifierNode = DelegatableNodeKt.asLayoutModifierNode(node2);
            if (layoutModifierNodeAsLayoutModifierNode != null) {
                NodeCoordinator nodeCoordinator2 = node2.coordinator;
                if (nodeCoordinator2 != null) {
                    LayoutModifierNodeCoordinator layoutModifierNodeCoordinator2 = (LayoutModifierNodeCoordinator) nodeCoordinator2;
                    LayoutModifierNode layoutModifierNode = layoutModifierNodeCoordinator2.layoutModifierNode;
                    layoutModifierNodeCoordinator2.setLayoutModifierNode$ui_release(layoutModifierNodeAsLayoutModifierNode);
                    layoutModifierNodeCoordinator = layoutModifierNodeCoordinator2;
                    if (layoutModifierNode != node2) {
                        OwnedLayer ownedLayer = layoutModifierNodeCoordinator2.layer;
                        layoutModifierNodeCoordinator = layoutModifierNodeCoordinator2;
                        if (ownedLayer != null) {
                            ownedLayer.invalidate();
                            layoutModifierNodeCoordinator = layoutModifierNodeCoordinator2;
                        }
                    }
                } else {
                    LayoutModifierNodeCoordinator layoutModifierNodeCoordinator3 = new LayoutModifierNodeCoordinator(layoutNode, layoutModifierNodeAsLayoutModifierNode);
                    node2.updateCoordinator$ui_release(layoutModifierNodeCoordinator3);
                    layoutModifierNodeCoordinator = layoutModifierNodeCoordinator3;
                }
                nodeCoordinator.wrappedBy = layoutModifierNodeCoordinator;
                layoutModifierNodeCoordinator.wrapped = nodeCoordinator;
                nodeCoordinator = layoutModifierNodeCoordinator;
            } else {
                node2.updateCoordinator$ui_release(nodeCoordinator);
            }
            node2 = node2.parent;
        }
        LayoutNode parent$ui_release = layoutNode.getParent$ui_release();
        nodeCoordinator.wrappedBy = parent$ui_release != null ? parent$ui_release.nodes.innerCoordinator : null;
        this.outerCoordinator = nodeCoordinator;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("[");
        Modifier.Node node = this.head;
        TailModifierNode tailModifierNode = this.tail;
        if (node == tailModifierNode) {
            sb.append("]");
        } else {
            while (true) {
                if (node == null || node == tailModifierNode) {
                    break;
                }
                sb.append(String.valueOf(node));
                if (node.child == tailModifierNode) {
                    sb.append("]");
                    break;
                }
                sb.append(",");
                node = node.child;
            }
        }
        return sb.toString();
    }
}
