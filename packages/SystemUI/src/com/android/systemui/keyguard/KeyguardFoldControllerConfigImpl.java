package com.android.systemui.keyguard;

import com.android.systemui.util.DeviceType;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class KeyguardFoldControllerConfigImpl implements KeyguardFoldControllerConfig {
    public final boolean isDebug() {
        return DeviceType.getDebugLevel() == DeviceType.DEBUG_LEVEL_MID || !DeviceType.isShipBuild();
    }
}
