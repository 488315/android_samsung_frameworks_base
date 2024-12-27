package com.android.systemui.media.mediaoutput.ext;

import android.bluetooth.BluetoothDevice;
import com.samsung.android.bluetooth.SmepTag;

public abstract class BluetoothDeviceExtKt {
    public static final int getBatteryByMetadata(BluetoothDevice bluetoothDevice, SmepTag smepTag) {
        int tag = smepTag.getTag();
        byte[] semGetMetadata = bluetoothDevice.semGetMetadata(new byte[]{(byte) tag, (byte) (tag >> 8)});
        if (semGetMetadata != null) {
            if (semGetMetadata.length <= 3) {
                semGetMetadata = null;
            }
            if (semGetMetadata != null) {
                return semGetMetadata[3];
            }
        }
        return -1;
    }
}
