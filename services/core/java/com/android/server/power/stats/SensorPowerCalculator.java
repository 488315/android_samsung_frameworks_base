package com.android.server.power.stats;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.BatteryStats;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;
import android.os.UidBatteryConsumer;
import android.util.SparseArray;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SensorPowerCalculator extends PowerCalculator {
    public final SparseArray mSensors;

    public SensorPowerCalculator(SensorManager sensorManager) {
        List<Sensor> sensorList = sensorManager.getSensorList(-1);
        this.mSensors = new SparseArray(sensorList.size());
        for (int i = 0; i < sensorList.size(); i++) {
            Sensor sensor = sensorList.get(i);
            this.mSensors.put(sensor.getHandle(), sensor);
        }
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final void calculate(BatteryUsageStats.Builder builder, BatteryStats batteryStats, long j, long j2, BatteryUsageStatsQuery batteryUsageStatsQuery) {
        double d;
        Sensor sensor;
        SparseArray uidBatteryConsumerBuilders = builder.getUidBatteryConsumerBuilders();
        int size = uidBatteryConsumerBuilders.size() - 1;
        double d2 = 0.0d;
        while (true) {
            int i = 0;
            if (size < 0) {
                builder.getAggregateBatteryConsumerBuilder(0).setConsumedPower(9, d2);
                builder.getAggregateBatteryConsumerBuilder(1).setConsumedPower(9, d2);
                return;
            }
            UidBatteryConsumer.Builder builder2 = (UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.valueAt(size);
            if (!builder2.isVirtualUid()) {
                BatteryStats.Uid batteryStatsUid = builder2.getBatteryStatsUid();
                SparseArray sensorStats = batteryStatsUid.getSensorStats();
                int size2 = sensorStats.size();
                int i2 = 0;
                double d3 = 0.0d;
                while (i2 < size2) {
                    int keyAt = sensorStats.keyAt(i2);
                    if (keyAt == -10000) {
                        d = d3;
                    } else {
                        d = d3;
                        if (((BatteryStats.Uid.Sensor) sensorStats.valueAt(i2)).getSensorTime().getTotalTimeLocked(j, i) / 1000 != 0 && (sensor = (Sensor) this.mSensors.get(keyAt)) != null) {
                            d += (sensor.getPower() * r10) / 3600000.0f;
                        }
                    }
                    i2++;
                    d3 = d;
                    i = 0;
                }
                double d4 = d3;
                SparseArray sensorStats2 = batteryStatsUid.getSensorStats();
                int size3 = sensorStats2.size();
                long j3 = 0;
                for (int i3 = 0; i3 < size3; i3++) {
                    if (sensorStats2.keyAt(i3) != -10000) {
                        j3 = (((BatteryStats.Uid.Sensor) sensorStats2.valueAt(i3)).getSensorTime().getTotalTimeLocked(j, 0) / 1000) + j3;
                    }
                }
                builder2.setUsageDurationMillis(9, j3).setConsumedPower(9, d4);
                d2 += d4;
            }
            size--;
        }
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final boolean isPowerComponentSupported(int i) {
        return i == 9;
    }
}
