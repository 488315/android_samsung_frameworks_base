package com.android.server.power.stats;

import android.os.BatteryConsumer;
import android.telephony.CellSignalStrength;
import android.util.Log;

import com.android.internal.os.PowerProfile;
import com.android.internal.power.ModemPowerProfile;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class MobileRadioPowerCalculator extends PowerCalculator {
    public static final int NUM_SIGNAL_STRENGTH_LEVELS =
            CellSignalStrength.getNumSignalStrengthLevels();
    public static final BatteryConsumer.Key[] UNINITIALIZED_KEYS = new BatteryConsumer.Key[0];
    public final UsageBasedPowerEstimator mActivePowerEstimator;
    public final UsageBasedPowerEstimator mIdlePowerEstimator;
    public final UsageBasedPowerEstimator[] mIdlePowerEstimators;
    public final PowerProfile mPowerProfile;
    public final UsageBasedPowerEstimator mScanPowerEstimator;
    public final UsageBasedPowerEstimator mSleepPowerEstimator;

    public MobileRadioPowerCalculator(PowerProfile powerProfile) {
        int i = NUM_SIGNAL_STRENGTH_LEVELS;
        this.mIdlePowerEstimators = new UsageBasedPowerEstimator[i];
        this.mPowerProfile = powerProfile;
        double averageBatteryDrainOrDefaultMa =
                powerProfile.getAverageBatteryDrainOrDefaultMa(4294967296L, Double.NaN);
        if (Double.isNaN(averageBatteryDrainOrDefaultMa)) {
            this.mSleepPowerEstimator = null;
        } else {
            this.mSleepPowerEstimator =
                    new UsageBasedPowerEstimator(averageBatteryDrainOrDefaultMa);
        }
        double averageBatteryDrainOrDefaultMa2 =
                powerProfile.getAverageBatteryDrainOrDefaultMa(4563402752L, Double.NaN);
        if (Double.isNaN(averageBatteryDrainOrDefaultMa2)) {
            this.mIdlePowerEstimator = null;
        } else {
            this.mIdlePowerEstimator =
                    new UsageBasedPowerEstimator(averageBatteryDrainOrDefaultMa2);
        }
        double averagePowerOrDefault =
                powerProfile.getAveragePowerOrDefault("radio.active", Double.NaN);
        if (Double.isNaN(averagePowerOrDefault)) {
            double averagePower = powerProfile.getAveragePower("modem.controller.rx") + 0.0d;
            for (int i2 = 0; i2 < i; i2++) {
                averagePower += powerProfile.getAveragePower("modem.controller.tx", i2);
            }
            averagePowerOrDefault = averagePower / (i + 1);
        }
        this.mActivePowerEstimator = new UsageBasedPowerEstimator(averagePowerOrDefault);
        if (Double.isNaN(powerProfile.getAveragePowerOrDefault("radio.on", Double.NaN))) {
            double averagePower2 = powerProfile.getAveragePower("modem.controller.idle");
            this.mIdlePowerEstimators[0] =
                    new UsageBasedPowerEstimator((25.0d * averagePower2) / 180.0d);
            for (int i3 = 1; i3 < i; i3++) {
                this.mIdlePowerEstimators[i3] =
                        new UsageBasedPowerEstimator(Math.max(1.0d, averagePower2 / 256.0d));
            }
        } else {
            for (int i4 = 0; i4 < i; i4++) {
                this.mIdlePowerEstimators[i4] =
                        new UsageBasedPowerEstimator(powerProfile.getAveragePower("radio.on", i4));
            }
        }
        this.mScanPowerEstimator =
                new UsageBasedPowerEstimator(
                        powerProfile.getAveragePowerOrDefault("radio.scanning", 0.0d));
    }

    public final double calcRxStatePowerMah(int i, int i2, long j) {
        long averageBatteryDrainKey =
                ModemPowerProfile.getAverageBatteryDrainKey(536870912, i, i2, -1);
        double averageBatteryDrainOrDefaultMa =
                this.mPowerProfile.getAverageBatteryDrainOrDefaultMa(
                        averageBatteryDrainKey, Double.NaN);
        if (!Double.isNaN(averageBatteryDrainOrDefaultMa)) {
            return (averageBatteryDrainOrDefaultMa * j) / 3600000.0d;
        }
        Log.w(
                "MobRadioPowerCalculator",
                "Unavailable Power Profile constant for key 0x"
                        + Long.toHexString(averageBatteryDrainKey));
        return Double.NaN;
    }

    public final double calcTxStatePowerMah(int i, int i2, int i3, long j) {
        long averageBatteryDrainKey =
                ModemPowerProfile.getAverageBatteryDrainKey(805306368, i, i2, i3);
        double averageBatteryDrainOrDefaultMa =
                this.mPowerProfile.getAverageBatteryDrainOrDefaultMa(
                        averageBatteryDrainKey, Double.NaN);
        if (!Double.isNaN(averageBatteryDrainOrDefaultMa)) {
            return (averageBatteryDrainOrDefaultMa * j) / 3600000.0d;
        }
        Log.w(
                "MobRadioPowerCalculator",
                "Unavailable Power Profile constant for key 0x"
                        + Long.toHexString(averageBatteryDrainKey));
        return Double.NaN;
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x0289  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x02bd  */
    @Override // com.android.server.power.stats.PowerCalculator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void calculate(
            android.os.BatteryUsageStats.Builder r43,
            android.os.BatteryStats r44,
            long r45,
            long r47,
            android.os.BatteryUsageStatsQuery r49) {
        /*
            Method dump skipped, instructions count: 749
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.power.stats.MobileRadioPowerCalculator.calculate(android.os.BatteryUsageStats$Builder,"
                    + " android.os.BatteryStats, long, long,"
                    + " android.os.BatteryUsageStatsQuery):void");
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final boolean isPowerComponentSupported(int i) {
        return i == 8;
    }
}
