package com.android.settingslib.wifi;

import com.android.settingslib.wifi.WifiStatusTracker;
import java.text.SimpleDateFormat;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class WifiStatusTracker$2$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiStatusTracker.C09462 f$0;

    public /* synthetic */ WifiStatusTracker$2$$ExternalSyntheticLambda0(WifiStatusTracker.C09462 c09462, int i) {
        this.$r8$classId = i;
        this.f$0 = c09462;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                WifiStatusTracker wifiStatusTracker = this.f$0.this$0;
                SimpleDateFormat simpleDateFormat = WifiStatusTracker.SSDF;
                wifiStatusTracker.postResults();
                break;
            default:
                WifiStatusTracker wifiStatusTracker2 = this.f$0.this$0;
                SimpleDateFormat simpleDateFormat2 = WifiStatusTracker.SSDF;
                wifiStatusTracker2.postResults();
                break;
        }
    }
}
