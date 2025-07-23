package androidx.compose.ui.node;

import androidx.collection.MutableObjectIntMap;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.internal.InlineClassHelperKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public final boolean m663hasH91voCI$ui_release(int i) {
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

    /* JADX WARN: Code restructure failed: missing block: B:100:0x01a8, code lost:
    
        if (r14 <= r7) goto L188;
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x01aa, code lost:
    
        if (r9 <= r12) goto L190;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x01ac, code lost:
    
        r27 = r9;
        r28 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x01ba, code lost:
    
        if (r0.areItemsTheSame(r14 - 1, r27 - 1) == false) goto L189;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x01bc, code lost:
    
        r14 = r14 - 1;
        r9 = r27 - 1;
        r13 = r28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x01c7, code lost:
    
        r29[r20 + r28] = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x01cb, code lost:
    
        if (r24 == 0) goto L184;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x01cd, code lost:
    
        r9 = r22 - r28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x01cf, code lost:
    
        if (r9 < r11) goto L185;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x01d1, code lost:
    
        if (r9 > r3) goto L186;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x01d7, code lost:
    
        if (r26[r20 + r9] < r14) goto L187;
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x01d9, code lost:
    
        r15[r32] = r14;
        r21 = 1;
        r15[1] = r27;
        r15[r17] = r23;
        r15[r19] = r25;
        r15[4] = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x026b, code lost:
    
        r13 = r28 + 2;
        r9 = r24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x01c3, code lost:
    
        r27 = r9;
        r28 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x019c, code lost:
    
        r27 = r32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x0195, code lost:
    
        r25 = r32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x0183, code lost:
    
        r9 = r29[(r13 + 1) + r20];
        r14 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x0176, code lost:
    
        r24 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x0181, code lost:
    
        r24 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x0273, code lost:
    
        r3 = r3 + 1;
        r9 = r35;
        r11 = r36;
        r13 = r26;
        r14 = r29;
        r21 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x015c, code lost:
    
        r9 = r32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00d1, code lost:
    
        if (r13[(r9 + 1) + r20] > r26[(r25 - 1) + r20]) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0151, code lost:
    
        r26 = r13;
        r29 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0157, code lost:
    
        if ((r22 & 1) != 0) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0159, code lost:
    
        r9 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x015e, code lost:
    
        r13 = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x015f, code lost:
    
        if (r13 > r3) goto L183;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0161, code lost:
    
        if (r13 == r11) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0163, code lost:
    
        if (r13 == r3) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0165, code lost:
    
        r24 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x0173, code lost:
    
        if (r29[(r13 + 1) + r20] >= r29[(r13 - 1) + r20]) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0178, code lost:
    
        r9 = r29[(r13 - 1) + r20];
        r14 = r9 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x018a, code lost:
    
        r23 = r10 - ((r8 - r14) - r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0190, code lost:
    
        if (r3 == 0) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0192, code lost:
    
        r25 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x0197, code lost:
    
        if (r14 != r9) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x0199, code lost:
    
        r27 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x019e, code lost:
    
        r25 = r23 + (r25 & r27);
        r23 = r9;
        r9 = r23;
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0145  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00f7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void structuralUpdate(int r32, androidx.compose.runtime.collection.MutableVector r33, androidx.compose.runtime.collection.MutableVector r34, androidx.compose.ui.Modifier.Node r35, boolean r36) {
        /*
            Method dump skipped, instructions count: 970
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.node.NodeChain.structuralUpdate(int, androidx.compose.runtime.collection.MutableVector, androidx.compose.runtime.collection.MutableVector, androidx.compose.ui.Modifier$Node, boolean):void");
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
            LayoutModifierNode asLayoutModifierNode = DelegatableNodeKt.asLayoutModifierNode(node2);
            if (asLayoutModifierNode != null) {
                NodeCoordinator nodeCoordinator2 = node2.coordinator;
                if (nodeCoordinator2 != null) {
                    LayoutModifierNodeCoordinator layoutModifierNodeCoordinator2 = (LayoutModifierNodeCoordinator) nodeCoordinator2;
                    LayoutModifierNode layoutModifierNode = layoutModifierNodeCoordinator2.layoutModifierNode;
                    layoutModifierNodeCoordinator2.setLayoutModifierNode$ui_release(asLayoutModifierNode);
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
                    LayoutModifierNodeCoordinator layoutModifierNodeCoordinator3 = new LayoutModifierNodeCoordinator(layoutNode, asLayoutModifierNode);
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
        if (node != tailModifierNode) {
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
        } else {
            sb.append("]");
        }
        return sb.toString();
    }
}
