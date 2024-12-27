package com.android.server.location.gnss;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
