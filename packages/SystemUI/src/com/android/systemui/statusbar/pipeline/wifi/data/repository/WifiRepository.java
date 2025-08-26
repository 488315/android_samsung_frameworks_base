package com.android.systemui.statusbar.pipeline.wifi.data.repository;

import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes3.dex */
public interface WifiRepository {

    public final class Companion {
        public static final /* synthetic */ int $r8$clinit = 0;

        static {
            new Companion();
        }

        private Companion() {
        }
    }

    static {
        int i = Companion.$r8$clinit;
    }

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
