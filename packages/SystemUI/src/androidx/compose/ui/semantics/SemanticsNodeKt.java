package androidx.compose.ui.semantics;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.SemanticsModifierNode;

/* loaded from: classes.dex */
public abstract class SemanticsNodeKt {
    /* JADX WARN: Removed duplicated region for block: B:35:0x0062 A[LOOP:0: B:4:0x000b->B:35:0x0062, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0065 A[EDGE_INSN: B:43:0x0065->B:36:0x0065 BREAK  A[LOOP:0: B:4:0x000b->B:35:0x0062], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final SemanticsNode SemanticsNode(LayoutNode layoutNode, boolean z) {
        Modifier.Node node = layoutNode.nodes.head;
        Object obj = null;
        if ((node.aggregateChildKindSet & 8) != 0) {
            loop0: while (true) {
                if (node == null) {
                    break;
                }
                if ((node.kindSet & 8) != 0) {
                    Modifier.Node nodeAccess$pop = node;
                    MutableVector mutableVector = null;
                    while (nodeAccess$pop != null) {
                        if (nodeAccess$pop instanceof SemanticsModifierNode) {
                            obj = nodeAccess$pop;
                            break loop0;
                        }
                        if ((nodeAccess$pop.kindSet & 8) != 0 && (nodeAccess$pop instanceof DelegatingNode)) {
                            int i = 0;
                            for (Modifier.Node node2 = ((DelegatingNode) nodeAccess$pop).delegate; node2 != null; node2 = node2.child) {
                                if ((node2.kindSet & 8) != 0) {
                                    i++;
                                    if (i == 1) {
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
                            if (i == 1) {
                            }
                        }
                        nodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector);
                    }
                    if ((node.aggregateChildKindSet & 8) != 0) {
                        break;
                    }
                    node = node.child;
                } else if ((node.aggregateChildKindSet & 8) != 0) {
                }
            }
        }
        obj.getClass();
        Modifier.Node node3 = ((Modifier.Node) ((SemanticsModifierNode) obj)).node;
        SemanticsConfiguration semanticsConfiguration = layoutNode.getSemanticsConfiguration();
        if (semanticsConfiguration == null) {
            semanticsConfiguration = new SemanticsConfiguration();
        }
        return new SemanticsNode(node3, z, layoutNode, semanticsConfiguration);
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x006b A[LOOP:0: B:4:0x000b->B:37:0x006b, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x006e A[EDGE_INSN: B:42:0x006e->B:38:0x006e BREAK  A[LOOP:0: B:4:0x000b->B:37:0x006b], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final SemanticsModifierNode getOuterMergingSemantics(LayoutNode layoutNode) {
        Modifier.Node node = layoutNode.nodes.head;
        Object obj = null;
        if ((node.aggregateChildKindSet & 8) != 0) {
            loop0: while (true) {
                if (node == null) {
                    break;
                }
                if ((node.kindSet & 8) != 0) {
                    Modifier.Node nodeAccess$pop = node;
                    MutableVector mutableVector = null;
                    while (nodeAccess$pop != null) {
                        if (nodeAccess$pop instanceof SemanticsModifierNode) {
                            if (((SemanticsModifierNode) nodeAccess$pop).getShouldMergeDescendantSemantics()) {
                                obj = nodeAccess$pop;
                                break loop0;
                            }
                        } else if ((nodeAccess$pop.kindSet & 8) != 0 && (nodeAccess$pop instanceof DelegatingNode)) {
                            int i = 0;
                            for (Modifier.Node node2 = ((DelegatingNode) nodeAccess$pop).delegate; node2 != null; node2 = node2.child) {
                                if ((node2.kindSet & 8) != 0) {
                                    i++;
                                    if (i == 1) {
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
                            if (i == 1) {
                            }
                        }
                        nodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector);
                    }
                    if ((node.aggregateChildKindSet & 8) != 0) {
                        break;
                    }
                    node = node.child;
                } else if ((node.aggregateChildKindSet & 8) != 0) {
                }
            }
        }
        return (SemanticsModifierNode) obj;
    }
}
