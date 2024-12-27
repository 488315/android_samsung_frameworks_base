package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import android.telephony.satellite.SatelliteManager;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final /* synthetic */ class DeviceBasedSatelliteRepositoryImpl$connectionState$1 extends FunctionReferenceImpl implements Function1 {
    public DeviceBasedSatelliteRepositoryImpl$connectionState$1(Object obj) {
        super(1, obj, DeviceBasedSatelliteRepositoryImpl.class, "connectionStateFlow", "connectionStateFlow(Landroid/telephony/satellite/SatelliteManager;)Lkotlinx/coroutines/flow/Flow;", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Flow invoke(SatelliteManager satelliteManager) {
        DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl = (DeviceBasedSatelliteRepositoryImpl) this.receiver;
        DeviceBasedSatelliteRepositoryImpl.Companion companion = DeviceBasedSatelliteRepositoryImpl.Companion;
        deviceBasedSatelliteRepositoryImpl.getClass();
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        DeviceBasedSatelliteRepositoryImpl$connectionStateFlow$1 deviceBasedSatelliteRepositoryImpl$connectionStateFlow$1 = new DeviceBasedSatelliteRepositoryImpl$connectionStateFlow$1(satelliteManager, deviceBasedSatelliteRepositoryImpl, null);
        conflatedCallbackFlow.getClass();
        return FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(deviceBasedSatelliteRepositoryImpl$connectionStateFlow$1), deviceBasedSatelliteRepositoryImpl.bgDispatcher);
    }
}
