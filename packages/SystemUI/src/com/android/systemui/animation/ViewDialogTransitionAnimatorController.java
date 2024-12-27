package com.android.systemui.animation;

import android.util.Log;
import android.view.GhostView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.TransitionAnimator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ViewDialogTransitionAnimatorController implements DialogTransitionAnimator.Controller {
    public final DialogCuj cuj;
    public final View source;
    public final Object sourceIdentity;

    public ViewDialogTransitionAnimatorController(View view, DialogCuj dialogCuj) {
        this.source = view;
        this.cuj = dialogCuj;
        this.sourceIdentity = view;
    }

    @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
    public final TransitionAnimator.Controller createExitController() {
        return new GhostedViewTransitionAnimatorController(this.source, null, null, null, null, null, 62, null);
    }

    @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
    public final TransitionAnimator.Controller createTransitionController() {
        final GhostedViewTransitionAnimatorController ghostedViewTransitionAnimatorController = new GhostedViewTransitionAnimatorController(this.source, null, null, null, null, null, 62, null);
        return new TransitionAnimator.Controller(this) { // from class: com.android.systemui.animation.ViewDialogTransitionAnimatorController$createTransitionController$1
            public final /* synthetic */ GhostedViewTransitionAnimatorController $$delegate_0;
            public final /* synthetic */ ViewDialogTransitionAnimatorController this$0;

            {
                this.this$0 = this;
                this.$$delegate_0 = GhostedViewTransitionAnimatorController.this;
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final TransitionAnimator.State createAnimatorState() {
                return this.$$delegate_0.createAnimatorState();
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final View getOpeningWindowSyncView() {
                this.$$delegate_0.getClass();
                return null;
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final ViewGroup getTransitionContainer() {
                return this.$$delegate_0.transitionContainer;
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final boolean isLaunching() {
                return this.$$delegate_0.isLaunching;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final void onTransitionAnimationEnd(boolean z) {
                GhostedViewTransitionAnimatorController.this.onTransitionAnimationEnd(z);
                ViewDialogTransitionAnimatorController viewDialogTransitionAnimatorController = this.this$0;
                View view = viewDialogTransitionAnimatorController.source;
                if (!(view instanceof LaunchableView)) {
                    view.setVisibility(4);
                } else {
                    ((LaunchableView) view).setShouldBlockVisibilityChanges(true);
                    viewDialogTransitionAnimatorController.source.setTransitionVisibility(4);
                }
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final void onTransitionAnimationProgress(TransitionAnimator.State state, float f, float f2) {
                this.$$delegate_0.onTransitionAnimationProgress(state, f, f2);
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final void onTransitionAnimationStart(boolean z) {
                GhostView.removeGhost(this.this$0.source);
                GhostedViewTransitionAnimatorController.this.onTransitionAnimationStart(z);
            }

            @Override // com.android.systemui.animation.TransitionAnimator.Controller
            public final void setTransitionContainer(ViewGroup viewGroup) {
                this.$$delegate_0.transitionContainer = viewGroup;
            }
        };
    }

    @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
    public final DialogCuj getCuj() {
        return this.cuj;
    }

    @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
    public final Object getSourceIdentity() {
        return this.sourceIdentity;
    }

    @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
    public final ViewRootImpl getViewRoot() {
        return this.source.getViewRootImpl();
    }

    @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
    public final InteractionJankMonitor.Configuration.Builder jankConfigurationBuilder() {
        DialogCuj dialogCuj = this.cuj;
        if (dialogCuj == null) {
            return null;
        }
        return InteractionJankMonitor.Configuration.Builder.withView(dialogCuj.cujType, this.source);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
    public final void onExitAnimationCancelled() {
        View view = this.source;
        if (view instanceof LaunchableView) {
            ((LaunchableView) view).setShouldBlockVisibilityChanges(false);
        } else if (view.getVisibility() == 4) {
            this.source.setVisibility(0);
        }
    }

    @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
    public final boolean shouldAnimateExit() {
        if (this.source.getVisibility() != 4 || !this.source.isAttachedToWindow()) {
            return false;
        }
        Object parent = this.source.getParent();
        View view = parent instanceof View ? (View) parent : null;
        return view != null ? view.isShown() : true;
    }

    @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
    public final void startDrawingInOverlayOf(ViewGroup viewGroup) {
        KeyEvent.Callback callback = this.source;
        LaunchableView launchableView = callback instanceof LaunchableView ? (LaunchableView) callback : null;
        if (launchableView != null) {
            launchableView.setShouldBlockVisibilityChanges(true);
        }
        if (this.source.getParent() instanceof ViewGroup) {
            GhostView.addGhost(this.source, viewGroup);
        } else {
            Log.w("ViewDialogTransitionAnimatorController", "source was detached right before drawing was moved to overlay");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.animation.DialogTransitionAnimator.Controller
    public final void stopDrawingInOverlay() {
        View view = this.source;
        if (view instanceof LaunchableView) {
            ((LaunchableView) view).setShouldBlockVisibilityChanges(false);
        } else {
            view.setVisibility(0);
        }
    }
}
