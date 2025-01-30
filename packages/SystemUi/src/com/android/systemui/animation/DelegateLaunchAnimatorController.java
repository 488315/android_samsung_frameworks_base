package com.android.systemui.animation;

import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.LaunchAnimator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class DelegateLaunchAnimatorController implements ActivityLaunchAnimator.Controller {
    public final ActivityLaunchAnimator.Controller delegate;

    public DelegateLaunchAnimatorController(ActivityLaunchAnimator.Controller controller) {
        this.delegate = controller;
    }

    @Override // com.android.systemui.animation.LaunchAnimator.Controller
    public final LaunchAnimator.State createAnimatorState() {
        return this.delegate.createAnimatorState();
    }

    @Override // com.android.systemui.animation.LaunchAnimator.Controller
    public final ViewGroup getLaunchContainer() {
        return this.delegate.getLaunchContainer();
    }

    @Override // com.android.systemui.animation.LaunchAnimator.Controller
    public final View getOpeningWindowSyncView() {
        return this.delegate.getOpeningWindowSyncView();
    }

    @Override // com.android.systemui.animation.ActivityLaunchAnimator.Controller
    public final boolean isBelowAnimatingWindow() {
        return this.delegate.isBelowAnimatingWindow();
    }

    @Override // com.android.systemui.animation.ActivityLaunchAnimator.Controller
    public final boolean isDialogLaunch() {
        return this.delegate.isDialogLaunch();
    }

    @Override // com.android.systemui.animation.ActivityLaunchAnimator.Controller
    public void onIntentStarted(boolean z) {
        this.delegate.onIntentStarted(z);
    }

    @Override // com.android.systemui.animation.ActivityLaunchAnimator.Controller
    public void onLaunchAnimationCancelled(Boolean bool) {
        this.delegate.onLaunchAnimationCancelled(bool);
    }

    @Override // com.android.systemui.animation.LaunchAnimator.Controller
    public void onLaunchAnimationEnd(boolean z) {
        this.delegate.onLaunchAnimationEnd(z);
    }

    @Override // com.android.systemui.animation.LaunchAnimator.Controller
    public final void onLaunchAnimationProgress(LaunchAnimator.State state, float f, float f2) {
        this.delegate.onLaunchAnimationProgress(state, f, f2);
    }

    @Override // com.android.systemui.animation.LaunchAnimator.Controller
    public void onLaunchAnimationStart(boolean z) {
        this.delegate.onLaunchAnimationStart(z);
    }

    @Override // com.android.systemui.animation.LaunchAnimator.Controller
    public final void setLaunchContainer(ViewGroup viewGroup) {
        this.delegate.setLaunchContainer(viewGroup);
    }
}
