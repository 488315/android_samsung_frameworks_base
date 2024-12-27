package com.android.systemui.volume.util;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;

public final class DeviceStateManagerWrapper {
    public final Context context;
    public DeviceStateManager.FoldStateListener foldStateListener;
    public boolean isFolded;

    public DeviceStateManagerWrapper(Context context) {
        this.context = context;
    }
}
