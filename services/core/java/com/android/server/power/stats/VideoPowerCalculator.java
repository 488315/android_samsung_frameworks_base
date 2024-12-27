package com.android.server.power.stats;

import android.os.BatteryStats;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;
import android.os.UidBatteryConsumer;
import android.util.SparseArray;

import com.android.internal.os.PowerProfile;

public final class VideoPowerCalculator extends PowerCalculator {
    public final UsageBasedPowerEstimator mPowerEstimator;

    public VideoPowerCalculator(PowerProfile powerProfile) {
        this.mPowerEstimator = new UsageBasedPowerEstimator(powerProfile.getAveragePower("video"));
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final void calculate(
            BatteryUsageStats.Builder builder,
            BatteryStats batteryStats,
            long j,
            long j2,
            BatteryUsageStatsQuery batteryUsageStatsQuery) {
        SparseArray uidBatteryConsumerBuilders = builder.getUidBatteryConsumerBuilders();
        double d = 0.0d;
        long j3 = 0;
        for (int size = uidBatteryConsumerBuilders.size() - 1; size >= 0; size--) {
            UidBatteryConsumer.Builder builder2 =
                    (UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.valueAt(size);
            BatteryStats.Timer videoTurnedOnTimer =
                    builder2.getBatteryStatsUid().getVideoTurnedOnTimer();
            UsageBasedPowerEstimator usageBasedPowerEstimator = this.mPowerEstimator;
            usageBasedPowerEstimator.getClass();
            double d2 = d;
            long calculateDuration =
                    UsageBasedPowerEstimator.calculateDuration(videoTurnedOnTimer, j);
            double d3 = usageBasedPowerEstimator.mAveragePowerMahPerMs * calculateDuration;
            builder2.setUsageDurationMillis(5, calculateDuration).setConsumedPower(5, d3);
            if (builder2.isVirtualUid()) {
                d = d2;
            } else {
                j3 += calculateDuration;
                d = d2 + d3;
            }
        }
        builder.getAggregateBatteryConsumerBuilder(0)
                .setUsageDurationMillis(5, j3)
                .setConsumedPower(5, d);
        builder.getAggregateBatteryConsumerBuilder(1)
                .setUsageDurationMillis(5, j3)
                .setConsumedPower(5, d);
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final boolean isPowerComponentSupported(int i) {
        return i == 5;
    }
}
