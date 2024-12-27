package com.android.server.location.gnss.hal;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class GnssNative$$ExternalSyntheticLambda3
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ GnssNative f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ GnssNative$$ExternalSyntheticLambda3(
            GnssNative gnssNative, int i, int i2, int i3) {
        this.$r8$classId = i3;
        this.f$0 = gnssNative;
        this.f$1 = i;
        this.f$2 = i2;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                GnssNative gnssNative = this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                int i3 = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative.lambda$reportGeofencePauseStatus$24(i, i2);
                break;
            case 1:
                GnssNative gnssNative2 = this.f$0;
                int i4 = this.f$1;
                int i5 = this.f$2;
                int i6 = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative2.lambda$reportGeofenceAddStatus$22(i4, i5);
                break;
            case 2:
                GnssNative gnssNative3 = this.f$0;
                int i7 = this.f$1;
                int i8 = this.f$2;
                int i9 = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative3.lambda$reportGeofenceRemoveStatus$23(i7, i8);
                break;
            default:
                GnssNative gnssNative4 = this.f$0;
                int i10 = this.f$1;
                int i11 = this.f$2;
                int i12 = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative4.lambda$reportGeofenceResumeStatus$25(i10, i11);
                break;
        }
    }
}
