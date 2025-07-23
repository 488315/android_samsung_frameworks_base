package com.android.systemui.dagger;

import android.content.Context;
import android.os.Handler;
import android.os.UserHandle;
import com.android.settingslib.bluetooth.HearingAidDeviceManager;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SettingsLibraryModule {
    public static HearingAidDeviceManager provideHearingAidDeviceManager(LocalBluetoothManager localBluetoothManager) {
        if (localBluetoothManager == null) {
            return null;
        }
        return localBluetoothManager.mCachedDeviceManager.getHearingAidDeviceManager();
    }

    public static LocalBluetoothManager provideLocalBluetoothController(Context context, Handler handler) {
        UserHandle userHandle = UserHandle.ALL;
        return LocalBluetoothManager.create(context, handler);
    }
}
