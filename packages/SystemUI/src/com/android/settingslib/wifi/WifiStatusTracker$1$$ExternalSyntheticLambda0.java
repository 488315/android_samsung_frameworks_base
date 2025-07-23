package com.android.settingslib.wifi;

import com.android.settingslib.wifi.WifiStatusTracker;
import java.text.SimpleDateFormat;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class WifiStatusTracker$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiStatusTracker.AnonymousClass1 f$0;

    public /* synthetic */ WifiStatusTracker$1$$ExternalSyntheticLambda0(WifiStatusTracker.AnonymousClass1 anonymousClass1, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass1;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        WifiStatusTracker.AnonymousClass1 anonymousClass1 = this.f$0;
        switch (i) {
            case 0:
                WifiStatusTracker wifiStatusTracker = anonymousClass1.this$0;
                SimpleDateFormat simpleDateFormat = WifiStatusTracker.SSDF;
                wifiStatusTracker.postResults();
                break;
            default:
                WifiStatusTracker wifiStatusTracker2 = anonymousClass1.this$0;
                SimpleDateFormat simpleDateFormat2 = WifiStatusTracker.SSDF;
                wifiStatusTracker2.postResults();
                break;
        }
    }
}
