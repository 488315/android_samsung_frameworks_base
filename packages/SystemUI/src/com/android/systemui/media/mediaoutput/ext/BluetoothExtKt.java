package com.android.systemui.media.mediaoutput.ext;

import android.bluetooth.BluetoothDevice;
import com.samsung.android.bluetooth.SmepTag;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class BluetoothExtKt {
    public static final int getBatteryByMetadata(BluetoothDevice bluetoothDevice, SmepTag smepTag) {
        int tag = smepTag.getTag();
        byte[] semGetMetadata = bluetoothDevice.semGetMetadata(new byte[]{(byte) tag, (byte) (tag >> 8)});
        if (semGetMetadata == null) {
            return -1;
        }
        if (semGetMetadata.length <= 3) {
            semGetMetadata = null;
        }
        if (semGetMetadata != null) {
            return semGetMetadata[3];
        }
        return -1;
    }
}
