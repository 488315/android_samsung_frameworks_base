package com.android.settingslib.bluetooth;

import android.R;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHidDevice;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class HidDeviceProfile implements LocalBluetoothProfile {
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothHidDevice mService;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class HidDeviceServiceListener implements BluetoothProfile.ServiceListener {
        public /* synthetic */ HidDeviceServiceListener(HidDeviceProfile hidDeviceProfile, int i) {
            this();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            BluetoothHidDevice bluetoothHidDevice = (BluetoothHidDevice) bluetoothProfile;
            HidDeviceProfile.this.mService = bluetoothHidDevice;
            for (BluetoothDevice bluetoothDevice : bluetoothHidDevice.getConnectedDevices()) {
                CachedBluetoothDevice findDevice = HidDeviceProfile.this.mDeviceManager.findDevice(bluetoothDevice);
                if (findDevice == null) {
                    Log.w("HidDeviceProfile", "HidProfile found new device: " + bluetoothDevice);
                    HidDeviceProfile hidDeviceProfile = HidDeviceProfile.this;
                    findDevice = hidDeviceProfile.mDeviceManager.addDevice(hidDeviceProfile.mProfileManager, bluetoothDevice);
                }
                Log.d("HidDeviceProfile", "Connection status changed: " + findDevice);
                findDevice.onProfileStateChanged(HidDeviceProfile.this, 2);
                findDevice.refresh();
            }
            HidDeviceProfile.this.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceDisconnected(int i) {
            HidDeviceProfile.this.mIsProfileReady = false;
        }

        private HidDeviceServiceListener() {
        }
    }

    public HidDeviceProfile(Context context, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, new HidDeviceServiceListener(this, 0), 19);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return true;
    }

    public final void finalize() {
        Log.d("HidDeviceProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(19, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("HidDeviceProfile", "Error cleaning up HID proxy", th);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothHidDevice bluetoothHidDevice = this.mService;
        if (bluetoothHidDevice == null) {
            return 0;
        }
        return bluetoothHidDevice.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return R.drawable.ic_android_satellite_24px;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 19;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean setEnabled(BluetoothDevice bluetoothDevice) {
        return this.mService.setConnectionPolicy(bluetoothDevice, 0);
    }

    public final String toString() {
        return "HID DEVICE";
    }
}
