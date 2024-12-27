package com.android.systemui.statusbar.pipeline.wifi.data.repository;

import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface WifiRepository {
    StateFlow getHideDuringMobileSwitching();

    StateFlow getReceivedInetCondition();

    StateFlow getSecondaryNetworks();

    StateFlow getWifiActivity();

    StateFlow getWifiConnectivityTestReported();

    StateFlow getWifiNetwork();

    StateFlow getWifiScanResults();

    StateFlow isWifiDefault();

    StateFlow isWifiEnabled();
}
