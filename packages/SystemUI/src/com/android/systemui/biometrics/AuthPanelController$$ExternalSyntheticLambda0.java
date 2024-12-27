package com.android.systemui.biometrics;

import android.animation.ValueAnimator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class AuthPanelController$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AuthPanelController f$0;

    public /* synthetic */ AuthPanelController$$ExternalSyntheticLambda0(AuthPanelController authPanelController, int i) {
        this.$r8$classId = i;
        this.f$0 = authPanelController;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        int i = this.$r8$classId;
        AuthPanelController authPanelController = this.f$0;
        authPanelController.getClass();
        switch (i) {
            case 0:
                authPanelController.mMargin = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                break;
            case 1:
                authPanelController.mCornerRadius = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                break;
            case 2:
                authPanelController.mContentHeight = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                authPanelController.mPanelView.invalidateOutline();
                break;
            default:
                authPanelController.mContentWidth = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                break;
        }
    }
}
