package com.android.systemui.media.controls.util;

import android.content.Context;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class LocalMediaManagerFactory {
    public final Context context;
    public final LocalBluetoothManager localBluetoothManager;

    public LocalMediaManagerFactory(Context context, LocalBluetoothManager localBluetoothManager) {
        this.context = context;
        this.localBluetoothManager = localBluetoothManager;
    }
}
