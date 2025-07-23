package com.android.systemui.media.mediaoutput.ext;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.text.TextUtils;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.vector.ImageVector;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.common.DeviceUtils;
import com.android.systemui.media.mediaoutput.ext.MultiSequenceString;
import com.android.systemui.media.mediaoutput.icons.Icons;
import com.android.systemui.media.mediaoutput.icons.device.Buds3LKt;
import com.android.systemui.media.mediaoutput.icons.device.Buds3ProLKt;
import com.android.systemui.media.mediaoutput.icons.device.EarphoneBuds3Kt;
import com.android.systemui.media.mediaoutput.icons.device.TrueWirelessEarbudsKt;
import com.android.systemui.media.mediaoutput.icons.device.TwsKt;
import com.samsung.android.bluetooth.SmepTag;
import com.samsung.android.settingslib.bluetooth.ManufacturerData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import kotlin.Triple;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.EmptySet;
import kotlin.collections.SetsKt___SetsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class CachedBluetoothDeviceExtKt {
    public static final List getAllAddresses(CachedBluetoothDevice cachedBluetoothDevice) {
        Set set = ArraysKt___ArraysKt.toSet(new CachedBluetoothDevice[]{cachedBluetoothDevice, cachedBluetoothDevice.mSubDevice});
        Set set2 = cachedBluetoothDevice.mMemberDevices;
        List filterNotNull = CollectionsKt___CollectionsKt.filterNotNull(SetsKt___SetsKt.plus(set, set2 != null ? set2 : EmptySet.INSTANCE));
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(filterNotNull, 10));
        Iterator it = filterNotNull.iterator();
        while (it.hasNext()) {
            arrayList.add(((CachedBluetoothDevice) it.next()).mDevice.getAddress());
        }
        return arrayList;
    }

    public static final MultiSequenceString getBatteryDescription(CachedBluetoothDevice cachedBluetoothDevice) {
        CachedBluetoothDevice cachedBluetoothDevice2 = isBudsDevice(cachedBluetoothDevice) ? cachedBluetoothDevice : null;
        if (cachedBluetoothDevice2 != null) {
            int batteryByMetadata = BluetoothExtKt.getBatteryByMetadata(cachedBluetoothDevice2.mDevice, SmepTag.STATE_BATTERY_L);
            int batteryByMetadata2 = BluetoothExtKt.getBatteryByMetadata(cachedBluetoothDevice2.mDevice, SmepTag.STATE_BATTERY_R);
            int batteryByMetadata3 = BluetoothExtKt.getBatteryByMetadata(cachedBluetoothDevice2.mDevice, SmepTag.STATE_BATTERY_CRADLE);
            TooltipPopup$$ExternalSyntheticOutline0.m(batteryByMetadata3, "CachedBluetoothDeviceExt", MutableObjectList$$ExternalSyntheticOutline0.m(batteryByMetadata, batteryByMetadata2, "budsBattery - left = ", ",  right = ", ", cradle = "));
            if (batteryByMetadata < 0 && batteryByMetadata2 < 0) {
                cachedBluetoothDevice2 = null;
            }
            Triple triple = cachedBluetoothDevice2 != null ? new Triple(Integer.valueOf(batteryByMetadata), Integer.valueOf(batteryByMetadata2), Integer.valueOf(batteryByMetadata3)) : null;
            if (triple != null) {
                int intValue = ((Number) triple.component1()).intValue();
                int intValue2 = ((Number) triple.component2()).intValue();
                int intValue3 = ((Number) triple.component3()).intValue();
                ArrayList arrayList = new ArrayList();
                DeviceUtils.INSTANCE.getClass();
                boolean z = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
                arrayList.addAll(Collections.singletonList(intValue >= 0 ? (intValue2 < 0 || Math.abs(intValue2 - intValue) >= 15) ? intValue2 >= 0 ? z ? MutableVectorKt$$ExternalSyntheticOutline0.m(intValue2, intValue, "R ", "% • L ", "%") : MutableVectorKt$$ExternalSyntheticOutline0.m(intValue, intValue2, "L ", "% • R ", "%") : ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(intValue, "L ", "%") : z ? ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(Math.min(intValue2, intValue), "R • L ", "%") : ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(Math.min(intValue2, intValue), "L • R ", "%") : ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(intValue2, "R ", "%")));
                if (1 <= intValue3 && intValue3 < 101) {
                    arrayList.addAll(Arrays.asList(" | ", new ResourceString(R.string.battery_case, null, 2, null), intValue3 + "%"));
                }
                return new MultiSequenceString(arrayList, null, 2, null);
            }
        }
        int batteryLevel = cachedBluetoothDevice.mDevice.getBatteryLevel();
        Integer valueOf = Integer.valueOf(batteryLevel);
        if (batteryLevel <= 0) {
            valueOf = null;
        }
        if (valueOf == null) {
            return null;
        }
        int intValue4 = valueOf.intValue();
        MultiSequenceString.Companion companion = MultiSequenceString.Companion;
        CharSequence[] charSequenceArr = {new ResourceString(R.string.battery, null, 2, null), intValue4 + "%"};
        companion.getClass();
        return new MultiSequenceString(ArraysKt___ArraysKt.toList(charSequenceArr), null, 2, null);
    }

    public static final boolean isActiveDeviceWithMembers(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        int i2;
        List list;
        if (i == 8) {
            i2 = 2;
        } else if (i == 23) {
            i2 = 21;
        } else {
            if (i != 26 && i != 27) {
                return false;
            }
            i2 = 22;
        }
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        List activeDevices = defaultAdapter != null ? defaultAdapter.getActiveDevices(i2) : null;
        if (activeDevices != null) {
            List filterNotNull = CollectionsKt___CollectionsKt.filterNotNull(activeDevices);
            list = new ArrayList();
            Iterator it = filterNotNull.iterator();
            while (it.hasNext()) {
                String address = ((BluetoothDevice) it.next()).getAddress();
                if (address != null) {
                    list.add(address);
                }
            }
        } else {
            list = EmptyList.INSTANCE;
        }
        Set set = ArraysKt___ArraysKt.toSet(new CachedBluetoothDevice[]{cachedBluetoothDevice, cachedBluetoothDevice.mSubDevice});
        Set set2 = cachedBluetoothDevice.mMemberDevices;
        List filterNotNull2 = CollectionsKt___CollectionsKt.filterNotNull(SetsKt___SetsKt.plus(set, set2 != null ? set2 : EmptySet.INSTANCE));
        if (filterNotNull2.isEmpty()) {
            return false;
        }
        Iterator it2 = filterNotNull2.iterator();
        while (it2.hasNext()) {
            if (list.contains(((CachedBluetoothDevice) it2.next()).mDevice.getAddress())) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isBudsDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        int i;
        ImageVector imageVector;
        ManufacturerData manufacturerData = cachedBluetoothDevice.mManufacturerData;
        if (manufacturerData != null) {
            byte[] bArr = manufacturerData.mData.mDeviceId;
            i = (bArr[1] & 255) | ((bArr[0] & 255) << 8);
        } else {
            i = -1;
        }
        if (257 <= i && i < 278) {
            Icons.Device device = Icons.Device.INSTANCE;
            imageVector = (ImageVector) TrueWirelessEarbudsKt.TrueWirelessEarbuds$delegate.getValue();
        } else if (278 <= i && i < 298) {
            Icons.Device device2 = Icons.Device.INSTANCE;
            imageVector = (ImageVector) EarphoneBuds3Kt.EarphoneBuds3$delegate.getValue();
        } else if ((298 <= i && i < 313) || ((313 <= i && i < 325) || ((325 <= i && i < 330) || (330 <= i && i < 333)))) {
            Icons.Device device3 = Icons.Device.INSTANCE;
            imageVector = (ImageVector) TwsKt.Tws$delegate.getValue();
        } else if (333 <= i && i < 340) {
            Icons.Device device4 = Icons.Device.INSTANCE;
            imageVector = (ImageVector) Buds3LKt.Buds3L$delegate.getValue();
        } else if (340 <= i && i < 347) {
            Icons.Device device5 = Icons.Device.INSTANCE;
            imageVector = (ImageVector) Buds3ProLKt.Buds3ProL$delegate.getValue();
        } else if (347 > i || i >= 355) {
            imageVector = null;
        } else {
            Icons.Device device6 = Icons.Device.INSTANCE;
            imageVector = (ImageVector) Buds3ProLKt.Buds3ProL$delegate.getValue();
        }
        return imageVector != null;
    }

    public static final boolean isConnectedWithMembers(CachedBluetoothDevice cachedBluetoothDevice) {
        Set set = ArraysKt___ArraysKt.toSet(new CachedBluetoothDevice[]{cachedBluetoothDevice, cachedBluetoothDevice.mSubDevice});
        Set set2 = cachedBluetoothDevice.mMemberDevices;
        List<CachedBluetoothDevice> filterNotNull = CollectionsKt___CollectionsKt.filterNotNull(SetsKt___SetsKt.plus(set, set2 != null ? set2 : EmptySet.INSTANCE));
        if (filterNotNull.isEmpty()) {
            return false;
        }
        for (CachedBluetoothDevice cachedBluetoothDevice2 : filterNotNull) {
            if (cachedBluetoothDevice2 != null ? cachedBluetoothDevice2.isConnected() : false) {
                return true;
            }
        }
        return false;
    }
}
