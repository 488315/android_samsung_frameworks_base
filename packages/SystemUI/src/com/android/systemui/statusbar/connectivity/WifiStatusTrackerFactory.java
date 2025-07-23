package com.android.systemui.statusbar.connectivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkScoreManager;
import android.net.wifi.WifiManager;
import android.os.Handler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WifiStatusTrackerFactory {
    public final ConnectivityManager mConnectivityManager;
    public final Context mContext;
    public final Handler mMainHandler;
    public final NetworkScoreManager mNetworkScoreManager;
    public final WifiManager mWifiManager;

    public WifiStatusTrackerFactory(Context context, WifiManager wifiManager, NetworkScoreManager networkScoreManager, ConnectivityManager connectivityManager, Handler handler) {
        this.mContext = context;
        this.mWifiManager = wifiManager;
        this.mNetworkScoreManager = networkScoreManager;
        this.mConnectivityManager = connectivityManager;
        this.mMainHandler = handler;
    }
}
