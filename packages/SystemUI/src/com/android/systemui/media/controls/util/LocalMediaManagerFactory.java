package com.android.systemui.media.controls.util;

import android.content.Context;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

public final class LocalMediaManagerFactory {
    public final Context context;
    public final LocalBluetoothManager localBluetoothManager;

    public LocalMediaManagerFactory(Context context, LocalBluetoothManager localBluetoothManager) {
        this.context = context;
        this.localBluetoothManager = localBluetoothManager;
    }
}
