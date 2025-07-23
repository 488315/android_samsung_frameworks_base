package com.android.systemui.qs.tiles.dialog;

import android.net.wifi.WifiManager;
import android.util.Log;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
