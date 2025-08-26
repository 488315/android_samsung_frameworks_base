package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import android.telephony.satellite.SatelliteManager;
import com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Ref$BooleanRef;

/* loaded from: classes3.dex */
public final /* synthetic */ class DeviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1$$ExternalSyntheticLambda1 implements Function0 {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Ref$BooleanRef f$0;
    public final /* synthetic */ SatelliteManager f$1;
    public final /* synthetic */ Object f$2;
    public final /* synthetic */ DeviceBasedSatelliteRepositoryImpl f$3;

    public /* synthetic */ DeviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1$$ExternalSyntheticLambda1(Ref$BooleanRef ref$BooleanRef, SatelliteManager satelliteManager, DeviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1$cb$1 deviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1$cb$1, DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl) {
        this.f$0 = ref$BooleanRef;
        this.f$1 = satelliteManager;
        this.f$2 = deviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1$cb$1;
        this.f$3 = deviceBasedSatelliteRepositoryImpl;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                SatelliteManager satelliteManager = this.f$1;
                DeviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1$cb$1 deviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1$cb$1 = (DeviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1$cb$1) this.f$2;
                if (this.f$0.element) {
                    satelliteManager.unregisterForNtnSignalStrengthChanged(deviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1$cb$1);
                    DeviceBasedSatelliteRepositoryImpl.Companion.i$default(DeviceBasedSatelliteRepositoryImpl.Companion, this.f$3.logBuffer, new DeviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0(9));
                }
                break;
            default:
                SatelliteManager satelliteManager2 = this.f$1;
                DeviceBasedSatelliteRepositoryImpl$isSatelliteAvailableFlow$1$callback$1 deviceBasedSatelliteRepositoryImpl$isSatelliteAvailableFlow$1$callback$1 = (DeviceBasedSatelliteRepositoryImpl$isSatelliteAvailableFlow$1$callback$1) this.f$2;
                if (this.f$0.element) {
                    DeviceBasedSatelliteRepositoryImpl.Companion.i$default(DeviceBasedSatelliteRepositoryImpl.Companion, this.f$3.logBuffer, new DeviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0(5));
                    satelliteManager2.unregisterForCommunicationAccessStateChanged(deviceBasedSatelliteRepositoryImpl$isSatelliteAvailableFlow$1$callback$1);
                }
                break;
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ DeviceBasedSatelliteRepositoryImpl$signalStrengthFlow$1$$ExternalSyntheticLambda1(Ref$BooleanRef ref$BooleanRef, DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl, SatelliteManager satelliteManager, DeviceBasedSatelliteRepositoryImpl$isSatelliteAvailableFlow$1$callback$1 deviceBasedSatelliteRepositoryImpl$isSatelliteAvailableFlow$1$callback$1) {
        this.f$0 = ref$BooleanRef;
        this.f$3 = deviceBasedSatelliteRepositoryImpl;
        this.f$1 = satelliteManager;
        this.f$2 = deviceBasedSatelliteRepositoryImpl$isSatelliteAvailableFlow$1$callback$1;
    }
}
