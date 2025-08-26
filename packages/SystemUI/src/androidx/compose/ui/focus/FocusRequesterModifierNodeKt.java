package androidx.compose.ui.focus;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatingNode;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public abstract class FocusRequesterModifierNodeKt {
    /* JADX WARN: Code restructure failed: missing block: B:98:0x0086, code lost:
    
        continue;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void requestFocus(FocusRequesterModifierNode focusRequesterModifierNode) {
        Modifier.Node node = (Modifier.Node) focusRequesterModifierNode;
        Modifier.Node nodeAccess$pop = node.node;
        MutableVector mutableVector = null;
        while (nodeAccess$pop != null) {
            if (nodeAccess$pop instanceof FocusTargetNode) {
                FocusTargetNode focusTargetNode = (FocusTargetNode) nodeAccess$pop;
                if (focusTargetNode.fetchFocusProperties$ui_release().canFocus) {
                    FocusTargetModifierNode.m379requestFocus3ESFkO8$default(focusTargetNode);
                    return;
                } else {
                    FocusDirection.Companion.getClass();
                    TwoDimensionalFocusSearchKt.m388findChildCorrespondingToFocusEnterOMvw8(focusTargetNode, FocusDirection.Enter, new Function1() { // from class: androidx.compose.ui.focus.FocusRequesterModifierNodeKt$requestFocus$1$1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            return Boolean.valueOf(FocusTargetModifierNode.m379requestFocus3ESFkO8$default((FocusTargetNode) obj));
                        }
                    });
                    return;
                }
            }
            if ((nodeAccess$pop.kindSet & 1024) != 0 && (nodeAccess$pop instanceof DelegatingNode)) {
                int i = 0;
                for (Modifier.Node node2 = ((DelegatingNode) nodeAccess$pop).delegate; node2 != null; node2 = node2.child) {
                    if ((node2.kindSet & 1024) != 0) {
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
        if (!node.node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("visitChildren called on an unattached node");
        }
        MutableVector mutableVector2 = new MutableVector(new Modifier.Node[16], 0);
        Modifier.Node node3 = node.node;
        Modifier.Node node4 = node3.child;
        if (node4 == null) {
            DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector2, node3);
        } else {
            mutableVector2.add(node4);
        }
        while (true) {
            int i2 = mutableVector2.size;
            if (i2 == 0) {
                return;
            }
            Modifier.Node nodeAccess$pop2 = (Modifier.Node) mutableVector2.removeAt(i2 - 1);
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
                                FocusTargetNode focusTargetNode2 = (FocusTargetNode) nodeAccess$pop2;
                                if (focusTargetNode2.fetchFocusProperties$ui_release().canFocus) {
                                    FocusTargetModifierNode.m379requestFocus3ESFkO8$default(focusTargetNode2);
                                    return;
                                } else {
                                    FocusDirection.Companion.getClass();
                                    TwoDimensionalFocusSearchKt.m388findChildCorrespondingToFocusEnterOMvw8(focusTargetNode2, FocusDirection.Enter, new Function1() { // from class: androidx.compose.ui.focus.FocusRequesterModifierNodeKt$requestFocus$1$1
                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final Object mo781invoke(Object obj) {
                                            return Boolean.valueOf(FocusTargetModifierNode.m379requestFocus3ESFkO8$default((FocusTargetNode) obj));
                                        }
                                    });
                                    return;
                                }
                            }
                            if ((nodeAccess$pop2.kindSet & 1024) != 0 && (nodeAccess$pop2 instanceof DelegatingNode)) {
                                int i3 = 0;
                                for (Modifier.Node node5 = ((DelegatingNode) nodeAccess$pop2).delegate; node5 != null; node5 = node5.child) {
                                    if ((node5.kindSet & 1024) != 0) {
                                        i3++;
                                        if (i3 == 1) {
                                            nodeAccess$pop2 = node5;
                                        } else {
                                            if (mutableVector3 == null) {
                                                mutableVector3 = new MutableVector(new Modifier.Node[16], 0);
                                            }
                                            if (nodeAccess$pop2 != null) {
                                                mutableVector3.add(nodeAccess$pop2);
                                                nodeAccess$pop2 = null;
                                            }
                                            mutableVector3.add(node5);
                                        }
                                    }
                                }
                                if (i3 == 1) {
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
