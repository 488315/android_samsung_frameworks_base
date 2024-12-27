package com.android.systemui.dagger;

import android.content.Context;
import android.os.Handler;
import android.os.UserHandle;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

public final class SettingsLibraryModule {
    public static LocalBluetoothManager provideLocalBluetoothController(Context context, Handler handler) {
        return LocalBluetoothManager.create(context, handler, UserHandle.ALL);
    }
}
