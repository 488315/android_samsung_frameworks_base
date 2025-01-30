package com.android.wifitrackerlib;

import com.android.wifitrackerlib.WifiEntry;
import com.samsung.android.wifitrackerlib.SemWifiEntryFilter;
import java.util.ArrayList;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda11 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiPickerTracker f$0;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda11(WifiPickerTracker wifiPickerTracker, int i) {
        this.$r8$classId = i;
        this.f$0 = wifiPickerTracker;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        WifiEntry.ConnectedInfo connectedInfo;
        WifiEntry.ConnectedInfo connectedInfo2;
        boolean z;
        boolean z2 = true;
        switch (this.$r8$classId) {
            case 0:
                WifiPickerTracker wifiPickerTracker = this.f$0;
                StandardWifiEntry standardWifiEntry = (StandardWifiEntry) obj;
                wifiPickerTracker.getClass();
                synchronized (standardWifiEntry) {
                    z = standardWifiEntry.mIsUserShareable;
                }
                if (!z) {
                    synchronized (wifiPickerTracker.mLock) {
                        z2 = ((ArrayList) wifiPickerTracker.mActiveWifiEntries).contains(standardWifiEntry);
                    }
                }
                return z2;
            default:
                WifiEntry wifiEntry = (WifiEntry) obj;
                SemWifiEntryFilter semWifiEntryFilter = this.f$0.mSemFilter;
                semWifiEntryFilter.getClass();
                synchronized (wifiEntry) {
                    connectedInfo = (wifiEntry.getConnectedState() == 2 && (connectedInfo2 = wifiEntry.mConnectedInfo) != null) ? new WifiEntry.ConnectedInfo(connectedInfo2) : null;
                }
                if (connectedInfo != null) {
                    if (!semWifiEntryFilter.DISPLAY_SSID_STATUS_BAR_INFO || !"Swisscom".equals(wifiEntry.getSsid())) {
                        return true;
                    }
                } else if (!semWifiEntryFilter.CSC_WIFI_SUPPORT_VZW_EAP_AKA || !"VerizonWiFi".equals(wifiEntry.getSsid())) {
                    return true;
                }
                return false;
        }
    }
}
