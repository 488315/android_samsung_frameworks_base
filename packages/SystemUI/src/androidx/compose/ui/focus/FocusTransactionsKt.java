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
import androidx.compose.ui.viewinterop.AndroidViewHolder;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class FocusTransactionsKt {

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
        ObserverModifierNodeKt.observeReads(focusTargetNode, new Function0() { // from class: androidx.compose.ui.focus.FocusTransactionsKt.grantFocus.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                focusTargetNode.fetchFocusProperties$ui_release();
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
    public static final CustomDestinationResult m381performCustomClearFocusMxy_nc0(FocusTargetNode focusTargetNode, int i) {
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
                CustomDestinationResult customDestinationResultM381performCustomClearFocusMxy_nc0 = m381performCustomClearFocusMxy_nc0(activeChild, i);
                CustomDestinationResult customDestinationResult = CustomDestinationResult.None;
                if (customDestinationResultM381performCustomClearFocusMxy_nc0 == customDestinationResult) {
                    customDestinationResultM381performCustomClearFocusMxy_nc0 = null;
                }
                if (customDestinationResultM381performCustomClearFocusMxy_nc0 != null) {
                    return customDestinationResultM381performCustomClearFocusMxy_nc0;
                }
                if (focusTargetNode.isProcessingCustomExit) {
                    return customDestinationResult;
                }
                focusTargetNode.isProcessingCustomExit = true;
                try {
                    FocusPropertiesImpl focusPropertiesImplFetchFocusProperties$ui_release = focusTargetNode.fetchFocusProperties$ui_release();
                    CancelIndicatingFocusBoundaryScope cancelIndicatingFocusBoundaryScope = new CancelIndicatingFocusBoundaryScope(i, null);
                    FocusTransactionManager focusTransactionManager = FocusTargetNodeKt.getFocusTransactionManager(focusTargetNode);
                    int i3 = focusTransactionManager != null ? focusTransactionManager.generation : 0;
                    FocusOwnerImpl focusOwnerImpl = ((AndroidComposeView) DelegatableNodeKt.requireOwner(focusTargetNode)).focusOwner;
                    FocusTargetNode focusTargetNode2 = focusOwnerImpl.activeFocusTargetNode;
                    focusPropertiesImplFetchFocusProperties$ui_release.onExit.mo781invoke(cancelIndicatingFocusBoundaryScope);
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
    public static final CustomDestinationResult m382performCustomEnterMxy_nc0(FocusTargetNode focusTargetNode, int i) {
        if (!focusTargetNode.isProcessingCustomEnter) {
            focusTargetNode.isProcessingCustomEnter = true;
            try {
                FocusPropertiesImpl focusPropertiesImplFetchFocusProperties$ui_release = focusTargetNode.fetchFocusProperties$ui_release();
                CancelIndicatingFocusBoundaryScope cancelIndicatingFocusBoundaryScope = new CancelIndicatingFocusBoundaryScope(i, null);
                FocusTransactionManager focusTransactionManager = FocusTargetNodeKt.getFocusTransactionManager(focusTargetNode);
                int i2 = focusTransactionManager != null ? focusTransactionManager.generation : 0;
                FocusOwnerImpl focusOwnerImpl = ((AndroidComposeView) DelegatableNodeKt.requireOwner(focusTargetNode)).focusOwner;
                FocusTargetNode focusTargetNode2 = focusOwnerImpl.activeFocusTargetNode;
                focusPropertiesImplFetchFocusProperties$ui_release.onEnter.mo781invoke(cancelIndicatingFocusBoundaryScope);
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
    public static final CustomDestinationResult m383performCustomRequestFocusMxy_nc0(FocusTargetNode focusTargetNode, int i) {
        Modifier.Node nodeAccess$pop;
        NodeChain nodeChain;
        int i2 = WhenMappings.$EnumSwitchMapping$0[focusTargetNode.getFocusState().ordinal()];
        if (i2 == 1 || i2 == 2) {
            return CustomDestinationResult.None;
        }
        if (i2 == 3) {
            FocusTargetNode activeChild = FocusTraversalKt.getActiveChild(focusTargetNode);
            if (activeChild != null) {
                return m381performCustomClearFocusMxy_nc0(activeChild, i);
            }
            throw new IllegalArgumentException("ActiveParent with no focused child");
        }
        if (i2 != 4) {
            throw new NoWhenBranchMatchedException();
        }
        if (!focusTargetNode.node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
        }
        Modifier.Node node = focusTargetNode.node.parent;
        LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(focusTargetNode);
        loop0: while (true) {
            if (layoutNodeRequireLayoutNode == null) {
                nodeAccess$pop = null;
                break;
            }
            if ((layoutNodeRequireLayoutNode.nodes.head.aggregateChildKindSet & 1024) != 0) {
                while (node != null) {
                    if ((node.kindSet & 1024) != 0) {
                        nodeAccess$pop = node;
                        MutableVector mutableVector = null;
                        while (nodeAccess$pop != null) {
                            if (nodeAccess$pop instanceof FocusTargetNode) {
                                break loop0;
                            }
                            if ((nodeAccess$pop.kindSet & 1024) != 0 && (nodeAccess$pop instanceof DelegatingNode)) {
                                int i3 = 0;
                                for (Modifier.Node node2 = ((DelegatingNode) nodeAccess$pop).delegate; node2 != null; node2 = node2.child) {
                                    if ((node2.kindSet & 1024) != 0) {
                                        i3++;
                                        if (i3 == 1) {
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
                                if (i3 == 1) {
                                }
                            }
                            nodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector);
                        }
                    }
                    node = node.parent;
                }
            }
            layoutNodeRequireLayoutNode = layoutNodeRequireLayoutNode.getParent$ui_release();
            node = (layoutNodeRequireLayoutNode == null || (nodeChain = layoutNodeRequireLayoutNode.nodes) == null) ? null : nodeChain.tail;
        }
        FocusTargetNode focusTargetNode2 = (FocusTargetNode) nodeAccess$pop;
        if (focusTargetNode2 == null) {
            return CustomDestinationResult.None;
        }
        int i4 = WhenMappings.$EnumSwitchMapping$0[focusTargetNode2.getFocusState().ordinal()];
        if (i4 == 1) {
            return m382performCustomEnterMxy_nc0(focusTargetNode2, i);
        }
        if (i4 == 2) {
            return CustomDestinationResult.Cancelled;
        }
        if (i4 == 3) {
            return m383performCustomRequestFocusMxy_nc0(focusTargetNode2, i);
        }
        if (i4 != 4) {
            throw new NoWhenBranchMatchedException();
        }
        CustomDestinationResult customDestinationResultM383performCustomRequestFocusMxy_nc0 = m383performCustomRequestFocusMxy_nc0(focusTargetNode2, i);
        CustomDestinationResult customDestinationResult = customDestinationResultM383performCustomRequestFocusMxy_nc0 != CustomDestinationResult.None ? customDestinationResultM383performCustomRequestFocusMxy_nc0 : null;
        return customDestinationResult == null ? m382performCustomEnterMxy_nc0(focusTargetNode2, i) : customDestinationResult;
    }

    /* JADX WARN: Removed duplicated region for block: B:118:0x018d  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0195 A[ADDED_TO_REGION, LOOP:9: B:120:0x0195->B:124:0x01a0, LOOP_START, PHI: r2
      0x0195: PHI (r2v9 int) = (r2v6 int), (r2v10 int) binds: [B:119:0x0193, B:124:0x01a0] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:125:0x01aa  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x01af  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final boolean performRequestFocus(FocusTargetNode focusTargetNode) {
        Modifier.Node nodeAccess$pop;
        NodeChain nodeChain;
        MutableVector mutableVector;
        int i;
        Object[] objArr;
        NodeChain nodeChain2;
        NodeChain nodeChain3;
        boolean zRequestFocusForChild = false;
        if (!ComposeUiFlags.isTrackFocusEnabled) {
            int i2 = WhenMappings.$EnumSwitchMapping$0[focusTargetNode.getFocusState().ordinal()];
            if (i2 == 1 || i2 == 2) {
                zRequestFocusForChild = true;
            } else if (i2 == 3) {
                FocusTargetNode activeChild = FocusTraversalKt.getActiveChild(focusTargetNode);
                if (activeChild != null ? clearFocus(activeChild, false) : true) {
                    grantFocus(focusTargetNode);
                    zRequestFocusForChild = true;
                }
            } else {
                if (i2 != 4) {
                    throw new NoWhenBranchMatchedException();
                }
                if (!focusTargetNode.node.isAttached) {
                    InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
                }
                Modifier.Node node = focusTargetNode.node.parent;
                LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(focusTargetNode);
                loop10: while (true) {
                    if (layoutNodeRequireLayoutNode == null) {
                        nodeAccess$pop = null;
                        break;
                    }
                    if ((layoutNodeRequireLayoutNode.nodes.head.aggregateChildKindSet & 1024) != 0) {
                        while (node != null) {
                            if ((node.kindSet & 1024) != 0) {
                                nodeAccess$pop = node;
                                MutableVector mutableVector2 = null;
                                while (nodeAccess$pop != null) {
                                    if (nodeAccess$pop instanceof FocusTargetNode) {
                                        break loop10;
                                    }
                                    if ((nodeAccess$pop.kindSet & 1024) != 0 && (nodeAccess$pop instanceof DelegatingNode)) {
                                        int i3 = 0;
                                        for (Modifier.Node node2 = ((DelegatingNode) nodeAccess$pop).delegate; node2 != null; node2 = node2.child) {
                                            if ((node2.kindSet & 1024) != 0) {
                                                i3++;
                                                if (i3 == 1) {
                                                    nodeAccess$pop = node2;
                                                } else {
                                                    if (mutableVector2 == null) {
                                                        mutableVector2 = new MutableVector(new Modifier.Node[16], 0);
                                                    }
                                                    if (nodeAccess$pop != null) {
                                                        mutableVector2.add(nodeAccess$pop);
                                                        nodeAccess$pop = null;
                                                    }
                                                    mutableVector2.add(node2);
                                                }
                                            }
                                        }
                                        if (i3 == 1) {
                                        }
                                    }
                                    nodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector2);
                                }
                            }
                            node = node.parent;
                        }
                    }
                    layoutNodeRequireLayoutNode = layoutNodeRequireLayoutNode.getParent$ui_release();
                    node = (layoutNodeRequireLayoutNode == null || (nodeChain = layoutNodeRequireLayoutNode.nodes) == null) ? null : nodeChain.tail;
                }
                FocusTargetNode focusTargetNode2 = (FocusTargetNode) nodeAccess$pop;
                if (focusTargetNode2 != null) {
                    FocusStateImpl focusState = focusTargetNode2.getFocusState();
                    zRequestFocusForChild = requestFocusForChild(focusTargetNode2, focusTargetNode);
                    if (zRequestFocusForChild && focusState != focusTargetNode2.getFocusState()) {
                        focusTargetNode2.dispatchFocusCallbacks$ui_release();
                    }
                } else if (((Boolean) ((AndroidComposeView) DelegatableNodeKt.requireOwner(focusTargetNode)).focusOwner.onRequestFocusForOwner.invoke(null, null)).booleanValue()) {
                    grantFocus(focusTargetNode);
                    zRequestFocusForChild = true;
                }
            }
            if (zRequestFocusForChild) {
                if (ComposeUiFlags.isViewFocusFixEnabled) {
                    AndroidViewHolder androidViewHolder = DelegatableNodeKt.requireLayoutNode(focusTargetNode).interopViewFactoryHolder;
                    if ((androidViewHolder != null ? androidViewHolder.view : null) == null) {
                        FocusOwnerImpl focusOwnerImpl = ((AndroidComposeView) DelegatableNodeKt.requireOwner(focusTargetNode)).focusOwner;
                        FocusDirection.Companion.getClass();
                        ((Boolean) focusOwnerImpl.onRequestFocusForOwner.invoke(FocusDirection.m368boximpl(FocusDirection.Next), null)).booleanValue();
                    }
                }
                focusTargetNode.dispatchFocusCallbacks$ui_release();
            }
            return zRequestFocusForChild;
        }
        FocusOwnerImpl focusOwnerImpl2 = ((AndroidComposeView) DelegatableNodeKt.requireOwner(focusTargetNode)).focusOwner;
        FocusTargetNode focusTargetNode3 = focusOwnerImpl2.activeFocusTargetNode;
        FocusStateImpl focusState2 = focusTargetNode.getFocusState();
        if (focusTargetNode3 == focusTargetNode) {
            focusTargetNode.dispatchFocusCallbacks$ui_release(focusState2, focusState2);
            return true;
        }
        if ((focusTargetNode3 == null || clearFocus(focusTargetNode3, false)) && (focusTargetNode3 != null || ((Boolean) ((AndroidComposeView) DelegatableNodeKt.requireOwner(focusTargetNode)).focusOwner.onRequestFocusForOwner.invoke(null, null)).booleanValue())) {
            grantFocus(focusTargetNode);
            if (focusTargetNode3 != null) {
                mutableVector = new MutableVector(new FocusTargetNode[16], 0);
                if (!focusTargetNode3.node.isAttached) {
                    InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
                }
                Modifier.Node node3 = focusTargetNode3.node.parent;
                LayoutNode layoutNodeRequireLayoutNode2 = DelegatableNodeKt.requireLayoutNode(focusTargetNode3);
                while (layoutNodeRequireLayoutNode2 != null) {
                    if ((layoutNodeRequireLayoutNode2.nodes.head.aggregateChildKindSet & 1024) != 0) {
                        while (node3 != null) {
                            if ((node3.kindSet & 1024) != 0) {
                                MutableVector mutableVector3 = null;
                                Modifier.Node nodeAccess$pop2 = node3;
                                while (nodeAccess$pop2 != null) {
                                    if (nodeAccess$pop2 instanceof FocusTargetNode) {
                                        mutableVector.add((FocusTargetNode) nodeAccess$pop2);
                                    } else if ((nodeAccess$pop2.kindSet & 1024) != 0 && (nodeAccess$pop2 instanceof DelegatingNode)) {
                                        int i4 = 0;
                                        for (Modifier.Node node4 = ((DelegatingNode) nodeAccess$pop2).delegate; node4 != null; node4 = node4.child) {
                                            if ((node4.kindSet & 1024) != 0) {
                                                i4++;
                                                if (i4 == 1) {
                                                    nodeAccess$pop2 = node4;
                                                } else {
                                                    if (mutableVector3 == null) {
                                                        mutableVector3 = new MutableVector(new Modifier.Node[16], 0);
                                                    }
                                                    if (nodeAccess$pop2 != null) {
                                                        mutableVector3.add(nodeAccess$pop2);
                                                        nodeAccess$pop2 = null;
                                                    }
                                                    mutableVector3.add(node4);
                                                }
                                            }
                                        }
                                        if (i4 == 1) {
                                        }
                                    }
                                    nodeAccess$pop2 = DelegatableNodeKt.access$pop(mutableVector3);
                                }
                            }
                            node3 = node3.parent;
                        }
                    }
                    layoutNodeRequireLayoutNode2 = layoutNodeRequireLayoutNode2.getParent$ui_release();
                    node3 = (layoutNodeRequireLayoutNode2 == null || (nodeChain3 = layoutNodeRequireLayoutNode2.nodes) == null) ? null : nodeChain3.tail;
                }
            } else {
                mutableVector = null;
            }
            MutableVector mutableVector4 = new MutableVector(new FocusTargetNode[16], 0);
            if (!focusTargetNode.node.isAttached) {
                InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
            }
            Modifier.Node node5 = focusTargetNode.node.parent;
            LayoutNode layoutNodeRequireLayoutNode3 = DelegatableNodeKt.requireLayoutNode(focusTargetNode);
            while (layoutNodeRequireLayoutNode3 != null) {
                if ((layoutNodeRequireLayoutNode3.nodes.head.aggregateChildKindSet & 1024) != 0) {
                    while (node5 != null) {
                        if ((node5.kindSet & 1024) != 0) {
                            MutableVector mutableVector5 = null;
                            Modifier.Node nodeAccess$pop3 = node5;
                            while (nodeAccess$pop3 != null) {
                                if (nodeAccess$pop3 instanceof FocusTargetNode) {
                                    FocusTargetNode focusTargetNode4 = (FocusTargetNode) nodeAccess$pop3;
                                    Boolean boolValueOf = mutableVector != null ? Boolean.valueOf(mutableVector.remove(focusTargetNode4)) : null;
                                    if (boolValueOf == null || !boolValueOf.booleanValue()) {
                                        mutableVector4.add(focusTargetNode4);
                                    }
                                } else if ((nodeAccess$pop3.kindSet & 1024) != 0 && (nodeAccess$pop3 instanceof DelegatingNode)) {
                                    int i5 = 0;
                                    for (Modifier.Node node6 = ((DelegatingNode) nodeAccess$pop3).delegate; node6 != null; node6 = node6.child) {
                                        if ((node6.kindSet & 1024) != 0) {
                                            i5++;
                                            if (i5 == 1) {
                                                nodeAccess$pop3 = node6;
                                            } else {
                                                if (mutableVector5 == null) {
                                                    mutableVector5 = new MutableVector(new Modifier.Node[16], 0);
                                                }
                                                if (nodeAccess$pop3 != null) {
                                                    mutableVector5.add(nodeAccess$pop3);
                                                    nodeAccess$pop3 = null;
                                                }
                                                mutableVector5.add(node6);
                                            }
                                        }
                                    }
                                    if (i5 == 1) {
                                    }
                                }
                                nodeAccess$pop3 = DelegatableNodeKt.access$pop(mutableVector5);
                            }
                        }
                        node5 = node5.parent;
                    }
                }
                layoutNodeRequireLayoutNode3 = layoutNodeRequireLayoutNode3.getParent$ui_release();
                node5 = (layoutNodeRequireLayoutNode3 == null || (nodeChain2 = layoutNodeRequireLayoutNode3.nodes) == null) ? null : nodeChain2.tail;
            }
            if (mutableVector != null) {
                int i6 = mutableVector.size - 1;
                Object[] objArr2 = mutableVector.content;
                if (i6 < objArr2.length) {
                    while (i6 >= 0) {
                        FocusTargetNode focusTargetNode5 = (FocusTargetNode) objArr2[i6];
                        if (focusOwnerImpl2.activeFocusTargetNode != focusTargetNode) {
                            break;
                        }
                        focusTargetNode5.dispatchFocusCallbacks$ui_release(FocusStateImpl.ActiveParent, FocusStateImpl.Inactive);
                        i6--;
                    }
                    i = mutableVector4.size - 1;
                    objArr = mutableVector4.content;
                    if (i >= objArr.length) {
                        while (i >= 0) {
                            FocusTargetNode focusTargetNode6 = (FocusTargetNode) objArr[i];
                            if (focusOwnerImpl2.activeFocusTargetNode != focusTargetNode) {
                                break;
                            }
                            focusTargetNode6.dispatchFocusCallbacks$ui_release(FocusStateImpl.Inactive, FocusStateImpl.ActiveParent);
                            i--;
                        }
                        if (focusOwnerImpl2.activeFocusTargetNode == focusTargetNode) {
                            focusTargetNode.dispatchFocusCallbacks$ui_release(focusState2, FocusStateImpl.Active);
                            if (focusOwnerImpl2.activeFocusTargetNode == focusTargetNode) {
                                if (ComposeUiFlags.isViewFocusFixEnabled) {
                                    AndroidViewHolder androidViewHolder2 = DelegatableNodeKt.requireLayoutNode(focusTargetNode).interopViewFactoryHolder;
                                    if ((androidViewHolder2 != null ? androidViewHolder2.view : null) == null) {
                                        FocusOwnerImpl focusOwnerImpl3 = ((AndroidComposeView) DelegatableNodeKt.requireOwner(focusTargetNode)).focusOwner;
                                        FocusDirection.Companion.getClass();
                                        ((Boolean) focusOwnerImpl3.onRequestFocusForOwner.invoke(FocusDirection.m368boximpl(FocusDirection.Next), null)).booleanValue();
                                    }
                                }
                                return true;
                            }
                        }
                    } else if (focusOwnerImpl2.activeFocusTargetNode == focusTargetNode) {
                    }
                } else {
                    i = mutableVector4.size - 1;
                    objArr = mutableVector4.content;
                    if (i >= objArr.length) {
                    }
                }
            }
        }
        return false;
    }

    public static final boolean requestFocusForChild(FocusTargetNode focusTargetNode, FocusTargetNode focusTargetNode2) {
        Modifier.Node nodeAccess$pop;
        Modifier.Node nodeAccess$pop2;
        NodeChain nodeChain;
        NodeChain nodeChain2;
        if (!focusTargetNode2.node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
        }
        Modifier.Node node = focusTargetNode2.node.parent;
        LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(focusTargetNode2);
        loop0: while (true) {
            if (layoutNodeRequireLayoutNode == null) {
                nodeAccess$pop = null;
                break;
            }
            if ((layoutNodeRequireLayoutNode.nodes.head.aggregateChildKindSet & 1024) != 0) {
                while (node != null) {
                    if ((node.kindSet & 1024) != 0) {
                        nodeAccess$pop = node;
                        MutableVector mutableVector = null;
                        while (nodeAccess$pop != null) {
                            if (nodeAccess$pop instanceof FocusTargetNode) {
                                break loop0;
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
                    }
                    node = node.parent;
                }
            }
            layoutNodeRequireLayoutNode = layoutNodeRequireLayoutNode.getParent$ui_release();
            node = (layoutNodeRequireLayoutNode == null || (nodeChain2 = layoutNodeRequireLayoutNode.nodes) == null) ? null : nodeChain2.tail;
        }
        if (!Intrinsics.areEqual(nodeAccess$pop, focusTargetNode)) {
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
                Modifier.Node node3 = focusTargetNode.node.parent;
                LayoutNode layoutNodeRequireLayoutNode2 = DelegatableNodeKt.requireLayoutNode(focusTargetNode);
                loop4: while (true) {
                    if (layoutNodeRequireLayoutNode2 == null) {
                        nodeAccess$pop2 = null;
                        break;
                    }
                    if ((layoutNodeRequireLayoutNode2.nodes.head.aggregateChildKindSet & 1024) != 0) {
                        while (node3 != null) {
                            if ((node3.kindSet & 1024) != 0) {
                                nodeAccess$pop2 = node3;
                                MutableVector mutableVector2 = null;
                                while (nodeAccess$pop2 != null) {
                                    if (nodeAccess$pop2 instanceof FocusTargetNode) {
                                        break loop4;
                                    }
                                    if ((nodeAccess$pop2.kindSet & 1024) != 0 && (nodeAccess$pop2 instanceof DelegatingNode)) {
                                        int i3 = 0;
                                        for (Modifier.Node node4 = ((DelegatingNode) nodeAccess$pop2).delegate; node4 != null; node4 = node4.child) {
                                            if ((node4.kindSet & 1024) != 0) {
                                                i3++;
                                                if (i3 == 1) {
                                                    nodeAccess$pop2 = node4;
                                                } else {
                                                    if (mutableVector2 == null) {
                                                        mutableVector2 = new MutableVector(new Modifier.Node[16], 0);
                                                    }
                                                    if (nodeAccess$pop2 != null) {
                                                        mutableVector2.add(nodeAccess$pop2);
                                                        nodeAccess$pop2 = null;
                                                    }
                                                    mutableVector2.add(node4);
                                                }
                                            }
                                        }
                                        if (i3 == 1) {
                                        }
                                    }
                                    nodeAccess$pop2 = DelegatableNodeKt.access$pop(mutableVector2);
                                }
                            }
                            node3 = node3.parent;
                        }
                    }
                    layoutNodeRequireLayoutNode2 = layoutNodeRequireLayoutNode2.getParent$ui_release();
                    node3 = (layoutNodeRequireLayoutNode2 == null || (nodeChain = layoutNodeRequireLayoutNode2.nodes) == null) ? null : nodeChain.tail;
                }
                FocusTargetNode focusTargetNode3 = (FocusTargetNode) nodeAccess$pop2;
                if (focusTargetNode3 == null && ((Boolean) ((AndroidComposeView) DelegatableNodeKt.requireOwner(focusTargetNode)).focusOwner.onRequestFocusForOwner.invoke(null, null)).booleanValue()) {
                    grantFocus(focusTargetNode2);
                    focusTargetNode.setFocusState(FocusStateImpl.ActiveParent);
                    return true;
                }
                if (focusTargetNode3 != null && requestFocusForChild(focusTargetNode3, focusTargetNode)) {
                    boolean zRequestFocusForChild = requestFocusForChild(focusTargetNode, focusTargetNode2);
                    if (focusTargetNode.getFocusState() != FocusStateImpl.ActiveParent) {
                        throw new IllegalStateException("Deactivated node is focused");
                    }
                    if (zRequestFocusForChild) {
                        focusTargetNode3.dispatchFocusCallbacks$ui_release();
                    }
                    return zRequestFocusForChild;
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
