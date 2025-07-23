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
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SppProfile implements LocalBluetoothProfile {
    public final CachedBluetoothDeviceManager mDeviceManager;
    public final AnonymousClass1 mHandler;
    public final LocalBluetoothAdapter mLocalAdapter;

    /* JADX WARN: Type inference failed for: r1v1, types: [android.os.Handler, com.samsung.android.settingslib.bluetooth.SppProfile$1] */
    public SppProfile(Context context, LocalBluetoothAdapter localBluetoothAdapter, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        ?? r1 = new Handler(Looper.getMainLooper()) { // from class: com.samsung.android.settingslib.bluetooth.SppProfile.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what != 0) {
                    return;
                }
                SppProfile sppProfile = SppProfile.this;
                Set<BluetoothDevice> bondedDevices = sppProfile.mLocalAdapter.mAdapter.getBondedDevices();
                if (bondedDevices == null || bondedDevices.isEmpty()) {
                    return;
                }
                for (BluetoothDevice bluetoothDevice : bondedDevices) {
                    if (bluetoothDevice.semIsGearConnected()) {
                        CachedBluetoothDevice findDevice = sppProfile.mDeviceManager.findDevice(bluetoothDevice);
                        if (findDevice == null) {
                            Log.w("SppProfile", "SppProfile found new device: " + bluetoothDevice);
                            findDevice = sppProfile.mDeviceManager.addDevice(bluetoothDevice);
                        }
                        if (findDevice != null) {
                            Log.d("SppProfile", "Update cached device : " + findDevice.getNameForLog());
                            findDevice.onProfileStateChanged(sppProfile, 2);
                            findDevice.refresh();
                        } else {
                            Log.d("SppProfile", "Bluetooth device is null");
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
        return bluetoothDevice.semIsGearConnected() ? 2 : 0;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return R.drawable.list_ic_general_device;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 200;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isEnabled(BluetoothDevice bluetoothDevice) {
        return bluetoothDevice.semIsGearConnected();
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        return true;
    }

    public final String toString() {
        return PeripheralConstants.ConnectionProfile.SPP;
    }

    public final void finalize() {
    }
}
