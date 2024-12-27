package com.android.server.wm;

import android.provider.DeviceConfig;

public final /* synthetic */ class ActivitySecurityModelFeatureFlags$$ExternalSyntheticLambda0
        implements DeviceConfig.OnPropertiesChangedListener {
    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
        ActivitySecurityModelFeatureFlags.sAsmToastsEnabled =
                DeviceConfig.getInt("window_manager", "ActivitySecurity__asm_toasts_enabled", 0);
        ActivitySecurityModelFeatureFlags.sAsmRestrictionsEnabled =
                DeviceConfig.getInt(
                        "window_manager", "ActivitySecurity__asm_restrictions_enabled", 0);
    }
}
