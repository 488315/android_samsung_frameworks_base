package com.android.systemui.statusbar;

import android.animation.ValueAnimator;
import android.view.animation.Interpolator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardSecAffordanceView$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardSecAffordanceView f$0;

    public /* synthetic */ KeyguardSecAffordanceView$$ExternalSyntheticLambda0(KeyguardSecAffordanceView keyguardSecAffordanceView, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardSecAffordanceView;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        switch (this.$r8$classId) {
            case 0:
                KeyguardSecAffordanceView keyguardSecAffordanceView = this.f$0;
                keyguardSecAffordanceView.mRectangleIconAlpha = (int) ((1.0f - (keyguardSecAffordanceView.mRectangleDistanceCovered / keyguardSecAffordanceView.mScreenWidth)) * 255.0f);
                keyguardSecAffordanceView.mRectanglePaint.setAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
                keyguardSecAffordanceView.invalidate();
                break;
            case 1:
                KeyguardSecAffordanceView keyguardSecAffordanceView2 = this.f$0;
                Interpolator interpolator = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                keyguardSecAffordanceView2.getClass();
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                if (keyguardSecAffordanceView2.mIsDown) {
                    KeyguardSecAffordanceView.cancelAnimator(keyguardSecAffordanceView2.mRectangleShrinkAnimator);
                }
                keyguardSecAffordanceView2.updatePanelViews(floatValue);
                keyguardSecAffordanceView2.invalidate();
                break;
            case 2:
                KeyguardSecAffordanceView keyguardSecAffordanceView3 = this.f$0;
                Interpolator interpolator2 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                keyguardSecAffordanceView3.getClass();
                keyguardSecAffordanceView3.mImageScale = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                keyguardSecAffordanceView3.invalidate();
                break;
            case 3:
                KeyguardSecAffordanceView keyguardSecAffordanceView4 = this.f$0;
                Interpolator interpolator3 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                keyguardSecAffordanceView4.getClass();
                keyguardSecAffordanceView4.mVerticalScale = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                break;
            case 4:
                KeyguardSecAffordanceView keyguardSecAffordanceView5 = this.f$0;
                Interpolator interpolator4 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                keyguardSecAffordanceView5.getClass();
                keyguardSecAffordanceView5.mRectangleIconScale = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                break;
            case 5:
                this.f$0.mRectanglePaint.setAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
                break;
            case 6:
                KeyguardSecAffordanceView keyguardSecAffordanceView6 = this.f$0;
                Interpolator interpolator5 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                keyguardSecAffordanceView6.getClass();
                float floatValue2 = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                keyguardSecAffordanceView6.setRectangleBounds(floatValue2);
                keyguardSecAffordanceView6.updateRectangleCornerRadius(floatValue2);
                break;
            case 7:
                KeyguardSecAffordanceView keyguardSecAffordanceView7 = this.f$0;
                Interpolator interpolator6 = KeyguardSecAffordanceView.SCALE_INTERPOLATOR;
                keyguardSecAffordanceView7.getClass();
                keyguardSecAffordanceView7.setRectangleBounds(((Float) valueAnimator.getAnimatedValue()).floatValue());
                keyguardSecAffordanceView7.invalidate();
                break;
            default:
                KeyguardSecAffordanceView keyguardSecAffordanceView8 = this.f$0;
                keyguardSecAffordanceView8.mRectanglePaint.setAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
                keyguardSecAffordanceView8.invalidate();
                break;
        }
    }
}
