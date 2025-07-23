package com.android.modules.expresslog;

/* loaded from: classes6.dex */
public final class Counter {
    private Counter() {
    }

    public static void logIncrement(String str) {
        logIncrement(str, 1L);
    }

    public static void logIncrementWithUid(String str, int i) {
        logIncrementWithUid(str, i, 1L);
    }

    public static void logIncrement(String str, long j) {
        long metricIdHash = MetricIds.getMetricIdHash(str, 1);
        if (metricIdHash != 0) {
            StatsExpressLog.write(528, metricIdHash, j);
        }
    }

    public static void logIncrementWithUid(String str, int i, long j) {
        long metricIdHash = MetricIds.getMetricIdHash(str, 3);
        if (metricIdHash != 0) {
            StatsExpressLog.write(644, metricIdHash, j, i);
        }
    }
}
