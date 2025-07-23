package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.BluetoothController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface SBluetoothController extends BluetoothController {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface SCallback extends BluetoothController.Callback {
        void onBluetoothScanStateChanged(boolean z);

        default void onMusicShareDiscoveryStateChanged(boolean z) {
        }

        default void onMusicShareStateChanged() {
        }
    }
}
