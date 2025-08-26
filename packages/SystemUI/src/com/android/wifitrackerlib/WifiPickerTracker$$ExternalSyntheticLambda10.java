package com.android.wifitrackerlib;

import android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus;
import android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda10 implements Consumer {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                KnownNetworkEntry knownNetworkEntry = (KnownNetworkEntry) obj;
                if (((KnownNetworkConnectionStatus) obj2).getStatus() != 2) {
                    knownNetworkEntry.getClass();
                    break;
                } else if (knownNetworkEntry.mConnectCallback != null) {
                    knownNetworkEntry.mCallbackHandler.post(new KnownNetworkEntry$$ExternalSyntheticLambda0(knownNetworkEntry, 0));
                    break;
                }
                break;
            default:
                HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus = (HotspotNetworkConnectionStatus) obj2;
                HotspotNetworkEntry hotspotNetworkEntry = (HotspotNetworkEntry) obj;
                if (!hotspotNetworkConnectionStatus.getExtras().getBoolean("connection_status_connected", false)) {
                    hotspotNetworkEntry.onConnectionStatusChanged(hotspotNetworkConnectionStatus.getStatus());
                    break;
                } else {
                    hotspotNetworkEntry.onConnectionStatusChanged(10);
                    break;
                }
        }
    }
}
