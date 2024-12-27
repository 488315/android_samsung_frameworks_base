package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import com.android.systemui.BasicRune;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PhoneStatusBarTransitions extends BarTransitions {
    public final View mBattery;
    public Animator mCurrentAnimation;
    public final float mIconAlphaWhenOpaque;
    public boolean mIsHeadsUp;
    public final View mNetspeedView;
    public final View mStartSide;
    public final View mStatusIcons;

    public PhoneStatusBarTransitions(PhoneStatusBarView phoneStatusBarView, View view) {
        super(view, R.drawable.status_background);
        this.mIconAlphaWhenOpaque = phoneStatusBarView.getContext().getResources().getFraction(R.dimen.status_bar_icon_drawing_alpha, 1, 1);
        this.mStartSide = phoneStatusBarView.findViewById(R.id.status_bar_start_side_except_heads_up);
        this.mStatusIcons = phoneStatusBarView.findViewById(R.id.statusIcons);
        this.mBattery = phoneStatusBarView.findViewById(R.id.battery);
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED) {
            this.mNetspeedView = phoneStatusBarView.findViewById(R.id.networkSpeed);
        }
        applyModeBackground(this.mMode, false);
        applyMode(this.mMode, false);
    }

    public final void applyMode(int i, boolean z) {
        View view;
        View view2;
        if (this.mStartSide == null) {
            return;
        }
        float iconAlphaBasedOnOpacity = this.mIsHeadsUp ? getIconAlphaBasedOnOpacity(i) : isLightsOut(i) ? 0.0f : getIconAlphaBasedOnOpacity(i);
        float iconAlphaBasedOnOpacity2 = isLightsOut(i) ? 0.0f : getIconAlphaBasedOnOpacity(i);
        float iconAlphaBasedOnOpacity3 = isLightsOut(i) ? 0.5f : getIconAlphaBasedOnOpacity(i);
        Animator animator = this.mCurrentAnimation;
        if (animator != null) {
            animator.cancel();
        }
        if (!z) {
            this.mStartSide.setAlpha(iconAlphaBasedOnOpacity);
            this.mStatusIcons.setAlpha(iconAlphaBasedOnOpacity2);
            this.mBattery.setAlpha(iconAlphaBasedOnOpacity3);
            if (!BasicRune.STATUS_REAL_TIME_NETWORK_SPEED || (view = this.mNetspeedView) == null) {
                return;
            }
            view.setAlpha(iconAlphaBasedOnOpacity3);
            return;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        View view3 = this.mStartSide;
        float[] fArr = {view3.getAlpha(), iconAlphaBasedOnOpacity};
        View view4 = this.mStatusIcons;
        float[] fArr2 = {view4.getAlpha(), iconAlphaBasedOnOpacity2};
        View view5 = this.mBattery;
        animatorSet.playTogether(ObjectAnimator.ofFloat(view3, "alpha", fArr), ObjectAnimator.ofFloat(view4, "alpha", fArr2), ObjectAnimator.ofFloat(view5, "alpha", view5.getAlpha(), iconAlphaBasedOnOpacity3));
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED && (view2 = this.mNetspeedView) != null) {
            animatorSet.playTogether(ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), iconAlphaBasedOnOpacity3));
        }
        if (isLightsOut(i)) {
            animatorSet.setDuration(1500L);
        }
        animatorSet.start();
        this.mCurrentAnimation = animatorSet;
    }

    public final float getIconAlphaBasedOnOpacity(int i) {
        if (i == 1 || i == 2 || i == 0 || i == 6) {
            return 1.0f;
        }
        return this.mIconAlphaWhenOpaque;
    }

    @Override // com.android.systemui.statusbar.phone.BarTransitions
    public final void onTransition(int i, int i2, boolean z) {
        applyModeBackground(i2, z);
        applyMode(i2, z);
    }
}
