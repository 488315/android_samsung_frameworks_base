package com.android.internal.hidden_from_bootclasspath.android.app.job;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_BACKUP_JOBS_EXEMPTION = "android.app.job.backup_jobs_exemption";
    public static final String FLAG_ENFORCE_MINIMUM_TIME_WINDOWS = "android.app.job.enforce_minimum_time_windows";
    public static final String FLAG_JOB_DEBUG_INFO_APIS = "android.app.job.job_debug_info_apis";

    public static boolean backupJobsExemption() {
        return FEATURE_FLAGS.backupJobsExemption();
    }

    public static boolean enforceMinimumTimeWindows() {
        return FEATURE_FLAGS.enforceMinimumTimeWindows();
    }

    public static boolean jobDebugInfoApis() {
        return FEATURE_FLAGS.jobDebugInfoApis();
    }
}
