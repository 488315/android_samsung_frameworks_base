package com.android.wifitrackerlib;

import android.content.Intent;
import android.net.wifi.WifiScanner;
import com.android.wifitrackerlib.BaseWifiTracker;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BaseWifiTracker$Scanner$1$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BaseWifiTracker.Scanner.C37651 f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BaseWifiTracker$Scanner$1$$ExternalSyntheticLambda1(BaseWifiTracker.Scanner.C37651 c37651, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = c37651;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = 1;
        switch (this.$r8$classId) {
            case 0:
                BaseWifiTracker.Scanner.C37651 c37651 = this.f$0;
                WifiScanner.ScanData[] scanDataArr = (WifiScanner.ScanData[]) this.f$1;
                BaseWifiTracker.Scanner scanner = c37651.this$1;
                int i2 = BaseWifiTracker.Scanner.$r8$clinit;
                if (scanner.mIsWifiEnabled && scanner.mIsStartedState) {
                    String str = scanner.this$0.mTag;
                    ArrayList arrayList = new ArrayList();
                    if (scanDataArr != null) {
                        for (WifiScanner.ScanData scanData : scanDataArr) {
                            arrayList.addAll(List.of((Object[]) scanData.getResults()));
                        }
                    }
                    c37651.this$1.this$0.mWorkerHandler.post(new BaseWifiTracker$Scanner$1$$ExternalSyntheticLambda1(c37651, arrayList, i));
                    c37651.this$1.scanLoop();
                    break;
                }
                break;
            default:
                BaseWifiTracker.Scanner.C37651 c376512 = this.f$0;
                c376512.this$1.this$0.mScanResultUpdater.update((List) this.f$1);
                c376512.this$1.this$0.handleScanResultsAvailableAction(new Intent("android.net.wifi.SCAN_RESULTS").putExtra("resultsUpdated", true));
                break;
        }
    }
}
