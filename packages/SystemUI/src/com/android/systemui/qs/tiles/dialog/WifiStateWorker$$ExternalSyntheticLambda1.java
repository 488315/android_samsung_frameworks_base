package com.android.systemui.qs.tiles.dialog;

import android.net.wifi.WifiManager;
import android.util.Log;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class WifiStateWorker$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ WifiStateWorker f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ WifiStateWorker$$ExternalSyntheticLambda1(WifiStateWorker wifiStateWorker, boolean z) {
        this.f$0 = wifiStateWorker;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        WifiStateWorker wifiStateWorker = this.f$0;
        boolean z = this.f$1;
        WifiManager wifiManager = wifiStateWorker.mWifiManager;
        if (wifiManager == null) {
            return;
        }
        wifiStateWorker.mWifiState = z ? 2 : 0;
        if (wifiManager.setWifiEnabled(z)) {
            return;
        }
        Log.e("WifiStateWorker", "Failed to WifiManager.setWifiEnabled(" + z + ");");
    }
}
