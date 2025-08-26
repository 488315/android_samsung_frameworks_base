package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import android.telephony.satellite.SatelliteManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Ref$BooleanRef;

/* loaded from: classes3.dex */
public final /* synthetic */ class DeviceBasedSatelliteRepositoryImpl$connectionStateFlow$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Ref$BooleanRef f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ DeviceBasedSatelliteRepositoryImpl$connectionStateFlow$1$$ExternalSyntheticLambda0(Ref$BooleanRef ref$BooleanRef, Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = ref$BooleanRef;
        this.f$1 = obj;
        this.f$2 = obj2;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                SatelliteManager satelliteManager = (SatelliteManager) this.f$1;
                DeviceBasedSatelliteRepositoryImpl$connectionStateFlow$1$cb$1 deviceBasedSatelliteRepositoryImpl$connectionStateFlow$1$cb$1 = (DeviceBasedSatelliteRepositoryImpl$connectionStateFlow$1$cb$1) this.f$2;
                if (this.f$0.element) {
                    satelliteManager.unregisterForModemStateChanged(deviceBasedSatelliteRepositoryImpl$connectionStateFlow$1$cb$1);
                }
                break;
            case 1:
                DeviceBasedSatelliteRepositoryImpl$satelliteIsSupportedCallback$1$callback$1 deviceBasedSatelliteRepositoryImpl$satelliteIsSupportedCallback$1$callback$1 = (DeviceBasedSatelliteRepositoryImpl$satelliteIsSupportedCallback$1$callback$1) this.f$2;
                if (this.f$0.element) {
                    ((DeviceBasedSatelliteRepositoryImpl) this.f$1).satelliteManager.unregisterForSupportedStateChanged(deviceBasedSatelliteRepositoryImpl$satelliteIsSupportedCallback$1$callback$1);
                }
                break;
            default:
                SatelliteManager satelliteManager2 = (SatelliteManager) this.f$1;
                DeviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1$callback$1 deviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1$callback$1 = (DeviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1$callback$1) this.f$2;
                if (this.f$0.element) {
                    satelliteManager2.unregisterForProvisionStateChanged(deviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1$callback$1);
                }
                break;
        }
        return Unit.INSTANCE;
    }
}
