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
        public final Object mo781invoke(Object obj) {
            FocusEnterExitScope focusEnterExitScope = (FocusEnterExitScope) obj;
            View viewAccess$getEmbeddedView = FocusGroupNode_androidKt.access$getEmbeddedView(this.this$0);
            if (!viewAccess$getEmbeddedView.isFocused() && !viewAccess$getEmbeddedView.hasFocus()) {
                CancelIndicatingFocusBoundaryScope cancelIndicatingFocusBoundaryScope = (CancelIndicatingFocusBoundaryScope) focusEnterExitScope;
                if (!FocusInteropUtils_androidKt.requestInteropFocus(viewAccess$getEmbeddedView, FocusInteropUtils_androidKt.m370toAndroidFocusDirection3ESFkO8(cancelIndicatingFocusBoundaryScope.requestedFocusDirection), FocusGroupNode_androidKt.access$getCurrentlyFocusedRect(((AndroidComposeView) DelegatableNodeKt.requireOwner(this.this$0)).focusOwner, DelegatableNode_androidKt.requireView(this.this$0), viewAccess$getEmbeddedView))) {
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
        public final Object mo781invoke(Object obj) {
            FocusEnterExitScope focusEnterExitScope = (FocusEnterExitScope) obj;
            View viewAccess$getEmbeddedView = FocusGroupNode_androidKt.access$getEmbeddedView(this.this$0);
            if (ComposeUiFlags.isViewFocusFixEnabled) {
                if (viewAccess$getEmbeddedView.hasFocus() || viewAccess$getEmbeddedView.isFocused()) {
                    viewAccess$getEmbeddedView.clearFocus();
                }
            } else if (viewAccess$getEmbeddedView.hasFocus()) {
                FocusOwnerImpl focusOwnerImpl = ((AndroidComposeView) DelegatableNodeKt.requireOwner(this.this$0)).focusOwner;
                View viewRequireView = DelegatableNode_androidKt.requireView(this.this$0);
                if (viewAccess$getEmbeddedView instanceof ViewGroup) {
                    Rect rectAccess$getCurrentlyFocusedRect = FocusGroupNode_androidKt.access$getCurrentlyFocusedRect(focusOwnerImpl, viewRequireView, viewAccess$getEmbeddedView);
                    CancelIndicatingFocusBoundaryScope cancelIndicatingFocusBoundaryScope = (CancelIndicatingFocusBoundaryScope) focusEnterExitScope;
                    Integer numM370toAndroidFocusDirection3ESFkO8 = FocusInteropUtils_androidKt.m370toAndroidFocusDirection3ESFkO8(cancelIndicatingFocusBoundaryScope.requestedFocusDirection);
                    int iIntValue = numM370toAndroidFocusDirection3ESFkO8 != null ? numM370toAndroidFocusDirection3ESFkO8.intValue() : 130;
                    FocusFinder focusFinder = FocusFinder.getInstance();
                    View view = this.this$0.focusedChild;
                    View viewFindNextFocus = view != null ? focusFinder.findNextFocus((ViewGroup) viewRequireView, view, iIntValue) : focusFinder.findNextFocusFromRect((ViewGroup) viewRequireView, rectAccess$getCurrentlyFocusedRect, iIntValue);
                    if (viewFindNextFocus != null && FocusGroupNode_androidKt.access$containsDescendant(viewAccess$getEmbeddedView, viewFindNextFocus)) {
                        viewFindNextFocus.requestFocus(iIntValue, rectAccess$getCurrentlyFocusedRect);
                        cancelIndicatingFocusBoundaryScope.isCanceled = true;
                    } else if (!viewRequireView.requestFocus()) {
                        throw new IllegalStateException("host view did not take focus");
                    }
                } else if (!viewRequireView.requestFocus()) {
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
                    Modifier.Node nodeAccess$pop = node2;
                    MutableVector mutableVector = null;
                    while (nodeAccess$pop != null) {
                        if (nodeAccess$pop instanceof FocusTargetNode) {
                            FocusTargetNode focusTargetNode = (FocusTargetNode) nodeAccess$pop;
                            if (z) {
                                return focusTargetNode;
                            }
                            z = true;
                        } else if ((nodeAccess$pop.kindSet & 1024) != 0 && (nodeAccess$pop instanceof DelegatingNode)) {
                            int i = 0;
                            for (Modifier.Node node3 = ((DelegatingNode) nodeAccess$pop).delegate; node3 != null; node3 = node3.child) {
                                if ((node3.kindSet & 1024) != 0) {
                                    i++;
                                    if (i == 1) {
                                        nodeAccess$pop = node3;
                                    } else {
                                        if (mutableVector == null) {
                                            mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                        }
                                        if (nodeAccess$pop != null) {
                                            mutableVector.add(nodeAccess$pop);
                                            nodeAccess$pop = null;
                                        }
                                        mutableVector.add(node3);
                                    }
                                }
                            }
                            if (i == 1) {
                            }
                        }
                        nodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector);
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
        View viewAccess$getEmbeddedView = FocusGroupNode_androidKt.access$getEmbeddedView(this);
        FocusOwnerImpl focusOwnerImpl = ((AndroidComposeView) DelegatableNodeKt.requireOwner(this)).focusOwner;
        Owner ownerRequireOwner = DelegatableNodeKt.requireOwner(this);
        boolean z = (view == null || view.equals(ownerRequireOwner) || !FocusGroupNode_androidKt.access$containsDescendant(viewAccess$getEmbeddedView, view)) ? false : true;
        boolean z2 = (view2 == null || view2.equals(ownerRequireOwner) || !FocusGroupNode_androidKt.access$containsDescendant(viewAccess$getEmbeddedView, view2)) ? false : true;
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
                focusOwnerImpl.m372clearFocusI7lrPNg(FocusDirection.Exit, false, false);
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
