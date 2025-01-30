package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class HeadsetProfile implements LocalBluetoothProfile {
    public final BluetoothAdapter mBluetoothAdapter;
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothHeadset mService;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class HeadsetServiceListener implements BluetoothProfile.ServiceListener {
        public /* synthetic */ HeadsetServiceListener(HeadsetProfile headsetProfile, int i) {
            this();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            BluetoothHeadset bluetoothHeadset = (BluetoothHeadset) bluetoothProfile;
            HeadsetProfile.this.mService = bluetoothHeadset;
            if (bluetoothHeadset == null) {
                Log.w("HeadsetProfile", "mService is null");
                return;
            }
            List<BluetoothDevice> connectedDevices = bluetoothHeadset.getConnectedDevices();
            if (connectedDevices != null && !connectedDevices.isEmpty()) {
                for (BluetoothDevice bluetoothDevice : connectedDevices) {
                    CachedBluetoothDevice findDevice = HeadsetProfile.this.mDeviceManager.findDevice(bluetoothDevice);
                    if (findDevice == null) {
                        Log.d("HeadsetProfile", "HeadsetProfile found new device: " + bluetoothDevice.getAddressForLogging());
                        HeadsetProfile headsetProfile = HeadsetProfile.this;
                        findDevice = headsetProfile.mDeviceManager.addDevice(headsetProfile.mProfileManager, bluetoothDevice);
                    }
                    if (findDevice != null) {
                        Log.d("HeadsetProfile", "Update cached device : " + findDevice.getNameForLog());
                        findDevice.onProfileStateChanged(HeadsetProfile.this, 2);
                        findDevice.refresh();
                    } else {
                        Log.d("HeadsetProfile", "Bluetooth device is null");
                    }
                }
            }
            HeadsetProfile headsetProfile2 = HeadsetProfile.this;
            headsetProfile2.mIsProfileReady = true;
            headsetProfile2.mProfileManager.callServiceConnectedListeners();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceDisconnected(int i) {
            HeadsetProfile.this.mProfileManager.callServiceDisconnectedListeners();
            HeadsetProfile headsetProfile = HeadsetProfile.this;
            headsetProfile.mIsProfileReady = false;
            headsetProfile.mService = null;
        }

        private HeadsetServiceListener() {
        }
    }

    static {
        new ParcelUuid[]{BluetoothUuid.HSP, BluetoothUuid.HFP};
    }

    public HeadsetProfile(Context context, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mBluetoothAdapter = defaultAdapter;
        defaultAdapter.getProfileProxy(context, new HeadsetServiceListener(this, 0), 1);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return true;
    }

    public final void finalize() {
        Log.d("HeadsetProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(1, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("HeadsetProfile", "Error cleaning up HID proxy", th);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        if (this.mService == null) {
            return 0;
        }
        if (BluetoothUtils.DEBUG) {
            Log.d("HeadsetProfile", "getConnectionStatus :: device : " + bluetoothDevice.getName());
        }
        List<BluetoothDevice> connectedDevices = this.mService.getConnectedDevices();
        if (connectedDevices != null && !connectedDevices.isEmpty()) {
            Iterator<BluetoothDevice> it = connectedDevices.iterator();
            while (it.hasNext()) {
                if (it.next().equals(bluetoothDevice)) {
                    int connectionState = this.mService.getConnectionState(bluetoothDevice);
                    ListPopupWindow$$ExternalSyntheticOutline0.m10m("getConnectionStatus :: ", connectionState, "HeadsetProfile");
                    return connectionState;
                }
            }
        }
        Log.d("HeadsetProfile", "getConnectionStatus :: BluetoothProfile.STATE_DISCONNECTED (cannot find device)");
        return 0;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return R.drawable.list_ic_mono_headset;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 1;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean setEnabled(BluetoothDevice bluetoothDevice) {
        BluetoothHeadset bluetoothHeadset = this.mService;
        if (bluetoothHeadset == null) {
            return false;
        }
        return bluetoothHeadset.setConnectionPolicy(bluetoothDevice, 0);
    }

    public final String toString() {
        return "HEADSET";
    }
}
