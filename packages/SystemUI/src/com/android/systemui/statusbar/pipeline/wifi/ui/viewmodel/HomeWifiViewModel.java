package com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel;

import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.pipeline.StatusBarPipelineFlags;

public final class HomeWifiViewModel extends LocationBasedWifiViewModel {
    public HomeWifiViewModel(WifiViewModelCommon wifiViewModelCommon, StatusBarPipelineFlags statusBarPipelineFlags, StatusBarLocation statusBarLocation) {
        super(wifiViewModelCommon, statusBarPipelineFlags, -16711681, statusBarLocation);
    }
}
