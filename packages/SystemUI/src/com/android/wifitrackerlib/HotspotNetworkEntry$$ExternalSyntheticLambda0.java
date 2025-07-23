package com.android.wifitrackerlib;

import com.android.wifitrackerlib.WifiEntry;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class HotspotNetworkEntry$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ HotspotNetworkEntry$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                int i2 = HotspotNetworkEntry.$r8$clinit;
                WifiEntry.ConnectCallback connectCallback = ((HotspotNetworkEntry) obj).mConnectCallback;
                if (connectCallback != null) {
                    connectCallback.onConnectResult(2);
                    break;
                }
                break;
            case 1:
                int i3 = HotspotNetworkEntry.$r8$clinit;
                WifiEntry.ConnectCallback connectCallback2 = ((HotspotNetworkEntry) obj).mConnectCallback;
                if (connectCallback2 != null) {
                    connectCallback2.onConnectResult(0);
                    break;
                }
                break;
            default:
                int i4 = HotspotNetworkEntry.$r8$clinit;
                ((WifiEntry.ConnectCallback) obj).onConnectResult(2);
                break;
        }
    }
}
