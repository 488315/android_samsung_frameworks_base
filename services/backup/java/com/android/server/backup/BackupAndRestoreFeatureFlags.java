package com.android.server.backup;

import android.os.IInstalld;
import android.provider.DeviceConfig;

/* loaded from: classes.dex */
public abstract class BackupAndRestoreFeatureFlags {
  public static long getBackupTransportFutureTimeoutMillis() {
    return DeviceConfig.getLong(
        "backup_and_restore", "backup_transport_future_timeout_millis", 600000L);
  }

  public static long getBackupTransportCallbackTimeoutMillis() {
    return DeviceConfig.getLong(
        "backup_and_restore",
        "backup_transport_callback_timeout_millis",
        BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS);
  }

  public static int getFullBackupWriteToTransportBufferSizeBytes() {
    return DeviceConfig.getInt(
        "backup_and_restore",
        "full_backup_write_to_transport_buffer_size_bytes",
        IInstalld.FLAG_FORCE);
  }

  public static int getFullBackupUtilsRouteBufferSizeBytes() {
    return DeviceConfig.getInt(
        "backup_and_restore", "full_backup_utils_route_buffer_size_bytes", 32768);
  }

  public static boolean getUnifiedRestoreContinueAfterTransportFailureInKvRestore() {
    return DeviceConfig.getBoolean(
        "backup_and_restore",
        "unified_restore_continue_after_transport_failure_in_kv_restore",
        true);
  }
}
