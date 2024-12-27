package com.android.systemui.statusbar.pipeline.wifi.data.repository;

import kotlinx.coroutines.flow.StateFlow;

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
