package com.android.server.am;

import android.os.WorkSource;

public final /* synthetic */ class BatteryStatsService$$ExternalSyntheticLambda29
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryStatsService f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ WorkSource f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ long f$4;
    public final /* synthetic */ long f$5;

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda29(
            BatteryStatsService batteryStatsService,
            String str,
            WorkSource workSource,
            int i,
            long j,
            long j2,
            int i2) {
        this.$r8$classId = i2;
        this.f$0 = batteryStatsService;
        this.f$1 = str;
        this.f$2 = workSource;
        this.f$3 = i;
        this.f$4 = j;
        this.f$5 = j2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BatteryStatsService batteryStatsService = this.f$0;
                String str = this.f$1;
                WorkSource workSource = this.f$2;
                int i = this.f$3;
                long j = this.f$4;
                long j2 = this.f$5;
                synchronized (batteryStatsService.mStats) {
                    batteryStatsService.mStats.noteAlarmStartOrFinishLocked(
                            32781, str, workSource, i, j, j2);
                }
                return;
            default:
                BatteryStatsService batteryStatsService2 = this.f$0;
                String str2 = this.f$1;
                WorkSource workSource2 = this.f$2;
                int i2 = this.f$3;
                long j3 = this.f$4;
                long j4 = this.f$5;
                synchronized (batteryStatsService2.mStats) {
                    batteryStatsService2.mStats.noteAlarmStartOrFinishLocked(
                            16397, str2, workSource2, i2, j3, j4);
                }
                return;
        }
    }
}
