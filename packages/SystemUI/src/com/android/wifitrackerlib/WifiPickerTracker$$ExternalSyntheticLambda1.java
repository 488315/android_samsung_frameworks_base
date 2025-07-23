package com.android.wifitrackerlib;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.net.wifi.sharedconnectivity.app.HotspotNetwork;
import android.net.wifi.sharedconnectivity.app.KnownNetwork;
import com.android.wifitrackerlib.StandardWifiEntry;
import java.util.ArrayList;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return new StandardWifiEntry.ScanResultKey((ScanResult) obj);
            case 1:
                return ((StandardWifiEntry) obj).mKey.mScanResultKey;
            case 2:
                return Long.valueOf(((HotspotNetwork) obj).getDeviceId());
            case 3:
                return (HotspotNetwork) obj;
            case 4:
                KnownNetwork knownNetwork = (KnownNetwork) obj;
                return new StandardWifiEntry.ScanResultKey(knownNetwork.getSsid(), new ArrayList(knownNetwork.getSecurityTypes()));
            case 5:
                return Integer.valueOf(((WifiConfiguration) obj).networkId);
            case 6:
                return PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(((PasspointConfiguration) obj).getUniqueId());
            case 7:
                return new StandardWifiEntry.StandardWifiEntryKey((WifiConfiguration) obj);
            case 8:
                return (KnownNetwork) obj;
            default:
                return Integer.valueOf(((WifiConfiguration) obj).networkId);
        }
    }
}
