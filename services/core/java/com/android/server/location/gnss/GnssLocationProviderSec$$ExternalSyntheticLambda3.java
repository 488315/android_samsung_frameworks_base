package com.android.server.location.gnss;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
