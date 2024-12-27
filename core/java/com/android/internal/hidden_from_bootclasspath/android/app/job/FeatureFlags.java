package com.android.internal.hidden_from_bootclasspath.android.app.job;

public interface FeatureFlags {
    boolean backupJobsExemption();

    boolean enforceMinimumTimeWindows();

    boolean jobDebugInfoApis();
}
