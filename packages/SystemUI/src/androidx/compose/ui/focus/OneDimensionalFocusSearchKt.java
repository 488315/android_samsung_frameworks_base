package androidx.compose.ui.focus;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.BeyondBoundsLayout;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.platform.AndroidComposeView;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class OneDimensionalFocusSearchKt {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FocusStateImpl.values().length];
            try {
                iArr[FocusStateImpl.ActiveParent.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FocusStateImpl.Active.ordinal()] = 2;
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

    /* JADX WARN: Removed duplicated region for block: B:12:0x009b A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final boolean backwardFocusSearch(androidx.compose.ui.focus.FocusTargetNode r9, kotlin.jvm.functions.Function1 r10) {
        /*
            androidx.compose.ui.focus.FocusStateImpl r0 = r9.getFocusState()
            int[] r1 = androidx.compose.ui.focus.OneDimensionalFocusSearchKt.WhenMappings.$EnumSwitchMapping$0
            int r0 = r0.ordinal()
            r0 = r1[r0]
            r2 = 4
            r3 = 3
            r4 = 2
            r5 = 0
            r6 = 1
            if (r0 == r6) goto L41
            if (r0 == r4) goto L3c
            if (r0 == r3) goto L3c
            if (r0 != r2) goto L36
            boolean r0 = pickChildForBackwardSearch(r9, r10)
            if (r0 != 0) goto L9c
            androidx.compose.ui.focus.FocusPropertiesImpl r0 = r9.fetchFocusProperties$ui_release()
            boolean r0 = r0.canFocus
            if (r0 == 0) goto L32
            java.lang.Object r9 = r10.mo779invoke(r9)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            goto L33
        L32:
            r9 = r5
        L33:
            if (r9 == 0) goto L9b
            goto L9c
        L36:
            kotlin.NoWhenBranchMatchedException r9 = new kotlin.NoWhenBranchMatchedException
            r9.<init>()
            throw r9
        L3c:
            boolean r9 = pickChildForBackwardSearch(r9, r10)
            return r9
        L41:
            androidx.compose.ui.focus.FocusTargetNode r0 = androidx.compose.ui.focus.FocusTraversalKt.getActiveChild(r9)
            java.lang.String r7 = "ActiveParent must have a focusedChild"
            if (r0 == 0) goto L9d
            androidx.compose.ui.focus.FocusStateImpl r8 = r0.getFocusState()
            int r8 = r8.ordinal()
            r1 = r1[r8]
            if (r1 == r6) goto L73
            if (r1 == r4) goto L67
            if (r1 == r3) goto L67
            if (r1 == r2) goto L61
            kotlin.NoWhenBranchMatchedException r9 = new kotlin.NoWhenBranchMatchedException
            r9.<init>()
            throw r9
        L61:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            r9.<init>(r7)
            throw r9
        L67:
            androidx.compose.ui.focus.FocusDirection$Companion r1 = androidx.compose.ui.focus.FocusDirection.Companion
            r1.getClass()
            int r1 = androidx.compose.ui.focus.FocusDirection.Previous
            boolean r9 = m382generateAndSearchChildren4C6V_qg(r9, r0, r1, r10)
            return r9
        L73:
            boolean r1 = backwardFocusSearch(r0, r10)
            if (r1 != 0) goto L9c
            androidx.compose.ui.focus.FocusDirection$Companion r1 = androidx.compose.ui.focus.FocusDirection.Companion
            r1.getClass()
            int r1 = androidx.compose.ui.focus.FocusDirection.Previous
            boolean r9 = m382generateAndSearchChildren4C6V_qg(r9, r0, r1, r10)
            if (r9 != 0) goto L9c
            androidx.compose.ui.focus.FocusPropertiesImpl r9 = r0.fetchFocusProperties$ui_release()
            boolean r9 = r9.canFocus
            if (r9 == 0) goto L9b
            java.lang.Object r9 = r10.mo779invoke(r0)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 == 0) goto L9b
            goto L9c
        L9b:
            return r5
        L9c:
            return r6
        L9d:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            r9.<init>(r7)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.focus.OneDimensionalFocusSearchKt.backwardFocusSearch(androidx.compose.ui.focus.FocusTargetNode, kotlin.jvm.functions.Function1):boolean");
    }

    public static final boolean forwardFocusSearch(FocusTargetNode focusTargetNode, Function1 function1) {
        int i = WhenMappings.$EnumSwitchMapping$0[focusTargetNode.getFocusState().ordinal()];
        if (i != 1) {
            if (i == 2 || i == 3) {
                return pickChildForForwardSearch(focusTargetNode, function1);
            }
            if (i == 4) {
                return focusTargetNode.fetchFocusProperties$ui_release().canFocus ? ((Boolean) function1.mo779invoke(focusTargetNode)).booleanValue() : pickChildForForwardSearch(focusTargetNode, function1);
            }
            throw new NoWhenBranchMatchedException();
        }
        FocusTargetNode activeChild = FocusTraversalKt.getActiveChild(focusTargetNode);
        if (activeChild == null) {
            throw new IllegalStateException("ActiveParent must have a focusedChild");
        }
        if (!forwardFocusSearch(activeChild, function1)) {
            FocusDirection.Companion.getClass();
            if (!m382generateAndSearchChildren4C6V_qg(focusTargetNode, activeChild, FocusDirection.Next, function1)) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: generateAndSearchChildren-4C6V_qg, reason: not valid java name */
    public static final boolean m382generateAndSearchChildren4C6V_qg(final FocusTargetNode focusTargetNode, final FocusTargetNode focusTargetNode2, final int i, final Function1 function1) {
        if (m383searchChildren4C6V_qg(focusTargetNode, focusTargetNode2, i, function1)) {
            return true;
        }
        final FocusTransactionManager requireTransactionManager = FocusTargetNodeKt.requireTransactionManager(focusTargetNode);
        final int i2 = requireTransactionManager.generation;
        final FocusTargetNode focusTargetNode3 = ((AndroidComposeView) DelegatableNodeKt.requireOwner(focusTargetNode)).focusOwner.activeFocusTargetNode;
        Boolean bool = (Boolean) BeyondBoundsLayoutKt.m365searchBeyondBoundsOMvw8(focusTargetNode, i, new Function1() { // from class: androidx.compose.ui.focus.OneDimensionalFocusSearchKt$generateAndSearchChildren$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BeyondBoundsLayout.BeyondBoundsScope beyondBoundsScope = (BeyondBoundsLayout.BeyondBoundsScope) obj;
                if (i2 != requireTransactionManager.generation || (ComposeUiFlags.isTrackFocusEnabled && focusTargetNode3 != ((AndroidComposeView) DelegatableNodeKt.requireOwner(focusTargetNode)).focusOwner.activeFocusTargetNode)) {
                    return Boolean.TRUE;
                }
                boolean m383searchChildren4C6V_qg = OneDimensionalFocusSearchKt.m383searchChildren4C6V_qg(focusTargetNode, focusTargetNode2, i, function1);
                Boolean valueOf = Boolean.valueOf(m383searchChildren4C6V_qg);
                if (m383searchChildren4C6V_qg || !beyondBoundsScope.getHasMoreContent()) {
                    return valueOf;
                }
                return null;
            }
        });
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    public static final boolean pickChildForBackwardSearch(FocusTargetNode focusTargetNode, Function1 function1) {
        MutableVector mutableVector = new MutableVector(new FocusTargetNode[16], 0);
        if (!focusTargetNode.node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("visitChildren called on an unattached node");
        }
        MutableVector mutableVector2 = new MutableVector(new Modifier.Node[16], 0);
        Modifier.Node node = focusTargetNode.node;
        Modifier.Node node2 = node.child;
        if (node2 == null) {
            DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector2, node);
        } else {
            mutableVector2.add(node2);
        }
        while (true) {
            int i = mutableVector2.size;
            if (i == 0) {
                break;
            }
            Modifier.Node node3 = (Modifier.Node) mutableVector2.removeAt(i - 1);
            if ((node3.aggregateChildKindSet & 1024) == 0) {
                DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector2, node3);
            } else {
                while (true) {
                    if (node3 == null) {
                        break;
                    }
                    if ((node3.kindSet & 1024) != 0) {
                        MutableVector mutableVector3 = null;
                        while (node3 != null) {
                            if (node3 instanceof FocusTargetNode) {
                                mutableVector.add((FocusTargetNode) node3);
                            } else if ((node3.kindSet & 1024) != 0 && (node3 instanceof DelegatingNode)) {
                                int i2 = 0;
                                for (Modifier.Node node4 = ((DelegatingNode) node3).delegate; node4 != null; node4 = node4.child) {
                                    if ((node4.kindSet & 1024) != 0) {
                                        i2++;
                                        if (i2 == 1) {
                                            node3 = node4;
                                        } else {
                                            if (mutableVector3 == null) {
                                                mutableVector3 = new MutableVector(new Modifier.Node[16], 0);
                                            }
                                            if (node3 != null) {
                                                mutableVector3.add(node3);
                                                node3 = null;
                                            }
                                            mutableVector3.add(node4);
                                        }
                                    }
                                }
                                if (i2 == 1) {
                                }
                            }
                            node3 = DelegatableNodeKt.access$pop(mutableVector3);
                        }
                    } else {
                        node3 = node3.child;
                    }
                }
            }
        }
        mutableVector.sortWith(FocusableChildrenComparator.INSTANCE);
        int i3 = mutableVector.size - 1;
        Object[] objArr = mutableVector.content;
        if (i3 < objArr.length) {
            while (i3 >= 0) {
                FocusTargetNode focusTargetNode2 = (FocusTargetNode) objArr[i3];
                if (FocusTraversalKt.isEligibleForFocusSearch(focusTargetNode2) && backwardFocusSearch(focusTargetNode2, function1)) {
                    return true;
                }
                i3--;
            }
        }
        return false;
    }

    public static final boolean pickChildForForwardSearch(FocusTargetNode focusTargetNode, Function1 function1) {
        MutableVector mutableVector = new MutableVector(new FocusTargetNode[16], 0);
        if (!focusTargetNode.node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("visitChildren called on an unattached node");
        }
        MutableVector mutableVector2 = new MutableVector(new Modifier.Node[16], 0);
        Modifier.Node node = focusTargetNode.node;
        Modifier.Node node2 = node.child;
        if (node2 == null) {
            DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector2, node);
        } else {
            mutableVector2.add(node2);
        }
        while (true) {
            int i = mutableVector2.size;
            if (i == 0) {
                break;
            }
            Modifier.Node node3 = (Modifier.Node) mutableVector2.removeAt(i - 1);
            if ((node3.aggregateChildKindSet & 1024) == 0) {
                DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector2, node3);
            } else {
                while (true) {
                    if (node3 == null) {
                        break;
                    }
                    if ((node3.kindSet & 1024) != 0) {
                        MutableVector mutableVector3 = null;
                        while (node3 != null) {
                            if (node3 instanceof FocusTargetNode) {
                                mutableVector.add((FocusTargetNode) node3);
                            } else if ((node3.kindSet & 1024) != 0 && (node3 instanceof DelegatingNode)) {
                                int i2 = 0;
                                for (Modifier.Node node4 = ((DelegatingNode) node3).delegate; node4 != null; node4 = node4.child) {
                                    if ((node4.kindSet & 1024) != 0) {
                                        i2++;
                                        if (i2 == 1) {
                                            node3 = node4;
                                        } else {
                                            if (mutableVector3 == null) {
                                                mutableVector3 = new MutableVector(new Modifier.Node[16], 0);
                                            }
                                            if (node3 != null) {
                                                mutableVector3.add(node3);
                                                node3 = null;
                                            }
                                            mutableVector3.add(node4);
                                        }
                                    }
                                }
                                if (i2 == 1) {
                                }
                            }
                            node3 = DelegatableNodeKt.access$pop(mutableVector3);
                        }
                    } else {
                        node3 = node3.child;
                    }
                }
            }
        }
        mutableVector.sortWith(FocusableChildrenComparator.INSTANCE);
        Object[] objArr = mutableVector.content;
        int i3 = mutableVector.size;
        for (int i4 = 0; i4 < i3; i4++) {
            FocusTargetNode focusTargetNode2 = (FocusTargetNode) objArr[i4];
            if (FocusTraversalKt.isEligibleForFocusSearch(focusTargetNode2) && forwardFocusSearch(focusTargetNode2, function1)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:131:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x01a5 A[EDGE_INSN: B:149:0x01a5->B:130:0x01a5 BREAK  A[LOOP:5: B:89:0x013a->B:144:0x013a], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x013c  */
    /* renamed from: searchChildren-4C6V_qg, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final boolean m383searchChildren4C6V_qg(androidx.compose.ui.focus.FocusTargetNode r11, androidx.compose.ui.focus.FocusTargetNode r12, int r13, kotlin.jvm.functions.Function1 r14) {
        /*
            Method dump skipped, instructions count: 452
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.focus.OneDimensionalFocusSearchKt.m383searchChildren4C6V_qg(androidx.compose.ui.focus.FocusTargetNode, androidx.compose.ui.focus.FocusTargetNode, int, kotlin.jvm.functions.Function1):boolean");
    }
}
