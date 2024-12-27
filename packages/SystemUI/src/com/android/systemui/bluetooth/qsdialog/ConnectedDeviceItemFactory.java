package com.android.systemui.bluetooth.qsdialog;

import android.content.Context;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.systemui.R;
import com.android.systemui.bluetooth.qsdialog.DeviceItemFactory;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ConnectedDeviceItemFactory extends DeviceItemFactory {
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
        String str = connectionSummary;
        int i = cachedBluetoothDevice.isBusy() ? R.drawable.bluetooth_tile_dialog_bg_off_busy : R.drawable.bluetooth_tile_dialog_bg_off;
        String string = context.getString(R.string.accessibility_quick_settings_bluetooth_device_tap_to_disconnect);
        DeviceItemFactory.Companion.getClass();
        return DeviceItemFactory.Companion.createDeviceItem(context, cachedBluetoothDevice, deviceItemType, str, i, string, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
    @Override // com.android.systemui.bluetooth.qsdialog.DeviceItemFactory
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isFilterMatched(android.content.Context r3, com.android.settingslib.bluetooth.CachedBluetoothDevice r4, android.media.AudioManager r5) {
        /*
            r2 = this;
            com.android.settingslib.flags.Flags.enableHideExclusivelyManagedBluetoothDevice()
            android.bluetooth.BluetoothDevice r2 = r4.mDevice
            boolean r2 = com.android.settingslib.bluetooth.BluetoothUtils.isExclusivelyManagedBluetoothDevice(r3, r2)
            r3 = 0
            if (r2 != 0) goto L53
            int r2 = r5.getMode()
            r5 = 1
            r0 = 2
            if (r2 == r5) goto L1c
            if (r2 == r0) goto L1c
            r1 = 3
            if (r2 != r1) goto L1a
            goto L1c
        L1a:
            r2 = r0
            goto L1d
        L1c:
            r2 = r5
        L1d:
            boolean r1 = com.android.settingslib.bluetooth.BluetoothUtils.isDeviceConnected(r4)
            if (r1 == 0) goto L4f
            boolean r1 = r4.isConnectedAshaHearingAidDevice()
            if (r1 != 0) goto L4f
            boolean r1 = r4.isConnectedLeAudioDevice()
            if (r1 == 0) goto L30
            goto L4f
        L30:
            if (r2 == r5) goto L3b
            if (r2 == r0) goto L35
            goto L4f
        L35:
            boolean r2 = r4.isConnectedA2dpDevice()
        L39:
            r2 = r2 ^ r5
            goto L50
        L3b:
            com.android.settingslib.bluetooth.LocalBluetoothProfileManager r2 = r4.mProfileManager
            if (r2 != 0) goto L41
        L3f:
            r2 = r3
            goto L39
        L41:
            com.android.settingslib.bluetooth.HeadsetProfile r2 = r2.mHeadsetProfile
            if (r2 == 0) goto L3f
            android.bluetooth.BluetoothDevice r4 = r4.mDevice
            int r2 = r2.getConnectionStatus(r4)
            if (r2 != r0) goto L3f
            r2 = r5
            goto L39
        L4f:
            r2 = r3
        L50:
            if (r2 == 0) goto L53
            r3 = r5
        L53:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bluetooth.qsdialog.ConnectedDeviceItemFactory.isFilterMatched(android.content.Context, com.android.settingslib.bluetooth.CachedBluetoothDevice, android.media.AudioManager):boolean");
    }
}
