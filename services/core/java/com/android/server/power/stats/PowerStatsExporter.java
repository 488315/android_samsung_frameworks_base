package com.android.server.power.stats;

import java.util.concurrent.TimeUnit;

public final class PowerStatsExporter {
    public static final long BATTERY_SESSION_TIME_SPAN_SLACK_MILLIS = TimeUnit.MINUTES.toMillis(2);
    public final long mBatterySessionTimeSpanSlackMillis = BATTERY_SESSION_TIME_SPAN_SLACK_MILLIS;
    public final PowerStatsAggregator mPowerStatsAggregator;
    public final PowerStatsStore mPowerStatsStore;

    public PowerStatsExporter(
            PowerStatsStore powerStatsStore, PowerStatsAggregator powerStatsAggregator) {
        this.mPowerStatsStore = powerStatsStore;
        this.mPowerStatsAggregator = powerStatsAggregator;
    }

    public static boolean areMatchingStates(int[] iArr, int i, int i2) {
        if (i != 1) {
            if (i == 2 && iArr[1] != 1) {
                return false;
            }
        } else if (iArr[1] != 0) {
            return false;
        }
        if (i2 != 1) {
            if (i2 == 2 && iArr[0] != 1) {
                return false;
            }
        } else if (iArr[0] != 0) {
            return false;
        }
        return true;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void populateBatteryUsageStatsBuilder(
            android.os.BatteryUsageStats.Builder r32,
            com.android.server.power.stats.AggregatedPowerStats r33) {
        /*
            Method dump skipped, instructions count: 742
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.power.stats.PowerStatsExporter.populateBatteryUsageStatsBuilder(android.os.BatteryUsageStats$Builder,"
                    + " com.android.server.power.stats.AggregatedPowerStats):void");
    }
}
