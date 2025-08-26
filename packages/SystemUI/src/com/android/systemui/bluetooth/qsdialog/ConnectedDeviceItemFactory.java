package com.android.systemui.bluetooth.qsdialog;

import android.content.Context;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.HeadsetProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.systemui.R;
import com.android.systemui.bluetooth.qsdialog.DeviceItemFactory;

/* loaded from: classes.dex */
public class ConnectedDeviceItemFactory extends DeviceItemFactory {
    @Override // com.android.systemui.bluetooth.qsdialog.DeviceItemFactory
    public final DeviceItem create(Context context, CachedBluetoothDevice cachedBluetoothDevice) {
        DeviceItemType deviceItemType = DeviceItemType.CONNECTED_BLUETOOTH_DEVICE;
        String connectionSummary = cachedBluetoothDevice.getConnectionSummary();
        if (connectionSummary == null || connectionSummary.length() == 0) {
            connectionSummary = null;
        }
        if (connectionSummary == null) {
            connectionSummary = context.getString(R.string.quick_settings_bluetooth_device_connected);
        }
        return DeviceItemFactory.Companion.createDeviceItem$default(DeviceItemFactory.Companion, cachedBluetoothDevice, deviceItemType, connectionSummary, cachedBluetoothDevice.isBusy() ? R.drawable.bluetooth_tile_dialog_bg_off_busy : R.drawable.bluetooth_tile_dialog_bg_off, context.getString(R.string.accessibility_quick_settings_bluetooth_device_tap_to_disconnect), false);
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0045 A[RETURN] */
    @Override // com.android.systemui.bluetooth.qsdialog.DeviceItemFactory
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean isFilterMatched(Context context, CachedBluetoothDevice cachedBluetoothDevice, boolean z) {
        boolean z2;
        HeadsetProfile headsetProfile;
        boolean zIsConnectedA2dpDevice;
        if (!BluetoothUtils.isExclusivelyManagedBluetoothDevice(context, cachedBluetoothDevice.mDevice)) {
            char c = z ? (char) 1 : (char) 2;
            if (!BluetoothUtils.isDeviceConnected(cachedBluetoothDevice) || cachedBluetoothDevice.isConnectedAshaHearingAidDevice() || cachedBluetoothDevice.isConnectedLeAudioDevice()) {
                z2 = false;
                if (z2) {
                    return true;
                }
            } else {
                if (c != 1) {
                    if (c == 2) {
                        zIsConnectedA2dpDevice = cachedBluetoothDevice.isConnectedA2dpDevice();
                    }
                    z2 = false;
                    if (z2) {
                    }
                } else {
                    LocalBluetoothProfileManager localBluetoothProfileManager = cachedBluetoothDevice.mProfileManager;
                    zIsConnectedA2dpDevice = (localBluetoothProfileManager == null || (headsetProfile = localBluetoothProfileManager.mHeadsetProfile) == null || headsetProfile.getConnectionStatus(cachedBluetoothDevice.mDevice) != 2) ? false : true;
                }
                z2 = !zIsConnectedA2dpDevice;
                if (z2) {
                }
            }
        }
        return false;
    }
}
