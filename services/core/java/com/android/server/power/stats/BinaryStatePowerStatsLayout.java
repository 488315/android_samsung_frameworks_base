package com.android.server.power.stats;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class BinaryStatePowerStatsLayout extends EnergyConsumerPowerStatsLayout {
    public BinaryStatePowerStatsLayout() {
        this.mDeviceDurationPosition = addDeviceSection(1, 1, "usage");
        this.mUidDurationPosition = addUidSection(1, 0, "time");
    }
}
