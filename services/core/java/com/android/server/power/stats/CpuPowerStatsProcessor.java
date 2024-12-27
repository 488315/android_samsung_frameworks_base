package com.android.server.power.stats;

import android.util.ArraySet;
import android.util.Log;
import com.android.internal.os.CpuScalingPolicies;
import com.android.internal.os.PowerProfile;
import com.android.internal.os.PowerStats;
import com.android.server.power.stats.PowerStatsProcessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class CpuPowerStatsProcessor extends PowerStatsProcessor {
    public static final double HOUR_IN_MILLIS = TimeUnit.HOURS.toMillis(1);
    public int[][] mCombinedEnergyConsumerToPowerBracketMap;
    public final int mCpuClusterCount;
    public final CpuScalingPolicies mCpuScalingPolicies;
    public final int mCpuScalingStepCount;
    public int[] mEnergyConsumerToCombinedEnergyConsumerMap;
    public PowerStats.Descriptor mLastUsedDescriptor;
    public PowerStatsProcessor.PowerEstimationPlan mPlan;
    public final double mPowerMultiplierForCpuActive;
    public final double[] mPowerMultipliersByCluster;
    public final double[] mPowerMultipliersByScalingStep;
    public final int[] mScalingStepToCluster;
    public CpuPowerStatsLayout mStatsLayout;
    public long[] mTmpDeviceStatsArray;
    public long[] mTmpUidStatsArray;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DeviceStatsIntermediates {
        public double[] powerByBracket;
        public long[] timeByBracket;
    }

    public CpuPowerStatsProcessor(CpuScalingPolicies cpuScalingPolicies, PowerProfile powerProfile) {
        this.mCpuScalingPolicies = cpuScalingPolicies;
        int scalingStepCount = cpuScalingPolicies.getScalingStepCount();
        this.mCpuScalingStepCount = scalingStepCount;
        this.mScalingStepToCluster = new int[scalingStepCount];
        this.mPowerMultipliersByScalingStep = new double[scalingStepCount];
        int[] policies = cpuScalingPolicies.getPolicies();
        int length = policies.length;
        this.mCpuClusterCount = length;
        this.mPowerMultipliersByCluster = new double[length];
        int i = 0;
        int i2 = 0;
        while (true) {
            int i3 = this.mCpuClusterCount;
            double d = HOUR_IN_MILLIS;
            if (i >= i3) {
                this.mPowerMultiplierForCpuActive = powerProfile.getAveragePower("cpu.active") / d;
                return;
            }
            int i4 = policies[i];
            this.mPowerMultipliersByCluster[i] = powerProfile.getAveragePowerForCpuScalingPolicy(i4) / d;
            int[] frequencies = cpuScalingPolicies.getFrequencies(i4);
            for (int i5 = 0; i5 < frequencies.length; i5++) {
                this.mScalingStepToCluster[i2] = i;
                this.mPowerMultipliersByScalingStep[i2] = powerProfile.getAveragePowerForCpuScalingStep(i4, i5) / d;
                i2++;
            }
            i++;
        }
    }

    @Override // com.android.server.power.stats.PowerStatsProcessor
    public final void finish(PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats, long j) {
        int[] iArr;
        CpuPowerStatsLayout cpuPowerStatsLayout;
        int i;
        PowerStatsProcessor.UidStateEstimate uidStateEstimate;
        int i2;
        PowerStatsProcessor.UidStateEstimate uidStateEstimate2;
        int i3;
        double[] dArr;
        long[] jArr;
        int i4;
        ArrayList arrayList;
        int i5;
        ArrayList arrayList2;
        CpuPowerStatsProcessor cpuPowerStatsProcessor = this;
        PowerStats.Descriptor descriptor = powerComponentAggregatedPowerStats.mPowerStatsDescriptor;
        if (descriptor == null) {
            return;
        }
        if (!descriptor.equals(cpuPowerStatsProcessor.mLastUsedDescriptor)) {
            cpuPowerStatsProcessor.mLastUsedDescriptor = descriptor;
            CpuPowerStatsLayout cpuPowerStatsLayout2 = new CpuPowerStatsLayout();
            cpuPowerStatsProcessor.mStatsLayout = cpuPowerStatsLayout2;
            cpuPowerStatsLayout2.fromExtras(descriptor.extras);
            cpuPowerStatsProcessor.mTmpDeviceStatsArray = new long[descriptor.statsArrayLength];
            cpuPowerStatsProcessor.mTmpUidStatsArray = new long[descriptor.uidStatsArrayLength];
        }
        if (cpuPowerStatsProcessor.mPlan == null) {
            cpuPowerStatsProcessor.mPlan = new PowerStatsProcessor.PowerEstimationPlan(powerComponentAggregatedPowerStats.mConfig);
            CpuPowerStatsLayout cpuPowerStatsLayout3 = cpuPowerStatsProcessor.mStatsLayout;
            int i6 = cpuPowerStatsLayout3.mDeviceEnergyConsumerCount;
            if (i6 != 0) {
                int i7 = cpuPowerStatsLayout3.mUidPowerBracketCount;
                cpuPowerStatsProcessor.mEnergyConsumerToCombinedEnergyConsumerMap = new int[i6];
                cpuPowerStatsProcessor.mCombinedEnergyConsumerToPowerBracketMap = new int[i6][];
                int[] policies = cpuPowerStatsProcessor.mCpuScalingPolicies.getPolicies();
                if (i6 == policies.length) {
                    int[] iArr2 = cpuPowerStatsProcessor.mStatsLayout.mScalingStepToPowerBracketMap;
                    int length = policies.length;
                    ArraySet[] arraySetArr = new ArraySet[length];
                    int i8 = 0;
                    for (int i9 = 0; i9 < policies.length; i9++) {
                        int[] frequencies = cpuPowerStatsProcessor.mCpuScalingPolicies.getFrequencies(policies[i9]);
                        arraySetArr[i9] = new ArraySet(frequencies.length);
                        int i10 = 0;
                        while (i10 < frequencies.length) {
                            arraySetArr[i9].add(Integer.valueOf(iArr2[i8]));
                            i10++;
                            i8++;
                        }
                    }
                    ArraySet[] arraySetArr2 = new ArraySet[policies.length];
                    int i11 = 0;
                    for (int i12 = 0; i12 < length; i12++) {
                        int i13 = 0;
                        while (true) {
                            if (i13 >= i11) {
                                i13 = -1;
                                break;
                            }
                            ArraySet arraySet = arraySetArr2[i13];
                            ArraySet arraySet2 = arraySetArr[i12];
                            for (int i14 = 0; i14 < arraySet2.size(); i14++) {
                                if (arraySet.contains(arraySet2.valueAt(i14))) {
                                    break;
                                }
                            }
                            i13++;
                        }
                        if (i13 != -1) {
                            cpuPowerStatsProcessor.mEnergyConsumerToCombinedEnergyConsumerMap[i12] = i13;
                            arraySetArr2[i13].addAll(arraySetArr[i12]);
                        } else {
                            cpuPowerStatsProcessor.mEnergyConsumerToCombinedEnergyConsumerMap[i12] = i11;
                            arraySetArr2[i11] = arraySetArr[i12];
                            i11++;
                        }
                    }
                    for (int i15 = r2 - 1; i15 >= 0; i15--) {
                        cpuPowerStatsProcessor.mCombinedEnergyConsumerToPowerBracketMap[i15] = new int[arraySetArr2[i15].size()];
                        for (int size = arraySetArr2[i15].size() - 1; size >= 0; size--) {
                            cpuPowerStatsProcessor.mCombinedEnergyConsumerToPowerBracketMap[i15][size] = ((Integer) arraySetArr2[i15].valueAt(size)).intValue();
                        }
                    }
                } else {
                    int[] iArr3 = new int[i7];
                    for (int i16 = 0; i16 < i7; i16++) {
                        iArr3[i16] = i16;
                    }
                    cpuPowerStatsProcessor.mCombinedEnergyConsumerToPowerBracketMap[0] = iArr3;
                }
            }
        }
        CpuPowerStatsLayout cpuPowerStatsLayout4 = cpuPowerStatsProcessor.mStatsLayout;
        int i17 = cpuPowerStatsLayout4.mDeviceCpuTimeByScalingStepCount;
        int i18 = cpuPowerStatsProcessor.mCpuScalingStepCount;
        if (i17 != i18) {
            Log.e("CpuPowerStatsProcessor", "Mismatched CPU scaling step count in PowerStats: " + i17 + ", expected: " + i18);
            return;
        }
        int i19 = cpuPowerStatsLayout4.mDeviceCpuTimeByClusterCount;
        int i20 = cpuPowerStatsProcessor.mCpuClusterCount;
        if (i19 != i20) {
            Log.e("CpuPowerStatsProcessor", "Mismatched CPU cluster count in PowerStats: " + i19 + ", expected: " + i20);
            return;
        }
        long[] jArr2 = new long[i18];
        long[] jArr3 = new long[i20];
        long[] jArr4 = new long[i20];
        ArrayList arrayList3 = (ArrayList) cpuPowerStatsProcessor.mPlan.deviceStateEstimations;
        int size2 = arrayList3.size() - 1;
        long j2 = 0;
        long j3 = 0;
        while (true) {
            iArr = cpuPowerStatsProcessor.mScalingStepToCluster;
            if (size2 < 0) {
                break;
            }
            if (powerComponentAggregatedPowerStats.getDeviceStats(((PowerStatsProcessor.DeviceStateEstimation) arrayList3.get(size2)).stateValues, cpuPowerStatsProcessor.mTmpDeviceStatsArray)) {
                j3 += cpuPowerStatsProcessor.mTmpDeviceStatsArray[cpuPowerStatsProcessor.mStatsLayout.mDeviceDurationPosition];
                int i21 = 0;
                while (i21 < i20) {
                    jArr3[i21] = jArr3[i21] + cpuPowerStatsProcessor.mTmpDeviceStatsArray[cpuPowerStatsProcessor.mStatsLayout.mDeviceCpuTimeByClusterPosition + i21];
                    i21++;
                    arrayList3 = arrayList3;
                }
                arrayList2 = arrayList3;
                for (int i22 = 0; i22 < i18; i22++) {
                    long j4 = cpuPowerStatsProcessor.mTmpDeviceStatsArray[cpuPowerStatsProcessor.mStatsLayout.mDeviceCpuTimeByScalingStepPosition + i22];
                    j2 += j4;
                    jArr2[i22] = jArr2[i22] + j4;
                    int i23 = iArr[i22];
                    jArr4[i23] = jArr4[i23] + j4;
                }
            } else {
                arrayList2 = arrayList3;
            }
            size2--;
            arrayList3 = arrayList2;
        }
        if (j2 == 0) {
            return;
        }
        double d = cpuPowerStatsProcessor.mPowerMultiplierForCpuActive * j3;
        double[] dArr2 = new double[i20];
        int i24 = 0;
        while (i24 < i20) {
            dArr2[i24] = cpuPowerStatsProcessor.mPowerMultipliersByCluster[i24] * jArr3[i24];
            i24++;
            cpuPowerStatsProcessor = this;
        }
        double[] dArr3 = new double[i18];
        int i25 = 0;
        while (i25 < i18) {
            int i26 = iArr[i25];
            double d2 = jArr2[i25];
            double d3 = d;
            double d4 = (d * d2) / j2;
            long j5 = jArr4[i26];
            if (j5 != 0) {
                d4 = ((dArr2[i26] * d2) / j5) + d4;
            }
            dArr3[i25] = (this.mPowerMultipliersByScalingStep[i25] * d2) + d4;
            i25++;
            d = d3;
        }
        CpuPowerStatsLayout cpuPowerStatsLayout5 = this.mStatsLayout;
        int i27 = cpuPowerStatsLayout5.mDeviceCpuTimeByScalingStepCount;
        int i28 = cpuPowerStatsLayout5.mUidPowerBracketCount;
        int[] iArr4 = cpuPowerStatsLayout5.mScalingStepToPowerBracketMap;
        int i29 = cpuPowerStatsLayout5.mDeviceEnergyConsumerCount;
        ArrayList arrayList4 = (ArrayList) this.mPlan.deviceStateEstimations;
        int size3 = arrayList4.size() - 1;
        long[] jArr5 = null;
        while (size3 >= 0) {
            PowerStatsProcessor.DeviceStateEstimation deviceStateEstimation = (PowerStatsProcessor.DeviceStateEstimation) arrayList4.get(size3);
            DeviceStatsIntermediates deviceStatsIntermediates = new DeviceStatsIntermediates();
            deviceStateEstimation.intermediates = deviceStatsIntermediates;
            deviceStatsIntermediates.timeByBracket = new long[i28];
            deviceStatsIntermediates.powerByBracket = new double[i28];
            long[] jArr6 = this.mTmpDeviceStatsArray;
            int[] iArr5 = deviceStateEstimation.stateValues;
            powerComponentAggregatedPowerStats.getDeviceStats(iArr5, jArr6);
            int i30 = 0;
            while (i30 < i27) {
                long j6 = jArr2[i30];
                if (j6 == 0) {
                    jArr = jArr2;
                    i4 = i27;
                    arrayList = arrayList4;
                    i5 = size3;
                } else {
                    jArr = jArr2;
                    i4 = i27;
                    long j7 = this.mTmpDeviceStatsArray[this.mStatsLayout.mDeviceCpuTimeByScalingStepPosition + i30];
                    arrayList = arrayList4;
                    i5 = size3;
                    double d5 = (dArr3[i30] * j7) / j6;
                    int i31 = iArr4[i30];
                    long[] jArr7 = deviceStatsIntermediates.timeByBracket;
                    jArr7[i31] = jArr7[i31] + j7;
                    double[] dArr4 = deviceStatsIntermediates.powerByBracket;
                    dArr4[i31] = dArr4[i31] + d5;
                }
                i30++;
                arrayList4 = arrayList;
                jArr2 = jArr;
                i27 = i4;
                size3 = i5;
            }
            long[] jArr8 = jArr2;
            int i32 = i27;
            ArrayList arrayList5 = arrayList4;
            int i33 = size3;
            if (i29 != 0 && (i3 = this.mStatsLayout.mDeviceEnergyConsumerCount) != 0) {
                if (jArr5 == null) {
                    jArr5 = new long[i3];
                } else {
                    Arrays.fill(jArr5, 0L);
                }
                for (int i34 = 0; i34 < i3; i34++) {
                    int i35 = this.mEnergyConsumerToCombinedEnergyConsumerMap[i34];
                    jArr5[i35] = this.mStatsLayout.getConsumedEnergy(i34, this.mTmpDeviceStatsArray) + jArr5[i35];
                }
                int length2 = this.mCombinedEnergyConsumerToPowerBracketMap.length - 1;
                while (length2 >= 0) {
                    int[] iArr6 = this.mCombinedEnergyConsumerToPowerBracketMap[length2];
                    if (iArr6 == null) {
                        dArr = dArr3;
                    } else {
                        double d6 = jArr5[length2] * 2.777777777777778E-7d;
                        int length3 = iArr6.length;
                        int i36 = 0;
                        double d7 = 0.0d;
                        while (i36 < length3) {
                            d7 += deviceStatsIntermediates.powerByBracket[iArr6[i36]];
                            i36++;
                            dArr3 = dArr3;
                        }
                        dArr = dArr3;
                        if (d7 != 0.0d) {
                            for (int i37 : iArr6) {
                                double[] dArr5 = deviceStatsIntermediates.powerByBracket;
                                dArr5[i37] = (dArr5[i37] * d6) / d7;
                            }
                        }
                    }
                    length2--;
                    dArr3 = dArr;
                }
            }
            double[] dArr6 = dArr3;
            double d8 = 0.0d;
            for (int length4 = deviceStatsIntermediates.powerByBracket.length - 1; length4 >= 0; length4--) {
                d8 += deviceStatsIntermediates.powerByBracket[length4];
            }
            this.mStatsLayout.setDevicePowerEstimate(this.mTmpDeviceStatsArray, d8);
            powerComponentAggregatedPowerStats.setDeviceStats(iArr5, this.mTmpDeviceStatsArray);
            size3 = i33 - 1;
            arrayList4 = arrayList5;
            jArr2 = jArr8;
            i27 = i32;
            dArr3 = dArr6;
        }
        for (int size4 = ((ArrayList) this.mPlan.combinedDeviceStateEstimations).size() - 1; size4 >= 0; size4--) {
            PowerStatsProcessor.CombinedDeviceStateEstimate combinedDeviceStateEstimate = (PowerStatsProcessor.CombinedDeviceStateEstimate) ((ArrayList) this.mPlan.combinedDeviceStateEstimations).get(size4);
            DeviceStatsIntermediates deviceStatsIntermediates2 = new DeviceStatsIntermediates();
            combinedDeviceStateEstimate.intermediates = deviceStatsIntermediates2;
            int i38 = this.mStatsLayout.mUidPowerBracketCount;
            deviceStatsIntermediates2.timeByBracket = new long[i38];
            deviceStatsIntermediates2.powerByBracket = new double[i38];
            ArrayList arrayList6 = (ArrayList) combinedDeviceStateEstimate.deviceStateEstimations;
            for (int size5 = arrayList6.size() - 1; size5 >= 0; size5--) {
                DeviceStatsIntermediates deviceStatsIntermediates3 = (DeviceStatsIntermediates) ((PowerStatsProcessor.DeviceStateEstimation) arrayList6.get(size5)).intermediates;
                deviceStatsIntermediates3.getClass();
                for (int i39 = 0; i39 < i38; i39++) {
                    long[] jArr9 = deviceStatsIntermediates2.timeByBracket;
                    jArr9[i39] = jArr9[i39] + deviceStatsIntermediates3.timeByBracket[i39];
                    double[] dArr7 = deviceStatsIntermediates2.powerByBracket;
                    dArr7[i39] = dArr7[i39] + deviceStatsIntermediates3.powerByBracket[i39];
                }
            }
        }
        ArrayList arrayList7 = new ArrayList();
        powerComponentAggregatedPowerStats.collectUids(arrayList7);
        if (!arrayList7.isEmpty()) {
            Iterator it = arrayList7.iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                int i40 = 0;
                while (i40 < ((ArrayList) this.mPlan.uidStateEstimates).size()) {
                    PowerStatsProcessor.UidStateEstimate uidStateEstimate3 = (PowerStatsProcessor.UidStateEstimate) ((ArrayList) this.mPlan.uidStateEstimates).get(i40);
                    DeviceStatsIntermediates deviceStatsIntermediates4 = (DeviceStatsIntermediates) uidStateEstimate3.combinedDeviceStateEstimate.intermediates;
                    int i41 = 0;
                    while (i41 < ((ArrayList) uidStateEstimate3.proportionalEstimates).size()) {
                        PowerStatsProcessor.UidStateProportionalEstimate uidStateProportionalEstimate = (PowerStatsProcessor.UidStateProportionalEstimate) ((ArrayList) uidStateEstimate3.proportionalEstimates).get(i41);
                        if (powerComponentAggregatedPowerStats.getUidStats(intValue, uidStateProportionalEstimate.stateValues, this.mTmpUidStatsArray)) {
                            double d9 = 0.0d;
                            int i42 = 0;
                            while (true) {
                                cpuPowerStatsLayout = this.mStatsLayout;
                                if (i42 >= cpuPowerStatsLayout.mUidPowerBracketCount) {
                                    break;
                                }
                                long j8 = deviceStatsIntermediates4.timeByBracket[i42];
                                if (j8 == 0) {
                                    i2 = i40;
                                    uidStateEstimate2 = uidStateEstimate3;
                                } else {
                                    i2 = i40;
                                    uidStateEstimate2 = uidStateEstimate3;
                                    long j9 = this.mTmpUidStatsArray[cpuPowerStatsLayout.mUidPowerBracketsPosition + i42];
                                    if (j9 != 0) {
                                        d9 = ((deviceStatsIntermediates4.powerByBracket[i42] * j9) / j8) + d9;
                                    }
                                }
                                i42++;
                                uidStateEstimate3 = uidStateEstimate2;
                                i40 = i2;
                            }
                            i = i40;
                            uidStateEstimate = uidStateEstimate3;
                            cpuPowerStatsLayout.setUidPowerEstimate(this.mTmpUidStatsArray, d9);
                            powerComponentAggregatedPowerStats.setUidStats(intValue, uidStateProportionalEstimate.stateValues, this.mTmpUidStatsArray);
                        } else {
                            i = i40;
                            uidStateEstimate = uidStateEstimate3;
                        }
                        i41++;
                        uidStateEstimate3 = uidStateEstimate;
                        i40 = i;
                    }
                    i40++;
                }
            }
        }
        this.mPlan.resetIntermediates();
    }
}
