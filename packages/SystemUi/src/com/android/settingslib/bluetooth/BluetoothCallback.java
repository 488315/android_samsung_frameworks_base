package com.android.settingslib.bluetooth;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface BluetoothCallback {
    default void onBluetoothStateChanged(int i) {
    }

    default void onDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {
    }

    default void onDeviceDeleted(CachedBluetoothDevice cachedBluetoothDevice) {
    }

    default void onScanningStateChanged(boolean z) {
    }

    default void onAudioModeChanged() {
    }

    default void onAclConnectionStateChanged(int i, CachedBluetoothDevice cachedBluetoothDevice) {
    }

    default void onActiveDeviceChanged(int i, CachedBluetoothDevice cachedBluetoothDevice) {
    }

    default void onConnectionStateChanged(int i, CachedBluetoothDevice cachedBluetoothDevice) {
    }

    default void onDeviceBondStateChanged(int i, CachedBluetoothDevice cachedBluetoothDevice) {
    }

    default void onProfileConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i, int i2) {
    }
}
