package com.android.server.power.stats;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PowerStatsScheduler$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PowerStatsScheduler f$0;

    public /* synthetic */ PowerStatsScheduler$$ExternalSyntheticLambda0(PowerStatsScheduler powerStatsScheduler, int i) {
        this.$r8$classId = i;
        this.f$0 = powerStatsScheduler;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        PowerStatsScheduler powerStatsScheduler = this.f$0;
        switch (i) {
            case 0:
                long currentTimeMillis = powerStatsScheduler.mClock.currentTimeMillis();
                long monotonicTime = powerStatsScheduler.mMonotonicClock.monotonicTime();
                long lastSavedSpanEndMonotonicTime = powerStatsScheduler.getLastSavedSpanEndMonotonicTime();
                if (lastSavedSpanEndMonotonicTime < 0) {
                    lastSavedSpanEndMonotonicTime = ((Long) powerStatsScheduler.mEarliestAvailableBatteryHistoryTimeMs.get()).longValue();
                }
                long j = lastSavedSpanEndMonotonicTime;
                long j2 = powerStatsScheduler.mAggregatedPowerStatsSpanDuration;
                long alignToWallClock = PowerStatsScheduler.alignToWallClock(j + j2, j2, monotonicTime, currentTimeMillis);
                while (true) {
                    long j3 = j;
                    j = alignToWallClock;
                    if (j > monotonicTime) {
                        break;
                    } else {
                        powerStatsScheduler.mPowerStatsAggregator.aggregatePowerStats(j3, j, new PowerStatsScheduler$$ExternalSyntheticLambda2(0, powerStatsScheduler));
                        alignToWallClock = powerStatsScheduler.mAggregatedPowerStatsSpanDuration + j;
                    }
                }
            default:
                powerStatsScheduler.scheduleNextPowerStatsAggregation();
                break;
        }
    }
}
