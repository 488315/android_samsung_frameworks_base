package com.android.server.power.stats;

import android.os.AggregateBatteryConsumer;
import android.os.BatteryStats;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;
import android.os.UidBatteryConsumer;
import android.util.Slog;
import android.util.SparseArray;

import java.util.Arrays;

public final class CustomEnergyConsumerPowerCalculator extends PowerCalculator {
    @Override // com.android.server.power.stats.PowerCalculator
    public final void calculate(
            BatteryUsageStats.Builder builder,
            BatteryStats batteryStats,
            long j,
            long j2,
            BatteryUsageStatsQuery batteryUsageStatsQuery) {
        int i;
        double[] dArr;
        SparseArray uidBatteryConsumerBuilders = builder.getUidBatteryConsumerBuilders();
        int size = uidBatteryConsumerBuilders.size() - 1;
        double[] dArr2 = null;
        double[] dArr3 = null;
        while (true) {
            i = 0;
            if (size < 0) {
                break;
            }
            UidBatteryConsumer.Builder builder2 =
                    (UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.valueAt(size);
            long[] customEnergyConsumerBatteryConsumptionUC =
                    builder2.getBatteryStatsUid().getCustomEnergyConsumerBatteryConsumptionUC();
            if (customEnergyConsumerBatteryConsumptionUC == null) {
                dArr = null;
            } else {
                dArr = new double[customEnergyConsumerBatteryConsumptionUC.length];
                for (int i2 = 0; i2 < customEnergyConsumerBatteryConsumptionUC.length; i2++) {
                    dArr[i2] = customEnergyConsumerBatteryConsumptionUC[i2] * 2.777777777777778E-7d;
                }
            }
            if (dArr != null) {
                if (dArr3 == null) {
                    dArr3 = new double[dArr.length];
                } else if (dArr3.length != dArr.length) {
                    Slog.wtf(
                            "CustomEnergyCsmrPowerCalc",
                            "Number of custom energy components is not the same for all apps: "
                                    + dArr3.length
                                    + ", "
                                    + dArr.length);
                    dArr3 = Arrays.copyOf(dArr3, dArr.length);
                }
                while (i < dArr.length) {
                    builder2.setConsumedPowerForCustomComponent(i + 1000, dArr[i]);
                    if (!builder2.isVirtualUid()) {
                        dArr3[i] = dArr3[i] + dArr[i];
                    }
                    i++;
                }
            } else {
                dArr3 = null;
            }
            size--;
        }
        long[] customEnergyConsumerBatteryConsumptionUC2 =
                batteryStats.getCustomEnergyConsumerBatteryConsumptionUC();
        if (customEnergyConsumerBatteryConsumptionUC2 != null) {
            dArr2 = new double[customEnergyConsumerBatteryConsumptionUC2.length];
            for (int i3 = 0; i3 < customEnergyConsumerBatteryConsumptionUC2.length; i3++) {
                dArr2[i3] = customEnergyConsumerBatteryConsumptionUC2[i3] * 2.777777777777778E-7d;
            }
        }
        if (dArr2 != null) {
            AggregateBatteryConsumer.Builder aggregateBatteryConsumerBuilder =
                    builder.getAggregateBatteryConsumerBuilder(0);
            for (int i4 = 0; i4 < dArr2.length; i4++) {
                aggregateBatteryConsumerBuilder.setConsumedPowerForCustomComponent(
                        i4 + 1000, dArr2[i4]);
            }
        }
        if (dArr3 != null) {
            AggregateBatteryConsumer.Builder aggregateBatteryConsumerBuilder2 =
                    builder.getAggregateBatteryConsumerBuilder(1);
            while (i < dArr3.length) {
                aggregateBatteryConsumerBuilder2.setConsumedPowerForCustomComponent(
                        i + 1000, dArr3[i]);
                i++;
            }
        }
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final boolean isPowerComponentSupported(int i) {
        return false;
    }
}
