package com.android.server.am;

import com.android.server.power.stats.BatteryStatsImpl;
import com.android.server.power.stats.wakeups.CpuWakeupStats;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BatteryStatsService$$ExternalSyntheticLambda32
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryStatsService f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ long f$3;
    public final /* synthetic */ long f$4;

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda32(
            int i, int i2, int i3, long j, long j2, BatteryStatsService batteryStatsService) {
        this.$r8$classId = i3;
        this.f$0 = batteryStatsService;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = j;
        this.f$4 = j2;
    }

    private final void run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda95() {
        BatteryStatsService batteryStatsService = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        long j = this.f$3;
        long j2 = this.f$4;
        CpuWakeupStats cpuWakeupStats = batteryStatsService.mCpuWakeupStats;
        synchronized (cpuWakeupStats) {
            cpuWakeupStats.mUidProcStates.put(i, i2);
        }
        synchronized (batteryStatsService.mStats) {
            batteryStatsService.mStats.noteUidProcessStateLocked(i, i2, j, j2);
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BatteryStatsService batteryStatsService = this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                long j = this.f$3;
                long j2 = this.f$4;
                synchronized (batteryStatsService.mStats) {
                    BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
                    int mapUid = batteryStatsImpl.mapUid(i);
                    if (batteryStatsImpl.mSensorNesting == 0) {
                        batteryStatsImpl.mHistory.recordStateStartEvent(j, j2, 8388608);
                    }
                    batteryStatsImpl.mSensorNesting++;
                    batteryStatsImpl
                            .getUidStatsLocked(mapUid, j, j2)
                            .getSensorTimerLocked(i2, true)
                            .startRunningLocked(j);
                }
                return;
            case 1:
                BatteryStatsService batteryStatsService2 = this.f$0;
                int i3 = this.f$1;
                int i4 = this.f$2;
                long j3 = this.f$3;
                long j4 = this.f$4;
                synchronized (batteryStatsService2.mStats) {
                    batteryStatsService2.mStats.noteUserActivityLocked(i3, i4, j3, j4);
                }
                return;
            case 2:
                run$com$android$server$am$BatteryStatsService$$ExternalSyntheticLambda95();
                return;
            default:
                BatteryStatsService batteryStatsService3 = this.f$0;
                int i5 = this.f$1;
                int i6 = this.f$2;
                long j5 = this.f$3;
                long j6 = this.f$4;
                synchronized (batteryStatsService3.mStats) {
                    BatteryStatsImpl batteryStatsImpl2 = batteryStatsService3.mStats;
                    int mapUid2 = batteryStatsImpl2.mapUid(i5);
                    int i7 = batteryStatsImpl2.mSensorNesting - 1;
                    batteryStatsImpl2.mSensorNesting = i7;
                    if (i7 == 0) {
                        batteryStatsImpl2.mHistory.recordStateStopEvent(j5, j6, 8388608);
                    }
                    BatteryStatsImpl.DualTimer sensorTimerLocked =
                            batteryStatsImpl2
                                    .getUidStatsLocked(mapUid2, j5, j6)
                                    .getSensorTimerLocked(i6, false);
                    if (sensorTimerLocked != null) {
                        sensorTimerLocked.stopRunningLocked(j5);
                    }
                }
                return;
        }
    }
}
