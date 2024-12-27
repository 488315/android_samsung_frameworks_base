package com.android.internal.hidden_from_bootclasspath.android.app.job;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.internal.hidden_from_bootclasspath.android.app.job.FeatureFlags
    public boolean backupJobsExemption() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.job.FeatureFlags
    public boolean enforceMinimumTimeWindows() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.app.job.FeatureFlags
    public boolean jobDebugInfoApis() {
        return true;
    }
}
