package com.android.systemui.bluetooth.qsdialog;

import android.content.Context;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;

/* loaded from: classes.dex */
public final class ConnectedHearingDeviceItemFactory extends ConnectedDeviceItemFactory {
    @Override // com.android.systemui.bluetooth.qsdialog.ConnectedDeviceItemFactory, com.android.systemui.bluetooth.qsdialog.DeviceItemFactory
    public final boolean isFilterMatched(Context context, CachedBluetoothDevice cachedBluetoothDevice, boolean z) {
        return cachedBluetoothDevice.isHearingDevice() && cachedBluetoothDevice.mBondState == 12 && cachedBluetoothDevice.mDevice.isConnected();
    }
}
