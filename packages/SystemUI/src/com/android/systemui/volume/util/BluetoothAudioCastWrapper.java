package com.android.systemui.volume.util;

import android.content.Context;
import com.samsung.android.bluetooth.SemBluetoothAudioCast;
import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import com.samsung.android.bluetooth.SemBluetoothCastProfile;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BluetoothAudioCastWrapper {
    public final BluetoothAudioCastWrapper$audioCastProfileListener$1 audioCastProfileListener;
    public SemBluetoothAudioCast service;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.volume.util.BluetoothAudioCastWrapper$audioCastProfileListener$1, com.samsung.android.bluetooth.SemBluetoothCastProfile$BluetoothCastProfileListener] */
    public BluetoothAudioCastWrapper(Context context) {
        ?? r0 = new SemBluetoothCastProfile.BluetoothCastProfileListener() { // from class: com.android.systemui.volume.util.BluetoothAudioCastWrapper$audioCastProfileListener$1
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
        };
        this.audioCastProfileListener = r0;
        SemBluetoothAudioCast.getProxy(context, (SemBluetoothCastProfile.BluetoothCastProfileListener) r0);
    }

    public final String getCastDeviceConnectedName() {
        List connectedDevices;
        SemBluetoothCastDevice semBluetoothCastDevice;
        String deviceName;
        SemBluetoothAudioCast semBluetoothAudioCast = this.service;
        return (semBluetoothAudioCast == null || (connectedDevices = semBluetoothAudioCast.getConnectedDevices()) == null || (semBluetoothCastDevice = (SemBluetoothCastDevice) CollectionsKt___CollectionsKt.firstOrNull(connectedDevices)) == null || (deviceName = semBluetoothCastDevice.getDeviceName()) == null) ? "" : deviceName;
    }
}
