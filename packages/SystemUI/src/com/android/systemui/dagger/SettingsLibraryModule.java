package com.android.systemui.dagger;

import android.content.Context;
import android.os.Handler;
import android.os.UserHandle;
import com.android.settingslib.bluetooth.HearingAidDeviceManager;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

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
