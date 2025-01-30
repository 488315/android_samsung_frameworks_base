package com.android.systemui.biometrics;

import android.animation.ValueAnimator;
import com.android.systemui.plugins.statusbar.StatusBarStateController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class UdfpsKeyguardViewControllerLegacy$stateListener$1 implements StatusBarStateController.StateListener {
    public final /* synthetic */ UdfpsKeyguardViewControllerLegacy this$0;

    public UdfpsKeyguardViewControllerLegacy$stateListener$1(UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy) {
        this.this$0 = udfpsKeyguardViewControllerLegacy;
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onDozeAmountChanged(float f, float f2) {
        UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy = this.this$0;
        if (udfpsKeyguardViewControllerLegacy.lastDozeAmount < f) {
            UdfpsKeyguardViewControllerLegacy.access$showUdfpsBouncer(udfpsKeyguardViewControllerLegacy, false);
        }
        ValueAnimator valueAnimator = udfpsKeyguardViewControllerLegacy.unlockedScreenOffDozeAnimator;
        valueAnimator.cancel();
        if (udfpsKeyguardViewControllerLegacy.unlockedScreenOffAnimationController.isAnimationPlaying()) {
            if (!(f == 0.0f)) {
                valueAnimator.start();
                udfpsKeyguardViewControllerLegacy.lastDozeAmount = f;
                udfpsKeyguardViewControllerLegacy.updatePauseAuth();
            }
        }
        UdfpsKeyguardViewLegacy udfpsKeyguardViewLegacy = udfpsKeyguardViewControllerLegacy.view;
        udfpsKeyguardViewLegacy.mAnimationType = 1;
        udfpsKeyguardViewLegacy.mInterpolatedDarkAmount = f2;
        udfpsKeyguardViewLegacy.updateAlpha();
        udfpsKeyguardViewControllerLegacy.lastDozeAmount = f;
        udfpsKeyguardViewControllerLegacy.updatePauseAuth();
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy = this.this$0;
        udfpsKeyguardViewControllerLegacy.statusBarState = i;
        udfpsKeyguardViewControllerLegacy.updateAlpha();
        udfpsKeyguardViewControllerLegacy.updatePauseAuth();
    }
}
