package com.android.systemui.volume.util;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DeviceStateManagerWrapper {
    public final Context context;
    public DeviceStateManager.FoldStateListener foldStateListener;
    public boolean isFolded;

    public DeviceStateManagerWrapper(Context context) {
        this.context = context;
    }
}
