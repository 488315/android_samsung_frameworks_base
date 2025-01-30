package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeBroadcastAssistant;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LocalBluetoothLeBroadcastAssistant implements LocalBluetoothProfile {
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothLeBroadcastAssistant mService;
    public final C09201 mServiceListener;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [android.bluetooth.BluetoothProfile$ServiceListener, com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant$1] */
    public LocalBluetoothLeBroadcastAssistant(Context context, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        ?? r0 = new BluetoothProfile.ServiceListener() { // from class: com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant.1
            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                Log.d("LocalBluetoothLeBroadcastAssistant", "Bluetooth service connected");
                BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = (BluetoothLeBroadcastAssistant) bluetoothProfile;
                LocalBluetoothLeBroadcastAssistant.this.mService = bluetoothLeBroadcastAssistant;
                List connectedDevices = bluetoothLeBroadcastAssistant.getConnectedDevices();
                while (!connectedDevices.isEmpty()) {
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) connectedDevices.remove(0);
                    CachedBluetoothDevice findDevice = LocalBluetoothLeBroadcastAssistant.this.mDeviceManager.findDevice(bluetoothDevice);
                    if (findDevice == null) {
                        Log.d("LocalBluetoothLeBroadcastAssistant", "LocalBluetoothLeBroadcastAssistant found new device: " + bluetoothDevice);
                        findDevice = LocalBluetoothLeBroadcastAssistant.this.mDeviceManager.addDevice(bluetoothDevice);
                    }
                    findDevice.onProfileStateChanged(LocalBluetoothLeBroadcastAssistant.this, 2);
                    findDevice.refresh();
                }
                LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant = LocalBluetoothLeBroadcastAssistant.this;
                localBluetoothLeBroadcastAssistant.mIsProfileReady = true;
                localBluetoothLeBroadcastAssistant.mProfileManager.callServiceConnectedListeners();
            }

            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public final void onServiceDisconnected(int i) {
                if (i != 29) {
                    Log.d("LocalBluetoothLeBroadcastAssistant", "The profile is not LE_AUDIO_BROADCAST_ASSISTANT");
                    return;
                }
                Log.d("LocalBluetoothLeBroadcastAssistant", "Bluetooth service disconnected");
                LocalBluetoothLeBroadcastAssistant.this.mProfileManager.callServiceDisconnectedListeners();
                LocalBluetoothLeBroadcastAssistant.this.mIsProfileReady = false;
            }
        };
        this.mServiceListener = r0;
        this.mProfileManager = localBluetoothProfileManager;
        this.mDeviceManager = cachedBluetoothDeviceManager;
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, r0, 29);
        new BluetoothLeBroadcastMetadata.Builder();
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return false;
    }

    public final void finalize() {
        Log.d("LocalBluetoothLeBroadcastAssistant", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(29, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("LocalBluetoothLeBroadcastAssistant", "Error cleaning up LeAudio proxy", th);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = this.mService;
        if (bluetoothLeBroadcastAssistant == null) {
            return 0;
        }
        return bluetoothLeBroadcastAssistant.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return 0;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 29;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean setEnabled(BluetoothDevice bluetoothDevice) {
        BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = this.mService;
        if (bluetoothLeBroadcastAssistant == null || bluetoothDevice == null) {
            return false;
        }
        return bluetoothLeBroadcastAssistant.setConnectionPolicy(bluetoothDevice, 0);
    }

    public final String toString() {
        return "LE_AUDIO_BROADCAST_ASSISTANT";
    }
}
