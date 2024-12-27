package com.android.server.power.stats;

import android.os.BatteryStats;

public final class UsageBasedPowerEstimator {
    public final double mAveragePowerMahPerMs;

    public UsageBasedPowerEstimator(double d) {
        this.mAveragePowerMahPerMs = d / 3600000.0d;
    }

    public static long calculateDuration(BatteryStats.Timer timer, long j) {
        if (timer == null) {
            return 0L;
        }
        return timer.getTotalTimeLocked(j, 0) / 1000;
    }
}
