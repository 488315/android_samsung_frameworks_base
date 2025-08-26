package com.android.settingslib.bluetooth;

/* loaded from: classes.dex */
public interface BluetoothCallback {
    default void onAudioModeChanged() {
    }

    default void onAutoOnStateChanged(int i) {
    }

    default void onBluetoothStateChanged(int i) {
    }

    default void onDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {
    }

    default void onDeviceDeleted(CachedBluetoothDevice cachedBluetoothDevice) {
    }

    default void onScanningStateChanged(boolean z) {
    }

    default void onAclConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
    }

    default void onActiveDeviceChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
    }

    default void onConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
    }

    default void onDeviceBondStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
    }

    default void onProfileConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i, int i2) {
    }
}
