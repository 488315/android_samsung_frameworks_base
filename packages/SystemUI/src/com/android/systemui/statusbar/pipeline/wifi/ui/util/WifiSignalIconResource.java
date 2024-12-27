package com.android.systemui.statusbar.pipeline.wifi.ui.util;

import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;

public final class WifiSignalIconResource {
    public final CarrierInfraMediator carrierInfraMediator;

    public WifiSignalIconResource(CarrierInfraMediator carrierInfraMediator) {
        this.carrierInfraMediator = carrierInfraMediator;
    }
}
