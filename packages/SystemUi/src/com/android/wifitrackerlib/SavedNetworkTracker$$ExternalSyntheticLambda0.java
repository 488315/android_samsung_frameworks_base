package com.android.wifitrackerlib;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.hotspot2.PasspointConfiguration;
import com.android.wifitrackerlib.StandardWifiEntry;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SavedNetworkTracker$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return new StandardWifiEntry.ScanResultKey((ScanResult) obj);
            case 1:
                return PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(((PasspointConfiguration) obj).getUniqueId());
            default:
                return new StandardWifiEntry.StandardWifiEntryKey((WifiConfiguration) obj);
        }
    }
}
