package com.android.wifitrackerlib;

import android.net.wifi.hotspot2.PasspointConfiguration;
import android.util.ArrayMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda7 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiPickerTracker f$0;
    public final /* synthetic */ Map f$1;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda7(WifiPickerTracker wifiPickerTracker, Map map, int i) {
        this.$r8$classId = i;
        this.f$0 = wifiPickerTracker;
        this.f$1 = map;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                WifiPickerTracker wifiPickerTracker = this.f$0;
                Map map = this.f$1;
                StandardWifiEntry standardWifiEntry = (StandardWifiEntry) obj;
                standardWifiEntry.updateConfig((List) ((ArrayMap) wifiPickerTracker.mStandardWifiConfigCache).get(standardWifiEntry.mKey));
                standardWifiEntry.semUpdateSemWifiConfig(map);
                return;
            default:
                WifiPickerTracker wifiPickerTracker2 = this.f$0;
                Map map2 = this.f$1;
                OsuWifiEntry osuWifiEntry = (OsuWifiEntry) obj;
                wifiPickerTracker2.getClass();
                PasspointConfiguration passpointConfiguration = (PasspointConfiguration) map2.get(osuWifiEntry.mOsuProvider);
                if (passpointConfiguration == null) {
                    synchronized (osuWifiEntry) {
                        osuWifiEntry.mIsAlreadyProvisioned = false;
                    }
                    return;
                }
                synchronized (osuWifiEntry) {
                    osuWifiEntry.mIsAlreadyProvisioned = true;
                }
                PasspointWifiEntry passpointWifiEntry = (PasspointWifiEntry) ((ArrayMap) wifiPickerTracker2.mPasspointWifiEntryCache).get(PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(passpointConfiguration.getUniqueId()));
                if (passpointWifiEntry == null) {
                    return;
                }
                synchronized (passpointWifiEntry) {
                    passpointWifiEntry.mOsuWifiEntry = osuWifiEntry;
                    synchronized (osuWifiEntry) {
                        osuWifiEntry.mListener = passpointWifiEntry;
                    }
                }
                return;
        }
    }
}
