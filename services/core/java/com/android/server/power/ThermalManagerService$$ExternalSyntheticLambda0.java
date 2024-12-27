package com.android.server.power;

import android.os.Binder;
import android.os.Temperature;

public final /* synthetic */ class ThermalManagerService$$ExternalSyntheticLambda0
        implements ThermalManagerService.ThermalHalWrapper.TemperatureChangedCallback {
    public final /* synthetic */ ThermalManagerService f$0;

    @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper.TemperatureChangedCallback
    public final void onValues(Temperature temperature) {
        ThermalManagerService thermalManagerService = this.f$0;
        thermalManagerService.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            thermalManagerService.onTemperatureChanged(temperature, true);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
