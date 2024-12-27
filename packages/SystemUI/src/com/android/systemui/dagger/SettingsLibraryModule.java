package com.android.systemui.dagger;

import android.content.Context;
import android.os.Handler;
import android.os.UserHandle;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SettingsLibraryModule {
    public static LocalBluetoothManager provideLocalBluetoothController(Context context, Handler handler) {
        return LocalBluetoothManager.create(context, handler, UserHandle.ALL);
    }
}
