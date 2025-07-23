package com.android.settingslib.bluetooth;

import android.R;
import android.bluetooth.BluetoothA2dpSink;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class A2dpSinkProfile implements LocalBluetoothProfile {
    public static final ParcelUuid[] SRC_UUIDS = {BluetoothUuid.A2DP_SOURCE, BluetoothUuid.ADV_AUDIO_DIST};
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothA2dpSink mService;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class A2dpSinkServiceListener implements BluetoothProfile.ServiceListener {
        public /* synthetic */ A2dpSinkServiceListener(A2dpSinkProfile a2dpSinkProfile, int i) {
            this();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            BluetoothA2dpSink bluetoothA2dpSink = (BluetoothA2dpSink) bluetoothProfile;
            A2dpSinkProfile.this.mService = bluetoothA2dpSink;
            List connectedDevices = bluetoothA2dpSink.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) connectedDevices.remove(0);
                CachedBluetoothDevice findDevice = A2dpSinkProfile.this.mDeviceManager.findDevice(bluetoothDevice);
                if (findDevice == null) {
                    Log.w("A2dpSinkProfile", "A2dpSinkProfile found new device: " + bluetoothDevice);
                    A2dpSinkProfile a2dpSinkProfile = A2dpSinkProfile.this;
                    findDevice = a2dpSinkProfile.mDeviceManager.addDevice(a2dpSinkProfile.mProfileManager, bluetoothDevice);
                }
                if (findDevice != null) {
                    findDevice.onProfileStateChanged(A2dpSinkProfile.this, 2);
                    findDevice.refresh();
                }
            }
            A2dpSinkProfile.this.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceDisconnected(int i) {
            A2dpSinkProfile.this.mIsProfileReady = false;
        }

        private A2dpSinkServiceListener() {
        }
    }

    public A2dpSinkProfile(Context context, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, new A2dpSinkServiceListener(this, 0), 11);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return true;
    }

    public final void finalize() {
        Log.d("A2dpSinkProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(11, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("A2dpSinkProfile", "Error cleaning up A2DP proxy", th);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothA2dpSink bluetoothA2dpSink = this.mService;
        if (bluetoothA2dpSink == null) {
            return 0;
        }
        return bluetoothA2dpSink.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return R.drawable.ic_doc_image;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 11;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isEnabled(BluetoothDevice bluetoothDevice) {
        BluetoothA2dpSink bluetoothA2dpSink = this.mService;
        return bluetoothA2dpSink != null && bluetoothA2dpSink.getConnectionPolicy(bluetoothDevice) > 0;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    public final String toString() {
        return "A2DPSink";
    }
}
