package com.android.systemui.statusbar.pipeline.carrier;

import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import kotlin.collections.ArraysKt___ArraysKt;

public final class FakeCarrierInfraMediatorImpl implements CarrierInfraMediator {
    public final CarrierInfraMediatorImpl carrierInfraMediatorImpl;

    public FakeCarrierInfraMediatorImpl(CarrierInfraMediatorImpl carrierInfraMediatorImpl) {
        this.carrierInfraMediatorImpl = carrierInfraMediatorImpl;
    }

    @Override // com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator
    public final Object get(CarrierInfraMediator.Values values, int i, Object... objArr) {
        return this.carrierInfraMediatorImpl.get(values, i, ArraysKt___ArraysKt.getOrNull(0, objArr));
    }

    @Override // com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator
    public final boolean isEnabled(CarrierInfraMediator.Conditions conditions, int i, Object... objArr) {
        return this.carrierInfraMediatorImpl.isEnabled(conditions, i, objArr);
    }
}
