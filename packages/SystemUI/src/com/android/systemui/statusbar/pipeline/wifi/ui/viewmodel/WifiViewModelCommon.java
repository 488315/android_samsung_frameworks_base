package com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface WifiViewModelCommon {
    StateFlow getActivityIcon();

    Flow getDeXWifiIcon();

    StateFlow getUpdateDeXWifiIconModel();

    StateFlow getWifiIcon();
}
