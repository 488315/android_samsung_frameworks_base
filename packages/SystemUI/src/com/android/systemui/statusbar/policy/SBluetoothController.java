package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.BluetoothController;

public interface SBluetoothController extends BluetoothController {

    public interface SCallback extends BluetoothController.Callback {
        void onBluetoothScanStateChanged(boolean z);

        default void onMusicShareStateChanged() {
        }

        default void onMusicShareDiscoveryStateChanged(boolean z) {
        }
    }
}
