package com.android.server.companion;

import android.os.Binder;
import android.provider.DeviceConfig;

public abstract class CompanionDeviceConfig {
    public static boolean isEnabled() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return DeviceConfig.getBoolean("companion", "enable_context_sync_telecom", false);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
