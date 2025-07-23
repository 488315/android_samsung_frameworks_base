package com.android.systemui.bluetooth.qsdialog;

import android.content.Context;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.systemui.R;
import com.android.systemui.bluetooth.qsdialog.DeviceItemFactory;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ActiveMediaDeviceItemFactory extends DeviceItemFactory {
    @Override // com.android.systemui.bluetooth.qsdialog.DeviceItemFactory
    public final DeviceItem create(Context context, CachedBluetoothDevice cachedBluetoothDevice) {
        DeviceItemType deviceItemType = DeviceItemType.ACTIVE_MEDIA_BLUETOOTH_DEVICE;
        String connectionSummary = cachedBluetoothDevice.getConnectionSummary();
        if (connectionSummary == null) {
            connectionSummary = "";
        }
        String string = context.getString(R.string.accessibility_quick_settings_bluetooth_device_tap_to_disconnect);
        return DeviceItemFactory.Companion.createDeviceItem$default(DeviceItemFactory.Companion, cachedBluetoothDevice, deviceItemType, connectionSummary, R.drawable.settingslib_switch_bar_bg_on, string, true);
    }

    @Override // com.android.systemui.bluetooth.qsdialog.DeviceItemFactory
    public boolean isFilterMatched(Context context, CachedBluetoothDevice cachedBluetoothDevice, boolean z) {
        return BluetoothUtils.isActiveMediaDevice(cachedBluetoothDevice) && BluetoothUtils.isAvailableMediaBluetoothDevice(cachedBluetoothDevice, z);
    }
}
