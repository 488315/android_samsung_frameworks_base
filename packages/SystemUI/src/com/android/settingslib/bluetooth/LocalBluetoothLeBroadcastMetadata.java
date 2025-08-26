package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothLeBroadcastMetadata;

/* loaded from: classes.dex */
public final class LocalBluetoothLeBroadcastMetadata {
    public final BluetoothLeBroadcastMetadata metadata;

    public LocalBluetoothLeBroadcastMetadata(BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
        this.metadata = bluetoothLeBroadcastMetadata;
    }

    public LocalBluetoothLeBroadcastMetadata() {
        this(null);
    }
}
