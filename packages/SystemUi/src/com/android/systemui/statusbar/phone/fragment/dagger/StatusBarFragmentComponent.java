package com.android.systemui.statusbar.phone.fragment.dagger;

import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController;
import com.android.systemui.statusbar.phone.LightsOutNotifController;
import com.android.systemui.statusbar.phone.PhoneStatusBarTransitions;
import com.android.systemui.statusbar.phone.PhoneStatusBarView;
import com.android.systemui.statusbar.phone.PhoneStatusBarViewController;
import com.android.systemui.statusbar.phone.StatusBarBoundsProvider;
import com.android.systemui.statusbar.phone.StatusBarDemoMode;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment;
import com.android.systemui.statusbar.policy.QSClockIndicatorViewController;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface StatusBarFragmentComponent {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Factory {
        StatusBarFragmentComponent create(CollapsedStatusBarFragment collapsedStatusBarFragment);
    }

    BatteryMeterViewController getBatteryMeterViewController();

    StatusBarBoundsProvider getBoundsProvider();

    HeadsUpAppearanceController getHeadsUpAppearanceController();

    LightsOutNotifController getLightsOutNotifController();

    PhoneStatusBarTransitions getPhoneStatusBarTransitions();

    PhoneStatusBarView getPhoneStatusBarView();

    PhoneStatusBarViewController getPhoneStatusBarViewController();

    QSClockIndicatorViewController getQSClockIndicatorViewController();

    Set getStartables();

    StatusBarDemoMode getStatusBarDemoMode();

    default void init() {
        getBatteryMeterViewController().init();
        getHeadsUpAppearanceController().init();
        getPhoneStatusBarViewController().init();
        getLightsOutNotifController().init();
        getStatusBarDemoMode().init();
        getQSClockIndicatorViewController().init();
    }
}
