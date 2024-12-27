package com.android.server.power.stats;

public class BinaryStatePowerStatsLayout extends EnergyConsumerPowerStatsLayout {
    public BinaryStatePowerStatsLayout() {
        this.mDeviceDurationPosition = addDeviceSection(1, 1, "usage");
        this.mUidDurationPosition = addUidSection(1, 0, "time");
    }
}
