package com.android.wifitrackerlib;

import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda4 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Set f$0;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda4(int i, Set set) {
        this.$r8$classId = i;
        this.f$0 = set;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                Set set = this.f$0;
                Map.Entry entry = (Map.Entry) obj;
                if (((PasspointWifiEntry) entry.getValue()).mLevel == -1 || (!set.contains(entry.getKey()) && ((PasspointWifiEntry) entry.getValue()).getConnectedState() == 0)) {
                    break;
                }
                break;
            case 1:
                Set set2 = this.f$0;
                WifiEntry wifiEntry = (WifiEntry) obj;
                if ((wifiEntry instanceof StandardWifiEntry) && set2.contains(((StandardWifiEntry) wifiEntry).mKey.mScanResultKey)) {
                    break;
                }
                break;
        }
        return true;
    }
}
