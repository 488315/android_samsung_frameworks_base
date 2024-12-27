package com.android.server.power.stats;

import android.os.BatteryStats;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;

import com.android.internal.os.PowerProfile;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AmbientDisplayPowerCalculator extends PowerCalculator {
    public final UsageBasedPowerEstimator[] mPowerEstimators;

    public AmbientDisplayPowerCalculator(PowerProfile powerProfile) {
        int numDisplays = powerProfile.getNumDisplays();
        this.mPowerEstimators = new UsageBasedPowerEstimator[numDisplays];
        for (int i = 0; i < numDisplays; i++) {
            this.mPowerEstimators[i] =
                    new UsageBasedPowerEstimator(
                            powerProfile.getAveragePowerForOrdinal("ambient.on.display", i));
        }
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final void calculate(
            BatteryUsageStats.Builder builder,
            BatteryStats batteryStats,
            long j,
            long j2,
            BatteryUsageStatsQuery batteryUsageStatsQuery) {
        double d;
        long screenDozeEnergyConsumptionUC = batteryStats.getScreenDozeEnergyConsumptionUC();
        int powerModel =
                PowerCalculator.getPowerModel(
                        screenDozeEnergyConsumptionUC, batteryUsageStatsQuery);
        long j3 = 1000;
        long screenDozeTime = batteryStats.getScreenDozeTime(j, 0) / 1000;
        if (powerModel != 2) {
            UsageBasedPowerEstimator[] usageBasedPowerEstimatorArr = this.mPowerEstimators;
            int length = usageBasedPowerEstimatorArr.length;
            d = 0.0d;
            int i = 0;
            while (i < length) {
                d +=
                        usageBasedPowerEstimatorArr[i].mAveragePowerMahPerMs
                                * (batteryStats.getDisplayScreenDozeTime(i, j) / j3);
                i++;
                j3 = 1000;
            }
        } else {
            d = screenDozeEnergyConsumptionUC * 2.777777777777778E-7d;
        }
        builder.getAggregateBatteryConsumerBuilder(0)
                .setUsageDurationMillis(15, screenDozeTime)
                .setConsumedPower(15, d, powerModel);
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final boolean isPowerComponentSupported(int i) {
        return i == 15;
    }
}
