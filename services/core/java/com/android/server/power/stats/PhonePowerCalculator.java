package com.android.server.power.stats;

import android.os.BatteryStats;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;

import com.android.internal.os.PowerProfile;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PhonePowerCalculator extends PowerCalculator {
    public final UsageBasedPowerEstimator mPowerEstimator;

    public PhonePowerCalculator(PowerProfile powerProfile) {
        this.mPowerEstimator =
                new UsageBasedPowerEstimator(
                        powerProfile.getAveragePowerOrDefault("radio.active", 90.0d));
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final void calculate(
            BatteryUsageStats.Builder builder,
            BatteryStats batteryStats,
            long j,
            long j2,
            BatteryUsageStatsQuery batteryUsageStatsQuery) {
        double d;
        double d2;
        long phoneEnergyConsumptionUC = batteryStats.getPhoneEnergyConsumptionUC();
        int powerModel =
                PowerCalculator.getPowerModel(phoneEnergyConsumptionUC, batteryUsageStatsQuery);
        long phoneOnTime = batteryStats.getPhoneOnTime(j, 0) / 1000;
        if (powerModel != 2) {
            d = this.mPowerEstimator.mAveragePowerMahPerMs;
            d2 = phoneOnTime;
        } else {
            d = phoneEnergyConsumptionUC;
            d2 = 2.777777777777778E-7d;
        }
        double d3 = d * d2;
        if (d3 == 0.0d) {
            return;
        }
        builder.getAggregateBatteryConsumerBuilder(0)
                .setConsumedPower(14, d3, powerModel)
                .setUsageDurationMillis(14, phoneOnTime);
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final boolean isPowerComponentSupported(int i) {
        return i == 14;
    }
}
