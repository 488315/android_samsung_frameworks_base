package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CarrierConfigCoreStartable implements CoreStartable {
    public final CarrierConfigRepository carrierConfigRepository;
    public final CoroutineScope scope;

    public CarrierConfigCoreStartable(CarrierConfigRepository carrierConfigRepository, CoroutineScope coroutineScope) {
        this.carrierConfigRepository = carrierConfigRepository;
        this.scope = coroutineScope;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new CarrierConfigCoreStartable$start$1(this, null), 7);
    }
}
