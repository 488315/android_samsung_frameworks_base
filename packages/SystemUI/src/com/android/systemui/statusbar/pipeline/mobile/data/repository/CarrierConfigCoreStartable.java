package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import com.android.systemui.CoreStartable;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

public final class CarrierConfigCoreStartable implements CoreStartable {
    public final CarrierConfigRepository carrierConfigRepository;
    public final CoroutineScope scope;

    public CarrierConfigCoreStartable(CarrierConfigRepository carrierConfigRepository, CoroutineScope coroutineScope) {
        this.carrierConfigRepository = carrierConfigRepository;
        this.scope = coroutineScope;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        BuildersKt.launch$default(this.scope, null, null, new CarrierConfigCoreStartable$start$1(this, null), 3);
    }
}
