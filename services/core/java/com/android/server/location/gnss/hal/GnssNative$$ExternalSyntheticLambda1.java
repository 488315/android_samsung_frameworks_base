package com.android.server.location.gnss.hal;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class GnssNative$$ExternalSyntheticLambda1
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ GnssNative f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ GnssNative$$ExternalSyntheticLambda1(
            GnssNative gnssNative, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = gnssNative;
        this.f$1 = i;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                GnssNative gnssNative = this.f$0;
                int i = this.f$1;
                int i2 = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative.lambda$reportStatus$7(i);
                break;
            case 1:
                GnssNative gnssNative2 = this.f$0;
                int i3 = this.f$1;
                int i4 = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative2.lambda$requestWlanScanInfo$39(i3);
                break;
            case 2:
                GnssNative gnssNative3 = this.f$0;
                int i5 = this.f$1;
                int i6 = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative3.lambda$psdsDownloadRequest$19(i5);
                break;
            case 3:
                GnssNative gnssNative4 = this.f$0;
                int i7 = this.f$1;
                int i8 = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative4.lambda$requestFlpLocation$35(i7);
                break;
            case 4:
                GnssNative gnssNative5 = this.f$0;
                int i9 = this.f$1;
                int i10 = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative5.lambda$requestSetID$26(i9);
                break;
            default:
                GnssNative gnssNative6 = this.f$0;
                int i11 = this.f$1;
                int i12 = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative6.lambda$requestUbpInfo$37(i11);
                break;
        }
    }
}
