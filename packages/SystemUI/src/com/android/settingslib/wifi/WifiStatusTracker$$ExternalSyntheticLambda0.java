package com.android.settingslib.wifi;

import com.android.settingslib.wifi.WifiStatusTracker;
import java.text.SimpleDateFormat;

/* loaded from: classes.dex */
public final /* synthetic */ class WifiStatusTracker$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ WifiStatusTracker$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                SimpleDateFormat simpleDateFormat = WifiStatusTracker.SSDF;
                ((WifiStatusTracker) obj).postResults();
                break;
            default:
                WifiStatusTracker wifiStatusTracker = ((WifiStatusTracker.AnonymousClass3) obj).this$0;
                SimpleDateFormat simpleDateFormat2 = WifiStatusTracker.SSDF;
                wifiStatusTracker.postResults();
                break;
        }
    }
}
