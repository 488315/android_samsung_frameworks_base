package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothPan;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;
import com.android.systemui.R;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PanProfile implements LocalBluetoothProfile {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CachedBluetoothDeviceManager mDeviceManager;
    public final HashMap mDeviceRoleMap = new HashMap();
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothPan mService;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PanServiceListener implements BluetoothProfile.ServiceListener {
        public /* synthetic */ PanServiceListener(PanProfile panProfile, int i) {
            this();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            BluetoothPan bluetoothPan = (BluetoothPan) bluetoothProfile;
            PanProfile.this.mService = bluetoothPan;
            List<BluetoothDevice> connectedDevices = bluetoothPan.getConnectedDevices();
            if (!connectedDevices.isEmpty()) {
                for (BluetoothDevice bluetoothDevice : connectedDevices) {
                    CachedBluetoothDevice findDevice = PanProfile.this.mDeviceManager.findDevice(bluetoothDevice);
                    if (findDevice == null) {
                        Log.w("PanProfile", "PanProfile found new device: " + bluetoothDevice);
                        PanProfile panProfile = PanProfile.this;
                        findDevice = panProfile.mDeviceManager.addDevice(panProfile.mProfileManager, bluetoothDevice);
                    }
                    if (findDevice != null) {
                        Log.d("PanProfile", "Update cached device: " + findDevice.getNameForLog());
                        findDevice.onProfileStateChanged(PanProfile.this, 2);
                        findDevice.refresh();
                    } else {
                        Log.d("PanProfile", "Bluetooth device is null");
                    }
                }
            }
            PanProfile panProfile2 = PanProfile.this;
            panProfile2.mIsProfileReady = true;
            panProfile2.mProfileManager.callServiceConnectedListeners();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceDisconnected(int i) {
            PanProfile panProfile = PanProfile.this;
            panProfile.mIsProfileReady = false;
            panProfile.mProfileManager.callServiceDisconnectedListeners();
            PanProfile.this.mService = null;
        }

        private PanServiceListener() {
        }
    }

    static {
        new HashSet<String>() { // from class: com.android.settingslib.bluetooth.PanProfile.1
            {
                add("PANNAP");
                add("PANU");
            }
        };
    }

    public PanProfile(Context context, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, new PanServiceListener(this, 0), 5);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return true;
    }

    public final void finalize() {
        Log.d("PanProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(5, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("PanProfile", "Error cleaning up PAN proxy", th);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothPan bluetoothPan = this.mService;
        if (bluetoothPan == null) {
            return 0;
        }
        return bluetoothPan.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return R.drawable.list_ic_bluetooth_pan_network;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 5;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isEnabled(BluetoothDevice bluetoothDevice) {
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0055 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isLocalRoleNap(final android.bluetooth.BluetoothDevice r5) {
        /*
            r4 = this;
            java.util.HashMap r0 = r4.mDeviceRoleMap
            boolean r0 = r0.containsKey(r5)
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L19
            java.util.HashMap r4 = r4.mDeviceRoleMap
            java.lang.Object r4 = r4.get(r5)
            java.lang.Integer r4 = (java.lang.Integer) r4
            int r4 = r4.intValue()
            if (r4 != r2) goto L56
            goto L55
        L19:
            android.bluetooth.BluetoothPan r0 = r4.mService
            if (r0 != 0) goto L20
            java.util.List r0 = java.util.Collections.EMPTY_LIST
            goto L24
        L20:
            java.util.List r0 = r0.getConnectedDevices()
        L24:
            java.util.stream.Stream r0 = r0.stream()
            com.android.settingslib.bluetooth.PanProfile$$ExternalSyntheticLambda0 r3 = new com.android.settingslib.bluetooth.PanProfile$$ExternalSyntheticLambda0
            r3.<init>()
            boolean r0 = r0.anyMatch(r3)
            if (r0 == 0) goto L56
            android.bluetooth.BluetoothPan r0 = r4.mService
            if (r0 != 0) goto L39
            r0 = r1
            goto L3d
        L39:
            boolean r0 = r0.isTetheringOn()
        L3d:
            if (r0 == 0) goto L43
            r4.setLocalRole(r5, r2)
            goto L47
        L43:
            r0 = 2
            r4.setLocalRole(r5, r0)
        L47:
            java.util.HashMap r4 = r4.mDeviceRoleMap
            java.lang.Object r4 = r4.get(r5)
            java.lang.Integer r4 = (java.lang.Integer) r4
            int r4 = r4.intValue()
            if (r4 != r2) goto L56
        L55:
            return r2
        L56:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.bluetooth.PanProfile.isLocalRoleNap(android.bluetooth.BluetoothDevice):boolean");
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    public final void setLocalRole(BluetoothDevice bluetoothDevice, int i) {
        if (getConnectionStatus(bluetoothDevice) == 0) {
            this.mDeviceRoleMap.remove(bluetoothDevice);
        } else {
            this.mDeviceRoleMap.put(bluetoothDevice, Integer.valueOf(i));
        }
    }

    public final String toString() {
        return "PAN";
    }
}
