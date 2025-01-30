package com.android.systemui.statusbar.pipeline.wifi.data.repository;

import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface WifiRepository {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class DefaultImpls {
        public static boolean isWifiConnectedWithValidSsid(WifiRepository wifiRepository) {
            WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) wifiRepository.getWifiNetwork().getValue();
            if (!(wifiNetworkModel instanceof WifiNetworkModel.Active)) {
                return false;
            }
            String str = ((WifiNetworkModel.Active) wifiNetworkModel).ssid;
            return str != null && !Intrinsics.areEqual(str, "<unknown ssid>");
        }
    }

    StateFlow getHideDuringMobileSwitching();

    StateFlow getReceivedInetCondition();

    StateFlow getWifiActivity();

    StateFlow getWifiConnectivityTestReported();

    StateFlow getWifiNetwork();

    boolean isWifiConnectedWithValidSsid();

    StateFlow isWifiDefault();

    StateFlow isWifiEnabled();
}
