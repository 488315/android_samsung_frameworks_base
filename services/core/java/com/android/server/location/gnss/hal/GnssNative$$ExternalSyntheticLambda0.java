package com.android.server.location.gnss.hal;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class GnssNative$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ GnssNative f$0;

    public /* synthetic */ GnssNative$$ExternalSyntheticLambda0(GnssNative gnssNative, int i) {
        this.$r8$classId = i;
        this.f$0 = gnssNative;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        GnssNative gnssNative = this.f$0;
        switch (i) {
            case 0:
                int i2 = GnssNative.GNSS_POSITION_MODE_STANDALONE;
                gnssNative.lambda$new$1();
                break;
            default:
                gnssNative.restartHal();
                break;
        }
    }
}
