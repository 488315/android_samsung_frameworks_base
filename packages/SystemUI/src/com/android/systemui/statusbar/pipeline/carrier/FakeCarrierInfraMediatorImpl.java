package com.android.systemui.statusbar.pipeline.carrier;

import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import kotlin.collections.ArraysKt___ArraysKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
