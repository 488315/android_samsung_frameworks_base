package com.android.wifitrackerlib;

import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiScanner;
import android.util.ArrayMap;
import android.util.Pair;
import androidx.lifecycle.Lifecycle;
import com.android.wifitrackerlib.BaseWifiTracker;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final /* synthetic */ class BaseWifiTracker$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BaseWifiTracker$$ExternalSyntheticLambda0(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((Lifecycle) this.f$1).addObserver(((BaseWifiTracker) this.f$0).mLifecycleObserver);
                return;
            default:
                BaseWifiTracker.Scanner.AnonymousClass1 anonymousClass1 = (BaseWifiTracker.Scanner.AnonymousClass1) this.f$0;
                WifiScanner.ScanData[] scanDataArr = (WifiScanner.ScanData[]) this.f$1;
                BaseWifiTracker.Scanner scanner = BaseWifiTracker.Scanner.this;
                int i = BaseWifiTracker.Scanner.$r8$clinit;
                if (scanner.shouldScan()) {
                    if (BaseWifiTracker.sVerboseLogging) {
                        String str = BaseWifiTracker.this.mTag;
                    }
                    ArrayList arrayList = new ArrayList();
                    int i2 = 0;
                    if (scanDataArr != null) {
                        for (WifiScanner.ScanData scanData : scanDataArr) {
                            arrayList.addAll(List.of((Object[]) scanData.getResults()));
                        }
                    }
                    ScanResultUpdater scanResultUpdater = BaseWifiTracker.this.mScanResultUpdater;
                    synchronized (scanResultUpdater.mLock) {
                        try {
                            int size = arrayList.size();
                            while (i2 < size) {
                                Object obj = arrayList.get(i2);
                                i2++;
                                ScanResult scanResult = (ScanResult) obj;
                                Pair pair = new Pair(scanResult.SSID, scanResult.BSSID);
                                ScanResult scanResult2 = (ScanResult) ((ArrayMap) scanResultUpdater.mScanResultsBySsidAndBssid).get(pair);
                                if (scanResult2 == null || scanResult2.timestamp < scanResult.timestamp) {
                                    ((ArrayMap) scanResultUpdater.mScanResultsBySsidAndBssid).put(pair, scanResult);
                                }
                            }
                            ((ArrayMap) scanResultUpdater.mScanResultsBySsidAndBssid).entrySet().removeIf(new ScanResultUpdater$$ExternalSyntheticLambda0(scanResultUpdater, scanResultUpdater.mMaxScanAgeMillis, 1));
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    BaseWifiTracker.this.handleScanResultsAvailableAction(new Intent("android.net.wifi.SCAN_RESULTS").putExtra("resultsUpdated", true));
                    BaseWifiTracker.Scanner.this.scanLoop();
                    return;
                }
                return;
        }
    }
}
