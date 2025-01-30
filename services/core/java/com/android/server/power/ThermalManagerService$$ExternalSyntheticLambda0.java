package com.android.server.power;

import android.os.Temperature;
import com.android.server.power.ThermalManagerService;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes3.dex */
public final /* synthetic */ class ThermalManagerService$$ExternalSyntheticLambda0 implements ThermalManagerService.ThermalHalWrapper.TemperatureChangedCallback {
    public final /* synthetic */ ThermalManagerService f$0;

    public /* synthetic */ ThermalManagerService$$ExternalSyntheticLambda0(ThermalManagerService thermalManagerService) {
        this.f$0 = thermalManagerService;
    }

    @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper.TemperatureChangedCallback
    public final void onValues(Temperature temperature) {
        this.f$0.onTemperatureChangedCallback(temperature);
    }
}
