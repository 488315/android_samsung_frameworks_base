package com.android.server.companion;

import android.os.Binder;
import android.provider.DeviceConfig;

/* loaded from: classes.dex */
public abstract class CompanionDeviceConfig {
    public static boolean isEnabled(String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return DeviceConfig.getBoolean("companion", str, false);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
