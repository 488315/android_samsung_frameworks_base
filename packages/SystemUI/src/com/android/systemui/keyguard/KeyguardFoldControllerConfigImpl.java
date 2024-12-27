package com.android.systemui.keyguard;

import com.android.systemui.util.DeviceType;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardFoldControllerConfigImpl implements KeyguardFoldControllerConfig {
    public final boolean isDebug() {
        return DeviceType.getDebugLevel() == DeviceType.DEBUG_LEVEL_MID || !DeviceType.isShipBuild();
    }
}
