package com.android.systemui.volume.util;

import android.bluetooth.BluetoothDevice;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BluetoothCommonUtil {
    public static final BluetoothCommonUtil INSTANCE = new BluetoothCommonUtil();

    private BluetoothCommonUtil() {
    }

    public static List mapNames(List list) {
        List list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        Iterator it = list2.iterator();
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
