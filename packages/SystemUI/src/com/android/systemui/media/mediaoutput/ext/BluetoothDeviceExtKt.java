package com.android.systemui.media.mediaoutput.ext;

import android.bluetooth.BluetoothDevice;
import com.samsung.android.bluetooth.SmepTag;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
