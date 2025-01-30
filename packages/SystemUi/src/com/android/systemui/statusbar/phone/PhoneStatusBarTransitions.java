package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import com.android.systemui.BasicRune;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PhoneStatusBarTransitions extends BarTransitions {
    public final View mBattery;
    public Animator mCurrentAnimation;
    public final float mIconAlphaWhenOpaque;
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

    public static ObjectAnimator animateTransitionTo(View view, float f) {
        return ObjectAnimator.ofFloat(view, "alpha", view.getAlpha(), f);
    }

    public final void applyMode(int i, boolean z) {
        View view = this.mStartSide;
        if (view == null) {
            return;
        }
        float nonBatteryClockAlphaFor = getNonBatteryClockAlphaFor(i);
        float nonBatteryClockAlphaFor2 = i == 3 || i == 6 ? 0.5f : getNonBatteryClockAlphaFor(i);
        Animator animator = this.mCurrentAnimation;
        if (animator != null) {
            animator.cancel();
        }
        View view2 = this.mNetspeedView;
        View view3 = this.mBattery;
        View view4 = this.mStatusIcons;
        if (!z) {
            view.setAlpha(nonBatteryClockAlphaFor);
            view4.setAlpha(nonBatteryClockAlphaFor);
            view3.setAlpha(nonBatteryClockAlphaFor2);
            if (!BasicRune.STATUS_REAL_TIME_NETWORK_SPEED || view2 == null) {
                return;
            }
            view2.setAlpha(nonBatteryClockAlphaFor2);
            return;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animateTransitionTo(view, nonBatteryClockAlphaFor), animateTransitionTo(view4, nonBatteryClockAlphaFor), animateTransitionTo(view3, nonBatteryClockAlphaFor2));
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED && view2 != null) {
            animatorSet.playTogether(animateTransitionTo(view2, nonBatteryClockAlphaFor2));
        }
        if (i == 3 || i == 6) {
            animatorSet.setDuration(1500L);
        }
        animatorSet.start();
        this.mCurrentAnimation = animatorSet;
    }

    public final float getNonBatteryClockAlphaFor(int i) {
        boolean z = false;
        if (i == 3 || i == 6) {
            return 0.0f;
        }
        if (i != 1 && i != 2 && i != 0 && i != 6) {
            z = true;
        }
        if (z) {
            return this.mIconAlphaWhenOpaque;
        }
        return 1.0f;
    }

    @Override // com.android.systemui.statusbar.phone.BarTransitions
    public final void onTransition(int i, int i2, boolean z) {
        applyModeBackground(i2, z);
        applyMode(i2, z);
    }
}
