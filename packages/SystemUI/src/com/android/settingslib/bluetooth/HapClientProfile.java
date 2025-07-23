package com.android.settingslib.bluetooth;

import android.R;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHapClient;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class HapClientProfile implements LocalBluetoothProfile {
    public final BluetoothAdapter mBluetoothAdapter;
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothHapClient mService;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class HapClientServiceListener implements BluetoothProfile.ServiceListener {
        public /* synthetic */ HapClientServiceListener(HapClientProfile hapClientProfile, int i) {
            this();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            BluetoothHapClient bluetoothHapClient = (BluetoothHapClient) bluetoothProfile;
            HapClientProfile.this.mService = bluetoothHapClient;
            List connectedDevices = bluetoothHapClient.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) connectedDevices.remove(0);
                CachedBluetoothDevice findDevice = HapClientProfile.this.mDeviceManager.findDevice(bluetoothDevice);
                if (findDevice == null) {
                    Log.w("HapClientProfile", "HapClient profile found new device: " + bluetoothDevice);
                    findDevice = HapClientProfile.this.mDeviceManager.addDevice(bluetoothDevice);
                }
                findDevice.onProfileStateChanged(HapClientProfile.this, 2);
                findDevice.refresh();
            }
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager = HapClientProfile.this.mDeviceManager;
            synchronized (cachedBluetoothDeviceManager) {
                cachedBluetoothDeviceManager.mHearingAidDeviceManager.updateHearingAidsDevices();
            }
            HapClientProfile hapClientProfile = HapClientProfile.this;
            hapClientProfile.mIsProfileReady = true;
            hapClientProfile.mProfileManager.callServiceConnectedListeners();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceDisconnected(int i) {
            HapClientProfile hapClientProfile = HapClientProfile.this;
            hapClientProfile.mIsProfileReady = false;
            hapClientProfile.mProfileManager.callServiceDisconnectedListeners();
        }

        private HapClientServiceListener() {
        }
    }

    public HapClientProfile(Context context, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(BluetoothManager.class);
        if (bluetoothManager == null) {
            this.mBluetoothAdapter = null;
            return;
        }
        BluetoothAdapter adapter = bluetoothManager.getAdapter();
        this.mBluetoothAdapter = adapter;
        adapter.getProfileProxy(context, new HapClientServiceListener(this, 0), 28);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return false;
    }

    public final void finalize() {
        Log.d("HapClientProfile", "finalize()");
        BluetoothProfile bluetoothProfile = this.mService;
        if (bluetoothProfile != null) {
            try {
                this.mBluetoothAdapter.closeProfileProxy(28, bluetoothProfile);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("HapClientProfile", "Error cleaning up HAP Client proxy", th);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothHapClient bluetoothHapClient = this.mService;
        if (bluetoothHapClient == null) {
            return 0;
        }
        return bluetoothHapClient.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return R.drawable.ic_doc_powerpoint;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 28;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isEnabled(BluetoothDevice bluetoothDevice) {
        BluetoothHapClient bluetoothHapClient = this.mService;
        return (bluetoothHapClient == null || bluetoothDevice == null || bluetoothHapClient.getConnectionPolicy(bluetoothDevice) <= 0) ? false : true;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    public final void selectPreset(BluetoothDevice bluetoothDevice, int i) {
        BluetoothHapClient bluetoothHapClient = this.mService;
        if (bluetoothHapClient == null) {
            Log.w("HapClientProfile", "Proxy not attached to service. Cannot select preset.");
        } else {
            bluetoothHapClient.selectPreset(bluetoothDevice, i);
        }
    }

    public final String toString() {
        return "HapClient";
    }
}
