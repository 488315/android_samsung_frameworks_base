package com.android.server.power.stats;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class EnergyConsumerPowerStatsLayout extends PowerStatsLayout {
    public EnergyConsumerPowerStatsLayout() {
        addDeviceSectionEnergyConsumers(1);
        addDeviceSectionPowerEstimate();
        this.mUidEnergyConsumerPosition = addUidSection(1, 1, "energy");
        this.mUidEnergyConsumerCount = 1;
        this.mUidPowerEstimatePosition = addUidSection(1, 5, "power");
    }
}
