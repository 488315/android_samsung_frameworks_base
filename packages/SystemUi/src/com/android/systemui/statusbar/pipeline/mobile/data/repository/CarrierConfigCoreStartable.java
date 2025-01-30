package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import com.android.systemui.CoreStartable;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
