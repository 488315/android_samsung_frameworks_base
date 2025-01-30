package com.android.systemui.statusbar.phone;

import android.graphics.Rect;
import com.android.systemui.R;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.statusbar.disableflags.DisableStateTracker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardStatusBarViewController$$ExternalSyntheticLambda2 implements SidelingCutoutContainerInfo, DisableStateTracker.Callback {
    public final /* synthetic */ KeyguardStatusBarViewController f$0;

    public /* synthetic */ KeyguardStatusBarViewController$$ExternalSyntheticLambda2(KeyguardStatusBarViewController keyguardStatusBarViewController) {
        this.f$0 = keyguardStatusBarViewController;
    }

    @Override // com.android.systemui.statusbar.phone.SidelingCutoutContainerInfo
    public int getRightSideAvailableWidth(Rect rect) {
        KeyguardStatusBarViewController keyguardStatusBarViewController = this.f$0;
        BatteryMeterView batteryMeterView = (BatteryMeterView) ((KeyguardStatusBarView) keyguardStatusBarViewController.mView).findViewById(R.id.battery);
        int paddingEnd = ((KeyguardStatusBarView) keyguardStatusBarViewController.mView).findViewById(R.id.statusIcons).getPaddingEnd();
        int paddingRight = keyguardStatusBarViewController.getSidePaddingContainer().getPaddingRight();
        int width = keyguardStatusBarViewController.getResources().getConfiguration().windowConfiguration.getBounds().width();
        TwoPhoneModeIconController twoPhoneModeIconController = keyguardStatusBarViewController.mTwoPhoneModeController;
        return (width - (((batteryMeterView.getMeasuredWidth() + paddingRight) + paddingEnd) + ((!twoPhoneModeIconController.featureEnabled() || twoPhoneModeIconController.getViewWidth() <= 0) ? 0 : twoPhoneModeIconController.getViewWidth()))) - (keyguardStatusBarViewController.getResources().getDimensionPixelSize(R.dimen.indicator_marquee_max_shift) + rect.right);
    }
}
