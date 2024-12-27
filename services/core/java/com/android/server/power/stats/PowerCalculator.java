package com.android.server.power.stats;

import android.os.BatteryStats;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;

public abstract class PowerCalculator {
    public static int getPowerModel(long j, BatteryUsageStatsQuery batteryUsageStatsQuery) {
        return (j == -1 || batteryUsageStatsQuery.shouldForceUsePowerProfileModel()) ? 1 : 2;
    }

    public abstract void calculate(
            BatteryUsageStats.Builder builder,
            BatteryStats batteryStats,
            long j,
            long j2,
            BatteryUsageStatsQuery batteryUsageStatsQuery);

    public abstract boolean isPowerComponentSupported(int i);
}
