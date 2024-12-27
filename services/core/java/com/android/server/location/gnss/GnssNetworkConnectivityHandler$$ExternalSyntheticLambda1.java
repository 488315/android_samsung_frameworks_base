package com.android.server.location.gnss;

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
