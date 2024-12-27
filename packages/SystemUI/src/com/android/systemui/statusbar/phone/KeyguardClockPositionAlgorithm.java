package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Resources;
import android.util.MathUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.PathInterpolator;
import com.android.app.animation.Interpolators;
import com.android.keyguard.BouncerPanelExpansionCalculator;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.doze.util.BurnInHelperKt;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.shade.LargeScreenHeaderHelper;
import com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.NotificationUtils;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class KeyguardClockPositionAlgorithm {
    public boolean mBypassEnabled;
    public float mClockBottom;
    public int mContainerTopPadding;
    public float mCurrentBurnInOffsetY;
    public int mCutoutTopInset = 0;
    public float mDarkAmount;
    public boolean mIsClockTopAligned;
    public boolean mIsSplitShade;
    public int mKeyguardStatusHeight;
    public int mMaxBurnInPreventionOffsetX;
    public int mMaxBurnInPreventionOffsetYClock;
    public int mMinTopMargin;
    public float mOverStretchAmount;
    public float mPanelExpansion;
    public float mQsExpansion;
    public int mSplitShadeTargetTopMargin;
    public int mSplitShadeTopNotificationsMargin;
    public int mStatusViewBottomMargin;
    public float mUdfpsTop;
    public int mUnlockedStackScrollerPadding;
    public int mUserSwitchHeight;
    public int mUserSwitchPreferredY;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Result {
        public float clockAlpha;
        public float clockScale;
        public int clockX;
        public int clockY;
        public int clockYFullyDozing;
        public List contentsContainerPosition;
        public int stackScrollerPadding;
        public int stackScrollerPaddingExpanded;
        public int userSwitchY;
    }

    public KeyguardClockPositionAlgorithm(LogBuffer logBuffer) {
        new Logger(logBuffer, "KeyguardClockPositionAlgorithm");
    }

    public int getBottomMarginY() {
        return 0;
    }

    public final int getClockY(float f, float f2) {
        float lerp = MathUtils.lerp((-this.mKeyguardStatusHeight) / 3.0f, this.mIsSplitShade ? this.mSplitShadeTargetTopMargin : this.mMinTopMargin, ((PathInterpolator) Interpolators.FAST_OUT_LINEAR_IN).getInterpolation(f));
        int i = this.mMaxBurnInPreventionOffsetYClock;
        float f3 = lerp - i;
        float f4 = this.mCutoutTopInset;
        float f5 = f3 < f4 ? f4 - f3 : 0.0f;
        float f6 = this.mUdfpsTop;
        if (f6 > -1.0f && !this.mIsClockTopAligned) {
            float f7 = this.mClockBottom;
            if (f6 < f7) {
                int i2 = ((int) (lerp - f4)) / 2;
                if (i >= i2) {
                    i = i2;
                }
                f5 = -i;
            } else {
                float f8 = lerp - f4;
                float f9 = f6 - f7;
                int i3 = ((int) (f9 + f8)) / 2;
                if (i >= i3) {
                    i = i3;
                }
                f5 = (f9 - f8) / 2.0f;
            }
        }
        float burnInOffset = BurnInHelperKt.getBurnInOffset(i * 2, false) - i;
        float f10 = lerp + burnInOffset + f5;
        this.mCurrentBurnInOffsetY = MathUtils.lerp(0.0f, burnInOffset, f2);
        return (int) (MathUtils.lerp(lerp, f10, f2) + this.mOverStretchAmount);
    }

    public float getLockscreenNotifPadding(float f) {
        return this.mBypassEnabled ? this.mUnlockedStackScrollerPadding - f : this.mIsSplitShade ? (this.mSplitShadeTargetTopMargin + this.mUserSwitchHeight) - f : this.mMinTopMargin + this.mKeyguardStatusHeight;
    }

    public boolean isPanelExpanded() {
        return false;
    }

    public void loadDimens(Context context, Resources resources) {
        this.mStatusViewBottomMargin = resources.getDimensionPixelSize(R.dimen.keyguard_status_view_bottom_margin);
        Flags.FEATURE_FLAGS.getClass();
        LargeScreenHeaderHelper.Companion.getClass();
        this.mSplitShadeTopNotificationsMargin = LargeScreenHeaderHelper.Companion.getLargeScreenHeaderHeight(context);
        this.mSplitShadeTargetTopMargin = resources.getDimensionPixelSize(R.dimen.keyguard_split_shade_top_margin);
        this.mContainerTopPadding = resources.getDimensionPixelSize(R.dimen.keyguard_clock_top_margin);
        this.mMaxBurnInPreventionOffsetX = resources.getDimensionPixelSize(R.dimen.burn_in_prevention_offset_x);
        this.mMaxBurnInPreventionOffsetYClock = resources.getDimensionPixelSize(R.dimen.burn_in_prevention_offset_y_clock);
    }

    public void run(Result result) {
        int clockY;
        int i;
        int i2;
        int clockY2 = getClockY(this.mPanelExpansion, this.mDarkAmount);
        result.clockY = clockY2;
        result.userSwitchY = (int) (MathUtils.lerp((-this.mKeyguardStatusHeight) - this.mUserSwitchHeight, this.mUserSwitchPreferredY, ((PathInterpolator) Interpolators.FAST_OUT_LINEAR_IN).getInterpolation(this.mPanelExpansion)) + this.mOverStretchAmount);
        result.clockYFullyDozing = getClockY(1.0f, 1.0f);
        float max = Math.max(0.0f, clockY2 / Math.max(1.0f, getClockY(1.0f, this.mDarkAmount)));
        if (!this.mIsSplitShade) {
            max *= 1.0f - MathUtils.saturate(this.mQsExpansion / 0.3f);
        }
        result.clockAlpha = MathUtils.lerp(((AccelerateInterpolator) Interpolators.ACCELERATE).getInterpolation(max), 1.0f, this.mDarkAmount);
        boolean z = this.mBypassEnabled;
        result.stackScrollerPadding = z ? (int) (this.mUnlockedStackScrollerPadding + this.mOverStretchAmount) : this.mIsSplitShade ? ((clockY2 - this.mSplitShadeTopNotificationsMargin) + this.mUserSwitchHeight) - ((int) this.mCurrentBurnInOffsetY) : clockY2 + this.mKeyguardStatusHeight;
        if (z) {
            i2 = this.mUnlockedStackScrollerPadding;
        } else {
            if (this.mIsSplitShade) {
                clockY = getClockY(1.0f, this.mDarkAmount);
                i = this.mUserSwitchHeight;
            } else {
                clockY = getClockY(1.0f, this.mDarkAmount);
                i = this.mKeyguardStatusHeight;
            }
            i2 = clockY + i;
        }
        result.stackScrollerPaddingExpanded = i2;
        result.clockX = (int) NotificationUtils.interpolate(0.0f, BurnInHelperKt.getBurnInOffset(this.mMaxBurnInPreventionOffsetX, true), this.mDarkAmount);
        result.clockScale = NotificationUtils.interpolate(BurnInHelperKt.zigzag(System.currentTimeMillis() / 60000.0f, 0.2f, 181.0f) + 0.8f, 1.0f, 1.0f - this.mDarkAmount);
    }

    public void setup(int i, float f, int i2, int i3, int i4, float f2, int i5, int i6, int i7, NotificationPanelViewController$$ExternalSyntheticLambda0 notificationPanelViewController$$ExternalSyntheticLambda0) {
        this.mMinTopMargin = Math.max(this.mContainerTopPadding, i3) + i;
        int i8 = BouncerPanelExpansionCalculator.$r8$clinit;
        this.mPanelExpansion = MathUtils.constrain((f - 0.7f) / 0.3f, 0.0f, 1.0f);
        this.mKeyguardStatusHeight = i2 + this.mStatusViewBottomMargin;
        this.mUserSwitchHeight = i3;
        this.mUserSwitchPreferredY = i4;
        this.mDarkAmount = f2;
        this.mOverStretchAmount = 0.0f;
        this.mBypassEnabled = false;
        this.mUnlockedStackScrollerPadding = i5;
        this.mQsExpansion = 0.0f;
        this.mCutoutTopInset = 0;
        this.mIsSplitShade = false;
        this.mUdfpsTop = 0.0f;
        this.mClockBottom = 0.0f;
        this.mIsClockTopAligned = false;
    }
}
