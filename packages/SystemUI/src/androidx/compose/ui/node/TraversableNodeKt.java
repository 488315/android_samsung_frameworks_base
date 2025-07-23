package androidx.compose.ui.node;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.internal.InlineClassHelperKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class TraversableNodeKt {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v0, types: [androidx.compose.ui.node.DelegatableNode, androidx.compose.ui.node.TraversableNode, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v10, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r3v11, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r3v9 */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v3, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r5v4 */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v6, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r5v8 */
    /* JADX WARN: Type inference failed for: r5v9 */
    public static final TraversableNode findNearestAncestor(TraversableNode traversableNode) {
        NodeChain nodeChain;
        Modifier.Node node = (Modifier.Node) traversableNode;
        if (!node.node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
        }
        Modifier.Node node2 = node.node.parent;
        LayoutNode requireLayoutNode = DelegatableNodeKt.requireLayoutNode(traversableNode);
        while (requireLayoutNode != null) {
            if ((requireLayoutNode.nodes.head.aggregateChildKindSet & 262144) != 0) {
                while (node2 != null) {
                    if ((node2.kindSet & 262144) != 0) {
                        DelegatingNode delegatingNode = node2;
                        ?? r5 = 0;
                        while (delegatingNode != 0) {
                            if (delegatingNode instanceof TraversableNode) {
                                TraversableNode traversableNode2 = (TraversableNode) delegatingNode;
                                if (Intrinsics.areEqual(traversableNode.getTraverseKey(), traversableNode2.getTraverseKey()) && traversableNode.getClass() == traversableNode2.getClass()) {
                                    return traversableNode2;
                                }
                            } else if ((delegatingNode.kindSet & 262144) != 0 && (delegatingNode instanceof DelegatingNode)) {
                                Modifier.Node node3 = delegatingNode.delegate;
                                int i = 0;
                                delegatingNode = delegatingNode;
                                r5 = r5;
                                while (node3 != null) {
                                    if ((node3.kindSet & 262144) != 0) {
                                        i++;
                                        r5 = r5;
                                        if (i == 1) {
                                            delegatingNode = node3;
                                        } else {
                                            if (r5 == 0) {
                                                r5 = new MutableVector(new Modifier.Node[16], 0);
                                            }
                                            if (delegatingNode != 0) {
                                                r5.add(delegatingNode);
                                                delegatingNode = 0;
                                            }
                                            r5.add(node3);
                                        }
                                    }
                                    node3 = node3.child;
                                    delegatingNode = delegatingNode;
                                    r5 = r5;
                                }
                                if (i == 1) {
                                }
                            }
                            delegatingNode = DelegatableNodeKt.access$pop(r5);
                        }
                    }
                    node2 = node2.parent;
                }
            }
            requireLayoutNode = requireLayoutNode.getParent$ui_release();
            node2 = (requireLayoutNode == null || (nodeChain = requireLayoutNode.nodes) == null) ? null : nodeChain.tail;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v0, types: [kotlin.jvm.functions.Function1] */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v14, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r1v15, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v18 */
    /* JADX WARN: Type inference failed for: r1v19 */
    /* JADX WARN: Type inference failed for: r1v20 */
    /* JADX WARN: Type inference failed for: r1v21 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r1v9, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    public static final void traverseAncestors(DelegatableNode delegatableNode, Object obj, Function1 function1) {
        NodeChain nodeChain;
        Modifier.Node node = (Modifier.Node) delegatableNode;
        if (!node.node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
        }
        Modifier.Node node2 = node.node.parent;
        LayoutNode requireLayoutNode = DelegatableNodeKt.requireLayoutNode(delegatableNode);
        while (requireLayoutNode != null) {
            if ((requireLayoutNode.nodes.head.aggregateChildKindSet & 262144) != 0) {
                while (node2 != null) {
                    if ((node2.kindSet & 262144) != 0) {
                        DelegatingNode delegatingNode = node2;
                        ?? r4 = 0;
                        while (delegatingNode != 0) {
                            if (delegatingNode instanceof TraversableNode) {
                                TraversableNode traversableNode = (TraversableNode) delegatingNode;
                                if (!(Intrinsics.areEqual(obj, traversableNode.getTraverseKey()) ? ((Boolean) function1.mo779invoke(traversableNode)).booleanValue() : true)) {
                                    return;
                                }
                            } else {
                                if (((delegatingNode.kindSet & 262144) != 0) && (delegatingNode instanceof DelegatingNode)) {
                                    Modifier.Node node3 = delegatingNode.delegate;
                                    int i = 0;
                                    delegatingNode = delegatingNode;
                                    r4 = r4;
                                    while (node3 != null) {
                                        if ((node3.kindSet & 262144) != 0) {
                                            i++;
                                            r4 = r4;
                                            if (i == 1) {
                                                delegatingNode = node3;
                                            } else {
                                                if (r4 == 0) {
                                                    r4 = new MutableVector(new Modifier.Node[16], 0);
                                                }
                                                if (delegatingNode != 0) {
                                                    r4.add(delegatingNode);
                                                    delegatingNode = 0;
                                                }
                                                r4.add(node3);
                                            }
                                        }
                                        node3 = node3.child;
                                        delegatingNode = delegatingNode;
                                        r4 = r4;
                                    }
                                    if (i == 1) {
                                    }
                                }
                            }
                            delegatingNode = DelegatableNodeKt.access$pop(r4);
                        }
                    }
                    node2 = node2.parent;
                }
            }
            requireLayoutNode = requireLayoutNode.getParent$ui_release();
            node2 = (requireLayoutNode == null || (nodeChain = requireLayoutNode.nodes) == null) ? null : nodeChain.tail;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v0, types: [kotlin.jvm.functions.Function1] */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v1, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v14 */
    /* JADX WARN: Type inference failed for: r6v15 */
    /* JADX WARN: Type inference failed for: r6v7 */
    /* JADX WARN: Type inference failed for: r6v8, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r6v9, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v3, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v6, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r7v9 */
    public static final void traverseDescendants(DelegatableNode delegatableNode, Object obj, Function1 function1) {
        Modifier.Node node = (Modifier.Node) delegatableNode;
        if (!node.node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("visitSubtreeIf called on an unattached node");
        }
        MutableVector mutableVector = new MutableVector(new Modifier.Node[16], 0);
        Modifier.Node node2 = node.node;
        Modifier.Node node3 = node2.child;
        if (node3 == null) {
            DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector, node2);
        } else {
            mutableVector.add(node3);
        }
        while (true) {
            int i = mutableVector.size;
            if (i == 0) {
                return;
            }
            Modifier.Node node4 = (Modifier.Node) mutableVector.removeAt(i - 1);
            if ((node4.aggregateChildKindSet & 262144) != 0) {
                for (Modifier.Node node5 = node4; node5 != null; node5 = node5.child) {
                    if ((node5.kindSet & 262144) != 0) {
                        DelegatingNode delegatingNode = node5;
                        ?? r7 = 0;
                        while (delegatingNode != 0) {
                            if (delegatingNode instanceof TraversableNode) {
                                TraversableNode traversableNode = (TraversableNode) delegatingNode;
                                TraversableNode$Companion$TraverseDescendantsAction traversableNode$Companion$TraverseDescendantsAction = Intrinsics.areEqual(obj, traversableNode.getTraverseKey()) ? (TraversableNode$Companion$TraverseDescendantsAction) function1.mo779invoke(traversableNode) : TraversableNode$Companion$TraverseDescendantsAction.ContinueTraversal;
                                if (traversableNode$Companion$TraverseDescendantsAction == TraversableNode$Companion$TraverseDescendantsAction.CancelTraversal) {
                                    return;
                                }
                                if (traversableNode$Companion$TraverseDescendantsAction == TraversableNode$Companion$TraverseDescendantsAction.SkipSubtreeAndContinueTraversal) {
                                    break;
                                }
                            } else if ((delegatingNode.kindSet & 262144) != 0 && (delegatingNode instanceof DelegatingNode)) {
                                Modifier.Node node6 = delegatingNode.delegate;
                                int i2 = 0;
                                delegatingNode = delegatingNode;
                                r7 = r7;
                                while (node6 != null) {
                                    if ((node6.kindSet & 262144) != 0) {
                                        i2++;
                                        r7 = r7;
                                        if (i2 == 1) {
                                            delegatingNode = node6;
                                        } else {
                                            if (r7 == 0) {
                                                r7 = new MutableVector(new Modifier.Node[16], 0);
                                            }
                                            if (delegatingNode != 0) {
                                                r7.add(delegatingNode);
                                                delegatingNode = 0;
                                            }
                                            r7.add(node6);
                                        }
                                    }
                                    node6 = node6.child;
                                    delegatingNode = delegatingNode;
                                    r7 = r7;
                                }
                                if (i2 == 1) {
                                }
                            }
                            delegatingNode = DelegatableNodeKt.access$pop(r7);
                        }
                    }
                }
            }
            DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector, node4);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v0, types: [androidx.compose.ui.node.DelegatableNode, androidx.compose.ui.node.TraversableNode, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r12v0, types: [kotlin.jvm.functions.Function1] */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v12, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r2v13, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v3, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r5v4 */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v6, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r5v8 */
    /* JADX WARN: Type inference failed for: r5v9 */
    public static final void traverseAncestors(TraversableNode traversableNode, Function1 function1) {
        NodeChain nodeChain;
        Modifier.Node node = (Modifier.Node) traversableNode;
        if (!node.node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
        }
        Modifier.Node node2 = node.node.parent;
        LayoutNode requireLayoutNode = DelegatableNodeKt.requireLayoutNode(traversableNode);
        while (requireLayoutNode != null) {
            if ((requireLayoutNode.nodes.head.aggregateChildKindSet & 262144) != 0) {
                while (node2 != null) {
                    if ((node2.kindSet & 262144) != 0) {
                        DelegatingNode delegatingNode = node2;
                        ?? r5 = 0;
                        while (delegatingNode != 0) {
                            boolean z = true;
                            if (delegatingNode instanceof TraversableNode) {
                                TraversableNode traversableNode2 = (TraversableNode) delegatingNode;
                                if (Intrinsics.areEqual(traversableNode.getTraverseKey(), traversableNode2.getTraverseKey()) && traversableNode.getClass() == traversableNode2.getClass()) {
                                    z = ((Boolean) function1.mo779invoke(traversableNode2)).booleanValue();
                                }
                                if (!z) {
                                    return;
                                }
                            } else {
                                if (((delegatingNode.kindSet & 262144) != 0) && (delegatingNode instanceof DelegatingNode)) {
                                    Modifier.Node node3 = delegatingNode.delegate;
                                    int i = 0;
                                    delegatingNode = delegatingNode;
                                    r5 = r5;
                                    while (node3 != null) {
                                        if ((node3.kindSet & 262144) != 0) {
                                            i++;
                                            r5 = r5;
                                            if (i == 1) {
                                                delegatingNode = node3;
                                            } else {
                                                if (r5 == 0) {
                                                    r5 = new MutableVector(new Modifier.Node[16], 0);
                                                }
                                                if (delegatingNode != 0) {
                                                    r5.add(delegatingNode);
                                                    delegatingNode = 0;
                                                }
                                                r5.add(node3);
                                            }
                                        }
                                        node3 = node3.child;
                                        delegatingNode = delegatingNode;
                                        r5 = r5;
                                    }
                                    if (i == 1) {
                                    }
                                }
                            }
                            delegatingNode = DelegatableNodeKt.access$pop(r5);
                        }
                    }
                    node2 = node2.parent;
                }
            }
            requireLayoutNode = requireLayoutNode.getParent$ui_release();
            node2 = (requireLayoutNode == null || (nodeChain = requireLayoutNode.nodes) == null) ? null : nodeChain.tail;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v0, types: [androidx.compose.ui.node.TraversableNode, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r14v0, types: [kotlin.jvm.functions.Function1] */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v15 */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v8, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r7v9, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v3, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v6, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r8v8 */
    /* JADX WARN: Type inference failed for: r8v9 */
    public static final void traverseDescendants(TraversableNode traversableNode, Function1 function1) {
        TraversableNode$Companion$TraverseDescendantsAction traversableNode$Companion$TraverseDescendantsAction;
        Modifier.Node node = (Modifier.Node) traversableNode;
        if (!node.node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("visitSubtreeIf called on an unattached node");
        }
        MutableVector mutableVector = new MutableVector(new Modifier.Node[16], 0);
        Modifier.Node node2 = node.node;
        Modifier.Node node3 = node2.child;
        if (node3 == null) {
            DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector, node2);
        } else {
            mutableVector.add(node3);
        }
        while (true) {
            int i = mutableVector.size;
            if (i == 0) {
                return;
            }
            Modifier.Node node4 = (Modifier.Node) mutableVector.removeAt(i - 1);
            if ((node4.aggregateChildKindSet & 262144) != 0) {
                for (Modifier.Node node5 = node4; node5 != null; node5 = node5.child) {
                    if ((node5.kindSet & 262144) != 0) {
                        DelegatingNode delegatingNode = node5;
                        ?? r8 = 0;
                        while (delegatingNode != 0) {
                            if (delegatingNode instanceof TraversableNode) {
                                TraversableNode traversableNode2 = (TraversableNode) delegatingNode;
                                if (Intrinsics.areEqual(traversableNode.getTraverseKey(), traversableNode2.getTraverseKey()) && traversableNode.getClass() == traversableNode2.getClass()) {
                                    traversableNode$Companion$TraverseDescendantsAction = (TraversableNode$Companion$TraverseDescendantsAction) function1.mo779invoke(traversableNode2);
                                } else {
                                    traversableNode$Companion$TraverseDescendantsAction = TraversableNode$Companion$TraverseDescendantsAction.ContinueTraversal;
                                }
                                if (traversableNode$Companion$TraverseDescendantsAction == TraversableNode$Companion$TraverseDescendantsAction.CancelTraversal) {
                                    return;
                                }
                                if (traversableNode$Companion$TraverseDescendantsAction == TraversableNode$Companion$TraverseDescendantsAction.SkipSubtreeAndContinueTraversal) {
                                    break;
                                }
                            } else if ((delegatingNode.kindSet & 262144) != 0 && (delegatingNode instanceof DelegatingNode)) {
                                Modifier.Node node6 = delegatingNode.delegate;
                                int i2 = 0;
                                delegatingNode = delegatingNode;
                                r8 = r8;
                                while (node6 != null) {
                                    if ((node6.kindSet & 262144) != 0) {
                                        i2++;
                                        r8 = r8;
                                        if (i2 == 1) {
                                            delegatingNode = node6;
                                        } else {
                                            if (r8 == 0) {
                                                r8 = new MutableVector(new Modifier.Node[16], 0);
                                            }
                                            if (delegatingNode != 0) {
                                                r8.add(delegatingNode);
                                                delegatingNode = 0;
                                            }
                                            r8.add(node6);
                                        }
                                    }
                                    node6 = node6.child;
                                    delegatingNode = delegatingNode;
                                    r8 = r8;
                                }
                                if (i2 == 1) {
                                }
                            }
                            delegatingNode = DelegatableNodeKt.access$pop(r8);
                        }
                    }
                }
            }
            DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector, node4);
        }
    }
}
