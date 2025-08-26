package com.android.wifitrackerlib;

import java.util.Map;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda38 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiPickerTracker f$0;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda38(WifiPickerTracker wifiPickerTracker, int i) {
        this.$r8$classId = i;
        this.f$0 = wifiPickerTracker;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        WifiPickerTracker wifiPickerTracker = this.f$0;
        switch (i) {
            case 0:
                ((StandardWifiEntry) obj).mSemFlags.networkScoringUiEnabled = wifiPickerTracker.mNetworkScoringUiEnabled;
                break;
            case 1:
                wifiPickerTracker.getClass();
                ((OsuWifiEntry) ((Map.Entry) obj).getValue()).mSemFlags.networkScoringUiEnabled = wifiPickerTracker.mNetworkScoringUiEnabled;
                break;
            case 2:
                wifiPickerTracker.getClass();
                ((PasspointWifiEntry) ((Map.Entry) obj).getValue()).mSemFlags.networkScoringUiEnabled = wifiPickerTracker.mNetworkScoringUiEnabled;
                break;
            default:
                ((StandardWifiEntry) obj).mSemFlags.networkScoringUiEnabled = wifiPickerTracker.mNetworkScoringUiEnabled;
                break;
        }
    }
}
