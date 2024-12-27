package com.android.server.backup;

import android.provider.DeviceConfig;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class BackupAndRestoreFeatureFlags {
    public static long getBackupTransportFutureTimeoutMillis() {
        return DeviceConfig.getLong(
                "backup_and_restore", "backup_transport_future_timeout_millis", 600000L);
    }
}
