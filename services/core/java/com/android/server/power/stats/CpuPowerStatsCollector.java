package com.android.server.power.stats;

import android.os.PersistableBundle;
import android.os.Process;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.os.CpuScalingPolicies;
import com.android.internal.os.PowerProfile;
import com.android.internal.os.PowerStats;
import com.android.server.CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0;

import java.util.Arrays;
import java.util.Locale;

public final class CpuPowerStatsCollector extends PowerStatsCollector {
    public PowerStatsCollector.ConsumedEnergyRetrieverImpl mConsumedEnergyRetriever;
    public int[] mCpuEnergyConsumerIds;
    public PowerStats mCpuPowerStats;
    public CpuScalingPolicies mCpuScalingPolicies;
    public long[] mCpuTimeByScalingStep;
    public int mDefaultCpuPowerBrackets;
    public int mDefaultCpuPowerBracketsPerEnergyConsumer;
    public final BatteryStatsImpl.PowerStatsCollectorInjector mInjector;
    public boolean mIsInitialized;
    public boolean mIsPerUidTimeInStateSupported;
    public KernelCpuStatsReader mKernelCpuStatsReader;
    public long[] mLastConsumedEnergyUws;
    public long mLastUpdateTimestampNanos;
    public long mLastUpdateUptimeMillis;
    public int mLastVoltageMv;
    public CpuPowerStatsLayout mLayout;
    public PowerProfile mPowerProfile;
    public PowerStats.Descriptor mPowerStatsDescriptor;
    public long[] mTempCpuTimeByScalingStep;
    public long[] mTempUidStats;
    public final SparseArray mUidStats;
    public BatteryStatsImpl$PowerStatsCollectorInjector$$ExternalSyntheticLambda0 mVoltageSupplier;

    interface KernelCpuStatsCallback {
        void processUidStats(int i, long[] jArr);
    }

    public class KernelCpuStatsReader {
        public native boolean nativeIsSupportedFeature();

        public native long nativeReadCpuStats(
                KernelCpuStatsCallback kernelCpuStatsCallback,
                int[] iArr,
                long j,
                long[] jArr,
                long[] jArr2);
    }

    public final class UidStats {
        public long[] stats;
        public long[] timeByPowerBracket;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public CpuPowerStatsCollector(
            com.android.server.power.stats.BatteryStatsImpl.PowerStatsCollectorInjector r8) {
        /*
            r7 = this;
            com.android.server.power.stats.BatteryStatsImpl r0 = com.android.server.power.stats.BatteryStatsImpl.this
            com.android.server.power.stats.BatteryStatsImpl$MyHandler r2 = r0.mHandler
            r0 = 1
            java.lang.String r0 = android.os.BatteryConsumer.powerComponentIdToString(r0)
            long r3 = r8.getPowerStatsCollectionThrottlePeriod(r0)
            com.android.server.power.stats.BatteryStatsImpl r0 = com.android.server.power.stats.BatteryStatsImpl.this
            com.android.server.power.stats.PowerStatsUidResolver r5 = r0.mPowerStatsUidResolver
            com.android.internal.os.Clock r6 = r0.mClock
            r1 = r7
            r1.<init>(r2, r3, r5, r6)
            android.util.SparseArray r0 = new android.util.SparseArray
            r0.<init>()
            r7.mUidStats = r0
            r0 = 0
            int[] r0 = new int[r0]
            r7.mCpuEnergyConsumerIds = r0
            r7.mInjector = r8
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.power.stats.CpuPowerStatsCollector.<init>(com.android.server.power.stats.BatteryStatsImpl$PowerStatsCollectorInjector):void");
    }

    public static void mapScalingStepsToDefaultBrackets(int[] iArr, double[] dArr, int i) {
        double d = Double.MAX_VALUE;
        double d2 = Double.MIN_VALUE;
        int i2 = 0;
        for (double d3 : dArr) {
            if (d3 < d) {
                d = d3;
            }
            if (d3 > d2) {
                d2 = d3;
            }
        }
        if (dArr.length <= i) {
            while (i2 < iArr.length) {
                iArr[i2] = i2;
                i2++;
            }
            return;
        }
        double log = Math.log(d);
        double log2 = (Math.log(d2) - log) / i;
        while (i2 < dArr.length) {
            int log3 = (int) ((Math.log(dArr[i2]) - log) / log2);
            if (log3 >= i) {
                log3 = i - 1;
            }
            iArr[i2] = log3;
            i2++;
        }
    }

    @Override // com.android.server.power.stats.PowerStatsCollector
    public final PowerStats collectStats() {
        if (!ensureInitialized$1() || !this.mIsPerUidTimeInStateSupported) {
            return null;
        }
        this.mCpuPowerStats.uidStats.clear();
        long nativeReadCpuStats =
                this.mKernelCpuStatsReader.nativeReadCpuStats(
                        new KernelCpuStatsCallback() { // from class:
                                                       // com.android.server.power.stats.CpuPowerStatsCollector$$ExternalSyntheticLambda0
                            @Override // com.android.server.power.stats.CpuPowerStatsCollector.KernelCpuStatsCallback
                            public final void processUidStats(int i, long[] jArr) {
                                CpuPowerStatsCollector cpuPowerStatsCollector =
                                        CpuPowerStatsCollector.this;
                                int i2 = cpuPowerStatsCollector.mLayout.mUidPowerBracketCount;
                                CpuPowerStatsCollector.UidStats uidStats =
                                        (CpuPowerStatsCollector.UidStats)
                                                cpuPowerStatsCollector.mUidStats.get(i);
                                if (uidStats == null) {
                                    uidStats = new CpuPowerStatsCollector.UidStats();
                                    uidStats.timeByPowerBracket = new long[i2];
                                    uidStats.stats =
                                            new long
                                                    [cpuPowerStatsCollector
                                                            .mLayout
                                                            .mUidStatsArrayLength];
                                    cpuPowerStatsCollector.mUidStats.put(i, uidStats);
                                }
                                boolean z = false;
                                for (int i3 = i2 - 1; i3 >= 0; i3--) {
                                    long max =
                                            Math.max(
                                                    0L, jArr[i3] - uidStats.timeByPowerBracket[i3]);
                                    if (max != 0) {
                                        z = true;
                                    }
                                    uidStats.stats[
                                                    cpuPowerStatsCollector
                                                                    .mLayout
                                                                    .mUidPowerBracketsPosition
                                                            + i3] =
                                            max;
                                    uidStats.timeByPowerBracket[i3] = jArr[i3];
                                }
                                if (z) {
                                    int appUidForSdkSandboxUid =
                                            Process.isSdkSandboxUid(i)
                                                    ? Process.getAppUidForSdkSandboxUid(i)
                                                    : cpuPowerStatsCollector.mUidResolver.mapUid(i);
                                    long[] jArr2 =
                                            (long[])
                                                    cpuPowerStatsCollector.mCpuPowerStats.uidStats
                                                            .get(appUidForSdkSandboxUid);
                                    if (jArr2 == null) {
                                        cpuPowerStatsCollector.mCpuPowerStats.uidStats.put(
                                                appUidForSdkSandboxUid, uidStats.stats);
                                        return;
                                    }
                                    for (int i4 = 0; i4 < jArr2.length; i4++) {
                                        jArr2[i4] = jArr2[i4] + uidStats.stats[i4];
                                    }
                                }
                            }
                        },
                        this.mLayout.mScalingStepToPowerBracketMap,
                        this.mLastUpdateTimestampNanos,
                        this.mTempCpuTimeByScalingStep,
                        this.mTempUidStats);
        for (int i = this.mLayout.mDeviceCpuTimeByScalingStepCount - 1; i >= 0; i--) {
            CpuPowerStatsLayout cpuPowerStatsLayout = this.mLayout;
            long[] jArr = this.mCpuPowerStats.stats;
            long[] jArr2 = this.mTempCpuTimeByScalingStep;
            long j = jArr2[i];
            long[] jArr3 = this.mCpuTimeByScalingStep;
            jArr[cpuPowerStatsLayout.mDeviceCpuTimeByScalingStepPosition + i] = j - jArr3[i];
            jArr3[i] = jArr2[i];
        }
        this.mCpuPowerStats.durationMs =
                (nativeReadCpuStats - this.mLastUpdateTimestampNanos) / 1000000;
        this.mLastUpdateTimestampNanos = nativeReadCpuStats;
        long uptimeMillis = this.mClock.uptimeMillis();
        long j2 = uptimeMillis - this.mLastUpdateUptimeMillis;
        this.mLastUpdateUptimeMillis = uptimeMillis;
        PowerStats powerStats = this.mCpuPowerStats;
        long j3 = powerStats.durationMs;
        if (j2 > j3) {
            j2 = j3;
        }
        powerStats.stats[this.mLayout.mDeviceDurationPosition] = j2;
        if (this.mCpuEnergyConsumerIds.length != 0) {
            int asInt = this.mVoltageSupplier.getAsInt();
            if (asInt <= 0) {
                Slog.wtf(
                        "CpuPowerStatsCollector",
                        "Unexpected battery voltage ("
                                + asInt
                                + " mV) when querying energy consumers");
            } else {
                int i2 = this.mLastVoltageMv;
                int i3 = i2 != 0 ? (i2 + asInt) / 2 : asInt;
                this.mLastVoltageMv = asInt;
                long[] consumedEnergyUws =
                        this.mConsumedEnergyRetriever.getConsumedEnergyUws(
                                this.mCpuEnergyConsumerIds);
                if (consumedEnergyUws != null) {
                    for (int length = consumedEnergyUws.length - 1; length >= 0; length--) {
                        long j4 = this.mLastConsumedEnergyUws[length];
                        long j5 = 0;
                        long j6 = j4 != -1 ? consumedEnergyUws[length] - j4 : 0L;
                        if (j6 >= 0) {
                            j5 = j6;
                        }
                        this.mLayout.setConsumedEnergy(
                                this.mCpuPowerStats.stats,
                                length,
                                PowerStatsCollector.uJtoUc(i3, j5));
                        this.mLastConsumedEnergyUws[length] = consumedEnergyUws[length];
                    }
                }
            }
        }
        return this.mCpuPowerStats;
    }

    public final boolean ensureInitialized$1() {
        int[] initDefaultPowerBrackets;
        if (this.mIsInitialized) {
            return true;
        }
        if (!this.mEnabled) {
            return false;
        }
        BatteryStatsImpl.PowerStatsCollectorInjector powerStatsCollectorInjector = this.mInjector;
        BatteryStatsImpl batteryStatsImpl = BatteryStatsImpl.this;
        this.mCpuScalingPolicies = batteryStatsImpl.mCpuScalingPolicies;
        this.mPowerProfile = batteryStatsImpl.mPowerProfile;
        KernelCpuStatsReader kernelCpuStatsReader = new KernelCpuStatsReader();
        this.mKernelCpuStatsReader = kernelCpuStatsReader;
        this.mConsumedEnergyRetriever = powerStatsCollectorInjector.mConsumedEnergyRetriever;
        this.mVoltageSupplier =
                new BatteryStatsImpl$PowerStatsCollectorInjector$$ExternalSyntheticLambda0(
                        powerStatsCollectorInjector);
        this.mDefaultCpuPowerBrackets = 3;
        this.mDefaultCpuPowerBracketsPerEnergyConsumer = 2;
        this.mIsPerUidTimeInStateSupported = kernelCpuStatsReader.nativeIsSupportedFeature();
        int[] energyConsumerIds = this.mConsumedEnergyRetriever.getEnergyConsumerIds(2, null);
        this.mCpuEnergyConsumerIds = energyConsumerIds;
        long[] jArr = new long[energyConsumerIds.length];
        this.mLastConsumedEnergyUws = jArr;
        Arrays.fill(jArr, -1L);
        int scalingStepCount = this.mCpuScalingPolicies.getScalingStepCount();
        this.mCpuTimeByScalingStep = new long[scalingStepCount];
        this.mTempCpuTimeByScalingStep = new long[scalingStepCount];
        if (this.mPowerProfile.getCpuPowerBracketCount() != -1) {
            initDefaultPowerBrackets = new int[this.mCpuScalingPolicies.getScalingStepCount()];
            int i = 0;
            for (int i2 : this.mCpuScalingPolicies.getPolicies()) {
                int[] frequencies = this.mCpuScalingPolicies.getFrequencies(i2);
                int i3 = 0;
                while (i3 < frequencies.length) {
                    initDefaultPowerBrackets[i] =
                            this.mPowerProfile.getCpuPowerBracketForScalingStep(i2, i3);
                    i3++;
                    i++;
                }
            }
        } else {
            int[] iArr = this.mCpuEnergyConsumerIds;
            if (iArr.length == 0 || iArr.length == 1) {
                initDefaultPowerBrackets = initDefaultPowerBrackets(this.mDefaultCpuPowerBrackets);
            } else if (this.mCpuScalingPolicies.getPolicies().length
                    == this.mCpuEnergyConsumerIds.length) {
                int i4 = this.mDefaultCpuPowerBracketsPerEnergyConsumer;
                int[] iArr2 = new int[this.mCpuScalingPolicies.getScalingStepCount()];
                int i5 = 0;
                int i6 = 0;
                for (int i7 : this.mCpuScalingPolicies.getPolicies()) {
                    int[] frequencies2 = this.mCpuScalingPolicies.getFrequencies(i7);
                    double[] dArr = new double[frequencies2.length];
                    for (int i8 = 0; i8 < frequencies2.length; i8++) {
                        dArr[i8] = this.mPowerProfile.getAveragePowerForCpuScalingStep(i7, i8);
                    }
                    int[] iArr3 = new int[frequencies2.length];
                    mapScalingStepsToDefaultBrackets(iArr3, dArr, i4);
                    int i9 = 0;
                    int i10 = 0;
                    while (i9 < frequencies2.length) {
                        int i11 = iArr3[i9] + i6;
                        int i12 = i5 + 1;
                        iArr2[i5] = i11;
                        if (i11 > i10) {
                            i10 = i11;
                        }
                        i9++;
                        i5 = i12;
                    }
                    i6 = i10 + 1;
                }
                initDefaultPowerBrackets = iArr2;
            } else {
                StringBuilder sb =
                        new StringBuilder(
                                "Assigning a single power brackets to each CPU_CLUSTER energy"
                                    + " consumer. Number of CPU clusters (");
                sb.append(this.mCpuScalingPolicies.getPolicies().length);
                sb.append(") does not match the number of energy consumers (");
                CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0.m(
                        sb,
                        this.mCpuEnergyConsumerIds.length,
                        ").  Using default power bucket assignment.",
                        "CpuPowerStatsCollector");
                initDefaultPowerBrackets = initDefaultPowerBrackets(this.mDefaultCpuPowerBrackets);
            }
        }
        CpuPowerStatsLayout cpuPowerStatsLayout = new CpuPowerStatsLayout();
        this.mLayout = cpuPowerStatsLayout;
        cpuPowerStatsLayout.mDeviceCpuTimeByScalingStepPosition =
                cpuPowerStatsLayout.addDeviceSection(scalingStepCount, 0, "steps");
        cpuPowerStatsLayout.mDeviceCpuTimeByScalingStepCount = scalingStepCount;
        CpuPowerStatsLayout cpuPowerStatsLayout2 = this.mLayout;
        int length = this.mCpuScalingPolicies.getPolicies().length;
        cpuPowerStatsLayout2.mDeviceCpuTimeByClusterPosition =
                cpuPowerStatsLayout2.addDeviceSection(length, 0, "clusters");
        cpuPowerStatsLayout2.mDeviceCpuTimeByClusterCount = length;
        CpuPowerStatsLayout cpuPowerStatsLayout3 = this.mLayout;
        cpuPowerStatsLayout3.mDeviceDurationPosition =
                cpuPowerStatsLayout3.addDeviceSection(1, 1, "usage");
        this.mLayout.addDeviceSectionEnergyConsumers(this.mCpuEnergyConsumerIds.length);
        this.mLayout.addDeviceSectionPowerEstimate();
        CpuPowerStatsLayout cpuPowerStatsLayout4 = this.mLayout;
        cpuPowerStatsLayout4.mScalingStepToPowerBracketMap = initDefaultPowerBrackets;
        cpuPowerStatsLayout4.mUidPowerBracketCount = 1;
        for (int i13 : initDefaultPowerBrackets) {
            if (i13 >= cpuPowerStatsLayout4.mUidPowerBracketCount) {
                cpuPowerStatsLayout4.mUidPowerBracketCount = i13 + 1;
            }
        }
        cpuPowerStatsLayout4.mUidPowerBracketsPosition =
                cpuPowerStatsLayout4.addUidSection(
                        cpuPowerStatsLayout4.mUidPowerBracketCount, 0, "time");
        CpuPowerStatsLayout cpuPowerStatsLayout5 = this.mLayout;
        cpuPowerStatsLayout5.mUidPowerEstimatePosition =
                cpuPowerStatsLayout5.addUidSection(1, 5, "power");
        PersistableBundle persistableBundle = new PersistableBundle();
        this.mLayout.toExtras(persistableBundle);
        CpuPowerStatsLayout cpuPowerStatsLayout6 = this.mLayout;
        this.mPowerStatsDescriptor =
                new PowerStats.Descriptor(
                        1,
                        cpuPowerStatsLayout6.mDeviceStatsArrayLength,
                        (SparseArray) null,
                        0,
                        cpuPowerStatsLayout6.mUidStatsArrayLength,
                        persistableBundle);
        this.mCpuPowerStats = new PowerStats(this.mPowerStatsDescriptor);
        this.mTempUidStats = new long[this.mLayout.mUidPowerBracketCount];
        this.mIsInitialized = true;
        return true;
    }

    public String getCpuPowerBracketDescription(int i) {
        if (!ensureInitialized$1()) {
            return "";
        }
        int[] iArr = this.mLayout.mScalingStepToPowerBracketMap;
        StringBuilder sb = new StringBuilder();
        int[] policies = this.mCpuScalingPolicies.getPolicies();
        int i2 = 0;
        for (int i3 : policies) {
            int[] frequencies = this.mCpuScalingPolicies.getFrequencies(i3);
            for (int i4 = 0; i4 < frequencies.length; i4++) {
                if (iArr[i2] == i) {
                    if (sb.length() != 0) {
                        sb.append(", ");
                    }
                    if (policies.length > 1) {
                        sb.append(i3);
                        sb.append('/');
                    }
                    sb.append(frequencies[i4] / 1000);
                    sb.append('(');
                    sb.append(
                            String.format(
                                    Locale.US,
                                    "%.1f",
                                    Double.valueOf(
                                            this.mPowerProfile.getAveragePowerForCpuScalingStep(
                                                    i3, i4))));
                    sb.append(')');
                }
                i2++;
            }
        }
        return sb.toString();
    }

    public PowerStats.Descriptor getPowerStatsDescriptor() {
        if (ensureInitialized$1()) {
            return this.mPowerStatsDescriptor;
        }
        return null;
    }

    public final int[] initDefaultPowerBrackets(int i) {
        int[] iArr = new int[this.mCpuScalingPolicies.getScalingStepCount()];
        double[] dArr = new double[this.mCpuScalingPolicies.getScalingStepCount()];
        int i2 = 0;
        for (int i3 : this.mCpuScalingPolicies.getPolicies()) {
            int[] frequencies = this.mCpuScalingPolicies.getFrequencies(i3);
            int i4 = 0;
            while (i4 < frequencies.length) {
                dArr[i2] = this.mPowerProfile.getAveragePowerForCpuScalingStep(i3, i4);
                i4++;
                i2++;
            }
        }
        mapScalingStepsToDefaultBrackets(iArr, dArr, i);
        return iArr;
    }

    @Override // com.android.server.power.stats.PowerStatsCollector
    public final void onUidRemoved(int i) {
        this.mUidStats.remove(i);
    }
}
