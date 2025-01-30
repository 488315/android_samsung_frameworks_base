package com.android.server.power.stats;

import android.os.BatteryStats;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;
import android.os.UidBatteryConsumer;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseLongArray;
import com.android.internal.os.PowerProfile;

/* loaded from: classes3.dex */
public class ScreenPowerCalculator extends PowerCalculator {
    public final boolean mHasDisplayPowerReporting;
    public final UsageBasedPowerEstimator[] mScreenFullPowerEstimators;
    public final UsageBasedPowerEstimator[] mScreenOnPowerEstimators;

    public class PowerAndDuration {
        public long durationMs;
        public double powerMah;

        public PowerAndDuration() {
        }
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public boolean isPowerComponentSupported(int i) {
        return i == 0;
    }

    public ScreenPowerCalculator(PowerProfile powerProfile, boolean z) {
        int numDisplays = powerProfile.getNumDisplays();
        this.mScreenOnPowerEstimators = new UsageBasedPowerEstimator[numDisplays];
        this.mScreenFullPowerEstimators = new UsageBasedPowerEstimator[numDisplays];
        for (int i = 0; i < numDisplays; i++) {
            this.mScreenOnPowerEstimators[i] = new UsageBasedPowerEstimator(powerProfile.getAveragePowerForOrdinal("screen.on.display", i));
            this.mScreenFullPowerEstimators[i] = new UsageBasedPowerEstimator(powerProfile.getAveragePowerForOrdinal("screen.full.display", i));
        }
        this.mHasDisplayPowerReporting = z;
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public void calculate(BatteryUsageStats.Builder builder, BatteryStats batteryStats, long j, long j2, BatteryUsageStatsQuery batteryUsageStatsQuery) {
        PowerAndDuration powerAndDuration;
        BatteryUsageStats.Builder builder2;
        long j3 = j;
        PowerAndDuration powerAndDuration2 = new PowerAndDuration();
        long screenOnEnergyConsumptionUC = batteryStats.getScreenOnEnergyConsumptionUC();
        int powerModel = PowerCalculator.getPowerModel(screenOnEnergyConsumptionUC, batteryUsageStatsQuery);
        calculateTotalDurationAndPower(powerAndDuration2, powerModel, batteryStats, j, 0, screenOnEnergyConsumptionUC);
        SparseArray uidBatteryConsumerBuilders = builder.getUidBatteryConsumerBuilders();
        double d = 0.0d;
        long j4 = 0;
        if (this.mHasDisplayPowerReporting) {
            for (int size = uidBatteryConsumerBuilders.size() - 1; size >= 0; size--) {
                UidBatteryConsumer.Builder builder3 = (UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.valueAt(size);
                long displayTime = builder3.getBatteryStatsUid().getDisplayTime(0);
                double displayPowerDrain = r9.getDisplayPowerDrain(0) / 1000.0d;
                builder3.setUsageDurationMillis(0, displayTime).setConsumedPower(0, displayPowerDrain, 1);
                d += displayPowerDrain;
                j4 += displayTime;
            }
        } else {
            if (powerModel == 2) {
                PowerAndDuration powerAndDuration3 = new PowerAndDuration();
                int size2 = uidBatteryConsumerBuilders.size() - 1;
                while (size2 >= 0) {
                    UidBatteryConsumer.Builder builder4 = (UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.valueAt(size2);
                    calculateAppUsingEnergyConsumption(powerAndDuration3, builder4.getBatteryStatsUid(), j3);
                    PowerAndDuration powerAndDuration4 = powerAndDuration2;
                    builder4.setUsageDurationMillis(0, powerAndDuration3.durationMs).setConsumedPower(0, powerAndDuration3.powerMah, powerModel);
                    if (!builder4.isVirtualUid()) {
                        d += powerAndDuration3.powerMah;
                        j4 += powerAndDuration3.durationMs;
                    }
                    size2--;
                    j3 = j;
                    powerAndDuration2 = powerAndDuration4;
                }
                powerAndDuration = powerAndDuration2;
                builder2 = builder;
                PowerAndDuration powerAndDuration5 = powerAndDuration;
                builder2.getAggregateBatteryConsumerBuilder(0).setConsumedPower(0, Math.max(powerAndDuration5.powerMah, d), powerModel).setUsageDurationMillis(0, powerAndDuration5.durationMs);
                builder2.getAggregateBatteryConsumerBuilder(1).setConsumedPower(0, d, powerModel).setUsageDurationMillis(0, j4);
            }
            smearScreenBatteryDrain(uidBatteryConsumerBuilders, powerAndDuration2, j3);
            d = powerAndDuration2.powerMah;
            j4 = powerAndDuration2.durationMs;
        }
        builder2 = builder;
        powerAndDuration = powerAndDuration2;
        PowerAndDuration powerAndDuration52 = powerAndDuration;
        builder2.getAggregateBatteryConsumerBuilder(0).setConsumedPower(0, Math.max(powerAndDuration52.powerMah, d), powerModel).setUsageDurationMillis(0, powerAndDuration52.durationMs);
        builder2.getAggregateBatteryConsumerBuilder(1).setConsumedPower(0, d, powerModel).setUsageDurationMillis(0, j4);
    }

    public final void calculateTotalDurationAndPower(PowerAndDuration powerAndDuration, int i, BatteryStats batteryStats, long j, int i2, long j2) {
        powerAndDuration.durationMs = calculateDuration(batteryStats, j, i2);
        if (this.mHasDisplayPowerReporting) {
            powerAndDuration.powerMah = batteryStats.getDisplayPowerDrainCount(i2) / 1000.0d;
            powerAndDuration.durationMs = calculateDuration(batteryStats, j, i2);
        } else if (i == 2) {
            powerAndDuration.powerMah = PowerCalculator.uCtoMah(j2);
        } else {
            powerAndDuration.powerMah = calculateTotalPowerFromBrightness(batteryStats, j);
        }
    }

    public final void calculateAppUsingEnergyConsumption(PowerAndDuration powerAndDuration, BatteryStats.Uid uid, long j) {
        powerAndDuration.durationMs = getProcessForegroundTimeMs(uid, j);
        long screenOnEnergyConsumptionUC = uid.getScreenOnEnergyConsumptionUC();
        if (screenOnEnergyConsumptionUC < 0) {
            Slog.wtf("ScreenPowerCalculator", "Screen energy not supported, so calculateApp shouldn't de called");
            powerAndDuration.powerMah = 0.0d;
        } else {
            powerAndDuration.powerMah = PowerCalculator.uCtoMah(screenOnEnergyConsumptionUC);
        }
    }

    public final long calculateDuration(BatteryStats batteryStats, long j, int i) {
        return batteryStats.getScreenOnTime(j, i) / 1000;
    }

    public final double calculateTotalPowerFromBrightness(BatteryStats batteryStats, long j) {
        int length = this.mScreenOnPowerEstimators.length;
        double d = 0.0d;
        for (int i = 0; i < length; i++) {
            long j2 = 1000;
            d += this.mScreenOnPowerEstimators[i].calculatePower(batteryStats.getDisplayScreenOnTime(i, j) / 1000);
            int i2 = 0;
            while (i2 < 5) {
                d += (this.mScreenFullPowerEstimators[i].calculatePower(batteryStats.getDisplayScreenBrightnessTime(i, i2, j) / j2) * (i2 + 0.5f)) / 5.0d;
                i2++;
                j2 = 1000;
            }
        }
        return d;
    }

    public final void smearScreenBatteryDrain(SparseArray sparseArray, PowerAndDuration powerAndDuration, long j) {
        SparseLongArray sparseLongArray = new SparseLongArray();
        long j2 = 0;
        long j3 = 0;
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            UidBatteryConsumer.Builder builder = (UidBatteryConsumer.Builder) sparseArray.valueAt(size);
            BatteryStats.Uid batteryStatsUid = builder.getBatteryStatsUid();
            long processForegroundTimeMs = getProcessForegroundTimeMs(batteryStatsUid, j);
            sparseLongArray.put(batteryStatsUid.getUid(), processForegroundTimeMs);
            if (!builder.isVirtualUid()) {
                j3 += processForegroundTimeMs;
            }
        }
        if (j3 >= 600000) {
            double d = powerAndDuration.powerMah;
            int size2 = sparseArray.size() - 1;
            while (size2 >= 0) {
                UidBatteryConsumer.Builder builder2 = (UidBatteryConsumer.Builder) sparseArray.valueAt(size2);
                long j4 = sparseLongArray.get(builder2.getUid(), j2);
                builder2.setUsageDurationMillis(0, j4).setConsumedPower(0, (j4 * d) / j3, 1);
                size2--;
                j2 = 0;
            }
        }
    }

    public long getProcessForegroundTimeMs(BatteryStats.Uid uid, long j) {
        return Math.min(0 + uid.getProcessStateTime(new int[]{0}[0], j, 0), getForegroundActivityTotalTimeUs(uid, j)) / 1000;
    }

    public long getForegroundActivityTotalTimeUs(BatteryStats.Uid uid, long j) {
        BatteryStats.Timer foregroundActivityTimer = uid.getForegroundActivityTimer();
        if (foregroundActivityTimer == null) {
            return 0L;
        }
        return foregroundActivityTimer.getTotalTimeLocked(j, 0);
    }
}
