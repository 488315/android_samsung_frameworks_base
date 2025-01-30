package com.android.wifitrackerlib;

import com.android.wifitrackerlib.WifiEntry;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiEntry$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ WifiEntry$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                WifiEntry.WifiEntryCallback wifiEntryCallback = ((WifiEntry) this.f$0).mListener;
                if (wifiEntryCallback != null) {
                    wifiEntryCallback.onUpdated();
                    break;
                }
                break;
            case 1:
                WifiEntry.ConnectCallback connectCallback = ((WifiEntry) this.f$0).mConnectCallback;
                if (connectCallback != null) {
                    connectCallback.onConnectResult(0);
                    break;
                }
                break;
            case 2:
                ((WifiEntry) this.f$0).getClass();
                break;
            default:
                WifiEntry.ConnectActionListener connectActionListener = (WifiEntry.ConnectActionListener) this.f$0;
                WifiEntry wifiEntry = connectActionListener.this$0;
                WifiEntry.ConnectCallback connectCallback2 = wifiEntry.mConnectCallback;
                if (connectCallback2 != null && wifiEntry.mCalledConnect && wifiEntry.getConnectedState() == 0) {
                    connectCallback2.onConnectResult(2);
                    connectActionListener.this$0.mCalledConnect = false;
                    break;
                }
                break;
        }
    }
}
