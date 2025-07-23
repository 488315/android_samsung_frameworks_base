package androidx.compose.ui.focus;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.NodeChain;
import androidx.compose.ui.node.ObserverModifierNodeKt;
import androidx.compose.ui.platform.AndroidComposeView;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class FocusTransactionsKt {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FocusStateImpl.values().length];
            try {
                iArr[FocusStateImpl.Active.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FocusStateImpl.Captured.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[FocusStateImpl.ActiveParent.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[FocusStateImpl.Inactive.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final boolean clearFocus(FocusTargetNode focusTargetNode, boolean z) {
        int i = WhenMappings.$EnumSwitchMapping$0[focusTargetNode.getFocusState().ordinal()];
        if (i == 1) {
            if (ComposeUiFlags.isTrackFocusEnabled) {
                ((AndroidComposeView) DelegatableNodeKt.requireOwner(focusTargetNode)).focusOwner.setActiveFocusTargetNode(null);
                focusTargetNode.dispatchFocusCallbacks$ui_release(FocusStateImpl.Active, FocusStateImpl.Inactive);
                return true;
            }
            focusTargetNode.setFocusState(FocusStateImpl.Inactive);
            focusTargetNode.dispatchFocusCallbacks$ui_release();
            return true;
        }
        if (i == 2) {
            if (z) {
                if (ComposeUiFlags.isTrackFocusEnabled) {
                    ((AndroidComposeView) DelegatableNodeKt.requireOwner(focusTargetNode)).focusOwner.setActiveFocusTargetNode(null);
                    focusTargetNode.dispatchFocusCallbacks$ui_release(FocusStateImpl.Captured, FocusStateImpl.Inactive);
                    return z;
                }
                focusTargetNode.setFocusState(FocusStateImpl.Inactive);
                focusTargetNode.dispatchFocusCallbacks$ui_release();
            }
            return z;
        }
        if (i != 3) {
            if (i == 4) {
                return true;
            }
            throw new NoWhenBranchMatchedException();
        }
        FocusTargetNode activeChild = FocusTraversalKt.getActiveChild(focusTargetNode);
        if (!(activeChild != null ? clearFocus(activeChild, z) : true)) {
            return false;
        }
        if (ComposeUiFlags.isTrackFocusEnabled) {
            focusTargetNode.dispatchFocusCallbacks$ui_release(FocusStateImpl.ActiveParent, FocusStateImpl.Inactive);
            return true;
        }
        focusTargetNode.setFocusState(FocusStateImpl.Inactive);
        focusTargetNode.dispatchFocusCallbacks$ui_release();
        return true;
    }

    public static final void grantFocus(final FocusTargetNode focusTargetNode) {
        ObserverModifierNodeKt.observeReads(focusTargetNode, new Function0() { // from class: androidx.compose.ui.focus.FocusTransactionsKt$grantFocus$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                FocusTargetNode.this.fetchFocusProperties$ui_release();
                return Unit.INSTANCE;
            }
        });
        int i = WhenMappings.$EnumSwitchMapping$0[focusTargetNode.getFocusState().ordinal()];
        if (i == 3 || i == 4) {
            if (ComposeUiFlags.isTrackFocusEnabled) {
                ((AndroidComposeView) DelegatableNodeKt.requireOwner(focusTargetNode)).focusOwner.setActiveFocusTargetNode(focusTargetNode);
            } else {
                focusTargetNode.setFocusState(FocusStateImpl.Active);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [kotlin.jvm.functions.Function1, kotlin.jvm.internal.Lambda] */
    /* renamed from: performCustomClearFocus-Mxy_nc0, reason: not valid java name */
    public static final CustomDestinationResult m379performCustomClearFocusMxy_nc0(FocusTargetNode focusTargetNode, int i) {
        int i2 = WhenMappings.$EnumSwitchMapping$0[focusTargetNode.getFocusState().ordinal()];
        if (i2 != 1) {
            if (i2 == 2) {
                return CustomDestinationResult.Cancelled;
            }
            if (i2 == 3) {
                FocusTargetNode activeChild = FocusTraversalKt.getActiveChild(focusTargetNode);
                if (activeChild == null) {
                    throw new IllegalArgumentException("ActiveParent with no focused child");
                }
                CustomDestinationResult m379performCustomClearFocusMxy_nc0 = m379performCustomClearFocusMxy_nc0(activeChild, i);
                CustomDestinationResult customDestinationResult = CustomDestinationResult.None;
                if (m379performCustomClearFocusMxy_nc0 == customDestinationResult) {
                    m379performCustomClearFocusMxy_nc0 = null;
                }
                if (m379performCustomClearFocusMxy_nc0 != null) {
                    return m379performCustomClearFocusMxy_nc0;
                }
                if (focusTargetNode.isProcessingCustomExit) {
                    return customDestinationResult;
                }
                focusTargetNode.isProcessingCustomExit = true;
                try {
                    FocusPropertiesImpl fetchFocusProperties$ui_release = focusTargetNode.fetchFocusProperties$ui_release();
                    CancelIndicatingFocusBoundaryScope cancelIndicatingFocusBoundaryScope = new CancelIndicatingFocusBoundaryScope(i, null);
                    FocusTransactionManager focusTransactionManager = FocusTargetNodeKt.getFocusTransactionManager(focusTargetNode);
                    int i3 = focusTransactionManager != null ? focusTransactionManager.generation : 0;
                    FocusOwnerImpl focusOwnerImpl = ((AndroidComposeView) DelegatableNodeKt.requireOwner(focusTargetNode)).focusOwner;
                    FocusTargetNode focusTargetNode2 = focusOwnerImpl.activeFocusTargetNode;
                    fetchFocusProperties$ui_release.onExit.mo779invoke(cancelIndicatingFocusBoundaryScope);
                    int i4 = focusTransactionManager != null ? focusTransactionManager.generation : 0;
                    FocusTargetNode focusTargetNode3 = focusOwnerImpl.activeFocusTargetNode;
                    if (cancelIndicatingFocusBoundaryScope.isCanceled) {
                        FocusRequester.Companion.getClass();
                        return CustomDestinationResult.Cancelled;
                    }
                    if (i3 == i4 && (!ComposeUiFlags.isTrackFocusEnabled || focusTargetNode2 == focusTargetNode3 || focusTargetNode3 == null)) {
                        return customDestinationResult;
                    }
                    FocusRequester.Companion.getClass();
                    return FocusRequester.Redirect == FocusRequester.Cancel ? CustomDestinationResult.Cancelled : CustomDestinationResult.Redirected;
                } finally {
                    focusTargetNode.isProcessingCustomExit = false;
                }
            }
            if (i2 != 4) {
                throw new NoWhenBranchMatchedException();
            }
        }
        return CustomDestinationResult.None;
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [kotlin.jvm.functions.Function1, kotlin.jvm.internal.Lambda] */
    /* renamed from: performCustomEnter-Mxy_nc0, reason: not valid java name */
    public static final CustomDestinationResult m380performCustomEnterMxy_nc0(FocusTargetNode focusTargetNode, int i) {
        if (!focusTargetNode.isProcessingCustomEnter) {
            focusTargetNode.isProcessingCustomEnter = true;
            try {
                FocusPropertiesImpl fetchFocusProperties$ui_release = focusTargetNode.fetchFocusProperties$ui_release();
                CancelIndicatingFocusBoundaryScope cancelIndicatingFocusBoundaryScope = new CancelIndicatingFocusBoundaryScope(i, null);
                FocusTransactionManager focusTransactionManager = FocusTargetNodeKt.getFocusTransactionManager(focusTargetNode);
                int i2 = focusTransactionManager != null ? focusTransactionManager.generation : 0;
                FocusOwnerImpl focusOwnerImpl = ((AndroidComposeView) DelegatableNodeKt.requireOwner(focusTargetNode)).focusOwner;
                FocusTargetNode focusTargetNode2 = focusOwnerImpl.activeFocusTargetNode;
                fetchFocusProperties$ui_release.onEnter.mo779invoke(cancelIndicatingFocusBoundaryScope);
                int i3 = focusTransactionManager != null ? focusTransactionManager.generation : 0;
                FocusTargetNode focusTargetNode3 = focusOwnerImpl.activeFocusTargetNode;
                if (cancelIndicatingFocusBoundaryScope.isCanceled) {
                    FocusRequester.Companion.getClass();
                    return CustomDestinationResult.Cancelled;
                }
                if (i2 != i3 || (ComposeUiFlags.isTrackFocusEnabled && focusTargetNode2 != focusTargetNode3 && focusTargetNode3 != null)) {
                    FocusRequester.Companion.getClass();
                    return FocusRequester.Redirect == FocusRequester.Cancel ? CustomDestinationResult.Cancelled : CustomDestinationResult.Redirected;
                }
            } finally {
                focusTargetNode.isProcessingCustomEnter = false;
            }
        }
        return CustomDestinationResult.None;
    }

    /* renamed from: performCustomRequestFocus-Mxy_nc0, reason: not valid java name */
    public static final CustomDestinationResult m381performCustomRequestFocusMxy_nc0(FocusTargetNode focusTargetNode, int i) {
        Modifier.Node node;
        NodeChain nodeChain;
        int i2 = WhenMappings.$EnumSwitchMapping$0[focusTargetNode.getFocusState().ordinal()];
        if (i2 == 1 || i2 == 2) {
            return CustomDestinationResult.None;
        }
        if (i2 == 3) {
            FocusTargetNode activeChild = FocusTraversalKt.getActiveChild(focusTargetNode);
            if (activeChild != null) {
                return m379performCustomClearFocusMxy_nc0(activeChild, i);
            }
            throw new IllegalArgumentException("ActiveParent with no focused child");
        }
        if (i2 != 4) {
            throw new NoWhenBranchMatchedException();
        }
        if (!focusTargetNode.node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
        }
        Modifier.Node node2 = focusTargetNode.node.parent;
        LayoutNode requireLayoutNode = DelegatableNodeKt.requireLayoutNode(focusTargetNode);
        loop0: while (true) {
            if (requireLayoutNode == null) {
                node = null;
                break;
            }
            if ((requireLayoutNode.nodes.head.aggregateChildKindSet & 1024) != 0) {
                while (node2 != null) {
                    if ((node2.kindSet & 1024) != 0) {
                        node = node2;
                        MutableVector mutableVector = null;
                        while (node != null) {
                            if (node instanceof FocusTargetNode) {
                                break loop0;
                            }
                            if ((node.kindSet & 1024) != 0 && (node instanceof DelegatingNode)) {
                                int i3 = 0;
                                for (Modifier.Node node3 = ((DelegatingNode) node).delegate; node3 != null; node3 = node3.child) {
                                    if ((node3.kindSet & 1024) != 0) {
                                        i3++;
                                        if (i3 == 1) {
                                            node = node3;
                                        } else {
                                            if (mutableVector == null) {
                                                mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                            }
                                            if (node != null) {
                                                mutableVector.add(node);
                                                node = null;
                                            }
                                            mutableVector.add(node3);
                                        }
                                    }
                                }
                                if (i3 == 1) {
                                }
                            }
                            node = DelegatableNodeKt.access$pop(mutableVector);
                        }
                    }
                    node2 = node2.parent;
                }
            }
            requireLayoutNode = requireLayoutNode.getParent$ui_release();
            node2 = (requireLayoutNode == null || (nodeChain = requireLayoutNode.nodes) == null) ? null : nodeChain.tail;
        }
        FocusTargetNode focusTargetNode2 = (FocusTargetNode) node;
        if (focusTargetNode2 == null) {
            return CustomDestinationResult.None;
        }
        int i4 = WhenMappings.$EnumSwitchMapping$0[focusTargetNode2.getFocusState().ordinal()];
        if (i4 == 1) {
            return m380performCustomEnterMxy_nc0(focusTargetNode2, i);
        }
        if (i4 == 2) {
            return CustomDestinationResult.Cancelled;
        }
        if (i4 == 3) {
            return m381performCustomRequestFocusMxy_nc0(focusTargetNode2, i);
        }
        if (i4 != 4) {
            throw new NoWhenBranchMatchedException();
        }
        CustomDestinationResult m381performCustomRequestFocusMxy_nc0 = m381performCustomRequestFocusMxy_nc0(focusTargetNode2, i);
        CustomDestinationResult customDestinationResult = m381performCustomRequestFocusMxy_nc0 != CustomDestinationResult.None ? m381performCustomRequestFocusMxy_nc0 : null;
        return customDestinationResult == null ? m380performCustomEnterMxy_nc0(focusTargetNode2, i) : customDestinationResult;
    }

    /* JADX WARN: Removed duplicated region for block: B:239:0x02ca  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final boolean performRequestFocus(androidx.compose.ui.focus.FocusTargetNode r15) {
        /*
            Method dump skipped, instructions count: 766
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.focus.FocusTransactionsKt.performRequestFocus(androidx.compose.ui.focus.FocusTargetNode):boolean");
    }

    public static final boolean requestFocusForChild(FocusTargetNode focusTargetNode, FocusTargetNode focusTargetNode2) {
        Modifier.Node node;
        Modifier.Node node2;
        NodeChain nodeChain;
        NodeChain nodeChain2;
        if (!focusTargetNode2.node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
        }
        Modifier.Node node3 = focusTargetNode2.node.parent;
        LayoutNode requireLayoutNode = DelegatableNodeKt.requireLayoutNode(focusTargetNode2);
        loop0: while (true) {
            if (requireLayoutNode == null) {
                node = null;
                break;
            }
            if ((requireLayoutNode.nodes.head.aggregateChildKindSet & 1024) != 0) {
                while (node3 != null) {
                    if ((node3.kindSet & 1024) != 0) {
                        node = node3;
                        MutableVector mutableVector = null;
                        while (node != null) {
                            if (node instanceof FocusTargetNode) {
                                break loop0;
                            }
                            if ((node.kindSet & 1024) != 0 && (node instanceof DelegatingNode)) {
                                int i = 0;
                                for (Modifier.Node node4 = ((DelegatingNode) node).delegate; node4 != null; node4 = node4.child) {
                                    if ((node4.kindSet & 1024) != 0) {
                                        i++;
                                        if (i == 1) {
                                            node = node4;
                                        } else {
                                            if (mutableVector == null) {
                                                mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                            }
                                            if (node != null) {
                                                mutableVector.add(node);
                                                node = null;
                                            }
                                            mutableVector.add(node4);
                                        }
                                    }
                                }
                                if (i == 1) {
                                }
                            }
                            node = DelegatableNodeKt.access$pop(mutableVector);
                        }
                    }
                    node3 = node3.parent;
                }
            }
            requireLayoutNode = requireLayoutNode.getParent$ui_release();
            node3 = (requireLayoutNode == null || (nodeChain2 = requireLayoutNode.nodes) == null) ? null : nodeChain2.tail;
        }
        if (!Intrinsics.areEqual(node, focusTargetNode)) {
            throw new IllegalStateException("Non child node cannot request focus.");
        }
        int i2 = WhenMappings.$EnumSwitchMapping$0[focusTargetNode.getFocusState().ordinal()];
        if (i2 == 1) {
            grantFocus(focusTargetNode2);
            focusTargetNode.setFocusState(FocusStateImpl.ActiveParent);
            return true;
        }
        if (i2 != 2) {
            if (i2 != 3) {
                if (i2 != 4) {
                    throw new NoWhenBranchMatchedException();
                }
                if (!focusTargetNode.node.isAttached) {
                    InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
                }
                Modifier.Node node5 = focusTargetNode.node.parent;
                LayoutNode requireLayoutNode2 = DelegatableNodeKt.requireLayoutNode(focusTargetNode);
                loop4: while (true) {
                    if (requireLayoutNode2 == null) {
                        node2 = null;
                        break;
                    }
                    if ((requireLayoutNode2.nodes.head.aggregateChildKindSet & 1024) != 0) {
                        while (node5 != null) {
                            if ((node5.kindSet & 1024) != 0) {
                                node2 = node5;
                                MutableVector mutableVector2 = null;
                                while (node2 != null) {
                                    if (node2 instanceof FocusTargetNode) {
                                        break loop4;
                                    }
                                    if ((node2.kindSet & 1024) != 0 && (node2 instanceof DelegatingNode)) {
                                        int i3 = 0;
                                        for (Modifier.Node node6 = ((DelegatingNode) node2).delegate; node6 != null; node6 = node6.child) {
                                            if ((node6.kindSet & 1024) != 0) {
                                                i3++;
                                                if (i3 == 1) {
                                                    node2 = node6;
                                                } else {
                                                    if (mutableVector2 == null) {
                                                        mutableVector2 = new MutableVector(new Modifier.Node[16], 0);
                                                    }
                                                    if (node2 != null) {
                                                        mutableVector2.add(node2);
                                                        node2 = null;
                                                    }
                                                    mutableVector2.add(node6);
                                                }
                                            }
                                        }
                                        if (i3 == 1) {
                                        }
                                    }
                                    node2 = DelegatableNodeKt.access$pop(mutableVector2);
                                }
                            }
                            node5 = node5.parent;
                        }
                    }
                    requireLayoutNode2 = requireLayoutNode2.getParent$ui_release();
                    node5 = (requireLayoutNode2 == null || (nodeChain = requireLayoutNode2.nodes) == null) ? null : nodeChain.tail;
                }
                FocusTargetNode focusTargetNode3 = (FocusTargetNode) node2;
                if (focusTargetNode3 == null && ((Boolean) ((AndroidComposeView) DelegatableNodeKt.requireOwner(focusTargetNode)).focusOwner.onRequestFocusForOwner.invoke(null, null)).booleanValue()) {
                    grantFocus(focusTargetNode2);
                    focusTargetNode.setFocusState(FocusStateImpl.ActiveParent);
                    return true;
                }
                if (focusTargetNode3 != null && requestFocusForChild(focusTargetNode3, focusTargetNode)) {
                    boolean requestFocusForChild = requestFocusForChild(focusTargetNode, focusTargetNode2);
                    if (focusTargetNode.getFocusState() != FocusStateImpl.ActiveParent) {
                        throw new IllegalStateException("Deactivated node is focused");
                    }
                    if (requestFocusForChild) {
                        focusTargetNode3.dispatchFocusCallbacks$ui_release();
                    }
                    return requestFocusForChild;
                }
            } else {
                if (FocusTraversalKt.getActiveChild(focusTargetNode) == null) {
                    throw new IllegalArgumentException("ActiveParent with no focused child");
                }
                FocusTargetNode activeChild = FocusTraversalKt.getActiveChild(focusTargetNode);
                if (activeChild != null ? clearFocus(activeChild, false) : true) {
                    grantFocus(focusTargetNode2);
                    return true;
                }
            }
        }
        return false;
    }
}
