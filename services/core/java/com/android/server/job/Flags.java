package com.android.server.job;

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
