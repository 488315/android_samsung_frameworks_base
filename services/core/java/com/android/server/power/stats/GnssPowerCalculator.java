package com.android.server.power.stats;

import android.os.BatteryStats;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;
import android.os.UidBatteryConsumer;
import android.util.SparseArray;

import com.android.internal.os.PowerProfile;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class GnssPowerCalculator extends PowerCalculator {
    public final double mAveragePowerGnssOn;
    public final double[] mAveragePowerPerSignalQuality = new double[2];

    public GnssPowerCalculator(PowerProfile powerProfile) {
        this.mAveragePowerGnssOn = powerProfile.getAveragePowerOrDefault("gps.on", -1.0d);
        for (int i = 0; i < 2; i++) {
            this.mAveragePowerPerSignalQuality[i] =
                    powerProfile.getAveragePower("gps.signalqualitybased", i);
        }
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final void calculate(
            BatteryUsageStats.Builder builder,
            BatteryStats batteryStats,
            long j,
            long j2,
            BatteryUsageStatsQuery batteryUsageStatsQuery) {
        long j3;
        long totalTimeLocked;
        int i;
        double d = this.mAveragePowerGnssOn;
        int i2 = 0;
        if (d == -1.0d) {
            long j4 = 0;
            double d2 = 0.0d;
            for (int i3 = 0; i3 < 2; i3++) {
                long gpsSignalQualityTime = batteryStats.getGpsSignalQualityTime(i3, j, 0) / 1000;
                j4 += gpsSignalQualityTime;
                d2 = (this.mAveragePowerPerSignalQuality[i3] * gpsSignalQualityTime) + d2;
            }
            d = j4 != 0 ? d2 / j4 : 0.0d;
        }
        SparseArray uidBatteryConsumerBuilders = builder.getUidBatteryConsumerBuilders();
        int size = uidBatteryConsumerBuilders.size() - 1;
        double d3 = 0.0d;
        while (size >= 0) {
            UidBatteryConsumer.Builder builder2 =
                    (UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.valueAt(size);
            double d4 = d;
            long gnssEnergyConsumptionUC =
                    builder2.getBatteryStatsUid().getGnssEnergyConsumptionUC();
            int powerModel =
                    PowerCalculator.getPowerModel(gnssEnergyConsumptionUC, batteryUsageStatsQuery);
            BatteryStats.Uid.Sensor sensor =
                    (BatteryStats.Uid.Sensor)
                            builder2.getBatteryStatsUid().getSensorStats().get(-10000);
            if (sensor == null) {
                totalTimeLocked = 0;
                i = 2;
                j3 = 1000;
            } else {
                j3 = 1000;
                totalTimeLocked = sensor.getSensorTime().getTotalTimeLocked(j, i2) / 1000;
                i = 2;
            }
            double d5 =
                    powerModel != i
                            ? (totalTimeLocked * d4) / 3600000.0d
                            : gnssEnergyConsumptionUC * 2.777777777777778E-7d;
            builder2.setUsageDurationMillis(10, totalTimeLocked)
                    .setConsumedPower(10, d5, powerModel);
            if (!builder2.isVirtualUid()) {
                d3 += d5;
            }
            size--;
            d = d4;
            i2 = 0;
        }
        long gnssEnergyConsumptionUC2 = batteryStats.getGnssEnergyConsumptionUC();
        int powerModel2 =
                PowerCalculator.getPowerModel(gnssEnergyConsumptionUC2, batteryUsageStatsQuery);
        builder.getAggregateBatteryConsumerBuilder(0)
                .setConsumedPower(
                        10,
                        powerModel2 == 2 ? gnssEnergyConsumptionUC2 * 2.777777777777778E-7d : d3,
                        powerModel2);
        builder.getAggregateBatteryConsumerBuilder(1).setConsumedPower(10, d3, powerModel2);
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final boolean isPowerComponentSupported(int i) {
        return i == 10;
    }
}
