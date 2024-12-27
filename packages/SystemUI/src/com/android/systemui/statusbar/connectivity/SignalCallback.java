package com.android.systemui.statusbar.connectivity;

import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
