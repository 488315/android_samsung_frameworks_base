package com.android.keyguard;

import android.content.Context;
import com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractor;
import kotlinx.coroutines.CoroutineScope;

public final class WifiTextManager {
    public boolean connected;
    public final Context context;
    public final CoroutineScope scope;
    public String ssid;
    public final WifiInteractor wifiInteractor;

    public WifiTextManager(Context context, CoroutineScope coroutineScope, WifiInteractor wifiInteractor) {
        this.context = context;
        this.scope = coroutineScope;
        this.wifiInteractor = wifiInteractor;
    }
}
