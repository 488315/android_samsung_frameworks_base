package com.android.server.power.stats;

import android.os.BatteryStats;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;
import android.os.UidBatteryConsumer;
import android.util.ArrayMap;
import android.util.SparseArray;

import com.android.internal.os.PowerProfile;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WakelockPowerCalculator extends PowerCalculator {
    public final UsageBasedPowerEstimator mPowerEstimator;

    public WakelockPowerCalculator(PowerProfile powerProfile) {
        this.mPowerEstimator =
                new UsageBasedPowerEstimator(powerProfile.getAveragePower("cpu.idle"));
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final void calculate(
            BatteryUsageStats.Builder builder,
            BatteryStats batteryStats,
            long j,
            long j2,
            BatteryUsageStatsQuery batteryUsageStatsQuery) {
        UsageBasedPowerEstimator usageBasedPowerEstimator;
        long j3;
        double d;
        long j4;
        BatteryUsageStats.Builder builder2;
        BatteryStats batteryStats2 = batteryStats;
        long j5 = j;
        SparseArray uidBatteryConsumerBuilders = builder.getUidBatteryConsumerBuilders();
        int size = uidBatteryConsumerBuilders.size() - 1;
        UidBatteryConsumer.Builder builder3 = null;
        long j6 = 0;
        double d2 = 0.0d;
        double d3 = 0.0d;
        long j7 = 0;
        while (true) {
            usageBasedPowerEstimator = this.mPowerEstimator;
            if (size < 0) {
                break;
            }
            UidBatteryConsumer.Builder builder4 =
                    (UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.valueAt(size);
            SparseArray sparseArray = uidBatteryConsumerBuilders;
            ArrayMap wakelockStats = builder4.getBatteryStatsUid().getWakelockStats();
            UidBatteryConsumer.Builder builder5 = builder3;
            int size2 = wakelockStats.size();
            double d4 = d3;
            int i = 0;
            long j8 = 0;
            while (i < size2) {
                ArrayMap arrayMap = wakelockStats;
                BatteryStats.Timer wakeTime =
                        ((BatteryStats.Uid.Wakelock) wakelockStats.valueAt(i)).getWakeTime(0);
                if (wakeTime != null) {
                    j8 = wakeTime.getTotalTimeLocked(j5, 0) + j8;
                }
                i++;
                wakelockStats = arrayMap;
            }
            long j9 = j8 / 1000;
            double d5 = usageBasedPowerEstimator.mAveragePowerMahPerMs * j9;
            builder4.setUsageDurationMillis(12, j9).setConsumedPower(12, d5);
            if (!builder4.isVirtualUid()) {
                j6 += j9;
                d2 += d5;
            }
            if (builder4.getUid() == 0) {
                j7 = j9;
                d3 = d5;
                builder3 = builder4;
            } else {
                builder3 = builder5;
                d3 = d4;
            }
            size--;
            batteryStats2 = batteryStats;
            j5 = j;
            uidBatteryConsumerBuilders = sparseArray;
        }
        UidBatteryConsumer.Builder builder6 = builder3;
        double d6 = d3;
        long batteryUptime =
                ((batteryStats2.getBatteryUptime(j2) - batteryStats2.getScreenOnTime(j, 0)) / 1000)
                        - j6;
        if (batteryUptime > 0) {
            j3 = j6;
            d = d6 + (usageBasedPowerEstimator.mAveragePowerMahPerMs * batteryUptime);
            j4 = j7 + batteryUptime;
        } else {
            j3 = j6;
            d = 0.0d;
            j4 = 0;
        }
        if (builder6 != null) {
            builder6.setUsageDurationMillis(12, j4).setConsumedPower(12, d);
        }
        long batteryUptime2 =
                (batteryStats2.getBatteryUptime(j2) - batteryStats2.getScreenOnTime(j, 0)) / 1000;
        if (batteryUptime2 < 0) {
            builder2 = builder;
            batteryUptime2 = 0;
        } else {
            builder2 = builder;
        }
        builder2.getAggregateBatteryConsumerBuilder(0)
                .setUsageDurationMillis(12, batteryUptime2)
                .setConsumedPower(12, d + d2);
        builder2.getAggregateBatteryConsumerBuilder(1)
                .setUsageDurationMillis(12, j3)
                .setConsumedPower(12, d2);
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final boolean isPowerComponentSupported(int i) {
        return i == 12;
    }
}
