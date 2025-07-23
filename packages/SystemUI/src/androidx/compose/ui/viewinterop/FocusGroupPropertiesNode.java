package androidx.compose.ui.viewinterop;

import android.graphics.Rect;
import android.view.FocusFinder;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.focus.CancelIndicatingFocusBoundaryScope;
import androidx.compose.ui.focus.FocusDirection;
import androidx.compose.ui.focus.FocusEnterExitScope;
import androidx.compose.ui.focus.FocusInteropUtils_androidKt;
import androidx.compose.ui.focus.FocusOwnerImpl;
import androidx.compose.ui.focus.FocusProperties;
import androidx.compose.ui.focus.FocusPropertiesModifierNode;
import androidx.compose.ui.focus.FocusTargetNode;
import androidx.compose.ui.focus.FocusTransactionManager;
import androidx.compose.ui.focus.FocusTransactionsKt;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatableNode_androidKt;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.Owner;
import androidx.compose.ui.platform.AndroidComposeView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class FocusGroupPropertiesNode extends Modifier.Node implements FocusPropertiesModifierNode, ViewTreeObserver.OnGlobalFocusChangeListener {
    public ViewTreeObserver attachedViewTreeObserver;
    public View focusedChild;
    public final Function1 onEnter = new Function1() { // from class: androidx.compose.ui.viewinterop.FocusGroupPropertiesNode$onEnter$1
        {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo779invoke(Object obj) {
            FocusEnterExitScope focusEnterExitScope = (FocusEnterExitScope) obj;
            View access$getEmbeddedView = FocusGroupNode_androidKt.access$getEmbeddedView(FocusGroupPropertiesNode.this);
            if (!access$getEmbeddedView.isFocused() && !access$getEmbeddedView.hasFocus()) {
                CancelIndicatingFocusBoundaryScope cancelIndicatingFocusBoundaryScope = (CancelIndicatingFocusBoundaryScope) focusEnterExitScope;
                if (!FocusInteropUtils_androidKt.requestInteropFocus(access$getEmbeddedView, FocusInteropUtils_androidKt.m368toAndroidFocusDirection3ESFkO8(cancelIndicatingFocusBoundaryScope.requestedFocusDirection), FocusGroupNode_androidKt.access$getCurrentlyFocusedRect(((AndroidComposeView) DelegatableNodeKt.requireOwner(FocusGroupPropertiesNode.this)).focusOwner, DelegatableNode_androidKt.requireView(FocusGroupPropertiesNode.this), access$getEmbeddedView))) {
                    cancelIndicatingFocusBoundaryScope.isCanceled = true;
                }
            }
            return Unit.INSTANCE;
        }
    };
    public final Function1 onExit = new Function1() { // from class: androidx.compose.ui.viewinterop.FocusGroupPropertiesNode$onExit$1
        {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo779invoke(Object obj) {
            FocusEnterExitScope focusEnterExitScope = (FocusEnterExitScope) obj;
            View access$getEmbeddedView = FocusGroupNode_androidKt.access$getEmbeddedView(FocusGroupPropertiesNode.this);
            if (ComposeUiFlags.isViewFocusFixEnabled) {
                if (access$getEmbeddedView.hasFocus() || access$getEmbeddedView.isFocused()) {
                    access$getEmbeddedView.clearFocus();
                }
            } else if (access$getEmbeddedView.hasFocus()) {
                FocusOwnerImpl focusOwnerImpl = ((AndroidComposeView) DelegatableNodeKt.requireOwner(FocusGroupPropertiesNode.this)).focusOwner;
                View requireView = DelegatableNode_androidKt.requireView(FocusGroupPropertiesNode.this);
                if (access$getEmbeddedView instanceof ViewGroup) {
                    Rect access$getCurrentlyFocusedRect = FocusGroupNode_androidKt.access$getCurrentlyFocusedRect(focusOwnerImpl, requireView, access$getEmbeddedView);
                    CancelIndicatingFocusBoundaryScope cancelIndicatingFocusBoundaryScope = (CancelIndicatingFocusBoundaryScope) focusEnterExitScope;
                    Integer m368toAndroidFocusDirection3ESFkO8 = FocusInteropUtils_androidKt.m368toAndroidFocusDirection3ESFkO8(cancelIndicatingFocusBoundaryScope.requestedFocusDirection);
                    int intValue = m368toAndroidFocusDirection3ESFkO8 != null ? m368toAndroidFocusDirection3ESFkO8.intValue() : 130;
                    FocusFinder focusFinder = FocusFinder.getInstance();
                    View view = FocusGroupPropertiesNode.this.focusedChild;
                    View findNextFocus = view != null ? focusFinder.findNextFocus((ViewGroup) requireView, view, intValue) : focusFinder.findNextFocusFromRect((ViewGroup) requireView, access$getCurrentlyFocusedRect, intValue);
                    if (findNextFocus != null && FocusGroupNode_androidKt.access$containsDescendant(access$getEmbeddedView, findNextFocus)) {
                        findNextFocus.requestFocus(intValue, access$getCurrentlyFocusedRect);
                        cancelIndicatingFocusBoundaryScope.isCanceled = true;
                    } else if (!requireView.requestFocus()) {
                        throw new IllegalStateException("host view did not take focus");
                    }
                } else if (!requireView.requestFocus()) {
                    throw new IllegalStateException("host view did not take focus");
                }
            }
            return Unit.INSTANCE;
        }
    };

    @Override // androidx.compose.ui.focus.FocusPropertiesModifierNode
    public final void applyFocusProperties(FocusProperties focusProperties) {
        focusProperties.setCanFocus(false);
        focusProperties.setOnEnter(this.onEnter);
        focusProperties.setOnExit(this.onExit);
    }

    public final FocusTargetNode getFocusTargetOfEmbeddedViewWrapper() {
        if (!this.node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("visitLocalDescendants called on an unattached node");
        }
        Modifier.Node node = this.node;
        if ((node.aggregateChildKindSet & 1024) != 0) {
            boolean z = false;
            for (Modifier.Node node2 = node.child; node2 != null; node2 = node2.child) {
                if ((node2.kindSet & 1024) != 0) {
                    Modifier.Node node3 = node2;
                    MutableVector mutableVector = null;
                    while (node3 != null) {
                        if (node3 instanceof FocusTargetNode) {
                            FocusTargetNode focusTargetNode = (FocusTargetNode) node3;
                            if (z) {
                                return focusTargetNode;
                            }
                            z = true;
                        } else if ((node3.kindSet & 1024) != 0 && (node3 instanceof DelegatingNode)) {
                            int i = 0;
                            for (Modifier.Node node4 = ((DelegatingNode) node3).delegate; node4 != null; node4 = node4.child) {
                                if ((node4.kindSet & 1024) != 0) {
                                    i++;
                                    if (i == 1) {
                                        node3 = node4;
                                    } else {
                                        if (mutableVector == null) {
                                            mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                        }
                                        if (node3 != null) {
                                            mutableVector.add(node3);
                                            node3 = null;
                                        }
                                        mutableVector.add(node4);
                                    }
                                }
                            }
                            if (i == 1) {
                            }
                        }
                        node3 = DelegatableNodeKt.access$pop(mutableVector);
                    }
                }
            }
        }
        throw new IllegalStateException("Could not find focus target of embedded view wrapper");
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        ViewTreeObserver viewTreeObserver = DelegatableNode_androidKt.requireView(this).getViewTreeObserver();
        this.attachedViewTreeObserver = viewTreeObserver;
        viewTreeObserver.addOnGlobalFocusChangeListener(this);
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        ViewTreeObserver viewTreeObserver = this.attachedViewTreeObserver;
        if (viewTreeObserver != null && viewTreeObserver.isAlive()) {
            viewTreeObserver.removeOnGlobalFocusChangeListener(this);
        }
        this.attachedViewTreeObserver = null;
        DelegatableNode_androidKt.requireView(this).getViewTreeObserver().removeOnGlobalFocusChangeListener(this);
        this.focusedChild = null;
    }

    @Override // android.view.ViewTreeObserver.OnGlobalFocusChangeListener
    public final void onGlobalFocusChanged(View view, View view2) {
        if (DelegatableNodeKt.requireLayoutNode(this).owner == null) {
            return;
        }
        View access$getEmbeddedView = FocusGroupNode_androidKt.access$getEmbeddedView(this);
        FocusOwnerImpl focusOwnerImpl = ((AndroidComposeView) DelegatableNodeKt.requireOwner(this)).focusOwner;
        Owner requireOwner = DelegatableNodeKt.requireOwner(this);
        boolean z = (view == null || view.equals(requireOwner) || !FocusGroupNode_androidKt.access$containsDescendant(access$getEmbeddedView, view)) ? false : true;
        boolean z2 = (view2 == null || view2.equals(requireOwner) || !FocusGroupNode_androidKt.access$containsDescendant(access$getEmbeddedView, view2)) ? false : true;
        if (z && z2) {
            this.focusedChild = view2;
            return;
        }
        if (!z2) {
            if (!z) {
                this.focusedChild = null;
                return;
            }
            this.focusedChild = null;
            if (getFocusTargetOfEmbeddedViewWrapper().getFocusState().isFocused()) {
                FocusDirection.Companion.getClass();
                focusOwnerImpl.m370clearFocusI7lrPNg(FocusDirection.Exit, false, false);
                return;
            }
            return;
        }
        this.focusedChild = view2;
        FocusTargetNode focusTargetOfEmbeddedViewWrapper = getFocusTargetOfEmbeddedViewWrapper();
        if (focusTargetOfEmbeddedViewWrapper.getFocusState().getHasFocus()) {
            return;
        }
        if (ComposeUiFlags.isTrackFocusEnabled) {
            FocusTransactionsKt.performRequestFocus(focusTargetOfEmbeddedViewWrapper);
            return;
        }
        FocusTransactionManager focusTransactionManager = focusOwnerImpl.focusTransactionManager;
        try {
            if (focusTransactionManager.ongoingTransaction) {
                FocusTransactionManager.access$cancelTransaction(focusTransactionManager);
            }
            focusTransactionManager.ongoingTransaction = true;
            FocusTransactionsKt.performRequestFocus(focusTargetOfEmbeddedViewWrapper);
            FocusTransactionManager.access$commitTransaction(focusTransactionManager);
        } catch (Throwable th) {
            FocusTransactionManager.access$commitTransaction(focusTransactionManager);
            throw th;
        }
    }
}
