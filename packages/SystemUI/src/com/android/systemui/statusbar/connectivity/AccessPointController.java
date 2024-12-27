package com.android.systemui.statusbar.connectivity;

import android.content.Intent;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface AccessPointController {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface AccessPointCallback {
        void onAccessPointsChanged(List list);

        void onSettingsActivityTriggered(Intent intent);

        void onWifiScan(boolean z);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface WifiApBleStateChangeCallback {
    }
}
