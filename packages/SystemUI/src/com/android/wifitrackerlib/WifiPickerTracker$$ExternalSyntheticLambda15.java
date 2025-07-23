package com.android.wifitrackerlib;

import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda15 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Set f$0;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda15(int i, Set set) {
        this.$r8$classId = i;
        this.f$0 = set;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Set set = this.f$0;
        switch (i) {
            case 0:
                WifiEntry wifiEntry = (WifiEntry) obj;
                return (wifiEntry instanceof StandardWifiEntry) && set.contains(((StandardWifiEntry) wifiEntry).mKey.mScanResultKey);
            case 1:
                KnownNetworkEntry knownNetworkEntry = (KnownNetworkEntry) obj;
                return knownNetworkEntry.getConnectedState() == 0 && !set.contains(knownNetworkEntry.mKey.mScanResultKey);
            case 2:
                return !set.contains(Long.valueOf(((HotspotNetworkEntry) obj).mKey.mDeviceId));
            default:
                Map.Entry entry = (Map.Entry) obj;
                return ((PasspointWifiEntry) entry.getValue()).getLevel() == -1 || (!set.contains(entry.getKey()) && ((PasspointWifiEntry) entry.getValue()).getConnectedState() == 0);
        }
    }
}
