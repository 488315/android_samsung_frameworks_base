package com.android.server.location.gnss;

public final /* synthetic */ class GnssLocationProviderSec$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ GnssLocationProviderSec f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ GnssLocationProviderSec$$ExternalSyntheticLambda1(
            GnssLocationProviderSec gnssLocationProviderSec, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = gnssLocationProviderSec;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                GnssLocationProviderSec gnssLocationProviderSec = this.f$0;
                int i = this.f$1;
                gnssLocationProviderSec.getClass();
                gnssLocationProviderSec.gnssConfigurationUpdateSec("XTRA_ENABLE=" + i);
                break;
            default:
                GnssLocationProviderSec gnssLocationProviderSec2 = this.f$0;
                int i2 = this.f$1;
                gnssLocationProviderSec2.getClass();
                gnssLocationProviderSec2.gnssConfigurationUpdateSec("LID_STATE=" + i2);
                break;
        }
    }
}
