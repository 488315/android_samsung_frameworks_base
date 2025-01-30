package com.android.settingslib.bluetooth;

import android.R;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothMapClient;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MapClientProfile implements LocalBluetoothProfile {
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothMapClient mService;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MapClientServiceListener implements BluetoothProfile.ServiceListener {
        public /* synthetic */ MapClientServiceListener(MapClientProfile mapClientProfile, int i) {
            this();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            BluetoothMapClient bluetoothMapClient = (BluetoothMapClient) bluetoothProfile;
            MapClientProfile.this.mService = bluetoothMapClient;
            List connectedDevices = bluetoothMapClient.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) connectedDevices.remove(0);
                CachedBluetoothDevice findDevice = MapClientProfile.this.mDeviceManager.findDevice(bluetoothDevice);
                if (findDevice == null) {
                    Log.w("MapClientProfile", "MapProfile found new device: " + bluetoothDevice);
                    MapClientProfile mapClientProfile = MapClientProfile.this;
                    findDevice = mapClientProfile.mDeviceManager.addDevice(mapClientProfile.mProfileManager, bluetoothDevice);
                }
                findDevice.onProfileStateChanged(MapClientProfile.this, 2);
                findDevice.refresh();
            }
            MapClientProfile.this.mProfileManager.callServiceConnectedListeners();
            MapClientProfile.this.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceDisconnected(int i) {
            MapClientProfile.this.mProfileManager.callServiceDisconnectedListeners();
            MapClientProfile.this.mIsProfileReady = false;
        }

        private MapClientServiceListener() {
        }
    }

    static {
        new ParcelUuid[]{BluetoothUuid.MAP, BluetoothUuid.MNS, BluetoothUuid.MAS};
    }

    public MapClientProfile(Context context, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, new MapClientServiceListener(this, 0), 18);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return true;
    }

    public final void finalize() {
        Log.d("MapClientProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(18, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("MapClientProfile", "Error cleaning up MAP Client proxy", th);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothMapClient bluetoothMapClient = this.mService;
        if (bluetoothMapClient == null) {
            return 0;
        }
        return bluetoothMapClient.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return R.drawable.ic_perm_group_camera;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 18;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("isProfileReady(): "), this.mIsProfileReady, "MapClientProfile");
        return this.mIsProfileReady;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean setEnabled(BluetoothDevice bluetoothDevice) {
        BluetoothMapClient bluetoothMapClient = this.mService;
        if (bluetoothMapClient == null) {
            return false;
        }
        return bluetoothMapClient.setConnectionPolicy(bluetoothDevice, 0);
    }

    public final String toString() {
        return "MAP Client";
    }
}
