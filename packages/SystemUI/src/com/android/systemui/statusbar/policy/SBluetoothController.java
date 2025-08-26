package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.BluetoothController;

/* loaded from: classes3.dex */
public interface SBluetoothController extends BluetoothController {

    public interface SCallback extends BluetoothController.Callback {
        void onBluetoothScanStateChanged(boolean z);

        default void onMusicShareDiscoveryStateChanged(boolean z) {
        }

        default void onMusicShareStateChanged() {
        }
    }
}
