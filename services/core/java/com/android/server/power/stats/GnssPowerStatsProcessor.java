package com.android.server.power.stats;

import android.os.BatteryStats;

import com.android.internal.os.PowerProfile;
import com.android.internal.os.PowerStats;
import com.android.internal.util.FrameworkStatsLog;

import java.util.ArrayList;
import java.util.Arrays;

public final class GnssPowerStatsProcessor extends BinaryStatePowerStatsProcessor {
    public static final GnssPowerStatsLayout sStatsLayout = new GnssPowerStatsLayout();
    public final long[] mGnssSignalDurations;
    public int mGnssSignalLevel;
    public long mGnssSignalLevelTimestamp;
    public final UsageBasedPowerEstimator[] mSignalLevelEstimators;
    public long[] mTmpDeviceStatsArray;
    public final boolean mUseSignalLevelEstimators;

    public GnssPowerStatsProcessor(
            PowerProfile powerProfile, PowerStatsUidResolver powerStatsUidResolver) {
        super(10, powerStatsUidResolver, powerProfile.getAveragePower("gps.on"), sStatsLayout);
        this.mGnssSignalLevel = -1;
        this.mGnssSignalDurations = new long[2];
        this.mSignalLevelEstimators = new UsageBasedPowerEstimator[2];
        boolean z = false;
        for (int i = 0; i < 2; i++) {
            double averagePower = powerProfile.getAveragePower("gps.signalqualitybased", i);
            if (averagePower != 0.0d) {
                z = true;
            }
            this.mSignalLevelEstimators[i] = new UsageBasedPowerEstimator(averagePower);
        }
        this.mUseSignalLevelEstimators = z;
    }

    @Override // com.android.server.power.stats.BinaryStatePowerStatsProcessor
    public final void computeDevicePowerEstimates(
            PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats,
            PowerStatsProcessor.PowerEstimationPlan powerEstimationPlan,
            boolean z) {
        GnssPowerStatsLayout gnssPowerStatsLayout;
        if (!this.mUseSignalLevelEstimators || z) {
            super.computeDevicePowerEstimates(
                    powerComponentAggregatedPowerStats, powerEstimationPlan, z);
            return;
        }
        if (this.mTmpDeviceStatsArray == null) {
            this.mTmpDeviceStatsArray =
                    new long
                            [powerComponentAggregatedPowerStats
                                    .mPowerStatsDescriptor
                                    .statsArrayLength];
        }
        for (int size = ((ArrayList) powerEstimationPlan.deviceStateEstimations).size() - 1;
                size >= 0;
                size--) {
            PowerStatsProcessor.DeviceStateEstimation deviceStateEstimation =
                    (PowerStatsProcessor.DeviceStateEstimation)
                            ((ArrayList) powerEstimationPlan.deviceStateEstimations).get(size);
            if (powerComponentAggregatedPowerStats.getDeviceStats(
                    deviceStateEstimation.stateValues, this.mTmpDeviceStatsArray)) {
                double d = 0.0d;
                int i = 0;
                while (true) {
                    gnssPowerStatsLayout = sStatsLayout;
                    if (i >= 2) {
                        break;
                    }
                    d +=
                            this.mSignalLevelEstimators[i].mAveragePowerMahPerMs
                                    * this.mTmpDeviceStatsArray[
                                            gnssPowerStatsLayout.mDeviceSignalLevelTimePosition
                                                    + i];
                    i++;
                }
                gnssPowerStatsLayout.setDevicePowerEstimate(this.mTmpDeviceStatsArray, d);
                powerComponentAggregatedPowerStats.setDeviceStats(
                        deviceStateEstimation.stateValues, this.mTmpDeviceStatsArray);
            }
        }
    }

    @Override // com.android.server.power.stats.BinaryStatePowerStatsProcessor
    public final int getBinaryState(BatteryStats.HistoryItem historyItem) {
        if ((historyItem.states & 536870912) == 0) {
            this.mGnssSignalLevel = -1;
            return 0;
        }
        int i = (historyItem.states2 & FrameworkStatsLog.NON_A11Y_TOOL_SERVICE_WARNING_REPORT) >> 7;
        if (i >= 2) {
            i = -1;
        }
        int i2 = this.mGnssSignalLevel;
        if (i == i2) {
            return 1;
        }
        if (i2 != -1) {
            long[] jArr = this.mGnssSignalDurations;
            jArr[i2] = (historyItem.time - this.mGnssSignalLevelTimestamp) + jArr[i2];
        }
        this.mGnssSignalLevel = i;
        this.mGnssSignalLevelTimestamp = historyItem.time;
        return 1;
    }

    @Override // com.android.server.power.stats.BinaryStatePowerStatsProcessor
    public final void recordUsageDuration(PowerStats powerStats, int i, long j) {
        super.recordUsageDuration(powerStats, i, j);
        int i2 = this.mGnssSignalLevel;
        long[] jArr = this.mGnssSignalDurations;
        if (i2 != -1) {
            jArr[i2] = (j - this.mGnssSignalLevelTimestamp) + jArr[i2];
        } else if (this.mUseSignalLevelEstimators) {
            jArr[1] = (j - this.mGnssSignalLevelTimestamp) + jArr[1];
        }
        for (int i3 = 0; i3 < 2; i3++) {
            long j2 = jArr[i3];
            long[] jArr2 = powerStats.stats;
            GnssPowerStatsLayout gnssPowerStatsLayout = sStatsLayout;
            jArr2[gnssPowerStatsLayout.mDeviceSignalLevelTimePosition + i3] = j2;
            if (i != -1) {
                long[] jArr3 = (long[]) powerStats.uidStats.get(i);
                if (jArr3 == null) {
                    long[] jArr4 = new long[powerStats.descriptor.uidStatsArrayLength];
                    powerStats.uidStats.put(i, jArr4);
                    jArr4[gnssPowerStatsLayout.mUidSignalLevelTimePosition + i3] = j2;
                } else {
                    int i4 = gnssPowerStatsLayout.mUidSignalLevelTimePosition;
                    jArr3[i4 + i3] = jArr3[i4 + i3] + j2;
                }
            }
        }
        this.mGnssSignalLevelTimestamp = j;
        Arrays.fill(jArr, 0L);
    }
}
