package com.android.compose.animation;

import android.content.ComponentName;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.window.WindowAnimationState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.TransitionAnimator;

/* loaded from: classes.dex */
public final class ExpandableControllerImpl$activityController$1 implements ActivityTransitionAnimator.Controller, TransitionAnimator.Controller {
    public final /* synthetic */ TransitionAnimator.Controller $$delegate_0;
    public final /* synthetic */ TransitionAnimator.Controller $delegate;
    public final /* synthetic */ Integer $launchCujType;
    public final /* synthetic */ Integer $returnCujType;
    public final ComponentName component;
    public final /* synthetic */ ExpandableControllerImpl this$0;
    public final ActivityTransitionAnimator.TransitionCookie transitionCookie;

    public ExpandableControllerImpl$activityController$1(TransitionAnimator.Controller controller, ActivityTransitionAnimator.TransitionCookie transitionCookie, ComponentName componentName, Integer num, Integer num2, ExpandableControllerImpl expandableControllerImpl) {
        this.$delegate = controller;
        this.$launchCujType = num;
        this.$returnCujType = num2;
        this.this$0 = expandableControllerImpl;
        this.$$delegate_0 = controller;
        this.transitionCookie = transitionCookie;
        this.component = componentName;
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final TransitionAnimator.State createAnimatorState() {
        return this.$$delegate_0.createAnimatorState();
    }

    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public final ComponentName getComponent() {
        return this.component;
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final View getOpeningWindowSyncView() {
        return this.$$delegate_0.getOpeningWindowSyncView();
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final ViewGroup getTransitionContainer() {
        return this.$$delegate_0.getTransitionContainer();
    }

    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public final ActivityTransitionAnimator.TransitionCookie getTransitionCookie() {
        return this.transitionCookie;
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final WindowAnimationState getWindowAnimatorState() {
        return this.$$delegate_0.getWindowAnimatorState();
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final boolean isLaunching() {
        return this.$$delegate_0.isLaunching();
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final void onTransitionAnimationEnd(boolean z) {
        Integer num = this.$$delegate_0.isLaunching() ? this.$launchCujType : this.$returnCujType;
        if (num != null) {
            InteractionJankMonitor.getInstance().end(num.intValue());
        }
        this.$delegate.onTransitionAnimationEnd(z);
        ((SnapshotMutableStateImpl) this.this$0.overlay$delegate).setValue(null);
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final void onTransitionAnimationProgress(TransitionAnimator.State state, float f, float f2) {
        this.$$delegate_0.onTransitionAnimationProgress(state, f, f2);
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final void onTransitionAnimationStart(boolean z) {
        this.$delegate.onTransitionAnimationStart(z);
        TransitionAnimator.Controller controller = this.$$delegate_0;
        ViewGroupOverlay overlay = controller.getTransitionContainer().getOverlay();
        ExpandableControllerImpl expandableControllerImpl = this.this$0;
        ((SnapshotMutableStateImpl) expandableControllerImpl.overlay$delegate).setValue(overlay);
        Integer num = controller.isLaunching() ? this.$launchCujType : this.$returnCujType;
        if (num != null) {
            InteractionJankMonitor.getInstance().begin(expandableControllerImpl.composeViewRoot, num.intValue());
        }
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final void setTransitionContainer(ViewGroup viewGroup) {
        this.$$delegate_0.setTransitionContainer(viewGroup);
    }
}
