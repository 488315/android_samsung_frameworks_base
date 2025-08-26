package com.android.systemui.statusbar.phone;

import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl;
import com.android.systemui.statusbar.phone.ui.TintedIconManager;
import com.android.systemui.statusbar.policy.NetspeedViewController;
import com.android.systemui.util.ViewController;

/* loaded from: classes3.dex */
public final class SubScreenQuickPanelHeaderController extends ViewController {
    public final BatteryMeterViewController batteryMeterViewController;
    public final StatusIconContainer iconContainer;
    public final TintedIconManager iconManager;
    public final SubScreenQuickPanelHeader mSubScreenQuickPanelHeader;
    public final NetspeedViewController netspeedViewController;
    public final StatusBarIconController statusBarIconController;

    public SubScreenQuickPanelHeaderController(SubScreenQuickPanelHeader subScreenQuickPanelHeader, BatteryMeterViewController batteryMeterViewController, StatusBarIconController statusBarIconController, NetspeedViewController netspeedViewController, TintedIconManager.Factory factory) {
        super(subScreenQuickPanelHeader);
        this.batteryMeterViewController = batteryMeterViewController;
        this.statusBarIconController = statusBarIconController;
        this.netspeedViewController = netspeedViewController;
        StatusIconContainer statusIconContainer = (StatusIconContainer) subScreenQuickPanelHeader.findViewById(R.id.statusIcons);
        this.iconContainer = statusIconContainer;
        this.iconManager = factory.create(statusIconContainer, StatusBarLocation.SUB_SCREEN_QUICK_PANEL);
        this.mSubScreenQuickPanelHeader = subScreenQuickPanelHeader;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        super.onInit();
        BatteryMeterViewController batteryMeterViewController = this.batteryMeterViewController;
        batteryMeterViewController.init();
        batteryMeterViewController.mIgnoreTunerUpdates = true;
        if (batteryMeterViewController.mIsSubscribedForTunerUpdates) {
            batteryMeterViewController.mTunerService.removeTunable(batteryMeterViewController.mTunable);
            batteryMeterViewController.mIsSubscribedForTunerUpdates = false;
        }
        batteryMeterViewController.mAdditionalScaleFactorForSpecificBatteryView = getResources().getFloat(R.dimen.b5_cover_battery_scale_factor);
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED) {
            NetspeedViewController netspeedViewController = this.netspeedViewController;
            if (netspeedViewController != null) {
                netspeedViewController.init();
            }
            if (netspeedViewController != null) {
                netspeedViewController.mFixedScaleFactorForSpecificNetspeedView = getResources().getFloat(R.dimen.b5_cover_battery_scale_factor);
            }
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        TintedIconManager tintedIconManager = this.iconManager;
        tintedIconManager.setTint(getContext().getColor(R.color.sub_screen_quick_panel_header_icon_color), -1);
        tintedIconManager.mAdditionalScaleFactor = getResources().getFloat(R.dimen.b5_cover_system_icons_scale_factor);
        ((StatusBarIconControllerImpl) this.statusBarIconController).addIconGroup(tintedIconManager);
        StatusIconContainer statusIconContainer = this.iconContainer;
        if (statusIconContainer != null) {
            statusIconContainer.mShouldRestrictIcons = false;
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        ((StatusBarIconControllerImpl) this.statusBarIconController).removeIconGroup(this.iconManager);
    }
}
