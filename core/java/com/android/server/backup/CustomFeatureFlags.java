package com.android.server.backup;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet =
            new HashSet(
                    Arrays.asList(
                            Flags.FLAG_ENABLE_CLEAR_PIPE_AFTER_RESTORE_FILE,
                            Flags.FLAG_ENABLE_INCREASE_DATATYPES_FOR_AGENT_LOGGING,
                            Flags.FLAG_ENABLE_INCREASED_BMM_LOGGING_FOR_RESTORE_AT_INSTALL,
                            Flags.FLAG_ENABLE_MAX_SIZE_WRITES_TO_PIPES,
                            Flags.FLAG_ENABLE_METRICS_SYSTEM_BACKUP_AGENTS,
                            Flags.FLAG_ENABLE_SKIPPING_RESTORE_LAUNCHED_APPS,
                            Flags.FLAG_ENABLE_V_TO_U_RESTORE_FOR_SYSTEM_COMPONENTS_IN_ALLOWLIST,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.server.backup.FeatureFlags
    public boolean enableClearPipeAfterRestoreFile() {
        return getValue(
                Flags.FLAG_ENABLE_CLEAR_PIPE_AFTER_RESTORE_FILE,
                new Predicate() { // from class:
                                  // com.android.server.backup.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableClearPipeAfterRestoreFile();
                    }
                });
    }

    @Override // com.android.server.backup.FeatureFlags
    public boolean enableIncreaseDatatypesForAgentLogging() {
        return getValue(
                Flags.FLAG_ENABLE_INCREASE_DATATYPES_FOR_AGENT_LOGGING,
                new Predicate() { // from class:
                                  // com.android.server.backup.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableIncreaseDatatypesForAgentLogging();
                    }
                });
    }

    @Override // com.android.server.backup.FeatureFlags
    public boolean enableIncreasedBmmLoggingForRestoreAtInstall() {
        return getValue(
                Flags.FLAG_ENABLE_INCREASED_BMM_LOGGING_FOR_RESTORE_AT_INSTALL,
                new Predicate() { // from class:
                                  // com.android.server.backup.CustomFeatureFlags$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableIncreasedBmmLoggingForRestoreAtInstall();
                    }
                });
    }

    @Override // com.android.server.backup.FeatureFlags
    public boolean enableMaxSizeWritesToPipes() {
        return getValue(
                Flags.FLAG_ENABLE_MAX_SIZE_WRITES_TO_PIPES,
                new Predicate() { // from class:
                                  // com.android.server.backup.CustomFeatureFlags$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableMaxSizeWritesToPipes();
                    }
                });
    }

    @Override // com.android.server.backup.FeatureFlags
    public boolean enableMetricsSystemBackupAgents() {
        return getValue(
                Flags.FLAG_ENABLE_METRICS_SYSTEM_BACKUP_AGENTS,
                new Predicate() { // from class:
                                  // com.android.server.backup.CustomFeatureFlags$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableMetricsSystemBackupAgents();
                    }
                });
    }

    @Override // com.android.server.backup.FeatureFlags
    public boolean enableSkippingRestoreLaunchedApps() {
        return getValue(
                Flags.FLAG_ENABLE_SKIPPING_RESTORE_LAUNCHED_APPS,
                new Predicate() { // from class:
                                  // com.android.server.backup.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enableSkippingRestoreLaunchedApps();
                    }
                });
    }

    @Override // com.android.server.backup.FeatureFlags
    public boolean enableVToURestoreForSystemComponentsInAllowlist() {
        return getValue(
                Flags.FLAG_ENABLE_V_TO_U_RESTORE_FOR_SYSTEM_COMPONENTS_IN_ALLOWLIST,
                new Predicate() { // from class:
                                  // com.android.server.backup.CustomFeatureFlags$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj)
                                .enableVToURestoreForSystemComponentsInAllowlist();
                    }
                });
    }

    public boolean isFlagReadOnlyOptimized(String flagName) {
        if (this.mReadOnlyFlagsSet.contains(flagName) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean isOptimizationEnabled() {
        return false;
    }

    protected boolean getValue(String flagName, Predicate<FeatureFlags> getter) {
        return this.mGetValueImpl.test(flagName, getter);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(
                Flags.FLAG_ENABLE_CLEAR_PIPE_AFTER_RESTORE_FILE,
                Flags.FLAG_ENABLE_INCREASE_DATATYPES_FOR_AGENT_LOGGING,
                Flags.FLAG_ENABLE_INCREASED_BMM_LOGGING_FOR_RESTORE_AT_INSTALL,
                Flags.FLAG_ENABLE_MAX_SIZE_WRITES_TO_PIPES,
                Flags.FLAG_ENABLE_METRICS_SYSTEM_BACKUP_AGENTS,
                Flags.FLAG_ENABLE_SKIPPING_RESTORE_LAUNCHED_APPS,
                Flags.FLAG_ENABLE_V_TO_U_RESTORE_FOR_SYSTEM_COMPONENTS_IN_ALLOWLIST);
    }
}
