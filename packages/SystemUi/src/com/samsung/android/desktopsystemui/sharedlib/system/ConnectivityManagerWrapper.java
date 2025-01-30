package com.samsung.android.desktopsystemui.sharedlib.system;

import android.app.AppGlobals;
import android.net.ConnectivityManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ConnectivityManagerWrapper {
    private static final ConnectivityManagerWrapper sInstance = new ConnectivityManagerWrapper();
    private static final ConnectivityManager mConnectivityManager = (ConnectivityManager) AppGlobals.getInitialApplication().getSystemService("connectivity");

    private ConnectivityManagerWrapper() {
    }

    public static ConnectivityManagerWrapper getInstance() {
        return sInstance;
    }

    public void setAirplaneMode(boolean z) {
        mConnectivityManager.setAirplaneMode(z);
    }
}
