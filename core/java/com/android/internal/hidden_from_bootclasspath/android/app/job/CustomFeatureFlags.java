package com.android.internal.hidden_from_bootclasspath.android.app.job;

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
                            Flags.FLAG_BACKUP_JOBS_EXEMPTION,
                            Flags.FLAG_ENFORCE_MINIMUM_TIME_WINDOWS,
                            Flags.FLAG_JOB_DEBUG_INFO_APIS,
                            ""));

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {
        this.mGetValueImpl = getValueImpl;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.job.FeatureFlags
    public boolean backupJobsExemption() {
        return getValue(
                Flags.FLAG_BACKUP_JOBS_EXEMPTION,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.app.job.CustomFeatureFlags$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).backupJobsExemption();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.job.FeatureFlags
    public boolean enforceMinimumTimeWindows() {
        return getValue(
                Flags.FLAG_ENFORCE_MINIMUM_TIME_WINDOWS,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.app.job.CustomFeatureFlags$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).enforceMinimumTimeWindows();
                    }
                });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.job.FeatureFlags
    public boolean jobDebugInfoApis() {
        return getValue(
                Flags.FLAG_JOB_DEBUG_INFO_APIS,
                new Predicate() { // from class:
                                  // com.android.internal.hidden_from_bootclasspath.android.app.job.CustomFeatureFlags$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((FeatureFlags) obj).jobDebugInfoApis();
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
                Flags.FLAG_BACKUP_JOBS_EXEMPTION,
                Flags.FLAG_ENFORCE_MINIMUM_TIME_WINDOWS,
                Flags.FLAG_JOB_DEBUG_INFO_APIS);
    }
}
