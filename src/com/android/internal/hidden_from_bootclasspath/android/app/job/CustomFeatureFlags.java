package com.android.internal.hidden_from_bootclasspath.android.app.job;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class CustomFeatureFlags implements FeatureFlags {
    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;
    private Set<String> mReadOnlyFlagsSet = new HashSet(Arrays.asList(Flags.FLAG_ADD_TYPE_INFO_TO_WAKELOCK_TAG, Flags.FLAG_ENFORCE_MINIMUM_TIME_WINDOWS, Flags.FLAG_GET_PENDING_JOB_REASONS_API, Flags.FLAG_GET_PENDING_JOB_REASONS_HISTORY_API, Flags.FLAG_HANDLE_ABANDONED_JOBS, Flags.FLAG_IGNORE_IMPORTANT_WHILE_FOREGROUND, Flags.FLAG_JOB_DEBUG_INFO_APIS, ""));

    private boolean isOptimizationEnabled() {
        return false;
    }

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        this.mGetValueImpl = biPredicate;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.job.FeatureFlags
    public boolean addTypeInfoToWakelockTag() {
        return getValue(Flags.FLAG_ADD_TYPE_INFO_TO_WAKELOCK_TAG, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.job.CustomFeatureFlags$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).addTypeInfoToWakelockTag();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.job.FeatureFlags
    public boolean enforceMinimumTimeWindows() {
        return getValue(Flags.FLAG_ENFORCE_MINIMUM_TIME_WINDOWS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.job.CustomFeatureFlags$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).enforceMinimumTimeWindows();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.job.FeatureFlags
    public boolean getPendingJobReasonsApi() {
        return getValue(Flags.FLAG_GET_PENDING_JOB_REASONS_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.job.CustomFeatureFlags$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).getPendingJobReasonsApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.job.FeatureFlags
    public boolean getPendingJobReasonsHistoryApi() {
        return getValue(Flags.FLAG_GET_PENDING_JOB_REASONS_HISTORY_API, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.job.CustomFeatureFlags$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).getPendingJobReasonsHistoryApi();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.job.FeatureFlags
    public boolean handleAbandonedJobs() {
        return getValue(Flags.FLAG_HANDLE_ABANDONED_JOBS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.job.CustomFeatureFlags$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).handleAbandonedJobs();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.job.FeatureFlags
    public boolean ignoreImportantWhileForeground() {
        return getValue(Flags.FLAG_IGNORE_IMPORTANT_WHILE_FOREGROUND, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.job.CustomFeatureFlags$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).ignoreImportantWhileForeground();
            }
        });
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.job.FeatureFlags
    public boolean jobDebugInfoApis() {
        return getValue(Flags.FLAG_JOB_DEBUG_INFO_APIS, new Predicate() { // from class: com.android.internal.hidden_from_bootclasspath.android.app.job.CustomFeatureFlags$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((FeatureFlags) obj).jobDebugInfoApis();
            }
        });
    }

    public boolean isFlagReadOnlyOptimized(String str) {
        return this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled();
    }

    protected boolean getValue(String str, Predicate<FeatureFlags> predicate) {
        return this.mGetValueImpl.test(str, predicate);
    }

    public List<String> getFlagNames() {
        return Arrays.asList(Flags.FLAG_ADD_TYPE_INFO_TO_WAKELOCK_TAG, Flags.FLAG_ENFORCE_MINIMUM_TIME_WINDOWS, Flags.FLAG_GET_PENDING_JOB_REASONS_API, Flags.FLAG_GET_PENDING_JOB_REASONS_HISTORY_API, Flags.FLAG_HANDLE_ABANDONED_JOBS, Flags.FLAG_IGNORE_IMPORTANT_WHILE_FOREGROUND, Flags.FLAG_JOB_DEBUG_INFO_APIS);
    }
}
