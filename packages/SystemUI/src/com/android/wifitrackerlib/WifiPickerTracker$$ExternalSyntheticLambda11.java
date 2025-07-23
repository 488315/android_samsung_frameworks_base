package com.android.wifitrackerlib;

import android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus;
import com.android.wifitrackerlib.WifiEntry;
import com.samsung.android.wifitrackerlib.SemWifiEntryFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda11 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda11(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean z;
        WifiEntry.ConnectedInfo connectedInfo;
        WifiEntry.ConnectedInfo connectedInfo2;
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                return ((HotspotNetworkEntry) obj).mKey.mDeviceId == ((HotspotNetworkConnectionStatus) obj2).getHotspotNetwork().getDeviceId();
            case 1:
                List list = (List) obj2;
                StandardWifiEntry standardWifiEntry = (StandardWifiEntry) obj;
                synchronized (standardWifiEntry) {
                    z = standardWifiEntry.mIsUserShareable;
                }
                if (z) {
                    return true;
                }
                return ((ArrayList) list).contains(standardWifiEntry);
            default:
                WifiEntry wifiEntry = (WifiEntry) obj;
                SemWifiEntryFilter semWifiEntryFilter = ((WifiPickerTracker) obj2).mSemFilter;
                semWifiEntryFilter.getClass();
                synchronized (wifiEntry) {
                    connectedInfo = (wifiEntry.getConnectedState() == 2 && (connectedInfo2 = wifiEntry.mConnectedInfo) != null) ? new WifiEntry.ConnectedInfo(connectedInfo2) : null;
                }
                return connectedInfo == null ? !(semWifiEntryFilter.CSC_WIFI_SUPPORT_VZW_EAP_AKA && "VerizonWiFi".equals(wifiEntry.getSsid())) : !(semWifiEntryFilter.DISPLAY_SSID_STATUS_BAR_INFO && "Swisscom".equals(wifiEntry.getSsid()));
        }
    }
}
