package com.android.server.power.stats;

import android.os.BatteryStats;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;
import android.os.UidBatteryConsumer;
import android.util.SparseArray;

import com.android.internal.os.PowerProfile;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CameraPowerCalculator extends PowerCalculator {
    public final UsageBasedPowerEstimator mPowerEstimator;

    public CameraPowerCalculator(PowerProfile powerProfile) {
        this.mPowerEstimator =
                new UsageBasedPowerEstimator(powerProfile.getAveragePower("camera.avg"));
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
        UidBatteryConsumer.Builder builder2;
        double d3;
        SparseArray uidBatteryConsumerBuilders = builder.getUidBatteryConsumerBuilders();
        for (int size = uidBatteryConsumerBuilders.size() - 1; size >= 0; size--) {
            UidBatteryConsumer.Builder builder3 =
                    (UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.valueAt(size);
            BatteryStats.Uid batteryStatsUid = builder3.getBatteryStatsUid();
            long cameraEnergyConsumptionUC =
                    builder3.getBatteryStatsUid().getCameraEnergyConsumptionUC();
            int powerModel =
                    PowerCalculator.getPowerModel(
                            cameraEnergyConsumptionUC, batteryUsageStatsQuery);
            BatteryStats.Timer cameraTurnedOnTimer = batteryStatsUid.getCameraTurnedOnTimer();
            UsageBasedPowerEstimator usageBasedPowerEstimator = this.mPowerEstimator;
            usageBasedPowerEstimator.getClass();
            long calculateDuration =
                    UsageBasedPowerEstimator.calculateDuration(cameraTurnedOnTimer, j);
            if (powerModel == 2) {
                d3 = cameraEnergyConsumptionUC * 2.777777777777778E-7d;
                builder2 = builder3;
            } else {
                builder2 = builder3;
                d3 = usageBasedPowerEstimator.mAveragePowerMahPerMs * calculateDuration;
            }
            builder2.setUsageDurationMillis(3, calculateDuration)
                    .setConsumedPower(3, d3, powerModel);
        }
        long cameraEnergyConsumptionUC2 = batteryStats.getCameraEnergyConsumptionUC();
        int powerModel2 =
                PowerCalculator.getPowerModel(cameraEnergyConsumptionUC2, batteryUsageStatsQuery);
        long cameraOnTime = batteryStats.getCameraOnTime(j, 0) / 1000;
        if (powerModel2 == 2) {
            d = cameraEnergyConsumptionUC2;
            d2 = 2.777777777777778E-7d;
        } else {
            d = this.mPowerEstimator.mAveragePowerMahPerMs;
            d2 = cameraOnTime;
        }
        double d4 = d * d2;
        builder.getAggregateBatteryConsumerBuilder(0)
                .setUsageDurationMillis(3, cameraOnTime)
                .setConsumedPower(3, d4, powerModel2);
        builder.getAggregateBatteryConsumerBuilder(1)
                .setUsageDurationMillis(3, cameraOnTime)
                .setConsumedPower(3, d4, powerModel2);
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final boolean isPowerComponentSupported(int i) {
        return i == 3;
    }
}
