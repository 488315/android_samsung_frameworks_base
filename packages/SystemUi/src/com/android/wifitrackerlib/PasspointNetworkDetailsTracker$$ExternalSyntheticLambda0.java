package com.android.wifitrackerlib;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.text.TextUtils;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PasspointNetworkDetailsTracker$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PasspointNetworkDetailsTracker$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return TextUtils.equals((String) this.f$0, PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(((PasspointConfiguration) obj).getUniqueId()));
            case 1:
                WifiConfiguration wifiConfiguration = (WifiConfiguration) obj;
                return wifiConfiguration.isPasspoint() && TextUtils.equals((String) this.f$0, PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(wifiConfiguration.getKey()));
            default:
                PasspointNetworkDetailsTracker passpointNetworkDetailsTracker = (PasspointNetworkDetailsTracker) this.f$0;
                passpointNetworkDetailsTracker.getClass();
                return TextUtils.equals(PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(((PasspointConfiguration) obj).getUniqueId()), passpointNetworkDetailsTracker.mChosenEntry.mKey);
        }
    }
}
