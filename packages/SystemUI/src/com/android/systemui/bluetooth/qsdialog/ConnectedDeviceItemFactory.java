package com.android.systemui.bluetooth.qsdialog;

import android.content.Context;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.systemui.R;
import com.android.systemui.bluetooth.qsdialog.DeviceItemFactory;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Removed duplicated region for block: B:17:0x0045 A[RETURN] */
    @Override // com.android.systemui.bluetooth.qsdialog.DeviceItemFactory
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isFilterMatched(android.content.Context r3, com.android.settingslib.bluetooth.CachedBluetoothDevice r4, boolean r5) {
        /*
            r2 = this;
            android.bluetooth.BluetoothDevice r2 = r4.mDevice
            boolean r2 = com.android.settingslib.bluetooth.BluetoothUtils.isExclusivelyManagedBluetoothDevice(r3, r2)
            r3 = 0
            if (r2 != 0) goto L46
            r2 = 2
            r0 = 1
            if (r5 == 0) goto Lf
            r5 = r0
            goto L10
        Lf:
            r5 = r2
        L10:
            boolean r1 = com.android.settingslib.bluetooth.BluetoothUtils.isDeviceConnected(r4)
            if (r1 == 0) goto L42
            boolean r1 = r4.isConnectedAshaHearingAidDevice()
            if (r1 != 0) goto L42
            boolean r1 = r4.isConnectedLeAudioDevice()
            if (r1 == 0) goto L23
            goto L42
        L23:
            if (r5 == r0) goto L2e
            if (r5 == r2) goto L28
            goto L42
        L28:
            boolean r2 = r4.isConnectedA2dpDevice()
        L2c:
            r2 = r2 ^ r0
            goto L43
        L2e:
            com.android.settingslib.bluetooth.LocalBluetoothProfileManager r5 = r4.mProfileManager
            if (r5 != 0) goto L34
        L32:
            r2 = r3
            goto L2c
        L34:
            com.android.settingslib.bluetooth.HeadsetProfile r5 = r5.mHeadsetProfile
            if (r5 == 0) goto L32
            android.bluetooth.BluetoothDevice r4 = r4.mDevice
            int r4 = r5.getConnectionStatus(r4)
            if (r4 != r2) goto L32
            r2 = r0
            goto L2c
        L42:
            r2 = r3
        L43:
            if (r2 == 0) goto L46
            return r0
        L46:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bluetooth.qsdialog.ConnectedDeviceItemFactory.isFilterMatched(android.content.Context, com.android.settingslib.bluetooth.CachedBluetoothDevice, boolean):boolean");
    }
}
