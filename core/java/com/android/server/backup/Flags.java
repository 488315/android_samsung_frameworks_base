package com.android.server.backup;

public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ENABLE_CLEAR_PIPE_AFTER_RESTORE_FILE =
            "com.android.server.backup.enable_clear_pipe_after_restore_file";
    public static final String FLAG_ENABLE_INCREASED_BMM_LOGGING_FOR_RESTORE_AT_INSTALL =
            "com.android.server.backup.enable_increased_bmm_logging_for_restore_at_install";
    public static final String FLAG_ENABLE_INCREASE_DATATYPES_FOR_AGENT_LOGGING =
            "com.android.server.backup.enable_increase_datatypes_for_agent_logging";
    public static final String FLAG_ENABLE_MAX_SIZE_WRITES_TO_PIPES =
            "com.android.server.backup.enable_max_size_writes_to_pipes";
    public static final String FLAG_ENABLE_METRICS_SYSTEM_BACKUP_AGENTS =
            "com.android.server.backup.enable_metrics_system_backup_agents";
    public static final String FLAG_ENABLE_SKIPPING_RESTORE_LAUNCHED_APPS =
            "com.android.server.backup.enable_skipping_restore_launched_apps";
    public static final String FLAG_ENABLE_V_TO_U_RESTORE_FOR_SYSTEM_COMPONENTS_IN_ALLOWLIST =
            "com.android.server.backup.enable_v_to_u_restore_for_system_components_in_allowlist";

    public static boolean enableClearPipeAfterRestoreFile() {
        return FEATURE_FLAGS.enableClearPipeAfterRestoreFile();
    }

    public static boolean enableIncreaseDatatypesForAgentLogging() {
        return FEATURE_FLAGS.enableIncreaseDatatypesForAgentLogging();
    }

    public static boolean enableIncreasedBmmLoggingForRestoreAtInstall() {
        return FEATURE_FLAGS.enableIncreasedBmmLoggingForRestoreAtInstall();
    }

    public static boolean enableMaxSizeWritesToPipes() {
        return FEATURE_FLAGS.enableMaxSizeWritesToPipes();
    }

    public static boolean enableMetricsSystemBackupAgents() {
        return FEATURE_FLAGS.enableMetricsSystemBackupAgents();
    }

    public static boolean enableSkippingRestoreLaunchedApps() {
        return FEATURE_FLAGS.enableSkippingRestoreLaunchedApps();
    }

    public static boolean enableVToURestoreForSystemComponentsInAllowlist() {
        return FEATURE_FLAGS.enableVToURestoreForSystemComponentsInAllowlist();
    }
}
