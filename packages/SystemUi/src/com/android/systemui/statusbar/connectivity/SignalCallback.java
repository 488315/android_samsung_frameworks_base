package com.android.systemui.statusbar.connectivity;

import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
