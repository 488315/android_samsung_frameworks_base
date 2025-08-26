package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothPan;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;
import com.android.systemui.R;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public final class PanProfile implements LocalBluetoothProfile {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CachedBluetoothDeviceManager mDeviceManager;
    public final HashMap mDeviceRoleMap = new HashMap();
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothPan mService;

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
                    CachedBluetoothDevice cachedBluetoothDeviceFindDevice = PanProfile.this.mDeviceManager.findDevice(bluetoothDevice);
                    if (cachedBluetoothDeviceFindDevice == null) {
                        Log.w("PanProfile", "PanProfile found new device: " + bluetoothDevice);
                        PanProfile panProfile = PanProfile.this;
                        cachedBluetoothDeviceFindDevice = panProfile.mDeviceManager.addDevice(panProfile.mProfileManager, bluetoothDevice);
                    }
                    if (cachedBluetoothDeviceFindDevice != null) {
                        Log.d("PanProfile", "Update cached device: " + cachedBluetoothDeviceFindDevice.getNameForLog());
                        cachedBluetoothDeviceFindDevice.onProfileStateChanged(PanProfile.this, 2);
                        cachedBluetoothDeviceFindDevice.refresh();
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

    /* JADX WARN: Removed duplicated region for block: B:22:0x0055 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isLocalRoleNap(final BluetoothDevice bluetoothDevice) {
        if (this.mDeviceRoleMap.containsKey(bluetoothDevice)) {
            return ((Integer) this.mDeviceRoleMap.get(bluetoothDevice)).intValue() == 1;
        }
        BluetoothPan bluetoothPan = this.mService;
        if ((bluetoothPan == null ? Collections.EMPTY_LIST : bluetoothPan.getConnectedDevices()).stream().anyMatch(new Predicate() { // from class: com.android.settingslib.bluetooth.PanProfile$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                BluetoothDevice bluetoothDevice2 = bluetoothDevice;
                int i = PanProfile.$r8$clinit;
                return ((BluetoothDevice) obj).getAddress().equals(bluetoothDevice2.getAddress());
            }
        })) {
            BluetoothPan bluetoothPan2 = this.mService;
            if (bluetoothPan2 == null ? false : bluetoothPan2.isTetheringOn()) {
                setLocalRole(bluetoothDevice, 1);
            } else {
                setLocalRole(bluetoothDevice, 2);
            }
            if (((Integer) this.mDeviceRoleMap.get(bluetoothDevice)).intValue() == 1) {
            }
        }
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
