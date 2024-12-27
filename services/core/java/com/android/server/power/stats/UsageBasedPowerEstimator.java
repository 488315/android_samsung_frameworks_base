package com.android.server.power.stats;

import android.os.BatteryStats;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
