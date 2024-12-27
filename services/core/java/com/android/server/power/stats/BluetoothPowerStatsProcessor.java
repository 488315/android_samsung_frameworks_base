package com.android.server.power.stats;

import com.android.internal.os.PowerProfile;
import com.android.internal.os.PowerStats;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class BluetoothPowerStatsProcessor extends PowerStatsProcessor {
    public final UsageBasedPowerEstimator mIdlePowerEstimator;
    public PowerStats.Descriptor mLastUsedDescriptor;
    public PowerStatsProcessor.PowerEstimationPlan mPlan;
    public final UsageBasedPowerEstimator mRxPowerEstimator;
    public BluetoothPowerStatsLayout mStatsLayout;
    public long[] mTmpDeviceStatsArray;
    public long[] mTmpUidStatsArray;
    public final UsageBasedPowerEstimator mTxPowerEstimator;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Intermediates {
        public long consumedEnergy;
        public double idlePower;
        public long rxBytes;
        public double rxPower;
        public long rxTime;
        public long scanTime;
        public long txBytes;
        public double txPower;
        public long txTime;
    }

    public BluetoothPowerStatsProcessor(PowerProfile powerProfile) {
        this.mRxPowerEstimator =
                new UsageBasedPowerEstimator(
                        powerProfile.getAveragePower("bluetooth.controller.rx"));
        this.mTxPowerEstimator =
                new UsageBasedPowerEstimator(
                        powerProfile.getAveragePower("bluetooth.controller.tx"));
        this.mIdlePowerEstimator =
                new UsageBasedPowerEstimator(
                        powerProfile.getAveragePower("bluetooth.controller.idle"));
    }

    @Override // com.android.server.power.stats.PowerStatsProcessor
    public final void finish(
            PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats, long j) {
        Iterator it;
        double d;
        double d2;
        double d3;
        double d4;
        Iterator it2;
        PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats2 =
                powerComponentAggregatedPowerStats;
        PowerStats.Descriptor descriptor =
                powerComponentAggregatedPowerStats2.mPowerStatsDescriptor;
        if (descriptor == null) {
            return;
        }
        if (!descriptor.equals(this.mLastUsedDescriptor)) {
            this.mLastUsedDescriptor = descriptor;
            this.mStatsLayout = new BluetoothPowerStatsLayout(descriptor);
            this.mTmpDeviceStatsArray = new long[descriptor.statsArrayLength];
            this.mTmpUidStatsArray = new long[descriptor.uidStatsArrayLength];
        }
        if (this.mPlan == null) {
            this.mPlan =
                    new PowerStatsProcessor.PowerEstimationPlan(
                            powerComponentAggregatedPowerStats2.mConfig);
        }
        boolean z = true;
        for (int size = ((ArrayList) this.mPlan.deviceStateEstimations).size() - 1;
                size >= 0;
                size--) {
            PowerStatsProcessor.DeviceStateEstimation deviceStateEstimation =
                    (PowerStatsProcessor.DeviceStateEstimation)
                            ((ArrayList) this.mPlan.deviceStateEstimations).get(size);
            Intermediates intermediates = new Intermediates();
            deviceStateEstimation.intermediates = intermediates;
            long[] jArr = this.mTmpDeviceStatsArray;
            int[] iArr = deviceStateEstimation.stateValues;
            if (powerComponentAggregatedPowerStats2.getDeviceStats(iArr, jArr)) {
                for (int i = this.mStatsLayout.mDeviceEnergyConsumerCount - 1; i >= 0; i--) {
                    intermediates.consumedEnergy =
                            this.mStatsLayout.getConsumedEnergy(i, this.mTmpDeviceStatsArray)
                                    + intermediates.consumedEnergy;
                }
                BluetoothPowerStatsLayout bluetoothPowerStatsLayout = this.mStatsLayout;
                long[] jArr2 = this.mTmpDeviceStatsArray;
                long j2 = jArr2[bluetoothPowerStatsLayout.mDeviceRxTimePosition];
                intermediates.rxTime = j2;
                long j3 = jArr2[bluetoothPowerStatsLayout.mDeviceTxTimePosition];
                intermediates.txTime = j3;
                intermediates.scanTime = jArr2[bluetoothPowerStatsLayout.mDeviceScanTimePosition];
                long j4 = jArr2[bluetoothPowerStatsLayout.mDeviceIdleTimePosition];
                double d5 = this.mRxPowerEstimator.mAveragePowerMahPerMs * j2;
                intermediates.rxPower = d5;
                double d6 = this.mTxPowerEstimator.mAveragePowerMahPerMs * j3;
                intermediates.txPower = d6;
                double d7 = this.mIdlePowerEstimator.mAveragePowerMahPerMs * j4;
                intermediates.idlePower = d7;
                bluetoothPowerStatsLayout.setDevicePowerEstimate(jArr2, d5 + d6 + d7);
                powerComponentAggregatedPowerStats2.setDeviceStats(iArr, this.mTmpDeviceStatsArray);
            }
        }
        double d8 = 0.0d;
        if (this.mStatsLayout.mDeviceEnergyConsumerCount != 0) {
            double d9 = 0.0d;
            long j5 = 0;
            for (int size2 = ((ArrayList) this.mPlan.deviceStateEstimations).size() - 1;
                    size2 >= 0;
                    size2--) {
                Intermediates intermediates2 =
                        (Intermediates)
                                ((PowerStatsProcessor.DeviceStateEstimation)
                                                ((ArrayList) this.mPlan.deviceStateEstimations)
                                                        .get(size2))
                                        .intermediates;
                d9 += intermediates2.rxPower + intermediates2.txPower + intermediates2.idlePower;
                j5 += intermediates2.consumedEnergy;
            }
            double d10 = d9 == 0.0d ? 1.0d : (j5 * 2.777777777777778E-7d) / d9;
            if (d10 != 1.0d) {
                for (int size3 = ((ArrayList) this.mPlan.deviceStateEstimations).size() - 1;
                        size3 >= 0;
                        size3--) {
                    PowerStatsProcessor.DeviceStateEstimation deviceStateEstimation2 =
                            (PowerStatsProcessor.DeviceStateEstimation)
                                    ((ArrayList) this.mPlan.deviceStateEstimations).get(size3);
                    int[] iArr2 = deviceStateEstimation2.stateValues;
                    Intermediates intermediates3 =
                            (Intermediates) deviceStateEstimation2.intermediates;
                    double d11 = intermediates3.rxPower * d10;
                    intermediates3.rxPower = d11;
                    double d12 = intermediates3.txPower * d10;
                    intermediates3.txPower = d12;
                    double d13 = intermediates3.idlePower * d10;
                    intermediates3.idlePower = d13;
                    double d14 = d11 + d12 + d13;
                    if (powerComponentAggregatedPowerStats2.getDeviceStats(
                            iArr2, this.mTmpDeviceStatsArray)) {
                        this.mStatsLayout.setDevicePowerEstimate(this.mTmpDeviceStatsArray, d14);
                        powerComponentAggregatedPowerStats2.setDeviceStats(
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
                intermediates4.rxTime += intermediates5.rxTime;
                intermediates4.rxBytes += intermediates5.rxBytes;
                intermediates4.rxPower += intermediates5.rxPower;
                intermediates4.txTime += intermediates5.txTime;
                intermediates4.txBytes += intermediates5.txBytes;
                intermediates4.txPower += intermediates5.txPower;
                intermediates4.idlePower += intermediates5.idlePower;
                intermediates4.scanTime += intermediates5.scanTime;
                intermediates4.consumedEnergy += intermediates5.consumedEnergy;
            }
        }
        ArrayList arrayList2 = new ArrayList();
        powerComponentAggregatedPowerStats2.collectUids(arrayList2);
        if (!arrayList2.isEmpty()) {
            Iterator it3 = arrayList2.iterator();
            while (it3.hasNext()) {
                int intValue = ((Integer) it3.next()).intValue();
                for (int i2 = 0; i2 < ((ArrayList) this.mPlan.uidStateEstimates).size(); i2++) {
                    PowerStatsProcessor.UidStateEstimate uidStateEstimate =
                            (PowerStatsProcessor.UidStateEstimate)
                                    ((ArrayList) this.mPlan.uidStateEstimates).get(i2);
                    Intermediates intermediates6 =
                            (Intermediates)
                                    uidStateEstimate.combinedDeviceStateEstimate.intermediates;
                    Iterator it4 = ((ArrayList) uidStateEstimate.proportionalEstimates).iterator();
                    while (it4.hasNext()) {
                        if (powerComponentAggregatedPowerStats2.getUidStats(
                                intValue,
                                ((PowerStatsProcessor.UidStateProportionalEstimate) it4.next())
                                        .stateValues,
                                this.mTmpUidStatsArray)) {
                            long j6 = intermediates6.rxBytes;
                            BluetoothPowerStatsLayout bluetoothPowerStatsLayout2 =
                                    this.mStatsLayout;
                            long[] jArr3 = this.mTmpUidStatsArray;
                            intermediates6.rxBytes =
                                    j6 + jArr3[bluetoothPowerStatsLayout2.mUidRxBytesPosition];
                            intermediates6.txBytes +=
                                    jArr3[bluetoothPowerStatsLayout2.mUidTxBytesPosition];
                        }
                    }
                }
            }
            Iterator it5 = arrayList2.iterator();
            while (it5.hasNext()) {
                int intValue2 = ((Integer) it5.next()).intValue();
                int i3 = 0;
                while (i3 < ((ArrayList) this.mPlan.uidStateEstimates).size()) {
                    PowerStatsProcessor.UidStateEstimate uidStateEstimate2 =
                            (PowerStatsProcessor.UidStateEstimate)
                                    ((ArrayList) this.mPlan.uidStateEstimates).get(i3);
                    Intermediates intermediates7 =
                            (Intermediates)
                                    uidStateEstimate2.combinedDeviceStateEstimate.intermediates;
                    long j7 = intermediates7.scanTime;
                    boolean z2 = j7 > intermediates7.rxTime ? z : false;
                    boolean z3 = j7 > intermediates7.txTime ? z : false;
                    Iterator it6 = ((ArrayList) uidStateEstimate2.proportionalEstimates).iterator();
                    while (it6.hasNext()) {
                        PowerStatsProcessor.UidStateProportionalEstimate
                                uidStateProportionalEstimate =
                                        (PowerStatsProcessor.UidStateProportionalEstimate)
                                                it6.next();
                        if (powerComponentAggregatedPowerStats2.getUidStats(
                                intValue2,
                                uidStateProportionalEstimate.stateValues,
                                this.mTmpUidStatsArray)) {
                            if (z2) {
                                long j8 = intermediates7.scanTime;
                                it = it6;
                                if (j8 != 0) {
                                    d2 =
                                            intermediates7.rxPower
                                                    * this.mTmpUidStatsArray[
                                                            this.mStatsLayout.mUidScanTimePosition];
                                    d3 = j8;
                                    d4 = (d2 / d3) + 0.0d;
                                } else {
                                    d = d8;
                                    d4 = d;
                                }
                            } else {
                                it = it6;
                                long j9 = intermediates7.rxBytes;
                                if (j9 != 0) {
                                    d2 =
                                            intermediates7.rxPower
                                                    * this.mTmpUidStatsArray[
                                                            this.mStatsLayout.mUidRxBytesPosition];
                                    d3 = j9;
                                    d4 = (d2 / d3) + 0.0d;
                                } else {
                                    d = 0.0d;
                                    d4 = d;
                                }
                            }
                            if (z3) {
                                long j10 = intermediates7.scanTime;
                                if (j10 != 0) {
                                    it2 = it5;
                                    d4 +=
                                            (intermediates7.txPower
                                                            * this.mTmpUidStatsArray[
                                                                    this.mStatsLayout
                                                                            .mUidScanTimePosition])
                                                    / j10;
                                } else {
                                    it2 = it5;
                                }
                            } else {
                                it2 = it5;
                                long j11 = intermediates7.txBytes;
                                if (j11 != 0) {
                                    d4 +=
                                            (intermediates7.txPower
                                                            * this.mTmpUidStatsArray[
                                                                    this.mStatsLayout
                                                                            .mUidTxBytesPosition])
                                                    / j11;
                                }
                            }
                            this.mStatsLayout.setUidPowerEstimate(this.mTmpUidStatsArray, d4);
                            powerComponentAggregatedPowerStats.setUidStats(
                                    intValue2,
                                    uidStateProportionalEstimate.stateValues,
                                    this.mTmpUidStatsArray);
                            powerComponentAggregatedPowerStats2 =
                                    powerComponentAggregatedPowerStats;
                            it6 = it;
                            it5 = it2;
                            d8 = 0.0d;
                        }
                    }
                    i3++;
                    z = true;
                    d8 = 0.0d;
                }
            }
        }
        this.mPlan.resetIntermediates();
    }
}
