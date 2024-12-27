package com.android.server.power.stats;

import android.os.BatteryStats;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;

public final class PowerSharingCalculator extends PowerCalculator {
    @Override // com.android.server.power.stats.PowerCalculator
    public final void calculate(
            BatteryUsageStats.Builder builder,
            BatteryStats batteryStats,
            long j,
            long j2,
            BatteryUsageStatsQuery batteryUsageStatsQuery) {
        long txPowerSharingTime = batteryStats.getTxPowerSharingTime(j, 0) / 1000;
        double txSharingDischargeAmount = batteryStats.getTxSharingDischargeAmount();
        if (txSharingDischargeAmount != 0.0d) {
            builder.getAggregateBatteryConsumerBuilder(0)
                    .setConsumedPower(18, txSharingDischargeAmount, 1)
                    .setUsageDurationMillis(18, txPowerSharingTime);
        }
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final boolean isPowerComponentSupported(int i) {
        return i == 18;
    }
}
