package com.android.wifitrackerlib;

import com.android.wifitrackerlib.WifiEntry;

/* loaded from: classes3.dex */
public final /* synthetic */ class WifiEntry$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiEntry f$0;

    public /* synthetic */ WifiEntry$$ExternalSyntheticLambda0(WifiEntry wifiEntry, int i) {
        this.$r8$classId = i;
        this.f$0 = wifiEntry;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        WifiEntry wifiEntry = this.f$0;
        switch (i) {
            case 0:
                WifiEntry.WifiEntryCallback wifiEntryCallback = wifiEntry.mListener;
                if (wifiEntryCallback != null) {
                    wifiEntryCallback.onUpdated();
                    break;
                }
                break;
            default:
                WifiEntry.ConnectCallback connectCallback = wifiEntry.mConnectCallback;
                if (connectCallback != null) {
                    connectCallback.onConnectResult(0);
                    break;
                }
                break;
        }
    }
}
