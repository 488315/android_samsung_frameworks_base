package com.android.systemui.statusbar.connectivity;

import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
