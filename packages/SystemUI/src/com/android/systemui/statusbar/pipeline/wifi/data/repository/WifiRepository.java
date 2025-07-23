package com.android.systemui.statusbar.pipeline.wifi.data.repository;

import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface WifiRepository {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
