package com.android.server.am;

import com.android.internal.app.IBatteryStats;
import com.android.server.power.stats.BatteryStatsImpl;

import java.util.concurrent.CountDownLatch;

public final /* synthetic */ class BatteryStatsService$$ExternalSyntheticLambda15
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BatteryStatsService$$ExternalSyntheticLambda15(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((BatteryStatsImpl) obj).schedulePowerStatsSampleCollection();
                break;
            default:
                IBatteryStats iBatteryStats = BatteryStatsService.sService;
                ((CountDownLatch) obj).countDown();
                break;
        }
    }
}
