package com.android.server.location.gnss;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class GnssNetworkConnectivityHandler$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ GnssNetworkConnectivityHandler f$0;

    public /* synthetic */ GnssNetworkConnectivityHandler$$ExternalSyntheticLambda1(
            GnssNetworkConnectivityHandler gnssNetworkConnectivityHandler, int i) {
        this.$r8$classId = i;
        this.f$0 = gnssNetworkConnectivityHandler;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        GnssNetworkConnectivityHandler gnssNetworkConnectivityHandler = this.f$0;
        switch (i) {
            case 0:
                boolean z = GnssNetworkConnectivityHandler.VERBOSE;
                gnssNetworkConnectivityHandler.handleReleaseSuplConnection(2);
                break;
            default:
                boolean z2 = GnssNetworkConnectivityHandler.VERBOSE;
                gnssNetworkConnectivityHandler.handleReleaseSuplConnection(2);
                break;
        }
    }
}
