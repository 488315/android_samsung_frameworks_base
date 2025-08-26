package com.android.systemui.media.mediaoutput.ext;

import android.bluetooth.BluetoothDevice;
import com.samsung.android.bluetooth.SmepTag;

/* loaded from: classes2.dex */
public abstract class BluetoothExtKt {
    public static final int getBatteryByMetadata(BluetoothDevice bluetoothDevice, SmepTag smepTag) {
        int tag = smepTag.getTag();
        byte[] bArrSemGetMetadata = bluetoothDevice.semGetMetadata(new byte[]{(byte) tag, (byte) (tag >> 8)});
        if (bArrSemGetMetadata == null) {
            return -1;
        }
        if (bArrSemGetMetadata.length <= 3) {
            bArrSemGetMetadata = null;
        }
        if (bArrSemGetMetadata != null) {
            return bArrSemGetMetadata[3];
        }
        return -1;
    }
}
