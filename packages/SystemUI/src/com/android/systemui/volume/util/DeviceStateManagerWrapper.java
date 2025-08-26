package com.android.systemui.volume.util;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;

/* loaded from: classes3.dex */
public final class DeviceStateManagerWrapper {
    public final Context context;
    public DeviceStateManager.FoldStateListener foldStateListener;
    public boolean isFolded;

    public DeviceStateManagerWrapper(Context context) {
        this.context = context;
    }
}
