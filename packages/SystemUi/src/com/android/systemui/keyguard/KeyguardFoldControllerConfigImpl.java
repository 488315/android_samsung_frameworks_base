package com.android.systemui.keyguard;

import com.android.systemui.util.DeviceType;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardFoldControllerConfigImpl implements KeyguardFoldControllerConfig {
    public final boolean isDebug() {
        return DeviceType.getDebugLevel() == 1 || !DeviceType.isShipBuild();
    }
}
