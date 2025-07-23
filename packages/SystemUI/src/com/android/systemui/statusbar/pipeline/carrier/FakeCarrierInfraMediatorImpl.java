package com.android.systemui.statusbar.pipeline.carrier;

import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import kotlin.collections.ArraysKt___ArraysKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
