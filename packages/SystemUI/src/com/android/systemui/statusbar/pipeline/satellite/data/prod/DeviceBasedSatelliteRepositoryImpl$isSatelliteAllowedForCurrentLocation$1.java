package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import android.telephony.satellite.SatelliteManager;
import com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class DeviceBasedSatelliteRepositoryImpl$isSatelliteAllowedForCurrentLocation$1 extends FunctionReferenceImpl implements Function1 {
    public DeviceBasedSatelliteRepositoryImpl$isSatelliteAllowedForCurrentLocation$1(Object obj) {
        super(1, obj, DeviceBasedSatelliteRepositoryImpl.class, "isSatelliteAvailableFlow", "isSatelliteAvailableFlow(Landroid/telephony/satellite/SatelliteManager;)Lkotlinx/coroutines/flow/Flow;", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke, reason: merged with bridge method [inline-methods] */
    public final Flow mo779invoke(SatelliteManager satelliteManager) {
        DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl = (DeviceBasedSatelliteRepositoryImpl) this.receiver;
        DeviceBasedSatelliteRepositoryImpl.Companion companion = DeviceBasedSatelliteRepositoryImpl.Companion;
        deviceBasedSatelliteRepositoryImpl.getClass();
        return FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new DeviceBasedSatelliteRepositoryImpl$isSatelliteAvailableFlow$1(deviceBasedSatelliteRepositoryImpl, satelliteManager, null)), deviceBasedSatelliteRepositoryImpl.bgDispatcher);
    }
}
