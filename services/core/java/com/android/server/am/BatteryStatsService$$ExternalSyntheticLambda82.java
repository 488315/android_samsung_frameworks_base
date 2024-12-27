package com.android.server.am;

import com.android.server.power.stats.BatteryStatsImpl;

public final /* synthetic */ class BatteryStatsService$$ExternalSyntheticLambda82
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryStatsService f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ long f$4;
    public final /* synthetic */ long f$5;

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda82(
            BatteryStatsService batteryStatsService,
            int i,
            String str,
            int i2,
            long j,
            long j2,
            int i3) {
        this.$r8$classId = i3;
        this.f$0 = batteryStatsService;
        this.f$2 = i;
        this.f$1 = str;
        this.f$3 = i2;
        this.f$4 = j;
        this.f$5 = j2;
    }

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda82(
            BatteryStatsService batteryStatsService, int i, String str, long j, long j2, int i2) {
        this.$r8$classId = 0;
        this.f$0 = batteryStatsService;
        this.f$1 = str;
        this.f$2 = i;
        this.f$3 = i2;
        this.f$4 = j;
        this.f$5 = j2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BatteryStatsService batteryStatsService = this.f$0;
                String str = this.f$1;
                int i = this.f$2;
                int i2 = this.f$3;
                long j = this.f$4;
                long j2 = this.f$5;
                synchronized (batteryStatsService.mStats) {
                    batteryStatsService.mStats.noteJobFinishLocked(i, i2, str, j, j2);
                }
                return;
            case 1:
                BatteryStatsService batteryStatsService2 = this.f$0;
                int i3 = this.f$2;
                String str2 = this.f$1;
                int i4 = this.f$3;
                long j3 = this.f$4;
                long j4 = this.f$5;
                synchronized (batteryStatsService2.mStats) {
                    batteryStatsService2.mStats.noteDeviceIdleModeLocked(i3, i4, str2, j3, j4);
                }
                return;
            default:
                BatteryStatsService batteryStatsService3 = this.f$0;
                int i5 = this.f$2;
                String str3 = this.f$1;
                int i6 = this.f$3;
                long j5 = this.f$4;
                long j6 = this.f$5;
                synchronized (batteryStatsService3.mStats) {
                    BatteryStatsImpl batteryStatsImpl = batteryStatsService3.mStats;
                    int mapUid = batteryStatsImpl.mapUid(i6);
                    if (batteryStatsImpl.mActiveEvents.updateState(i5, str3, mapUid, 0)) {
                        batteryStatsImpl.mHistory.recordEvent(j5, j6, i5, str3, mapUid);
                    }
                }
                return;
        }
    }
}
