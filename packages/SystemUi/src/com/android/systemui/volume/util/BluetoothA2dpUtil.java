package com.android.systemui.volume.util;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothDevice;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BluetoothA2dpUtil {
    public static final BluetoothA2dpUtil INSTANCE = new BluetoothA2dpUtil();

    private BluetoothA2dpUtil() {
    }

    public static List getOrderConnectedDevices(BluetoothA2dp bluetoothA2dp) {
        BluetoothCommonUtil.INSTANCE.getClass();
        List connectedDevices = BluetoothCommonUtil.connectedDevices(bluetoothA2dp);
        BluetoothDevice activeDevice = bluetoothA2dp.getActiveDevice();
        if (activeDevice == null) {
            return connectedDevices;
        }
        ArrayList arrayList = new ArrayList(connectedDevices);
        if (arrayList.contains(activeDevice)) {
            arrayList.remove(arrayList.indexOf(activeDevice));
            arrayList.add(0, activeDevice);
        }
        return CollectionsKt___CollectionsKt.toList(arrayList);
    }
}
