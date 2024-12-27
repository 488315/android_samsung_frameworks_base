package com.android.server.power.stats;

import com.android.internal.os.PowerProfile;
import com.android.internal.os.PowerStats;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class WifiPowerStatsProcessor extends PowerStatsProcessor {
    public final UsageBasedPowerEstimator mActivePowerEstimator;
    public final UsageBasedPowerEstimator mBatchedScanPowerEstimator;
    public boolean mHasWifiPowerController;
    public final UsageBasedPowerEstimator mIdlePowerEstimator;
    public PowerStats.Descriptor mLastUsedDescriptor;
    public PowerStatsProcessor.PowerEstimationPlan mPlan;
    public final UsageBasedPowerEstimator mRxPowerEstimator;
    public final UsageBasedPowerEstimator mScanPowerEstimator;
    public WifiPowerStatsLayout mStatsLayout;
    public long[] mTmpDeviceStatsArray;
    public long[] mTmpUidStatsArray;
    public final UsageBasedPowerEstimator mTxPowerEstimator;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Intermediates {
        public double activePower;
        public long basicScanDuration;
        public double basicScanPower;
        public long batchedScanDuration;
        public double batchedScanPower;
        public long consumedEnergy;
        public double idlePower;
        public long rxPackets;
        public double rxPower;
        public double scanPower;
        public long txPackets;
        public double txPower;
    }

    public WifiPowerStatsProcessor(PowerProfile powerProfile) {
        this.mRxPowerEstimator =
                new UsageBasedPowerEstimator(powerProfile.getAveragePower("wifi.controller.rx"));
        this.mTxPowerEstimator =
                new UsageBasedPowerEstimator(powerProfile.getAveragePower("wifi.controller.tx"));
        this.mIdlePowerEstimator =
                new UsageBasedPowerEstimator(powerProfile.getAveragePower("wifi.controller.idle"));
        this.mActivePowerEstimator =
                new UsageBasedPowerEstimator(powerProfile.getAveragePower("wifi.active"));
        this.mScanPowerEstimator =
                new UsageBasedPowerEstimator(powerProfile.getAveragePower("wifi.scan"));
        this.mBatchedScanPowerEstimator =
                new UsageBasedPowerEstimator(powerProfile.getAveragePower("wifi.batchedscan"));
    }

    @Override // com.android.server.power.stats.PowerStatsProcessor
    public final void finish(
            PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats, long j) {
        Iterator it;
        double d;
        long j2;
        long j3;
        double d2;
        double d3;
        double d4;
        double d5;
        int i;
        PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats2;
        PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats3 =
                powerComponentAggregatedPowerStats;
        PowerStats.Descriptor descriptor =
                powerComponentAggregatedPowerStats3.mPowerStatsDescriptor;
        if (descriptor == null) {
            return;
        }
        if (!descriptor.equals(this.mLastUsedDescriptor)) {
            this.mLastUsedDescriptor = descriptor;
            WifiPowerStatsLayout wifiPowerStatsLayout = new WifiPowerStatsLayout(descriptor);
            this.mStatsLayout = wifiPowerStatsLayout;
            this.mTmpDeviceStatsArray = new long[descriptor.statsArrayLength];
            this.mTmpUidStatsArray = new long[descriptor.uidStatsArrayLength];
            this.mHasWifiPowerController = wifiPowerStatsLayout.mPowerReportingSupported;
        }
        if (this.mPlan == null) {
            this.mPlan =
                    new PowerStatsProcessor.PowerEstimationPlan(
                            powerComponentAggregatedPowerStats3.mConfig);
        }
        int size = ((ArrayList) this.mPlan.deviceStateEstimations).size() - 1;
        while (size >= 0) {
            PowerStatsProcessor.DeviceStateEstimation deviceStateEstimation =
                    (PowerStatsProcessor.DeviceStateEstimation)
                            ((ArrayList) this.mPlan.deviceStateEstimations).get(size);
            Intermediates intermediates = new Intermediates();
            deviceStateEstimation.intermediates = intermediates;
            long[] jArr = this.mTmpDeviceStatsArray;
            int[] iArr = deviceStateEstimation.stateValues;
            if (powerComponentAggregatedPowerStats3.getDeviceStats(iArr, jArr)) {
                for (int i2 = this.mStatsLayout.mDeviceEnergyConsumerCount - 1; i2 >= 0; i2--) {
                    intermediates.consumedEnergy =
                            this.mStatsLayout.getConsumedEnergy(i2, this.mTmpDeviceStatsArray)
                                    + intermediates.consumedEnergy;
                }
                WifiPowerStatsLayout wifiPowerStatsLayout2 = this.mStatsLayout;
                long[] jArr2 = this.mTmpDeviceStatsArray;
                long j4 = jArr2[wifiPowerStatsLayout2.mDeviceBasicScanTimePosition];
                intermediates.basicScanDuration = j4;
                long j5 = jArr2[wifiPowerStatsLayout2.mDeviceBatchedScanTimePosition];
                intermediates.batchedScanDuration = j5;
                boolean z = this.mHasWifiPowerController;
                UsageBasedPowerEstimator usageBasedPowerEstimator = this.mScanPowerEstimator;
                if (z) {
                    double d6 =
                            this.mRxPowerEstimator.mAveragePowerMahPerMs
                                    * jArr2[wifiPowerStatsLayout2.mDeviceRxTimePosition];
                    intermediates.rxPower = d6;
                    double d7 =
                            this.mTxPowerEstimator.mAveragePowerMahPerMs
                                    * jArr2[wifiPowerStatsLayout2.mDeviceTxTimePosition];
                    intermediates.txPower = d7;
                    double d8 =
                            usageBasedPowerEstimator.mAveragePowerMahPerMs
                                    * jArr2[wifiPowerStatsLayout2.mDeviceScanTimePosition];
                    intermediates.scanPower = d8;
                    i = size;
                    double d9 =
                            this.mIdlePowerEstimator.mAveragePowerMahPerMs
                                    * jArr2[wifiPowerStatsLayout2.mDeviceIdleTimePosition];
                    intermediates.idlePower = d9;
                    wifiPowerStatsLayout2.setDevicePowerEstimate(jArr2, d6 + d7 + d8 + d9);
                } else {
                    i = size;
                    double d10 =
                            this.mActivePowerEstimator.mAveragePowerMahPerMs
                                    * jArr2[wifiPowerStatsLayout2.mDeviceActiveTimePosition];
                    intermediates.activePower = d10;
                    double d11 = usageBasedPowerEstimator.mAveragePowerMahPerMs * j4;
                    intermediates.basicScanPower = d11;
                    double d12 = this.mBatchedScanPowerEstimator.mAveragePowerMahPerMs * j5;
                    intermediates.batchedScanPower = d12;
                    wifiPowerStatsLayout2.setDevicePowerEstimate(jArr2, d10 + d11 + d12);
                }
                powerComponentAggregatedPowerStats2 = powerComponentAggregatedPowerStats;
                powerComponentAggregatedPowerStats2.setDeviceStats(iArr, this.mTmpDeviceStatsArray);
            } else {
                i = size;
                powerComponentAggregatedPowerStats2 = powerComponentAggregatedPowerStats3;
            }
            PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats4 =
                    powerComponentAggregatedPowerStats2;
            size = i - 1;
            powerComponentAggregatedPowerStats3 = powerComponentAggregatedPowerStats4;
        }
        PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats5 =
                powerComponentAggregatedPowerStats3;
        if (this.mStatsLayout.mDeviceEnergyConsumerCount != 0) {
            double d13 = 0.0d;
            long j6 = 0;
            for (int size2 = ((ArrayList) this.mPlan.deviceStateEstimations).size() - 1;
                    size2 >= 0;
                    size2--) {
                Intermediates intermediates2 =
                        (Intermediates)
                                ((PowerStatsProcessor.DeviceStateEstimation)
                                                ((ArrayList) this.mPlan.deviceStateEstimations)
                                                        .get(size2))
                                        .intermediates;
                if (this.mHasWifiPowerController) {
                    d4 = intermediates2.rxPower + intermediates2.txPower + intermediates2.scanPower;
                    d5 = intermediates2.idlePower;
                } else {
                    d4 = intermediates2.activePower + intermediates2.basicScanPower;
                    d5 = intermediates2.batchedScanPower;
                }
                d13 = d4 + d5 + d13;
                j6 += intermediates2.consumedEnergy;
            }
            double d14 = d13 == 0.0d ? 1.0d : (j6 * 2.777777777777778E-7d) / d13;
            if (d14 != 1.0d) {
                for (int size3 = ((ArrayList) this.mPlan.deviceStateEstimations).size() - 1;
                        size3 >= 0;
                        size3--) {
                    PowerStatsProcessor.DeviceStateEstimation deviceStateEstimation2 =
                            (PowerStatsProcessor.DeviceStateEstimation)
                                    ((ArrayList) this.mPlan.deviceStateEstimations).get(size3);
                    int[] iArr2 = deviceStateEstimation2.stateValues;
                    Intermediates intermediates3 =
                            (Intermediates) deviceStateEstimation2.intermediates;
                    if (this.mHasWifiPowerController) {
                        double d15 = intermediates3.rxPower * d14;
                        intermediates3.rxPower = d15;
                        double d16 = intermediates3.txPower * d14;
                        intermediates3.txPower = d16;
                        double d17 = intermediates3.scanPower * d14;
                        intermediates3.scanPower = d17;
                        double d18 = intermediates3.idlePower * d14;
                        intermediates3.idlePower = d18;
                        d3 = d15 + d16 + d17 + d18;
                    } else {
                        double d19 = intermediates3.activePower * d14;
                        intermediates3.activePower = d19;
                        double d20 = intermediates3.basicScanPower * d14;
                        intermediates3.basicScanPower = d20;
                        double d21 = intermediates3.batchedScanPower * d14;
                        intermediates3.batchedScanPower = d21;
                        d3 = d21 + d19 + d20;
                    }
                    if (powerComponentAggregatedPowerStats5.getDeviceStats(
                            iArr2, this.mTmpDeviceStatsArray)) {
                        this.mStatsLayout.setDevicePowerEstimate(this.mTmpDeviceStatsArray, d3);
                        powerComponentAggregatedPowerStats5.setDeviceStats(
                                iArr2, this.mTmpDeviceStatsArray);
                    }
                }
            }
        }
        for (int size4 = ((ArrayList) this.mPlan.combinedDeviceStateEstimations).size() - 1;
                size4 >= 0;
                size4--) {
            PowerStatsProcessor.CombinedDeviceStateEstimate combinedDeviceStateEstimate =
                    (PowerStatsProcessor.CombinedDeviceStateEstimate)
                            ((ArrayList) this.mPlan.combinedDeviceStateEstimations).get(size4);
            Intermediates intermediates4 = new Intermediates();
            combinedDeviceStateEstimate.intermediates = intermediates4;
            ArrayList arrayList = (ArrayList) combinedDeviceStateEstimate.deviceStateEstimations;
            for (int size5 = arrayList.size() - 1; size5 >= 0; size5--) {
                Intermediates intermediates5 =
                        (Intermediates)
                                ((PowerStatsProcessor.DeviceStateEstimation) arrayList.get(size5))
                                        .intermediates;
                if (this.mHasWifiPowerController) {
                    intermediates4.rxPower += intermediates5.rxPower;
                    intermediates4.txPower += intermediates5.txPower;
                    intermediates4.scanPower += intermediates5.scanPower;
                    intermediates4.idlePower += intermediates5.idlePower;
                } else {
                    intermediates4.activePower += intermediates5.activePower;
                    intermediates4.basicScanPower += intermediates5.basicScanPower;
                    intermediates4.batchedScanPower += intermediates5.batchedScanPower;
                }
                intermediates4.basicScanDuration += intermediates5.basicScanDuration;
                intermediates4.batchedScanDuration += intermediates5.batchedScanDuration;
                intermediates4.consumedEnergy += intermediates5.consumedEnergy;
            }
        }
        ArrayList arrayList2 = new ArrayList();
        powerComponentAggregatedPowerStats5.collectUids(arrayList2);
        if (!arrayList2.isEmpty()) {
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                int intValue = ((Integer) it2.next()).intValue();
                for (int i3 = 0; i3 < ((ArrayList) this.mPlan.uidStateEstimates).size(); i3++) {
                    PowerStatsProcessor.UidStateEstimate uidStateEstimate =
                            (PowerStatsProcessor.UidStateEstimate)
                                    ((ArrayList) this.mPlan.uidStateEstimates).get(i3);
                    Intermediates intermediates6 =
                            (Intermediates)
                                    uidStateEstimate.combinedDeviceStateEstimate.intermediates;
                    Iterator it3 = ((ArrayList) uidStateEstimate.proportionalEstimates).iterator();
                    while (it3.hasNext()) {
                        if (powerComponentAggregatedPowerStats5.getUidStats(
                                intValue,
                                ((PowerStatsProcessor.UidStateProportionalEstimate) it3.next())
                                        .stateValues,
                                this.mTmpUidStatsArray)) {
                            long j7 = intermediates6.rxPackets;
                            WifiPowerStatsLayout wifiPowerStatsLayout3 = this.mStatsLayout;
                            long[] jArr3 = this.mTmpUidStatsArray;
                            intermediates6.rxPackets =
                                    j7 + jArr3[wifiPowerStatsLayout3.mUidRxPacketsPosition];
                            intermediates6.txPackets +=
                                    jArr3[wifiPowerStatsLayout3.mUidTxPacketsPosition];
                        }
                    }
                }
            }
            Iterator it4 = arrayList2.iterator();
            while (it4.hasNext()) {
                int intValue2 = ((Integer) it4.next()).intValue();
                for (int i4 = 0; i4 < ((ArrayList) this.mPlan.uidStateEstimates).size(); i4++) {
                    PowerStatsProcessor.UidStateEstimate uidStateEstimate2 =
                            (PowerStatsProcessor.UidStateEstimate)
                                    ((ArrayList) this.mPlan.uidStateEstimates).get(i4);
                    Intermediates intermediates7 =
                            (Intermediates)
                                    uidStateEstimate2.combinedDeviceStateEstimate.intermediates;
                    Iterator it5 = ((ArrayList) uidStateEstimate2.proportionalEstimates).iterator();
                    while (it5.hasNext()) {
                        PowerStatsProcessor.UidStateProportionalEstimate
                                uidStateProportionalEstimate =
                                        (PowerStatsProcessor.UidStateProportionalEstimate)
                                                it5.next();
                        if (powerComponentAggregatedPowerStats5.getUidStats(
                                intValue2,
                                uidStateProportionalEstimate.stateValues,
                                this.mTmpUidStatsArray)) {
                            if (this.mHasWifiPowerController) {
                                long j8 = intermediates7.rxPackets;
                                d =
                                        j8 != 0
                                                ? ((intermediates7.rxPower
                                                                        * this.mTmpUidStatsArray[
                                                                                this.mStatsLayout
                                                                                        .mUidRxPacketsPosition])
                                                                / j8)
                                                        + 0.0d
                                                : 0.0d;
                                long j9 = intermediates7.txPackets;
                                if (j9 != 0) {
                                    it = it5;
                                    d +=
                                            (intermediates7.txPower
                                                            * this.mTmpUidStatsArray[
                                                                    this.mStatsLayout
                                                                            .mUidTxPacketsPosition])
                                                    / j9;
                                } else {
                                    it = it5;
                                }
                                j2 =
                                        intermediates7.basicScanDuration
                                                + intermediates7.batchedScanDuration;
                                if (j2 != 0) {
                                    WifiPowerStatsLayout wifiPowerStatsLayout4 = this.mStatsLayout;
                                    long[] jArr4 = this.mTmpUidStatsArray;
                                    j3 =
                                            jArr4[wifiPowerStatsLayout4.mUidScanTimePosition]
                                                    + jArr4[
                                                            wifiPowerStatsLayout4
                                                                    .mUidBatchScanTimePosition];
                                    d2 = intermediates7.scanPower;
                                    d += (d2 * j3) / j2;
                                }
                                this.mStatsLayout.setUidPowerEstimate(this.mTmpUidStatsArray, d);
                                powerComponentAggregatedPowerStats5.setUidStats(
                                        intValue2,
                                        uidStateProportionalEstimate.stateValues,
                                        this.mTmpUidStatsArray);
                                it5 = it;
                            } else {
                                it = it5;
                                long j10 = intermediates7.rxPackets + intermediates7.txPackets;
                                if (j10 != 0) {
                                    WifiPowerStatsLayout wifiPowerStatsLayout5 = this.mStatsLayout;
                                    long[] jArr5 = this.mTmpUidStatsArray;
                                    d =
                                            ((intermediates7.activePower
                                                                    * (jArr5[
                                                                                    wifiPowerStatsLayout5
                                                                                            .mUidRxPacketsPosition]
                                                                            + jArr5[
                                                                                    wifiPowerStatsLayout5
                                                                                            .mUidTxPacketsPosition]))
                                                            / j10)
                                                    + 0.0d;
                                } else {
                                    d = 0.0d;
                                }
                                long j11 = intermediates7.basicScanDuration;
                                if (j11 != 0) {
                                    d =
                                            ((intermediates7.basicScanPower
                                                                    * this.mTmpUidStatsArray[
                                                                            this.mStatsLayout
                                                                                    .mUidScanTimePosition])
                                                            / j11)
                                                    + d;
                                }
                                j2 = intermediates7.batchedScanDuration;
                                if (j2 != 0) {
                                    j3 =
                                            this.mTmpUidStatsArray[
                                                    this.mStatsLayout.mUidBatchScanTimePosition];
                                    d2 = intermediates7.batchedScanPower;
                                    d += (d2 * j3) / j2;
                                }
                                this.mStatsLayout.setUidPowerEstimate(this.mTmpUidStatsArray, d);
                                powerComponentAggregatedPowerStats5.setUidStats(
                                        intValue2,
                                        uidStateProportionalEstimate.stateValues,
                                        this.mTmpUidStatsArray);
                                it5 = it;
                            }
                        }
                    }
                }
            }
        }
        this.mPlan.resetIntermediates();
    }
}
