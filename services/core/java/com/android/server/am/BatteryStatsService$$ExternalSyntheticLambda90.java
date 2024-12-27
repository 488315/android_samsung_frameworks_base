package com.android.server.am;

import com.android.internal.os.Clock;
import com.android.server.power.stats.BatteryStatsImpl;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class BatteryStatsService$$ExternalSyntheticLambda90
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryStatsService f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ long f$2;
    public final /* synthetic */ long f$3;
    public final /* synthetic */ long f$4;

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda90(
            int i, int i2, long j, long j2, long j3, BatteryStatsService batteryStatsService) {
        this.$r8$classId = i2;
        this.f$0 = batteryStatsService;
        this.f$1 = i;
        this.f$2 = j;
        this.f$3 = j2;
        this.f$4 = j3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BatteryStatsService batteryStatsService = this.f$0;
                int i = this.f$1;
                long j = this.f$2;
                long j2 = this.f$3;
                long j3 = this.f$4;
                synchronized (batteryStatsService.mStats) {
                    batteryStatsService.mStats.noteScreenStateLocked(i, -1, j, j2, j3);
                }
                return;
            default:
                BatteryStatsService batteryStatsService2 = this.f$0;
                int i2 = this.f$1;
                long j4 = this.f$2;
                long j5 = this.f$3;
                long j6 = this.f$4;
                synchronized (batteryStatsService2.mStats) {
                    BatteryStatsImpl batteryStatsImpl = batteryStatsService2.mStats;
                    BatteryStatsImpl.Uid uidStatsLocked =
                            batteryStatsImpl.getUidStatsLocked(batteryStatsImpl.mapUid(i2), j5, j6);
                    if (uidStatsLocked.mVibratorOnTimer == null) {
                        BatteryStatsImpl batteryStatsImpl2 = uidStatsLocked.mBsi;
                        Clock clock = batteryStatsImpl2.mClock;
                        BatteryStatsImpl.TimeBase timeBase = batteryStatsImpl2.mOnBatteryTimeBase;
                        BatteryStatsImpl.BatchTimer batchTimer =
                                new BatteryStatsImpl.BatchTimer(clock, timeBase);
                        batchTimer.mInDischarge = timeBase.mRunning;
                        uidStatsLocked.mVibratorOnTimer = batchTimer;
                    }
                    BatteryStatsImpl.BatchTimer batchTimer2 = uidStatsLocked.mVibratorOnTimer;
                    long j7 = j5 * 1000;
                    batchTimer2.recomputeLastDuration(j7, true);
                    batchTimer2.mLastAddedTimeUs = j7;
                    long j8 = j4 * 1000;
                    batchTimer2.mLastAddedDurationUs = j8;
                    if (batchTimer2.mInDischarge) {
                        batchTimer2.mTotalTimeUs += j8;
                        batchTimer2.mCount++;
                    }
                }
                return;
        }
    }
}
