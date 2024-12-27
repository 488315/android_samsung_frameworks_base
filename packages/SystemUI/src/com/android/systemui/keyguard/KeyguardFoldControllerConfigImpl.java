package com.android.systemui.keyguard;

import com.android.systemui.util.DeviceType;

public final class KeyguardFoldControllerConfigImpl implements KeyguardFoldControllerConfig {
    public final boolean isDebug() {
        return DeviceType.getDebugLevel() == DeviceType.DEBUG_LEVEL_MID || !DeviceType.isShipBuild();
    }
}
