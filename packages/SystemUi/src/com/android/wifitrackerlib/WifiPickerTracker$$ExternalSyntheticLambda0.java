package com.android.wifitrackerlib;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.text.TextUtils;
import java.util.Map;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean isEmpty;
        boolean z;
        boolean z2;
        switch (this.$r8$classId) {
            case 0:
                isEmpty = TextUtils.isEmpty(((ScanResult) obj).SSID);
                break;
            case 1:
                KnownNetworkEntry knownNetworkEntry = (KnownNetworkEntry) obj;
                return knownNetworkEntry.mLevel == -1 && knownNetworkEntry.getConnectedState() == 0;
            case 2:
                isEmpty = ((WifiConfiguration) obj).isEphemeral();
                break;
            case 3:
                ScanResult scanResult = (ScanResult) obj;
                return (TextUtils.isEmpty(scanResult.SSID) || scanResult.capabilities.contains("[IBSS]")) ? false : true;
            case 4:
                StandardWifiEntry standardWifiEntry = (StandardWifiEntry) obj;
                return standardWifiEntry.mLevel == -1 && standardWifiEntry.getConnectedState() == 0;
            case 5:
                ScanResult scanResult2 = (ScanResult) obj;
                return (TextUtils.isEmpty(scanResult2.SSID) || scanResult2.capabilities.contains("[IBSS]")) ? false : true;
            case 6:
                StandardWifiEntry standardWifiEntry2 = (StandardWifiEntry) obj;
                return standardWifiEntry2.mLevel == -1 && standardWifiEntry2.getConnectedState() == 0;
            case 7:
                return ((WifiEntry) obj).getConnectedState() == 0;
            case 8:
                StandardWifiEntry standardWifiEntry3 = (StandardWifiEntry) obj;
                if (standardWifiEntry3.getConnectedState() != 0) {
                    return false;
                }
                synchronized (standardWifiEntry3) {
                    z = standardWifiEntry3.mIsUserShareable;
                }
                return z;
            case 9:
                return ((PasspointWifiEntry) obj).getConnectedState() == 0;
            case 10:
                OsuWifiEntry osuWifiEntry = (OsuWifiEntry) obj;
                if (osuWifiEntry.getConnectedState() != 0) {
                    return false;
                }
                synchronized (osuWifiEntry) {
                    z2 = osuWifiEntry.mIsAlreadyProvisioned;
                }
                return !z2;
            case 11:
                return ((WifiEntry) obj).getConnectedState() == 0;
            default:
                return ((OsuWifiEntry) ((Map.Entry) obj).getValue()).mLevel == -1;
        }
        return !isEmpty;
    }
}
