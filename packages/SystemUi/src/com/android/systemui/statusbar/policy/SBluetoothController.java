package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.BluetoothController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface SBluetoothController extends BluetoothController {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface SCallback extends BluetoothController.Callback {
        void onBluetoothScanStateChanged(boolean z);

        default void onMusicShareDiscoveryStateChanged(boolean z) {
        }
    }
}
