package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothVolumeControl;
import android.content.Context;
import android.util.Log;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class VolumeControlProfile implements LocalBluetoothProfile {
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothVolumeControl mService;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class VolumeControlProfileServiceListener implements BluetoothProfile.ServiceListener {
        public /* synthetic */ VolumeControlProfileServiceListener(VolumeControlProfile volumeControlProfile, int i) {
            this();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            Log.d("VolumeControlProfile", "Bluetooth service connected");
            BluetoothVolumeControl bluetoothVolumeControl = (BluetoothVolumeControl) bluetoothProfile;
            VolumeControlProfile.this.mService = bluetoothVolumeControl;
            List connectedDevices = bluetoothVolumeControl.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) connectedDevices.remove(0);
                CachedBluetoothDevice findDevice = VolumeControlProfile.this.mDeviceManager.findDevice(bluetoothDevice);
                if (findDevice == null) {
                    Log.d("VolumeControlProfile", "VolumeControlProfile found new device: " + bluetoothDevice);
                    VolumeControlProfile volumeControlProfile = VolumeControlProfile.this;
                    findDevice = volumeControlProfile.mDeviceManager.addDevice(volumeControlProfile.mProfileManager, bluetoothDevice);
                }
                if (findDevice != null) {
                    findDevice.onProfileStateChanged(VolumeControlProfile.this, 2);
                    findDevice.refresh();
                }
            }
            VolumeControlProfile.this.mProfileManager.callServiceConnectedListeners();
            VolumeControlProfile.this.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceDisconnected(int i) {
            Log.d("VolumeControlProfile", "Bluetooth service disconnected");
            VolumeControlProfile.this.mProfileManager.callServiceDisconnectedListeners();
            VolumeControlProfile volumeControlProfile = VolumeControlProfile.this;
            volumeControlProfile.mIsProfileReady = false;
            volumeControlProfile.mService = null;
        }

        private VolumeControlProfileServiceListener() {
        }
    }

    public VolumeControlProfile(Context context, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, new VolumeControlProfileServiceListener(this, 0), 23);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return false;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothVolumeControl bluetoothVolumeControl = this.mService;
        if (bluetoothVolumeControl == null) {
            return 0;
        }
        return bluetoothVolumeControl.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return 0;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 23;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean setEnabled(BluetoothDevice bluetoothDevice) {
        if (this.mService == null || bluetoothDevice == null) {
            return false;
        }
        Log.d("VolumeControlProfile", bluetoothDevice.getAnonymizedAddress() + " setEnabled: false");
        return this.mService.setConnectionPolicy(bluetoothDevice, 0);
    }

    public final String toString() {
        return "VCP";
    }
}
