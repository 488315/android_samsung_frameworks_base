package com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel;

import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.pipeline.StatusBarPipelineFlags;

public final class SubScreenQsWifiViewModel extends LocationBasedWifiViewModel {
    public SubScreenQsWifiViewModel(WifiViewModelCommon wifiViewModelCommon, StatusBarPipelineFlags statusBarPipelineFlags, StatusBarLocation statusBarLocation) {
        super(wifiViewModelCommon, statusBarPipelineFlags, -16711936, statusBarLocation);
    }
}
