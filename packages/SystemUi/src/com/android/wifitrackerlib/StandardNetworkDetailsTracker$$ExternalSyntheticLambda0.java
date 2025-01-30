package com.android.wifitrackerlib;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import com.android.wifitrackerlib.StandardWifiEntry;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class StandardNetworkDetailsTracker$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StandardNetworkDetailsTracker f$0;

    public /* synthetic */ StandardNetworkDetailsTracker$$ExternalSyntheticLambda0(StandardNetworkDetailsTracker standardNetworkDetailsTracker, int i) {
        this.$r8$classId = i;
        this.f$0 = standardNetworkDetailsTracker;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                StandardNetworkDetailsTracker standardNetworkDetailsTracker = this.f$0;
                standardNetworkDetailsTracker.getClass();
                return new StandardWifiEntry.ScanResultKey((ScanResult) obj).equals(standardNetworkDetailsTracker.mKey.mScanResultKey);
            default:
                StandardNetworkDetailsTracker standardNetworkDetailsTracker2 = this.f$0;
                WifiConfiguration wifiConfiguration = (WifiConfiguration) obj;
                standardNetworkDetailsTracker2.getClass();
                if (wifiConfiguration.isPasspoint()) {
                    return false;
                }
                StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey = standardNetworkDetailsTracker2.mKey;
                return standardWifiEntryKey.equals(new StandardWifiEntry.StandardWifiEntryKey(wifiConfiguration, standardWifiEntryKey.mIsTargetingNewNetworks));
        }
    }
}
