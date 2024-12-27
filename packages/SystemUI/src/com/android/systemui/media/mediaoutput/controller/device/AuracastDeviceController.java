package com.android.systemui.media.mediaoutput.controller.device;

import android.media.AudioManager;
import android.util.Log;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class AuracastDeviceController extends DeviceController {
    public final AudioManager audioManager;
    public final LocalBluetoothManager localBluetoothManager;

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
