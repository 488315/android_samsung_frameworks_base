package com.android.systemui.media.mediaoutput.ext;

import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class CachedBluetoothDeviceExtKt {
    public static final List getAllAddresses(CachedBluetoothDevice cachedBluetoothDevice) {
        List list;
        List singletonList = Collections.singletonList(cachedBluetoothDevice.mDevice.getAddress());
        Set set = cachedBluetoothDevice.mMemberDevices;
        if (set != null) {
            Set set2 = set;
            list = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set2, 10));
            Iterator it = set2.iterator();
            while (it.hasNext()) {
                list.add(((CachedBluetoothDevice) it.next()).mDevice.getAddress());
            }
        } else {
            list = EmptyList.INSTANCE;
        }
        return CollectionsKt___CollectionsKt.plus((Iterable) list, (Collection) singletonList);
    }

    public static final boolean isActiveDeviceWithMembers(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        Set<CachedBluetoothDevice> plus = SetsKt___SetsKt.plus(Collections.singleton(cachedBluetoothDevice), (Iterable) cachedBluetoothDevice.mMemberDevices);
        if (plus.isEmpty()) {
            return false;
        }
        for (CachedBluetoothDevice cachedBluetoothDevice2 : plus) {
            if (i != 8 ? i != 23 ? (i == 26 || i == 27) ? cachedBluetoothDevice.isActiveDevice(22) : false : cachedBluetoothDevice.isActiveDevice(21) : cachedBluetoothDevice.isActiveDevice(2)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isConnectedWithMembers(CachedBluetoothDevice cachedBluetoothDevice) {
        Set plus = SetsKt___SetsKt.plus(Collections.singleton(cachedBluetoothDevice), (Iterable) cachedBluetoothDevice.mMemberDevices);
        if (plus.isEmpty()) {
            return false;
        }
        Iterator it = plus.iterator();
        while (it.hasNext()) {
            if (((CachedBluetoothDevice) it.next()).isConnected()) {
                return true;
            }
        }
        return false;
    }

    public static final String printActiveDevice(final CachedBluetoothDevice cachedBluetoothDevice) {
        return CollectionsKt___CollectionsKt.joinToString$default(CollectionsKt__CollectionsKt.listOf(1, 2, 21, 22), null, null, null, new Function1() { // from class: com.android.systemui.media.mediaoutput.ext.CachedBluetoothDeviceExtKt$printActiveDevice$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                int intValue = ((Number) obj).intValue();
                return intValue + " = " + CachedBluetoothDevice.this.isActiveDevice(intValue);
            }
        }, 31);
    }
}
