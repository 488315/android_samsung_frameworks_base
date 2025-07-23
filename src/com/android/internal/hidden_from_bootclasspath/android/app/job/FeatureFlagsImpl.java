package com.android.internal.hidden_from_bootclasspath.android.app.job;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.internal.hidden_from_bootclasspath.android.app.job.FeatureFlags
    public boolean addTypeInfoToWakelockTag() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.job.FeatureFlags
    public boolean enforceMinimumTimeWindows() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.job.FeatureFlags
    public boolean getPendingJobReasonsApi() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.job.FeatureFlags
    public boolean getPendingJobReasonsHistoryApi() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.job.FeatureFlags
    public boolean handleAbandonedJobs() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.job.FeatureFlags
    public boolean ignoreImportantWhileForeground() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.job.FeatureFlags
    public boolean jobDebugInfoApis() {
        return true;
    }
}
