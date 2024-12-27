package com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel;

import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.pipeline.StatusBarPipelineFlags;

public final class KeyguardWifiViewModel extends LocationBasedWifiViewModel {
    public KeyguardWifiViewModel(WifiViewModelCommon wifiViewModelCommon, StatusBarPipelineFlags statusBarPipelineFlags, StatusBarLocation statusBarLocation) {
        super(wifiViewModelCommon, statusBarPipelineFlags, -65281, statusBarLocation);
    }
}
