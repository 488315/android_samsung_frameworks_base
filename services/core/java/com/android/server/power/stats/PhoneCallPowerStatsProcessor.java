package com.android.server.power.stats;

import android.os.PersistableBundle;
import android.util.SparseArray;

import com.android.internal.os.PowerStats;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PhoneCallPowerStatsProcessor extends PowerStatsProcessor {
    public final PowerStats.Descriptor mDescriptor;
    public PowerStats.Descriptor mMobileRadioStatsDescriptor;
    public MobileRadioPowerStatsLayout mMobileRadioStatsLayout;
    public final PowerStatsLayout mStatsLayout;
    public final long[] mTmpDeviceStats;
    public long[] mTmpMobileRadioDeviceStats;

    public PhoneCallPowerStatsProcessor() {
        PowerStatsLayout powerStatsLayout = new PowerStatsLayout();
        this.mStatsLayout = powerStatsLayout;
        powerStatsLayout.addDeviceSectionPowerEstimate();
        PersistableBundle persistableBundle = new PersistableBundle();
        powerStatsLayout.toExtras(persistableBundle);
        PowerStats.Descriptor descriptor =
                new PowerStats.Descriptor(
                        14,
                        powerStatsLayout.mDeviceStatsArrayLength,
                        (SparseArray) null,
                        0,
                        0,
                        persistableBundle);
        this.mDescriptor = descriptor;
        this.mTmpDeviceStats = new long[descriptor.statsArrayLength];
    }

    @Override // com.android.server.power.stats.PowerStatsProcessor
    public final void finish(
            final PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats, long j) {
        powerComponentAggregatedPowerStats.mPowerStatsDescriptor = this.mDescriptor;
        final PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats2 =
                (PowerComponentAggregatedPowerStats)
                        powerComponentAggregatedPowerStats.mAggregatedPowerStats
                                .mPowerComponentStats.get(8);
        if (powerComponentAggregatedPowerStats2 == null) {
            return;
        }
        if (this.mMobileRadioStatsDescriptor == null) {
            PowerStats.Descriptor descriptor =
                    powerComponentAggregatedPowerStats2.mPowerStatsDescriptor;
            this.mMobileRadioStatsDescriptor = descriptor;
            if (descriptor == null) {
                return;
            }
            this.mMobileRadioStatsLayout = new MobileRadioPowerStatsLayout(descriptor);
            this.mTmpMobileRadioDeviceStats =
                    new long[this.mMobileRadioStatsDescriptor.statsArrayLength];
        }
        MultiStateStats.States.forEachTrackedStateCombination(
                new Consumer() { // from class:
                                 // com.android.server.power.stats.PhoneCallPowerStatsProcessor$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        PhoneCallPowerStatsProcessor phoneCallPowerStatsProcessor =
                                PhoneCallPowerStatsProcessor.this;
                        PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats3 =
                                powerComponentAggregatedPowerStats2;
                        PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats4 =
                                powerComponentAggregatedPowerStats;
                        int[] iArr = (int[]) obj;
                        powerComponentAggregatedPowerStats3.getDeviceStats(
                                iArr, phoneCallPowerStatsProcessor.mTmpMobileRadioDeviceStats);
                        double d =
                                phoneCallPowerStatsProcessor
                                                .mTmpMobileRadioDeviceStats[
                                                phoneCallPowerStatsProcessor
                                                        .mMobileRadioStatsLayout
                                                        .mDeviceCallPowerPosition]
                                        / 1000000.0d;
                        PowerStatsLayout powerStatsLayout =
                                phoneCallPowerStatsProcessor.mStatsLayout;
                        long[] jArr = phoneCallPowerStatsProcessor.mTmpDeviceStats;
                        powerStatsLayout.setDevicePowerEstimate(jArr, d);
                        powerComponentAggregatedPowerStats4.setDeviceStats(iArr, jArr);
                    }
                },
                powerComponentAggregatedPowerStats2.mConfig.getDeviceStateConfig(),
                new int[2],
                0);
    }
}
