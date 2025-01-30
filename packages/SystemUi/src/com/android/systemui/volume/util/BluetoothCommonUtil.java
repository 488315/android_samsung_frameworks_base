package com.android.systemui.volume.util;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BluetoothCommonUtil {
    public static final BluetoothCommonUtil INSTANCE = new BluetoothCommonUtil();

    private BluetoothCommonUtil() {
    }

    public static List connectedDevices(BluetoothProfile bluetoothProfile) {
        List<BluetoothDevice> connectedDevices = bluetoothProfile.getConnectedDevices();
        return connectedDevices == null ? EmptyList.INSTANCE : connectedDevices;
    }

    public static List mapNames(List list) {
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String semGetAliasName = ((BluetoothDevice) it.next()).semGetAliasName();
            if (semGetAliasName == null) {
                semGetAliasName = "";
            }
            arrayList.add(semGetAliasName);
        }
        return arrayList;
    }
}
