package com.android.systemui.statusbar;

import android.animation.ValueAnimator;
import android.graphics.drawable.PaintDrawable;
import android.view.animation.Interpolator;

public final /* synthetic */ class KeyguardSecAffordanceView$$ExternalSyntheticLambda2 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardSecAffordanceView f$0;

    public /* synthetic */ KeyguardSecAffordanceView$$ExternalSyntheticLambda2(KeyguardSecAffordanceView keyguardSecAffordanceView, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardSecAffordanceView;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        PaintDrawable paintDrawable;
        int i = this.$r8$classId;
        KeyguardSecAffordanceView keyguardSecAffordanceView = this.f$0;
        switch (i) {
            case 0:
                Interpolator interpolator = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                keyguardSecAffordanceView.getClass();
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                keyguardSecAffordanceView.setRectangleBounds(floatValue);
                if (floatValue >= keyguardSecAffordanceView.mScreenWidth && (paintDrawable = keyguardSecAffordanceView.mPanelBackgroundDrawable) != null) {
                    paintDrawable.setCornerRadius(0.0f);
                    break;
                }
                break;
            case 1:
                Interpolator interpolator2 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                keyguardSecAffordanceView.getClass();
                keyguardSecAffordanceView.mImageScale = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                keyguardSecAffordanceView.invalidate();
                break;
            case 2:
                Interpolator interpolator3 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                keyguardSecAffordanceView.getClass();
                keyguardSecAffordanceView.mRectangleIconScale = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                break;
            case 3:
                Interpolator interpolator4 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                keyguardSecAffordanceView.getClass();
                keyguardSecAffordanceView.setRectangleBounds(((Float) valueAnimator.getAnimatedValue()).floatValue());
                keyguardSecAffordanceView.invalidate();
                break;
            case 4:
                Interpolator interpolator5 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                keyguardSecAffordanceView.getClass();
                keyguardSecAffordanceView.mVerticalScale = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                break;
            case 5:
                keyguardSecAffordanceView.mRectanglePaint.setAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
                break;
            case 6:
                Interpolator interpolator6 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                keyguardSecAffordanceView.getClass();
                float floatValue2 = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                if (keyguardSecAffordanceView.mIsDown) {
                    KeyguardSecAffordanceView.cancelAnimator(keyguardSecAffordanceView.mRectangleShrinkAnimator);
                }
                keyguardSecAffordanceView.updatePanelViews(floatValue2);
                keyguardSecAffordanceView.invalidate();
                break;
            default:
                keyguardSecAffordanceView.mRectangleIconAlpha = (int) ((1.0f - (keyguardSecAffordanceView.mRectangleDistanceCovered / keyguardSecAffordanceView.mScreenWidth)) * 255.0f);
                keyguardSecAffordanceView.invalidate();
                break;
        }
    }
}
