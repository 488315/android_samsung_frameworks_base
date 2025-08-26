package com.android.systemui.bluetooth.qsdialog;

import android.content.Context;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;

/* loaded from: classes.dex */
public final class SavedHearingDeviceItemFactory extends SavedDeviceItemFactory {
    @Override // com.android.systemui.bluetooth.qsdialog.SavedDeviceItemFactory, com.android.systemui.bluetooth.qsdialog.DeviceItemFactory
    public final boolean isFilterMatched(Context context, CachedBluetoothDevice cachedBluetoothDevice, boolean z) {
        return !BluetoothUtils.isExclusivelyManagedBluetoothDevice(context, cachedBluetoothDevice.mDevice) && cachedBluetoothDevice.isHearingDevice() && cachedBluetoothDevice.mBondState == 12 && !cachedBluetoothDevice.isConnected();
    }
}
