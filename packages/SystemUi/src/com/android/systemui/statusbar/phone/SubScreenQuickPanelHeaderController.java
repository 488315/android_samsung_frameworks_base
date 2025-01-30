package com.android.systemui.statusbar.phone;

import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.statusbar.connectivity.ui.MobileContextProvider;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.pipeline.StatusBarPipelineFlags;
import com.android.systemui.statusbar.pipeline.mobile.ui.MobileUiAdapter;
import com.android.systemui.statusbar.pipeline.shared.ui.BTTetherUiAdapter;
import com.android.systemui.statusbar.pipeline.wifi.ui.WifiUiAdapter;
import com.android.systemui.statusbar.policy.NetspeedViewController;
import com.android.systemui.util.ViewController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SubScreenQuickPanelHeaderController extends ViewController {
    public final BatteryMeterViewController batteryMeterViewController;
    public final StatusIconContainer iconContainer;
    public final StatusBarIconController.TintedIconManager iconManager;
    public final NetspeedViewController netspeedViewController;
    public final StatusBarIconController statusBarIconController;

    public SubScreenQuickPanelHeaderController(SubScreenQuickPanelHeader subScreenQuickPanelHeader, BatteryMeterViewController batteryMeterViewController, StatusBarIconController statusBarIconController, NetspeedViewController netspeedViewController, StatusBarPipelineFlags statusBarPipelineFlags, WifiUiAdapter wifiUiAdapter, MobileUiAdapter mobileUiAdapter, MobileContextProvider mobileContextProvider, BTTetherUiAdapter bTTetherUiAdapter) {
        super(subScreenQuickPanelHeader);
        this.batteryMeterViewController = batteryMeterViewController;
        this.statusBarIconController = statusBarIconController;
        this.netspeedViewController = netspeedViewController;
        StatusIconContainer statusIconContainer = (StatusIconContainer) subScreenQuickPanelHeader.findViewById(R.id.statusIcons);
        this.iconContainer = statusIconContainer;
        this.iconManager = new StatusBarIconController.TintedIconManager(statusIconContainer, StatusBarLocation.SUB_SCREEN_QUICK_PANEL, statusBarPipelineFlags, wifiUiAdapter, mobileUiAdapter, mobileContextProvider, bTTetherUiAdapter);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
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
        StatusBarIconController.TintedIconManager tintedIconManager = this.iconManager;
        tintedIconManager.setTint(getContext().getColor(R.color.sub_screen_quick_panel_header_icon_color));
        tintedIconManager.mAdditionalScaleFactor = getResources().getFloat(R.dimen.b5_cover_system_icons_scale_factor);
        ((StatusBarIconControllerImpl) this.statusBarIconController).addIconGroup(tintedIconManager);
        this.iconContainer.mShouldRestrictIcons = false;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        ((StatusBarIconControllerImpl) this.statusBarIconController).removeIconGroup(this.iconManager);
    }
}
