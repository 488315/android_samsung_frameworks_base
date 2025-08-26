package com.android.systemui.communal.widgets;

import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.DelegateTransitionAnimatorController;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.shared.model.CommunalScenes;

/* loaded from: classes2.dex */
public final class CommunalTransitionAnimatorController extends DelegateTransitionAnimatorController {
    public final CommunalSceneInteractor communalSceneInteractor;

    public CommunalTransitionAnimatorController(ActivityTransitionAnimator.Controller controller, CommunalSceneInteractor communalSceneInteractor) {
        super(controller);
        this.communalSceneInteractor = communalSceneInteractor;
    }

    @Override // com.android.systemui.animation.DelegateTransitionAnimatorController, com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public final void onIntentStarted(boolean z) {
        if (!z) {
            this.communalSceneInteractor._isLaunchingWidget.updateState(null, Boolean.FALSE);
        }
        this.delegate.onIntentStarted(z);
    }

    @Override // com.android.systemui.animation.DelegateTransitionAnimatorController, com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public final void onTransitionAnimationCancelled() {
        this.communalSceneInteractor._isLaunchingWidget.updateState(null, Boolean.FALSE);
        this.delegate.onTransitionAnimationCancelled();
    }

    @Override // com.android.systemui.animation.DelegateTransitionAnimatorController, com.android.systemui.animation.TransitionAnimator.Controller
    public final void onTransitionAnimationEnd(boolean z) {
        this.communalSceneInteractor._isLaunchingWidget.updateState(null, Boolean.FALSE);
        this.delegate.onTransitionAnimationEnd(z);
    }

    @Override // com.android.systemui.animation.DelegateTransitionAnimatorController, com.android.systemui.animation.TransitionAnimator.Controller
    public final void onTransitionAnimationStart(boolean z) {
        this.delegate.onTransitionAnimationStart(z);
        CommunalSceneInteractor.snapToScene$default(this.communalSceneInteractor, CommunalScenes.Blank, "CommunalTransitionAnimatorController", ActivityTransitionAnimator.TIMINGS.totalDuration, 8);
    }
}
