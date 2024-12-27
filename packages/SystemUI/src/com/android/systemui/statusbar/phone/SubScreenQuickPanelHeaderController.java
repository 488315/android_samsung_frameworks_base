package com.android.systemui.statusbar.phone;

import com.android.systemui.R;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.statusbar.connectivity.ui.MobileContextProvider;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl;
import com.android.systemui.statusbar.phone.ui.TintedIconManager;
import com.android.systemui.statusbar.pipeline.StatusBarPipelineFlags;
import com.android.systemui.statusbar.pipeline.mobile.ui.MobileUiAdapter;
import com.android.systemui.statusbar.pipeline.shared.ui.BTTetherUiAdapter;
import com.android.systemui.statusbar.pipeline.wifi.ui.WifiUiAdapter;
import com.android.systemui.util.ViewController;

public final class SubScreenQuickPanelHeaderController extends ViewController {
    public final BatteryMeterViewController batteryMeterViewController;
    public final StatusIconContainer iconContainer;
    public final TintedIconManager iconManager;
    public final StatusBarIconController statusBarIconController;

    public SubScreenQuickPanelHeaderController(SubScreenQuickPanelHeader subScreenQuickPanelHeader, BatteryMeterViewController batteryMeterViewController, StatusBarIconController statusBarIconController, StatusBarPipelineFlags statusBarPipelineFlags, WifiUiAdapter wifiUiAdapter, MobileUiAdapter mobileUiAdapter, MobileContextProvider mobileContextProvider, BTTetherUiAdapter bTTetherUiAdapter) {
        super(subScreenQuickPanelHeader);
        this.batteryMeterViewController = batteryMeterViewController;
        this.statusBarIconController = statusBarIconController;
        StatusIconContainer statusIconContainer = (StatusIconContainer) subScreenQuickPanelHeader.findViewById(R.id.statusIcons);
        this.iconContainer = statusIconContainer;
        this.iconManager = new TintedIconManager(statusIconContainer, StatusBarLocation.SUB_SCREEN_QUICK_PANEL, wifiUiAdapter, mobileUiAdapter, mobileContextProvider, bTTetherUiAdapter);
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
