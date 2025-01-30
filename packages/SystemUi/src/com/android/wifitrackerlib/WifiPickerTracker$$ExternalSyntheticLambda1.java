package com.android.wifitrackerlib;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.net.wifi.sharedconnectivity.app.HotspotNetwork;
import android.net.wifi.sharedconnectivity.app.KnownNetwork;
import com.android.wifitrackerlib.StandardWifiEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return new StandardWifiEntry.ScanResultKey((ScanResult) obj);
            case 1:
                KnownNetwork knownNetwork = (KnownNetwork) obj;
                return new StandardWifiEntry.ScanResultKey(knownNetwork.getSsid(), new ArrayList(knownNetwork.getSecurityTypes()));
            case 2:
                return (KnownNetwork) obj;
            case 3:
                return ((List) obj).stream();
            case 4:
                return Integer.valueOf(((WifiConfiguration) obj).networkId);
            case 5:
                return new StandardWifiEntry.ScanResultKey((ScanResult) obj);
            case 6:
                return new StandardWifiEntry.StandardWifiEntryKey((WifiConfiguration) obj);
            case 7:
                return new StandardWifiEntry.ScanResultKey((ScanResult) obj);
            case 8:
                return Integer.valueOf(((WifiConfiguration) obj).networkId);
            case 9:
                return PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(((PasspointConfiguration) obj).getUniqueId());
            case 10:
                return ((StandardWifiEntry) obj).mKey.mScanResultKey;
            case 11:
                return Long.valueOf(((HotspotNetwork) obj).getDeviceId());
            default:
                return (HotspotNetwork) obj;
        }
    }
}
