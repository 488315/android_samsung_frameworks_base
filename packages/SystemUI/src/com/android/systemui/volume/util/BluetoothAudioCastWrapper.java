package com.android.systemui.volume.util;

import android.content.Context;
import com.samsung.android.bluetooth.SemBluetoothAudioCast;
import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import com.samsung.android.bluetooth.SemBluetoothCastProfile;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class BluetoothAudioCastWrapper {
    public final BluetoothAudioCastWrapper$audioCastProfileListener$1 audioCastProfileListener;
    public SemBluetoothAudioCast service;

    public BluetoothAudioCastWrapper(Context context) {
        SemBluetoothAudioCast.getProxy(context, new SemBluetoothCastProfile.BluetoothCastProfileListener() { // from class: com.android.systemui.volume.util.BluetoothAudioCastWrapper$audioCastProfileListener$1
            public final void onServiceConnected(SemBluetoothCastProfile semBluetoothCastProfile) {
                BluetoothAudioCastWrapper.this.service = (SemBluetoothAudioCast) semBluetoothCastProfile;
            }

            public final void onServiceDisconnected() {
                BluetoothAudioCastWrapper bluetoothAudioCastWrapper = BluetoothAudioCastWrapper.this;
                SemBluetoothAudioCast semBluetoothAudioCast = bluetoothAudioCastWrapper.service;
                if (semBluetoothAudioCast != null) {
                    semBluetoothAudioCast.closeProxy();
                    bluetoothAudioCastWrapper.service = null;
                }
            }
        });
    }

    public final String getCastDeviceConnectedName() {
        List connectedDevices;
        SemBluetoothCastDevice semBluetoothCastDevice;
        SemBluetoothAudioCast semBluetoothAudioCast = this.service;
        String deviceName = (semBluetoothAudioCast == null || (connectedDevices = semBluetoothAudioCast.getConnectedDevices()) == null || (semBluetoothCastDevice = (SemBluetoothCastDevice) CollectionsKt___CollectionsKt.firstOrNull(connectedDevices)) == null) ? null : semBluetoothCastDevice.getDeviceName();
        return deviceName == null ? "" : deviceName;
    }
}
