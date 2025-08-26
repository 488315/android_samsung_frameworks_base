package androidx.compose.ui.focus;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatingNode;

/* loaded from: classes.dex */
public abstract class FocusEventModifierNodeKt {

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FocusStateImpl.values().length];
            try {
                iArr[FocusStateImpl.Active.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FocusStateImpl.ActiveParent.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[FocusStateImpl.Captured.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[FocusStateImpl.Inactive.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:104:0x0083, code lost:
    
        continue;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final FocusStateImpl getFocusState(FocusEventModifierNode focusEventModifierNode) {
        FocusStateImpl focusState;
        Modifier.Node node = (Modifier.Node) focusEventModifierNode;
        Modifier.Node nodeAccess$pop = node.node;
        MutableVector mutableVector = null;
        while (nodeAccess$pop != null) {
            if (nodeAccess$pop instanceof FocusTargetNode) {
                FocusStateImpl focusState2 = ((FocusTargetNode) nodeAccess$pop).getFocusState();
                int i = WhenMappings.$EnumSwitchMapping$0[focusState2.ordinal()];
                if (i == 1 || i == 2 || i == 3) {
                    return focusState2;
                }
            } else if ((nodeAccess$pop.kindSet & 1024) != 0 && (nodeAccess$pop instanceof DelegatingNode)) {
                int i2 = 0;
                for (Modifier.Node node2 = ((DelegatingNode) nodeAccess$pop).delegate; node2 != null; node2 = node2.child) {
                    if ((node2.kindSet & 1024) != 0) {
                        i2++;
                        if (i2 == 1) {
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
                if (i2 == 1) {
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
        loop2: while (true) {
            int i3 = mutableVector2.size;
            if (i3 == 0) {
                return FocusStateImpl.Inactive;
            }
            Modifier.Node nodeAccess$pop2 = (Modifier.Node) mutableVector2.removeAt(i3 - 1);
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
                                focusState = ((FocusTargetNode) nodeAccess$pop2).getFocusState();
                                int i4 = WhenMappings.$EnumSwitchMapping$0[focusState.ordinal()];
                                if (i4 == 1 || i4 == 2 || i4 == 3) {
                                    break loop2;
                                }
                            } else if ((nodeAccess$pop2.kindSet & 1024) != 0 && (nodeAccess$pop2 instanceof DelegatingNode)) {
                                int i5 = 0;
                                for (Modifier.Node node5 = ((DelegatingNode) nodeAccess$pop2).delegate; node5 != null; node5 = node5.child) {
                                    if ((node5.kindSet & 1024) != 0) {
                                        i5++;
                                        if (i5 == 1) {
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
                                if (i5 == 1) {
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
        return focusState;
    }
}
