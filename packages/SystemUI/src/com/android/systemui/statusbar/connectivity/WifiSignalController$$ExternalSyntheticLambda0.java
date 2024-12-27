package com.android.systemui.statusbar.connectivity;

import android.net.NetworkInfo;
import android.net.NetworkKey;
import android.net.wifi.WifiInfo;
import com.android.settingslib.wifi.WifiStatusTracker;

public final /* synthetic */ class WifiSignalController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiSignalController f$0;

    public /* synthetic */ WifiSignalController$$ExternalSyntheticLambda0(WifiSignalController wifiSignalController, int i) {
        this.$r8$classId = i;
        this.f$0 = wifiSignalController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        WifiSignalController wifiSignalController = this.f$0;
        switch (i) {
            case 0:
                wifiSignalController.getClass();
                wifiSignalController.doInBackground(new WifiSignalController$$ExternalSyntheticLambda0(wifiSignalController, 1));
                break;
            case 1:
                wifiSignalController.copyWifiStates();
                wifiSignalController.notifyListenersIfNecessary();
                break;
            default:
                WifiStatusTracker wifiStatusTracker = wifiSignalController.mWifiTracker;
                if (wifiStatusTracker.mWifiManager != null) {
                    wifiStatusTracker.updateWifiState();
                    NetworkInfo networkInfo = wifiStatusTracker.mConnectivityManager.getNetworkInfo(1);
                    boolean z = networkInfo != null && networkInfo.isConnected();
                    wifiStatusTracker.connected = z;
                    String str = null;
                    wifiStatusTracker.mWifiInfo = null;
                    wifiStatusTracker.ssid = null;
                    if (z) {
                        WifiInfo connectionInfo = wifiStatusTracker.mWifiManager.getConnectionInfo();
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
                        }
                    }
                    wifiStatusTracker.updateStatusLabel();
                }
                wifiSignalController.copyWifiStates();
                wifiSignalController.notifyListenersIfNecessary();
                break;
        }
    }
}
