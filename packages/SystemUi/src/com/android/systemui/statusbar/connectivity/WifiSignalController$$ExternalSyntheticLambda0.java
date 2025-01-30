package com.android.systemui.statusbar.connectivity;

import android.net.NetworkInfo;
import android.net.NetworkKey;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import com.android.settingslib.wifi.WifiStatusTracker;
import com.samsung.android.wifi.SemOpBrandingLoader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiSignalController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiSignalController f$0;

    public /* synthetic */ WifiSignalController$$ExternalSyntheticLambda0(WifiSignalController wifiSignalController, int i) {
        this.$r8$classId = i;
        this.f$0 = wifiSignalController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        char c = 1;
        switch (this.$r8$classId) {
            case 0:
                WifiSignalController wifiSignalController = this.f$0;
                wifiSignalController.getClass();
                wifiSignalController.doInBackground(new WifiSignalController$$ExternalSyntheticLambda0(wifiSignalController, c == true ? 1 : 0));
                break;
            case 1:
                WifiSignalController wifiSignalController2 = this.f$0;
                wifiSignalController2.copyWifiStates();
                wifiSignalController2.notifyListenersIfNecessary();
                break;
            default:
                WifiSignalController wifiSignalController3 = this.f$0;
                WifiStatusTracker wifiStatusTracker = wifiSignalController3.mWifiTracker;
                WifiManager wifiManager = wifiStatusTracker.mWifiManager;
                if (wifiManager != null) {
                    wifiStatusTracker.updateWifiState();
                    NetworkInfo networkInfo = wifiStatusTracker.mConnectivityManager.getNetworkInfo(1);
                    boolean z = networkInfo != null && networkInfo.isConnected();
                    wifiStatusTracker.connected = z;
                    String str = null;
                    wifiStatusTracker.mWifiInfo = null;
                    wifiStatusTracker.ssid = null;
                    if (z) {
                        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                        wifiStatusTracker.mWifiInfo = connectionInfo;
                        if (connectionInfo != null) {
                            if (connectionInfo.isPasspointAp() || wifiStatusTracker.mWifiInfo.isOsuAp()) {
                                wifiStatusTracker.ssid = wifiStatusTracker.mWifiInfo.getPasspointProviderFriendlyName();
                            } else {
                                String ssid = wifiStatusTracker.mWifiInfo.getSSID();
                                if (ssid != null && !"<unknown ssid>".equals(ssid)) {
                                    str = ssid;
                                }
                                wifiStatusTracker.ssid = str;
                            }
                            wifiStatusTracker.isCarrierMerged = wifiStatusTracker.mWifiInfo.isCarrierMerged();
                            wifiStatusTracker.subId = wifiStatusTracker.mWifiInfo.getSubscriptionId();
                            wifiStatusTracker.updateRssi(wifiStatusTracker.mWifiInfo.getRssi());
                            NetworkKey createFromWifiInfo = NetworkKey.createFromWifiInfo(wifiStatusTracker.mWifiInfo);
                            if (wifiStatusTracker.mWifiNetworkScoreCache.getScoredNetwork(createFromWifiInfo) == null) {
                                wifiStatusTracker.mNetworkScoreManager.requestScores(new NetworkKey[]{createFromWifiInfo});
                            }
                            if (SemOpBrandingLoader.SemVendor.KTT == SemOpBrandingLoader.getInstance().getOpBranding()) {
                                WifiStatusTracker.updateCarrierWifi(wifiStatusTracker.mWifiInfo);
                            }
                        }
                    }
                    wifiStatusTracker.updateStatusLabel();
                }
                wifiSignalController3.copyWifiStates();
                wifiSignalController3.notifyListenersIfNecessary();
                break;
        }
    }
}
