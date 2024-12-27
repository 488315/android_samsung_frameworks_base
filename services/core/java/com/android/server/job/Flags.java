package com.android.server.job;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class Flags {
    public static boolean batchActiveBucketJobs() {
        return false;
    }

    public static boolean batchConnectivityJobsPerNetwork() {
        return false;
    }

    public static boolean countQuotaFix() {
        return true;
    }

    public static boolean doNotForceRushExecutionAtBoot() {
        return false;
    }

    public static boolean relaxPrefetchConnectivityConstraintOnlyOnCharger() {
        return true;
    }

    public static boolean thermalRestrictionsToFgsJobs() {
        return false;
    }
}
