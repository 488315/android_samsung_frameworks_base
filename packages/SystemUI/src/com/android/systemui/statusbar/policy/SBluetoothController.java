package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.BluetoothController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public interface SBluetoothController extends BluetoothController {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface SCallback extends BluetoothController.Callback {
        void onBluetoothScanStateChanged(boolean z);

        default void onMusicShareStateChanged() {
        }

        default void onMusicShareDiscoveryStateChanged(boolean z) {
        }
    }
}
