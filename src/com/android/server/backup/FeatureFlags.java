package com.android.server.backup;

/* loaded from: classes6.dex */
public interface FeatureFlags {
    boolean enableClearPipeAfterRestoreFile();

    boolean enableIncreaseDatatypesForAgentLogging();

    boolean enableIncreasedBmmLoggingForRestoreAtInstall();

    boolean enableMaxSizeWritesToPipes();

    boolean enableMetricsSettingsBackupAgents();

    boolean enableMetricsSystemBackupAgents();

    boolean enableReadAllExternalStorageFiles();

    boolean enableRestrictedModeChanges();

    boolean enableSkippingRestoreLaunchedApps();

    boolean enableVToURestoreForSystemComponentsInAllowlist();
}
