package com.android.server.powerstats;

import android.hardware.power.stats.Channel;
import android.hardware.power.stats.EnergyConsumer;
import android.hardware.power.stats.EnergyConsumerResult;
import android.hardware.power.stats.EnergyMeasurement;
import android.hardware.power.stats.PowerEntity;
import android.hardware.power.stats.StateResidencyResult;

public interface PowerStatsHALWrapper$IPowerStatsHALWrapper {
    EnergyConsumerResult[] getEnergyConsumed(int[] iArr);

    EnergyConsumer[] getEnergyConsumerInfo();

    Channel[] getEnergyMeterInfo();

    PowerEntity[] getPowerEntityInfo();

    StateResidencyResult[] getStateResidency(int[] iArr);

    boolean isInitialized();

    EnergyMeasurement[] readEnergyMeter(int[] iArr);
}
