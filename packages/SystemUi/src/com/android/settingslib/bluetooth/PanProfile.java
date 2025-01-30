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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PanProfile implements LocalBluetoothProfile {
    public final CachedBluetoothDeviceManager mDeviceManager;
    public final HashMap mDeviceRoleMap = new HashMap();
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothPan mService;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                        Log.w("PanProfile", "PanProfile found new device: " + bluetoothDevice.getAddressForLogging());
                        PanProfile panProfile = PanProfile.this;
                        findDevice = panProfile.mDeviceManager.addDevice(panProfile.mProfileManager, bluetoothDevice);
                    }
                    if (findDevice != null) {
                        Log.d("PanProfile", "Update cached device : " + findDevice.getNameForLog());
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
    public final boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean setEnabled(BluetoothDevice bluetoothDevice) {
        if (this.mService == null) {
            return false;
        }
        Log.d("PanProfile", "setEnabled : false");
        return this.mService.setConnectionPolicy(bluetoothDevice, 0);
    }

    public final String toString() {
        return "PAN";
    }
}
