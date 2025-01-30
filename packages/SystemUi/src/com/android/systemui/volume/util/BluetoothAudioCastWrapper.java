package com.android.systemui.volume.util;

import android.content.Context;
import com.samsung.android.bluetooth.SemBluetoothAudioCast;
import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import com.samsung.android.bluetooth.SemBluetoothCastProfile;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        SemBluetoothAudioCast semBluetoothAudioCast = this.service;
        String deviceName = (semBluetoothAudioCast == null || (connectedDevices = semBluetoothAudioCast.getConnectedDevices()) == null || (semBluetoothCastDevice = (SemBluetoothCastDevice) CollectionsKt___CollectionsKt.firstOrNull(connectedDevices)) == null) ? null : semBluetoothCastDevice.getDeviceName();
        return deviceName == null ? "" : deviceName;
    }
}
