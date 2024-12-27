package com.android.server.powerstats;

import android.hardware.power.stats.Channel;
import android.hardware.power.stats.EnergyConsumer;
import android.hardware.power.stats.EnergyConsumerResult;
import android.hardware.power.stats.EnergyMeasurement;
import android.hardware.power.stats.PowerEntity;
import android.hardware.power.stats.StateResidencyResult;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public interface PowerStatsHALWrapper$IPowerStatsHALWrapper {
    EnergyConsumerResult[] getEnergyConsumed(int[] iArr);

    EnergyConsumer[] getEnergyConsumerInfo();

    Channel[] getEnergyMeterInfo();

    PowerEntity[] getPowerEntityInfo();

    StateResidencyResult[] getStateResidency(int[] iArr);

    boolean isInitialized();

    EnergyMeasurement[] readEnergyMeter(int[] iArr);
}
