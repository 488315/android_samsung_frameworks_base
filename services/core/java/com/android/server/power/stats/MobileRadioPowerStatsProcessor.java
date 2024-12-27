package com.android.server.power.stats;

import android.telephony.CellSignalStrength;
import android.telephony.ModemActivityInfo;
import android.util.Log;
import android.util.SparseArray;

import com.android.internal.os.PowerProfile;
import com.android.internal.os.PowerStats;
import com.android.internal.power.ModemPowerProfile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.IntConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class MobileRadioPowerStatsProcessor extends PowerStatsProcessor {
    public static final int NUM_SIGNAL_STRENGTH_LEVELS =
            CellSignalStrength.getNumSignalStrengthLevels();
    public final UsageBasedPowerEstimator mCallPowerEstimator;
    public final UsageBasedPowerEstimator mIdlePowerEstimator;
    public PowerStats.Descriptor mLastUsedDescriptor;
    public PowerStatsProcessor.PowerEstimationPlan mPlan;
    public final SparseArray mRxTxPowerEstimators = new SparseArray();
    public final UsageBasedPowerEstimator mScanPowerEstimator;
    public final UsageBasedPowerEstimator mSleepPowerEstimator;
    public MobileRadioPowerStatsLayout mStatsLayout;
    public long[] mTmpDeviceStatsArray;
    public long[] mTmpStateStatsArray;
    public long[] mTmpUidStatsArray;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Intermediates {
        public double callPower;
        public long consumedEnergy;
        public double inactivePower;
        public long rxPackets;
        public double rxPower;
        public long txPackets;
        public double txPower;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RxTxPowerEstimators {
        public UsageBasedPowerEstimator mRxPowerEstimator;
        public UsageBasedPowerEstimator[] mTxPowerEstimators;
    }

    public MobileRadioPowerStatsProcessor(PowerProfile powerProfile) {
        double d;
        double d2;
        double d3 = Double.NaN;
        double averageBatteryDrainOrDefaultMa =
                powerProfile.getAverageBatteryDrainOrDefaultMa(4294967296L, Double.NaN);
        if (Double.isNaN(averageBatteryDrainOrDefaultMa)) {
            this.mSleepPowerEstimator = null;
        } else {
            this.mSleepPowerEstimator =
                    new UsageBasedPowerEstimator(averageBatteryDrainOrDefaultMa);
        }
        double averageBatteryDrainOrDefaultMa2 =
                powerProfile.getAverageBatteryDrainOrDefaultMa(4563402752L, Double.NaN);
        if (Double.isNaN(averageBatteryDrainOrDefaultMa2)) {
            this.mIdlePowerEstimator = null;
        } else {
            this.mIdlePowerEstimator =
                    new UsageBasedPowerEstimator(averageBatteryDrainOrDefaultMa2);
        }
        double averagePowerOrDefault =
                powerProfile.getAveragePowerOrDefault("radio.active", Double.NaN);
        int i = 1;
        if (Double.isNaN(averagePowerOrDefault)) {
            double averagePower = powerProfile.getAveragePower("modem.controller.rx") + 0.0d;
            int i2 = 0;
            while (true) {
                if (i2 >= NUM_SIGNAL_STRENGTH_LEVELS) {
                    break;
                }
                averagePower += powerProfile.getAveragePower("modem.controller.tx", i2);
                i2++;
            }
            averagePowerOrDefault = averagePower / (r11 + 1);
        }
        this.mCallPowerEstimator = new UsageBasedPowerEstimator(averagePowerOrDefault);
        this.mScanPowerEstimator =
                new UsageBasedPowerEstimator(
                        powerProfile.getAveragePowerOrDefault("radio.scanning", 0.0d));
        int i3 = 0;
        while (i3 < 3) {
            int i4 = 2;
            int i5 = i3 == 2 ? 5 : i;
            int i6 = 0;
            while (i6 < i5) {
                SparseArray sparseArray = this.mRxTxPowerEstimators;
                int i7 = i3 == i4 ? (i6 << 8) | i3 : i3;
                RxTxPowerEstimators rxTxPowerEstimators = new RxTxPowerEstimators();
                rxTxPowerEstimators.mTxPowerEstimators =
                        new UsageBasedPowerEstimator[ModemActivityInfo.getNumTxPowerLevels()];
                long averageBatteryDrainKey =
                        ModemPowerProfile.getAverageBatteryDrainKey(536870912, i3, i6, -1);
                double averageBatteryDrainOrDefaultMa3 =
                        powerProfile.getAverageBatteryDrainOrDefaultMa(averageBatteryDrainKey, d3);
                if (Double.isNaN(averageBatteryDrainOrDefaultMa3)) {
                    Log.w(
                            "MobileRadioPowerStatsProcessor",
                            "Unavailable Power Profile constant for key 0x"
                                    + Long.toHexString(averageBatteryDrainKey));
                    d = 0.0d;
                } else {
                    d = averageBatteryDrainOrDefaultMa3;
                }
                rxTxPowerEstimators.mRxPowerEstimator = new UsageBasedPowerEstimator(d);
                int i8 = 0;
                while (i8 < ModemActivityInfo.getNumTxPowerLevels()) {
                    long averageBatteryDrainKey2 =
                            ModemPowerProfile.getAverageBatteryDrainKey(805306368, i3, i6, i8);
                    double averageBatteryDrainOrDefaultMa4 =
                            powerProfile.getAverageBatteryDrainOrDefaultMa(
                                    averageBatteryDrainKey2, d3);
                    if (Double.isNaN(averageBatteryDrainOrDefaultMa4)) {
                        Log.w(
                                "MobileRadioPowerStatsProcessor",
                                "Unavailable Power Profile constant for key 0x"
                                        + Long.toHexString(averageBatteryDrainKey2));
                        d2 = 0.0d;
                    } else {
                        d2 = averageBatteryDrainOrDefaultMa4;
                    }
                    rxTxPowerEstimators.mTxPowerEstimators[i8] = new UsageBasedPowerEstimator(d2);
                    i8++;
                    d3 = Double.NaN;
                }
                sparseArray.put(i7, rxTxPowerEstimators);
                i6++;
                i4 = 2;
                d3 = Double.NaN;
            }
            i3++;
            d3 = Double.NaN;
            i = 1;
        }
    }

    @Override // com.android.server.power.stats.PowerStatsProcessor
    public final void finish(
            final PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats, long j) {
        PowerStats.Descriptor descriptor = powerComponentAggregatedPowerStats.mPowerStatsDescriptor;
        if (descriptor == null) {
            return;
        }
        if (!descriptor.equals(this.mLastUsedDescriptor)) {
            this.mLastUsedDescriptor = descriptor;
            this.mStatsLayout = new MobileRadioPowerStatsLayout(descriptor);
            this.mTmpDeviceStatsArray = new long[descriptor.statsArrayLength];
            this.mTmpStateStatsArray = new long[descriptor.stateStatsArrayLength];
            this.mTmpUidStatsArray = new long[descriptor.uidStatsArrayLength];
        }
        if (this.mPlan == null) {
            this.mPlan =
                    new PowerStatsProcessor.PowerEstimationPlan(
                            powerComponentAggregatedPowerStats.mConfig);
        }
        int size = ((ArrayList) this.mPlan.deviceStateEstimations).size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            PowerStatsProcessor.DeviceStateEstimation deviceStateEstimation =
                    (PowerStatsProcessor.DeviceStateEstimation)
                            ((ArrayList) this.mPlan.deviceStateEstimations).get(size);
            final Intermediates intermediates = new Intermediates();
            deviceStateEstimation.intermediates = intermediates;
            long[] jArr = this.mTmpDeviceStatsArray;
            final int[] iArr = deviceStateEstimation.stateValues;
            if (powerComponentAggregatedPowerStats.getDeviceStats(iArr, jArr)) {
                for (int i = this.mStatsLayout.mDeviceEnergyConsumerCount - 1; i >= 0; i--) {
                    intermediates.consumedEnergy =
                            this.mStatsLayout.getConsumedEnergy(i, this.mTmpDeviceStatsArray)
                                    + intermediates.consumedEnergy;
                }
                UsageBasedPowerEstimator usageBasedPowerEstimator = this.mSleepPowerEstimator;
                if (usageBasedPowerEstimator != null) {
                    intermediates.inactivePower =
                            (usageBasedPowerEstimator.mAveragePowerMahPerMs
                                            * this.mTmpDeviceStatsArray[
                                                    this.mStatsLayout.mDeviceSleepTimePosition])
                                    + intermediates.inactivePower;
                }
                UsageBasedPowerEstimator usageBasedPowerEstimator2 = this.mIdlePowerEstimator;
                if (usageBasedPowerEstimator2 != null) {
                    intermediates.inactivePower =
                            (usageBasedPowerEstimator2.mAveragePowerMahPerMs
                                            * this.mTmpDeviceStatsArray[
                                                    this.mStatsLayout.mDeviceIdleTimePosition])
                                    + intermediates.inactivePower;
                }
                UsageBasedPowerEstimator usageBasedPowerEstimator3 = this.mScanPowerEstimator;
                if (usageBasedPowerEstimator3 != null) {
                    intermediates.inactivePower =
                            (usageBasedPowerEstimator3.mAveragePowerMahPerMs
                                            * this.mTmpDeviceStatsArray[
                                                    this.mStatsLayout.mDeviceScanTimePosition])
                                    + intermediates.inactivePower;
                }
                IntConsumer intConsumer =
                        new IntConsumer() { // from class:
                                            // com.android.server.power.stats.MobileRadioPowerStatsProcessor$$ExternalSyntheticLambda0
                            @Override // java.util.function.IntConsumer
                            public final void accept(int i2) {
                                MobileRadioPowerStatsProcessor mobileRadioPowerStatsProcessor =
                                        MobileRadioPowerStatsProcessor.this;
                                PowerComponentAggregatedPowerStats
                                        powerComponentAggregatedPowerStats2 =
                                                powerComponentAggregatedPowerStats;
                                int[] iArr2 = iArr;
                                MobileRadioPowerStatsProcessor.Intermediates intermediates2 =
                                        intermediates;
                                MobileRadioPowerStatsProcessor.RxTxPowerEstimators
                                        rxTxPowerEstimators =
                                                (MobileRadioPowerStatsProcessor.RxTxPowerEstimators)
                                                        mobileRadioPowerStatsProcessor
                                                                .mRxTxPowerEstimators.get(i2);
                                long[] jArr2 = mobileRadioPowerStatsProcessor.mTmpStateStatsArray;
                                powerComponentAggregatedPowerStats2.getClass();
                                int length = iArr2.length;
                                MultiStateStats.States[] statesArr =
                                        powerComponentAggregatedPowerStats2.mDeviceStateConfig;
                                if (length != statesArr.length) {
                                    throw new IllegalArgumentException(
                                            "Invalid number of tracked states: "
                                                    + iArr2.length
                                                    + " expected: "
                                                    + statesArr.length);
                                }
                                MultiStateStats multiStateStats =
                                        (MultiStateStats)
                                                powerComponentAggregatedPowerStats2.mStateStats.get(
                                                        i2);
                                if (multiStateStats != null) {
                                    multiStateStats.mCounter.getCounts(
                                            jArr2, multiStateStats.mFactory.getSerialState(iArr2));
                                }
                                intermediates2.rxPower =
                                        (rxTxPowerEstimators.mRxPowerEstimator.mAveragePowerMahPerMs
                                                        * mobileRadioPowerStatsProcessor
                                                                .mTmpStateStatsArray[
                                                                mobileRadioPowerStatsProcessor
                                                                        .mStatsLayout
                                                                        .mStateRxTimePosition])
                                                + intermediates2.rxPower;
                                for (int i3 = 0;
                                        i3 < ModemActivityInfo.getNumTxPowerLevels();
                                        i3++) {
                                    intermediates2.txPower =
                                            (rxTxPowerEstimators
                                                                    .mTxPowerEstimators[i3]
                                                                    .mAveragePowerMahPerMs
                                                            * mobileRadioPowerStatsProcessor
                                                                    .mTmpStateStatsArray[
                                                                    mobileRadioPowerStatsProcessor
                                                                                    .mStatsLayout
                                                                                    .mStateTxTimesPosition
                                                                            + i3])
                                                    + intermediates2.txPower;
                                }
                            }
                        };
                for (int size2 = powerComponentAggregatedPowerStats.mStateStats.size() - 1;
                        size2 >= 0;
                        size2--) {
                    intConsumer.accept(powerComponentAggregatedPowerStats.mStateStats.keyAt(size2));
                }
                UsageBasedPowerEstimator usageBasedPowerEstimator4 = this.mCallPowerEstimator;
                if (usageBasedPowerEstimator4 != null) {
                    intermediates.callPower =
                            usageBasedPowerEstimator4.mAveragePowerMahPerMs
                                    * this.mTmpDeviceStatsArray[
                                            this.mStatsLayout.mDeviceCallTimePosition];
                }
                this.mStatsLayout.setDevicePowerEstimate(
                        this.mTmpDeviceStatsArray,
                        intermediates.rxPower
                                + intermediates.txPower
                                + intermediates.inactivePower);
                MobileRadioPowerStatsLayout mobileRadioPowerStatsLayout = this.mStatsLayout;
                long[] jArr2 = this.mTmpDeviceStatsArray;
                jArr2[mobileRadioPowerStatsLayout.mDeviceCallPowerPosition] =
                        (long) (intermediates.callPower * 1000000.0d);
                powerComponentAggregatedPowerStats.setDeviceStats(iArr, jArr2);
            }
        }
        if (this.mStatsLayout.mDeviceEnergyConsumerCount != 0) {
            double d = 0.0d;
            long j2 = 0;
            for (int size3 = ((ArrayList) this.mPlan.deviceStateEstimations).size() - 1;
                    size3 >= 0;
                    size3--) {
                Intermediates intermediates2 =
                        (Intermediates)
                                ((PowerStatsProcessor.DeviceStateEstimation)
                                                ((ArrayList) this.mPlan.deviceStateEstimations)
                                                        .get(size3))
                                        .intermediates;
                d +=
                        intermediates2.rxPower
                                + intermediates2.txPower
                                + intermediates2.inactivePower
                                + intermediates2.callPower;
                j2 += intermediates2.consumedEnergy;
            }
            double d2 = d == 0.0d ? 1.0d : (j2 * 2.777777777777778E-7d) / d;
            if (d2 != 1.0d) {
                for (int size4 = ((ArrayList) this.mPlan.deviceStateEstimations).size() - 1;
                        size4 >= 0;
                        size4--) {
                    PowerStatsProcessor.DeviceStateEstimation deviceStateEstimation2 =
                            (PowerStatsProcessor.DeviceStateEstimation)
                                    ((ArrayList) this.mPlan.deviceStateEstimations).get(size4);
                    int[] iArr2 = deviceStateEstimation2.stateValues;
                    Intermediates intermediates3 =
                            (Intermediates) deviceStateEstimation2.intermediates;
                    intermediates3.rxPower *= d2;
                    intermediates3.txPower *= d2;
                    intermediates3.inactivePower *= d2;
                    intermediates3.callPower *= d2;
                    if (powerComponentAggregatedPowerStats.getDeviceStats(
                            iArr2, this.mTmpDeviceStatsArray)) {
                        this.mStatsLayout.setDevicePowerEstimate(
                                this.mTmpDeviceStatsArray,
                                intermediates3.rxPower
                                        + intermediates3.txPower
                                        + intermediates3.inactivePower);
                        MobileRadioPowerStatsLayout mobileRadioPowerStatsLayout2 =
                                this.mStatsLayout;
                        long[] jArr3 = this.mTmpDeviceStatsArray;
                        jArr3[mobileRadioPowerStatsLayout2.mDeviceCallPowerPosition] =
                                (long) (intermediates3.callPower * 1000000.0d);
                        powerComponentAggregatedPowerStats.setDeviceStats(iArr2, jArr3);
                    }
                }
            }
        }
        for (int size5 = ((ArrayList) this.mPlan.combinedDeviceStateEstimations).size() - 1;
                size5 >= 0;
                size5--) {
            PowerStatsProcessor.CombinedDeviceStateEstimate combinedDeviceStateEstimate =
                    (PowerStatsProcessor.CombinedDeviceStateEstimate)
                            ((ArrayList) this.mPlan.combinedDeviceStateEstimations).get(size5);
            Intermediates intermediates4 = new Intermediates();
            combinedDeviceStateEstimate.intermediates = intermediates4;
            ArrayList arrayList = (ArrayList) combinedDeviceStateEstimate.deviceStateEstimations;
            for (int size6 = arrayList.size() - 1; size6 >= 0; size6--) {
                Intermediates intermediates5 =
                        (Intermediates)
                                ((PowerStatsProcessor.DeviceStateEstimation) arrayList.get(size6))
                                        .intermediates;
                intermediates4.rxPower += intermediates5.rxPower;
                intermediates4.txPower += intermediates5.txPower;
                intermediates4.inactivePower += intermediates5.inactivePower;
                intermediates4.consumedEnergy += intermediates5.consumedEnergy;
            }
        }
        ArrayList arrayList2 = new ArrayList();
        powerComponentAggregatedPowerStats.collectUids(arrayList2);
        if (!arrayList2.isEmpty()) {
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                for (int i2 = 0; i2 < ((ArrayList) this.mPlan.uidStateEstimates).size(); i2++) {
                    PowerStatsProcessor.UidStateEstimate uidStateEstimate =
                            (PowerStatsProcessor.UidStateEstimate)
                                    ((ArrayList) this.mPlan.uidStateEstimates).get(i2);
                    Intermediates intermediates6 =
                            (Intermediates)
                                    uidStateEstimate.combinedDeviceStateEstimate.intermediates;
                    Iterator it2 = ((ArrayList) uidStateEstimate.proportionalEstimates).iterator();
                    while (it2.hasNext()) {
                        if (powerComponentAggregatedPowerStats.getUidStats(
                                intValue,
                                ((PowerStatsProcessor.UidStateProportionalEstimate) it2.next())
                                        .stateValues,
                                this.mTmpUidStatsArray)) {
                            long j3 = intermediates6.rxPackets;
                            MobileRadioPowerStatsLayout mobileRadioPowerStatsLayout3 =
                                    this.mStatsLayout;
                            long[] jArr4 = this.mTmpUidStatsArray;
                            intermediates6.rxPackets =
                                    j3 + jArr4[mobileRadioPowerStatsLayout3.mUidRxPacketsPosition];
                            intermediates6.txPackets +=
                                    jArr4[mobileRadioPowerStatsLayout3.mUidTxPacketsPosition];
                        }
                    }
                }
            }
            Iterator it3 = arrayList2.iterator();
            while (it3.hasNext()) {
                int intValue2 = ((Integer) it3.next()).intValue();
                for (int i3 = 0; i3 < ((ArrayList) this.mPlan.uidStateEstimates).size(); i3++) {
                    PowerStatsProcessor.UidStateEstimate uidStateEstimate2 =
                            (PowerStatsProcessor.UidStateEstimate)
                                    ((ArrayList) this.mPlan.uidStateEstimates).get(i3);
                    Intermediates intermediates7 =
                            (Intermediates)
                                    uidStateEstimate2.combinedDeviceStateEstimate.intermediates;
                    Iterator it4 = ((ArrayList) uidStateEstimate2.proportionalEstimates).iterator();
                    while (it4.hasNext()) {
                        PowerStatsProcessor.UidStateProportionalEstimate
                                uidStateProportionalEstimate =
                                        (PowerStatsProcessor.UidStateProportionalEstimate)
                                                it4.next();
                        if (powerComponentAggregatedPowerStats.getUidStats(
                                intValue2,
                                uidStateProportionalEstimate.stateValues,
                                this.mTmpUidStatsArray)) {
                            long j4 = intermediates7.rxPackets;
                            double d3 =
                                    j4 != 0
                                            ? ((intermediates7.rxPower
                                                                    * this.mTmpUidStatsArray[
                                                                            this.mStatsLayout
                                                                                    .mUidRxPacketsPosition])
                                                            / j4)
                                                    + 0.0d
                                            : 0.0d;
                            long j5 = intermediates7.txPackets;
                            Iterator it5 = it4;
                            if (j5 != 0) {
                                d3 +=
                                        (intermediates7.txPower
                                                        * this.mTmpUidStatsArray[
                                                                this.mStatsLayout
                                                                        .mUidTxPacketsPosition])
                                                / j5;
                            }
                            this.mStatsLayout.setUidPowerEstimate(this.mTmpUidStatsArray, d3);
                            powerComponentAggregatedPowerStats.setUidStats(
                                    intValue2,
                                    uidStateProportionalEstimate.stateValues,
                                    this.mTmpUidStatsArray);
                            it4 = it5;
                        }
                    }
                }
            }
        }
        this.mPlan.resetIntermediates();
    }
}
