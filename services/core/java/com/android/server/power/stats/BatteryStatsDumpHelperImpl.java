package com.android.server.power.stats;

import android.os.BatteryStats;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;

public final class BatteryStatsDumpHelperImpl implements BatteryStats.BatteryStatsDumpHelper {
    public final BatteryUsageStatsProvider mBatteryUsageStatsProvider;

    public BatteryStatsDumpHelperImpl(BatteryUsageStatsProvider batteryUsageStatsProvider) {
        this.mBatteryUsageStatsProvider = batteryUsageStatsProvider;
    }

    public final BatteryUsageStats getBatteryUsageStats(BatteryStats batteryStats, boolean z) {
        BatteryUsageStatsQuery.Builder maxStatsAgeMs =
                new BatteryUsageStatsQuery.Builder().setMaxStatsAgeMs(0L);
        if (z) {
            maxStatsAgeMs.includePowerModels().includeProcessStateData().includeVirtualUids();
        }
        BatteryUsageStatsProvider batteryUsageStatsProvider = this.mBatteryUsageStatsProvider;
        return batteryUsageStatsProvider.getBatteryUsageStats(
                (BatteryStatsImpl) batteryStats,
                maxStatsAgeMs.build(),
                batteryUsageStatsProvider.mClock.currentTimeMillis());
    }
}
