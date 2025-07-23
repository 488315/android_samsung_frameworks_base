package com.android.internal.hidden_from_bootclasspath.android.app.job;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ADD_TYPE_INFO_TO_WAKELOCK_TAG = "android.app.job.add_type_info_to_wakelock_tag";
    public static final String FLAG_ENFORCE_MINIMUM_TIME_WINDOWS = "android.app.job.enforce_minimum_time_windows";
    public static final String FLAG_GET_PENDING_JOB_REASONS_API = "android.app.job.get_pending_job_reasons_api";
    public static final String FLAG_GET_PENDING_JOB_REASONS_HISTORY_API = "android.app.job.get_pending_job_reasons_history_api";
    public static final String FLAG_HANDLE_ABANDONED_JOBS = "android.app.job.handle_abandoned_jobs";
    public static final String FLAG_IGNORE_IMPORTANT_WHILE_FOREGROUND = "android.app.job.ignore_important_while_foreground";
    public static final String FLAG_JOB_DEBUG_INFO_APIS = "android.app.job.job_debug_info_apis";

    public static boolean addTypeInfoToWakelockTag() {
        return FEATURE_FLAGS.addTypeInfoToWakelockTag();
    }

    public static boolean enforceMinimumTimeWindows() {
        return FEATURE_FLAGS.enforceMinimumTimeWindows();
    }

    public static boolean getPendingJobReasonsApi() {
        return FEATURE_FLAGS.getPendingJobReasonsApi();
    }

    public static boolean getPendingJobReasonsHistoryApi() {
        return FEATURE_FLAGS.getPendingJobReasonsHistoryApi();
    }

    public static boolean handleAbandonedJobs() {
        return FEATURE_FLAGS.handleAbandonedJobs();
    }

    public static boolean ignoreImportantWhileForeground() {
        return FEATURE_FLAGS.ignoreImportantWhileForeground();
    }

    public static boolean jobDebugInfoApis() {
        return FEATURE_FLAGS.jobDebugInfoApis();
    }
}
