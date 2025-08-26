package com.android.wifitrackerlib;

import android.net.wifi.ScanResult;
import com.android.wifitrackerlib.StandardWifiEntry;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda9 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StandardWifiEntry.ScanResultKey f$0;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda9(StandardWifiEntry.ScanResultKey scanResultKey, int i) {
        this.$r8$classId = i;
        this.f$0 = scanResultKey;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        StandardWifiEntry.ScanResultKey scanResultKey = this.f$0;
        switch (i) {
            case 0:
                return ((KnownNetworkEntry) obj).mKey.mScanResultKey.equals(scanResultKey);
            default:
                return scanResultKey.equals(new StandardWifiEntry.ScanResultKey((ScanResult) obj));
        }
    }
}
