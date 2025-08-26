package com.samsung.android.settingslib.bluetooth.bluetoothcast;

/* loaded from: classes4.dex */
public interface BluetoothCastCallback {
    void onCastDeviceAdded();

    void onCastDeviceRemoved();

    void onCastDiscoveryStateChanged(boolean z);

    void onCastProfileStateChanged(CachedBluetoothCastDevice cachedBluetoothCastDevice);
}
