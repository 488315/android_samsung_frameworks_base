package com.android.settingslib.wifi;

import com.android.settingslib.wifi.WifiStatusTracker;
import java.text.SimpleDateFormat;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        switch (this.$r8$classId) {
            case 0:
                ((WifiStatusTracker) this.f$0).postResults();
                break;
            default:
                WifiStatusTracker wifiStatusTracker = ((WifiStatusTracker.C09473) this.f$0).this$0;
                SimpleDateFormat simpleDateFormat = WifiStatusTracker.SSDF;
                wifiStatusTracker.postResults();
                break;
        }
    }
}
