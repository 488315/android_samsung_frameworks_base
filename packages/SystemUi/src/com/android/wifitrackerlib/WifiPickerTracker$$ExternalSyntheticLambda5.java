package com.android.wifitrackerlib;

import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda5 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiPickerTracker f$0;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda5(WifiPickerTracker wifiPickerTracker, int i) {
        this.$r8$classId = i;
        this.f$0 = wifiPickerTracker;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                WifiPickerTracker wifiPickerTracker = this.f$0;
                wifiPickerTracker.getClass();
                ((PasspointWifiEntry) ((Map.Entry) obj).getValue()).mSemFlags.networkScoringUiEnabled = wifiPickerTracker.mNetworkScoringUiEnabled;
                break;
            case 1:
                ((StandardWifiEntry) obj).mSemFlags.networkScoringUiEnabled = this.f$0.mNetworkScoringUiEnabled;
                break;
            case 2:
                ((StandardWifiEntry) obj).mSemFlags.networkScoringUiEnabled = this.f$0.mNetworkScoringUiEnabled;
                break;
            default:
                WifiPickerTracker wifiPickerTracker2 = this.f$0;
                wifiPickerTracker2.getClass();
                ((OsuWifiEntry) ((Map.Entry) obj).getValue()).mSemFlags.networkScoringUiEnabled = wifiPickerTracker2.mNetworkScoringUiEnabled;
                break;
        }
    }
}
