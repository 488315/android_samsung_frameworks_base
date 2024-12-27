package com.android.server.backup;

public interface FeatureFlags {
    boolean enableClearPipeAfterRestoreFile();

    boolean enableIncreaseDatatypesForAgentLogging();

    boolean enableIncreasedBmmLoggingForRestoreAtInstall();

    boolean enableMaxSizeWritesToPipes();

    boolean enableMetricsSystemBackupAgents();

    boolean enableSkippingRestoreLaunchedApps();

    boolean enableVToURestoreForSystemComponentsInAllowlist();
}
