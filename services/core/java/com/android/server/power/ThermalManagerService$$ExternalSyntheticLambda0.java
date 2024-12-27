package com.android.server.power;

import android.os.Binder;
import android.os.Temperature;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
