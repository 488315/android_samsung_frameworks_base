package com.samsung.android.settingslib.bluetooth.bluetoothcast;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface BluetoothCastCallback {
    void onCastDeviceAdded();

    void onCastDeviceRemoved();

    void onCastDiscoveryStateChanged(boolean z);

    void onCastProfileStateChanged(CachedBluetoothCastDevice cachedBluetoothCastDevice);
}
