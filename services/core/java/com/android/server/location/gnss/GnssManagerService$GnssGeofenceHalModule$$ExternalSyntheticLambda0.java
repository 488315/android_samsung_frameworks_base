package com.android.server.location.gnss;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */
class GnssManagerService$GnssGeofenceHalModule$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ GnssManagerService.GnssGeofenceHalModule f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ GnssManagerService$GnssGeofenceHalModule$$ExternalSyntheticLambda0(
            GnssManagerService.GnssGeofenceHalModule gnssGeofenceHalModule, int i, int i2, int i3) {
        this.$r8$classId = i3;
        this.f$0 = gnssGeofenceHalModule;
        this.f$1 = i;
        this.f$2 = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                GnssManagerService.GnssGeofenceHalModule gnssGeofenceHalModule = this.f$0;
                gnssGeofenceHalModule
                        .getGeofenceHardware()
                        .reportGeofencePauseStatus(
                                this.f$1,
                                GnssManagerService.GnssGeofenceHalModule.translateGeofenceStatus(
                                        this.f$2));
                break;
            case 1:
                GnssManagerService.GnssGeofenceHalModule gnssGeofenceHalModule2 = this.f$0;
                gnssGeofenceHalModule2
                        .getGeofenceHardware()
                        .reportGeofenceAddStatus(
                                this.f$1,
                                GnssManagerService.GnssGeofenceHalModule.translateGeofenceStatus(
                                        this.f$2));
                break;
            case 2:
                GnssManagerService.GnssGeofenceHalModule gnssGeofenceHalModule3 = this.f$0;
                gnssGeofenceHalModule3
                        .getGeofenceHardware()
                        .reportGeofenceRemoveStatus(
                                this.f$1,
                                GnssManagerService.GnssGeofenceHalModule.translateGeofenceStatus(
                                        this.f$2));
                break;
            default:
                GnssManagerService.GnssGeofenceHalModule gnssGeofenceHalModule4 = this.f$0;
                gnssGeofenceHalModule4
                        .getGeofenceHardware()
                        .reportGeofenceResumeStatus(
                                this.f$1,
                                GnssManagerService.GnssGeofenceHalModule.translateGeofenceStatus(
                                        this.f$2));
                break;
        }
    }
}
