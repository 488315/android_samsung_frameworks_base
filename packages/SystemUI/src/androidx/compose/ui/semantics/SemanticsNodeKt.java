package androidx.compose.ui.semantics;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.SemanticsModifierNode;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SemanticsNodeKt {
    public static final SemanticsNode SemanticsNode(LayoutNode layoutNode, boolean z) {
        Modifier.Node node = layoutNode.nodes.head;
        Object obj = null;
        if ((node.aggregateChildKindSet & 8) != 0) {
            loop0: while (true) {
                if (node == null) {
                    break;
                }
                if ((node.kindSet & 8) != 0) {
                    Modifier.Node node2 = node;
                    MutableVector mutableVector = null;
                    while (node2 != null) {
                        if (node2 instanceof SemanticsModifierNode) {
                            obj = node2;
                            break loop0;
                        }
                        if ((node2.kindSet & 8) != 0 && (node2 instanceof DelegatingNode)) {
                            int i = 0;
                            for (Modifier.Node node3 = ((DelegatingNode) node2).delegate; node3 != null; node3 = node3.child) {
                                if ((node3.kindSet & 8) != 0) {
                                    i++;
                                    if (i == 1) {
                                        node2 = node3;
                                    } else {
                                        if (mutableVector == null) {
                                            mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                        }
                                        if (node2 != null) {
                                            mutableVector.add(node2);
                                            node2 = null;
                                        }
                                        mutableVector.add(node3);
                                    }
                                }
                            }
                            if (i == 1) {
                            }
                        }
                        node2 = DelegatableNodeKt.access$pop(mutableVector);
                    }
                }
                if ((node.aggregateChildKindSet & 8) == 0) {
                    break;
                }
                node = node.child;
            }
        }
        obj.getClass();
        Modifier.Node node4 = ((Modifier.Node) ((SemanticsModifierNode) obj)).node;
        SemanticsConfiguration semanticsConfiguration = layoutNode.getSemanticsConfiguration();
        if (semanticsConfiguration == null) {
            semanticsConfiguration = new SemanticsConfiguration();
        }
        return new SemanticsNode(node4, z, layoutNode, semanticsConfiguration);
    }

    public static final SemanticsModifierNode getOuterMergingSemantics(LayoutNode layoutNode) {
        Modifier.Node node = layoutNode.nodes.head;
        Object obj = null;
        if ((node.aggregateChildKindSet & 8) != 0) {
            loop0: while (true) {
                if (node == null) {
                    break;
                }
                if ((node.kindSet & 8) != 0) {
                    Modifier.Node node2 = node;
                    MutableVector mutableVector = null;
                    while (node2 != null) {
                        if (node2 instanceof SemanticsModifierNode) {
                            if (((SemanticsModifierNode) node2).getShouldMergeDescendantSemantics()) {
                                obj = node2;
                                break loop0;
                            }
                        } else if ((node2.kindSet & 8) != 0 && (node2 instanceof DelegatingNode)) {
                            int i = 0;
                            for (Modifier.Node node3 = ((DelegatingNode) node2).delegate; node3 != null; node3 = node3.child) {
                                if ((node3.kindSet & 8) != 0) {
                                    i++;
                                    if (i == 1) {
                                        node2 = node3;
                                    } else {
                                        if (mutableVector == null) {
                                            mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                        }
                                        if (node2 != null) {
                                            mutableVector.add(node2);
                                            node2 = null;
                                        }
                                        mutableVector.add(node3);
                                    }
                                }
                            }
                            if (i == 1) {
                            }
                        }
                        node2 = DelegatableNodeKt.access$pop(mutableVector);
                    }
                }
                if ((node.aggregateChildKindSet & 8) == 0) {
                    break;
                }
                node = node.child;
            }
        }
        return (SemanticsModifierNode) obj;
    }
}
