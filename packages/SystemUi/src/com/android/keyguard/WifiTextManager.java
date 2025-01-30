package com.android.keyguard;

import android.content.Context;
import com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractor;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
