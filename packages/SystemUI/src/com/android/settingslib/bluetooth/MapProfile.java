package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothMap;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import java.util.List;

/* loaded from: classes.dex */
public final class MapProfile implements LocalBluetoothProfile {
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothMap mService;

    public final class MapServiceListener implements BluetoothProfile.ServiceListener {
        public /* synthetic */ MapServiceListener(MapProfile mapProfile, int i) {
            this();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            BluetoothMap bluetoothMap = (BluetoothMap) bluetoothProfile;
            MapProfile.this.mService = bluetoothMap;
            List<BluetoothDevice> connectedDevices = bluetoothMap.getConnectedDevices();
            if (!connectedDevices.isEmpty()) {
                for (BluetoothDevice bluetoothDevice : connectedDevices) {
                    CachedBluetoothDevice cachedBluetoothDeviceFindDevice = MapProfile.this.mDeviceManager.findDevice(bluetoothDevice);
                    if (cachedBluetoothDeviceFindDevice == null) {
                        Log.w("MapProfile", "MapProfile found new device: " + bluetoothDevice);
                        MapProfile mapProfile = MapProfile.this;
                        cachedBluetoothDeviceFindDevice = mapProfile.mDeviceManager.addDevice(mapProfile.mProfileManager, bluetoothDevice);
                    }
                    if (cachedBluetoothDeviceFindDevice != null) {
                        Log.d("MapProfile", "Update cached device : " + cachedBluetoothDeviceFindDevice.getNameForLog());
                        cachedBluetoothDeviceFindDevice.onProfileStateChanged(MapProfile.this, 2);
                        cachedBluetoothDeviceFindDevice.refresh();
                    } else {
                        Log.d("MapProfile", "Bluetooth device is null");
                    }
                }
            }
            MapProfile.this.mProfileManager.callServiceConnectedListeners();
            MapProfile.this.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceDisconnected(int i) {
            MapProfile.this.mProfileManager.callServiceDisconnectedListeners();
            MapProfile.this.mIsProfileReady = false;
        }

        private MapServiceListener() {
        }
    }

    static {
        ParcelUuid[] parcelUuidArr = new ParcelUuid[3];
        ParcelUuid parcelUuid = BluetoothUuid.MAP;
        ParcelUuid parcelUuid2 = BluetoothUuid.MNS;
        ParcelUuid parcelUuid3 = BluetoothUuid.MAS;
    }

    public MapProfile(Context context, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, new MapServiceListener(this, 0), 9);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return true;
    }

    public final void finalize() {
        Log.d("MapProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(9, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("MapProfile", "Error cleaning up MAP proxy", th);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothMap bluetoothMap = this.mService;
        if (bluetoothMap == null) {
            return 0;
        }
        return bluetoothMap.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return R.drawable.list_ic_accessmsg;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 9;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isEnabled(BluetoothDevice bluetoothDevice) {
        BluetoothMap bluetoothMap = this.mService;
        return bluetoothMap != null && bluetoothMap.getConnectionPolicy(bluetoothDevice) > 0;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("isProfileReady(): "), this.mIsProfileReady, "MapProfile");
        return this.mIsProfileReady;
    }

    public final String toString() {
        return "MAP";
    }
}
