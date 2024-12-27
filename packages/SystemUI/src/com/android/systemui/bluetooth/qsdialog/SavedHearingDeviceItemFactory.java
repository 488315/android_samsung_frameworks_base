package com.android.systemui.bluetooth.qsdialog;

import android.content.Context;
import android.media.AudioManager;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.flags.Flags;

public final class SavedHearingDeviceItemFactory extends SavedDeviceItemFactory {
    @Override // com.android.systemui.bluetooth.qsdialog.SavedDeviceItemFactory, com.android.systemui.bluetooth.qsdialog.DeviceItemFactory
    public final boolean isFilterMatched(Context context, CachedBluetoothDevice cachedBluetoothDevice, AudioManager audioManager) {
        Flags.enableHideExclusivelyManagedBluetoothDevice();
        return !BluetoothUtils.isExclusivelyManagedBluetoothDevice(context, cachedBluetoothDevice.mDevice) && cachedBluetoothDevice.isHearingAidDevice() && cachedBluetoothDevice.mBondState == 12 && !cachedBluetoothDevice.isConnected();
    }
}
