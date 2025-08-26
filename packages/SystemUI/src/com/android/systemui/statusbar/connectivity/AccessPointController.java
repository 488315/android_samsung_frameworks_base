package com.android.systemui.statusbar.connectivity;

import android.content.Intent;
import java.util.List;

/* loaded from: classes3.dex */
public interface AccessPointController {

    public interface AccessPointCallback {
        void onAccessPointsChanged(List list);

        void onSettingsActivityTriggered(Intent intent);

        void onWifiScan(boolean z);
    }

    public interface WifiApBleStateChangeCallback {
    }
}
