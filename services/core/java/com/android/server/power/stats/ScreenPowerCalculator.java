package com.android.server.power.stats;

import android.os.BatteryStats;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;
import android.os.UidBatteryConsumer;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseLongArray;

import com.android.internal.os.PowerProfile;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ScreenPowerCalculator extends PowerCalculator {
    public final UsageBasedPowerEstimator[] mScreenFullPowerEstimators;
    public final UsageBasedPowerEstimator[] mScreenOnPowerEstimators;

    public ScreenPowerCalculator(PowerProfile powerProfile) {
        int numDisplays = powerProfile.getNumDisplays();
        this.mScreenOnPowerEstimators = new UsageBasedPowerEstimator[numDisplays];
        this.mScreenFullPowerEstimators = new UsageBasedPowerEstimator[numDisplays];
        for (int i = 0; i < numDisplays; i++) {
            this.mScreenOnPowerEstimators[i] =
                    new UsageBasedPowerEstimator(
                            powerProfile.getAveragePowerForOrdinal("screen.on.display", i));
            this.mScreenFullPowerEstimators[i] =
                    new UsageBasedPowerEstimator(
                            powerProfile.getAveragePowerForOrdinal("screen.full.display", i));
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
        double d2;
        long j3;
        double d3;
        long j4;
        double d4;
        int i;
        long j5 = j;
        long screenOnEnergyConsumptionUC = batteryStats.getScreenOnEnergyConsumptionUC();
        int powerModel =
                PowerCalculator.getPowerModel(screenOnEnergyConsumptionUC, batteryUsageStatsQuery);
        int i2 = 0;
        long j6 = 1000;
        long screenOnTime = batteryStats.getScreenOnTime(j5, 0) / 1000;
        if (powerModel != 2) {
            UsageBasedPowerEstimator[] usageBasedPowerEstimatorArr = this.mScreenOnPowerEstimators;
            int length = usageBasedPowerEstimatorArr.length;
            d = 0.0d;
            while (i2 < length) {
                d =
                        (usageBasedPowerEstimatorArr[i2].mAveragePowerMahPerMs
                                        * (batteryStats.getDisplayScreenOnTime(i2, j5) / j6))
                                + d;
                for (int i3 = 0; i3 < 5; i3++) {
                    d =
                            (((this.mScreenFullPowerEstimators[i2].mAveragePowerMahPerMs
                                                            * (batteryStats
                                                                            .getDisplayScreenBrightnessTime(
                                                                                    i2, i3, j5)
                                                                    / 1000))
                                                    * (i3 + 0.5f))
                                            / 5.0d)
                                    + d;
                }
                i2++;
                j6 = 1000;
            }
        } else {
            d = screenOnEnergyConsumptionUC * 2.777777777777778E-7d;
        }
        double d5 = d;
        SparseArray uidBatteryConsumerBuilders = builder.getUidBatteryConsumerBuilders();
        if (powerModel != 2) {
            SparseLongArray sparseLongArray = new SparseLongArray();
            int size = uidBatteryConsumerBuilders.size() - 1;
            long j7 = 0;
            while (size >= 0) {
                UidBatteryConsumer.Builder builder2 =
                        (UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.valueAt(size);
                BatteryStats.Uid batteryStatsUid = builder2.getBatteryStatsUid();
                long j8 = screenOnTime;
                long processForegroundTimeMs = getProcessForegroundTimeMs(batteryStatsUid, j5);
                sparseLongArray.put(batteryStatsUid.getUid(), processForegroundTimeMs);
                if (!builder2.isVirtualUid()) {
                    j7 += processForegroundTimeMs;
                }
                size--;
                screenOnTime = j8;
            }
            j3 = screenOnTime;
            if (j7 >= 600000) {
                int size2 = uidBatteryConsumerBuilders.size() - 1;
                while (size2 >= 0) {
                    UidBatteryConsumer.Builder builder3 =
                            (UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.valueAt(size2);
                    long j9 = sparseLongArray.get(builder3.getUid(), 0L);
                    builder3.setUsageDurationMillis(0, j9).setConsumedPower(0, (j9 * d5) / j7, 1);
                    size2--;
                    d5 = d5;
                }
            }
            d2 = d5;
            j4 = j3;
            d3 = d2;
        } else {
            d2 = d5;
            j3 = screenOnTime;
            int size3 = uidBatteryConsumerBuilders.size() - 1;
            d3 = 0.0d;
            j4 = 0;
            while (size3 >= 0) {
                UidBatteryConsumer.Builder builder4 =
                        (UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.valueAt(size3);
                BatteryStats.Uid batteryStatsUid2 = builder4.getBatteryStatsUid();
                long processForegroundTimeMs2 = getProcessForegroundTimeMs(batteryStatsUid2, j5);
                SparseArray sparseArray = uidBatteryConsumerBuilders;
                long screenOnEnergyConsumptionUC2 =
                        batteryStatsUid2.getScreenOnEnergyConsumptionUC();
                if (screenOnEnergyConsumptionUC2 < 0) {
                    Slog.wtf(
                            "ScreenPowerCalculator",
                            "Screen energy not supported, so calculateApp shouldn't de called");
                    d4 = 0.0d;
                    i = 0;
                } else {
                    d4 = screenOnEnergyConsumptionUC2 * 2.777777777777778E-7d;
                    i = 0;
                }
                builder4.setUsageDurationMillis(i, processForegroundTimeMs2)
                        .setConsumedPower(i, d4, powerModel);
                if (!builder4.isVirtualUid()) {
                    d3 += d4;
                    j4 += processForegroundTimeMs2;
                }
                size3--;
                j5 = j;
                uidBatteryConsumerBuilders = sparseArray;
            }
        }
        builder.getAggregateBatteryConsumerBuilder(0)
                .setConsumedPower(0, Math.max(d2, d3), powerModel)
                .setUsageDurationMillis(0, j3);
        builder.getAggregateBatteryConsumerBuilder(1)
                .setConsumedPower(0, d3, powerModel)
                .setUsageDurationMillis(0, j4);
    }

    public long getForegroundActivityTotalTimeUs(BatteryStats.Uid uid, long j) {
        BatteryStats.Timer foregroundActivityTimer = uid.getForegroundActivityTimer();
        if (foregroundActivityTimer == null) {
            return 0L;
        }
        return foregroundActivityTimer.getTotalTimeLocked(j, 0);
    }

    public long getProcessForegroundTimeMs(BatteryStats.Uid uid, long j) {
        return Math.min(
                        uid.getProcessStateTime(new int[] {0}[0], j, 0),
                        getForegroundActivityTotalTimeUs(uid, j))
                / 1000;
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final boolean isPowerComponentSupported(int i) {
        return i == 0;
    }
}
