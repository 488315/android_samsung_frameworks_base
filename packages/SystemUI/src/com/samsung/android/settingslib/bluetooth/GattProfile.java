package com.samsung.android.settingslib.bluetooth;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.CachedBluetoothDeviceManager;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.systemui.R;
import java.util.Set;

/* loaded from: classes4.dex */
public final class GattProfile implements LocalBluetoothProfile {
    public final CachedBluetoothDeviceManager mDeviceManager;
    public final AnonymousClass1 mHandler;
    public final LocalBluetoothAdapter mLocalAdapter;

    /* JADX WARN: Type inference failed for: r1v1, types: [android.os.Handler, com.samsung.android.settingslib.bluetooth.GattProfile$1] */
    public GattProfile(Context context, LocalBluetoothAdapter localBluetoothAdapter, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        ?? r1 = new Handler(Looper.getMainLooper()) { // from class: com.samsung.android.settingslib.bluetooth.GattProfile.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what != 0) {
                    return;
                }
                GattProfile gattProfile = GattProfile.this;
                Set<BluetoothDevice> bondedDevices = gattProfile.mLocalAdapter.mAdapter.getBondedDevices();
                if (bondedDevices == null || bondedDevices.isEmpty()) {
                    return;
                }
                for (BluetoothDevice bluetoothDevice : bondedDevices) {
                    if (bluetoothDevice.semIsGattConnected()) {
                        CachedBluetoothDevice cachedBluetoothDeviceFindDevice = gattProfile.mDeviceManager.findDevice(bluetoothDevice);
                        if (cachedBluetoothDeviceFindDevice == null) {
                            Log.w("GattProfile", "GattProfile found new device: " + bluetoothDevice);
                            cachedBluetoothDeviceFindDevice = gattProfile.mDeviceManager.addDevice(bluetoothDevice);
                        }
                        if (cachedBluetoothDeviceFindDevice != null) {
                            Log.d("GattProfile", "Update cached device : " + cachedBluetoothDeviceFindDevice.getNameForLog());
                            cachedBluetoothDeviceFindDevice.onProfileStateChanged(gattProfile, 2);
                            cachedBluetoothDeviceFindDevice.refresh();
                        } else {
                            Log.d("GattProfile", "Bluetooth device is null");
                        }
                    }
                }
            }
        };
        this.mHandler = r1;
        this.mLocalAdapter = localBluetoothAdapter;
        this.mDeviceManager = cachedBluetoothDeviceManager;
        Message message = new Message();
        message.what = 0;
        r1.sendMessageDelayed(message, 300L);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return false;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        return bluetoothDevice.semIsGattConnected() ? 2 : 0;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return R.drawable.list_ic_general_device;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 7;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isEnabled(BluetoothDevice bluetoothDevice) {
        return bluetoothDevice.semIsGattConnected();
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        return true;
    }

    public final String toString() {
        return "GATT";
    }

    public final void finalize() {
    }
}
