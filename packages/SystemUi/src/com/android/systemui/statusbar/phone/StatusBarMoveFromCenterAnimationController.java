package com.android.systemui.statusbar.phone;

import android.view.WindowManager;
import com.android.systemui.shared.animation.UnfoldMoveFromCenterAnimator;
import com.android.systemui.statusbar.phone.PhoneStatusBarViewController;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.system.ActivityManagerActivityTypeProvider;
import com.android.systemui.unfold.util.CurrentActivityTypeProvider;
import com.android.systemui.unfold.util.ScopedUnfoldTransitionProgressProvider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarMoveFromCenterAnimationController {
    public final CurrentActivityTypeProvider currentActivityTypeProvider;
    public Boolean isOnHomeActivity;
    public final UnfoldMoveFromCenterAnimator moveFromCenterAnimator;
    public final ScopedUnfoldTransitionProgressProvider progressProvider;
    public final TransitionListener transitionListener = new TransitionListener();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class StatusBarIconsAlphaProvider implements UnfoldMoveFromCenterAnimator.AlphaProvider {
        public StatusBarIconsAlphaProvider() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TransitionListener implements UnfoldTransitionProgressProvider.TransitionProgressListener {
        public TransitionListener() {
        }

        @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
        public final void onTransitionFinished() {
            StatusBarMoveFromCenterAnimationController statusBarMoveFromCenterAnimationController = StatusBarMoveFromCenterAnimationController.this;
            statusBarMoveFromCenterAnimationController.moveFromCenterAnimator.onTransitionProgress(1.0f);
            statusBarMoveFromCenterAnimationController.isOnHomeActivity = null;
        }

        @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
        public final void onTransitionProgress(float f) {
            StatusBarMoveFromCenterAnimationController.this.moveFromCenterAnimator.onTransitionProgress(f);
        }

        @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
        public final void onTransitionStarted() {
            StatusBarMoveFromCenterAnimationController statusBarMoveFromCenterAnimationController = StatusBarMoveFromCenterAnimationController.this;
            statusBarMoveFromCenterAnimationController.isOnHomeActivity = ((ActivityManagerActivityTypeProvider) statusBarMoveFromCenterAnimationController.currentActivityTypeProvider)._isHomeActivity;
        }
    }

    public StatusBarMoveFromCenterAnimationController(ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider, CurrentActivityTypeProvider currentActivityTypeProvider, WindowManager windowManager) {
        this.progressProvider = scopedUnfoldTransitionProgressProvider;
        this.currentActivityTypeProvider = currentActivityTypeProvider;
        this.moveFromCenterAnimator = new UnfoldMoveFromCenterAnimator(windowManager, null, new PhoneStatusBarViewController.StatusBarViewsCenterProvider(), new StatusBarIconsAlphaProvider(), 2, null);
    }
}
