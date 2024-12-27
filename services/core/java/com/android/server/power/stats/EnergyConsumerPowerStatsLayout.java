package com.android.server.power.stats;

public class EnergyConsumerPowerStatsLayout extends PowerStatsLayout {
    public EnergyConsumerPowerStatsLayout() {
        addDeviceSectionEnergyConsumers(1);
        addDeviceSectionPowerEstimate();
        this.mUidEnergyConsumerPosition = addUidSection(1, 1, "energy");
        this.mUidEnergyConsumerCount = 1;
        this.mUidPowerEstimatePosition = addUidSection(1, 5, "power");
    }
}
