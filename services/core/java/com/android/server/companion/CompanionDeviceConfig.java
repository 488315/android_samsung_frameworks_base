package com.android.server.companion;

import android.os.Binder;
import android.provider.DeviceConfig;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
