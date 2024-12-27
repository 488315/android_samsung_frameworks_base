package com.android.server.am;

import android.os.WorkSource;

public final /* synthetic */ class BatteryStatsService$$ExternalSyntheticLambda71
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryStatsService f$0;
    public final /* synthetic */ WorkSource f$1;
    public final /* synthetic */ WorkSource f$2;
    public final /* synthetic */ long f$3;
    public final /* synthetic */ long f$4;

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda71(
            BatteryStatsService batteryStatsService,
            WorkSource workSource,
            WorkSource workSource2,
            long j,
            long j2,
            int i) {
        this.$r8$classId = i;
        this.f$0 = batteryStatsService;
        this.f$1 = workSource;
        this.f$2 = workSource2;
        this.f$3 = j;
        this.f$4 = j2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BatteryStatsService batteryStatsService = this.f$0;
                WorkSource workSource = this.f$1;
                WorkSource workSource2 = this.f$2;
                long j = this.f$3;
                long j2 = this.f$4;
                synchronized (batteryStatsService.mStats) {
                    batteryStatsService.mStats.noteWifiRunningChangedLocked(
                            workSource, workSource2, j, j2);
                }
                return;
            default:
                BatteryStatsService batteryStatsService2 = this.f$0;
                WorkSource workSource3 = this.f$1;
                WorkSource workSource4 = this.f$2;
                long j3 = this.f$3;
                long j4 = this.f$4;
                synchronized (batteryStatsService2.mStats) {
                    batteryStatsService2.mStats.noteGpsChangedLocked(
                            workSource3, workSource4, j3, j4);
                }
                return;
        }
    }
}
