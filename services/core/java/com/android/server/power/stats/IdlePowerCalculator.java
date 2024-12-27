package com.android.server.power.stats;

import android.os.BatteryStats;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;

import com.android.internal.os.PowerProfile;

public final class IdlePowerCalculator extends PowerCalculator {
    public final double mAveragePowerCpuIdleMahPerUs;
    public final double mAveragePowerCpuSuspendMahPerUs;
    public long mDurationMs;
    public double mPowerMah;

    public IdlePowerCalculator(PowerProfile powerProfile) {
        this.mAveragePowerCpuSuspendMahPerUs = powerProfile.getAveragePower("cpu.suspend") / 3.6E9d;
        this.mAveragePowerCpuIdleMahPerUs = powerProfile.getAveragePower("cpu.idle") / 3.6E9d;
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final void calculate(
            BatteryUsageStats.Builder builder,
            BatteryStats batteryStats,
            long j,
            long j2,
            BatteryUsageStatsQuery batteryUsageStatsQuery) {
        long computeBatteryRealtime = batteryStats.computeBatteryRealtime(j, 0);
        double computeBatteryUptime =
                (batteryStats.computeBatteryUptime(j2, 0) * this.mAveragePowerCpuIdleMahPerUs)
                        + (computeBatteryRealtime * this.mAveragePowerCpuSuspendMahPerUs);
        this.mPowerMah = computeBatteryUptime;
        this.mDurationMs = computeBatteryRealtime / 1000;
        if (computeBatteryUptime != 0.0d) {
            builder.getAggregateBatteryConsumerBuilder(0)
                    .setConsumedPower(16, this.mPowerMah)
                    .setUsageDurationMillis(16, this.mDurationMs);
        }
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public final boolean isPowerComponentSupported(int i) {
        return i == 16;
    }
}
