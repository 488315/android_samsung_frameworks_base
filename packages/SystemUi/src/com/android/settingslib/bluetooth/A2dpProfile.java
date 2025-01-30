package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class A2dpProfile implements LocalBluetoothProfile {
    public static final ParcelUuid[] SINK_UUIDS = {BluetoothUuid.A2DP_SINK, BluetoothUuid.ADV_AUDIO_DIST};
    public final BluetoothAdapter mBluetoothAdapter;
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothA2dp mService;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class A2dpServiceListener implements BluetoothProfile.ServiceListener {
        public /* synthetic */ A2dpServiceListener(A2dpProfile a2dpProfile, int i) {
            this();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            BluetoothA2dp bluetoothA2dp = (BluetoothA2dp) bluetoothProfile;
            A2dpProfile.this.mService = bluetoothA2dp;
            List<BluetoothDevice> connectedDevices = bluetoothA2dp.getConnectedDevices();
            if (!connectedDevices.isEmpty()) {
                for (BluetoothDevice bluetoothDevice : connectedDevices) {
                    CachedBluetoothDevice findDevice = A2dpProfile.this.mDeviceManager.findDevice(bluetoothDevice);
                    if (findDevice == null) {
                        Log.w("A2dpProfile", "A2dpProfile found new device: " + bluetoothDevice.getAddressForLogging());
                        A2dpProfile a2dpProfile = A2dpProfile.this;
                        findDevice = a2dpProfile.mDeviceManager.addDevice(a2dpProfile.mProfileManager, bluetoothDevice);
                    }
                    if (findDevice != null) {
                        Log.d("A2dpProfile", "Update cached device : " + findDevice.getNameForLog());
                        findDevice.onProfileStateChanged(A2dpProfile.this, 2);
                        findDevice.refresh();
                    } else {
                        Log.d("A2dpProfile", "Bluetooth device is null");
                    }
                }
            }
            A2dpProfile a2dpProfile2 = A2dpProfile.this;
            a2dpProfile2.mIsProfileReady = true;
            a2dpProfile2.mProfileManager.callServiceConnectedListeners();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceDisconnected(int i) {
            A2dpProfile a2dpProfile = A2dpProfile.this;
            a2dpProfile.mIsProfileReady = false;
            a2dpProfile.mProfileManager.callServiceDisconnectedListeners();
            A2dpProfile.this.mService = null;
        }

        private A2dpServiceListener() {
        }
    }

    public A2dpProfile(Context context, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mBluetoothAdapter = defaultAdapter;
        defaultAdapter.getProfileProxy(context, new A2dpServiceListener(this, 0), 2);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return true;
    }

    public final void finalize() {
        Log.d("A2dpProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(2, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("A2dpProfile", "Error cleaning up A2DP proxy", th);
            }
        }
    }

    public final BluetoothDevice getActiveDevice() {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter == null) {
            return null;
        }
        List activeDevices = bluetoothAdapter.getActiveDevices(2);
        if (activeDevices.size() > 0) {
            return (BluetoothDevice) activeDevices.get(0);
        }
        return null;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothA2dp bluetoothA2dp = this.mService;
        if (bluetoothA2dp == null) {
            Log.d("A2dpProfile", "getConnectionStatus :: BluetoothProfile.STATE_DISCONNECTED");
            return 0;
        }
        int connectionState = bluetoothA2dp.getConnectionState(bluetoothDevice);
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("getConnectionStatus :: ", connectionState, "A2dpProfile");
        return connectionState;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return R.drawable.list_ic_sound_accessory_default;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 2;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean setEnabled(BluetoothDevice bluetoothDevice) {
        BluetoothA2dp bluetoothA2dp = this.mService;
        if (bluetoothA2dp == null) {
            return false;
        }
        return bluetoothA2dp.setConnectionPolicy(bluetoothDevice, 0);
    }

    public final String toString() {
        return "A2DP";
    }
}
