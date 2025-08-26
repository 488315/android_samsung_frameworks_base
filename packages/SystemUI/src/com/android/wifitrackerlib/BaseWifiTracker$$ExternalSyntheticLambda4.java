package com.android.wifitrackerlib;

import com.android.wifitrackerlib.BaseWifiTracker;

/* loaded from: classes3.dex */
public final /* synthetic */ class BaseWifiTracker$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BaseWifiTracker.BaseWifiTrackerCallback f$0;

    public /* synthetic */ BaseWifiTracker$$ExternalSyntheticLambda4(BaseWifiTracker.BaseWifiTrackerCallback baseWifiTrackerCallback, int i) {
        this.$r8$classId = i;
        this.f$0 = baseWifiTrackerCallback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        BaseWifiTracker.BaseWifiTrackerCallback baseWifiTrackerCallback = this.f$0;
        switch (i) {
            case 0:
                baseWifiTrackerCallback.onWifiStateChanged();
                break;
            default:
                baseWifiTrackerCallback.onScanRequested();
                break;
        }
    }
}
