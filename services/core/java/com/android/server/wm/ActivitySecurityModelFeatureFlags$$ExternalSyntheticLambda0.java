package com.android.server.wm;

import android.provider.DeviceConfig;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
