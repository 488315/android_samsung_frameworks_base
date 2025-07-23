package com.android.wifitrackerlib;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.util.ArrayMap;
import com.samsung.android.wifi.SemWifiConfiguration;
import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda33 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiPickerTracker f$0;
    public final /* synthetic */ Map f$1;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda33(WifiPickerTracker wifiPickerTracker, Map map, int i) {
        this.$r8$classId = i;
        this.f$0 = wifiPickerTracker;
        this.f$1 = map;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                WifiPickerTracker wifiPickerTracker = this.f$0;
                Map map = this.f$1;
                wifiPickerTracker.getClass();
                PasspointWifiEntry passpointWifiEntry = (PasspointWifiEntry) ((Map.Entry) obj).getValue();
                PasspointConfiguration passpointConfiguration = (PasspointConfiguration) ((ArrayMap) wifiPickerTracker.mPasspointConfigCache).get(passpointWifiEntry.mKey);
                synchronized (passpointWifiEntry) {
                    try {
                        passpointWifiEntry.mPasspointConfig = passpointConfiguration;
                        if (passpointConfiguration != null) {
                            passpointWifiEntry.mSubscriptionExpirationTimeInMillis = passpointConfiguration.getSubscriptionExpirationTimeMillis();
                            passpointWifiEntry.mMeteredOverride = passpointConfiguration.getMeteredOverride();
                        }
                        passpointWifiEntry.semUpdateFlags(passpointWifiEntry.mPasspointConfig);
                        passpointWifiEntry.notifyOnUpdated();
                    } finally {
                    }
                }
                synchronized (passpointWifiEntry) {
                    try {
                        WifiConfiguration wifiConfiguration = passpointWifiEntry.mWifiConfig;
                        if (wifiConfiguration != null) {
                            SemWifiConfiguration semWifiConfiguration = (SemWifiConfiguration) ((HashMap) map).get(wifiConfiguration.getKey());
                            SemWifiEntryFlags semWifiEntryFlags = passpointWifiEntry.mSemFlags;
                            if (semWifiConfiguration != null) {
                                semWifiConfiguration.isLockDown();
                                semWifiEntryFlags.getClass();
                                semWifiConfiguration.isCaptivePortal();
                                semWifiEntryFlags.semConfig = semWifiConfiguration;
                            } else {
                                semWifiEntryFlags.getClass();
                            }
                            passpointWifiEntry.notifyOnUpdated();
                        }
                        passpointWifiEntry.notifyOnUpdated();
                    } finally {
                    }
                }
                return (passpointWifiEntry.isSubscription() || passpointWifiEntry.isSuggestion()) ? false : true;
            default:
                WifiPickerTracker wifiPickerTracker2 = this.f$0;
                Map map2 = this.f$1;
                StandardWifiEntry standardWifiEntry = (StandardWifiEntry) obj;
                standardWifiEntry.updateConfig((List) ((ArrayMap) wifiPickerTracker2.mSuggestedConfigCache).get(standardWifiEntry.mKey));
                standardWifiEntry.semUpdateSemWifiConfig(map2);
                return !standardWifiEntry.isSuggestion();
        }
    }
}
