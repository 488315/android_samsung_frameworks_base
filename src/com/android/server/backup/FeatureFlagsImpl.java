package com.android.server.backup;

/* loaded from: classes6.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.server.backup.FeatureFlags
    public boolean enableClearPipeAfterRestoreFile() {
        return true;
    }

    @Override // com.android.server.backup.FeatureFlags
    public boolean enableIncreaseDatatypesForAgentLogging() {
        return true;
    }

    @Override // com.android.server.backup.FeatureFlags
    public boolean enableIncreasedBmmLoggingForRestoreAtInstall() {
        return false;
    }

    @Override // com.android.server.backup.FeatureFlags
    public boolean enableMaxSizeWritesToPipes() {
        return true;
    }

    @Override // com.android.server.backup.FeatureFlags
    public boolean enableMetricsSettingsBackupAgents() {
        return true;
    }

    @Override // com.android.server.backup.FeatureFlags
    public boolean enableMetricsSystemBackupAgents() {
        return true;
    }

    @Override // com.android.server.backup.FeatureFlags
    public boolean enableReadAllExternalStorageFiles() {
        return false;
    }

    @Override // com.android.server.backup.FeatureFlags
    public boolean enableRestrictedModeChanges() {
        return true;
    }

    @Override // com.android.server.backup.FeatureFlags
    public boolean enableSkippingRestoreLaunchedApps() {
        return false;
    }

    @Override // com.android.server.backup.FeatureFlags
    public boolean enableVToURestoreForSystemComponentsInAllowlist() {
        return false;
    }
}
