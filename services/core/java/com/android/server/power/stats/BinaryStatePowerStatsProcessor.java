package com.android.server.power.stats;

import android.os.BatteryStats;
import android.os.PersistableBundle;
import android.util.SparseArray;

import com.android.internal.os.PowerStats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class BinaryStatePowerStatsProcessor extends PowerStatsProcessor {
    public PowerStats.Descriptor mDescriptor;
    public boolean mEnergyConsumerSupported;
    public int mInitiatingUid;
    public int mLastState;
    public long mLastStateTimestamp;
    public long mLastUpdateTimestamp;
    public PowerStatsProcessor.PowerEstimationPlan mPlan;
    public final int mPowerComponentId;
    public PowerStats mPowerStats;
    public final BinaryStatePowerStatsLayout mStatsLayout;
    public long[] mTmpDeviceStatsArray;
    public long[] mTmpUidStatsArray;
    public final PowerStatsUidResolver mUidResolver;
    public final UsageBasedPowerEstimator mUsageBasedPowerEstimator;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Intermediates {
        public long duration;
        public double power;
    }

    public BinaryStatePowerStatsProcessor(
            int i, PowerStatsUidResolver powerStatsUidResolver, double d) {
        this(i, powerStatsUidResolver, d, new BinaryStatePowerStatsLayout());
    }

    public BinaryStatePowerStatsProcessor(
            int i,
            PowerStatsUidResolver powerStatsUidResolver,
            double d,
            BinaryStatePowerStatsLayout binaryStatePowerStatsLayout) {
        this.mInitiatingUid = -1;
        this.mLastState = 0;
        this.mPowerComponentId = i;
        this.mUsageBasedPowerEstimator = new UsageBasedPowerEstimator(d);
        this.mUidResolver = powerStatsUidResolver;
        this.mStatsLayout = binaryStatePowerStatsLayout;
    }

    @Override // com.android.server.power.stats.PowerStatsProcessor
    public final void addPowerStats(
            PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats,
            PowerStats powerStats,
            long j) {
        ensureInitialized();
        if (this.mLastState == 1) {
            recordUsageDuration(this.mPowerStats, this.mInitiatingUid, j);
        }
        long[] jArr = powerStats.stats;
        BinaryStatePowerStatsLayout binaryStatePowerStatsLayout = this.mStatsLayout;
        long consumedEnergy = binaryStatePowerStatsLayout.getConsumedEnergy(0, jArr);
        if (consumedEnergy != -1) {
            this.mEnergyConsumerSupported = true;
            binaryStatePowerStatsLayout.setConsumedEnergy(
                    this.mPowerStats.stats, 0, consumedEnergy);
        }
        flushPowerStats(powerComponentAggregatedPowerStats, j);
    }

    public void computeDevicePowerEstimates(
            PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats,
            PowerStatsProcessor.PowerEstimationPlan powerEstimationPlan,
            boolean z) {
        for (int size = ((ArrayList) powerEstimationPlan.deviceStateEstimations).size() - 1;
                size >= 0;
                size--) {
            PowerStatsProcessor.DeviceStateEstimation deviceStateEstimation =
                    (PowerStatsProcessor.DeviceStateEstimation)
                            ((ArrayList) powerEstimationPlan.deviceStateEstimations).get(size);
            if (powerComponentAggregatedPowerStats.getDeviceStats(
                    deviceStateEstimation.stateValues, this.mTmpDeviceStatsArray)) {
                long[] jArr = this.mTmpDeviceStatsArray;
                BinaryStatePowerStatsLayout binaryStatePowerStatsLayout = this.mStatsLayout;
                long j = jArr[binaryStatePowerStatsLayout.mDeviceDurationPosition];
                if (j > 0) {
                    binaryStatePowerStatsLayout.setDevicePowerEstimate(
                            jArr,
                            z
                                    ? jArr[
                                                    binaryStatePowerStatsLayout
                                                            .mDeviceEnergyConsumerPosition]
                                            * 2.777777777777778E-7d
                                    : j * this.mUsageBasedPowerEstimator.mAveragePowerMahPerMs);
                    powerComponentAggregatedPowerStats.setDeviceStats(
                            deviceStateEstimation.stateValues, this.mTmpDeviceStatsArray);
                }
            }
        }
    }

    public final void ensureInitialized() {
        if (this.mDescriptor != null) {
            return;
        }
        PersistableBundle persistableBundle = new PersistableBundle();
        BinaryStatePowerStatsLayout binaryStatePowerStatsLayout = this.mStatsLayout;
        binaryStatePowerStatsLayout.toExtras(persistableBundle);
        this.mDescriptor =
                new PowerStats.Descriptor(
                        this.mPowerComponentId,
                        binaryStatePowerStatsLayout.mDeviceStatsArrayLength,
                        (SparseArray) null,
                        0,
                        binaryStatePowerStatsLayout.mUidStatsArrayLength,
                        persistableBundle);
        PowerStats powerStats = new PowerStats(this.mDescriptor);
        this.mPowerStats = powerStats;
        PowerStats.Descriptor descriptor = this.mDescriptor;
        int i = descriptor.statsArrayLength;
        powerStats.stats = new long[i];
        this.mTmpDeviceStatsArray = new long[i];
        this.mTmpUidStatsArray = new long[descriptor.uidStatsArrayLength];
    }

    @Override // com.android.server.power.stats.PowerStatsProcessor
    public final void finish(
            PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats, long j) {
        BinaryStatePowerStatsLayout binaryStatePowerStatsLayout;
        BinaryStatePowerStatsLayout binaryStatePowerStatsLayout2;
        int i = 1;
        if (this.mLastState == 1) {
            recordUsageDuration(this.mPowerStats, this.mInitiatingUid, j);
        }
        flushPowerStats(powerComponentAggregatedPowerStats, j);
        if (this.mPlan == null) {
            this.mPlan =
                    new PowerStatsProcessor.PowerEstimationPlan(
                            powerComponentAggregatedPowerStats.mConfig);
        }
        computeDevicePowerEstimates(
                powerComponentAggregatedPowerStats, this.mPlan, this.mEnergyConsumerSupported);
        int size = ((ArrayList) this.mPlan.combinedDeviceStateEstimations).size() - 1;
        while (true) {
            binaryStatePowerStatsLayout = this.mStatsLayout;
            if (size < 0) {
                break;
            }
            PowerStatsProcessor.CombinedDeviceStateEstimate combinedDeviceStateEstimate =
                    (PowerStatsProcessor.CombinedDeviceStateEstimate)
                            ((ArrayList) this.mPlan.combinedDeviceStateEstimations).get(size);
            Intermediates intermediates = new Intermediates();
            combinedDeviceStateEstimate.intermediates = intermediates;
            for (int size2 =
                            ((ArrayList) combinedDeviceStateEstimate.deviceStateEstimations).size()
                                    - 1;
                    size2 >= 0;
                    size2--) {
                if (powerComponentAggregatedPowerStats.getDeviceStats(
                        ((PowerStatsProcessor.DeviceStateEstimation)
                                        ((ArrayList)
                                                        combinedDeviceStateEstimate
                                                                .deviceStateEstimations)
                                                .get(size2))
                                .stateValues,
                        this.mTmpDeviceStatsArray)) {
                    intermediates.power =
                            (this.mTmpDeviceStatsArray[
                                                    binaryStatePowerStatsLayout
                                                            .mDevicePowerEstimatePosition]
                                            / 1000000.0d)
                                    + intermediates.power;
                }
            }
            size--;
        }
        ArrayList arrayList = new ArrayList();
        powerComponentAggregatedPowerStats.collectUids(arrayList);
        for (int size3 = ((ArrayList) this.mPlan.uidStateEstimates).size() - 1;
                size3 >= 0;
                size3--) {
            PowerStatsProcessor.UidStateEstimate uidStateEstimate =
                    (PowerStatsProcessor.UidStateEstimate)
                            ((ArrayList) this.mPlan.uidStateEstimates).get(size3);
            Intermediates intermediates2 =
                    (Intermediates) uidStateEstimate.combinedDeviceStateEstimate.intermediates;
            for (int size4 = arrayList.size() - 1; size4 >= 0; size4--) {
                int intValue = ((Integer) arrayList.get(size4)).intValue();
                Iterator it = ((ArrayList) uidStateEstimate.proportionalEstimates).iterator();
                while (it.hasNext()) {
                    if (powerComponentAggregatedPowerStats.getUidStats(
                            intValue,
                            ((PowerStatsProcessor.UidStateProportionalEstimate) it.next())
                                    .stateValues,
                            this.mTmpUidStatsArray)) {
                        intermediates2.duration +=
                                this.mTmpUidStatsArray[
                                        binaryStatePowerStatsLayout.mUidDurationPosition];
                    }
                }
            }
        }
        int size5 = ((ArrayList) this.mPlan.uidStateEstimates).size() - 1;
        while (size5 >= 0) {
            PowerStatsProcessor.UidStateEstimate uidStateEstimate2 =
                    (PowerStatsProcessor.UidStateEstimate)
                            ((ArrayList) this.mPlan.uidStateEstimates).get(size5);
            Intermediates intermediates3 =
                    (Intermediates) uidStateEstimate2.combinedDeviceStateEstimate.intermediates;
            if (intermediates3.duration != 0) {
                ArrayList arrayList2 = (ArrayList) uidStateEstimate2.proportionalEstimates;
                int size6 = arrayList2.size() - i;
                while (size6 >= 0) {
                    PowerStatsProcessor.UidStateProportionalEstimate uidStateProportionalEstimate =
                            (PowerStatsProcessor.UidStateProportionalEstimate)
                                    arrayList2.get(size6);
                    int size7 = arrayList.size() - i;
                    while (size7 >= 0) {
                        int intValue2 = ((Integer) arrayList.get(size7)).intValue();
                        if (powerComponentAggregatedPowerStats.getUidStats(
                                intValue2,
                                uidStateProportionalEstimate.stateValues,
                                this.mTmpUidStatsArray)) {
                            double d = intermediates3.power;
                            binaryStatePowerStatsLayout2 = binaryStatePowerStatsLayout;
                            binaryStatePowerStatsLayout2.setUidPowerEstimate(
                                    this.mTmpUidStatsArray,
                                    (d * r14[binaryStatePowerStatsLayout.mUidDurationPosition])
                                            / intermediates3.duration);
                            powerComponentAggregatedPowerStats.setUidStats(
                                    intValue2,
                                    uidStateProportionalEstimate.stateValues,
                                    this.mTmpUidStatsArray);
                        } else {
                            binaryStatePowerStatsLayout2 = binaryStatePowerStatsLayout;
                        }
                        size7--;
                        binaryStatePowerStatsLayout = binaryStatePowerStatsLayout2;
                    }
                    size6--;
                    i = 1;
                }
            }
            size5--;
            binaryStatePowerStatsLayout = binaryStatePowerStatsLayout;
            i = 1;
        }
    }

    public final void flushPowerStats(
            PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats, long j) {
        PowerStats powerStats = this.mPowerStats;
        powerStats.durationMs = j - this.mLastUpdateTimestamp;
        powerComponentAggregatedPowerStats.addProcessedPowerStats(powerStats, j);
        Arrays.fill(this.mPowerStats.stats, 0L);
        this.mPowerStats.uidStats.clear();
        this.mLastUpdateTimestamp = j;
    }

    public abstract int getBinaryState(BatteryStats.HistoryItem historyItem);

    @Override // com.android.server.power.stats.PowerStatsProcessor
    public final void noteStateChange(
            PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats,
            BatteryStats.HistoryItem historyItem) {
        int binaryState = getBinaryState(historyItem);
        if (binaryState == this.mLastState) {
            return;
        }
        if (binaryState != 1) {
            recordUsageDuration(this.mPowerStats, this.mInitiatingUid, historyItem.time);
            this.mInitiatingUid = -1;
            if (!this.mEnergyConsumerSupported) {
                flushPowerStats(powerComponentAggregatedPowerStats, historyItem.time);
            }
        } else if (historyItem.eventCode == 32789) {
            this.mInitiatingUid = this.mUidResolver.mapUid(historyItem.eventTag.uid);
        }
        this.mLastStateTimestamp = historyItem.time;
        this.mLastState = binaryState;
    }

    public void recordUsageDuration(PowerStats powerStats, int i, long j) {
        long j2 = j - this.mLastStateTimestamp;
        PowerStats powerStats2 = this.mPowerStats;
        long[] jArr = powerStats2.stats;
        BinaryStatePowerStatsLayout binaryStatePowerStatsLayout = this.mStatsLayout;
        int i2 = binaryStatePowerStatsLayout.mDeviceDurationPosition;
        jArr[i2] = jArr[i2] + j2;
        if (i != -1) {
            if (((long[]) powerStats2.uidStats.get(i)) == null) {
                long[] jArr2 = new long[this.mDescriptor.uidStatsArrayLength];
                this.mPowerStats.uidStats.put(i, jArr2);
                jArr2[binaryStatePowerStatsLayout.mUidDurationPosition] = j2;
            } else {
                long[] jArr3 = this.mPowerStats.stats;
                int i3 = binaryStatePowerStatsLayout.mDeviceDurationPosition;
                jArr3[i3] = jArr3[i3] + j2;
            }
        }
        this.mLastStateTimestamp = j;
    }

    @Override // com.android.server.power.stats.PowerStatsProcessor
    public final void start(
            PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats, long j) {
        ensureInitialized();
        this.mLastState = 0;
        this.mLastStateTimestamp = j;
        this.mInitiatingUid = -1;
        flushPowerStats(powerComponentAggregatedPowerStats, j);
    }
}
