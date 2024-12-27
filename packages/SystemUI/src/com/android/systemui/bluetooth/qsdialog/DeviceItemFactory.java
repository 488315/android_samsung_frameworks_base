package com.android.systemui.bluetooth.qsdialog;

import android.content.Context;
import android.media.AudioManager;
import android.util.Pair;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import kotlin.jvm.internal.DefaultConstructorMarker;

public abstract class DeviceItemFactory {
    public static final Companion Companion = new Companion(null);

    public final class Companion {
        private Companion() {
        }

        public static DeviceItem createDeviceItem(Context context, CachedBluetoothDevice cachedBluetoothDevice, DeviceItemType deviceItemType, String str, int i, String str2, boolean z) {
            String name = cachedBluetoothDevice.getName();
            Pair btClassDrawableWithDescription = BluetoothUtils.getBtClassDrawableWithDescription(context, cachedBluetoothDevice);
            return new DeviceItem(deviceItemType, cachedBluetoothDevice, name, str, new kotlin.Pair(btClassDrawableWithDescription.first, btClassDrawableWithDescription.second), Integer.valueOf(i), !cachedBluetoothDevice.isBusy(), str2, z);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public abstract DeviceItem create(Context context, CachedBluetoothDevice cachedBluetoothDevice);

    public abstract boolean isFilterMatched(Context context, CachedBluetoothDevice cachedBluetoothDevice, AudioManager audioManager);
}
