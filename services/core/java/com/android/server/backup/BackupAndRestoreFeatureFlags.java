package com.android.server.backup;

import android.provider.DeviceConfig;

public abstract class BackupAndRestoreFeatureFlags {
    public static long getBackupTransportFutureTimeoutMillis() {
        return DeviceConfig.getLong(
                "backup_and_restore", "backup_transport_future_timeout_millis", 600000L);
    }
}
