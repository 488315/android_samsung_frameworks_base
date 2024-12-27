package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import com.android.systemui.CoreStartable;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
