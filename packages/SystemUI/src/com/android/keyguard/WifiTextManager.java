package com.android.keyguard;

import android.content.Context;
import com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractor;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
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
