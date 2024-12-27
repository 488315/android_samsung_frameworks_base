package com.android.systemui.statusbar.connectivity;

import java.util.List;

public interface SignalCallback {
    default void setEthernetIndicators(IconState iconState) {
    }

    default void setIsAirplaneMode(IconState iconState) {
    }

    default void setMobileDataEnabled(boolean z) {
    }

    default void setMobileDataIndicators(MobileDataIndicators mobileDataIndicators) {
    }

    default void setSubs(List list) {
    }

    default void setWifiIndicators(WifiIndicators wifiIndicators) {
    }

    default void setNoSims(boolean z, boolean z2) {
    }

    default void setConnectivityStatus(boolean z, boolean z2, boolean z3) {
    }
}
