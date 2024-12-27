package com.android.systemui.bluetooth.qsdialog;

import android.content.Context;
import android.media.AudioManager;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.systemui.R;
import com.android.systemui.bluetooth.qsdialog.DeviceItemFactory;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class AvailableMediaDeviceItemFactory extends DeviceItemFactory {
    @Override // com.android.systemui.bluetooth.qsdialog.DeviceItemFactory
    public final DeviceItem create(Context context, CachedBluetoothDevice cachedBluetoothDevice) {
        DeviceItemType deviceItemType = DeviceItemType.AVAILABLE_MEDIA_BLUETOOTH_DEVICE;
        String connectionSummary = cachedBluetoothDevice.getConnectionSummary();
        if (connectionSummary == null || connectionSummary.length() == 0) {
            connectionSummary = null;
        }
        if (connectionSummary == null) {
            connectionSummary = context.getString(R.string.quick_settings_bluetooth_device_connected);
        }
        String str = connectionSummary;
        int i = cachedBluetoothDevice.isBusy() ? R.drawable.bluetooth_tile_dialog_bg_off_busy : R.drawable.bluetooth_tile_dialog_bg_off;
        String string = context.getString(R.string.accessibility_quick_settings_bluetooth_device_tap_to_activate);
        DeviceItemFactory.Companion.getClass();
        return DeviceItemFactory.Companion.createDeviceItem(context, cachedBluetoothDevice, deviceItemType, str, i, string, false);
    }

    @Override // com.android.systemui.bluetooth.qsdialog.DeviceItemFactory
    public boolean isFilterMatched(Context context, CachedBluetoothDevice cachedBluetoothDevice, AudioManager audioManager) {
        return !BluetoothUtils.isActiveMediaDevice(cachedBluetoothDevice) && BluetoothUtils.isAvailableMediaBluetoothDevice(cachedBluetoothDevice, audioManager);
    }
}
