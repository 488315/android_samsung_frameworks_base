package com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

public interface WifiViewModelCommon {
    StateFlow getActivityIcon();

    Flow getDeXWifiIcon();

    StateFlow getUpdateDeXWifiIconModel();

    StateFlow getWifiIcon();
}
