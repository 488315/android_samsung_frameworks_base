package com.android.server.power.stats;

import android.os.BatteryStats;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;
import android.os.UidBatteryConsumer;
import android.util.SparseArray;

import com.android.internal.os.CpuScalingPolicies;
import com.android.internal.os.PowerProfile;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SystemServicePowerCalculator extends PowerCalculator {
    public final CpuPowerCalculator mCpuPowerCalculator;
    public final UsageBasedPowerEstimator[] mPowerEstimators;

    public SystemServicePowerCalculator(
            CpuScalingPolicies cpuScalingPolicies, PowerProfile powerProfile) {
        this.mCpuPowerCalculator = new CpuPowerCalculator(cpuScalingPolicies, powerProfile);
        this.mPowerEstimators =
                new UsageBasedPowerEstimator[cpuScalingPolicies.getScalingStepCount()];
        int i = 0;
        for (int i2 : cpuScalingPolicies.getPolicies()) {
            int length = cpuScalingPolicies.getFrequencies(i2).length;
            int i3 = 0;
            while (i3 < length) {
                this.mPowerEstimators[i] =
                        new UsageBasedPowerEstimator(
                                powerProfile.getAveragePowerForCpuScalingStep(i2, i3));
                i3++;
                i++;
            }
        }
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final void calculate(
            BatteryUsageStats.Builder builder,
            BatteryStats batteryStats,
            long j,
            long j2,
            BatteryUsageStatsQuery batteryUsageStatsQuery) {
        double calculatePowerUsingPowerProfile;
        BatteryStats.Uid uid = (BatteryStats.Uid) batteryStats.getUidStats().get(1000);
        if (uid == null) {
            return;
        }
        long cpuEnergyConsumptionUC = uid.getCpuEnergyConsumptionUC();
        int powerModel =
                PowerCalculator.getPowerModel(cpuEnergyConsumptionUC, batteryUsageStatsQuery);
        if (powerModel == 2) {
            double calculatePowerUsingPowerProfile2 = calculatePowerUsingPowerProfile(batteryStats);
            CpuPowerCalculator cpuPowerCalculator = this.mCpuPowerCalculator;
            cpuPowerCalculator.getClass();
            double calculateUidModeledPowerMah =
                    cpuPowerCalculator.calculateUidModeledPowerMah(
                            uid,
                            uid.getCpuActiveTime(),
                            uid.getCpuClusterTimes(),
                            uid.getCpuFreqTimes(0));
            calculatePowerUsingPowerProfile = 0.0d;
            if (calculateUidModeledPowerMah > 0.0d) {
                calculatePowerUsingPowerProfile =
                        ((cpuEnergyConsumptionUC * 2.777777777777778E-7d)
                                        * calculatePowerUsingPowerProfile2)
                                / calculateUidModeledPowerMah;
            }
        } else {
            calculatePowerUsingPowerProfile = calculatePowerUsingPowerProfile(batteryStats);
        }
        SparseArray uidBatteryConsumerBuilders = builder.getUidBatteryConsumerBuilders();
        UidBatteryConsumer.Builder builder2 =
                (UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.get(1000);
        if (builder2 != null) {
            calculatePowerUsingPowerProfile =
                    Math.min(calculatePowerUsingPowerProfile, builder2.getTotalPower());
            builder2.setConsumedPower(17, -calculatePowerUsingPowerProfile, powerModel);
        }
        for (int size = uidBatteryConsumerBuilders.size() - 1; size >= 0; size--) {
            UidBatteryConsumer.Builder builder3 =
                    (UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.valueAt(size);
            if (builder3 != builder2) {
                builder3.setConsumedPower(
                        7,
                        builder3.getBatteryStatsUid().getProportionalSystemServiceUsage()
                                * calculatePowerUsingPowerProfile,
                        powerModel);
            }
        }
        builder.getAggregateBatteryConsumerBuilder(0)
                .setConsumedPower(7, calculatePowerUsingPowerProfile);
        builder.getAggregateBatteryConsumerBuilder(1)
                .setConsumedPower(7, calculatePowerUsingPowerProfile);
    }

    public final double calculatePowerUsingPowerProfile(BatteryStats batteryStats) {
        long[] systemServiceTimeAtCpuSpeeds = batteryStats.getSystemServiceTimeAtCpuSpeeds();
        double d = 0.0d;
        if (systemServiceTimeAtCpuSpeeds == null) {
            return 0.0d;
        }
        UsageBasedPowerEstimator[] usageBasedPowerEstimatorArr = this.mPowerEstimators;
        int min = Math.min(usageBasedPowerEstimatorArr.length, systemServiceTimeAtCpuSpeeds.length);
        for (int i = 0; i < min; i++) {
            d +=
                    usageBasedPowerEstimatorArr[i].mAveragePowerMahPerMs
                            * (systemServiceTimeAtCpuSpeeds[i] / 1000);
        }
        return d;
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final boolean isPowerComponentSupported(int i) {
        return i == 7;
    }
}
