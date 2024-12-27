package com.android.server.power.stats;

import android.os.BatteryStats;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;
import android.os.UidBatteryConsumer;
import android.util.SparseArray;
import com.android.internal.os.PowerProfile;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class FlashlightPowerCalculator extends PowerCalculator {
    public final UsageBasedPowerEstimator mPowerEstimator;

    public FlashlightPowerCalculator(PowerProfile powerProfile) {
        this.mPowerEstimator = new UsageBasedPowerEstimator(powerProfile.getAveragePower("camera.flashlight"));
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final void calculate(BatteryUsageStats.Builder builder, BatteryStats batteryStats, long j, long j2, BatteryUsageStatsQuery batteryUsageStatsQuery) {
        SparseArray uidBatteryConsumerBuilders = builder.getUidBatteryConsumerBuilders();
        for (int size = uidBatteryConsumerBuilders.size() - 1; size >= 0; size--) {
            UidBatteryConsumer.Builder builder2 = (UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.valueAt(size);
            BatteryStats.Timer flashlightTurnedOnTimer = builder2.getBatteryStatsUid().getFlashlightTurnedOnTimer();
            UsageBasedPowerEstimator usageBasedPowerEstimator = this.mPowerEstimator;
            usageBasedPowerEstimator.getClass();
            long calculateDuration = UsageBasedPowerEstimator.calculateDuration(flashlightTurnedOnTimer, j);
            builder2.setUsageDurationMillis(6, calculateDuration).setConsumedPower(6, usageBasedPowerEstimator.mAveragePowerMahPerMs * calculateDuration);
        }
        long flashlightOnTime = batteryStats.getFlashlightOnTime(j, 0) / 1000;
        double d = this.mPowerEstimator.mAveragePowerMahPerMs * flashlightOnTime;
        builder.getAggregateBatteryConsumerBuilder(0).setUsageDurationMillis(6, flashlightOnTime).setConsumedPower(6, d);
        builder.getAggregateBatteryConsumerBuilder(1).setUsageDurationMillis(6, flashlightOnTime).setConsumedPower(6, d);
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final boolean isPowerComponentSupported(int i) {
        return i == 6;
    }
}
