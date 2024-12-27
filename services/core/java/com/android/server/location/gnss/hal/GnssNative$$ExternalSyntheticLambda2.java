package com.android.server.location.gnss.hal;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class GnssNative$$ExternalSyntheticLambda2
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ GnssNative f$0;

    public /* synthetic */ GnssNative$$ExternalSyntheticLambda2(GnssNative gnssNative, int i) {
        this.$r8$classId = i;
        this.f$0 = gnssNative;
    }

    public final void runOrThrow() {
        int i = this.$r8$classId;
        GnssNative gnssNative = this.f$0;
        switch (i) {
            case 0:
                int i2 = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative.lambda$requestWlanCapability$38();
                break;
            case 1:
                int i3 = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative.lambda$requestUbpCapability$36();
                break;
            case 2:
                int i4 = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative.lambda$requestLppeCommonIesCapability$34();
                break;
            case 3:
                int i5 = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative.lambda$requestRefLocationSec$30();
                break;
            case 4:
                int i6 = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative.lambda$requestUtcTime$28();
                break;
            default:
                int i7 = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative.lambda$requestRefLocation$29();
                break;
        }
    }
}
