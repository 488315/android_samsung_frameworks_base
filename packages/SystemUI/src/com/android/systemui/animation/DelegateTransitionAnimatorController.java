package com.android.systemui.animation;

import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.TransitionAnimator;

public class DelegateTransitionAnimatorController implements ActivityTransitionAnimator.Controller {
    public final ActivityTransitionAnimator.Controller delegate;

    public DelegateTransitionAnimatorController(ActivityTransitionAnimator.Controller controller) {
        this.delegate = controller;
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final TransitionAnimator.State createAnimatorState() {
        return this.delegate.createAnimatorState();
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final View getOpeningWindowSyncView() {
        return this.delegate.getOpeningWindowSyncView();
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final ViewGroup getTransitionContainer() {
        return this.delegate.getTransitionContainer();
    }

    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public final ActivityTransitionAnimator.TransitionCookie getTransitionCookie() {
        return this.delegate.getTransitionCookie();
    }

    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public final boolean isBelowAnimatingWindow() {
        return this.delegate.isBelowAnimatingWindow();
    }

    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public final boolean isDialogLaunch() {
        return this.delegate.isDialogLaunch();
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public boolean isLaunching() {
        return this.delegate.isLaunching();
    }

    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public void onIntentStarted(boolean z) {
        this.delegate.onIntentStarted(z);
    }

    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public void onTransitionAnimationCancelled(Boolean bool) {
        this.delegate.onTransitionAnimationCancelled(bool);
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public void onTransitionAnimationEnd(boolean z) {
        this.delegate.onTransitionAnimationEnd(z);
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final void onTransitionAnimationProgress(TransitionAnimator.State state, float f, float f2) {
        this.delegate.onTransitionAnimationProgress(state, f, f2);
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public void onTransitionAnimationStart(boolean z) {
        this.delegate.onTransitionAnimationStart(z);
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final void setTransitionContainer(ViewGroup viewGroup) {
        this.delegate.setTransitionContainer(viewGroup);
    }
}
