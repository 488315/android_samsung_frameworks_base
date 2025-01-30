package com.android.systemui.volume.util;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DeviceStateManagerWrapper {
    public final Context context;
    public DeviceStateManager.FoldStateListener foldStateListener;
    public boolean isFolded;

    public DeviceStateManagerWrapper(Context context) {
        this.context = context;
    }
}
