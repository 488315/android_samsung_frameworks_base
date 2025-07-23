package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.shared.statusbar.phone.BarTransitions;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
        float f = 0.0f;
        float iconAlphaBasedOnOpacity = this.mIsHeadsUp ? getIconAlphaBasedOnOpacity(i) : (i == 3 || i == 6) ? 0.0f : getIconAlphaBasedOnOpacity(i);
        if (i != 3 && i != 6) {
            f = getIconAlphaBasedOnOpacity(i);
        }
        float iconAlphaBasedOnOpacity2 = (i == 3 || i == 6) ? 0.5f : getIconAlphaBasedOnOpacity(i);
        Animator animator = this.mCurrentAnimation;
        if (animator != null) {
            animator.cancel();
        }
        if (!z) {
            this.mStartSide.setAlpha(iconAlphaBasedOnOpacity);
            this.mStatusIcons.setAlpha(f);
            this.mBattery.setAlpha(iconAlphaBasedOnOpacity2);
            if (!BasicRune.STATUS_REAL_TIME_NETWORK_SPEED || (view = this.mNetspeedView) == null) {
                return;
            }
            view.setAlpha(iconAlphaBasedOnOpacity2);
            return;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        View view3 = this.mStartSide;
        float[] fArr = {view3.getAlpha(), iconAlphaBasedOnOpacity};
        View view4 = this.mStatusIcons;
        float[] fArr2 = {view4.getAlpha(), f};
        View view5 = this.mBattery;
        animatorSet.playTogether(ObjectAnimator.ofFloat(view3, "alpha", fArr), ObjectAnimator.ofFloat(view4, "alpha", fArr2), ObjectAnimator.ofFloat(view5, "alpha", view5.getAlpha(), iconAlphaBasedOnOpacity2));
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED && (view2 = this.mNetspeedView) != null) {
            animatorSet.playTogether(ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), iconAlphaBasedOnOpacity2));
        }
        if (i == 3 || i == 6) {
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

    @Override // com.android.systemui.shared.statusbar.phone.BarTransitions
    public final void onTransition(int i, int i2, boolean z) {
        applyModeBackground(i2, z);
        applyMode(i2, z);
    }
}
