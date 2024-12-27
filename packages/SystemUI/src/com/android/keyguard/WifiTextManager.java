package com.android.keyguard;

import android.content.Context;
import com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractor;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
