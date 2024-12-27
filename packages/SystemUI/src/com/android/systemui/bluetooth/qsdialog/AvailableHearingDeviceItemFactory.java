package com.android.systemui.bluetooth.qsdialog;

import android.content.Context;
import android.media.AudioManager;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;

public final class AvailableHearingDeviceItemFactory extends AvailableMediaDeviceItemFactory {
    @Override // com.android.systemui.bluetooth.qsdialog.AvailableMediaDeviceItemFactory, com.android.systemui.bluetooth.qsdialog.DeviceItemFactory
    public final boolean isFilterMatched(Context context, CachedBluetoothDevice cachedBluetoothDevice, AudioManager audioManager) {
        return !BluetoothUtils.isActiveMediaDevice(cachedBluetoothDevice) && BluetoothUtils.isAvailableHearingDevice(cachedBluetoothDevice);
    }
}
