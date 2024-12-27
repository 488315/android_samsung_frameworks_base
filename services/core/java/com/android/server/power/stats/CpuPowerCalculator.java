package com.android.server.power.stats;

import android.os.BatteryConsumer;
import android.os.BatteryStats;

import com.android.internal.os.CpuScalingPolicies;
import com.android.internal.os.PowerProfile;
import com.android.server.audio.AudioService$$ExternalSyntheticOutline0;

public final class CpuPowerCalculator extends PowerCalculator {
    public static final BatteryConsumer.Key[] UNINITIALIZED_KEYS = new BatteryConsumer.Key[0];
    public final UsageBasedPowerEstimator mCpuActivePowerEstimator;
    public final CpuScalingPolicies mCpuScalingPolicies;
    public final int mNumCpuClusters;
    public final UsageBasedPowerEstimator[] mPerClusterPowerEstimators;
    public final UsageBasedPowerEstimator[] mPerCpuFreqPowerEstimators;
    public final UsageBasedPowerEstimator[][] mPerCpuFreqPowerEstimatorsByCluster;

    public CpuPowerCalculator(CpuScalingPolicies cpuScalingPolicies, PowerProfile powerProfile) {
        this.mCpuScalingPolicies = cpuScalingPolicies;
        int[] policies = cpuScalingPolicies.getPolicies();
        this.mNumCpuClusters = policies.length;
        this.mCpuActivePowerEstimator =
                new UsageBasedPowerEstimator(powerProfile.getAveragePower("cpu.active"));
        this.mPerClusterPowerEstimators = new UsageBasedPowerEstimator[policies.length];
        for (int i = 0; i < policies.length; i++) {
            this.mPerClusterPowerEstimators[i] =
                    new UsageBasedPowerEstimator(
                            powerProfile.getAveragePowerForCpuScalingPolicy(policies[i]));
        }
        this.mPerCpuFreqPowerEstimators =
                new UsageBasedPowerEstimator[cpuScalingPolicies.getScalingStepCount()];
        this.mPerCpuFreqPowerEstimatorsByCluster =
                new UsageBasedPowerEstimator[this.mNumCpuClusters][];
        int i2 = 0;
        for (int i3 = 0; i3 < policies.length; i3++) {
            int i4 = policies[i3];
            int[] frequencies = cpuScalingPolicies.getFrequencies(i4);
            this.mPerCpuFreqPowerEstimatorsByCluster[i3] =
                    new UsageBasedPowerEstimator[frequencies.length];
            int i5 = 0;
            while (i5 < frequencies.length) {
                UsageBasedPowerEstimator usageBasedPowerEstimator =
                        new UsageBasedPowerEstimator(
                                powerProfile.getAveragePowerForCpuScalingStep(i4, i5));
                this.mPerCpuFreqPowerEstimatorsByCluster[i3][i5] = usageBasedPowerEstimator;
                this.mPerCpuFreqPowerEstimators[i2] = usageBasedPowerEstimator;
                i5++;
                i2++;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x01fa  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x01ff  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0103  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x008a  */
    @Override // com.android.server.power.stats.PowerCalculator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void calculate(
            android.os.BatteryUsageStats.Builder r40,
            android.os.BatteryStats r41,
            long r42,
            long r44,
            android.os.BatteryUsageStatsQuery r46) {
        /*
            Method dump skipped, instructions count: 564
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.power.stats.CpuPowerCalculator.calculate(android.os.BatteryUsageStats$Builder,"
                    + " android.os.BatteryStats, long, long,"
                    + " android.os.BatteryUsageStatsQuery):void");
    }

    public final double calculateUidModeledPowerMah(
            BatteryStats.Uid uid, long j, long[] jArr, long[] jArr2) {
        double d = this.mCpuActivePowerEstimator.mAveragePowerMahPerMs * j;
        if (jArr != null) {
            int length = jArr.length;
            int i = this.mNumCpuClusters;
            if (length == i) {
                for (int i2 = 0; i2 < i; i2++) {
                    d += this.mPerClusterPowerEstimators[i2].mAveragePowerMahPerMs * jArr[i2];
                }
            } else {
                StringBuilder sb = new StringBuilder("UID ");
                sb.append(uid.getUid());
                sb.append(" CPU cluster # mismatch: Power Profile # ");
                sb.append(i);
                sb.append(" actual # ");
                AudioService$$ExternalSyntheticOutline0.m(sb, jArr.length, "CpuPowerCalculator");
            }
        }
        if (jArr2 != null) {
            int length2 = jArr2.length;
            UsageBasedPowerEstimator[] usageBasedPowerEstimatorArr =
                    this.mPerCpuFreqPowerEstimators;
            if (length2 == usageBasedPowerEstimatorArr.length) {
                for (int i3 = 0; i3 < jArr2.length; i3++) {
                    d += usageBasedPowerEstimatorArr[i3].mAveragePowerMahPerMs * jArr2[i3];
                }
            } else {
                StringBuilder sb2 = new StringBuilder("UID ");
                sb2.append(uid.getUid());
                sb2.append(" CPU freq # mismatch: Power Profile # ");
                sb2.append(usageBasedPowerEstimatorArr.length);
                sb2.append(" actual # ");
                AudioService$$ExternalSyntheticOutline0.m(sb2, jArr2.length, "CpuPowerCalculator");
            }
        }
        return d;
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final boolean isPowerComponentSupported(int i) {
        return i == 1;
    }
}
