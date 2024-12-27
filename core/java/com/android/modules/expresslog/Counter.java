package com.android.modules.expresslog;

public final class Counter {
    private Counter() {}

    public static void logIncrement(String metricId) {
        logIncrement(metricId, 1L);
    }

    public static void logIncrementWithUid(String metricId, int uid) {
        logIncrementWithUid(metricId, uid, 1L);
    }

    public static void logIncrement(String metricId, long amount) {
        long metricIdHash = MetricIds.getMetricIdHash(metricId, 1);
        StatsExpressLog.write(528, metricIdHash, amount);
    }

    public static void logIncrementWithUid(String metricId, int uid, long amount) {
        long metricIdHash = MetricIds.getMetricIdHash(metricId, 3);
        StatsExpressLog.write(644, metricIdHash, amount, uid);
    }
}
