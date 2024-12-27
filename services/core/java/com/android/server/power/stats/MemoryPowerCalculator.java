package com.android.server.power.stats;

import android.os.BatteryStats;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;
import android.util.LongSparseArray;

import com.android.internal.os.PowerProfile;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class MemoryPowerCalculator extends PowerCalculator {
    public final UsageBasedPowerEstimator[] mPowerEstimators;

    public MemoryPowerCalculator(PowerProfile powerProfile) {
        int numElements = powerProfile.getNumElements("memory.bandwidths");
        this.mPowerEstimators = new UsageBasedPowerEstimator[numElements];
        for (int i = 0; i < numElements; i++) {
            this.mPowerEstimators[i] =
                    new UsageBasedPowerEstimator(
                            powerProfile.getAveragePower("memory.bandwidths", i));
        }
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final void calculate(
            BatteryUsageStats.Builder builder,
            BatteryStats batteryStats,
            long j,
            long j2,
            BatteryUsageStatsQuery batteryUsageStatsQuery) {
        UsageBasedPowerEstimator[] usageBasedPowerEstimatorArr;
        LongSparseArray kernelMemoryStats = batteryStats.getKernelMemoryStats();
        long j3 = 0;
        int i = 0;
        while (true) {
            int size = kernelMemoryStats.size();
            usageBasedPowerEstimatorArr = this.mPowerEstimators;
            if (i >= size || i >= usageBasedPowerEstimatorArr.length) {
                break;
            }
            UsageBasedPowerEstimator usageBasedPowerEstimator = usageBasedPowerEstimatorArr[i];
            BatteryStats.Timer timer = (BatteryStats.Timer) kernelMemoryStats.valueAt(i);
            usageBasedPowerEstimator.getClass();
            j3 += UsageBasedPowerEstimator.calculateDuration(timer, j);
            i++;
        }
        LongSparseArray kernelMemoryStats2 = batteryStats.getKernelMemoryStats();
        double d = 0.0d;
        for (int i2 = 0;
                i2 < kernelMemoryStats2.size() && i2 < usageBasedPowerEstimatorArr.length;
                i2++) {
            UsageBasedPowerEstimator usageBasedPowerEstimator2 =
                    usageBasedPowerEstimatorArr[(int) kernelMemoryStats2.keyAt(i2)];
            BatteryStats.Timer timer2 = (BatteryStats.Timer) kernelMemoryStats2.valueAt(i2);
            usageBasedPowerEstimator2.getClass();
            d +=
                    usageBasedPowerEstimator2.mAveragePowerMahPerMs
                            * UsageBasedPowerEstimator.calculateDuration(timer2, j);
        }
        builder.getAggregateBatteryConsumerBuilder(0)
                .setUsageDurationMillis(13, j3)
                .setConsumedPower(13, d);
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final boolean isPowerComponentSupported(int i) {
        return i == 13;
    }
}
