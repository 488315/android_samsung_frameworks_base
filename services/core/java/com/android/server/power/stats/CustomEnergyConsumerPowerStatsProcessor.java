package com.android.server.power.stats;

import com.android.internal.os.PowerStats;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class CustomEnergyConsumerPowerStatsProcessor extends PowerStatsProcessor {
    public static final EnergyConsumerPowerStatsLayout sLayout =
            new EnergyConsumerPowerStatsLayout();
    public PowerStatsProcessor.PowerEstimationPlan mPlan;
    public long[] mTmpDeviceStatsArray;
    public long[] mTmpUidStatsArray;

    @Override // com.android.server.power.stats.PowerStatsProcessor
    public final void finish(
            PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats, long j) {
        EnergyConsumerPowerStatsLayout energyConsumerPowerStatsLayout;
        PowerStats.Descriptor descriptor = powerComponentAggregatedPowerStats.mPowerStatsDescriptor;
        this.mTmpDeviceStatsArray = new long[descriptor.statsArrayLength];
        this.mTmpUidStatsArray = new long[descriptor.uidStatsArrayLength];
        if (this.mPlan == null) {
            this.mPlan =
                    new PowerStatsProcessor.PowerEstimationPlan(
                            powerComponentAggregatedPowerStats.mConfig);
        }
        int size = ((ArrayList) this.mPlan.deviceStateEstimations).size();
        while (true) {
            size--;
            energyConsumerPowerStatsLayout = sLayout;
            if (size < 0) {
                break;
            }
            PowerStatsProcessor.DeviceStateEstimation deviceStateEstimation =
                    (PowerStatsProcessor.DeviceStateEstimation)
                            ((ArrayList) this.mPlan.deviceStateEstimations).get(size);
            if (powerComponentAggregatedPowerStats.getDeviceStats(
                    deviceStateEstimation.stateValues, this.mTmpDeviceStatsArray)) {
                energyConsumerPowerStatsLayout.setDevicePowerEstimate(
                        this.mTmpDeviceStatsArray,
                        energyConsumerPowerStatsLayout.getConsumedEnergy(0, r3)
                                * 2.777777777777778E-7d);
                powerComponentAggregatedPowerStats.setDeviceStats(
                        deviceStateEstimation.stateValues, this.mTmpDeviceStatsArray);
            }
        }
        ArrayList arrayList = new ArrayList();
        powerComponentAggregatedPowerStats.collectUids(arrayList);
        if (arrayList.isEmpty()) {
            return;
        }
        for (int size2 = ((ArrayList) this.mPlan.uidStateEstimates).size() - 1;
                size2 >= 0;
                size2--) {
            ArrayList arrayList2 =
                    (ArrayList)
                            ((PowerStatsProcessor.UidStateEstimate)
                                            ((ArrayList) this.mPlan.uidStateEstimates).get(size2))
                                    .proportionalEstimates;
            for (int size3 = arrayList2.size() - 1; size3 >= 0; size3--) {
                PowerStatsProcessor.UidStateProportionalEstimate uidStateProportionalEstimate =
                        (PowerStatsProcessor.UidStateProportionalEstimate) arrayList2.get(size3);
                for (int size4 = arrayList.size() - 1; size4 >= 0; size4--) {
                    int intValue = ((Integer) arrayList.get(size4)).intValue();
                    if (powerComponentAggregatedPowerStats.getUidStats(
                            intValue,
                            uidStateProportionalEstimate.stateValues,
                            this.mTmpUidStatsArray)) {
                        energyConsumerPowerStatsLayout.setUidPowerEstimate(
                                this.mTmpUidStatsArray,
                                r8[energyConsumerPowerStatsLayout.mUidEnergyConsumerPosition]
                                        * 2.777777777777778E-7d);
                        powerComponentAggregatedPowerStats.setUidStats(
                                intValue,
                                uidStateProportionalEstimate.stateValues,
                                this.mTmpUidStatsArray);
                    }
                }
            }
        }
    }
}
