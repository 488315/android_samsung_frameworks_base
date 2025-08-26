package androidx.compose.ui.focus;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatingNode;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class FocusRequester {
    public final MutableVector focusRequesterNodes = new MutableVector(new FocusRequesterModifierNode[16], 0);
    public static final Companion Companion = new Companion(null);
    public static final FocusRequester Default = new FocusRequester();
    public static final FocusRequester Cancel = new FocusRequester();
    public static final FocusRequester Redirect = new FocusRequester();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: requestFocus-3ESFkO8$default, reason: not valid java name */
    public static void m378requestFocus3ESFkO8$default(FocusRequester focusRequester) {
        FocusDirection.Companion.getClass();
        final int i = FocusDirection.Enter;
        focusRequester.getClass();
        focusRequester.findFocusTargetNode$ui_release(new Function1() { // from class: androidx.compose.ui.focus.FocusRequester$requestFocus$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return Boolean.valueOf(((FocusTargetNode) obj).m380requestFocus3ESFkO8(i));
            }
        });
    }

    /* JADX WARN: Code restructure failed: missing block: B:78:0x0044, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean findFocusTargetNode$ui_release(Function1 function1) {
        boolean zM388findChildCorrespondingToFocusEnterOMvw8;
        Companion.getClass();
        if (this == Default) {
            throw new IllegalStateException("\n    Please check whether the focusRequester is FocusRequester.Cancel or FocusRequester.Default\n    before invoking any functions on the focusRequester.\n");
        }
        if (this == Cancel) {
            throw new IllegalStateException("\n    Please check whether the focusRequester is FocusRequester.Cancel or FocusRequester.Default\n    before invoking any functions on the focusRequester.\n");
        }
        MutableVector mutableVector = this.focusRequesterNodes;
        int i = mutableVector.size;
        if (i == 0) {
            throw new IllegalStateException("\n   FocusRequester is not initialized. Here are some possible fixes:\n\n   1. Remember the FocusRequester: val focusRequester = remember { FocusRequester() }\n   2. Did you forget to add a Modifier.focusRequester() ?\n   3. Are you attempting to request focus during composition? Focus requests should be made in\n   response to some event. Eg Modifier.clickable { focusRequester.requestFocus() }\n");
        }
        Object[] objArr = mutableVector.content;
        boolean z = false;
        for (int i2 = 0; i2 < i; i2++) {
            Modifier.Node node = (Modifier.Node) ((FocusRequesterModifierNode) objArr[i2]);
            if (!node.node.isAttached) {
                InlineClassHelperKt.throwIllegalStateException("visitChildren called on an unattached node");
            }
            MutableVector mutableVector2 = new MutableVector(new Modifier.Node[16], 0);
            Modifier.Node node2 = node.node;
            Modifier.Node node3 = node2.child;
            if (node3 == null) {
                DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector2, node2);
            } else {
                mutableVector2.add(node3);
            }
            while (true) {
                int i3 = mutableVector2.size;
                if (i3 != 0) {
                    Modifier.Node nodeAccess$pop = (Modifier.Node) mutableVector2.removeAt(i3 - 1);
                    if ((nodeAccess$pop.aggregateChildKindSet & 1024) == 0) {
                        DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector2, nodeAccess$pop);
                    } else {
                        while (true) {
                            if (nodeAccess$pop == null) {
                                break;
                            }
                            if ((nodeAccess$pop.kindSet & 1024) != 0) {
                                MutableVector mutableVector3 = null;
                                while (nodeAccess$pop != null) {
                                    if (nodeAccess$pop instanceof FocusTargetNode) {
                                        FocusTargetNode focusTargetNode = (FocusTargetNode) nodeAccess$pop;
                                        if (focusTargetNode.fetchFocusProperties$ui_release().canFocus) {
                                            zM388findChildCorrespondingToFocusEnterOMvw8 = ((Boolean) function1.mo781invoke(focusTargetNode)).booleanValue();
                                        } else {
                                            FocusDirection.Companion.getClass();
                                            zM388findChildCorrespondingToFocusEnterOMvw8 = TwoDimensionalFocusSearchKt.m388findChildCorrespondingToFocusEnterOMvw8(focusTargetNode, FocusDirection.Enter, function1);
                                        }
                                        if (zM388findChildCorrespondingToFocusEnterOMvw8) {
                                            z = true;
                                            break;
                                        }
                                    } else if (((nodeAccess$pop.kindSet & 1024) != 0) && (nodeAccess$pop instanceof DelegatingNode)) {
                                        int i4 = 0;
                                        for (Modifier.Node node4 = ((DelegatingNode) nodeAccess$pop).delegate; node4 != null; node4 = node4.child) {
                                            if ((node4.kindSet & 1024) != 0) {
                                                i4++;
                                                if (i4 == 1) {
                                                    nodeAccess$pop = node4;
                                                } else {
                                                    if (mutableVector3 == null) {
                                                        mutableVector3 = new MutableVector(new Modifier.Node[16], 0);
                                                    }
                                                    if (nodeAccess$pop != null) {
                                                        mutableVector3.add(nodeAccess$pop);
                                                        nodeAccess$pop = null;
                                                    }
                                                    mutableVector3.add(node4);
                                                }
                                            }
                                        }
                                        if (i4 == 1) {
                                        }
                                    }
                                    nodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector3);
                                }
                            } else {
                                nodeAccess$pop = nodeAccess$pop.child;
                            }
                        }
                    }
                }
            }
        }
        return z;
    }
}
