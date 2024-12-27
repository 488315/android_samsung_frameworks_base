package com.android.server.power.stats;

import android.os.BatteryConsumer;
import android.os.BatteryStats;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;
import android.os.UidBatteryConsumer;
import android.util.SparseArray;

import com.android.internal.os.PowerProfile;

import java.util.Arrays;

public final class BluetoothPowerCalculator extends PowerCalculator {
    public static final BatteryConsumer.Key[] UNINITIALIZED_KEYS = new BatteryConsumer.Key[0];
    public final boolean mHasBluetoothPowerController;
    public final double mIdleMa;
    public final double mRxMa;
    public final double mTxMa;

    public final class PowerAndDuration {
        public long durationMs;
        public BatteryConsumer.Key[] keys;
        public double powerMah;
        public double[] powerPerKeyMah;
        public long totalDurationMs;
        public double totalPowerMah;
    }

    public BluetoothPowerCalculator(PowerProfile powerProfile) {
        double averagePower = powerProfile.getAveragePower("bluetooth.controller.idle");
        this.mIdleMa = averagePower;
        double averagePower2 = powerProfile.getAveragePower("bluetooth.controller.rx");
        this.mRxMa = averagePower2;
        double averagePower3 = powerProfile.getAveragePower("bluetooth.controller.tx");
        this.mTxMa = averagePower3;
        this.mHasBluetoothPowerController =
                (averagePower == 0.0d || averagePower2 == 0.0d || averagePower3 == 0.0d)
                        ? false
                        : true;
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final void calculate(
            BatteryUsageStats.Builder builder,
            BatteryStats batteryStats,
            long j,
            long j2,
            BatteryUsageStatsQuery batteryUsageStatsQuery) {
        if (batteryStats.hasBluetoothActivityReporting()) {
            BatteryConsumer.Key[] keyArr = UNINITIALIZED_KEYS;
            PowerAndDuration powerAndDuration = new PowerAndDuration();
            SparseArray uidBatteryConsumerBuilders = builder.getUidBatteryConsumerBuilders();
            int size = uidBatteryConsumerBuilders.size() - 1;
            while (size >= 0) {
                UidBatteryConsumer.Builder builder2 =
                        (UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.valueAt(size);
                if (keyArr == UNINITIALIZED_KEYS) {
                    if (batteryUsageStatsQuery.isProcessStateDataNeeded()) {
                        keyArr = builder2.getKeys(2);
                        powerAndDuration.keys = keyArr;
                        powerAndDuration.powerPerKeyMah = new double[keyArr.length];
                    } else {
                        keyArr = null;
                    }
                }
                long bluetoothEnergyConsumptionUC =
                        builder2.getBatteryStatsUid().getBluetoothEnergyConsumptionUC();
                int powerModel =
                        PowerCalculator.getPowerModel(
                                bluetoothEnergyConsumptionUC, batteryUsageStatsQuery);
                BatteryConsumer.Key[] keyArr2 = keyArr;
                calculatePowerAndDuration(
                        builder2.getBatteryStatsUid(),
                        powerModel,
                        bluetoothEnergyConsumptionUC,
                        builder2.getBatteryStatsUid().getBluetoothControllerActivity(),
                        batteryUsageStatsQuery.shouldForceUsePowerProfileModel(),
                        powerAndDuration);
                builder2.setUsageDurationMillis(2, powerAndDuration.durationMs)
                        .setConsumedPower(2, powerAndDuration.powerMah, powerModel);
                if (!builder2.isVirtualUid()) {
                    powerAndDuration.totalDurationMs += powerAndDuration.durationMs;
                    powerAndDuration.totalPowerMah += powerAndDuration.powerMah;
                }
                if (batteryUsageStatsQuery.isProcessStateDataNeeded()
                        && powerAndDuration.keys != null) {
                    int i = 0;
                    while (true) {
                        BatteryConsumer.Key[] keyArr3 = powerAndDuration.keys;
                        if (i < keyArr3.length) {
                            BatteryConsumer.Key key = keyArr3[i];
                            if (key.processState != 0) {
                                builder2.setConsumedPower(
                                        key, powerAndDuration.powerPerKeyMah[i], powerModel);
                            }
                            i++;
                        }
                    }
                }
                size--;
                keyArr = keyArr2;
            }
            long bluetoothEnergyConsumptionUC2 = batteryStats.getBluetoothEnergyConsumptionUC();
            int powerModel2 =
                    PowerCalculator.getPowerModel(
                            bluetoothEnergyConsumptionUC2, batteryUsageStatsQuery);
            calculatePowerAndDuration(
                    null,
                    powerModel2,
                    bluetoothEnergyConsumptionUC2,
                    batteryStats.getBluetoothControllerActivity(),
                    batteryUsageStatsQuery.shouldForceUsePowerProfileModel(),
                    powerAndDuration);
            Math.max(0L, powerAndDuration.durationMs - powerAndDuration.totalDurationMs);
            builder.getAggregateBatteryConsumerBuilder(0)
                    .setUsageDurationMillis(2, powerAndDuration.durationMs)
                    .setConsumedPower(
                            2,
                            Math.max(powerAndDuration.powerMah, powerAndDuration.totalPowerMah),
                            powerModel2);
            builder.getAggregateBatteryConsumerBuilder(1)
                    .setUsageDurationMillis(2, powerAndDuration.totalDurationMs)
                    .setConsumedPower(2, powerAndDuration.totalPowerMah, powerModel2);
        }
    }

    public final void calculatePowerAndDuration(
            BatteryStats.Uid uid,
            int i,
            long j,
            BatteryStats.ControllerActivityCounter controllerActivityCounter,
            boolean z,
            PowerAndDuration powerAndDuration) {
        if (controllerActivityCounter == null) {
            powerAndDuration.durationMs = 0L;
            powerAndDuration.powerMah = 0.0d;
            double[] dArr = powerAndDuration.powerPerKeyMah;
            if (dArr != null) {
                Arrays.fill(dArr, 0.0d);
                return;
            }
            return;
        }
        BatteryStats.LongCounter idleTimeCounter = controllerActivityCounter.getIdleTimeCounter();
        BatteryStats.LongCounter rxTimeCounter = controllerActivityCounter.getRxTimeCounter();
        int i2 = 0;
        BatteryStats.LongCounter longCounter = controllerActivityCounter.getTxTimeCounters()[0];
        long countLocked = idleTimeCounter.getCountLocked(0);
        long countLocked2 = rxTimeCounter.getCountLocked(0);
        long countLocked3 = longCounter.getCountLocked(0);
        powerAndDuration.durationMs = countLocked + countLocked2 + countLocked3;
        if (i == 2) {
            powerAndDuration.powerMah = j * 2.777777777777778E-7d;
            if (uid == null || powerAndDuration.keys == null) {
                return;
            }
            while (true) {
                BatteryConsumer.Key[] keyArr = powerAndDuration.keys;
                if (i2 >= keyArr.length) {
                    return;
                }
                if (keyArr[i2].processState != 0) {
                    powerAndDuration.powerPerKeyMah[i2] =
                            uid.getBluetoothEnergyConsumptionUC(r2) * 2.777777777777778E-7d;
                }
                i2++;
            }
        } else {
            if (!z) {
                double countLocked4 =
                        controllerActivityCounter.getPowerCounter().getCountLocked(0) / 3600000.0d;
                if (countLocked4 != 0.0d) {
                    powerAndDuration.powerMah = countLocked4;
                    double[] dArr2 = powerAndDuration.powerPerKeyMah;
                    if (dArr2 != null) {
                        Arrays.fill(dArr2, 0.0d);
                        return;
                    }
                    return;
                }
            }
            if (!this.mHasBluetoothPowerController) {
                powerAndDuration.powerMah = 0.0d;
                double[] dArr3 = powerAndDuration.powerPerKeyMah;
                if (dArr3 != null) {
                    Arrays.fill(dArr3, 0.0d);
                    return;
                }
                return;
            }
            powerAndDuration.powerMah = calculatePowerMah(countLocked2, countLocked3, countLocked);
            if (powerAndDuration.keys == null) {
                return;
            }
            int i3 = 0;
            while (true) {
                BatteryConsumer.Key[] keyArr2 = powerAndDuration.keys;
                if (i3 >= keyArr2.length) {
                    return;
                }
                int i4 = keyArr2[i3].processState;
                if (i4 != 0) {
                    powerAndDuration.powerPerKeyMah[i3] =
                            calculatePowerMah(
                                    rxTimeCounter.getCountForProcessState(i4),
                                    longCounter.getCountForProcessState(i4),
                                    idleTimeCounter.getCountForProcessState(i4));
                }
                i3++;
            }
        }
    }

    public final double calculatePowerMah(long j, long j2, long j3) {
        return ((j2 * this.mTxMa) + ((j * this.mRxMa) + (j3 * this.mIdleMa))) / 3600000.0d;
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final boolean isPowerComponentSupported(int i) {
        return i == 2;
    }
}
