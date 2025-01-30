package com.android.systemui.statusbar.pipeline.carrier;

import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import kotlin.collections.ArraysKt___ArraysKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FakeCarrierInfraMediatorImpl implements CarrierInfraMediator {
    public final CarrierInfraMediatorImpl carrierInfraMediatorImpl;

    public FakeCarrierInfraMediatorImpl(CarrierInfraMediatorImpl carrierInfraMediatorImpl) {
        this.carrierInfraMediatorImpl = carrierInfraMediatorImpl;
    }

    @Override // com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator
    public final Object get(CarrierInfraMediator.Values values, int i, Object... objArr) {
        return this.carrierInfraMediatorImpl.get(values, i, ArraysKt___ArraysKt.getOrNull(objArr));
    }

    @Override // com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator
    public final boolean isEnabled(CarrierInfraMediator.Conditions conditions, int i, Object... objArr) {
        return this.carrierInfraMediatorImpl.isEnabled(conditions, i, objArr);
    }

    @Override // com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator
    public final void set(CarrierInfraMediator.Values values, Object... objArr) {
        this.carrierInfraMediatorImpl.set(values, ArraysKt___ArraysKt.getOrNull(objArr));
    }
}
