package com.android.systemui.media.mediaoutput.controller.device;

import android.media.AudioManager;
import android.util.Log;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AuracastDeviceController extends DeviceController {
    public final AudioManager audioManager;
    public final LocalBluetoothManager localBluetoothManager;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public AuracastDeviceController(AudioManager audioManager, LocalBluetoothManager localBluetoothManager) {
        LocalBluetoothProfileManager localBluetoothProfileManager;
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast;
        this.audioManager = audioManager;
        this.localBluetoothManager = localBluetoothManager;
        Log.d("AuracastDeviceController", "init() - " + ((localBluetoothManager == null || (localBluetoothProfileManager = localBluetoothManager.mProfileManager) == null || (localBluetoothLeBroadcast = localBluetoothProfileManager.mLeAudioBroadcast) == null) ? null : Integer.valueOf(localBluetoothLeBroadcast.mBroadcastId)));
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void close() {
        super.close();
        Log.d("AuracastDeviceController", "close()");
    }
}
