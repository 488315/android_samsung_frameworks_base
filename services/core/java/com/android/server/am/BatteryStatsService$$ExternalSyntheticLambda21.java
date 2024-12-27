package com.android.server.am;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class BatteryStatsService$$ExternalSyntheticLambda21
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryStatsService f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ long f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ long f$4;
    public final /* synthetic */ long f$5;

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda21(
            int i,
            int i2,
            int i3,
            long j,
            long j2,
            long j3,
            BatteryStatsService batteryStatsService) {
        this.$r8$classId = i3;
        this.f$0 = batteryStatsService;
        this.f$1 = i;
        this.f$2 = j;
        this.f$3 = i2;
        this.f$4 = j2;
        this.f$5 = j3;
    }

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda21(
            int i, int i2, long j, long j2, long j3, BatteryStatsService batteryStatsService) {
        this.$r8$classId = 2;
        this.f$0 = batteryStatsService;
        this.f$1 = i;
        this.f$3 = i2;
        this.f$2 = j;
        this.f$4 = j2;
        this.f$5 = j3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String str;
        switch (this.$r8$classId) {
            case 0:
                BatteryStatsService batteryStatsService = this.f$0;
                int i = this.f$1;
                long j = this.f$2;
                int i2 = this.f$3;
                long j2 = this.f$4;
                long j3 = this.f$5;
                synchronized (batteryStatsService.mStats) {
                    try {
                        if (batteryStatsService.mLastPowerStateFromWifi == i) {
                            return;
                        }
                        batteryStatsService.mLastPowerStateFromWifi = i;
                        if (batteryStatsService.mStats.mOnBattery) {
                            if (i != 3 && i != 2) {
                                str = "inactive";
                                batteryStatsService.mWorker.scheduleSync(
                                        2, "wifi-data: ".concat(str));
                            }
                            str = "active";
                            batteryStatsService.mWorker.scheduleSync(2, "wifi-data: ".concat(str));
                        }
                        batteryStatsService.mStats.noteWifiRadioPowerState(i, i2, j, j2, j3);
                        return;
                    } finally {
                    }
                }
            case 1:
                BatteryStatsService batteryStatsService2 = this.f$0;
                int i3 = this.f$1;
                long j4 = this.f$2;
                int i4 = this.f$3;
                long j5 = this.f$4;
                long j6 = this.f$5;
                synchronized (batteryStatsService2.mStats) {
                    try {
                        if (batteryStatsService2.mLastPowerStateFromRadio == i3) {
                            return;
                        }
                        batteryStatsService2.mLastPowerStateFromRadio = i3;
                        batteryStatsService2.mStats.noteMobileRadioPowerStateLocked(
                                i3, i4, j4, j5, j6);
                        return;
                    } finally {
                    }
                }
            default:
                BatteryStatsService batteryStatsService3 = this.f$0;
                int i5 = this.f$1;
                int i6 = this.f$3;
                long j7 = this.f$2;
                long j8 = this.f$4;
                long j9 = this.f$5;
                synchronized (batteryStatsService3.mStats) {
                    batteryStatsService3.mStats.noteJobsDeferredLocked(i5, i6, j7, j8, j9);
                }
                return;
        }
    }
}
