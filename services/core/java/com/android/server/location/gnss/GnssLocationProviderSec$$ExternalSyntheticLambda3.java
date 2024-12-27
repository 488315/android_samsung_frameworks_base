package com.android.server.location.gnss;

public final /* synthetic */ class GnssLocationProviderSec$$ExternalSyntheticLambda3
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ GnssLocationProviderSec f$0;

    public /* synthetic */ GnssLocationProviderSec$$ExternalSyntheticLambda3(
            GnssLocationProviderSec gnssLocationProviderSec, int i) {
        this.$r8$classId = i;
        this.f$0 = gnssLocationProviderSec;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        GnssLocationProviderSec gnssLocationProviderSec = this.f$0;
        switch (i) {
            case 0:
                gnssLocationProviderSec.gnssConfigurationUpdateSec("EMERGENCY_SMS=1");
                break;
            default:
                gnssLocationProviderSec.gnssConfigurationUpdateSec("USE_SECGPS_CONF=0");
                break;
        }
    }
}
