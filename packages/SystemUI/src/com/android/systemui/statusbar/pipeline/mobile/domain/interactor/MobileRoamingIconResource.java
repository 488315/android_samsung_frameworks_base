package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;

public final class MobileRoamingIconResource {
    public final CarrierInfraMediator carrierInfraMediator;

    public MobileRoamingIconResource(CarrierInfraMediator carrierInfraMediator) {
        this.carrierInfraMediator = carrierInfraMediator;
    }
}
