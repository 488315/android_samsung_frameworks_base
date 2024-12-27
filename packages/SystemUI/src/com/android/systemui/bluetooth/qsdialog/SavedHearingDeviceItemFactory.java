package com.android.systemui.bluetooth.qsdialog;

import android.content.Context;
import android.media.AudioManager;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.flags.Flags;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class SavedHearingDeviceItemFactory extends SavedDeviceItemFactory {
    @Override // com.android.systemui.bluetooth.qsdialog.SavedDeviceItemFactory, com.android.systemui.bluetooth.qsdialog.DeviceItemFactory
    public final boolean isFilterMatched(Context context, CachedBluetoothDevice cachedBluetoothDevice, AudioManager audioManager) {
        Flags.enableHideExclusivelyManagedBluetoothDevice();
        return !BluetoothUtils.isExclusivelyManagedBluetoothDevice(context, cachedBluetoothDevice.mDevice) && cachedBluetoothDevice.isHearingAidDevice() && cachedBluetoothDevice.mBondState == 12 && !cachedBluetoothDevice.isConnected();
    }
}
