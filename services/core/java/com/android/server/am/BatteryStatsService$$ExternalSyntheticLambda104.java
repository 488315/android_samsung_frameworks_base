package com.android.server.am;

import android.os.SemModemActivityInfo;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class BatteryStatsService$$ExternalSyntheticLambda104
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryStatsService f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda104(
            BatteryStatsService batteryStatsService, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = batteryStatsService;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BatteryStatsService batteryStatsService = this.f$0;
                SemModemActivityInfo semModemActivityInfo = (SemModemActivityInfo) this.f$1;
                synchronized (batteryStatsService.mStats) {
                    batteryStatsService.mStats.updateSemModemActivityInfoLocked(
                            semModemActivityInfo);
                }
                return;
            default:
                BatteryStatsService batteryStatsService2 = this.f$0;
                String str = (String) this.f$1;
                batteryStatsService2.mWorker.scheduleSync(6, "update-network-" + str);
                return;
        }
    }
}
