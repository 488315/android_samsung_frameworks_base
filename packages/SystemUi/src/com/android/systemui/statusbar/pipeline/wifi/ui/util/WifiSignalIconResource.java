package com.android.systemui.statusbar.pipeline.wifi.ui.util;

import android.net.wifi.WifiManager;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WifiSignalIconResource {
    public final CarrierInfraMediator carrierInfraMediator;
    public final WifiManager wifiManager;

    public WifiSignalIconResource(CarrierInfraMediator carrierInfraMediator, WifiManager wifiManager) {
        this.carrierInfraMediator = carrierInfraMediator;
        this.wifiManager = wifiManager;
    }
}
