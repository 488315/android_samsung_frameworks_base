package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothCsipSetCoordinator;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class CsipSetCoordinatorProfile implements LocalBluetoothProfile {
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothCsipSetCoordinator mService;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CoordinatedSetServiceListener implements BluetoothProfile.ServiceListener {
        public /* synthetic */ CoordinatedSetServiceListener(CsipSetCoordinatorProfile csipSetCoordinatorProfile, int i) {
            this();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            Log.d("CsipSetCoordinatorProfile", "Bluetooth service connected");
            BluetoothCsipSetCoordinator bluetoothCsipSetCoordinator = (BluetoothCsipSetCoordinator) bluetoothProfile;
            CsipSetCoordinatorProfile.this.mService = bluetoothCsipSetCoordinator;
            List<BluetoothDevice> connectedDevices = bluetoothCsipSetCoordinator.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice remove = connectedDevices.remove(0);
                CachedBluetoothDevice findDevice = CsipSetCoordinatorProfile.this.mDeviceManager.findDevice(remove);
                if (findDevice == null) {
                    Log.d("CsipSetCoordinatorProfile", "CsipSetCoordinatorProfile found new device: " + remove);
                    findDevice = CsipSetCoordinatorProfile.this.mDeviceManager.addDevice(remove);
                }
                findDevice.onProfileStateChanged(CsipSetCoordinatorProfile.this, 2);
                findDevice.refresh();
            }
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager = CsipSetCoordinatorProfile.this.mDeviceManager;
            synchronized (cachedBluetoothDeviceManager) {
                cachedBluetoothDeviceManager.mCsipDeviceManager.updateCsipDevices();
            }
            CsipSetCoordinatorProfile.this.mProfileManager.callServiceConnectedListeners();
            CsipSetCoordinatorProfile.this.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceDisconnected(int i) {
            Log.d("CsipSetCoordinatorProfile", "Bluetooth service disconnected");
            CsipSetCoordinatorProfile.this.mProfileManager.callServiceDisconnectedListeners();
            CsipSetCoordinatorProfile.this.mIsProfileReady = false;
        }

        private CoordinatedSetServiceListener() {
        }
    }

    public CsipSetCoordinatorProfile(Context context, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, new CoordinatedSetServiceListener(this, 0), 25);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return false;
    }

    public final void finalize() {
        Log.d("CsipSetCoordinatorProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(25, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("CsipSetCoordinatorProfile", "Error cleaning up CSIP Set Coordinator proxy", th);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothCsipSetCoordinator bluetoothCsipSetCoordinator = this.mService;
        if (bluetoothCsipSetCoordinator == null) {
            return 0;
        }
        return bluetoothCsipSetCoordinator.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return 0;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 25;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isEnabled(BluetoothDevice bluetoothDevice) {
        BluetoothCsipSetCoordinator bluetoothCsipSetCoordinator = this.mService;
        return (bluetoothCsipSetCoordinator == null || bluetoothDevice == null || bluetoothCsipSetCoordinator.getConnectionPolicy(bluetoothDevice) <= 0) ? false : true;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    public final String toString() {
        return "CSIP Set Coordinator";
    }
}
