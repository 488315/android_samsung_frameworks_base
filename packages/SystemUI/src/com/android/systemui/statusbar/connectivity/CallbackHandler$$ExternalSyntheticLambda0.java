package com.android.systemui.statusbar.connectivity;

import java.util.ArrayList;

/* loaded from: classes3.dex */
public final /* synthetic */ class CallbackHandler$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CallbackHandler f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ CallbackHandler$$ExternalSyntheticLambda0(CallbackHandler callbackHandler, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = callbackHandler;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                CallbackHandler callbackHandler = this.f$0;
                WifiIndicators wifiIndicators = (WifiIndicators) this.f$1;
                ArrayList arrayList = callbackHandler.mSignalCallbacks;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((SignalCallback) obj).setWifiIndicators(wifiIndicators);
                }
                break;
            default:
                CallbackHandler callbackHandler2 = this.f$0;
                MobileDataIndicators mobileDataIndicators = (MobileDataIndicators) this.f$1;
                ArrayList arrayList2 = callbackHandler2.mSignalCallbacks;
                int size2 = arrayList2.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj2 = arrayList2.get(i2);
                    i2++;
                    ((SignalCallback) obj2).setMobileDataIndicators(mobileDataIndicators);
                }
                break;
        }
    }
}
